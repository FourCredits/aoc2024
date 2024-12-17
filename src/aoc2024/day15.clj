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
        cell-types (->> grid str/split-lines
                        two-d/grid
                        two-d/group-positions-by-value)]
    {:robot (first (cell-types \@))
     :boxes (set (cell-types \O))
     :walls (set (cell-types \#))
     :directions (keep char->direction instructions)}))

(defn- score [{:keys [boxes]}]
  (transduce (map (fn [[r c]] (+ (* 100 r) c))) + boxes))

(defn- embiggen [{:keys [walls directions boxes robot]}]
  (letfn [(to-big-coords [[r c]] [r (* c 2)])
          (duplicate [p] [p (two-d/right p)])]
    {:robot (to-big-coords robot)
     :boxes (into #{} (map to-big-coords) boxes)
     :walls (into #{} (mapcat (comp duplicate to-big-coords)) walls)
     :directions directions}))

(defn- swap [s old new] (-> s (disj old) (conj new)))

(defn- move-boxes1 [walls]
  (fn [direction]
    (fn inner [boxes p]
      (let [p' (direction p)]
        (cond
          (walls p) nil
          (boxes p) (some-> (inner boxes p') (swap p p'))
          :else boxes)))))

(defn- move-boxes2 [walls]
  (fn [direction]
    (condp = direction
      two-d/right (fn inner [boxes p]
                    (let [p' (direction p)
                          p'' (direction p')]
                      (cond
                        (walls p) nil
                        (boxes p) (some-> boxes (inner p'') (swap p p'))
                        :else boxes)))
      two-d/left (fn inner [boxes p]
                   (let [p' (direction p)
                         p'' (direction p')]
                     (cond
                       (walls p) nil
                       (boxes p') (some-> boxes (inner p'') (swap p' p''))
                       :else boxes)))
      (fn inner [boxes p]
        (let [p' (direction p)]
          (cond
            (walls p) nil
            (boxes p) (some-> boxes
                              (inner p')
                              (inner (two-d/right p'))
                              (swap p p'))
            (boxes (two-d/left p)) (some-> boxes
                                           (inner p')
                                           (inner (two-d/left p'))
                                           (swap (two-d/left p) (two-d/left p')))
            :else boxes))))))

(defn- simulate-2 [make-space {:keys [directions walls] :as state}]
  (let [f (make-space walls)
        move (fn [{:keys [boxes robot] :as state} direction]
               (if-let [boxes' ((f direction) boxes (direction robot))]
                 (assoc state :boxes boxes' :robot (direction robot))
                 state))]
    (reduce move state directions)))

(defn part1 [input] (->> input          (simulate-2 move-boxes1) score))
(defn part2 [input] (->> input embiggen (simulate-2 move-boxes2) score))

(defn solve [input] ((juxt part1 part2) (parse input)))
