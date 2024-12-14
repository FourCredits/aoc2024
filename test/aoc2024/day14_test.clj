(ns aoc2024.day14-test
  (:require
   [aoc2024.day14 :refer [parse part1 part2-visual]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (parse (file-inputs/example-file 14))]
    (testing "example input"
      (is (= (part1 7 11 input) 12)))))

(deftest ^:real real
  (let [input (parse (file-inputs/real-file 14))]
    (testing "real input"
      (is (= (part1 103 101 input) 233709840)))))

(comment
  (clojure.test/run-tests))
