(ns hyperspace.misc)

(defn rand-range
  "Returns a random integer between low (inclusive) and high (inclusive)"
  [low high]
  (+ low (rand-int (+ 1 (- high low)))))

(defn saturation
  [value low high]
  (cond
    (< value low) low
    (< high value) high
    :else value))