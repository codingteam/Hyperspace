(ns hyperspace.test.geometry
  (:use (clojure test)
        (hyperspace.test utils)
        (hyperspace geometry)))

;; Object creation tests

(deftest point-test
  (let [{x :x y :y} (make-point 10 20)]
    (is (almost= x 10))
    (is (almost= y 20))))

(deftest vector-test
  (let [{x :x y :y} (make-vector 100 500)]
    (is (almost= x 100))
    (is (almost= y 500))))

;; Object behavior tests

(deftest point-move-test
  (let [point (make-point 1 7)
        vector (make-vector 2 4)
        moved-point (move-point point vector)]
    (is (almost= (:x moved-point) 3))
    (is (almost= (:y moved-point) 11))))

(deftest point-move-test2
  (let [point (make-point 0.2 0.4)
        vector (make-vector 0.4 0.8)
        moved-point (move-point point vector)]
    (is (almost= (:x moved-point) 0.6))
    (is (almost= (:y moved-point) 1.2))))

(deftest point-distance-test
  (let [point1 (make-point 0 0)
        point2 (make-point 1 1)
        distance (point-distance point2 point1)]
    (is (almost= distance (Math/sqrt 2)))))

(deftest vector-sum-test
  (let [vector1 (make-vector 1 2)
        vector2 (make-vector 3 4)
        sum (vector-sum vector1 vector2)]
    (is (almost= (:x sum) 4))
    (is (almost= (:y sum) 6))))

(deftest vector-length-test
  (let [v (make-vector 10 15)]
    (is (almost= (vector-length v)
                 (Math/sqrt (+ (* 10 10)
                               (* 15 15)))))))

(deftest vector-bearing-test
  (let [v (make-vector 0 -5)]
    (is (almost= (vector-bearing v)
                 (- (/ Math/PI 2))))))

(deftest bearing-test
  (let [point1 (make-point 0 0)
        point2 (make-point 0 1)
        bearing (bearing-to point1 point2)]
    (is (almost= bearing (/ Math/PI 2)))))
