(ns aoc2024.day19
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (let [[towel-section pattern-section] (str/split input #"\n\n")
        towels (str/split towel-section #", ")
        designs (str/split-lines pattern-section)]
    {:towels towels :designs designs}))

(defn- strip-prefix [s prefix] (subs s (count prefix)))

(declare memoized)
(defn- make-design2 [towels design]
  (if (.isEmpty design)
    1
    (transduce (comp
                (filter #(str/starts-with? design %))
                (map #(memoized towels (strip-prefix design %))))
               +
               towels)))

(def ^:private memoized (memoize make-design2))

(defn part1 [{:keys [towels designs]}]
  (count (filter pos? (map #(memoized towels %) designs))))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) (parse input)))
