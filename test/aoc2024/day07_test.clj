(ns aoc2024.day07-test
  (:require
   [aoc2024.day07 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [example-input (parse (file-inputs/example-file 7))]
    (testing "example input"
      (is (= (part1 example-input) 3749))
      (is (= (part2 example-input) 11387)))))

(deftest ^:real real
  (let [real-input (parse (file-inputs/real-file 7))]
    (testing "real input"
      (is (= (part1 real-input) 3119088655389))
      (is (= (part2 real-input) 264184041398847)))))
