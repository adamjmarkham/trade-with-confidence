(ns ^{:doc
      "Namespace that contains core functions for starting off the applications main menu."
      :author "Adam Markham"}
    tradewc.controllers.start-controller
  (:require [tradewc.views.start-view :as startview]))

(defn start-tradewc
  "Function responsible for starting the application and displaying the main view."
  []
  (startview/start-frame "Main Menu"))





