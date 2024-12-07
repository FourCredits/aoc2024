(ns aoc2024.day06
  (:require [aoc2024.two-d :as two-d]
            [clojure.string :as str]))

(defn parse [input]
  (let [grid (two-d/grid-seq (str/split-lines input))
        by-type (update-vals (group-by second grid) #(map first %))
        [[max-r max-c] _] (last grid)]
    {:in-bounds? (fn [[r c]] (and (<= 0 r max-r) (<= 0 c max-c)))
     :obstacles (set (by-type \#))
     :start (first (by-type \^))}))

;; TODO: move to two-d
(defn- turn-right [direction]
  (condp = direction
    two-d/up    two-d/right
    two-d/right two-d/down
    two-d/down  two-d/left
    two-d/left  two-d/up))

(defn- simulate-guard [obstacles start in-bounds?]
  (loop [pos start
         dir two-d/up
         visited #{[pos dir]}]
    (let [pos' (dir pos)]
      (cond
        (visited [pos' dir]) :loop
        (not (in-bounds? pos')) (set (map first visited))
        (obstacles pos') (let [dir' (turn-right dir)]
                           (recur pos dir' (conj visited [pos dir'])))
        :else (recur pos' dir (conj visited [pos' dir]))))))

(defn part1 [{:keys [in-bounds? obstacles start]}]
  (count (simulate-guard obstacles start in-bounds?)))

(defn part2 [{:keys [in-bounds? obstacles start]}]
  (let [unobstructed-path (simulate-guard obstacles start in-bounds?)]
    (->> (disj unobstructed-path start)
         (filter #(= :loop (simulate-guard (conj obstacles %) start in-bounds?)))
         count)))

(defn solve [input] ((juxt part1 part2) (parse input)))
