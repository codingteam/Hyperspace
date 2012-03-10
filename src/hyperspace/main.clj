(ns hyperspace.main
  (:use (hyperspace world
                    ui)))

(defn -main [& args]
  (let [world (generate-world)]
    (start-ui world)))
