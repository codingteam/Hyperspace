(ns hyperspace.server.database.user
  (:import (java.util UUID))
  (:require [crypto.password.scrypt :as password]
            [crypto.random :as random])
  (:use [azql.core]
        [hyperspace.server.database.datasource :only [db-spec]]))

(defn create [{login :login password :password}]
  (insert! db-spec :users
           (values [{:login    login
                     :password (password/encrypt password)}])))

(defn- generate-session []
  ;; 8 random bytes = 16 hex digits should be enough for duplicates to be almost impossible with probability of about
  ;; 10^-12
  (random/hex 8))

(defn login [{login :login password :password}]
  (with-connection [c db-spec]
    (transaction c
      (let [user (fetch-one c
                   (select (fields [:login :password])
                           (from :u :users)
                           (where (= :u.login login))))
            user-id (:id user)]
        (if (password/check password (:password user))
          (let [session (generate-session)]
            (update! c :users
              (setf :session session)
              (where (= :id user-id)))
            session)
          nil)))))