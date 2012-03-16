(ns hyperspace.game
  (:use (hyperspace gravity
                    geometry
                    world))
  (:import (hyperspace.world bullet
                             world)))

(defn move-bullet
  [bullet planets]
  (let [acceleration (get-acceleration bullet planets)
        position (:center bullet)
        velocity (:velocity bullet)
        new-position (point-move position velocity)
        new-velocity (vector-sum velocity acceleration)]
    (bullet. new-position new-velocity)))

(defn destroy-bullet?
  [bullet planets]
  (let [bullet-center (:center bullet)]
    (some #(<= (point-distance bullet-center
                               (:center %))
               (:radius %))
          planets)))

(defn update-world
  "Simulates few steps for world."
  [init-world time-delta]
  (loop [time time-delta
         world init-world]
    (if (<= time 0)
      world
      (let [planets (:planets world)
            bullets (map #(move-bullet % planets)
                         (filter #(not (destroy-bullet? % planets))
                                 (:bullets world)))
            players (:players world)
            new-world (world. planets players bullets)
            new-time (- time 1)]
        (recur new-time new-world)))))