(ns aoc2024.day14)

(def ^:private ticks 100)

(defn which-quadrant [height width [x y]]
  (let [half-height (int (/ height 2))
        half-width (int (/ width 2))
        x-comp (compare x half-width)
        y-comp (compare y half-height)]
    (if (or (zero? x-comp) (zero? y-comp)) nil [x-comp y-comp])))

(defn- simulate [height width [px py vx vy]]
  [(mod (+ px (* ticks vx)) width)
   (mod (+ py (* ticks vy)) height)])

(defn part1 [height width input]
  (->> input
       (re-seq #"-?\d+")
       (map parse-long)
       (partition 4)
       (map (comp #(which-quadrant height width %) #(simulate height width %)))
       frequencies
       (#(dissoc % nil))
       (map val)
       (apply *)))

(defn part2 [input] :todo)

(defn solve [input] ((juxt part1 part2) input))
