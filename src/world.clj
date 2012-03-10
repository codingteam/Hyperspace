(defrecord planet-record [center mass radius])
(defrecord player-record [center heading])
(defrecord bullet-record [center heading velocity])

(defrecord world-record [planets players bullets])

(defn generate-world
  []
  ; TODO: Randomize world generation.
  (let [planet (planet-record. (point 0 0) 1 10)
        player (player-record. (point 1 1) 0)]
    (world-record. [planet] [player] [])))
