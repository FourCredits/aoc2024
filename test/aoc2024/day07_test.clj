(ns aoc2024.day07-test
  (:require
   [aoc2024.day07 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (file-inputs/example-file 7)]
    (testing "example input"
      (is (= (part1 example-input) 3749)))))

(deftest ^:real real
  (let [real-input (file-inputs/real-file 7)]
    (testing "real input"
      (is (= (part1 real-input) 3119088655389)))))

(comment
  (part2 (file-inputs/real-file 7)) ; 3119088655389
  (clojure.test/run-tests))
