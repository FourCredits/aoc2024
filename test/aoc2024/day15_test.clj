(ns aoc2024.day15-test
  (:require
   [aoc2024.day15 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example data"
    (let [input (parse (file-inputs/example-file 15))]
      (is (= (part1 input) 10092))
      (is (= (part2 input) 9021)))))

(deftest ^:real real
  (testing "real data"
    (let [input (parse (file-inputs/real-file 15))]
      (is (= (part1 input) 1577255))
      (is (= (part2 input) 1597035)))))

(comment
  (clojure.test/run-tests))
