(ns aoc2024.day07
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (for [line (str/split-lines input)
        :let [[target rhs] (str/split line #": ")]]
    [(parse-long target) (map parse-long (str/split rhs #" "))]))

(defn- options [operations target numbers]
  (case (count numbers)
    (0 1) numbers
    (for [operation operations
          :let [[a b & c] numbers
                result (operation a b)]
          ;; further processing cannot make the value smaller; so if the running
          ;; value is larger than the target, we don't need to keep going
          :when (<= result target)
          option (options operations target (cons result c))]
      option)))

(defn find-total-calibration-value [operations equations]
  (->> equations
       (pmap (fn [[target rhs]]
               (or (some #{target} (options operations target rhs)) 0)))
       (apply +)))

(defn part1 [equations] (find-total-calibration-value [+ *] equations))

(def ^:private catenate (comp parse-long str))

(defn part2 [equations] (find-total-calibration-value [+ * catenate] equations))

(defn solve [input] ((juxt part1 part2) input))
