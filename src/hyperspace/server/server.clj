(ns hyperspace.server.server
  (:require [clojure.contrib.server-socket :as socket]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [hyperspace.server.game :as game]))

(def +players-in-game+ 2)
(def +width+ 1024)
(def +height+ 768)

(defn new-server []
  (ref {:games {} :last-player-id 0}))

(defn find-free-game [server]
  (dosync
    (let [games-map (:games @server)
          free-game (-> games-map
        (filter #(< (val %) +players-in-game+))
        (first))]
      (if (= free-game nil)
        [(game/new +width+ +height+) 0]
        free-game))))

(defn create-player [server]
  (dosync
    (let [player-id (+ (:last-player-id @server) 1)
          [game count] (find-free-game @server)
          games (:games @server)]
      (ref-set server (assoc server
                        :last-player-id player-id
                        :games (assoc games game (+ count 1))))
      [game player-id])))

(defn get-message []
  (json/read *in* :key-fn keyword))

(defn send-message [message]
  (json/write message *out*))

(defn prepare-turn [message]
  ;; TODO: validation
  message)

(defn socket-handler [server input output]
  (println "Incoming socket connection.")
  (binding [*in* (io/reader input)
            *out* (io/writer output)
            *err* (io/writer System/err)]
    (let [[game player-id] (create-player server)]
      (game/add-player game player-id)
      (loop []
        (let [message (get-message)]
          (if (not (:exit message))
            (let [turn (prepare-turn message)]
              (game/add-turn game player-id turn)
              (let [state (game/get-state game)]
                (send-message state))
              (recur))))))))

(defn start [port]
  (println "Creating server...")
  (let [server (new-server)]
    (println "Listening on port" port)
    (socket/create-server (Integer. port) (partial socket-handler server))))