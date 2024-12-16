(ns aoc2024.two-d)

(defn up    [[r c]] [(dec r) c])
(defn down  [[r c]] [(inc r) c])
(defn right [[r c]] [r       (inc c)])
(defn left  [[r c]] [r       (dec c)])

(def up-left    (comp up left))
(def up-right   (comp up right))
(def down-left  (comp down left))
(def down-right (comp down right))

(def taxicab-directions
  "The set of steps that, according to the
  [taxicab distance](https://en.wikipedia.org/wiki/Taxicab_geometry), are one
  away from the starting point"
  [up down left right])

(def chebyshev-directions
  "The set of steps that, according to the
  [Chebyshev distance](https://en.wikipedia.org/wiki/Chebyshev_distance), are
  one away from the starting point"
  [up-left up up-right left right down-left down down-right])

(def diagonal-directions [up-left up-right down-left down-right])

(defn grid
  "Given a collection of collections, gives row and column indexes to each
  value, flattening the result. Given no arguments, returns a stateful
  transducer."
  ([]
   (comp (map-indexed (fn [r row] (map-indexed (fn [c v] [[r c] v]) row))) cat))
  ([input]
   (for [[r-index row] (map vector (range) input)
         [c-index value] (map vector (range) row)]
     [[r-index c-index] value])))

(defn inside [[[min-r min-c] [max-r max-c]] [r c]]
  (and (<= min-r r max-r) (<= min-c c max-c)))

(defn on-grid? [grid pos] (inside [[0 0] (first (last grid))] pos))

(defn group-positions-by-value
  "Given a grid (as returned by `grid`), returns a map of value => positions
  that have that value"
  [grid]
  (update-vals (group-by second grid) #(mapv first %)))

(defn distance-between [[r1 c1] [r2 c2]] [(- r2 r1) (- c2 c1)])

(defn scale [n [r c]] [(* r n) (* c n)])

(defn add-vectors [[r1 c1] [r2 c2]] [(+ r1 r2) (+ c1 c2)])

(def anticlockwise
  {up left
   left down
   down right
   right up})

(def clockwise
  {up right
   right down
   down left
   left up})

