(ns hyperspace.test.game
  (:use (clojure test)
        (hyperspace.test utils)
        (hyperspace game
                    geometry
                    gravity
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

(deftest bullet-move-test
  (let [planet1 (make-planet (make-point 0 0) 1 100)
        planet2 (make-planet (make-point 0 5) 1 200)
        planets [planet1 planet2]
        bullet (make-bullet (make-point 5 5) (make-vector 1 1))
        acceleration (get-acceleration bullet planets)
        new-velocity (vector-sum (:velocity bullet) acceleration)
        new-bullet (move-bullet bullet planets)]
    (is (almost= (:x (:center new-bullet)) 6))
    (is (almost= (:y (:center new-bullet)) 6))
    (is (almost= (:x (:velocity new-bullet))
                 (:x new-velocity)))
    (is (almost= (:y (:velocity new-bullet))
                 (:y new-velocity)))))
