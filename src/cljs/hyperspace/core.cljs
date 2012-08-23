(ns hyperspace.core
  (:require [hyperspace.webgl :as webgl]
            [hyperspace.render :as render]
            [hyperspace.game :as game]
            [hyperspace.world :as world]))

(defn get-time
  []
  (.now js/Date))

(defn process-input
  [world]
; TODO: process keys
  world)

(defn webgl-loop
  [canvas context initial-world]
  (def timestamp-atom         (atom (get-time)))
  (def accumulated-time-atom  (atom 0))
  (def world-atom             (atom initial-world))
  (def render (fn []
    (let [timestamp (deref timestamp-atom)
          accumulated-time (deref accumulated-time-atom)
          world (deref world-atom)
          new-timestamp (get-time)
          delta-time    (+ (- new-timestamp timestamp) accumulated-time)
          [new-world remaining-time] (game/update-world world delta-time)
          final-world (process-input new-world)]
      (render/render-world context final-world)
      (reset! timestamp-atom        new-timestamp)
      (reset! accumulated-time-atom remaining-time)
      (reset! world-atom            final-world))
    (js/requestAnimFrame render canvas)))
    (render))

;; Run main function:
(set! (.-onload js/window)
      (fn []
        (let [canvas  (.getElementById js/document "canvas")
              context (webgl/get-context canvas)
              world   (world/generate-world 800 600)]
          (webgl/init context)
          (webgl-loop canvas context world))))
