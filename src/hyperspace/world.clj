(ns hyperspace.world
  (:use (hyperspace geometry
                    misc))
  (:import (hyperspace.geometry Point2)))

(defrecord Planet [center mass radius])
(defrecord Player [center heading])
(defrecord Bullet [center velocity])
(defrecord Trace [points])

(defrecord World [planets players bullets traces])

(def min-x 100)
(def max-x 700)
(def min-y 100)
(def max-y 500)

(def min-planet-size 20)
(def max-planet-size 100)

(defn random-point []
  (Point2. (rand-range min-x max-x)
           (rand-range min-y max-y)))

(defn random-planet []
  (let [size (rand-range min-planet-size
                         max-planet-size)]
    (Planet. (random-point) size size))) ;;TODO make planet mass something like sqrt(radius)*10^20

(defn generate-planets [n]
  (if (= n 0)
    nil
    (cons (random-planet) (generate-planets (- n 1)))))

;;TODO Place players in different parts of a world.  
(defn generate-world
  []
  (let [planet-quatinty (rand-range 2 5)] ;; 2 to 5 planets should be fine
    (World. (generate-planets planet-quatinty)
            [(Player. (random-point) 0) (Player. (random-point) 0)]
            [] [])))

(defn make-trace
  [bullet]
  (Trace. (list (:center bullet))))
