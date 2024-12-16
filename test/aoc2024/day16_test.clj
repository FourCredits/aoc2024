(ns aoc2024.day16-test
  (:require
   [aoc2024.day16 :refer [part1 part2]]
   [aoc2024.file-inputs :as file-inputs]
   [clojure.test :refer [deftest is testing]]))

(deftest ^:example example
  (let [input (file-inputs/example-file 16)]
    (testing "example input"
      (is (= (part1 input) 7036)))))

(comment
  (clojure.test/run-tests))
