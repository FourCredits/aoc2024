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

(defn- update-stone [stone]
  (some #(% stone) [#(when (= % 0) [1]) split-in-two #(vector (* % 2024))]))

(defn- new-function [{:keys [cache stones]} [stone amount]]
  (let [cached (get cache stone)
        updates (or cached (update-stone stone))
        new-stones (update-vals (frequencies updates) #(* % amount))]
    {:cache (if (some? cached) cache (assoc cache stone updates))
     :stones (merge-with + stones new-stones)}))

(defn- step [{:keys [cache stones]}]
  (reduce new-function {:cache cache :stones {}} stones))

(defn- count-stones-2 [blinks stones]
  (let [state {:cache {} :stones (zipmap stones (repeat 1))}]
    (apply + (vals (:stones (nth (iterate step state) blinks))))))

(defn part1 [stones] (count-stones-2 25 stones))

(defn part2 [stones] (count-stones-2 75 stones))

(defn solve [input] ((juxt part1 part2) (parse input)))
