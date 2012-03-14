(ns hyperspace.misc)

(defn rand-range
  "Returns a random integer between low (inclusive) and high (inclusive)"
  [low high]
  (+ low (rand-int (+ 1 (- high low)))))
