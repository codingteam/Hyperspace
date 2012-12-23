(ns hyperspace.client.richworld)

;;; Missles related stuff

(defn make-missile
  [position velocity trace-index]

  {:position position
   :velocity velocity
   :radius missile-radius
   :mass missile-mass
   :trace-index trace-index})

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

;;; Fragments related stuff

(defn make-fragment
  [position velocity radius]

  {:position position
   :velocity velocity
   :radius radius
   :mass radius})
