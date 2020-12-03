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

(defn parse-line
  "Parse regular expression into map of components"
  [line]
  (as-> line v
    (re-find #"(\d+)-(\d+) (\S+): (\S+)" v)
    (zipmap [:all :num1 :num2 :char :passwd] v)
    (update-in v [:num1] read-string)
    (update-in v [:num2] read-string)
    (update-in v [:char] first)))

(defn policy-1
  "Return true if password data `p` specifies a valid rule/password combination by policy 1."
  [{:keys [num1 num2 passwd char]}]
  (let [char-count (count-chars
                    char
                    passwd)]
    (and (>= char-count num1)
         (<= char-count num2))))

;; Count passwords
(defn count-valid
  "Return number of lines in `password-input` satisfying `policy`"
  [password-input policy]
  (->>
   password-input       
   (map parse-line)     ;; extract line data
   (map policy)         ;; apply policy
   (filter #'identity)  ;; filter out nil values
   count))              ;; count entries

;; Solution to part 1
(count-valid password-input policy-1)

;; Part 2
(defn policy-2 [{:keys [num1 num2 passwd char]}]
  "Return true if passwd specifies a valid rule/password combination by policy 2."
  (= 1 (->>
        [num1 num2]
        (map dec)
        (map #(= (nth passwd %1) char))
        (filter #'identity)
        count))))

;; Solution to part 2
(count-valid password-input policy-2)
