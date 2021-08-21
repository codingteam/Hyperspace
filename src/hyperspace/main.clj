(ns hyperspace.main
  (:require [hyperspace.swagger.server :as swagger])
  (:gen-class))

(defn -main [& args]
  (let [[application & app-args] args]
    (cond
      (= application "swagger") (swagger/start))))
