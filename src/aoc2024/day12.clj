(ns aoc2024.day12
  (:require
   [aoc2024.iter :refer [counting]]
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

(defn parse [input]
  (into {} (two-d/grid) (str/split-lines input)))

(def move-out (mapcat (apply juxt two-d/taxicab-directions)))

(defn- flood-fill [grid plant from]
  (loop [region #{}
         new #{from}]
    (let [region (into region new)
          should-add? #(and (= plant (grid %)) (not (region %)))
          next-elements (into #{} (comp move-out (filter should-add?)) new)]
      (if (empty? next-elements) region (recur region next-elements)))))

(defn- separate-by-region [grid]
  (loop [results []
         remaining grid]
    (if-let [[[pos plant] & _] (seq remaining)]
      (let [region (flood-fill grid plant pos)]
        (recur (conj results region) (apply dissoc remaining region)))
      results)))

(defn- perimeter [region]
  (transduce (comp move-out (remove region)) counting region))

(def ^:private up-edge    [two-d/up    two-d/left two-d/up-left])
(def ^:private down-edge  [two-d/down  two-d/left two-d/down-left])
(def ^:private left-edge  [two-d/left  two-d/up   two-d/up-left])
(def ^:private right-edge [two-d/right two-d/up   two-d/up-right])
(def ^:private edges [up-edge down-edge left-edge right-edge])

(defn- edge? [[a b c]] (and (not a) (or (not b) c)))

(defn- count-sides [region]
  (let [at (fn [p direction] (region (direction p)))
        num-edges #(filter (comp edge? (partial map (partial at %))) edges)]
    (transduce (mapcat num-edges) counting region)))

(defn- fencing-price [grid price-for-region]
  (transduce (map price-for-region) + (separate-by-region grid)))

(defn part1 [grid] (fencing-price grid #(* (count %) (perimeter %))))
(defn part2 [grid] (fencing-price grid #(* (count %) (count-sides %))))

(defn solve [input] ((juxt part1 part2) (parse input)))
