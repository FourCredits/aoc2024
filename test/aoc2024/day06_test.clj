(ns aoc2024.day06-test
  (:require
   [aoc2024.day06 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (file-inputs/example-file 6)]
    (testing "example input"
      (is (= (part1 example-input) 41)))))

(deftest ^:real real
  (let [real-input (file-inputs/real-file 6)]
    (testing "real input"
      (is (= (part1 real-input) 4826)))))

(comment
  (clojure.test/run-tests))
