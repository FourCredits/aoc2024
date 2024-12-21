(ns aoc2024.day17
  (:require
   [clojure.math :as math]
   [clojure.string :as str]
   [aoc2024.file-inputs :as file-inputs]))

(defn parse [input]
  (let [[a b c & program] (map parse-long (re-seq #"\d+" input))
        program (vec program)
        program-end (count program)
        end? #(>= (inc %) program-end)
        fetch (fn [{:keys [pc]}]
                (when (not (end? pc)) (subvec program pc (+ pc 2))))]
    {:a a :b b :c c :pc 0 :output [] :program program :fetch fetch}))

(defn- combo-operand [{:keys [a b c]} operand]
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

(defn- step [{:keys [fetch] :as computer}]
  (if-let [instruction (fetch computer)]
    (execute-instruction computer instruction)
    (assoc computer :halted? true)))

(defn- run-and-get-output [computer]
  (->> computer
       (iterate step)
       (filter :halted?)
       first
       :output))

(defn part1 [computer] (str/join \, (run-and-get-output computer)))

(comment
  (time
   (let [{:keys [program b c] :as computer} (parse (file-inputs/real-file 17))]
     (let [constraint (fn [a]
                        (mod (bit-xor (mod a 8)
                                      3
                                      5
                                      (quot a
                                            (bit-shift-left 1 (bit-xor (mod a 8) 3))))
                             8))]
       (loop [[target & target-output] (reverse program)
              as [0]]
         (if target
           (recur target-output
                  (sequence (comp (mapcat #(range (* 8 %) (* 8 (inc %))))
                                  (filter #(= (constraint %) target)))
                            as))
           (reduce min as)))))))

(defn part2-example
  "Not fast enough for the real thing - but hypothetically correct"
  [computer]
  (->> (range)
       (filter #(= (run-and-get-output (assoc computer :a %)) (:program computer)))
       first))

(defn part2-real [{:keys [program]}]
  (let [constraint (fn [a]
                     (mod (bit-xor (mod a 8)
                                   3
                                   5
                                   (quot a
                                         (bit-shift-left 1 (bit-xor (mod a 8) 3))))
                          8))]
    (loop [[target & target-output] (reverse program)
           as [0]]
      (if target
        (recur target-output
               (sequence (comp (mapcat #(range (* 8 %) (* 8 (inc %))))
                               (filter #(= (constraint %) target)))
                         as))
        (reduce min as)))))

(defn solve [input] ((juxt part1 part2-real) (parse input)))
