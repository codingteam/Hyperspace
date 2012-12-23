(ns hyperspace.main
  (:use [hyperspace.client :as client]
        [hyperspace.server :as server]))

(defn -main [application & args]
  (cond
    (= application "client") (apply client/run args)
    (= application "server") (apply server/run args)))
