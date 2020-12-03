(ns aoc.core
  (:require
   [clojure.string :as str]
   [clojure.math.combinatorics :as combo]))

(defn skip-n
  "Select every nth element of sequence."
  [n coll]
  (keep-indexed #(if (= 0 (mod %1 n)) %2 nil) coll))

(defn count-trees
  "Count trees along path"
  [right down]
  (count
   (filter #'identity
           (map (fn [row col]
                  (= (nth (cycle row) col) \#))
                (skip-n down
                        (str/split (slurp "resources/03.txt") #"\n"))
                (skip-n right (range))))))

;; Part 1 answer
(count-trees 3 5)

;; Part 2 answer
(reduce
 (fn [acc [right down]]
   (* acc (count-trees right down)))
 1 '((1 1) (3 1) (5 1) (7 1) (1 2)))
