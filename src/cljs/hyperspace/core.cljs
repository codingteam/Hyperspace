(ns hyperspace.cljscore
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

(defn webgl-init
  [context]
  )

(defn webgl-loop
  [context initial-world]
  (loop [initial-timestamp (get-time)
         accumulated-time  0
         world             initial-world]
    (let [new-timestamp (get-time)
          delta-time    (+ (- new-timestamp initial-timestamp) accumulated-time)
          [new-world remaining-time] (game/update-world world delta-time)
          final-world (process-input new-world)]
      (render/render-world context final-world)
      (webgl/update context)
      (recur new-timestamp remaining-time final-world))))

;; Run main function:
(set! (.-onload js/window)
      (fn []
        (let [canvas  (.getElementById js/document "canvas")
              context (webgl/get-context canvas)
              world   (world/generate-world 800 600)]
          (webgl-init context)
          (webgl-loop context world))))
