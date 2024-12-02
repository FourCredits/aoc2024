(ns aoc2024.day02
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (->> input str/split-lines (map #(map parse-long (str/split % #" ")))))

(comment
  (let [input
        "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"
        parsed (parse input)]
    [(part1 parsed)])
  (partition 2 1 (range 1 10)))

(defn safe? [report]
  (and (or (apply < report) (apply > report))
       (->> report
            (partition 2 1)
            (remove (fn [[a b]] (<= 1 (abs (- a b)) 3)))
            empty?)))

(defn part1 [reports] (->> reports (filter safe?) count))

(defn solve [input]
  (let [parsed (parse input)]
    [(part1 parsed)]))
