;; Include required namespaces
(ns aoc.core
  (:require
   [clojure.string :as str]))

(def password-input
  "Read the password input as list of lines"
  (->
   "resources/passwords.txt"
   slurp
   (str/split #"\n")))

(defn count-chars
  "Count occurances of character `character` in `string`"
  [character string]
  (count
   (filter (partial = character) string)))

(defn validate-passwd
  "Return true if `line` specifies a valid rule/password combination."
  [line]
  (let [[all min max character passwd]
        (re-find #"(\d+)-(\d+) (\S+): (\S+)" line)
        char-count (count-chars (first character) passwd)]
    (and (>= char-count (read-string min))
         (<= char-count (read-string max)))))

;; Use function to  valid passwords
(->>
 password-input                       ;; -> list of lines
 (map validate-passwd)                ;; -> list of valid/invalid flags
 (filter #'identity)                  ;; -> filter out invalid flags
 count)                               ;; -> count entries
