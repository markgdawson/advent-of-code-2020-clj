(ns aoc.core
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))

(def valid-fields #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})
(def input (slurp "resources/04.txt"))

(defn validate-presence
  "Validation strategy for Part 1."
  [kv]
  (empty?
   (set/difference valid-fields
                   (set (keys kv)))))

(defn validate-yr-range
  "Helper for validation of date ranges."
  [yr-str start end]
  (let [yr (read-string yr-str)]
    (and (>= yr start)
         (<= yr end))))

(defn validate-height
  "Helper for height validation."
  [height]
  (let [[all n unit] (re-find #"(\d+)(cm|in)" height)
        n (if n (read-string n) n)]
    (or
     (and (= unit "cm") (>= n 150) (<= n 193))
     (and (= unit "in") (>= n 59) (<= n 76)))))

(defn validate-fields
  "Validation strategy for Part 2."
  [passport]
  (let [{:strs [byr iyr eyr hgt hcl ecl pid]} passport]
    (and
     (validate-presence passport)
     (validate-yr-range byr 1920 2002)
     (validate-yr-range iyr 2010 2020)
     (validate-yr-range eyr 2020 2030)
     (validate-height hgt)
     (re-find #"^#[0-9a-f]{6}$" hcl)
     (some #(= ecl %1) '("amb" "blu" "brn" "gry" "grn" "hzl" "oth"))
     (re-find #"^[0-9]{9}$" pid))))

(defn validate-passports
  "Validate passports with validator."
  [input validator]
  (->> (str/split input #"\n\n")
       ;; extract keys as list of alternating key/value pairs
       (map #(str/split %1 #" |\n|:"))
       ;; turn alternating list into hash map
       (map (partial apply hash-map))
       ;; count valid passports
       (reduce #(if (validator %2) (inc %1) %1) 0)))

;; Part 1
(validate-passports input validate-presence)

;; Part 2 solution
(validate-passports input validate-fields)
