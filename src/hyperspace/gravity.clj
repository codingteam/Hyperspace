(ns hyperspace.gravity
  (:use (hyperspace geometry))
  (:import (hyperspace.geometry vector2)))

(def gravity-constant 3);6.6725e-11) TODO: Fix this!
(def bullet-mass 1)

(defn gravity-force
  [m1 m2 distance]
  (/ (* gravity-constant m1 m2) (Math/pow distance 2)))

(defn planet-gravity-force
  [bullet planet]
  (let [planet-center (:center planet)
        bullet-center (:center bullet)
        planet-mass (:mass planet)
        distance (point-distance planet-center bullet-center)
        force (gravity-force planet-mass bullet-mass distance)
        acceleration (/ force bullet-mass)
        angle (bearing-to bullet-center planet-center)]
    (make-vector-radial acceleration angle)))

(defn get-acceleration
  [bullet planets]
  (reduce #(vector-sum %1 (planet-gravity-force bullet %2))
          (vector2. 0 0)
          planets))