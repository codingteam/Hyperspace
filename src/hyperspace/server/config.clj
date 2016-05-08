(ns hyperspace.server.config
  (:import (java.io PushbackReader))
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defn -from-edn
  [fname]
  (with-open [rdr (-> (io/reader fname)
                      PushbackReader.)]
    (edn/read rdr)))

(def config (-from-edn "config.edn"))
