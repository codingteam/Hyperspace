(ns hyperspace.test.library.sumulation
  (:use [hyperspace.library.simulation]
        [hyperspace.test.checkers]
        [midje.sweet])
  (:require [hyperspace.library.world :as world]))

(def missile-fixture (world/make-missile [0 0] [10 0]))
(def player-fixture (world/make-player [10 10]))
(def nonexistent-player-fixture (world/make-player [20 20]))
(def world-fixture (-> (world/create 800 600)
                       (assoc :players [player-fixture])))

(facts "about kill-player function"
  (kill-player world-fixture player-fixture) => (assoc world-fixture :players [(assoc player-fixture :status :dead)])
  (kill-player world-fixture nonexistent-player-fixture) => world-fixture)

(facts "about update-particle function"
  (:position (update-particle missile-fixture [] 1)) => (just [(almost= 1e-2)
                                                               (almost= 0)])
  ;; TODO: test with planet
  ;; TODO: test with two planets
  )

;;; TODO: Test the fire function.
