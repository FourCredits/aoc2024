(ns aoc2024.day16
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(def ^:private anticlockwise
  {two-d/up two-d/left
   two-d/left two-d/down
   two-d/down two-d/right
   two-d/right two-d/up})

(def ^:private clockwise
  {two-d/up two-d/right
   two-d/right two-d/down
   two-d/down two-d/left
   two-d/left two-d/up})

(defn part1 [input]
  (let [groups (->> input
                    str/split-lines
                    two-d/grid
                    two-d/group-positions-by-value)
        walls (set (groups \#))
        options (fn [[cost direction pos]]
                  (let [pos' (direction pos)]
                    (cond-> [[(+ cost 1000) (clockwise direction) pos]
                             [(+ cost 1000) (anticlockwise direction) pos]]
                      (not (walls pos')) (conj [(inc cost) direction pos']))))
        start (first (groups \S))
        end (first (groups \E))]
    (loop [states [[0 two-d/right start]]
           visited {}
           best nil]
      (if-let [[[cost direction pos :as state] & ss] (seq states)]
        (let [previous-cost (visited [direction pos])
              visited' (assoc visited [direction pos] cost)]
          (cond
            (and (some? previous-cost) (< previous-cost cost)) (recur ss visited best)
            (and (= pos end) (or (nil? best) (< cost best))) (recur ss visited' cost)
            :else (recur (concat ss (options state)) visited' best)))
        best))))

(defn part2 [input] :todo)

(defn solve [input] ((juxt part1 part2) input))
