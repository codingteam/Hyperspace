(ns hyperspace.test.client.controls
  (:use [hyperspace.client.controls]
        [hyperspace.client.richworld]
        [hyperspace.test.checkers]
        [midje.sweet]))

(let [world (enrich-world {:players [{}]})]
  (facts "about turn-left function"
         (turn-left world 5) => {:players [{:heading [5 1]}]})
  (facts "about turn-right function"
         (turn-right world 5) => {:players [{:heading [-5 1]}]}))
