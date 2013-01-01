(ns hyperspace.client.richworld
  (:require [hyperspace.library.simulation :as simulation]
            [hyperspace.library.world :as world])
  (:use [hyperspace.client particles]
        [hyperspace.library geometry]))

;;; Missles related stuff

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

;;; World updating stuff:

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