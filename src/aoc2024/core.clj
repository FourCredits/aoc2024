(ns aoc2024.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [aoc2024.day01 :as d01]))

(def days {1 d01/solve})

(defn perform-day [day]
  (let [input (slurp (io/resource (format "day%02d.txt" day)))
        f (get days day)
        [part1 part2] (f input)]
    {:day day :part1 part1 :part2 part2}))

(defn -main [& args]
  (if-let [day (some->> args first parse-long)]
    (pp/pprint (perform-day day))
    (pp/print-table (mapv perform-day (keys days)))))
