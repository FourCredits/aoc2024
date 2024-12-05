(ns aoc2024.day05
  (:require
   [clojure.string :as str]))

(defn- middle [coll] (nth coll (quot (count coll) 2)))

(defn- ordered-correctly? [rules page-numbers]
  (every? (fn [[f & r]] (not-any? (get rules f #{}) r))
          (reductions (fn [x _] (rest x)) page-numbers page-numbers)))

(defn- sum-of-middles [coll] (transduce (map middle) + coll))

(defn part1 [{:keys [rules updates]}]
  (sum-of-middles (filter #(ordered-correctly? rules %) updates)))

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

(defn part2 [{:keys [rules updates]}]
  (let [ordered-incorrectly? #(not (ordered-correctly? rules %))
        sort-by-rules #(sort-by-rules rules %)]
    (sum-of-middles (map sort-by-rules (filter ordered-incorrectly? updates)))))

(defn parse [input]
  (let [[rules updates] (map str/split-lines (str/split input #"\n\n"))
        mk-graph (fn [[b a]] {a #{b}})
        mk-dependency #(->> (str/split % #"\|") (map parse-long) mk-graph)]
    {:rules (->> rules (map mk-dependency) (apply merge-with into))
     :updates (map #(map parse-long (str/split % #",")) updates)}))

(defn solve [input] ((juxt part1 part2) (parse input)))
