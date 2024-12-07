(ns aoc2024.day07
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [clojure.string :as str]))

(defn parse [input]
  (->> input
       str/split-lines
       (map #(let [[target rhs] (str/split % #": ")]
               {:target (parse-long target)
                :rhs (map parse-long (str/split rhs #" "))}))))

(defn- options [numbers]
  (if (= 1 (count numbers))
    numbers
    (let [[f & r] (seq numbers)
          r-options (options r)]
      (concat (map #(* f %) r-options) (map #(+ f %) r-options)))))

(defn- options2 [numbers]
  (condp #(= %1 (count %2)) numbers
    0 []
    1 numbers
    (for [operation [+ * (fn [x y] (parse-long (str x y)))]
          :let [[a b & c] numbers]
          option (options2 (cons (operation a b) c))]
      option)))

(defn part1 [equations]
  (->> equations
       (keep (fn [{:keys [target rhs]}] (->> rhs reverse options (some #{target}))))
       (apply +)))

(defn part2 [equations]
  (->> equations
       (keep (fn [{:keys [target rhs]}] (some #{target} (options2 rhs))))
       (apply +)))

(defn solve [input] ((juxt part1 part2) input))
