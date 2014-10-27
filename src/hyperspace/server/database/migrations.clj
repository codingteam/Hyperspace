(ns hyperspace.server.database.migrations
  (:require [clj-liquibase.change :as ch])
  (:use [clj-liquibase.core :only (defchangelog)]))

(def add-users-table ["id=1"
                      "author=ForNeVeR"
                      [(ch/create-table :users
                                        [[:id :int :null false :pk true :autoinc true]
                                         [:login [:varchar 40] :null false] ;; TODO: enforce login uniqueness
                                         [:password [:varchar 16] :null false]])]])

(defchangelog changelog "hyperspace-server" [add-users-table])