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
   [aoc2024.day12 :as d12]
   [aoc2024.day13 :as d13]
   [aoc2024.day14 :as d14]
   [aoc2024.day15 :as d15]
   [aoc2024.day16 :as d16]
   [aoc2024.day17 :as d17]
   [aoc2024.day18 :as d18]
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
   12 d12/solve
   13 d13/solve
   14 d14/solve
   15 d15/solve
   16 d16/solve
   17 d17/solve
   18 d18/solve
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
