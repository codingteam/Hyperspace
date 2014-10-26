(ns hyperspace.server.database.datasource
  (:require [clj-liquibase.cli :as cli]
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
