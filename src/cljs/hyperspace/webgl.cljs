(ns hyperspace.webgl)

(defn get-context
  [canvas]
  (.getContext canvas "experimental-webgl"))

(defn clear
  [context]
  (.clear context (.-COLOR_BUFFER_BIT context)))

(defn update
  [context]
;  (Display/update)
;  (Display/sync fps)
  )

(defn ellipse
  [context [x, y] radius segments]
;  (let [angle (/ (* 2 Math/PI) segments)]
;    (GL11/glBegin GL11/GL_POLYGON)
;    (dotimes [i segments]
;      (GL11/glVertex2f (->> i (* angle) Math/cos (* radius) (+ x))
;                       (->> i (* angle) Math/sin (* radius) (+ y))))
;    (GL11/glEnd))
  )