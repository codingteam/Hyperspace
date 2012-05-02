(ns hyperspace.main
  (:use (hyperspace ui
                    world)))

(defn -main
  [& args]
  (let [world (generate-world)]
    (start-ui world)))
