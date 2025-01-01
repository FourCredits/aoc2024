(ns aoc2024.day20
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (let [grid (two-d/grid (str/split-lines input))
        max-pos (first (last grid))
        groups (two-d/group-positions-by-value grid)
        start (first (groups \S))
        end (first (groups \E))]
    {:start start
     :end end
     :walls (set (groups \#))
     :track (set (list* start end (groups \.)))
     :on-grid? (fn [pos] (two-d/inside [[0 0] max-pos] pos))}))

(defn- queue [& initial-values]
  (into clojure.lang.PersistentQueue/EMPTY initial-values))

(defn part1 [minimum-savings {:keys [start end on-grid? walls]}]
  (let [legal? (fn [pos] (and (on-grid? pos) (not (walls pos))))
        initial (loop [visited #{}
                       output {}
                       q (queue [start 0])]
                  (when-let [[position distance] (peek q)]
                    (let [output (assoc output position distance)]
                      (if (= position end)
                        output
                        (recur (conj visited position)
                               output
                               (->> two-d/taxicab-directions
                                    (map #(% position))
                                    (filter legal?)
                                    (remove visited)
                                    (map #(vector % (inc distance)))
                                    (into (pop q))))))))
        foo (for [[s d] initial
                  skipped (filter walls (map #(% s) two-d/taxicab-directions))
                  e (filter initial (map #(% skipped) two-d/taxicab-directions))]
              [[s e] (- (initial e) (+ d 2))])]
    (count (filter #(>= (second %) minimum-savings) (into {} (sort-by second foo))))))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt (partial part1 100) part2) (parse input)))
