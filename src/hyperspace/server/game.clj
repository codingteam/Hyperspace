(ns hyperspace.server.game
  (:use clojure.tools.logging)
  (:require [hyperspace.library.simulation :as simulation]
            [hyperspace.library.world :as world]))

(defn get-player-by-id
  "Finds game player by its identifier."
  [game player-id]
  (debug "searching player" player-id "in" game)
  (let [index (->> (:player-ids game)
                   (map-indexed vector)
                   (some (fn [[i p]] (if (= p player-id) i nil))))
        player (nth (:players (:world game)) index)]
    player))

(defn process-turn
  "Processes the player turn. Returns world after turn processed."
  [game [world targets] [player-id {heading :heading
                                    power :power
                                    :as turn}]]
  (debug "processing turn" turn)
  (let [player (get-player-by-id game player-id)
        [world target] (simulation/fire world player heading power)]
    [world (conj targets target)]))

;;; Public contract:

(defn create [width height]
  (agent {:player-ids (sorted-set)
          :world (world/generate width height)
          :turns {}
          :targets []}
    :error-handler (fn [game exception] (.printStackTrace exception))))

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
            (warn "Attempt to make turn from non-existent player" player-id)
            game))))))

(defn get-state [game]
  (loop []
    (await game)
    (if (not= (count (:turns @game)) 2)
      (recur)))
  (send game
    (fn [game]
      (debug "Turn processing...")
      (trace "game:" game)
      (let [[world targets] (reduce (partial process-turn game)
                                    [(:world game) []]
                                    (:turns game))]
        (trace "world" world)
        (assoc game
          :world world
          :targets targets))))
  (await game)
  (let [game @game]
    [(:world game) (:targets game)]))
