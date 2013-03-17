(ns ^{:doc
      "Namespace that contains core functions for creating generic GUI components."
      :author "Adam Markham"}
    tradewc.views.generic-comp
  (:require [seesaw.core :as ssaw]))

(defn msg
  "Creates a message dialog."
  [content type option-type]
  (ssaw/dialog :content content
          :option-type option-type                                                                  
          :type type)
  ssaw/pack!
  ssaw/show!)
            







