(ns hyperspace.client.main
  (:require [hyperspace.client.ui :as ui]
            [hyperspace.library.world :as world]))

(defn run [& args]
  (ui/start (world/generate 1024 768)))
