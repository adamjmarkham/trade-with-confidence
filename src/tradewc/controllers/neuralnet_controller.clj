(ns ^{:doc
      "Namespace that contains core functions for controlling the neural network implementation written in Java."
      :author "Adam Markham"}
    tradewc.controllers.neuralnet-controller
  (:require [tradewc.stock-model.stock-data :as sdm]
            [seesaw.core :as ssaw]
            [clj-time.core :as time]
            [clj-time.coerce :as convert]
            [incanter.core :as icore]
            [incanter.charts :as icharts])
  (:import [com.tradewc.neuralnetwork NeuralNetworkFactory]
           [com.tradewc.neuralnetwork StockMarketNeuralNetwork]
           [com.tradewc.datasource NNStockMarketDataSource]
           [com.tradewc.datasource YahooFinanceDataSource]))

(defn create-observer
  "Function that creates an object which implements the Java Observer interface."
  [component-to-update]
  (reify java.util.Observer
    (update [this o arg]
      (ssaw/config! component-to-update :text (str arg)))))

(def neural-net (atom (NeuralNetworkFactory/createStockMarketPriceNeuralNetwork
                       (sdm/get-stockmarket-data-src "RBS.L"
                                                     (.toDate (time/date-time 2006 3 2))
                                                     (.toDate (time/date-time 2007 3 2)))
                       10 10)))

(def task (atom (future)))

(defn create-neural-net
  "Function that constructs a neural network."
  []
  (def neural-net (atom (NeuralNetworkFactory/createStockMarketPriceNeuralNetwork
                       (sdm/get-stockmarket-data-src "RBS.L"
                                                     (.toDate (time/date-time 2006 3 2))
                                                     (.toDate (time/date-time 2007 3 2)))
                       10 10))))

(defn swap-neural-net
  "Function which swaps an old neural net for a new one."
  [old-net new-net]
  new-net)

(defn get-task
  "Function which returns a background future task for running the old neural net
   in the background."
  [old-task progress-component training-time]
  (future
      (doto @neural-net
        (.addObserver (create-observer progress-component))
        (.setTrainingTime training-time)
        (.trainForTime))
      (-> 
       (ssaw/dialog :content "Finished training neural network.\nYou may now use it for price predictions."
                    :type :info
                    :title "Training Complete")
       ssaw/pack! ssaw/show!)))

(defn cancel-neural-net
  "Function which cancels the current neural network which is being trained."
  []
  (future-cancel @task))

(defn training-not-complete
  "Function which displays a training not complete dialog"
  []
  (->
   (ssaw/dialog :content "Training has not been completed! You must train the neural network first."
                :type :error
                :title "Training Not Complete!")
   ssaw/pack! ssaw/show!))

(defn prediction-dialog
  "Function which shows a dialog with a price prediction."
  [stock stock-symbol val]
  (->
   (ssaw/dialog :content (str "The predicted adjusted closing price % change for " stock " - " stock-symbol " is: " val "%")
                :type :info
                :title "Prediction Calculated!")
   ssaw/pack! ssaw/show!))

(defn train-neural-net
  "Function which trains a neural net given a training-time and num of neurones
   in hidden layers."
  [data-index from to progress-component train-time layer1 layer2]
  (let [{stock :stock stock-symbol :symbol} (sdm/get-prediction-at @(sdm/get-predictions) data-index)
        data-src (sdm/get-stockmarket-data-src stock-symbol from to)]
    (swap! neural-net swap-neural-net (NeuralNetworkFactory/createStockMarketPriceNeuralNetwork data-src layer1 layer2))
    (swap! task get-task progress-component train-time)))

(defn predict-neural-net
  "Function which gets a prediction from the neural network."
  [stock-index]
  (let [{stock-symbol :symbol stock :stock prediction-val :predicted} (sdm/get-prediction-at @(sdm/get-predictions) stock-index)]
      (if (.trainingComplete @neural-net)
        (let [prediction (.predict @neural-net stock-symbol)]
          (swap! (sdm/get-predictions) sdm/update-prediction stock-index (* prediction 100))
          (prediction-dialog stock stock-symbol (format "%.3f" (* prediction 100))))
        (training-not-complete))))

(defn evaluate-neural-net
  "Function which evaluates the current neural network."
  [stock-index from]
  (if (.trainingComplete @neural-net)
    (let [{stock-symbol :symbol} (sdm/get-prediction-at @(sdm/get-predictions) stock-index)
          to (time/plus (convert/from-date from) (time/days 60))
          evaluation (.evaluate @neural-net stock-symbol from (convert/to-date to))
          percentage-eval (vec (map (fn [[a b]] [(* 100 a) (* 100 b)]) evaluation))
          actual-data (vec (map (fn [[a b]] a) percentage-eval))
          predicted-data (vec (map (fn [[a b]] b) percentage-eval))
          plot (icharts/line-chart (vec (range 1 (+ (count evaluation) 1)))
                                   actual-data
                                   :legend true
                                   :series-label "Actual % Change"
                                   :x-label "Days"
                                   :y-label "% Price Change"
                                   :title (str "Comparison of Actual & Predicted % Price Change For - " stock-symbol))
          actual-data-set (zipmap (->> actual-data count (+ 1) (range 1)) actual-data)
          predicted-data-set (zipmap (->> predicted-data count (+ 1) (range 1)) predicted-data)
          table (icore/data-table (icore/to-dataset [actual-data-set predicted-data-set]))]
      (swap! (sdm/get-predictions) sdm/update-actual-eval stock-index actual-data)
      (swap! (sdm/get-predictions) sdm/update-predicted-eval stock-index predicted-data)
      (icharts/add-categories plot
                              (vec (range 1 (+ (count evaluation) 1)))
                              predicted-data
                              :series-label "Predicted % Change")
      (-> (ssaw/frame :title stock-symbol :content (ssaw/scrollable table)) ssaw/pack! ssaw/show!)
      (doto (icore/view plot)
        (.setTitle stock-symbol)))
    (training-not-complete)))
