(ns ^{:doc
      "Namespace that contains core functions for displaying stock data tables."
      :author "Adam Markham"}
    tradewc.views.table-view
  (:require [seesaw.core :as ssaw]))

(defn menus
  "Returns a vector of menu items."
  []
  (let [print-item (ssaw/menu-item :text "Print")
        save-item (ssaw/menu-item :text "Save As")]     
    [(ssaw/menu :text "File" :items [print-item
                                     save-item])]))

(defn stock-table
  "Builds a stock table inside a frame and displays it."
  [column-names rows name]
  (let [table (ssaw/scrollable (ssaw/table :model [:columns (vec column-names)
                                                   :rows rows]
                                           :show-grid? true))]
    (->
     (ssaw/frame :title name :content table :menubar (ssaw/menubar :items (menus)) :on-close :dispose)
     ssaw/pack!
     ssaw/show!)))


  


