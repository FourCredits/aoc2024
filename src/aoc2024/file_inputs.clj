(ns aoc2024.file-inputs
  (:require
   [clojure.java.io :as io]))

(defn- file-from-resources
  ([input-type day]
   (slurp (io/resource (format "%s/day%02d.txt" input-type day))))
  ([input-type day part]
   (slurp (io/resource (format "%s/day%02d_part%d.txt" input-type day part)))))

(defn example-file
  ([day] (file-from-resources "example" day))
  ([day part] (file-from-resources "example" day part)))

(defn real-file [day] (file-from-resources "real" day))
