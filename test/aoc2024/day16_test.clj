(ns aoc2024.day16-test
  (:require
   [aoc2024.day16 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (parse (file-inputs/example-file 16))]
    (testing "example input"
      (is (= (part1 input) 7036))
      (is (= (part2 input) 45)))))

(deftest ^:real real
  (let [input (parse (file-inputs/real-file 16))]
    (testing "real input"
      (is (= (part1 input) 88468))
      (is (= (part2 input) 616)))))
