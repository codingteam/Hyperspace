(ns hyperspace.test.server.game
  (:use clojure.test)
  (:require [hyperspace.server.game :as game]))

(defn set-world
  "Set the world for game."
  [game world]
  (send game
    (fn [game]
      (assoc game
        :world world))))

(deftest add-player-test
  (testing "Add player to game"
    (let [game (game/new 800 600)]
      (game/add-player game "player1")
      (await game)
      (is (= (:player-ids @game) #{"player1"}) "added player identifier is equal to source")
      (game/add-player game "player2")
      (await game)
      (is (= (:player-ids @game) #{"player1" "player2"}) "player 2 identifier is equal to source"))))

(deftest add-turn-test
  (testing "Add turn to game"
    (let [game (game/new 800 600)
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
