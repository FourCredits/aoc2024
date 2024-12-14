(ns aoc2024.day13-test
  (:require
   [aoc2024.day13 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (parse (file-inputs/example-file 13))]
    (testing "example input"
      (is (= (part1 input) 480))
      (is (= (part2 input) 875318608908)))))

(deftest ^:real real
  (let [input (parse (file-inputs/real-file 13))]
    (testing "real input"
      (is (= (part1 input) 29187))
      (is (= (part2 input) 99968222587852)))))
