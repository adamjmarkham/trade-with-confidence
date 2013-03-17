(ns ^{:doc
      "Namespace that contains core functions for generating the main view."
      :author "Adam Markham"}
    tradewc.views.main-view
  (:require [seesaw.core :as ssaw]
            [seesaw.mig :as ssawmig]
            [seesaw.font :as ssawfont]
            [seesaw.chooser :as ssawcho]
            [tradewc.controllers.stock-data-controller :as sdc]
            [tradewc.controllers.chart-controller :as cc]
            [tradewc.views.stock-config-view :as scv]
            [tradewc.controllers.stock-table-controller :as stc]
            [tradewc.controllers.neuralnet-controller :as nnc]
            [incanter.core :as icore]
            [incanter.charts :as icharts]
            [incanter.stats :as istats]
            [clj-time.core :as time]
            [clj-time.coerce :as convert])
  (:import [org.jfree.chart ChartPanel]
           [java.awt Component Dimension]
           [javax.swing JPanel JTable]
           [java.awt BorderLayout]
           [javax.swing.border TitledBorder]
           [javax.swing BorderFactory]
           [org.jdesktop.swingx JXDatePicker]))

(defn show
  "Displays a component."
  [component]
  (-> component ssaw/pack! ssaw/show!))

(defn menu-items
  "Builds a map of menu items and updates the stock-list."
  [stock-list]
  (let [save-action (ssaw/action :name "Save Data"
                                 :handler (fn [e]
                                            (ssawcho/choose-file :type :save
                                                                 :filters [["TWC Files" ["twc"]]]
                                                                 :multi? false
                                                                 :success-fn (fn [fc file]
                                                                               (sdc/save-all-predictions (str file ".twc"))))))

        load-dialog (ssaw/dialog :title "Overwrite Previous Stocks"
                              :content "Loading data will overwrite all previous stock data loaded into the application.\nContinue?"
                              :type :warning
                              :option-type :ok-cancel
                              :default-option :cancel
                              :success-fn (fn [f]
                                            ;(ssaw/dispose! f)
                                            (ssawcho/choose-file :filters [["TWC Files" ["twc"]]]
                                                                 :multi? false
                                                                 :success-fn (fn [fc file]
                                                                               (ssaw/config! stock-list :model [])
                                                                               (sdc/load-all-predictions stock-list file)))))
        
        load-action (ssaw/action :name "Load Data"
                                 :handler (fn [e] (-> load-dialog ssaw/pack! ssaw/show!)))

        csv-action (ssaw/action :name "Export To CSV"
                                :handler (fn [e]
                                            (ssawcho/choose-file :type :save
                                                                 :filters [["CSV Files" ["csv"]]]
                                                                 :multi? false
                                                                 :success-fn (fn [fc file]
                                                                               (sdc/save-to-csv (str file ".csv")
                                                                                                (.getSelectedIndex stock-list))))))
        
        items {:export-menu [(ssaw/menu-item :text "Export To CSV"
                                             :action csv-action)]
               :file-menu [(ssaw/menu-item :text "Save Data"
                                           :id :save
                                           :action save-action)
                           (ssaw/menu-item :text "Load Data"
                                           :id :load
                                           :action load-action)]}]
    items))

(defn no-stock-dialog
  "A dialog box used to display a please select stock error message."
  []
  (->
   (ssaw/dialog :content (str "Please select a stock before continuing.")
                :type :error
                :title "No Stock Selected!")
   ssaw/pack! ssaw/show!))

(defn menus
  "Returns a vector of menus given some items."
  [items]
  (let [file-menu (ssaw/menu :text "File" :items (:file-menu items))
        export-menu (ssaw/menu :text "Export Data" :items (:export-menu items))]
    [file-menu export-menu]))

(defn render-item
  "Renders an item in the GUI."
  [renderer {:keys [value]}]
  (ssaw/config! renderer :text value))


(defn main-frame
  "Builds the main frame given a title."
  [title]
  (let [stock-list (ssaw/listbox :model [] :selection-mode :single :id :predictionlist)
        fp (ssaw/flow-panel :items [(ChartPanel. (icharts/function-plot icore/sin -10 10))])
        prediction-panel (ssaw/border-panel)
       ; chart-panel (ssaw/border-panel :center fp :id :charts)
       ; right-panel (ssaw/border-panel :center neural-net-panel)
       ; right-scroll (ssaw/scrollable right-panel :vscroll :always)
        menu-bar (ssaw/menubar :items (menus (menu-items stock-list)))
        scroll-list (ssaw/scrollable stock-list :vscroll :always)
        add-btn (ssaw/button :text "+")
        remove-btn (ssaw/button :text "-")
        candle-btn (ssaw/button :text "Candlestick Chart")
        table-btn (ssaw/button :text "View Historical Data")
        date-from (ssaw/label "")
        date-to (ssaw/label "")
        prediction-title (BorderFactory/createTitledBorder "Current Predictions")
        portfolio-panel (ssawmig/mig-panel
                         :border prediction-title
                         :constraints ["" "[grow][grow][grow]" ""]
                         :items [[scroll-list "wrap, growx, growy, span 3 1"]
                                 [add-btn ""]
                                 [remove-btn "cell 2 1, wrap, align right"]
                                 [candle-btn "wrap, span 3 1, align center"]
                                 [table-btn " span 3 1, align center, wrap"]])
       ; left-panel (ssaw/border-panel :center portfolio-panel)
       ; left-scroll (ssaw/scrollable left-panel :hscroll :always :vscroll :always)
       ;border-panel (ssaw/border-panel :center left-scroll)
        date-from-picker (JXDatePicker. (java.util.Date.))
        date-to-picker   (JXDatePicker. (java.util.Date.))
        neural-net-readout (ssaw/text :multi-line? true :editable? false :rows 6)
        neural-net-progress (ssaw/scrollable neural-net-readout)
        neural-net-title  (BorderFactory/createTitledBorder "Neural Network")
        training-from (JXDatePicker. (java.util.Date.))
        training-to (JXDatePicker. (java.util.Date.))
        train-time (ssaw/spinner :model (ssaw/spinner-model 1 :from 1 :to 100 :by 1))
        layer1-count (ssaw/spinner :model (ssaw/spinner-model 10 :from 10 :to 100 :by 1))
        layer2-count (ssaw/spinner :model (ssaw/spinner-model 10 :from 10 :to 100 :by 1))
        layer1-label (ssaw/label "")
        layer2-label (ssaw/label "")
        train-btn (ssaw/button :text "Train")
        cancel-btn (ssaw/button :text "Cancel")
        evaluate-btn (ssaw/button :text "Evaluate")
        predict-btn (ssaw/button :text "Predict")
        neural-net-panel (ssawmig/mig-panel :border neural-net-title
                                            :constraints ["" "[grow][grow][grow]" ""]
                                            :items [["Training Time (mins):" ""] [train-time "wrap"]
                                                    ["Neurones Layer 1:" ""][layer1-count ""]["Neurones Layer 2:" ""] [layer2-count "wrap"] 
                                                    ["Date From:" "align left"][training-from ""]["Date To:" "align left"][training-to "wrap"]
                                                    [neural-net-progress "span 2 2, grow"] ["Num Neurones Layer 1:" ""] [layer1-label "wrap"]
                                                    ["Num Neurones Layer 2:" ""] [layer2-label "wrap"]
                                                    [train-btn ""][cancel-btn "wrap"]
                                                    [evaluate-btn ""] [predict-btn ""]])
        split-panel (ssaw/left-right-split portfolio-panel neural-net-panel)
        frame (ssaw/frame :title "Main Menu" :content split-panel :menubar menu-bar :resizable? true :on-close :exit)]
    (.setTitleJustification prediction-title TitledBorder/CENTER)
    (.setTitleJustification neural-net-title TitledBorder/CENTER)
    (ssaw/config! frame :title title)
    (ssaw/selection! stock-list :multi? false)

    (ssaw/listen predict-btn :action (fn [e]
                                       (if (= (.getSelectedIndex stock-list) -1)
                                         (no-stock-dialog)
                                         (nnc/predict-neural-net (.getSelectedIndex stock-list)))))

    (ssaw/listen evaluate-btn :action (fn [e]
                                         (if (= (.getSelectedIndex stock-list) -1)
                                           (no-stock-dialog)
                                           (nnc/evaluate-neural-net (.getSelectedIndex stock-list)
                                                                    (.getDate training-to)))))
    (ssaw/listen cancel-btn :action (fn [e]
                                     (nnc/cancel-neural-net)))
    (ssaw/listen train-btn :action (fn [e]
                                      (if (= (.getSelectedIndex stock-list) -1)
                                           (no-stock-dialog)
                                           (let [layer1 (ssaw/selection layer1-count)
                                                 layer2 (ssaw/selection layer2-count)]
                                             (ssaw/config! layer1-label :text layer1)
                                             (ssaw/config! layer2-label :text layer2)
                                             (nnc/train-neural-net (.getSelectedIndex stock-list)
                                                                   (.getDate training-from) (.getDate training-to)
                                                                   neural-net-readout
                                                                   (* 60 (ssaw/selection train-time))                                                          
                                                                   (ssaw/selection layer1-count)
                                                                   (ssaw/selection layer2-count))))))
    (ssaw/listen table-btn :action (fn [e]
                                     (if (not (nil? (ssaw/selection stock-list)))
                                       (stc/get-stock-table (.getSelectedIndex stock-list))
                                        (->
                                           (ssaw/dialog :content "You must select a stock from the list before viewing data on it."
                                                        :type :error
                                                        :parent frame
                                                        :title "No Stock Selected!")
                                           ssaw/pack! ssaw/show!))))
    (ssaw/listen candle-btn :action (fn [e]
                                      (let [selected (ssaw/selection stock-list)
                                            list-index (.getSelectedIndex stock-list)]
                                        (if (not (= list-index -1))
                                          (cc/get-candlestick list-index)
                                          (->
                                           (ssaw/dialog :content "You must select a stock from the list before viewing data on it."
                                                        :type :error
                                                        :parent frame
                                                        :title "No Stock Selected!")
                                           ssaw/pack! ssaw/show!)))))
                                         
    (ssaw/listen add-btn :action (fn [e]
                                   (sdc/build-view stock-list)))
    (ssaw/listen remove-btn :action (fn [e]
                                      (sdc/remove-prediction (.getSelectedIndex stock-list))
                                      (.removeElement (ssaw/config stock-list :model) (ssaw/selection stock-list))))
    (show frame)
    (.setLocationRelativeTo frame nil)
    frame))






