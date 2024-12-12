(ns aoc2024.day12
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn- price [region]
  (let [positions (set region)
        area (count positions)
        perimeter (->> positions
                       (mapcat (apply juxt two-d/taxicab-directions))
                       (remove positions)
                       count)]
    (* area perimeter)))

(defn- flood-fill [grid plant from]
  (loop [region #{}
         new-elements #{from}]
    (let [region (into region new-elements)
          next-elements (->> new-elements
                             (mapcat (apply juxt two-d/taxicab-directions))
                             (filter #(and (= plant (grid %))
                                           (not (region %))))
                             set)]
      (if (empty? next-elements) region (recur region next-elements)))))

(defn- separate-by-region [grid]
  (loop [results []
         remaining grid]
    (if-let [[[pos plant] & _] (seq remaining)]
      (let [region (flood-fill grid plant pos)]
        (recur (conj results region)
               (into {} (remove (comp region key) remaining))))
      results)))

(defn part1 [input]
  (->> input
       str/split-lines
       two-d/grid
       (into {})
       separate-by-region
       (map price)
       (apply +)))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) input))
