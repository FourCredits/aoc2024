(ns aoc2024.day17-test
  (:require
   [aoc2024.day17 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (file-inputs/example-file 17)]
    (testing "example input"
      (is (= (part1 input) "4,6,3,5,6,3,5,2,1,0")))))

(deftest ^:real real
  (let [input (file-inputs/real-file 17)]
    (testing "real input"
      (is (= (part1 input) "6,2,7,2,3,1,6,0,5")))))

(comment
  (part1 (file-inputs/real-file 17)) ; "6,2,7,2,3,1,6,0,5"
  (clojure.test/run-tests))
