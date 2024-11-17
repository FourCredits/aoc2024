(ns aoc2024.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [aoc2023.day01 :as d01]))

(def days {})

(defn input-file [day]
  (slurp (io/resource (format "day%02d.txt" day))))

(defn perform-day [day]
  (let [input (input-file day)
        [part1 part2] (get days day)]
    {:part1 (when part1 (part1 input)) :part2 (when part2 (part2 input))}))

(defn -main [& args]
  (if-let [day (some->> args first parse-long)]
    (pp/pprint (perform-day day))
    (pp/print-table (map perform-day (keys days)))))
