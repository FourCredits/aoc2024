(ns aoc2024.day03-test
  (:require
   [aoc2024.day03 :refer [part1 part2]]
   [clojure.test :refer [deftest is testing]]
   [aoc2024.file-inputs :as file-inputs]))

(deftest ^:example example
  (testing "example input"
    (is (= (part1 (file-inputs/example-file 3 1)) 161))
    (is (= (part2 (file-inputs/example-file 3 2)) 48))))

(deftest ^:real real
  (let [input (file-inputs/real-file 3)]
    (testing "real input"
      (is (= (part1 input) 174960292))
      (is (= (part2 input) 56275602)))))
