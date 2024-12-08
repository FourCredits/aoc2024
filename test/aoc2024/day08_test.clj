(ns aoc2024.day08-test
  (:require
   [aoc2024.day08 :refer [part1]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example data"
    (let [input (file-inputs/example-file 8)]
      (is (= (part1 input) 14)))))

(deftest ^:real real
  (testing "real data"
    (let [input (file-inputs/real-file 8)]
      (is (= (part1 input) 240)))))

(comment
  (clojure.test/run-tests))
