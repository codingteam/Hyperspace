(def gravity-constant 6.6725e-11)
(def bullet-mass 1)

(declare update-world)
(declare move-bullet)
(declare get-acceleration)
(declare gravity-force)

(defn get-acceleration
  [bullet planets]
  (let [planet-center (:center planet)
        bullet-center (:center bullet)
         force (reduce #(+ %1 (gravity-force
                                bullet-mass (:mass %2)
                                (point-distance planet-center bullet-center))
                        0
                        planets))]
    (/ force bullet-mass)))

(defn gravity-force
  [m1 m2 distance]
  (/ (* gravity-constant m1 m2) (Math/pow distance 2)))
