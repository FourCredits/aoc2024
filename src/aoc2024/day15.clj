(ns aoc2024.day15
  (:require
   [clojure.string :as str]
   [aoc2024.two-d :as two-d]))

(def ^:private char->direction
  {\< two-d/left
   \> two-d/right
   \^ two-d/up
   \v two-d/down})

(defn parse [input]
  (let [[grid instructions] (str/split input #"\n\n")
        directions (keep char->direction instructions)
        cell-types (->> grid str/split-lines
                        two-d/grid
                        two-d/group-positions-by-value)]
    {:robot (first (cell-types \@))
     :boxes (set (cell-types \O))
     :walls (set (cell-types \#))
     :directions directions}))

(defn- make-space
  "try to move boxes in the specified direction such that pos becomes available.
  returns nil if moving the boxes in this way is impossible"
  [walls pos direction boxes]
  (let [pos' (direction pos)]
    (cond
      (walls pos) nil
      (boxes pos) (when-let [boxes' (make-space walls pos' direction boxes)]
                    (-> boxes' (disj pos) (conj pos')))
      :else boxes)))

(defn- move [{:keys [walls boxes robot] :as state} direction]
  (if-let [boxes' (make-space walls (direction robot) direction boxes)]
    (assoc state :boxes boxes' :robot (direction robot))
    state))

(defn simulate [{:keys [directions] :as state}] (reduce move state directions))

(defn score [{:keys [boxes]}]
  (transduce (map (fn [[r c]] (+ (* 100 r) c))) + boxes))

(defn part1 [input] (->> input parse simulate score))

(defn part2 [input] :todo)

(defn solve [input] ((juxt part1 part2) input))
