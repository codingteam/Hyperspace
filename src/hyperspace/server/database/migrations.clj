(ns hyperspace.server.database.migrations
  (:require [clj-liquibase.change :as ch])
  (:use [clj-liquibase.core :only (defchangelog)]))

(def add-users-table
  (ch/create-table :users
                   [[:id :int :null false :pk true :autoinc true]
                    [:name [:varchar 40] :null false]
                    [:password [:varchar 40] :null false]]))

(def changeset-1 ["id=1" "author=ForNeVeR" [add-users-table]])

(defchangelog changelog "hyperspace-server" [changeset-1])