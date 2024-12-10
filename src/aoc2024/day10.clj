(ns aoc2024.day10
  (:require
   [aoc2024.parsing :refer [char->int]]
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [update-value #(update % 1 char->int)]
    (into {} (comp (two-d/grid) (map update-value)) (str/split-lines input))))

(defn- next-directions [point] (map #(% point) two-d/taxicab-directions))

(def ^:private extract-trailheads
  (keep (fn [[k v]] (when (= 0 v) [0 [k]]))))

(defn part1 [grid]
  (let [next-step-up (fn [height points]
                       (->> points
                            (mapcat next-directions)
                            (filter (fn [n] (= (grid n) (inc height))))
                            distinct))
        score-trailhead (fn [[height points]]
                          (if (= height 9)
                            (count points)
                            (recur [(inc height) (next-step-up height points)])))]
    (transduce (comp extract-trailheads (map score-trailhead)) + grid)))

(defn part2 [grid]
  (let [next-step (fn [[height points]]
                    (->> points
                         (mapcat next-directions)
                         (filter (fn [n] (= (grid n) (inc height))))
                         (vector (inc height))))
        score-trailhead (fn [trailhead]
                          (->> (nth (iterate next-step trailhead) 9) second count))]
    (transduce (comp extract-trailheads (map score-trailhead)) + grid)))

(defn solve [input] ((juxt part1 part2) (parse input)))
