(ns hyperspace)

(load "geometry")
(load "world")
(load "gravity")
(load "world")
(load "game")
(load "ui")

(let [world (generate-world)]
  (start-ui world))
