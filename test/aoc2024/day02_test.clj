(ns aoc2024.day02-test
  (:require
   [aoc2024.day02 :refer [parse part1 part2]]
   [clojure.test :refer [deftest is testing]]
   [aoc2024.file-inputs :as file-inputs]))

(deftest ^:example example
  (let [parsed (parse (file-inputs/example-file 2))]
    (testing "example input"
      (is (= (part1 parsed) 2))
      (is (= (part2 parsed) 4)))))

(deftest ^:real real
  (testing "real input"
    (let [parsed (parse (file-inputs/real-file 2))]
      (is (= (part1 parsed) 334))
      (is (= (part2 parsed) 400)))))
