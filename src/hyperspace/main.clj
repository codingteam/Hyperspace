(ns hyperspace.main
  (:use [hyperspace game geometry ui world]
        [clojure.pprint :only (pprint)]))

(defn -main
  [& args]
  (start-ui (generate-world 1024 768)))