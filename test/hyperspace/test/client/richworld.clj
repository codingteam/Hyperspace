(ns hyperspace.test.client.richworld
  (:use [hyperspace.client.richworld]
        [hyperspace.test.checkers]
        [midje.sweet]))

(facts "about make-missile function"
  (:trace-index (make-missile [10 20] [30 40] 888)) => 888)

(facts "about enrich-player function"
       (enrich-player {}) => {:heading [0 1]})

(facts "about enrich-world function"
       (enrich-world {:players [{}]}) => {:players [{:heading [0 1]}]})
