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
        bullet (make-bullet point (make-vector-radial power angle))]
    (assoc world
      :bullets (conj (:bullets world) bullet))))

(defn destroy-bullet?
  [bullet planets]
  (let [bullet-center (:center bullet)]
    (some (fn [{planet-radius :radius
                planet-center :center}]
            (<= (point-distance bullet-center
                                planet-center)
                planet-radius))
          planets)))

(defn update-bullet
  [bullet planets]
  (if (= (:status bullet) :dead)
    bullet
    (let [acceleration (get-acceleration bullet planets)
          {position :center
           velocity :velocity
           traces :traces} bullet]
      (assoc bullet
        :center   (move-point position velocity)
        :velocity (vector-sum velocity acceleration)
        :status   (if (destroy-bullet? bullet planets) :dead :alive)
        :traces   (conj traces position)))))

(defn update-world
  "Simulates few steps for world."
  [world time]
  (let [{bullets :bullets
         planets :planets} world]
    (if (< time 0)
      world
      (recur (assoc world
               :bullets (doall (map #(update-bullet % planets) bullets)))
             (- time 1)))))
