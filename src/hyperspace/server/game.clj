(ns hyperspace.server.game
  (:require [hyperspace.library.game :as game]
            [hyperspace.library.world :as world]))

(defn new [width height]
  (agent {:player-ids #{}
          :world (world/generate width height)
          :turns {}}))

(defn add-player [game player-id]
  (send game (fn [game]
               (let [players (:player-ids game)]
                 (assoc game
                   :player-ids (conj players player-id))))))

(defn add-turn
  "Accepts game, player id and turn. Turn is a map {:heading rad, :power p}"
  [game player-id turn]
  (send game
    (fn [game]
      (let [players (:player-ids game)
            turns   (:turns game)]
        (if (contains? players player-id)
          (assoc game
            :turns (assoc turns
                     player-id turn))
          (do
            (println "Attempt to make turn from non-existent player" player-id)
            game))))))

(defn get-state [game]
  ;; TODO: block thread until turns != []
  ;; TODO: apply turns
  (:world @game))
