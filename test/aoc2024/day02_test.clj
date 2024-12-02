(ns aoc2024.day02-test
  (:require
   [aoc2024.day02 :refer [parse part1 part2]]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"
        parsed (parse input)]
    (testing "example input"
      (is (= (part1 parsed) 2))
      (is (= (part2 parsed) 4)))))

(deftest ^:real real
  (testing "real input"
    (let [parsed (parse (slurp (io/resource "day02.txt")))]
      (is (= (part1 parsed) 334))
      (is (= (part2 parsed) 400)))))
