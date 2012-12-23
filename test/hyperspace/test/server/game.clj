(ns hyperspace.test.server.game
  (:use clojure.test)
  (:require [hyperspace.library.geometry :as geometry]
            [hyperspace.library.world :as world]
            [hyperspace.server.game :as game]))

(defn set-world
  "Set the world for game."
  [game world]
  (send game
    (fn [game]
      (assoc game
        :world world))))

(deftest add-player-test
  (testing "Add player to game"
    (let [game (game/create 800 600)]
      (game/add-player game "player1")
      (await game)
      (is (= (:player-ids @game) #{"player1"}) "added player identifier is equal to source")
      (game/add-player game "player2")
      (await game)
      (is (= (:player-ids @game) #{"player1" "player2"}) "player 2 identifier is equal to source"))))

(deftest add-turn-test
  (testing "Add turn to game"
    (let [game (game/create 800 600)
          player1 "player1"
          player2 "player2"
          turn1 {:heading 1, :power 1}
          turn2 {:heading 2, :power 2}]
      (game/add-player game player1)
      (game/add-player game player2)
      (game/add-turn game player1 turn1)
      (await game)
      (is (= (:turns @game) {player1 turn1}) "player 1 turn set")
      (game/add-turn game player2 turn2)
      (await game)
      (is (= (:turns @game) {player1 turn1, player2 turn2}) "player 2 turn set")
      (game/add-turn game player1 turn2)
      (await game)
      (is (= (:turns @game) {player1 turn2, player2 turn2}) "player 1 turn reset"))))

(deftest get-state-test
  (testing "Testing game state"
    (let [game (game/create 800 600)
          player1 (world/make-player [0 0])
          player2 (world/make-player [100 100])
          world (assoc (world/create 800 600)
                  :players [player1 player2])
          player1-id "player1"
          player2-id "player2"
          turn1 {:heading (/ Math/PI 4), :power 30}
          turn2 {:heading 0, :power 30}]
      ;; Replace the world with special prepared object:
      (set-world game world)

      (game/add-player game player1-id)
      (game/add-player game player2-id)
      (game/add-turn game player1-id turn1)
      (game/add-turn game player2-id turn2)
      (let [[world1 targets] (game/get-state game)]
        (is (= (:players world1) [player1
                                  (assoc player2
                                    :status :dead)]) "player 2 should be dead")
        (is (= targets [nil player2]))))))

(deftest planet-shot-test
  (let [game (game/create 800 600)
        world (:world @game)
        player1id "player1"
        player2id "player2"
        planet (first (:planets world))
        players (:players world)
        player1 (first players)
        player2 (second players)
        turn1 {:heading (geometry/heading player1 planet)
               :power 5}]
    (game/add-player game player1id)
    (game/add-player game player2id)
    (await game)
    (let [[world1 targets] (game/process-turn @game [world []] [player1id turn1])]
      (is (= world1 world) "world should not change")
      (is (= targets [planet])))))

;;; TODO: game/process-turn tests:
;;; 1) shot into enemy;
;;; 2) shot into space.
