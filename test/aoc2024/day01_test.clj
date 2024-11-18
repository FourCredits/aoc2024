(ns aoc2024.day01-test
  (:require [clojure.test :refer [deftest is run-tests testing]]
            [clojure.java.io :as io]
            [aoc2024.day01 :refer [part1]]))

(deftest part1-tests
  (testing "example data"
    (is (= (part1 "") :wrong)))
  (testing "real thing"
    (is (= (part1 (slurp (io/resource "day01.txt"))) :wrong))))

(comment
  (run-tests))
