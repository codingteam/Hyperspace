(ns hyperspace.library.simulation
  (:use [hyperspace.library geometry gravity])
  (:require [hyperspace.library.world :as world]))

(def simulation-step 10)
(def max-cycle 1000000)

(defn kill-player
  "Kills the specified player."
  [{players :players
    :as world}
   player]
  (println "Killing player" player)
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
  (println "planets" planets)
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
    players :players
    :as world}
   player
   heading
   power]
  (println "simulation/fire" world player heading power)
  (println "players1" players)
  (loop [bullet {:position (:position player)
                 :velocity [(* (Math/cos heading) power)
                            (* (Math/sin heading) power)]
                 :radius world/missile-radius
                 :mass world/missile-mass}
         counter 0]
    (println "players2" players)
    (let [bullet (update-particle bullet planets simulation-step)
          planet (circle-X-any-circle bullet planets)
          player (circle-X-any-circle bullet players)]
      (println "players3" players)
      (println "planets" planets)
      (println "bullet" bullet)
      (println "planet" planet)
      (println "player" player)
      (cond
        (> counter max-cycle) [world nil]
        planet [world (:position planet)]
        player [(kill-player world player) (:position planet)]
        :else (recur bullet (+ counter 1))))))
