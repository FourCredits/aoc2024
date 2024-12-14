(ns aoc2024.day13-test
  (:require
   [aoc2024.day13 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (file-inputs/example-file 13)]
    (testing "example input"
      (is (= (part1 input) 480)))))

(deftest ^:real real
  (let [input (file-inputs/real-file 13)]
    (testing "real input"
      (is (= (part1 input) 29187)))))

(comment
  (clojure.test/run-tests))
