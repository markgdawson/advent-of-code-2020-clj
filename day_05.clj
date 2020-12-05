(ns )

(defn binary-branching [input-pass input-rows low-label]
  (loop [rows input-rows
         pass input-pass]
    (if (= 0 (count pass))
      (first rows)
      (let [n-split (/ (count rows) 2)
            [lower higher] (partition n-split rows)
            remainder (if (= low-label (first pass))
                        lower
                        higher)]
        (recur remainder (rest pass))))))

(defn row-col
  "Return a map with ids :row :col :id :pass for a given boarding `pass`."
  [pass]
  (let [row (binary-branching (take 7 pass) (range 128) \F)
        col (binary-branching (drop 7 pass) (range 8) \L)]
    {:row row :col col :id (+ (* row 8) col) :pass pass}))

;; Seat ids as a set
(def ids
  (set (map #(:id (row-col %1))
        (str/split (slurp "resources/05.txt") #"\n"))))

;; Part 1 solution
;; Use a reduction to extract seat ID from parsed seat info and compute max
(reduce #(max %1 (:id (row-col %2))) 0 seats)

;; Part 2 solution
;; My seat is the only seat missing in the range between the minimum and maximum seat.
(set/difference
 (set (range
       (apply min ids)
       (apply max ids)))
 ids)
