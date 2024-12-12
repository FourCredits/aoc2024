(ns aoc2024.day12-test
  (:require
   [aoc2024.day12 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (file-inputs/example-file 12)]
    (testing "example input"
      (is (= (part1 input) 1930)))))

(deftest ^:real real
  (let [input (file-inputs/real-file 12)]
    (testing "real input"
      (is (= (part1 input) 1486324)))))

(comment
  (clojure.test/run-tests)
  (part1 (file-inputs/real-file 12))) ; 1486324
