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

(defn distance-between [[r1 c1] [r2 c2]] [(- r2 r1) (- c2 c1)])

(defn scale [n [r c]] [(* r n) (* c n)])

(defn add-vectors [[r1 c1] [r2 c2]] [(+ r1 r2) (+ c1 c2)])

(defn nth-harmonic [n v diff] (add-vectors v (scale n diff)))

(defn antinodes [positions]
  (for [[p1 p2] (all-pairs-no-swaps positions)
        :let [difference (distance-between p1 p2)]
        n [(nth-harmonic -1 p1 difference) (nth-harmonic 2 p1 difference)]]
    n))

(defn part1 [{:keys [by-frequency on-map?]}]
  (->> by-frequency (mapcat antinodes) (filter on-map?) distinct count))

(defn antinodes2 [on-map? positions]
  (for [[p1 p2] (all-pairs-no-swaps positions)
        :let [difference (distance-between p1 p2)
              nth-harmonic (fn [n] (nth-harmonic n p1 difference))]
        n (concat
           (take-while on-map? (map nth-harmonic (iterate inc 0)))
           (take-while on-map? (map nth-harmonic (iterate dec 0))))]
    n))

(defn part2 [{:keys [by-frequency on-map?]}]
  (->> by-frequency (mapcat #(antinodes2 on-map? %)) distinct count))

(defn solve [input] ((juxt part1 part2) (parse input)))
