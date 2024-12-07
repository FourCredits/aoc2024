(ns aoc2024.day06-test
  (:require
   [aoc2024.day06 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (parse (file-inputs/example-file 6))]
    (testing "example input"
      (is (= (part1 example-input) 41))
      (is (= (part2 example-input) 6)))))

(deftest ^:real real
  (let [real-input (parse (file-inputs/real-file 6))]
    (testing "real input"
      (is (= (part1 real-input) 4826))
      (is (= (part2 real-input) 1721)))))

(comment
  (time (part2 (file-inputs/real-file 6)))
  (clojure.test/run-tests))
