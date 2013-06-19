(ns hyperspace.test.client.network
  (:import [java.io BufferedReader
                    InputStreamReader
                    PrintWriter])
  (:use [hyperspace.client.network]
        [hyperspace.test.checkers]
        [midje.sweet])
  (:require [clojure.contrib.server-socket :as socket]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(def fake-server-port 10501)
(def fake-server (socket/create-server
                   fake-server-port
                   (fn [in out]
                     (binding [*in*  (BufferedReader. (InputStreamReader. in))
                               *out* (PrintWriter. out)]
                       (loop []
                         (println (read-line))
                         (recur))))))

(let [connection (connect "localhost" fake-server-port)]
  (facts "about connect function"
    (:socket connection) => truthy
    (:in connection) => truthy
    (:out connection) => truthy
    (.isClosed (:socket connection)) => false)
  (disconnect connection))

(let [connection (connect "localhost" fake-server-port)]
  (disconnect connection)
  (facts "about disconnect function"
    (.isClosed (:socket connection)) => true))

(socket/close-server fake-server)

(def answer (promise))
(def mock-server-port 10502)
(def mock-server (socket/create-server
                   mock-server-port
                   (fn [in out]
                     (let [reader (io/reader in)
                           writer (io/writer out)]
                       (loop []
                         (let [message (json/read reader :key-fn keyword)]
                           (deliver answer message))
                         (recur))))))

(let [connection (connect "localhost" mock-server-port)
      object {:test 1, :field "2"}]
  (send-message connection object)
  (facts "about send-message function"
    (deref answer 15000 nil) => object)
  (println "finished1")
  (disconnect connection)
  (println "finished22"))

(socket/close-server mock-server)
(println "finished23")

(def echo-server-port 10503)
(def echo-server (socket/create-server
                   echo-server-port
                   (fn [in out]
                     (let [reader (io/reader in)
                           writer (io/writer out)]
                       (loop []
                         (let [c (.read reader)]
                           (if (not= c -1)
                             (do
                               (println "r" (char c))
                               (.write writer c)
                               (.flush writer)
                               (recur)))))))))

(let [connection (connect "localhost" echo-server-port)
      message {:a 1, :b 2}]
  (println "finished11")
  (send-message connection message)
  (println "finished14")
  (facts "about receive-message function"
    (receive-message connection) => {:a 1, :b 2})
  (println "finished12")
  (disconnect connection))

(println "finished13")
(socket/close-server echo-server)
