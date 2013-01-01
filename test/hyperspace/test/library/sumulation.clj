(ns hyperspace.test.library.sumulation
  (:use [hyperspace.library.simulation]
        [hyperspace.test.checkers]
        [midje.sweet])
  (:require [hyperspace.library.world :as world]))

(def player-fixture (world/make-player [10 10]))
(def nonexistent-player-fixture (world/make-player [20 20]))
(def world-fixture (-> (world/create 800 600)
                       (assoc :players [player-fixture])))

(facts "about kill-player function"
  (kill-player world-fixture player-fixture) => (assoc world-fixture :players [(assoc player-fixture :status :dead)])
  (kill-player world-fixture nonexistent-player-fixture) => world-fixture)
