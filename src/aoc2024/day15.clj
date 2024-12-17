(ns aoc2024.day15
  (:require
   [aoc2024.two-d :as two-d]
   [clojure.string :as str]))

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

(defn- simulate [{:keys [directions] :as state}] (reduce move state directions))

(defn- score [{:keys [boxes]}]
  (transduce (map (fn [[r c]] (+ (* 100 r) c))) + boxes))

(defn part1 [input] (->> input parse simulate score))

(defn- to-big-coords [[r c]] [r (* c 2)])

(defn- duplicate [p] [p (two-d/right p)])

(defn- embiggen [{:keys [walls directions boxes robot]}]
  {:robot (to-big-coords robot)
   :boxes (into #{} (map to-big-coords) boxes)
   :walls (into #{} (mapcat (comp duplicate to-big-coords)) walls)
   :directions directions})

(defn- replace-with
  "takes a set s, removes old, and adds in new"
  [s old new]
  (-> s (disj old) (conj new)))

(defn- make-space2 [boxes walls pos direction]
  (let [pos' (direction pos)]
    (cond
      (walls pos) nil
      (= direction two-d/right) (if (boxes pos)
                                  (some-> boxes
                                          (make-space2 walls (direction pos') direction)
                                          (replace-with pos pos'))
                                  boxes)
      (= direction two-d/left) (if (boxes pos')
                                 (let [pos'' (direction pos')]
                                   (some-> boxes
                                           (make-space2 walls pos'' direction)
                                           (replace-with pos' pos'')))
                                 boxes)
      (boxes pos) (some-> boxes
                          (make-space2 walls pos' direction)
                          (make-space2 walls (two-d/right pos') direction)
                          (replace-with pos pos'))
      (boxes (two-d/left pos)) (some-> boxes
                                       (make-space2 walls pos' direction)
                                       (make-space2 walls (two-d/left pos') direction)
                                       (replace-with (two-d/left pos) (two-d/left pos')))
      :else boxes)))

(defn- move2 [{:keys [walls boxes robot] :as state} direction]
  (if-let [boxes' (make-space2 boxes walls (direction robot) direction)]
    (assoc state :boxes boxes' :robot (direction robot))
    state))

(defn- simulate2 [{:keys [directions] :as state}]
  (reduce move2 state directions))

;; TODO: move parse out of both functions?
(defn part2 [input] (-> input parse embiggen simulate2 score))

(defn solve [input] ((juxt part1 part2) input))
