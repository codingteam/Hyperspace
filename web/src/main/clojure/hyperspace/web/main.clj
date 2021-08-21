(ns hyperspace.web.main
  (:require [hyperspace.swagger.server :as swagger])
  (:gen-class))

(defn -main []
  (swagger/start))
