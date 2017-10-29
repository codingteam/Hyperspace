(ns hyperspace.main
  (:require [hyperspace.client.main :as client]
            [hyperspace.server.server :as server]))

(defn -main [& args]
  (let [[application & app-args] args]
    (cond
      (= application "client") (apply client/run app-args)
      (= application "server") (apply server/start app-args)
      :else (apply client/run args))))
