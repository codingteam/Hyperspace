(defproject hyperspace "1.0.0-SNAPSHOT"
  :description "Simple 2D game written in Clojure."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.lwjgl.lwjgl/lwjgl "2.9.1"]
                 [org.ru.codingteam/lwjgl-platform-natives "2.9.1"]
                 [org.clojars.rexim/jinput-platform-natives "2.0.5"]]
  :dev-dependencies [[midje "1.4.0"]
                     [lein-midje "1.0.10"]]
  :main hyperspace.main)
