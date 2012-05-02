(ns hyperspace.test.game
  (:use (clojure test))
  (:use (hyperspace game
                    geometry
                    world)))

(deftest player-params-test
  (let [player1 (make-player (make-point 0 0) 0 0 "p1")
        player2 (make-player (make-point 0 1) 10 30 "p2")
        player2-changed (make-player (make-point 0 1) 15 20 "p2")
        players [player1 player2]
        world1 (make-world [] players)
        world2 (update-player-params world1 "p2" 15 20)]
    (is (= (set (:players world1))
           (set players)))
    (is (= (set (:players world2))
           (set [player1 player2-changed])))))
