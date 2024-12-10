(ns aoc2024.day10-test
  (:require
   [aoc2024.day10 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (parse (file-inputs/example-file 10))]
    (testing "example input"
      (is (= (part1 input) 36))
      (is (= (part2 input) 81)))))

(deftest ^:real real
  (let [input (parse (file-inputs/real-file 10))]
    (testing "real input"
      (is (= (part1 input) 744))
      (is (= (part2 input) 1651)))))

(comment
  (clojure.test/run-tests))
