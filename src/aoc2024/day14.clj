(ns aoc2024.day14
  (:require
   [aoc2024.file-inputs :as file-inputs]))

(defn parse [input]
  (->> input (re-seq #"-?\d+") (map parse-long) (partition 4)))

(defn- which-quadrant [height width [x y]]
  (let [half-height (int (/ height 2))
        half-width (int (/ width 2))
        x-comp (compare x half-width)
        y-comp (compare y half-height)]
    (if (or (zero? x-comp) (zero? y-comp)) nil [x-comp y-comp])))

(defn- simulate [height width [px py vx vy]]
  [(mod (+ px (* 100 vx)) width)
   (mod (+ py (* 100 vy)) height)])

(defn part1 [height width robots]
  (->> robots
       (map (comp #(which-quadrant height width %) #(simulate height width %)))
       frequencies
       (#(dissoc % nil))
       (map val)
       (apply *)))

(defn part2 [_input] "this one needs to be run visually")

(defn clear-screen []
  (let [esc (char 27)]
    (print (str esc "[2J"))
    (print (str esc "[;H"))))

(defn- print-robots [height width robots]
  (let [positions (set (map (fn [[px py _ _]] [px py]) robots))]
    (doseq [y (range height)]
      (println (apply str (map #(if (positions [% y]) \# \.) (range width)))))))

(defn part2-visual
  "should be run with `clj -X aoc2024.day14/part2-visual`"
  [& _args]
  (let [[height width] [103 101]
        step-one #(map (fn [[px py vx vy]]
                         [(mod (+ px vx) width) (mod (+ py vy) height) vx vy])
                       %)
        steps (iterate step-one (parse (file-inputs/real-file 14)))
        start 28
        jump 103
        end 6620]
    (loop [tick start]
      (when (or (nil? end) (<= tick end))
        (clear-screen)
        (println tick)
        (print-robots height width (nth steps tick))
        (recur (+ tick jump))))))

(defn solve [input] ((juxt part1 part2) (parse input)))
