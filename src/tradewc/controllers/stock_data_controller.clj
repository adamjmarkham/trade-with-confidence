(ns ^{:doc
      "Namespace that contains core functions for controlling stock data from the stock model and
       updating the views."
      :author "Adam Markham"}

    tradewc.controllers.stock-data-controller
   (:require [tradewc.views.stock-config-view :as scv]
             [tradewc.stock-model.stock-data :as sdm]
             [seesaw.core :as ssaw]))

(defn build-view
  "Builds the add stock prediction view given a prediction-list"
  [prediction-list]
  (def main-frame (scv/build-prediction-frame "Add Stock Prediction" prediction-list)))

(defn remove-prediction
  "Removes a prediction from the model at a particular position pos."
  [pos]
  (swap! (sdm/get-predictions) sdm/remove-prediction pos))

(defn save-all-predictions
  "Saves all predictions currently loaded."
  [file-path]
  (sdm/save-predictions
   (vec (for [m @(sdm/get-predictions)]
          (into {} m)))
   file-path))

(defn load-all-predictions
  "Loads all predictions into the prediction-list given a file."
  [prediction-list file]
  (sdm/load-predictions file)
  (ssaw/config! prediction-list
                :model (vec (for [stock @(sdm/get-predictions)]
                              (str (:symbol stock) " - " (:stock stock))))))

(defn get-dates
  "Gets the dates from the stock model for a particular stock at
   a specific position pos."
  [pos]
  (let [{from :date-from to :date-to} (sdm/get-prediction-at @(sdm/get-predictions) pos)]
    [from to]))

(defn save-to-csv
  "Saves the evaluation of the neural network to a file."
  [file stock-index]
  (let [{actual :actual-eval predicted :predicted-eval} (sdm/get-prediction-at @(sdm/get-predictions) stock-index)]
    (sdm/write-csv [(into ["Actual"] actual)
                    (into ["Predicted"] predicted)] file)))








