(ns hyperspace.test.cases
  (:use (hyperspace geometry
                    world)))

(defn get-test-world
  []
  (let [players [(make-player (make-point 100 100) 0 0 "player-1")
                 (make-player (make-point 200 200) 0 0 "player-2")]
        planet (make-planet (make-point 0 0) 100 100e12)]
    (make-world [planet] players)))
