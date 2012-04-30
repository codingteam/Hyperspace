(ns hyperspace.test.geometry
  (:use (hyperspace geometry))
  (:use (clojure test)))

(declare
  point-test
  vector-test
  point-move-test
  point-distance-test
  vector-sum-test
  bearing-test)

(deftest geometry-test
  (point-test)
  (vector-test)
  (point-move-test)
  (point-distance-test)
  (vector-sum-test)
  (bearing-test))

(deftest point-test
  (let [point (make-point 10 20)]
    (is (= (:x point) 10))
    (is (= (:y point) 20))))

(deftest vector-test
  (let [vector (make-vector 100 500)]
    (is (= (:x vector) 100))
    (is (= (:y vector) 500))))

(deftest point-move-test
  (let
    [point (make-point 1 7)
     vector (make-vector 2 4)
     moved-point (move-point point vector)]
    (is (= (:x moved-point) 3))
    (is (= (:y moved-point) 11))))

(deftest point-move-test2
  (let [point (make-point 0.2 0.4)
        vector (make-vector 0.4 0.8)
        moved-point (move-point point vector)]
    (is (= (:x moved-point) 0.6))
    (is (= (:y moved-point) 1.2))))

(deftest point-distance-test
  (let
    [point1 (make-point 0 0)
     point2 (make-point 1 1)
     distance (point-distance point2 point1)]
    (is (= distance (Math/sqrt 2)))))

(deftest vector-sum-test
  (let
    [vector1 (make-vector 1 2)
     vector2 (make-vector 3 4)
     sum (vector-sum vector1 vector2)]
    (is (= (:x sum) 4))
    (is (= (:y sum) 6))))

(deftest bearing-test
  (let
    [point1 (make-point 0 0)
     point2 (make-point 0 1)
     bearing (bearing-to point1 point2)]
    (is (= bearing (/ Math/PI 2)))))
