(defproject hyperspace "1.0.0-SNAPSHOT"
  :description "Simple 2D game written in Clojure."
  :dependencies [[azql "0.2.0"]
                 [clj-liquibase "0.5.2"]
                 [com.h2database/h2 "1.3.173"]
                 [crypto-password "0.1.3"]
                 [crypto-random "1.2.0"]
                 [log4j/log4j "1.2.17"]
                 [metosin/ring-swagger "0.13.0"]
                 [metosin/compojure-api "0.16.0"]
                 [metosin/ring-http-response "0.5.0"]
                 [metosin/ring-swagger-ui "2.0.17"]
                 [midje "1.5.0"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.2.2"]
                 [org.clojure/tools.logging "0.2.3"]
                 [ru.org.codingteam/jinput-platform-natives "2.0.6"]
                 [ru.org.codingteam/lwjgl-platform-natives "2.9.1"]
                 [org.lwjgl.lwjgl/lwjgl "2.9.1"]]
  :ring {:handler hyperspace.swagger.server/app}
  :uberjar-name "hyperspace-server.jar"
  :profiles {:uberjar {:resource-paths ["swagger-ui"]
                       :aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.8.11"]]}}
  :main hyperspace.main)
