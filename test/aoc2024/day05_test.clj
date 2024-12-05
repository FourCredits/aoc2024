(ns aoc2024.day05-test
  (:require
   [aoc2024.day05 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (file-inputs/example-file 5)]
    (testing "example input"
      (is (= (part1 example-input) 143))
      (is (= (part2 example-input) 123)))))

(deftest ^:real real
  (let [real-input (file-inputs/real-file 5)]
    (testing "real input"
      (is (= (part1 real-input) 5275))
      (is (= (part2 real-input) 6191)))))
