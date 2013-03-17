(ns ^{:doc
      "Namespace that contains core functions for generating charts to
       represent stock data with."
      :author "Adam Markham"}
  tradewc.controllers.chart-controller
    (:require [tradewc.charts.stock-charts :as sc]
              [tradewc.stock-model.stock-data :as sdm]
              [incanter.core :as ic]))

(defn get-candlestick
  "Generates a candlestick chart for a particular stock at a stock-index"
  [data-index]
  (let [{data :stock-data name :stock stock-symbol :symbol} (sdm/get-prediction-at @(sdm/get-predictions) data-index)]
    (ic/view
     (sc/candle-stick data name stock-symbol))))






