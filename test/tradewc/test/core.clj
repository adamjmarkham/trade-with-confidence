(ns tradewc.test.core
  (:use [tradewc.stock-model.stock-data])
  (:use [clojure.test]))

(def predictions [{:open 1 :low 2 :high 3 :close 4}
                  {:open 5 :low 6 :high 7 :close 8}])

(deftest test-pred-at
  (is (= (get-prediction-at predictions 1) {:open 5 :low 6 :high 7 :close 8}))
  (is (= (get-prediction-at predictions 0) {:open 1 :low 2 :high 3 :close 4}))
  (is (thrown? Exception (get-prediction-at predictions 4))))

(deftest test-add-to
  (is (= (add-to-predictions predictions {:open 1}) {:open 1}))
  (is (= (add-to-predictions predictions {:close 2}) {:close 2})))

(deftest test-remove
  (is (= (remove-prediction predictions 0) [{:open 5 :low 6 :high 7 :close 8}]))
  (is (= (remove-prediction predictions 1) [{:open 1 :low 2 :high 3 :close 4}])))

(deftest test-replace
  (is (= (replace-predict 1 2) 2)))

(deftest update-test
  (is (= (update-prediction [{:predicted 1}] 1 2) [{:predicted 2}]))
  (is (= (update-prediction [{:predicted 2}] 2 1) [{:predicted 1}])))

(deftest test-convert
  (is (= (convert-stock-map {"close" 1 "high" 2}) {:close 1 :high 2})))
