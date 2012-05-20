(ns hyperspace.test.checkers
  (:use [midje.sweet]))

(defchecker almost= [expected]
  (chatty-checker [actual]
    (< (Math/abs (- expected actual)) 1e-6)))