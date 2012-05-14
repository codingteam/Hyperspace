(ns hyperspace.main
  (:use [hyperspace game geometry ui world]
        [clojure.pprint :only (pprint)]))

(defn -main
  [& args]
  (start-ui (generate-world 800 600)))