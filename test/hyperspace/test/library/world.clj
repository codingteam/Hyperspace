(ns hyperspace.test.library.world
  (:use [hyperspace.library.world]
        [hyperspace.test.checkers]
        [midje.sweet]))

(facts "about make-missile function"
  (make-missile [10 20] [30 40]) => {:position [10 20]
                                     :velocity [30 40]
                                     :radius   missile-radius
                                     :mass     missile-mass})
