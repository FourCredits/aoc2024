(ns aoc2024.day20-test
  (:require
   [aoc2024.day20 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example input"
    (let [input (parse (file-inputs/example-file 20))]
      (is (= (part1 20 input) 5)))))

(deftest ^:real real
  (testing "real input"
    (let [input (parse (file-inputs/real-file 20))]
      (is (= (part1 100 input) 1406)))))
