(ns hyperspace.render
  (:require [hyperspace.webgl :as webgl]))

(defn render-missile
  [context
   {position :position
    radius :radius
    :as missile}]
;  (GL11/glColor3f 1.0 0.0 1.0)
  (webgl/ellipse context position radius 30)
  missile)

(defn render-planet
  [context
   {position :position
    radius :radius
    :as planet}]
;  (GL11/glColor3f 0.0 1.0 0.0)
  (webgl/ellipse context position radius 30)
  planet)

(defn render-fragment
  [context
   {position :position
    radius :radius
    :as fragment}]
;  (GL11/glColor3f 1.0 0.0 0.0)
  (webgl/ellipse context position radius 30)
  fragment)

(defn render-trace
  [context
   trace]
;  (GL11/glColor3f 1.0 1.0 0.0)
;  (GL11/glBegin GL11/GL_LINE_STRIP)
;  (doseq [[x, y] trace]
;    (GL11/glVertex2f x y))
;  (GL11/glEnd)
  trace)

(defn render-player
  [context
   {[x, y :as position] :position
    radius              :radius
    [a, d :as heading]  :heading
    :as player}]
;  (GL11/glColor3f 0.0 1.0 1.0)
  (webgl/ellipse context position radius 30)

;  (GL11/glColor3f 1.0 1.0 1.0)
;  (GL11/glBegin GL11/GL_LINES)
;  (let [[hx, hy] (-> (polar->cartesian [a (* d 10)])
;                     (vector-sum position))]
;    (GL11/glVertex2f x y)
;    (GL11/glVertex2f hx hy))
;  (GL11/glEnd)
  player)

(defn render-world
  [context
   {planets   :planets
    missiles  :missiles
    fragments :fragments
    traces    :traces
    players   :players
    :as world}]
  (webgl/clear context)
  (doseq [trace traces]       (render-trace context trace))
  (doseq [planet planets]     (render-planet context planet))
  (doseq [missile missiles]   (render-missile context missile))
  (doseq [fragment fragments] (render-fragment context fragment))
  (doseq [player players]     (render-player context player))
  world)