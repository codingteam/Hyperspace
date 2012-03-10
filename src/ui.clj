(import '(org.lwjgl.opengl Display DisplayMode))
(import '(org.lwjgl.opengl GL11))

(declare start-ui)
(declare setup-display)
(declare ui-loop)
(declare clear-display)
(declare render-planets)
(declare render-players)
(declare render-bullets)
(declare render-planet)
(declare render-player)
(declare render-bullet)

(def window-width 800)
(def window-height 600)
(def scale 1) ; pixels per light year

(defn start-ui
  "Starts UI."
  [world]
  (setup-display)
  (ui-loop world))

(defn setup-display []
  (Display/create)
  (Display/setDisplayMode (DisplayMode. window-width window-height))
  (GL11/glClearColor 0 0 0 0)
  (GL11/glViewport 0 0 window-width window-height))

(defn ui-loop [world]
  (while (not (Display/isCloseRequested))
    (clear-display)
    (render-planets world)
    (render-players world)
    (render-bullets world)
    (Display/update)))

(defn clear-display []
  (GL11/glClear (bit-or GL11/GL_COLOR_BUFFER_BIT GL11/GL_DEPTH_BUFFER_BIT)))

(defn render-planets [world]
  (doseq [planet (:planets world)]
    (render-planet planet)))

(defn render-players [world]
  (doseq [player (:players world)]
    (render-player player)))

(defn render-bullets [world]
  (doseq [bullet (:bullets world)]
    (render-bullet bullet)))

(defn normalize-x [x]
  (/ x window-width))

(defn normalize-y [y]
  (/ y window-height))

(defn space-point-to-display [point]
  (point2.
    (- (* 2 (normalize-x (* (:x point) scale))) 1)
    (- (* 2 (normalize-y (* (:y point) scale))) 1)))

(defn render-planet
  "A planet now renders as green rectangle."
  [planet]
  (GL11/glColor3f 0 1 0)
  (let [center (space-point-to-display (:center planet))
        center-x (:x center)
        center-y (:y center)
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
        center-x (:x center)
        center-y (:y center)
        x1 (- center-x (normalize-x 2))
        y1 (- center-y (normalize-y 2))
        x2 (+ center-x (normalize-x 2))
        y2 (+ center-y (normalize-x 2))]
    (GL11/glRectf x1 y1 x2 y2)))

(defn render-bullet [bullet]
  "A bullet now renders as a... guess what? Right, red rectangle!"
  (GL11/glColor3f 1 0 0)
  (let [center (space-point-to-display (:center bullet))
        center-x (:x center)
        center-y (:y center)
        x1 (- (normalize-x 1) center-x)
        y1 (- (normalize-y 1) center-y)
        x2 (+ (normalize-x 1) center-x)
        y2 (+ (normalize-y 1) center-y)]
    (GL11/glRectf x1 y1 x2 y2)))
