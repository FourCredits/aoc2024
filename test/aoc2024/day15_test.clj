(ns aoc2024.day15-test
  (:require
   [aoc2024.day15 :refer [part1 part2]]
   [clojure.test :refer [deftest is testing]]
   [aoc2024.file-inputs :as file-inputs]))

(deftest ^:example example
  (testing "example data"
    (let [input (file-inputs/example-file 15)]
      (is (= (part1 input) 10092))
      (is (= (part2 input) 9021)))))

(deftest ^:real real
  (testing "real data"
    (let [input (file-inputs/real-file 15)]
      (is (= (part1 input) 1577255))
      (is (= (part2 input) 1597035)))))
