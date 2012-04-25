(ns hyperspace.game
  (:use (hyperspace gravity
                    geometry
                    world))
  (:import (hyperspace.world Bullet
                             Trace
                             World)))

(def max-traces 1000)

(defn move-bullet
  [bullet planets]
  (let [acceleration (get-acceleration bullet planets)
        {position :center
         velocity :velocity} bullet]
    (assoc bullet
      :center   (point-move position velocity)
      :velocity (vector-sum velocity acceleration))))

(defn destroy-bullet?
  [bullet planets]
  (let [bullet-center (:center bullet)]
    (some (fn [{planet-radius :radius
                planet-center :center}]
            (<= (point-distance bullet-center
                                planet-center)
                planet-radius))
          planets)))

(defn update-trace
  [trace bullet]
  (Trace. (conj (:points trace)
            (:center bullet))))

(defn update-world
  "Simulates few steps for world."
  [init-world time-delta]
  (loop [time time-delta

         {bullets :bullets
          planets :planets
          traces  :traces :as world} init-world]
    (if (<= time 0)
      world
      (recur (- time 1)
             (assoc world
               :bullets (map #(move-bullet % planets)
                             (filter #(not (destroy-bullet? % planets))
                                     bullets))
               :traces (map #(update-trace %1 %2)
                            traces bullets))))))
