(ns hyperspace.client.richworld
  (:require [hyperspace.library.simulation :as simulation]
            [hyperspace.library.world :as world])
  (:use [hyperspace.client particles]
        [hyperspace.library geometry]))

;;; Player-related stuff:

(defn enrich-player
  "Enrich player with UI-required properties."
  [player]
  (assoc player :heading [0 1]))

;;; Missiles-related stuff:

(defn make-missile
  [position velocity trace-index]
  (assoc (world/make-missile position velocity)
    :trace-index trace-index))

(defn add-missile
  [{missiles :missiles
    traces   :traces
    :as world}
   position velocity]
  (let [new-missile (make-missile position
                                  velocity
                                  (count traces))]
    (assoc world
      :missiles (conj missiles new-missile)
      :traces (conj traces []))))

;;; World-related stuff:

(defn enrich-world
  "Update player definitions in world (enrich with UI-required properties)."
  [world]
  (update-in world [:players] (fn [players] (->> players
                                                 (map enrich-player)
                                                 (into [])))))

(defn update-world
  [{missiles :missiles
    planets :planets
    fragments :fragments
    traces :traces
    :as world}
   delta-time]
  (if (<= delta-time simulation/simulation-step)
    [world delta-time]
    (let [broken-particles (mapcat #(break-particle % planets)
                            (concat missiles fragments))
          ;; FIXME: Duplicate code
          new-missiles (->> missiles
                        (filter #(circle-X-rectangle? % world))
                        (remove #(circle-X-any-circle % planets))
                        (map #(simulation/update-particle % planets simulation/simulation-step)))
          new-fragments (->> fragments
                         (concat broken-particles)
                         (filter #(circle-X-rectangle? % world))
                         (remove #(circle-X-any-circle % planets))
                         (map #(simulation/update-particle % planets simulation/simulation-step)))

          new-traces  (reduce update-traces traces new-missiles)]
      (recur
        (assoc world
          :missiles new-missiles
          :fragments new-fragments
          :traces new-traces)
        (- delta-time simulation/simulation-step)))))

(defn fire
  [{[player & _] :players
    :as world}]
  (let [{position               :position
         [_, power :as heading] :heading} player
        missile-position (-> (polar->cartesian heading)
                             normalize-vector
                             (multiply-by-scalar (+ world/player-radius
                                                    world/missile-radius))
                             (vector-sum position))
        missile-velocity (-> (polar->cartesian heading)
                             normalize-vector
                             (multiply-by-scalar (* power 100)))]
    (add-missile world
                 missile-position
                 missile-velocity)))
