(ns hyperspace.server.main
  (:require [hyperspace.server.server :as server]))

(defn run [port & args]
  (server/start port))
