(ns hyperspace.game
  (:use (hyperspace gravity
                    geometry
                    world))
  (:import (hyperspace.world bullet
                             trace
                             world)))

(def max-traces 1000)

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
            bullets (:bullets world)
            traces (:traces world)
            new-planets planets
            new-players (:players world)
            new-bullets (map #(move-bullet % planets)
                         (filter #(not (destroy-bullet? % planets))
                                 bullets))
            new-traces (concat (map #(trace. (:center %)) bullets)
                               (take max-traces traces))
            new-world (world. new-planets new-players new-bullets new-traces)
            new-time (- time 1)]
        (recur new-time new-world)))))
