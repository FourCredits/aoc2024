(ns aoc2024.day08
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [grid (two-d/grid-seq (str/split-lines input))]
    {:by-frequency (vals (dissoc (two-d/group-positions-by-value grid) \.))
     :on-map? #(two-d/on-grid? grid %)}))

(defn all-pairs-no-swaps [coll]
  (for [i (range (count coll))
        j (range (inc i) (count coll))]
    [(get coll i) (get coll j)]))

(defn- nth-harmonic [v diff n] (two-d/add-vectors v (two-d/scale n diff)))

(defn- antinodes [{:keys [on-map? by-frequency]} resonance-vectors]
  (count (distinct (for [positions by-frequency
                         [p1 p2] (all-pairs-no-swaps positions)
                         :let [diff (two-d/distance-between p1 p2)]
                         resonance-vector resonance-vectors
                         harmonic resonance-vector
                         :let [pos (nth-harmonic p1 diff harmonic)]
                         :while (on-map? pos)]
                     pos))))

(defn part1 [args] (antinodes args [[-1] [2]]))
(defn part2 [args] (antinodes args [(iterate dec -1) (iterate inc 0)]))

(defn solve [input] ((juxt part1 part2) (parse input)))
