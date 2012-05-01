(ns hyperspace.test.world
  (:use (clojure test))
  (:use (hyperspace.test utils))
  (:use (hyperspace geometry
                    world)))

;; Object creation tests

(deftest planet-test
  (let [planet (make-planet (make-point 1 0) 7000 10000)]
    (is (= (:center planet) (make-point 1 0)))
    (is (= (:radius planet) 7000))
    (is (= (:mass planet) 10000))))

(deftest player-test
  (let [player (make-player (make-point 10 5) (/ Math/PI 2) 120 "test-player")]
    (is (= (:center player) (make-point 10 5)))
    (is (= (:heading player (/ Math/PI 2))))
    (is (= (:power player) 120))
    (is (= (:name player "test-player")))))

(deftest bullet-test
  (let [bullet (make-bullet (make-point 10 20)
                            (make-vector 0 1))]
    (is (= (:center bullet) (make-point 10 20)))
    (is (= (:velocity bullet) (make-vector 0 1)))))

(deftest trace-test
  (let [trace (make-trace [(make-point 1 0)
                           (make-point 2 2)])]
    (is (= (:points trace) [(make-point 1 0)
                            (make-point 2 2)]))))
