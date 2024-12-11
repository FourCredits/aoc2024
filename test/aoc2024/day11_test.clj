(ns aoc2024.day11-test
  (:require
   [aoc2024.day11 :refer [part1]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (file-inputs/example-file 11)]
    (testing "example input"
      (is (= (part1 input) 55312)))))

(deftest ^:real real
  (let [input (file-inputs/real-file 11)]
    (testing "real input"
      (is (= (part1 input) 200446)))))

(comment
  (clojure.test/run-tests))
