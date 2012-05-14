(ns hyperspace.world
  (:use [clojure.pprint :only (pprint)]
        [clojure.java.io :only (reader writer)]
        [hyperspace.misc]))

(def missile-radius 5)
(def player-radius 15)
(def missile-mass 10)

(def amount-of-planets 3)
(def amount-of-players 2)
(def amount-of-missiles 0)

;;; Planets related stuff

(defn make-planet
  [position radius]

  {:position position
   :radius radius
   :mass (* (Math/sqrt radius)
            (Math/pow 10 7))})

(defn add-random-planet
  [{planets         :planets
    [width, height] :size
    :as world}]
  (let [radius (-> (min width height) (/ 4) rand)
        x      (rand-range radius (- width radius))
        y      (rand-range radius (- height radius))]
    (assoc world
      :planets (conj planets (make-planet [x, y] radius)))))

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

(defn add-random-missile
  [{[width, height] :size
    :as world}]
  (let [x (rand-range missile-radius (- width missile-radius))
        y (rand-range missile-radius (- height missile-radius))
        vx (rand-range -300 300)
        vy (rand-range -300 300)]
    (add-missile world [x, y] [vx, vy])))

(defn generate-missiles
  [world]
  (-> (iterate add-random-missile world)
      (nth amount-of-missiles)))

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
   :heading heading
   :radius player-radius})

(defn add-random-player
  [{[width, height] :size
    players         :players
    :as world}]
  (let [x (rand-range player-radius (- width player-radius))
        y (rand-range player-radius (- height player-radius))]
    (assoc world
      :players (conj players (make-player [x, y] [0, 3.0])))))

(defn generate-players
  [world]
  (-> (iterate add-random-player world)
      (nth amount-of-players)))

;;; World related stuff

(defn generate-world
  [width height]
  (->> {:position [0, 0]
        :size [width, height]
        :players []
        :planets ()
        :missiles ()
        :fragments ()
        :traces []}
       generate-players
       generate-planets
       generate-missiles))