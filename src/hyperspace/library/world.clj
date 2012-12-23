(ns hyperspace.library.world
  (:use [clojure.pprint :only (pprint)]
        [clojure.java.io :only (reader writer)]
        [hyperspace.library geometry misc]))

(def missile-radius 5)
(def player-radius 15)
(def missile-mass 10)

(def amount-of-planets 3)
(def amount-of-players 2)

(defn generate-without-intersection
  "Generates new object (player, planet etc). fgen should be 0-arity function"
  [{:keys [planets] :as world} fgen]
  (first
   (remove #(circle-X-any-circle? % planets)
           (repeatedly fgen))))

;;; Planets related stuff

(defn make-planet
  [position radius]

  {:position position
   :radius radius
   :mass (* (Math/sqrt radius)
            (Math/pow 10 7))})

(defn generate-random-planet
  [{planets         :planets
    [x, y]          :position
    [width, height] :size
    :as world}]
  (let [radius (-> (min width height) (/ 5) rand)
        x      (rand-range (+ x radius) (- (+ x width) radius))
        y      (rand-range (+ y radius) (- (+ y height) radius))]
    (make-planet [x y] radius)))

(defn add-random-planet
  [world]
  (let [p (generate-without-intersection world (partial generate-random-planet world))]
    (update-in world [:planets] conj p)))

(defn generate-planets
  [world]
  (-> (iterate add-random-planet world)
      (nth amount-of-planets)))

;;; Missles related stuff

(defn make-missile
  [position velocity trace-index]

  {:position position
   :velocity velocity
   :radius missile-radius
   :mass missile-mass
   :trace-index trace-index})

(defn add-missile
  [{missiles :missiles
    traces   :traces
    :as world}
   position velocity]
  (let [new-missile (make-missile position
                                  velocity
                                  (count traces))]
    (assoc world
      :missiles (conj missiles new-missile)
      :traces (conj traces []))))

;;; Fragments related stuff

(defn make-fragment
  [position velocity radius]

  {:position position
   :velocity velocity
   :radius radius
   :mass radius})

;;; Players related stuff

(defn make-player
  [position heading]

  {:position position
   :radius player-radius})

(defn generate-random-player
  [{[x, y]          :position
    [width, height] :size
    players         :players
    :as world}]
  (let [x (rand-range (+ x player-radius) (- (+ x width) player-radius))
        y (rand-range (+ y player-radius) (- (+ y height) player-radius))]
    (make-player [x y] [0 3.0])))

(defn add-random-player
  [world]
  (let [player (generate-without-intersection world (partial generate-random-player world))]
    (update-in world [:players] conj player)))

(defn generate-players
  [world]
  (-> (iterate add-random-player world)
      (nth amount-of-players)))

;;; World related stuff

(defn generate
  [width height]
  (->> {:position [0, 0]
        :size [width, height]
        :players []
        :planets ()
        :missiles ()
        :fragments ()
        :traces []
        :exit false}
       generate-players
       generate-planets))