(ns ^{:doc
      "Namespace that contains core functions for displaying the stock config view."
      :author "Adam Markham"}
    tradewc.views.stock-config-view
  (:require [seesaw.core :as ssaw]
            [seesaw.mig :as ssawmig]
            [seesaw.font :as ssawfont]
            [seesaw.table :as ssawtbl]
            [seesaw.keymap :as km]
            [tradewc.controllers.query-table-controller :as qtc])
  (:import [org.jdesktop.swingx JXDatePicker]))

(defn table-columns
  "Gets the table columns as a vector."
  [column-names rows]
  (for [key [column-names]
        val rows]
    (zipmap key val)))

(defn search-stock
  "Searches for a particular stock given a search-item."
  [frame search-item]
  (qtc/search-symbol frame search-item))
       
(defn build-prediction-frame
  "Builds a prediction frame given a title and prediction-list."
  [title prediction-list]
  (let [stock-table (ssaw/table :model [:columns [:symbol :name :exch :type :exchDisp :typeDisp]
                                                   :rows []]
                                          :show-grid? true
                                          :id :query-table
                                          :selection-mode :single)
        slt  (ssaw/scrollable stock-table)
        heading (ssaw/label :text "Add Stock To Prediction List" :font (ssawfont/font :size 18))
        search-field (ssaw/text :text "Type in a stock to search for...")
        heading-stock-panel (ssawmig/mig-panel
                     :constraints ["" "[][grow][]" ""]
                     :items [[heading "wrap, growx, growy, span 2 1, align center"]])
        search-btn (ssaw/button :text "Search")
        date-from-picker (JXDatePicker. (java.util.Date.))
        date-to-picker   (JXDatePicker. (java.util.Date.))
        stock-search-panel (ssawmig/mig-panel
                           :constraints ["" "[][grow][][]" ""]
                           :items [["Date From:" "align left"][date-from-picker ""]["Date To:" "align left"][date-to-picker "wrap"]
                                  ["Search Stocks:" "align left, shrink"]
                                  [search-field "grow, align left, span 3 1, shrink 0"]
                                  [search-btn "wrap, align right"]
                                  [slt "span 5 1, grow"]])
        cancel-btn (ssaw/button :text "Cancel")
        ok-btn (ssaw/button :text "Add Stock")
        button-panel (ssawmig/mig-panel
                      :constraints ["" "[][grow][]" ""]
                      :items [[cancel-btn "align right, span 2 1"]
                              [ok-btn "align right"]])
        two-panel (ssaw/flow-panel :items [stock-search-panel])
        stock-select-panel (ssaw/border-panel :north heading-stock-panel :center two-panel)
        frame (ssaw/dialog :title "Main Menu" :content stock-select-panel :resizable? false
                           :option-type :ok-cancel
                           :success-fn (fn [f]
                                         (qtc/add-to-predictions
                                          (ssawtbl/value-at
                                           stock-table
                                           (ssaw/selection stock-table {:multi? false}))
                                          (.getDate date-from-picker) (.getDate date-to-picker)
                                          prediction-list f)))]
  (ssaw/listen search-btn :action (fn [e] (search-stock frame (ssaw/text search-field))))
  (km/map-key search-field "ENTER" (fn [e] (search-stock frame (ssaw/text search-field))))
  (ssaw/listen cancel-btn :action (fn [e]
                                    (ssaw/dispose! frame)))
  (ssaw/listen ok-btn :action (fn [e]
                                (qtc/add-to-predictions
                                 (ssawtbl/value-at
                                  stock-table
                                  (ssaw/selection stock-table {:multi? false}))
                                 (.getDate date-from-picker) (.getDate date-to-picker)
                                 prediction-list frame)))
  (ssaw/config! frame :title title)
  (-> frame ssaw/pack! ssaw/show!)
  (.setLocationRelativeTo frame nil)))





