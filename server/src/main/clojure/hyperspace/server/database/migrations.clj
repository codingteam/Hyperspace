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

(defchangelog changelog "hyperspace-server" [add-users-table])
