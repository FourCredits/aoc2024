(ns aoc2024.day13
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (map #(map parse-long (re-seq #"\d+" %)) (str/split input #"\n\n")))

(defn- cost-to-win [[ax ay bx by tx ty]]
  (let [a (/ (- (* ty bx) (* by tx))
             (- (* ay bx) (* by ax)))
        b (/ (- tx (* ax a)) bx)]
    (if (and (integer? a) (integer? b)) (+ (* 3 a) b) 0)))

(defn- adjust [[ax ay bx by tx ty]]
  [ax ay bx by (+ tx 10000000000000) (+ ty 10000000000000)])

(defn part1 [machines] (transduce (map cost-to-win) + machines))
(defn part2 [machines] (transduce (map (comp cost-to-win adjust)) + machines))

(defn solve [input] ((juxt part1 part2) (parse input)))
