(ns aoc2024.day04
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [clojure.string :as str]))

(defn- up    [[r c]] [(dec r) c])
(defn- down  [[r c]] [(inc r) c])
(defn- right [[r c]] [r       (inc c)])
(defn- left  [[r c]] [r       (dec c)])

(def ^:private up-left    (comp up left))
(def ^:private up-right   (comp up right))
(def ^:private down-left  (comp down left))
(def ^:private down-right (comp down right))

(def ^:private directions
  [up down right left up-left up-right down-left down-right])

(defn part1 [input]
  (let [crossword (str/split-lines input)]
    (count
     (for [[row-pos row] (zipmap (range) crossword)
           [col-pos letter] (zipmap (range) row)
           :when (= letter \X)
           direction directions
           :when (->> [row-pos col-pos]
                      (iterate direction)
                      (take 4)
                      (map #(get-in crossword %))
                      (= [\X \M \A \S]))]
       :found-you))))

(defn part2 [input] :todo)

(defn solve [input] ((juxt part1 part2) input))
