(ns hyperspace.server.database.datasource
  (:require [azql.emit :as emit]
            [azql.dialect :as dialect]
            [clj-liquibase.cli :as cli]
            [hyperspace.server.database.migrations :as migrations])
  (:use [hyperspace.server.config :only [config]])
  (:import [org.h2.jdbcx JdbcConnectionPool]))

(def datasource
  (let [{url :url
         user :user
         password :password} (:database config)
        ds (JdbcConnectionPool/create url user password)]
    (cli/update {:datasource ds
                 :changelog  migrations/changelog})
    ds))

(dialect/register-dialect ::h2)
(defmethod dialect/guess-dialect :h2 [_] ::h2)
(defmethod emit/quote-name ::h2 [name] (str name))

(def db-spec {:datasource datasource})