(ns aoc2024.day19
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (let [[towel-section pattern-section] (str/split input #"\n\n")
        towels (str/split towel-section #", ")
        designs (str/split-lines pattern-section)]
    {:towels towels :designs designs}))

(defn- strip-prefix [s prefix] (subs s (count prefix)))

(def ^:private make-designs
  (letfn [(foo [towels design]
            (let [xf (comp
                      (filter #(str/starts-with? design %))
                      (map #(make-designs towels (strip-prefix design %))))]
              (if (.isEmpty design) 1 (transduce xf + towels))))]
    (memoize foo)))

(defn part1 [{:keys [towels designs]}]
  (->> designs (map #(make-designs towels %)) (filter pos?) count))

(defn part2 [{:keys [towels designs]}]
  (transduce (map #(make-designs towels %)) + designs))

(defn solve [input] ((juxt part1 part2) (parse input)))
