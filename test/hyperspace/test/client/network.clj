(ns hyperspace.test.client.network
  (:import [java.io BufferedReader
                    InputStreamReader
                    PrintWriter])
  (:use [hyperspace.client.network]
        [hyperspace.test.checkers]
        [midje.sweet])
  (:require [clojure.contrib.server-socket :as socket]))

(def test-server-port 10501)
(def server-fake (socket/create-server
                   test-server-port
                   (fn [in out]
                     (binding [*in*  (BufferedReader. (InputStreamReader. in))
                               *out* (PrintWriter. out)]
                       (loop []
                         (println (read-line))
                         (recur))))))

(let [connection (connect "localhost" test-server-port)]
  (facts "about connect function"
    (:socket connection) => truthy
    (:in connection) => truthy
    (:out connection) => truthy)
  (disconnect connection))

;;; TODO: test the disconnect function.
;;; TODO: test the send-message function.
;;; TODO: test the receive-message function.
