(ns ^{:doc
      "Namespace that contains core functions for controlling the list of predictions"
      :author "Adam Markham"}
    tradewc.controllers.prediction-list-controller
  (:require [tradewc.stock-model.stock-data :as sdm]))

(defn remove-prediction
  "Removes a prediction at a particular position pos."
  [pos]
  (swap! (sdm/get-predictions) sdm/remove-prediction pos))



