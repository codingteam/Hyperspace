(ns hyperspace.ui
  (:import [org.lwjgl Sys]
           [org.lwjgl.opengl Display DisplayMode GL11]
           [org.lwjgl.input Mouse Keyboard])
  (:use [hyperspace.library world geometry game]))

(def window-width 800)
(def window-height 600)
(def fps 60)

(defn get-time []
  "Returns current time in milliseconds"
  (/ (* 1000 (Sys/getTime))
     (Sys/getTimerResolution)))

(defn draw-ellipse
  [[x, y] radius segments]
  (let [angle (/ (* 2 Math/PI) segments)]
    (GL11/glBegin GL11/GL_POLYGON)
    (dotimes [i segments]
      (GL11/glVertex2f (->> i (* angle) Math/cos (* radius) (+ x))
                       (->> i (* angle) Math/sin (* radius) (+ y))))
    (GL11/glEnd)))

(defn process-input
  [world]
  (if (and (Keyboard/next)
           (Keyboard/getEventKeyState))
    (let [key (Keyboard/getEventKey)]
      (cond
        (= key Keyboard/KEY_LEFT)
        (turn-left world 0.1)

        (= key Keyboard/KEY_RIGHT)
        (turn-right world 0.1)

        (= key Keyboard/KEY_UP)
        (increase-power world 1)

        (= key Keyboard/KEY_DOWN)
        (decrease-power world 1)

        (= key Keyboard/KEY_ESCAPE)
        (exit world)

        (and (= key Keyboard/KEY_SPACE)
             (not (Keyboard/isRepeatEvent)))
        (fire world)

        :else world))
    world))

(defn render-missile
  [{position :position
    radius :radius
    :as missile}]
  (GL11/glColor3f 1.0 0.0 1.0)
  (draw-ellipse position radius 30)
  missile)

(defn render-planet
  [{position :position
    radius :radius
    :as planet}]
  (GL11/glColor3f 0.0 1.0 0.0)
  (draw-ellipse position radius 30)
  planet)

(defn render-fragment
  [{position :position
    radius :radius
    :as fragment}]
  (GL11/glColor3f 1.0 0.0 0.0)
  (draw-ellipse position radius 30)
  fragment)

(defn render-trace
  [trace]
  (GL11/glColor3f 1.0 1.0 0.0)
  (GL11/glBegin GL11/GL_LINE_STRIP)
  (doseq [[x, y] trace]
    (GL11/glVertex2f x y))
  (GL11/glEnd)
  trace)

(defn render-player
  [{[x, y :as position] :position
    radius              :radius
    [a, d :as heading]  :heading
    :as player}]
  (GL11/glColor3f 0.0 1.0 1.0)
  (draw-ellipse position radius 30)

  (GL11/glColor3f 1.0 1.0 1.0)
  (GL11/glBegin GL11/GL_LINES)
  (let [[hx, hy] (-> (polar->cartesian [a (* d 10)])
                     (vector-sum position))]
    (GL11/glVertex2f x y)
    (GL11/glVertex2f hx hy))
  (GL11/glEnd)
  player)

(defn render-world
  [{planets   :planets
    missiles  :missiles
    fragments :fragments
    traces    :traces
    players   :players
    :as world}]
  (GL11/glClear GL11/GL_COLOR_BUFFER_BIT)
  (doseq [trace traces]       (render-trace trace))
  (doseq [planet planets]     (render-planet planet))
  (doseq [missile missiles]   (render-missile missile))
  (doseq [fragment fragments] (render-fragment fragment))
  (doseq [player players]     (render-player player))
  world)

(defn setup-ui
  [{[x, y]          :position
    [width, height] :size}]

  ;; Create display
  (Display/setDisplayMode (DisplayMode. window-width window-height))
  (Display/create)

  ;; Init OpenGL
  (GL11/glMatrixMode GL11/GL_PROJECTION)
  (GL11/glLoadIdentity)
  (GL11/glOrtho x (+ x width)
                y (+ y height)
                1 -1)
  (GL11/glMatrixMode GL11/GL_MODELVIEW))

(defn ui-loop
  [initial-world]
  (loop [initial-timestamp (get-time)
         accumulated-time  0
         world             initial-world]
    (if (or (Display/isCloseRequested)
            (:exit world))
      (Display/destroy)

      (let [new-timestamp (get-time)
            delta-time    (+ (- new-timestamp initial-timestamp) accumulated-time)
            [new-world remaining-time] (update-world world delta-time)
            final-world (-> new-world process-input
                                      render-world)]
        (Display/update)
        (Display/sync fps)
        (recur new-timestamp remaining-time final-world)))))

(defn start-ui
  [world]
  (setup-ui world)
  (ui-loop world))