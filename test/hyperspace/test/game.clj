(ns hyperspace.test.game
  (:use (clojure test)
        (hyperspace.test cases
                         utils)
        (hyperspace game
                    geometry
                    gravity
                    world)))

;; Helper functions:

(defn alive-bullet-count
  [world]
  (count (filter #(= (:status %) :alive) (:bullets world))))

(defn dead-bullets-count
  [world]
  (count (filter #(= (:status %) :dead) (:bullets world))))

(defn trace-count
  [bullet]
  (count (:traces bullet)))

(defn alive-player-count
  [world]
  (count (:players world)))

(defn world-after
  [world time]
  (first (update-world world time)))

;; Simple tests:

(deftest get-player-test
  (let [world (get-test-world)
        player (get-player world "player-1")]
    (is (= (:name player) "player-1"))))

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

(deftest fire-test
  (let [world1 (get-test-world)
        world2 (fire world1 "player-1")
        world3 (fire world2 "player-2")]
    (is (= (alive-bullet-count world1) 0))
    (is (= (alive-bullet-count world2) 1))
    (is (= (alive-bullet-count world3) 2))))

(deftest bullet-move-test
  (let [planet1 (make-planet (make-point 0 0) 1 100)
        planet2 (make-planet (make-point 0 5) 1 200)
        planets [planet1 planet2]
        bullet1 (make-bullet (make-point 5 5) (make-vector 1 1))
        bullet2 (update-bullet bullet1 planets)

        acceleration (get-acceleration bullet1 planets)
        new-velocity (vector-sum (:velocity bullet1) acceleration)]
    (is (points-almost= (:center bullet2)
                        (make-point 6 6)))
    (is (vectors-almost= (:velocity bullet2)
                         new-velocity))))

(deftest trace-test
  (let [planet (make-planet (make-point 1 5) 5 100)
        bullet1 (make-bullet (make-point 1 30) (make-vector 1 1))
        bullet2 (update-bullet bullet1 [planet])
        bullet3 (update-bullet bullet2 [planet])]
    (is (= (trace-count bullet1) 0))
    (is (= (trace-count bullet2) 1))
    (is (= (trace-count bullet3) 2))

    (is (points-almost= (first (:traces bullet2))
                        (:center bullet1)))
    (is (points-almost= (first (:traces bullet3))
                        (:center bullet1)))
    (is (points-almost= (last (:traces bullet3))
                        (:center bullet2)))))

(deftest bullet-destroy-test
  (let [planet (make-planet (make-point 0 0) 5 100)
        bullet1 (make-bullet (make-point 1 1) (make-vector 0 0))
        bullet2 (make-bullet (make-point 4 4) (make-vector 0 0))]
    (is (destroy-bullet? bullet1 [planet]))
    (is (not (destroy-bullet? bullet2 [planet])))))

;; Complex tests:

(deftest timing-test
  (let [world1 (get-test-world)
        world2 (fire world1 "player-1")
        [world3 dtime3] (update-world world2 10.5)
        [world4 dtime4] (update-world world3 0.6)
        bullet3 (first (:bullets world3))
        bullet4 (first (:bullets world4))]
    (is (= (count (:traces bullet3)) 10))
    (is (almost= dtime3 0.5))
    (is (almost= dtime4 0.6))
    (is (= (:center bullet3)
           (:center bullet4)))))

(deftest complex-firing-test
  (let [world1 (get-test-world)
        world2 (fire world1 "player-1")
        world3 (world-after world2 10000)]
    (is (= (alive-bullet-count world2) 1))
    (is (= (dead-bullets-count world2) 0))

    (is (= (alive-bullet-count world3) 0))
    (is (= (dead-bullets-count world3) 1))))

(deftest kill-test
  (let [world1 (get-test-world)
        world2 (world-after (fire world1 "player-2") 10000)]
    (is (= (alive-player-count world2) 1))))
