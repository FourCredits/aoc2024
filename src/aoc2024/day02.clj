(ns aoc2024.day02
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (->> input str/split-lines (map #(map parse-long (str/split % #" ")))))

(defn- small-jump? [[a b]] (<= 1 (abs (- a b)) 3))

(defn- safe? [report]
  (and (or (apply < report) (apply > report))
       (every? small-jump? (partition 2 1 report))))

(defn part1 [reports] (count (filter safe? reports)))

(defn- variations-with-one-missing [report]
  (for [i (range (count report))]
    (concat (take i report) (drop (inc i) report))))

(defn- safe-with-dampener? [report]
  (some safe? (cons report (variations-with-one-missing report))))

(defn part2 [reports] (count (filter safe-with-dampener? reports)))

(defn solve [input] ((juxt part1 part2) (parse input)))
