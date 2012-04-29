(ns hyperspace.world
  (:use (hyperspace geometry
                    misc)))

(defrecord Planet [center mass radius])
(defrecord Player [center heading power])
(defrecord Bullet [center velocity])
(defrecord Trace [points])

(defrecord World [planets players bullets traces])

(defn make-trace
  [bullet]
  (Trace. (list (:center bullet))))

(defn make-player
  [point]
  (Player. point 0 0))

(def min-x 100)
(def max-x 700)
(def min-y 100)
(def max-y 500)

(def min-planet-radius 20)
(def max-planet-radius 100)

(defn random-point []
  (make-point (rand-range min-x max-x)
              (rand-range min-y max-y)))

(defn random-planet []
  (let [radius (rand-range min-planet-radius
                           max-planet-radius)
        mass (* (Math/sqrt radius)
                (Math/pow 10 12))]
    (Planet. (random-point) mass radius)))

(defn generate-planets [n]
  (repeatedly n random-planet))

;;TODO Place players in different parts of a world.  
(defn generate-world
  []
  (let [planet-quatinty (rand-range 2 5)] ;; 2 to 5 planets should be fine
    (World. (generate-planets planet-quatinty)
            [(make-player (random-point)) (make-player (random-point))]
            [] [])))

