(ns aoc2024.day07
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [clojure.string :as str]))

(defn- options [numbers]
  (if (= 1 (count numbers))
    numbers
    (let [[f & r] (seq numbers)
          r-options (options r)]
      (concat (map #(* f %) r-options) (map #(+ f %) r-options)))))

(comment
  (let [input (file-inputs/example-file 7)]
    (let [parse-line (fn [line]
                       (let [[target rhs] (str/split line #": ")]
                         {:target (parse-long target)
                          :rhs (map parse-long (str/split rhs #" "))}))]
      (->> input
           str/split-lines
           (map parse-line)
           (keep (fn [{:keys [target rhs]}]
                   (->> rhs
                        reverse
                        options
                        (some #{target}))))
           (apply +)))))

(defn part1 [input]
  (let [parse-line (fn [line]
                     (let [[target rhs] (str/split line #": ")]
                       {:target (parse-long target)
                        :rhs (map parse-long (str/split rhs #" "))}))]
    (->> input
         str/split-lines
         (map parse-line)
         (keep (fn [{:keys [target rhs]}]
                 (->> rhs reverse options (some #{target}))))
         (apply +))))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) input))
