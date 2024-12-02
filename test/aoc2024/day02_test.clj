(ns aoc2024.day02-test
  (:require
   [aoc2024.day02 :refer [parse part1]]
   [clojure.test :refer [deftest is run-tests testing]]
   [clojure.java.io :as io]))

(def ^:private example-part1
  "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")

(def ^:private real-input (slurp (io/resource "day02.txt")))

(deftest part1-tests
  (testing "example data"
    (is (= (part1 (parse example-part1)) 2)))
  (testing "real data"
    (is (= (part1 (parse real-input)) 334))))

(comment
  (run-tests))
