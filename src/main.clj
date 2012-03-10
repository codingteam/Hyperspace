(ns hyperspace)

(load "geometry")
(load "world")
(load "gravity")
(load "game")
(load "ui")

(let [world (generate-world)]
  (start-ui world))
