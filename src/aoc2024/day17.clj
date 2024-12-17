(ns aoc2024.day17
  (:require
   [clojure.math :as math]
   [clojure.string :as str]))

(defn combo-operand [{:keys [a b c]} operand]
  (case operand
    (0 1 2 3) operand
    4 a
    5 b
    6 c
    :error))

(defn- execute-instruction [{:keys [a b c pc output] :as registers} [opcode operand]]
  (let [pc' (+ pc 2)]
    (case opcode
      0 (assoc registers :pc pc' :a (int (/ a (math/pow 2 (combo-operand registers operand)))))
      1 (assoc registers :pc pc' :b (bit-xor b operand))
      2 (assoc registers :pc pc' :b (mod (combo-operand registers operand) 8))
      3 (assoc registers :pc (if (zero? a) pc' operand))
      4 (assoc registers :pc pc' :b (bit-xor b c))
      5 (assoc registers :pc pc' :output (conj output (mod (combo-operand registers operand) 8)))
      6 (assoc registers :pc pc' :b (int (/ a (math/pow 2 (combo-operand registers operand)))))
      7 (assoc registers :pc pc' :c (int (/ a (math/pow 2 (combo-operand registers operand))))))))

(defn part1 [input]
  (let [[a b c & program] (map parse-long (re-seq #"\d+" input))
        program (vec program)
        program-end (count program)
        end? #(>= % program-end)]
    (loop [{:keys [output pc] :as computer} {:a a :b b :c c :pc 0 :output []}]
      (if-let [ins (when (not (end? pc)) (subvec program pc (+ pc 2)))]
        (recur (execute-instruction computer ins))
        (str/join \, output)))))

(defn part2 [input] :todo)

(defn solve [input] ((juxt part1 part2) input))
