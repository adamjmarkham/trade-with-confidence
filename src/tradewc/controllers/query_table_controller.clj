(ns ^{:doc
      "Namespace that contains core functions for controlling the query table of stocks."
      :author "Adam Markham"}
    tradewc.controllers.query-table-controller
  (:require [tradewc.stock-model.stock-data :as sdm]
            [tradewc.views.generic-comp :as genc]
            [seesaw.core :as ssaw]))

(defn update-stock-table
  "Updates the stock-table given a root component from the view and some stock-data."
  [root table-id data]
  (let [column-names [:symbol :name :exch :type :exchDisp :typeDisp]
        table (ssaw/select root [table-id])]
    (ssaw/config!
     table
     :model
     [:columns column-names
      :rows data])))

(defn search-symbol
  "Searches for a particular symbol in the stock table."
  [parent symbol]
  (try
     (update-stock-table parent :#query-table (sdm/query-stock-symbol symbol))
    (catch IllegalArgumentException e
      (->
       (ssaw/dialog :content (.getLocalizedMessage e)
                    :type :error
                    :parent parent
                    :title "No results found!")
       ssaw/pack! ssaw/show!))))

(defn add-to-predictions
  "Adds data from the query table to a list of predictions."
  [data date-from date-to prediction-list frame]
  (if (>= (compare date-from date-to) 0)
    (->
     (ssaw/dialog :content (str "Please enter a valid date. Dates entered were:\nFrom: " date-from "\nTo: " date-to)
                  :type :error
                  :title "Invalid dates")
     ssaw/pack!
     ssaw/show!)
  (try
    (let [{:keys [name symbol]} data
          stock-data  (vec (sdm/get-stock-data-as-map symbol date-from date-to))]
      (swap! (sdm/get-predictions) sdm/add-to-predictions
             (sdm/create-prediction name symbol date-from date-to stock-data nil))
      (.addElement (ssaw/config prediction-list :model) (str symbol " - " name " " date-from " - " date-to))
      (ssaw/dispose! frame))
    (catch RuntimeException e
                          (->
                           (ssaw/dialog :content (str "Stock data could not be retrieved for:\nSymbol: " (:symbol data) "\nFrom: " date-from "\nTo: " date-to)
                                     :type :error
                                     :title "No data found!")
                           ssaw/pack!
                           ssaw/show!)))))


 







