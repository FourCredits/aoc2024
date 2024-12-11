(ns aoc2024.core
  (:gen-class)
  (:require
   [aoc2024.day01 :as d01]
   [aoc2024.day02 :as d02]
   [aoc2024.day03 :as d03]
   [aoc2024.day04 :as d04]
   [aoc2024.day05 :as d05]
   [aoc2024.day06 :as d06]
   [aoc2024.day07 :as d07]
   [aoc2024.day08 :as d08]
   [aoc2024.day09 :as d09]
   [aoc2024.day10 :as d10]
   [aoc2024.day11 :as d11]
   [clojure.java.io :as io]
   [clojure.pprint :as pp]))

(def days
  {1 d01/solve
   2 d02/solve
   3 d03/solve
   4 d04/solve
   5 d05/solve
   6 d06/solve
   7 d07/solve
   8 d08/solve
   9 d09/solve
   10 d10/solve
   11 d11/solve
   ;
   })

(defn perform-day [day]
  (let [input (slurp (io/resource (format "day%02d.txt" day)))
        f (get days day)
        [part1 part2] (f input)]
    {:day day :part1 part1 :part2 part2}))

(defn -main [& args]
  (if-let [day (some->> args first parse-long)]
    (pp/pprint (perform-day day))
    (pp/print-table (mapv perform-day (keys days)))))
