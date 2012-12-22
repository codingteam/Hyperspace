(ns hyperspace.server.main
  (:require [hyperspace.server.server :as server]))

(defn -main [port & args]
  (server/start port))
