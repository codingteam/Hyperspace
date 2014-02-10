(ns hyperspace.server.main
  (:require [hyperspace.server.server :as server]
            [hyperspace.server.database :as database]))

(defn run [port & args]
  (database/migrate)
  (server/start port))
