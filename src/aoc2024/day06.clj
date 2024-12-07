(ns aoc2024.day06
  (:require [aoc2024.two-d :as two-d]
            [clojure.string :as str]
            [aoc2024.file-inputs :as file-inputs]))

(defn parse [input]
  (let [grid (two-d/grid-seq (str/split-lines input))
        by-type (update-vals (group-by second grid) #(map first %))
        [[max-r max-c] _] (last grid)]
    {:in-bounds? (fn [[r c]] (and (<= 0 r max-r) (<= 0 c max-c)))
     :obstacles (set (by-type \#))
     :start (first (by-type \^))}))

;; TODO: move to two-d
(defn- turn-right [direction]
  (condp = direction
    two-d/up    two-d/right
    two-d/right two-d/down
    two-d/down  two-d/left
    two-d/left  two-d/up))

(defn- next-step [blocked? [pos dir]]
  (let [pos' (dir pos)] (if (blocked? pos') [pos (turn-right dir)] [pos' dir])))

(defn- simulate-guard [obstacles start in-bounds?]
  (letfn [(step [visited [pos _ :as guard]]
            (cond
              (visited guard) (reduced :loop)
              (not (in-bounds? pos)) (reduced (set (map first visited)))
              :else (conj visited guard)))]
    (reduce step #{} (iterate #(next-step obstacles %) [start two-d/up]))))

(defn part1 [{:keys [in-bounds? obstacles start]}]
  (count (simulate-guard obstacles start in-bounds?)))

(defn- simulate-guard2
  ([obstacles start in-bounds?]
   (simulate-guard2 obstacles start in-bounds? {}))
  ([obstacles start in-bounds? visited]
   (letfn [(add-info [visited coll]
             (lazy-seq
              (when-let [[[prev [pos _ :as guard]] & remainder] (seq coll)]
                (cond
                  (visited guard) [{:prev prev :guard guard :visited visited :loop true}]
                  (not (in-bounds? pos)) nil
                  :else (cons {:prev prev :guard guard :visited visited}
                              (add-info (conj visited guard) remainder))))))]
     (->> start
          (iterate #(next-step obstacles %))
          (partition 2 1)
          (add-info visited)))))

(defn part2 [{:keys [in-bounds? obstacles start]}]
  (->> (simulate-guard2 obstacles [start two-d/up] in-bounds?)
       #_rest ;; can't put a rock where the guard is at the start
       (map (fn [{[pos _] :guard :keys [prev visited]}]
              (println pos prev)
              (simulate-guard2 (conj obstacles pos)
                               prev
                               in-bounds?
                               visited)))
       (filter #(:loop (last %)))
       count))

(comment
  (time (part2 (parse (file-inputs/example-file 6))))
  1)

(defn solve [input] ((juxt part1 part2) (parse input)))
