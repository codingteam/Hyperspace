(ns hyperspace.test.utils)

(defn equal-with-eps
  [eps a b]
  (< (Math/abs (- a b)) eps))

(defn almost=
  [x]
  (partial equal-with-eps 1e-6 x))