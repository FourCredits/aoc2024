(ns aoc2024.day13
  (:require
   [clojure.string :as str]))

(defn part1 [input]
  (->> (str/split input #"\n\n")
       (map #(let [[ax ay bx by tx ty] (map parse-long (re-seq #"\d+" %))
                   a (/ (- (* ty bx) (* by tx))
                        (- (* ay bx) (* by ax)))
                   b (/ (- tx (* ax a)) bx)]
               (if (integer? a) (+ (* 3 a) b) 0)))
       (apply +)))

(defn part2 [input]
  (->> (str/split input #"\n\n")
       (map #(let [[ax ay bx by tx ty] (map parse-long (re-seq #"\d+" %))
                   tx (+ tx 10000000000000)
                   ty (+ ty 10000000000000)
                   a (/ (- (* ty bx) (* by tx))
                        (- (* ay bx) (* by ax)))
                   b (/ (- tx (* ax a)) bx)]
               (if (integer? a) (+ (* 3 a) b) 0)))
       (apply +)))

(defn solve [input] ((juxt part1 part2) input))
