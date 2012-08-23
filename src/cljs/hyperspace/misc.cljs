(ns hyperspace.misc)

(defn rand-range
  "Returns a random integer between low (inclusive) and
  high (inclusive)"
  [low high]
  (-> (- high low)
      (+ 1)
      rand-int
      (+ low)))

(defn saturation
  "Ensures that the value is not going out of bounds [low, high]"
  [value low high]
  (cond
    (< value low) low
    (< high value) high
    :else value))