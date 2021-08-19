(ns hyperspace.main
  (:require [hyperspace.client.main :as client]
            [hyperspace.server.server :as server]
            [hyperspace.swagger.server :as swagger]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn -main [& args]
  (let [[application & app-args] args]
    (cond
      (= application "client") (apply client/run app-args)
      (= application "server") (apply server/start app-args)
      (= application "swagger") (swagger/start)
      :else (apply client/run args))))
