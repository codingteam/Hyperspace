(defn update-world
  "Simulates few steps for world."
  [init-world time-delta]
  (loop [time time-delta
         world init-world]
    (let [planets (:planets world)
          bullets (map #(move-bullet % planets) (:bullets world))
          players (:players world)
          new-world (world. planets bullets players)]
      (recur (- time 1) new-world))))

(defn move-bullet
  [bullet planets]
  (let [acceleration (get-acceleration planets)
        position (:center bullet)
        velocity (:velocity bullet)
        new-position (point-move position velocity)
        new-velocity (vector-sum velocity acceleration)]
    (bullet. new-position new-velocity)))
