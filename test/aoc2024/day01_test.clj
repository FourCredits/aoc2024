(ns aoc2024.day01-test
  (:require [clojure.test :refer [deftest is run-tests testing]]
            [clojure.java.io :as io]
            [aoc2024.day01 :refer [part1 part2 parse]]))

(def ^:private example
  "3   4
4   3
2   5
1   3
3   9
3   3")

(def ^:private real-input (slurp (io/resource "day01.txt")))

(deftest part1-tests
  (testing "example data"
    (is (= (part1 (parse example)) 11)))
  (testing "real input"
    (is (= (part1 (parse real-input)) 2192892))))

(deftest part2-tests
  (testing "example data"
    (is (= (part2 (parse example)) 31)))
  (testing "real input"
    (is (= (part2 (parse real-input)) 22962826))))

(comment
  (run-tests))
