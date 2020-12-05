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
     (re-find #"^amb|blu|brn|gry|grn|hzl|oth$" ecl)
     (re-find #"^[0-9]{9}$" pid))))

(defn count-valid-passports
  "Count passports for which validator returns true.
  Validator is a function which takes the passport as a map."
  [input validator]
  
  (reduce (fn [n passport-text]
            (if (validator
                 (apply hash-map
                        (str/split passport-text
                                   #" |\n|:")))
              (inc n)
              n))
          0
          (str/split input #"\n\n")))

;; Part 1
(validate-passports input validate-presence)

;; Part 2 solution
(validate-passports input validate-fields)
