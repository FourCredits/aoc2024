(ns aoc2024.day09
  (:require
   [aoc2024.parsing :refer [char->int]]
   [clojure.string :as str]))

(defn- id [index] (when (even? index) (quot index 2)))

(defn- format-filesystem [filesystem]
  (loop [fs (transient filesystem), h 0, t (dec (count filesystem))]
    (cond
      (>= h t) (persistent! fs)
      (some? (fs h)) (recur fs (inc h) t)
      (nil? (fs t)) (recur fs h (dec t))
      :else (recur (assoc! fs h (fs t) t (fs h)) (inc h) (dec t)))))

(defn part1 [input]
  (let [mk-region (fn [index n] (repeat (char->int n) (id index)))
        fs (into [] (comp (map-indexed mk-region) cat) (str/trim input))
        checksum (comp (take-while some?) (map-indexed *))]
    (transduce checksum + (format-filesystem fs))))

(defn- map-accum
  ([f initial-state]
   (fn [rf]
     (let [state (volatile! initial-state)]
       (fn
         ([] (rf))
         ([result input]
          (let [[new-state input] (f @state input)]
            (vreset! state new-state)
            (rf result input)))
         ([result] (rf result))))))
  ([f initial-state coll]
   (lazy-seq
    (when-let [[a & as] (seq coll)]
      (let [[next-state b] (f initial-state a)]
        (cons b (map-accum f next-state as)))))))

(defn- fits? [file {:keys [start size] :as space}]
  (when (and (>= size (:size file)) (< start (:start file)))
    [space {:size (- size (:size file)) :start (+ start (:size file))}]))

(defn- checksum-file [{:keys [start size id]}]
  (* id (/ (* size (+ (* start 2) size -1)) 2)))

(defn- fit-file-in [free-spaces file]
  (if-let [[old new] (some #(fits? file %) free-spaces)]
    [(-> free-spaces (disj old) (conj new))
     (checksum-file (assoc file :start (:start old)))]
    [free-spaces (checksum-file file)]))

(defn- mk-region [offset [index x]]
  (let [s (char->int x)]
    [(+ offset s) {:start offset :size s :id (id index)}]))

(defn- compare-by [keyfn] (fn [x1 x2] (< (keyfn x1) (keyfn x2))))

(defn part2 [input]
  (let [regions (map-accum mk-region 0 (map-indexed vector (str/trim input)))
        {free-spaces false files true} (group-by (comp some? :id) regions)
        free-spaces (apply sorted-set-by (compare-by :start) free-spaces)]
    (transduce (map-accum fit-file-in free-spaces) + (reverse files))))

(defn solve [input] ((juxt part1 part2) input))
