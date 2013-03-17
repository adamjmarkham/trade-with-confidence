(ns ^{:doc
      "Namespace that contains core functions for starting the application. Its
       is where the main method is invoked from."
      :author "Adam Markham"}
    tradewc.main.core
  (:require [tradewc.controllers.main-controller :as mainctrl]
            [seesaw.core :as ssaw])
  (:gen-class))

(defn -main []
  (ssaw/native!)
  (mainctrl/build-main-controller))
