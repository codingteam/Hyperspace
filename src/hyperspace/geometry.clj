(ns hyperspace.geometry)

(defrecord Point2 [x y])
(defrecord Vector2 [x y])

(def make-point ->Point2)
(def make-vector ->Vector2)

(defn make-vector-radial
  [length angle]
  (let [x (* length (Math/cos angle))
        y (* length (Math/sin angle))]
    (make-vector x y)))

(defn move-point
  [point vector]
  (let [new-x (+ (:x point) (:x vector))
        new-y (+ (:y point) (:y vector))]
    (make-point new-x new-y)))

(defn point-distance
  [p1 p2]
  (let [x1 (:x p1)
        x2 (:x p2)
        y1 (:y p1)
        y2 (:y p2)]
    (Math/sqrt (+ (Math/pow (- x2 x1) 2)
                  (Math/pow (- y2 y1) 2)))))

(defn vector-sum
  [v1 v2]
  (let [new-x (+ (:x v1) (:x v2))
        new-y (+ (:y v1) (:y v2))]
    (make-vector new-x new-y)))

(defn bearing-to
  [p1 p2]
  (let [x1 (:x p1)
        x2 (:x p2)
        y1 (:y p1)
        y2 (:y p2)]
    (Math/atan2 (- y2 y1) (- x2 x1))))
