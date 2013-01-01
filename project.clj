(defproject hyperspace "1.0.0-SNAPSHOT"
  :description "Simple 2D game written in Clojure."
  :dependencies [[log4j/log4j "1.2.17"]
                 [org.clojars.rexim/jinput-platform-natives "2.0.5"]
                 [org.clojars.rexim/lwjgl-platform-natives "2.8.4"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.2.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.lwjgl.lwjgl/lwjgl "2.8.4"]]
  :dev-dependencies [[midje "1.4.0"]
                     [lein-midje "1.0.10"]]
  :main hyperspace.main)
