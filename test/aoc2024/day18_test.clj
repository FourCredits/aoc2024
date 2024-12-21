(ns aoc2024.day18-test
  (:require
   [aoc2024.day18 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example input"
    (let [input (file-inputs/example-file 18)]
      (is (= (part1 [6 6] 12 input) 22)))))

(deftest ^:real real
  (testing "real input"
    (let [input (file-inputs/real-file 18)]
      (is (= (part1 input) 336)))))
