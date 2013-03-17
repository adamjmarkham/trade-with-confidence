(ns ^{:doc
      "Namespace that contains core functions starting the application."
      :author "Adam Markham"}
   
    tradewc.controllers.main-controller
  (:require [tradewc.views.main-view :as mainview]))

(defn build-main-controller
  "Builds the main frame after the application is started."
  []
  (def main-view (mainview/main-frame "Trade With Confidence - Main View")))



