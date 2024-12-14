(ns aoc2024.day12-test
  (:require
   [aoc2024.day12 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (parse (file-inputs/example-file 12))]
    (testing "example input"
      (is (= (part1 input) 1930))
      (is (= (part2 input) 1206)))))

(deftest ^:real real
  (let [input (parse (file-inputs/real-file 12))]
    (testing "real input"
      (is (= (part1 input) 1486324))
      (is (= (part2 input) 898684)))))

(comment
  (clojure.test/run-tests))
