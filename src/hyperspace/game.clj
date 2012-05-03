(ns hyperspace.game
  (:use (hyperspace geometry
                    gravity
                    world)))

(defn get-player
  [world name]
  (first (filter #(= name (:name %)) (:players world))))

(defn update-player-params
  [world name heading power]
  (assoc world
    :players (map (fn [player]
                    (if (= (:name player) name)
                      (assoc player
                        :heading heading
                        :power power)
                      player))
                  (:players world))))

(defn fire
  [world player-name]
  (let [player (get-player world player-name)
        {point :center
         power :power
         angle :heading} player
        bullet (make-bullet point (make-vector-radial power angle))
        trace (make-trace bullet)]
    (assoc world
      :bullets (conj (:bullets world) bullet)
      :traces  (conj (:traces world) trace))))

(defn move-bullet
  [bullet planets]
  (let [acceleration (get-acceleration bullet planets)
        {position :center
         velocity :velocity} bullet]
    (assoc bullet
      :center   (move-point position velocity)
      :velocity (vector-sum velocity acceleration))))

(defn destroy-bullet?
  [bullet planets]
  (let [bullet-center (:center bullet)]
    (some (fn [{planet-radius :radius
                planet-center :center}]
            (<= (point-distance bullet-center
                                planet-center)
                planet-radius))
          planets)))

(defn update-world
  "Simulates few steps for world."
  [world time]
  (let [{bullets :bullets
         planets :planets
         traces  :traces} world]
    (if (<= time 0)
      world
      (recur
        (assoc world
          :bullets (doall
                     (map #(move-bullet % planets)
                       (filter #(not (destroy-bullet? % planets))
                               bullets)))
          :traces (doall
                    (map #(update-trace %1 %2)
                      traces bullets)))
        (- time 1)))))
