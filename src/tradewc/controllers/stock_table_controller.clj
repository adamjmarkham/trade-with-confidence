(ns ^{:doc
      "Namespace that contains core functions for controlling the stock data table."
      :author "Adam Markham"}

    tradewc.controllers.stock-table-controller
  (:require [tradewc.views.table-view :as tv]
            [tradewc.stock-model.stock-data :as sdm]))

(defn get-stock-table
  "Gets the stock table once stocks have been loaded into it."
  [selected-index]
  (let [{stock-data :stock-data name :stock stock-symbol :symbol} (sdm/get-prediction-at
                                                                      @(sdm/get-predictions)
                                                                      selected-index)]
    (tv/stock-table
     (cons :date
           (remove #{:date} (keys (first stock-data))))
     stock-data
     (str stock-symbol " - " name))))









