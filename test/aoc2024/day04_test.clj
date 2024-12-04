(ns aoc2024.day04-test
  (:require
   [aoc2024.day04 :refer [part1]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (file-inputs/example-file 4)]
    (testing "example input"
      (is (= (part1 example-input) 18)))))

(deftest ^:real real
  (let [real-input (file-inputs/real-file 4)]
    (testing "real input"
      (is (= (part1 real-input) 2557)))))

(comment
  (clojure.test/run-tests))
