(ns aoc2024.day01-test
  (:require
   [aoc2024.day01 :refer [parse part1 part2]]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input "3   4
4   3
2   5
1   3
3   9
3   3"
        parsed (parse input)]
    (testing "example input"
      (is (= (part1 parsed) 11))
      (is (= (part2 parsed) 31)))))

(deftest ^:real real
  (testing "real input"
    (let [parsed (parse (slurp (io/resource "day01.txt")))]
      (is (= (part1 parsed) 2192892))
      (is (= (part2 parsed) 22962826)))))
