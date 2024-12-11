(ns aoc2024.day11
  (:require
   [clojure.math :as math]
   [clojure.string :as str]))

(defn- split-in-two [element]
  (let [num-digits (int (math/floor (inc (math/log10 element))))]
    (when (even? num-digits)
      (let [n (int (math/pow 10 (/ num-digits 2)))]
        [(quot element n) (mod element n)]))))

(defn part1 [input]
  (letfn [(update-stone [value]
            (some #(% value)
                  [#(when (= % 0) [1]) split-in-two #(vector (* % 2024))]))]
    (->> (str/split (str/trim input) #" ")
         (map parse-long)
         (iterate #(mapcat update-stone %))
         (#(nth % 25))
         count)))

(defn solve [input] ((juxt part1) input))
