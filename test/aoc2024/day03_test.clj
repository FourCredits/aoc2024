(ns aoc2024.day03-test
  (:require
   [aoc2024.day03 :refer [part1 part2]]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example input"
    (let [input "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"]
      (is (= (part1 input) 161)))
    (let [input "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"]
      (is (= (part2 input) 48)))))

(deftest ^:real real
  (let [input (slurp (io/resource "day03.txt"))]
    (testing "real input"
      (is (= (part1 input) 174960292))
      (is (= (part2 input) 56275602)))))
