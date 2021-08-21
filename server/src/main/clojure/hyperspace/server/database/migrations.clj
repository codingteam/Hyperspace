(ns hyperspace.server.database.migrations
  (:require [clj-liquibase.change :as ch])
  (:use [clj-liquibase.core :only (defchangelog)]))

(def add-users-table ["id=1"
                      "author=ForNeVeR"
                      [(ch/create-table :users
                                        [[:id :int :null false :pk true :autoinc true]
                                         [:login [:varchar 128] :unique true :null false]
                                         [:password [:varchar 128] :null false]
                                         [:session [:varchar 16] :null true :unique true]])
                       (ch/create-index :users [:login])]])

(def add-games-table ["id=2"
                      "author=ForNeVeR"
                      [(ch/create-table :games
                                        [[:id :int :null false :pk true :autoinc true]
                                         [:players :int :null false]
                                         [:state [:varchar 16]]])
                       (ch/create-index :games [:state])
                       (ch/create-table :game-users
                                        [[:user-id :int
                                          :references "users(id)" :foreign-key-name :game-users-users]
                                         [:game-id :int
                                          :references "games(id)" :foreign-key-name :game-users-games]])]])

(defchangelog changelog "hyperspace-server" [add-users-table
                                             add-games-table])
