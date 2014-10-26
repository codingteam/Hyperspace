(ns hyperspace.server.config
  (:import (java.io PushbackReader))
  (:require [clojure.java.io :as io]))

(defn -from-edn
  [fname]
  (with-open [rdr (-> (io/reader fname)
                      PushbackReader.)]
    (clojure.edn/read rdr)))

(def config (-from-edn "config.edn"))