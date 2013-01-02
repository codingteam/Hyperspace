(ns hyperspace.test.library.sumulation
  (:use [hyperspace.library.simulation]
        [hyperspace.test.checkers]
        [midje.sweet])
  (:require [hyperspace.library.geometry :as geometry]
            [hyperspace.library.gravity :as gravity]
            [hyperspace.library.world :as world]))

(def player-fixture (world/make-player [10 10]))
(def nonexistent-player-fixture (world/make-player [20 20]))
(def world-fixture (-> (world/create 800 600)
                       (assoc :players [player-fixture])))

(facts "about kill-player function"
  (kill-player world-fixture player-fixture) => (assoc world-fixture :players [(assoc player-fixture :status :dead)])
  (kill-player world-fixture nonexistent-player-fixture) => world-fixture)

(def missile-fixture (world/make-missile [0 0] [10 0]))
(def planet-fixture (world/make-planet [100 100] 50))

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
  (facts "about update-particle function"
    (:velocity missile1) => (just [(almost= 10)
                                   (almost= 0)])
    (:position missile1) => (just [(almost= 1e-2)
                                   (almost= 0)])
    (:velocity missile2) => velocity2
    (:position missile2) => position2
    ;; TODO: test with two planets
    ;; TODO: test with time > 1
    ))

;;; TODO: Test the fire function.
