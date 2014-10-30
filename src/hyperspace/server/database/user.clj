(ns hyperspace.server.database.user
  (:require [crypto.password.scrypt :as password])
  (:use [azql.core :only [select from insert! values]]
        [hyperspace.server.database.datasource :only [db-spec]]))

(defn create [{login :login password :password}]
  (insert! db-spec :users
           (values [{:login    login
                     :password (password/encrypt password)}])))

(defn login [{login :login password :password}]
  ;; TODO: log in and save session
  nil)