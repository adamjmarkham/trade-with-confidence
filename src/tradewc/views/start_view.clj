(ns ^{:doc
      "Namespace that contains core functions for viewing the start view."
      :author "Adam Markham"}
    tradewc.views.start-view
  (:use [seesaw.core])
  (:require [tradewc.controllers.main-controller :as mainctrl]))

(defn start-frame
  "Displays the start-frame given a frame title."
  [title]
  (let [start-frame (frame :title title :on-close :exit)
        start-button (button :text "Start TWC" :halign :center :valign :center)
        quit-button (button :text "Quit TWC" :halign :center :valign :center)
        start-panel (vertical-panel :items [start-button quit-button])]
    (listen quit-button :action (fn [e]  (-> (dialog :content "Are you sure you want to quit TWC?"
                                                :option-type :yes-no
                                                :success-fn (fn [dialog] (System/exit 0))                                                                     
                                                :type :question)
                                             pack!
                                             show!)))
    (listen start-button :action (fn [e] (mainctrl/build-main-controller)
                                   (dispose! start-frame)))
    (config! start-frame :content start-panel)
    (-> start-frame pack! show!)))

(defn show
  "Displays a GUI component on the screen."
  [component]
  (-> component pack! show!))



