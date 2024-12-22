(ns aoc2024.day19-test
  (:require
   [aoc2024.day19 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example input"
    (let [input (parse (file-inputs/example-file 19))]
      (is (= (part1 input) 6)))))

(deftest ^:real real
  (testing "real input"
    (let [input (parse (file-inputs/real-file 19))]
      (is (= (part1 input) 363)))))
