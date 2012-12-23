(ns hyperspace.client.particles
  (:use [hyperspace.library geometry]))

(defn make-fragment
  [position velocity radius]

  {:position position
   :velocity velocity
   :radius radius
   :mass radius})

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
  (let [planet (some #(when (circle-X-circle? % particle) %) planets)]
    (if (and planet (> radius 1.0))
      (let [{planet-position   :position
             planet-radius     :radius} planet

            fragments-position (-> (vector-subtract particle-position planet-position)
          normalize-vector
          (multiply-by-scalar (+ particle-radius planet-radius 5))
          (vector-sum planet-position))]
        (repeatedly 5
          (fn []
            (make-fragment fragments-position
              (polar->cartesian [(rand (* 2 Math/PI)),
                                 (/ (vector-length velocity) 2)])
              (- radius 2)))))
      ())))