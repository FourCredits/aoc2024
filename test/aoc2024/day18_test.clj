(ns aoc2024.day18-test
  (:require
   [aoc2024.day18 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example input"
    (let [input (parse (file-inputs/example-file 18))]
      (is (= (part1 [6 6] 12 input) 22))
      (is (= (part2 [6 6] input) "6,1")))))

(deftest ^:real real
  (testing "real input"
    (let [input (parse (file-inputs/real-file 18))]
      (is (= (part1 input) 336))
      (is (= (part2 input) "24,30")))))
