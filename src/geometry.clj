(defrecord point-record [x y])
(defrecord vector-record [x y])

(defn point
  [x y]
  (point-record. x y))
