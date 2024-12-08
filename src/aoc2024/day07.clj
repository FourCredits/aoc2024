(ns aoc2024.day07
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (for [line (str/split-lines input)
        :let [[target numbers] (str/split line #": ")]]
    [(parse-long target) (map parse-long (str/split numbers #" "))]))

(defn- evaluate [operations target [a b & c :as numbers]]
  (case (count numbers)
    0 false
    1 (= a target)
    (some (fn [operation]
            (let [r (operation a b)]
              (and (<= r target) (evaluate operations target (cons r c)))))
          operations)))

(defn- find-total-calibration-value [equations operations]
  (apply + (pmap (fn [[target numbers]]
                   (if (evaluate operations target numbers) target 0))
                 equations)))

(def ^:private catenate (comp parse-long str))

(defn part1 [equations] (find-total-calibration-value equations [+ *]))
(defn part2 [equations] (find-total-calibration-value equations [+ * catenate]))

(defn solve [input] ((juxt part1 part2) input))
