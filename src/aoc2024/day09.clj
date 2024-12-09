(ns aoc2024.day09
  (:require
   [aoc2024.file-inputs :as file-inputs]
   [clojure.string :as str]))

(defn- char->int [element] (- (int element) (int \0)))

(defn- format-filesystem [filesystem]
  (loop [fs filesystem
         head 0
         tail (dec (count filesystem))]
    (let [hblock (fs head)
          tblock (fs tail)]
      (cond
        (>= head tail) fs
        (some? hblock) (recur fs (inc head) tail)
        (nil? tblock) (recur fs head (dec tail))
        :else (recur (assoc fs head tblock tail hblock)
                     (inc head)
                     (dec tail))))))

(defn- id [index] (when (even? index) (quot index 2)))

(defn- mk-region [index n] (repeat (char->int n) (id index)))

(defn checksum [fs]
  (transduce (comp (take-while some?) (map-indexed *)) + fs))

(defn part1 [input]
  (let [fs (into [] (comp (map-indexed mk-region) cat) (str/trim input))]
    (checksum (format-filesystem fs))))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) input))
