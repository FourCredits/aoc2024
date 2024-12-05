(ns aoc2024.day05
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [clojure.string :as str]))

(defn- middle [coll] (nth coll (/ (count coll) 2)))

(defn- ordered-correctly? [rules page-numbers]
  (if-let [[f & r] (seq page-numbers)]
    (let [dependents (or (rules f) #{})]
      (if (some dependents r) false (recur rules r)))
    true))

(comment
  (let [input (file-inputs/example-file 5)]
    (let [[rules-part updates-part] (str/split input #"\n\n")
          rules (->> rules-part
                     str/split-lines
                     (map #(map parse-long (str/split % #"\|")))
                     (group-by second)
                     (#(update-vals % (comp set (partial map first)))))
          parse-updates (fn [line] (map parse-long (str/split line #",")))
          updates (->> updates-part str/split-lines (map parse-updates))]
      (->> updates
           (filter #(ordered-correctly? rules %))
           (map middle)
           (apply +))))
  1)

(defn part1 [input]
  (let [[rules-part updates-part] (str/split input #"\n\n")
        rules (->> rules-part
                   str/split-lines
                   (map #(map parse-long (str/split % #"\|")))
                   (group-by second)
                   (#(update-vals % (comp set (partial map first)))))
        parse-updates (fn [line] (map parse-long (str/split line #",")))
        updates (->> updates-part str/split-lines (map parse-updates))
        ordered-correctly? (partial ordered-correctly? rules)]
    (->> updates (filter ordered-correctly?) (map middle) (apply +))))

(defn part2 [input] :todo)

(defn solve [input] ((juxt part1 part2) input))
