(ns aoc2024.day02-test
  (:require
   [aoc2024.day02 :refer [parse part1 part2]]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is run-tests testing]]))

(def ^:private example
  "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")

(def ^:private real-input (slurp (io/resource "day02.txt")))

(deftest part1-tests
  (testing "example data"
    (is (= (part1 (parse example)) 2)))
  (testing "real data"
    (is (= (part1 (parse real-input)) 334))))

(deftest part2-tests
  (testing "example data"
    (is (= (part2 (parse example)) 4)))
  (testing "real data"
    (is (= (part2 (parse real-input)) 400))))

(comment
  (run-tests))
