(ns hyperspace.test.server.game
  (:use clojure.test)
  (:require [hyperspace.server.game :as game]))

(deftest add-player-test
  (testing "Player adding to game"
    (let [game (game/new 800 600)]
      (game/add-player game "player1")
      (await game)
      (is (= (:player-ids @game) ["player1"]) "added player identifier is equal to source")
      (game/add-player game "player2")
      (await game)
      (is (= (:player-ids @game) ["player1" "player2"]) "player 2 identifier is equal to source"))))
