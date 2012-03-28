(ns hyperspace.ui
  (:use (hyperspace game
                    geometry
                    world))
  (:import (org.lwjgl Sys)
           (org.lwjgl.input Keyboard)
           (org.lwjgl.opengl Display DisplayMode GL11)
           (hyperspace.geometry point2 vector2)
           (hyperspace.world bullet world)))

(declare start-ui)
(declare setup-display)
(declare get-time)
(declare ui-loop)
(declare clear-display)
(declare render-traces)
(declare render-planets)
(declare render-players)
(declare render-bullets)
(declare process-input)
(declare render-trace)
(declare render-planet)
(declare render-player)
(declare render-bullet)

(def window-width 800)
(def window-height 600)
(def fps 60)
(def scale 1) ; pixels per kilometer
(def time-scale 10) ; rounds per second

(defn start-ui
  "Starts UI."
  [world]
  (setup-display)
  (ui-loop world))

(defn setup-display []
  (Display/setDisplayMode (DisplayMode. window-width window-height))
  (Display/create)
  (GL11/glClearColor 0 0 0 0)
  (GL11/glViewport 0 0 window-width window-height))

(defn get-time
  []
  (/ (Sys/getTime) (Sys/getTimerResolution)))

(defn ui-loop [init-world]
  (loop [time (get-time)
         world init-world]
    (clear-display)
    (render-traces world)
    (render-planets world)
    (render-players world)
    (render-bullets world)
    (let [world (process-input world)]
      (if (Display/isCloseRequested)
        (Display/destroy)
        (let [new-time (get-time)
              time-delta (- new-time time)
              new-world (update-world world (* time-delta time-scale))]
          (Display/sync fps)
          (Display/update)
          (recur new-time new-world))))))

(defn clear-display []
  (GL11/glClear (bit-or GL11/GL_COLOR_BUFFER_BIT GL11/GL_DEPTH_BUFFER_BIT)))

(defn render-traces [{traces :traces}]
  (doseq [trace traces]
    (render-trace trace)))

(defn render-planets [{planets :planets}]
  (doseq [planet planets]
    (render-planet planet)))

(defn render-players [{players :players}]
  (doseq [player players]
    (render-player player)))

(defn render-bullets [{bullets :bullets}]
  (doseq [bullet bullets]
    (render-bullet bullet)))

(defn process-input [world]
  (if (Keyboard/next)
    (let [{[{player-center :center} & _] :players
           bullets                       :bullets} world]
      (assoc world
        :bullets (conj bullets (bullet. player-center (vector2. 1 1)))))
    world))

(defn normalize-x [x]
  (/ x window-width))

(defn normalize-y [y]
  (/ y window-height))

(defn space-point-to-display [{x :x y :y :as point}]
  (assoc point
    :x (- (* 2 (normalize-x (* x scale))) 1)
    :y (- (* 2 (normalize-y (* y scale))) 1)))

(defn render-trace
  "Suddenly, a trace renders as a yellow point."
  [trace]
  (GL11/glColor3f 1 1 0)
  (let [center (space-point-to-display (:center trace))
        {center-x :x center-y :y} center
        x1 (- center-x (normalize-x 1))
        y1 (- center-y (normalize-y 1))
        x2 (+ center-x (normalize-x 1))
        y2 (+ center-y (normalize-y 1))]
    (GL11/glRectf x1 y1 x2 y2)))

(defn render-planet
  "A planet now renders as green rectangle."
  [planet]
  (GL11/glColor3f 0 1 0)
  (let [center (space-point-to-display (:center planet))
        {center-x :x center-y :y} center
        radius-px (* (:radius planet) scale)
        delta-x (normalize-x radius-px)
        delta-y (normalize-y radius-px)
        x1 (- center-x delta-x)
        y1 (- center-y delta-y)
        x2 (+ center-x delta-x)
        y2 (+ center-y delta-y)]
    (GL11/glRectf x1 y1 x2 y2)))

(defn render-player [player]
  "A player now renders as a, well, blue rectangle."
  (GL11/glColor3f 0 0 1)
  (let [center (space-point-to-display (:center player))
        {center-x :x center-y :y} center
        x1 (- center-x (normalize-x 2))
        y1 (- center-y (normalize-y 2))
        x2 (+ center-x (normalize-x 2))
        y2 (+ center-y (normalize-y 2))]
    (GL11/glRectf x1 y1 x2 y2)))

(defn render-bullet [bullet]
  "A bullet now renders as a... guess what? Right, red rectangle!"
  (GL11/glColor3f 1 0 0)
  (let [center (space-point-to-display (:center bullet))
        {center-x :x center-y :y} center
        x1 (- center-x (normalize-x 1))
        y1 (- center-y (normalize-y 1))
        x2 (+ center-x (normalize-x 1))
        y2 (+ center-y (normalize-y 1))]
    (GL11/glRectf x1 y1 x2 y2)))