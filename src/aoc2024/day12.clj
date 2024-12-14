(ns aoc2024.day12
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [aoc2024.two-d :as two-d :refer [down down-left left right up up-left
                                    up-right]]
   [clojure.string :as str]))

;; TODO: possible optimisation: find area and perimeter at the same time as
;; doing the flood-fill
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
               (apply dissoc remaining region)))
      results)))

(defn- price [region]
  (* (count region)
     (->> region
          (mapcat (apply juxt two-d/taxicab-directions))
          (remove region)
          count)))

(defn part1 [input]
  (->> input
       str/split-lines
       two-d/grid
       (into {})
       separate-by-region
       (map price)
       (apply +)))

(defn- count-sides [region]
  (let [at (fn [p direction] (not (region (direction p))))
        num-edges (fn [point]
                    (let [n  (at point up)
                          w  (at point left)
                          e  (at point right)
                          s  (at point down)
                          nw (at point up-left)
                          ne (at point up-right)
                          sw (at point down-left)
                          u? (and n (or w (not nw)))
                          d? (and s (or w (not sw)))
                          l? (and w (or n (not nw)))
                          r? (and e (or n (not ne)))]
                      (filter identity [u? d? l? r?])))]
    (count (mapcat num-edges region))))

(defn part2 [input]
  (let [grid (into {} (two-d/grid) (str/split-lines input))
        price (fn [region] (* (count region) (count-sides region)))]
    (transduce (map price) + (separate-by-region grid))))

(defn solve [input] ((juxt part1 part2) input))
