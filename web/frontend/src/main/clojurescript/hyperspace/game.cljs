(ns hyperspace.game
  (:require [hyperspace.geometry :as geometry]
            [hyperspace.gravity :as gravity]
            [hyperspace.world :as world]))

(defn update-particle
  [{position :position
    velocity :velocity
    :as particle}
   planets
   delta-time]
  (let [acceleration (apply geometry/vector-sum
                            (map #(gravity/gravity-acceleration particle %)
                                 planets))
        ;; FIXME: Duplicate code
        new-velocity (-> acceleration
                         (geometry/multiply-by-scalar (* delta-time 1e-3))
                         (geometry/vector-sum velocity))
        new-position (-> new-velocity
                         (geometry/multiply-by-scalar (* delta-time 1e-3))
                         (geometry/vector-sum position))]
    (assoc particle
      :position new-position
      :velocity new-velocity)))

(defn update-traces
  [traces
   {position :position
    trace-index :trace-index}]
  (update-in traces [trace-index] conj position))

(defn break-particle
  [{particle-position :position
    particle-radius   :radius
    velocity          :velocity
    radius            :radius
    :as particle}
   planets]
  (let [planet (some #(when (geometry/circle-X-circle?? % particle) %) planets)]
    (if (and planet (> radius 1.0))
      (let [{planet-position   :position
             planet-radius     :radius} planet

             fragments-position (-> (geometry/vector-subtract particle-position planet-position)
                                    geometry/normalize-vector
                                    (geometry/multiply-by-scalar (+ particle-radius planet-radius 5))
                                    (geometry/vector-sum planet-position))]
        (repeatedly 5
                    (fn []
                      (world/make-fragment fragments-position
                                     (geometry/polar->cartesian [(rand (* 2 Math/PI)),
                                                        (/ (geometry/vector-length velocity) 2)])
                                     (- radius 2)))))
      ())))

(defn exit [world]
  (assoc world
    :exit true))

(def simulation-step 10)

(defn update-world
  [{missiles :missiles
    planets :planets
    fragments :fragments
    traces :traces
    :as world}
   delta-time]
  (if (<= delta-time simulation-step)
    [world delta-time]
    (let [broken-particles (mapcat #(break-particle % planets)
                                   (concat missiles fragments))
          ;; FIXME: Duplicate code
          new-missiles (->> missiles
                            (filter #(geometry/circle-X-rectangle?? % world))
                            (remove #(geometry/circle-X-any-circle?? % planets))
                            (map #(update-particle % planets simulation-step)))
          new-fragments (->> fragments
                             (concat broken-particles)
                             (filter #(geometry/circle-X-rectangle?? % world))
                             (remove #(geometry/circle-X-any-circle?? % planets))
                             (map #(update-particle % planets simulation-step)))

          new-traces  (reduce update-traces traces new-missiles)]
      (recur
        (assoc world
          :missiles new-missiles
          :fragments new-fragments
          :traces new-traces)
        (- delta-time simulation-step)))))
