(ns hyperspace.world
  (:use (hyperspace geometry))
  (:import (hyperspace.geometry point2)))

(defrecord planet [center mass radius])
(defrecord player [center heading])
(defrecord bullet [center velocity])

(defrecord world [planets players bullets])

(defn generate-world
  []
  ; TODO: Randomize world generation.
  (let [planet (planet. (point2. 0 0) 1 10)
        player (player. (point2. 1 1) 0)]
    (world. [planet] [player] [])))
