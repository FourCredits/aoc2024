(ns aoc2024.day05
  (:require
   [clojure.string :as str]))

(defn- middle [coll] (nth coll (quot (count coll) 2)))

(defn- ordered-correctly? [rules page-numbers]
  (if-let [[f & r] (seq page-numbers)]
    (and (not (some (get rules f #{}) r)) (recur rules r))
    true))

(defn- sort-by-rules
  "topological sort of `page-numbers` according to `rules`."
  [rules page-numbers]
  (loop [result []
         completed #{}
         remaining page-numbers]
    (let [has-dependents? (fn [page]
                            (some #(and (not (completed %))
                                        (some #{%} (remove #{page} remaining)))
                                  (rules page)))
          {deps true no-deps false} (group-by (comp boolean has-dependents?) remaining)
          result' (into result no-deps)]
      (if deps
        (recur result' (into completed no-deps) deps)
        result'))))

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

(defn part2 [input]
  (let [[rules-part updates-part] (str/split input #"\n\n")
        rules (->> rules-part
                   str/split-lines
                   (map #(map parse-long (str/split % #"\|")))
                   (group-by second)
                   (#(update-vals % (comp set (partial map first)))))
        parse-updates (fn [line] (map parse-long (str/split line #",")))
        updates (->> updates-part str/split-lines (map parse-updates))]
    (->> updates
         (filter #(not (ordered-correctly? rules %)))
         (map #(middle (sort-by-rules rules %)))
         (apply +))))

(defn solve [input] ((juxt part1 part2) input))
