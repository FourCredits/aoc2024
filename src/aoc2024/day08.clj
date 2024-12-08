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

(defn nth-harmonic [n v diff] (two-d/add-vectors v (two-d/scale n diff)))

(defn without-resonance [on-map? [p1 p2]]
  (let [difference (two-d/distance-between p1 p2)]
    (->> [-1 2] (map #(nth-harmonic % p1 difference)) (filter on-map?))))

(defn with-resonance [on-map? [p1 p2]]
  (let [diff (two-d/distance-between p1 p2)]
    (->> [(iterate inc 0) (iterate dec 0)]
         (mapcat (fn [direction]
                   (->> direction
                        (map #(nth-harmonic % p1 diff))
                        (take-while on-map?)))))))

(defn antinodes [resonance positions]
  (mapcat resonance (all-pairs-no-swaps positions)))

(defn all-antinodes [resonance by-frequency]
  (->> by-frequency (mapcat #(antinodes resonance %)) distinct count))

(defn part1 [{:keys [by-frequency on-map?]}]
  (all-antinodes #(without-resonance on-map? %) by-frequency))

(defn part2 [{:keys [by-frequency on-map?]}]
  (all-antinodes #(with-resonance on-map? %) by-frequency))

(defn solve [input] ((juxt part1 part2) (parse input)))
