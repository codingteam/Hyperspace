(ns hyperspace.test.checkers
  (:use [clojure.test]))

(defn is-almost= [x y]
  (if (vector? x)
    (do
      (is (= (count x) (count y)))
      (doseq [[a b] (map vector x y)]
        (is (< (Math/abs (- b a)) 1e-6))))
    (is (< (Math/abs (- y x)) 1e-6))))

(deftest is-almost=-tests
  (is-almost= 1.0 1.0)
  (is-almost= [1.0] [1.0])
  (is-almost= [1.0 1.1] [1.0 1.1]))
