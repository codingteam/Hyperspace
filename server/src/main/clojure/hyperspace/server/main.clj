(ns hyperspace.server.main
  (:require [hyperspace.server.server :as server])
  (:gen-class))

(defn -main [& args]
  (apply server/start args))
