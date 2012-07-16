(ns hyperspace.world
  (:use (hyperspace geometry
                    misc)))

(defrecord Planet [center radius mass])
(defrecord Player [center heading power name])
(defrecord Bullet [center velocity status traces index])

(defrecord World [planets players bullets])

(def make-planet ->Planet)
(def make-player ->Player)

(defn make-bullet
  [center velocity]
  (->Bullet center velocity :alive [] 0))

(defn make-world
  [planets players]
  (->World planets players []))

(def min-x 100)
(def max-x 700)
(def min-y 100)
(def max-y 500)

(def min-planet-radius 20)
(def max-planet-radius 80)

(defn random-point
  []
  (make-point (rand-range min-x max-x)
              (rand-range min-y max-y)))

(defn random-planet
  []
  (let [radius (rand-range min-planet-radius
                           max-planet-radius)
        mass (* (Math/sqrt radius)
                (Math/pow 10 12))]
    (make-planet (random-point) radius mass)))

(defn generate-planets
  [n]
  (repeatedly n random-planet))

(defn generate-player
  [name]
  (make-player (random-point) 0 0 name))

;; TODO Place players in different parts of a world.
(defn generate-world-raw
  []
  (let [planet-quatinty (rand-range 2 5)] ;; 2 to 5 planets should be fine
    (make-world (generate-planets planet-quatinty)
                [(generate-player "player1") (generate-player "player2")])))

(defn effective-planet-radius [p]
  (*
   (:radius p)
   1.2)) ;; todo: should be based on mass

(defn player-on-planet? [player planet]
  (<=
   (point-distance (:center player) (:center planet))
   (effective-planet-radius planet)))

(defn planets-intersect? [p1 p2]
  (let [sr (+ (:radius p1) (:radius p2))
         c1 (:center p1)
         c2 (:center p2)]
    (<= (point-distance c1 c2) sr)))

(defn world-is-bad? [w]
  (or
   (some identity
         (for [plr (:players w)
               plt (:planets w)]
           (player-on-planet? plr plt)))
   (some identity
         (for [p1 (:planets w)
               p2 (:planets w)
               :when (not= p1 p2)]
           (planets-intersect? p1 p2)))))

(defn generate-world []
  (first
   (drop-while
    world-is-bad?
    (repeatedly generate-world-raw))))
