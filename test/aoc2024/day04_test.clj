(ns aoc2024.day04-test
  (:require
   [aoc2024.day04 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (parse (file-inputs/example-file 4))]
    (testing "example input"
      (is (= (part1 example-input) 18))
      (is (= (part2 example-input) 9)))))

(deftest ^:real real
  (let [real-input (parse (file-inputs/real-file 4))]
    (testing "real input"
      (is (= (part1 real-input) 2557))
      (is (= (part2 real-input) 1854)))))

(comment
  (clojure.test/run-tests))
