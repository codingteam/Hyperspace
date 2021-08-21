(ns hyperspace.client.controls
  (:require [hyperspace.library.misc :as misc]))

(defn turn-right
  [{[player & _] :players
    :as world}
   delta-angle]
  (let [{[angle, _] :heading} player]
    (assoc-in world
      [:players 0 :heading 0]
      (- angle delta-angle))))

(defn turn-left
  [{[player & _] :players
    :as world}
   delta-angle]
  (let [{[angle, _] :heading} player]
    (assoc-in world
      [:players 0 :heading 0]
      (+ angle delta-angle))))

(defn increase-power
  [world delta-power]
  (let [power (get-in world [:players 0 :heading 1])]
    (assoc-in world
      [:players 0 :heading 1]
      (misc/saturation (+ power delta-power)
        1 10))))

(defn decrease-power
  [world delta-power]
  (let [power (get-in world [:players 0 :heading 1])]
    (assoc-in world
      [:players 0 :heading 1]
      (misc/saturation (- power delta-power)
        1 10))))

(defn exit [world]
  (assoc world
    :exit true))
