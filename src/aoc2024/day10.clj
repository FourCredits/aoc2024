(ns aoc2024.day10
  (:require
   [aoc2024.parsing :refer [char->int]]
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn part1 [input]
  (let [update-value #(update % 1 char->int)
        grid (into {} (comp two-d/grid (map update-value)) (str/split-lines input))
        next-directions (fn [p] (map (fn [dir] (dir p)) two-d/taxicab-directions))
        next-step-up (fn [height points]
                       (->> points
                            (mapcat next-directions)
                            (filter (fn [n] (= (grid n) (inc height))))
                            distinct))
        score-trailhead (fn [[height points]]
                          (if (= height 9)
                            (count points)
                            (recur [(inc height) (next-step-up height points)])))
        score-all (comp (keep (fn [[k v]] (when (= 0 v) [0 [k]])))
                        (map score-trailhead))]
    (transduce score-all + grid)))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) input))
