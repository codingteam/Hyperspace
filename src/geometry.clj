(defrecord point2 [x y])
(defrecord vector2 [x y])

(defn point-move
  [point vector]
  (let [new-x (+ (:x point) (:x vector))
        new-y (+ (:y point) (:y vector))]
    (point2. new-x new-y)))

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
    (vector2. new-x new-y)))
