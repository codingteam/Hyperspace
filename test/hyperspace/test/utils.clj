(ns hyperspace.test.utils)

(defn compare-with-eps
  [eps a b]
  (< (Math/abs (- a b)) eps))

(def almost= (partial compare-with-eps 1e-6))
