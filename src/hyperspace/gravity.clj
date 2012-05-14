(ns hyperspace.gravity
  (:use [hyperspace geometry]))

(def gravity-constant 0.1;6.6725e-11
  )

(defn gravity-acceleration
  [{position1 :position mass1 :mass}
   {position2 :position mass2 :mass}]
  (let [d (distance position1 position2)
        force (/ (* gravity-constant mass1 mass2)
                 (* d d))
        acceleration (/ force mass1)]
    (-> (vector-subtract position2 position1)
        normilize-vector
        (multiply-by-scalar acceleration))))