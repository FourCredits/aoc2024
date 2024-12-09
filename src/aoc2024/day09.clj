(ns aoc2024.day09
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [clojure.string :as str]))

(defn- char->int [element] (- (int element) (int \0)))

(defn- id [index] (when (even? index) (quot index 2)))

(defn- format-filesystem [filesystem]
  (loop [fs (transient filesystem)
         head 0
         tail (dec (count filesystem))]
    (let [hblock (fs head)
          tblock (fs tail)]
      (cond
        (>= head tail) (persistent! fs)
        (some? hblock) (recur fs (inc head) tail)
        (nil? tblock) (recur fs head (dec tail))
        :else (recur (assoc! fs head tblock tail hblock)
                     (inc head)
                     (dec tail))))))

(defn part1 [input]
  (let [mk-region (fn [index n] (repeat (char->int n) (id index)))
        fs (into [] (comp (map-indexed mk-region) cat) (str/trim input))
        checksum (comp (take-while some?) (map-indexed *))]
    (transduce checksum + (format-filesystem fs))))

(defn- map-accum [f initial-state coll]
  (lazy-seq
   (when-let [[a & as] (seq coll)]
     (let [[next-state b] (f initial-state a)]
       (cons b (map-accum f next-state as))))))

(defn- fits? [file-size {:keys [start size] :as space}]
  (when (>= size file-size)
    [space {:size (- size file-size) :start (+ start file-size)}]))

(defn- fit-file-in [free-spaces {file-size :size :as file}]
  (if-let [[old new] (some #(fits? file-size %) free-spaces)]
    [(-> free-spaces (disj old) (conj new)) (assoc file :start (:start old))]
    [free-spaces file]))

(defn- compare-by [keyfn] (fn [x1 x2] (< (keyfn x1) (keyfn x2))))

(defn part2 [input]
  (let [mk-region (fn [offset [index x]]
                    (let [s (char->int x)]
                      [(+ offset s) {:start offset :size s :id (id index)}]))
        regions (map-accum mk-region 0 (map-indexed vector (str/trim input)))
        {free-spaces false files true} (group-by (comp some? :id) regions)
        free-spaces (into (sorted-set-by (compare-by :start)) free-spaces)]
    (->> files
         reverse
         (map-accum fit-file-in free-spaces)
         (mapcat (fn [{:keys [start size id]}]
                   (map #(* id (+ start %)) (range size))))
         (apply +))))

(defn solve [input] ((juxt part1 part2) input))
