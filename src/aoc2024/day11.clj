(ns aoc2024.day11
  (:require
   [clojure.math :as math]
   [clojure.string :as str]))

(defn parse [input] (map parse-long (str/split (str/trim input) #" ")))

(defn- split-in-two [stone]
  (let [num-digits (int (math/floor (inc (math/log10 stone))))]
    (when (even? num-digits)
      (let [n (int (math/pow 10 (/ num-digits 2)))]
        [(quot stone n) (mod stone n)]))))

(defn- tick-stone [stone]
  (some #(% stone) [#(when (= % 0) [1]) split-in-two #(vector (* % 2024))]))

(defn- tick-same-number-stones [[stone amount]]
  (update-vals (frequencies (tick-stone stone)) #(* % amount)))

(defn- tick [stones]
  (transduce (map tick-same-number-stones) (partial merge-with +) stones))

(defn- count-stones [blinks stones]
  (apply + (vals (nth (iterate tick (frequencies stones)) blinks))))

(defn part1 [stones] (count-stones 25 stones))
(defn part2 [stones] (count-stones 75 stones))

(defn solve [input] ((juxt part1 part2) (parse input)))
