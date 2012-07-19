(ns hyperspace.game
  (:use (hyperspace geometry
                    gravity
                    world)))

(def max-bullets 30)
(def max-bullet-distance 3000)

(defn get-player
  [world name]
  (first (filter #(= name (:name %)) (:players world))))

(defn bullet-alive?
  [bullet]
  (let [s (:status bullet)]
    (= :alive s)))

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
        bullets (conj (:bullets world) bullet)
        n-bullets (map #(assoc %1 :index %2) bullets (range))]
    (assoc world :bullets n-bullets)))

(defn destroy-bullet?
  [bullet planets]
  (or
   (> (:distance bullet) max-bullet-distance)
   (let [bullet-center (:center bullet)]
     (some (fn [{planet-radius :radius
                 planet-center :center}]
             (<= (point-distance bullet-center
                                 planet-center)
                 planet-radius))
           planets))))

(defn update-bullet
  [bullet planets]
  (if-not (bullet-alive? bullet)
    bullet
    (let [acceleration (get-acceleration bullet planets)
          {:keys [center velocity traces distance]} bullet
          new-center (move-point center velocity)
          dd (point-distance center new-center)]
      (assoc bullet
        :center   new-center
        :velocity (vector-sum velocity acceleration)
        :status   (if (destroy-bullet? bullet planets) :dead :alive)
        :traces   (conj traces new-center)
        :distance (+ distance dd)))))

(defn update-world-tick
  "Simulate one step. Return new world and (dec time)"
  [[world time]]
  (let [{:keys [bullets planets]} world
        nbullets (take max-bullets (map #(update-bullet % planets) bullets))]
    [(assoc world :bullets nbullets) (dec time)]))

(defn update-world
  "Simulates few steps for world. Returns new world and remaining time (zero)."
  [world time]
  (let [worlds (iterate update-world-tick [world time])
        d (drop-while (fn [[_ t]] (>= t 1)) worlds)]
    (first d)))
