(ns hyperspace.world
  (:use (hyperspace geometry
                    misc))
  (:import (hyperspace.geometry point2)))

(defrecord planet [center mass radius])
(defrecord player [center heading])
(defrecord bullet [center velocity])

(defrecord world [planets players bullets])

(def min-x 100)
(def max-x 700)
(def min-y 100)
(def max-y 500)

(def min-planet-size 20)
(def max-planet-size 100)

(defn random-point []
  (point2. (rand-range min-x max-x)
           (rand-range min-y max-y)))

(defn random-planet []
  (let [size (rand-range min-planet-size
                         max-planet-size)]
    (planet. (random-point) size size))) ;;TODO make planet mass something like sqrt(radius)*10^20

(defn generate-planets [n]
  (if (= n 0)
    nil
    (cons (random-planet) (generate-planets (- n 1)))))

;;TODO Place players in different parts of a world.  
(defn generate-world
  []
  (let [planet-quatinty (rand-range 2 5)] ;; 2 to 5 planets should be fine
    (world. (generate-planets planet-quatinty) [(player. (random-point) 0) (player. (random-point) 0)] [])))
