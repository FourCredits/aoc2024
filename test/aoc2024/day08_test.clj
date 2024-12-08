(ns aoc2024.day08-test
  (:require
   [aoc2024.day08 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example data"
    (let [input (parse (file-inputs/example-file 8))]
      (is (= (part1 input) 14))
      (is (= (part2 input) 34)))))

(deftest ^:real real
  (testing "real data"
    (let [input (parse (file-inputs/real-file 8))]
      (is (= (part1 input) 240))
      (is (= (part2 input) 955)))))

(comment
  (clojure.test/run-tests))
