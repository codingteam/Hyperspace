(ns hyperspace.client.main
  (:use [hyperspace.client.ui :as ui]
        [hyperspace.library.world :as world]))

(defn run [& args]
  (start-ui (world/generate 1024 768)))