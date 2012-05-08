(ns hyperspace.test.utils)

(defn compare-with-eps
  [eps a b]
  (< (Math/abs (- a b)) eps))

(def almost= (partial compare-with-eps 1e-6))

(defn points-almost=
  [p1 p2]
  (and (almost= (:x p1) (:x p2))
       (almost= (:y p1) (:y p2))))

(defn vectors-almost=
  [v1 v2]
  ;; Vector is almost the same as point, so we can reuse points-amlost= function here.
  (points-almost= v1 v2))
