(ns hyperspace.main
  (:require [hyperspace.server.server :as server]
            [hyperspace.swagger.server :as swagger])
  (:gen-class))

(defn -main [& args]
  (let [[application & app-args] args]
    (cond
      (= application "server") (apply server/start app-args)
      (= application "swagger") (swagger/start))))
