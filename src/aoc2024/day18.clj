(ns aoc2024.day18
  (:require
   [aoc2024.two-d :as two-d]))

(defn part1
  ([input] (part1 [70 70] 1024 input))
  ([end fallen input]
   (let [obstacles (->> input
                        (re-seq #"\d+")
                        (map parse-long)
                        (partition-all 2)
                        (map vec)
                        (take fallen)
                        set)
         legal? (fn [p] (and (two-d/inside [[0 0] end] p) (not (obstacles p))))
         best (fn [current new] (if (and current (<= current new)) current new))
         next-options (fn [p steps]
                        (->> two-d/taxicab-directions
                             (keep #(when (legal? (% p)) [(% p) (inc steps)]))))
         visited? (fn [cache p s] (when-let [prev (cache p)] (>= s prev)))]
     (loop [[[p steps] & others] [[[0 0] 0]]
            cache {}
            best-result nil]
       (cond
         (nil? p) best-result
         (= p end) (recur others cache (best best-result steps))
         (visited? cache p steps) (recur others cache best-result)
         :else (recur (concat others (next-options p steps))
                      (assoc cache p steps)
                      best-result))))))

(defn part2 [input]
  :todo)

(defn solve [input] ((juxt part1 part2) input))
