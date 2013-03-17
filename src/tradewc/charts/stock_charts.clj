(ns ^{:doc
      "Namespace that contains core functions for processing and viewing stock charts."
      :author "Adam Markham"}
    tradewc.charts.stock-charts
  (:require [incanter.core :as ic]
            [incanter.charts :as ichart]
            [incanter.pdf :as ipdf]))

(defn save-chart
  "Function for saving a chart."
  [chart save-to]
  (ipdf/save-pdf chart save-to))

(defn map-to-dataset
  "Converts data to an incanter dataset."
  [data]
  (ic/dataset [:date :high :low :open :volume :close]
   (map
    (fn [day]
      {:date (:date day)
       :high (:high day)
       :low (:low day)
       :volume (:volume day)
       :open (:open day)
       :close (:close day)})
    data)))

(defn candle-stick
  "Generates a candlestick chart"
  [data stock-name stock-symbol]
  (ichart/candle-stick-plot
   :data (map-to-dataset data)
   :title (str "Candlestick Chart\n" stock-symbol " - " stock-name)
   :x-label "Date"
   :y-label "Price"))



