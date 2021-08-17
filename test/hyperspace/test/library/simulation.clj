(ns hyperspace.test.library.simulation
  (:use [clojure.test]
        [hyperspace.library.simulation]
        [hyperspace.test.checkers])
  (:require [hyperspace.library.geometry :as geometry]
            [hyperspace.library.gravity :as gravity]
            [hyperspace.library.world :as world]))

(def player-fixture (world/make-player [10 10]))
(def player2-fixture (world/make-player [15 15]))
(def nonexistent-player-fixture (world/make-player [20 20]))
(def world-fixture (-> (world/create 800 600)
                       (assoc :players [player-fixture])))
(def two-player-world-fixture (assoc world-fixture
                                     :players [player-fixture player2-fixture]))

(deftest kill-player-tests
  (is (= (kill-player world-fixture player-fixture)
         (assoc world-fixture :players [(assoc player-fixture :status :dead)])))
  (is (= (kill-player world-fixture nonexistent-player-fixture) world-fixture)))

(def missile-fixture (world/make-missile [0 0] [10 0]))
(def planet-fixture (world/make-planet [100 100] 50))

(deftest update-particle-tests
  (let [missile1  (update-particle missile-fixture [] 1)
        missile2  (update-particle missile-fixture [planet-fixture] 1)
        velocity2 (geometry/vector-sum
                    (:velocity missile-fixture)
                    (geometry/multiply-by-scalar
                      (gravity/gravity-acceleration
                        missile-fixture
                        planet-fixture)
                      1e-3))
        position2 (geometry/vector-sum
                    (:position missile-fixture)
                    (geometry/multiply-by-scalar
                      velocity2
                      1e-3))]
    (is-almost= (:velocity missile1) [10 0])
    (is-almost= (:position missile1) [1e-2 0])
    (is (= (:velocity missile2) velocity2))
    (is (= (:position missile2) position2))
    ;; TODO: test with two planets
    ;; TODO: test with time > 1
    ))

(deftest fire-tests
  (let [heading (geometry/heading (:position player-fixture) (:position player2-fixture))
        result-player2 (assoc player2-fixture :status :dead)
        result-world (assoc two-player-world-fixture :players [player-fixture result-player2])]
    (is (= (fire two-player-world-fixture player-fixture heading 10)
          [result-world player2-fixture])))
  ;; TODO: test shot to planet
  (is (= (fire world-fixture player-fixture 0 10) [world-fixture nil])))
