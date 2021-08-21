(ns hyperspace.test.client.controls
  (:use [clojure.test]
        [hyperspace.client.controls]
        [hyperspace.client.richworld]))

(deftest controls-test
  (let [world (enrich-world {:players [{}]})]
    (testing "turn-left function"
      (is (= (turn-left world 5) {:players [{:heading [5 1]}]})))
    (testing "turn-right function"
      (is (= (turn-right world 5) {:players [{:heading [-5 1]}]})))))
