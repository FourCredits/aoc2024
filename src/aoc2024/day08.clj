(ns aoc2024.day08
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [grid (two-d/grid-seq (str/split-lines input))
        [[max-r max-c] _] (last grid)
        on-map? (fn [[r c]] (and (<= 0 r max-r) (<= 0 c max-c)))
        grouping (group-by second (remove #(= \. (second %)) grid))
        by-frequency (->> grouping vals (map #(mapv first %)))]
    {:by-frequency by-frequency :on-map? on-map?}))

(defn all-pairs-no-swaps [coll]
  (for [i (range (count coll))
        j (range (inc i) (count coll))]
    [(get coll i) (get coll j)]))

(defn antinodes [positions]
  (for [[[r1 c1] [r2 c2]] (all-pairs-no-swaps positions)
        :let [r-diff (- r2 r1)
              c-diff (- c2 c1)]
        n [[(+ r1 (* -1 r-diff)) (+ c1 (* -1 c-diff))]
           [(+ r1 (* 2 r-diff)) (+ c1 (* 2 c-diff))]]]
    n))

(defn part1 [{:keys [by-frequency on-map?]}]
  (->> by-frequency (mapcat antinodes) (filter on-map?) distinct count))

(defn part2 [{:keys [by-frequency on-map?]}]
  :todo)

(defn solve [input] ((juxt part1 part2) (parse input)))
