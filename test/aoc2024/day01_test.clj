(ns aoc2024.day01-test
  (:require
   [aoc2024.day01 :refer [parse part1 part2]]
   [clojure.test :refer [deftest is testing]]
   [aoc2024.file-inputs :as file-inputs]))

(deftest ^:example example
  (let [parsed (parse (file-inputs/example-file 1))]
    (testing "example input"
      (is (= (part1 parsed) 11))
      (is (= (part2 parsed) 31)))))

(deftest ^:real real
  (testing "real input"
    (let [parsed (parse (file-inputs/real-file 1))]
      (is (= (part1 parsed) 2192892))
      (is (= (part2 parsed) 22962826)))))
