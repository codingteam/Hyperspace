(ns hyperspace.test.client.richworld
  (:use [hyperspace.client.richworld]
        [hyperspace.test.checkers]
        [midje.sweet]))

(facts "about make-missile function"
  (:trace-index (make-missile [10 20] [30 40] 888)) => 888)
