(ns hyperspace.server.database
  (:import [org.h2.jdbcx JdbcConnectionPool]
           [com.googlecode.flyway.core Flyway]))

(def dataSource (JdbcConnectionPool/create
                  "jdbc:h2:hyperspace-server;DB_CLOSE_DELAY=-1"
                  "sa"
                  ""))

(defn migrate []
  (let [flyway (doto (Flyway.)
                 (.setInitOnMigrate true)
                 (.setDataSource dataSource))]
    (.migrate flyway)))
