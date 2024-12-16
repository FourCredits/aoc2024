(ns aoc2024.day16
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [groups (->> input
                    str/split-lines
                    two-d/grid
                    two-d/group-positions-by-value)]
    {:walls (set (groups \#))
     :start (first (groups \S))
     :end (first (groups \E))}))

(defn options [walls [cost direction pos path]]
  (let [pos' (direction pos)]
    (cond-> [[(+ cost 1000) (two-d/clockwise direction) pos path]
             [(+ cost 1000) (two-d/anticlockwise direction) pos path]]
      (not (walls pos')) (conj [(inc cost) direction pos' (conj path pos')]))))

(defn part1 [{:keys [walls start end]}]
  (loop [states [[0 two-d/right start [start]]]
         visited {}
         best nil]
    (if-let [[[cost direction pos :as state] & ss] (seq states)]
      (let [previous-cost (visited [direction pos])
            visited' (assoc visited [direction pos] cost)]
        (cond
          (and (some? previous-cost) (< previous-cost cost)) (recur ss visited best)
          (and (= pos end) (or (nil? best) (< cost best))) (recur ss visited' cost)
          :else (recur (concat ss (options walls state)) visited' best)))
      best)))

(defn part2 [{:keys [walls start end]}]
  (loop [states [[0 two-d/right start [start]]]
         visited {}
         paths {}]
    (if-let [[[cost direction pos path :as state] & ss] (seq states)]
      (let [previous-cost (visited [direction pos])
            visited' (assoc visited [direction pos] cost)]
        (cond
          (and (some? previous-cost) (< previous-cost cost)) (recur ss visited paths)
          (= pos end) (recur ss visited' (update paths cost #(conj (or % []) path)))
          :else (recur (concat ss (options walls state)) visited' paths)))
      (count (into #{} cat (val (apply min-key key paths)))))))

(defn solve [input] ((juxt part1 part2) (parse input)))
