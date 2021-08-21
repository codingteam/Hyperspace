(ns hyperspace.library.simulation
  (:use clojure.tools.logging
        [hyperspace.library geometry gravity])
  (:require [hyperspace.library.world :as world]))

(def simulation-step 10)
(def max-cycle 1000000)

(defn kill-player
  "Kills the specified player."
  [{players :players
    :as world}
   player]
  (debug "Killing player" player)
  (assoc world
    :players (replace {player (assoc player
                                :status :dead)} players)))

(defn update-particle
  "Updates particle position."
  [{position :position
    velocity :velocity
    :as particle}
   planets
   delta-time]
  (trace "update-particle" particle planets delta-time)
  (let [acceleration (apply vector-sum
                            (conj (map #(gravity-acceleration particle %)
                                       planets)
                                  [0 0]))
        new-velocity (-> acceleration
                         (multiply-by-scalar (* delta-time 1e-3))
                         (vector-sum velocity))
        new-position (-> new-velocity
                         (multiply-by-scalar (* delta-time 1e-3))
                         (vector-sum position))]
    (assoc particle
      :position new-position
      :velocity new-velocity)))

(defn fire
  "Produces the world state resulted from player fire."
  [{planets :planets
    :as world}
   player
   heading
   power]
  (let [players (->> world
                     :players
                     ; Sometimes game will pass players with wrong states here (dead vs alive), so
                     ; just compare positions here.
                     (remove #(= (:position %) (:position player))))]
    (loop [bullet {:position (:position player)
                   :velocity [(* (Math/cos heading) power)
                              (* (Math/sin heading) power)]
                   :radius world/missile-radius
                   :mass world/missile-mass}
           counter 0]
      (let [bullet (update-particle bullet planets simulation-step)
            planet (circle-X-any-circle bullet planets)
            player (circle-X-any-circle bullet players)]
        (cond
          (> counter max-cycle) [world nil]
          planet [world planet]
          player [(kill-player world player) player]
          :else (recur bullet (+ counter 1)))))))
