(ns aoc2024.day03
  (:require
   [clojure.string :as str]))

(defn part1 [input]
  (->> input
       (re-seq #"mul\((\d{1,3}),(\d{1,3})\)")
       (map (fn [[_ n1 n2]] (* (parse-long n1) (parse-long n2))))
       (apply +)))

(defn part2 [input]
  (letfn [(handle-mul [{:keys [enabled?] :as acc} n1 n2]
            (let [product (* (parse-long n1) (parse-long n2))]
              (update acc :n #(cond-> % enabled? (+ product)))))
          (update-state [acc [op n1 n2]]
            (condp #(str/starts-with? %2 %1) op
              "mul" (handle-mul acc n1 n2)
              "don't" (assoc acc :enabled? false)
              "do" (assoc acc :enabled? true)))]
    (->> input
         (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|don't\(\)|do\(\)")
         (reduce update-state {:enabled? true :n 0})
         :n)))

(defn solve [input] ((juxt part1 part2) input))
