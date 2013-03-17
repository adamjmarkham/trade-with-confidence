(ns tradewc.controllers.observers)

(def neuralnet-observer (proxy [java.util.Observer] []
                          (update [o arg]
                            (+ 1 2 3))))

