(ns hyperspace.game
  (:use [hyperspace world geometry gravity misc]))

(defn turn-right
  [{[player & _] :players
    :as world}
   delta-angle]
  (let [{[angle, _] :heading} player]
    (assoc-in world
              [:players 0 :heading 0]
              (- angle delta-angle))))

(defn turn-left
  [{[player & _] :players
    :as world}
   delta-angle]
  (let [{[angle, _] :heading} player]
    (assoc-in world
              [:players 0 :heading 0]
              (+ angle delta-angle))))

(defn increase-power
  [world delta-power]
  (let [power (get-in world [:players 0 :heading 1])]
    (assoc-in world
              [:players 0 :heading 1]
              (saturation (+ power delta-power)
                          1 10))))

(defn decrease-power
  [world delta-power]
  (let [power (get-in world [:players 0 :heading 1])]
    (assoc-in world
              [:players 0 :heading 1]
              (saturation (- power delta-power)
                          1 10))))

(defn fire
  [{[player & _] :players
    :as world}]
  (let [{position               :position
         [_, power :as heading] :heading} player
         missile-position (-> (polar->cartesian heading)
                              normalize-vector
                              (multiply-by-scalar (+ player-radius
                                                     missile-radius))
                              (vector-sum position))
         missile-velocity (-> (polar->cartesian heading)
                              normalize-vector
                              (multiply-by-scalar (* power 100)))]
    (add-missile world
                 missile-position
                 missile-velocity)))

(defn update-particle
  [{position :position
    velocity :velocity
    :as particle}
   planets
   delta-time]
  (let [acceleration (apply vector-sum
                            (map #(gravity-acceleration particle %)
                                 planets))
        ;; FIXME: Duplicate code
        new-velocity (-> acceleration
                         (multiply-by-scalar (* delta-time 1e-3))
                         (vector-sum velocity))
        new-position (-> new-velocity
                         (multiply-by-scalar (* delta-time 1e-3))
                         (vector-sum position))]
    (assoc particle
      :position new-position
      :velocity new-velocity)))

(defn update-traces
  [traces
   {position :position
    trace-index :trace-index}]
  (update-in traces [trace-index] conj position))

(defn break-particle
  [{particle-position :position
    particle-radius   :radius
    velocity          :velocity
    radius            :radius
    :as particle}
   planets]
  (let [planet (some #(when (circle-X-circle? % particle) %) planets)]
    (if (and planet (> radius 1.0))
      (let [{planet-position   :position
             planet-radius     :radius} planet

             fragments-position (-> (vector-subtract particle-position planet-position)
                                    normalize-vector
                                    (multiply-by-scalar (+ particle-radius planet-radius 5))
                                    (vector-sum planet-position))]
        (repeatedly 5
                    (fn []
                      (make-fragment fragments-position
                                     (polar->cartesian [(rand (* 2 Math/PI)),
                                                        (/ (vector-length velocity) 2)])
                                     (- radius 2)))))
      ())))

(defn exit [world]
  (assoc world
    :exit true))

(defn update-world
  [{missiles :missiles
    planets :planets
    fragments :fragments
    traces :traces
    :as world}
   delta-time]
  (let [broken-particles (mapcat #(break-particle % planets)
                                 (concat missiles fragments))
        ;; FIXME: Duplicate code
        new-missiles (->> missiles
                          (filter #(circle-X-rectangle? % world))
                          (remove #(circle-X-any-circle? % planets))
                          (map #(update-particle % planets delta-time)))
        new-fragments (->> fragments
                           (concat broken-particles)
                           (filter #(circle-X-rectangle? % world))
                           (remove #(circle-X-any-circle? % planets))
                           (map #(update-particle % planets delta-time)))

        new-traces  (reduce update-traces traces new-missiles)]
    (assoc world
      :missiles new-missiles
      :fragments new-fragments
      :traces new-traces)))