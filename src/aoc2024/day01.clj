(ns aoc2024.day01
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (->> input
       str/split-lines
       (map #(map parse-long (str/split % #" +")))
       (apply map vector)))

(defn part1 [lists]
  (->> lists
       (map sort)
       (apply map (comp abs -))
       (apply +)))

(defn count-occurrences [coll]
  (reduce (fn [acc elem] (update acc elem #(inc (or % 0)))) {} coll))

(defn part2 [[l1 l2]]
  (let [occurences-in-l2 (count-occurrences l2)
        similarity-score (fn [x] (* x (get occurences-in-l2 x 0)))]
    (apply + (map similarity-score l1))))

(defn solve [input] ((juxt part1 part2) (parse input)))
