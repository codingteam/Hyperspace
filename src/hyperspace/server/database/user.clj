(ns hyperspace.server.database.user
  (:require [crypto.password.scrypt :as password])
  (:use [hyperspace.server.database.datasource :only [datasource]]
        [azql.core :only [insert! values]]))

(defn create [login password]
  '())

;;
;;
;;  (insert! datasource :user
;;           (values [{:login    login
;;                     :password (password/encrypt password)}]))