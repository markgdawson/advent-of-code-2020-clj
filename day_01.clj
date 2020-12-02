;; Include required namespaces
(ns aoc.core
  (:require
   [clojure.string :as str]
   [clojure.math.combinatorics :as combo]))

(defn filter-sum-return-product
  "Return the product of the first set of `n` values from `data`
  which sum to `val`."
  [data n val]
  (some
   #(if (= (reduce #'+ %1) val)
      (reduce #'* %1))
   (combo/combinations data n)))

;; Read expenses from file
(def expenses
  (->
   "resources/expenses.txt"
   slurp
   read-string))

;; Solve part 1
(filter-sum-return-product expenses 2 2020)

;; Solve part 2
(filter-sum-return-product expenses 3 2020)
