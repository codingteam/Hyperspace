(ns hyperspace.server.database.user
  (:require [crypto.password.scrypt :as password])
  (:use [azql.core :only [select from insert! values]]
        [hyperspace.server.database.datasource :only [db-spec]]))

(defn create [login password]
  (insert! db-spec :users
           (values [{:login    login
                     :password (password/encrypt password)}])))