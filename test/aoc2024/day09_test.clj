(ns aoc2024.day09-test
  (:require
   [aoc2024.day09 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (file-inputs/example-file 9)]
    (testing "example input"
      (is (= (part1 input) 1928))
      (is (= (part2 input) 2858)))))

(deftest ^:real real
  (let [input (file-inputs/real-file 9)]
    (testing "real input"
      (is (= (part1 input) 6367087064415))
      (is (= (part2 input) 6390781891880)))))
