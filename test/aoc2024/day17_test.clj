(ns aoc2024.day17-test
  (:require
   [aoc2024.day17 :refer [parse part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (testing "example input"
    (is (= (part1 (parse (file-inputs/example-file 17 1)))
           "4,6,3,5,6,3,5,2,1,0"))
    (is (= (part2 (parse (file-inputs/example-file 17 2))) 117440))))

(deftest ^:real real
  (let [input (parse (file-inputs/real-file 17))]
    (testing "real input"
      (is (= (part1 input) "6,2,7,2,3,1,6,0,5"))
      #_(is (= (part2 input) :placeholder)))))
