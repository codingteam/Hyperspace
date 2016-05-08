(ns hyperspace.main
  (:require [hyperspace.client.main :as client]
            [hyperspace.server.server :as server]))

(defn -main [application & args]
  (cond
    (= application "client") (apply client/run args)
    (= application "server") (apply server/start args)))
