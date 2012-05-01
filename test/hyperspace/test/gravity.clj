(ns hyperspace.test.gravity
  (:use (clojure test))
  (:use (hyperspace.test utils))
  (:use (hyperspace geometry
                    gravity
                    world)))

(deftest gravity-force-test
  (is (almost= (gravity-force 10 20 1000)
               (* gravity-constant (/ (* 10 20) (Math/pow 1000 2))))))

(deftest gravity-constant-test
  (is (almost= gravity-constant 6.674e-11)))

(deftest planet-gravity-test
  (let [planet (make-planet (make-point 10 10) 10 500)
        bullet (make-bullet (make-point 10 15) (make-vector 10 0))
        force (planet-gravity-force planet bullet)]
    (is (almost= (vector-length force)
                 (gravity-force 10 bullet-mass 5)))
    (is (almost= (vector-bearing force)
                 (- (/ Math/PI 2))))))

(deftest acceleration-test
  (let [planet1 (make-planet (make-point 0 0) 0 100)
        planet2 (make-planet (make-point 10 0) 0 100)
        bullet (make-bullet (make-point 6 0) (make-vector 0 0))
        acceleration (get-acceleration bullet [planet1 planet2])
        force (vector-sum (planet-gravity-force planet1 bullet)
                          (planet-gravity-force planet2 bullet))]
    (is (almost= (vector-length acceleration)
                 (/ (vector-length force) 100)))
    (is (almost= (vector-bearing acceleration)
                 (vector-bearing force)))))
