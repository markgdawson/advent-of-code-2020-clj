(ns aoc.core
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))
;; Day 6
(def puzzle-input (slurp "resources/06.txt"))

(defn summarise-answers
  "Return sum of result of calling function `summary_fn` on each
  group in `input`."
  [input summary_fn]
  (reduce #(+ %1 (summary_fn %2))
          0
          (str/split input #"\n\n")))

;; Answer part 1
(summarise-answers puzzle-input
                   #(count (set (str/replace %1 #"\n" ""))))

(defn count-char
  "Utility functions for counting characters."
  [string char]
  (count (filter #(= %1 char) string)))

(defn part-2-group-contrib
  "Contribution from group with string `group-input` tot total"
  [group-input]
  (reduce (fn [tot ch]
            (if (= (inc (count-char group-input \newline))
                   (count-char group-input ch))
              (inc tot)
              tot))
          0
          (set group-input)))

;; Solution part 2
(summarise-answers puzzle-input
                   part-2-group-contrib)
