(ns hyperspace.test.client.richworld
  (:use [clojure.test]
        [hyperspace.client.richworld]
        [hyperspace.test.checkers]))

(deftest make-missile-test
  (is (= (:trace-index (make-missile [10 20] [30 40] 888)) 888)))

(deftest enrich-player-test
  (is (= (enrich-player {}) {:heading [0 1]})))

(deftest enrich-world-test
  (is (= (enrich-world {:players [{}]}) {:players [{:heading [0 1]}]})))
