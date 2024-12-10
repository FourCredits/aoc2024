(ns aoc2024.day10
  (:require
   [aoc2024.parsing :refer [char->int]]
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [update-value #(update % 1 char->int)]
    (into {} (comp (two-d/grid) (map update-value)) (str/split-lines input))))

(defn- neighbours [point] (map #(% point) two-d/taxicab-directions))

(def ^:private extract-trailheads (keep (fn [[k v]] (when (= 0 v) [0 [k]]))))

(defn- mk-next-step [grid remove-duplicates?]
  (fn [[h ps]]
    (let [f (if remove-duplicates? distinct identity)
          one-step? (fn [n] (= (grid n) (inc h)))]
      (->> ps (mapcat neighbours) (filter one-step?) f (vector (inc h))))))

(defn- score-all-trailheads [grid remove-duplicates?]
  (let [next-step (mk-next-step grid remove-duplicates?)
        score-trailhead #(->> (nth (iterate next-step %) 9) second count)]
    (transduce (comp extract-trailheads (map score-trailhead)) + grid)))

(defn part1 [grid] (score-all-trailheads grid true))
(defn part2 [grid] (score-all-trailheads grid false))

(defn solve [input] ((juxt part1 part2) (parse input)))
