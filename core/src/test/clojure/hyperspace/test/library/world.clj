(ns hyperspace.test.library.world
  (:use [clojure.test]
        [hyperspace.library.world]
        [hyperspace.test.checkers]))

(deftest make-missile-tests
  (is (= (make-missile [10 20] [30 40]) {:position [10 20]
                                         :velocity [30 40]
                                         :radius   missile-radius
                                         :mass     missile-mass})))
