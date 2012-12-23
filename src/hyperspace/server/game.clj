(ns hyperspace.server.game
  (:require [hyperspace.library.game :as game]
            [hyperspace.library.world :as world]))

(defn new [width height]
  (agent {:player-ids []
          :world (world/generate width height)
          :turns []}))

(defn add-player [game player-id]
  (send game (fn [game]
               (let [players (:player-ids game)]
                 (assoc game
                   :player-ids (conj players player-id))))))

(defn add-turn [game player-id turn]
  (send game (fn [game]
               (let [players (:player-ids game)
                     turns   (conj (:turns game) turn)]
                 (if (= (count turns)
                        (count players))
                   (let [world (:world game)
                         player (first :players world)] ; TODO: get proper player; not first
                     (game/fire world player)
                     (let [world (game/instant-update-world world)]
                       (assoc game
                         :world world
                         :turns [])))
                   (assoc game
                     :turns turns))))))

(defn get-state [game]
  ;; TODO: block thread until turns != []
  (:world @game))
