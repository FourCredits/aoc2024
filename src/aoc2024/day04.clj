(ns aoc2024.day04
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [c (str/split-lines input)
        grid (two-d/grid-seq c)]
    [#(keep (fn [[p l]] (when (= l %) p)) grid) (partial map #(get-in c %))]))

(defn cartesian-product [f coll1 coll2] (for [x coll1, y coll2] (f x y)))

(defn part1 [[find-letter get-letters]]
  (->> (find-letter \X)
       (cartesian-product iterate two-d/chebyshev-directions)
       (filter #(= [\X \M \A \S] (get-letters (take 4 %))))
       count))

(defn part2 [[find-letter get-letters]]
  (let [cross (apply juxt two-d/diagonal-directions)
        valid-cross? (set (map vec ["MSMS" "MMSS" "SMSM" "SSMM"]))
        cross? (comp valid-cross? get-letters cross)]
    (count (filter cross? (find-letter \A)))))

(defn solve [input] ((juxt part1 part2) (parse input)))
