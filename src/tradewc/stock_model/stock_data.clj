(ns ^{:doc
      "Namespace contains core functions and defrecord Stock for representation and transformation of stock data. Pure functions
       transform the stock data. Also contains a list of stocks that are selected at any one time."
      :author "Adam Markham"}

  tradewc.stock-model.stock-data
  (:require [clojure.data.json :only [read-json json-str] :as js]
            [incanter.io :only [read-dataset] :as iio]
            [incanter.core :only [dataset] :as inccore]
            [clj-time.core :as time]
            [clj-time.coerce :as convert]
            [clj-time.format :as ftime]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:import [com.tradewc.datasource StockMarketDataSourceFactory]))

; Represents a stock with some data (high, low, volume etc) 
(defrecord Stock [stock symbol date-from date-to stock-data predicted actual-eval predicted-eval])

; The current list of stocks
(def ^:private stocks (atom []))

(defn get-predictions
  "Returns all stock predictions from the current list."
  []
  stocks)

(defn save-to
  "Saves any data to a specified file-path"
  [data file-path]
  (spit file-path data))

(defn get-prediction-at
  "Gets a stock prediction from a list col at a specific position pos."
  [col pos]
  (nth col pos (IndexOutOfBoundsException. "Position does not exist in predictions")))

(defn create-prediction
  "Creates a Stock defrecord for a stock, consiting of name, symbol, date,
   stock-data and the predicted value."
  [stock symbol date-from date-to stock-data predicted]
  (Stock. stock symbol date-from date-to stock-data predicted [] []))

(defn add-to-predictions
  "Adds a prediction to the end of a list col."
  [col prediction]
  (conj col prediction))

(defn- remove-at
  "Removes an item from a list col at a specified position pos."
  [col pos]
  (remove #(= (.indexOf col %) pos) col))

(defn remove-prediction
  "Removes a prediction from a list col at a specified position pos."
  [col pos]
  (vec
   (remove-at col pos)))

(defn replace-predict
  "Replaces an original prediciton orig-predict with a new prediction new-predict."
  [orig-predict new-predict]
  new-predict)

(defn update-prediction
  "Updates a prediction value from a list col at a specified position pos with predicted-val."
  [col pos predicted-val]
  (update-in col [pos :predicted] replace-predict predicted-val))

(defn update-actual-eval
  ""
  [col pos data]
  (update-in col [pos :actual-eval] replace-predict data))

(defn update-predicted-eval
  ""
  [col pos data]
  (update-in col [pos :predicted-eval] replace-predict data))

(defn convert-stock-data
  "Converts any Java map to a Clojure map."
  [lst]
  (map
   (fn [map]
     (into {}
           (for [[k v] map]
             [(keyword k) v]))) lst))

(defn get-stockmarket-data-src
  "Returns a stock market data source given a stock symbol and date-from and date-to."
  [stock-symbol date-from date-to]
  (StockMarketDataSourceFactory/getNNStockMarketDataSource stock-symbol date-from date-to))

(defn get-stock-data-as-map
  "Gets a map of stock-data from a stock-symbol, date-from and date-to."
  [stock-symbol date-from date-to]
  (try
    (let [source (get-stockmarket-data-src stock-symbol date-from date-to)]
      (try
        (doto source
          (.pullDataFromSource))
          (catch java.io.FileNotFoundException fnfe
            (throw fnfe)))
      (convert-stock-data (.getStockData source)))
    (catch IllegalArgumentException e
      (.getMessage e))))

(defn save-predictions
  "Saves data predictions to a specified file-path."
  [predictions file-path]
  (spit file-path predictions))

(defn load-predictions
  "Loads data predictions from a specified file-path, overwrites the current list of predictions."
  [file-path]
  (reset! stocks (vec (for [stock (read-string (slurp file-path))]
                        (Stock/create stock)))))

(defn map-to-json
  "Writes a Clojure data structure to JSON file at a specified file-path."
  [data-as-map complete-file-path]
  (spit
   complete-file-path
   (js/json-str data-as-map)))

(defn load-json-map
  "Loads a Clojure data structure from a JSON file at a specified file-path."
  [complete-file-path]
  (js/read-json (slurp complete-file-path)))

(defn query-stock-symbol
  "Returns a vector of maps containing stock-data matching a particular stock-symbol given a symbol to query"
  [symbol]
  (try
    (let [url (str "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=" symbol "&callback=YAHOO.Finance."
                 "SymbolSuggest.ssCallback") 
        file (slurp url)
        json (js/read-json (subs file 39 (- (count file) 1)))
        results (-> json :ResultSet :Result)]
    (if (empty? results)
      (throw (IllegalArgumentException. (str "Stock symbol " symbol "  was not found."))))
    results)
    (catch java.io.IOException ioe
      (throw (IllegalArgumentException. "Please enter a valid stock symbol to search for.")))))

(defn write-csv [data file-path]
  (with-open [out-file (io/writer file-path)]
    (csv/write-csv out-file data)))
                               