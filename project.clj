(defproject hyperspace "1.0.0-SNAPSHOT"
  :description "Simple 2D game written in Clojure."
  :dependencies [[com.googlecode.flyway/flyway-core "2.3"]
                 [com.h2database/h2 "1.3.173"]
                 [log4j/log4j "1.2.17"]
                 [midje "1.5.0"]
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.2.2"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.clojars.rexim/jinput-platform-natives "2.0.5"]
                 [org.ru.codingteam/lwjgl-platform-natives "2.9.1"]
                 [org.lwjgl.lwjgl/lwjgl "2.9.1"]]
  :main hyperspace.main)
