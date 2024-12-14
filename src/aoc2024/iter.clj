(ns aoc2024.iter
  "various tools for iteration, sequences, and transducers")

(defn counting
  "A reducing function for counting all the elements in the input."
  ([] 0)
  ([result _] (inc result))
  ([result] result))

