(ns aoc2024.day06
  (:require [aoc2024.two-d :as two-d]
            [aoc2024.file-inputs :as file-inputs]
            [clojure.string :as str]))

(comment
  (let [input (file-inputs/example-file 6)]
    (let [grid (two-d/grid-seq (str/split-lines input))
          by-type (update-vals (group-by second grid) #(map first %))
          [[max-r max-c] _] (last grid)
          obstacle? (set (by-type \#))
          in-bounds? (fn [[r c]] (and (<= 0 r max-r) (<= 0 c max-c)))]
      (loop [guard (first (by-type \^))
             direction two-d/up
             visited #{guard}]
        (let [new-pos (direction guard)]
          (cond
            (not (in-bounds? new-pos)) (count visited)
            (obstacle? new-pos) (recur guard
                                       (condp = direction
                                         two-d/up    two-d/right
                                         two-d/right two-d/down
                                         two-d/down  two-d/left
                                         two-d/left  two-d/up)
                                       visited)
            :else (recur new-pos direction (conj visited new-pos)))))))
  1)

(defn part1 [input]
  (let [grid (two-d/grid-seq (str/split-lines input))
        by-type (update-vals (group-by second grid) #(map first %))
        [[max-r max-c] _] (last grid)
        obstacle? (set (by-type \#))
        in-bounds? (fn [[r c]] (and (<= 0 r max-r) (<= 0 c max-c)))
        next-direction (fn [d]
                         (condp = d
                           two-d/up    two-d/right
                           two-d/right two-d/down
                           two-d/down  two-d/left
                           two-d/left  two-d/up))]
    (loop [guard (first (by-type \^))
           direction two-d/up
           visited #{guard}]
      (let [new-pos (direction guard)]
        (cond
          (not (in-bounds? new-pos)) (count visited)
          (obstacle? new-pos) (recur guard (next-direction direction) visited)
          :else (recur new-pos direction (conj visited new-pos)))))))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) input))
