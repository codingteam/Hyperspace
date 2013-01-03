(ns hyperspace.client.network
  (:import java.net.Socket)
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn connect
  "Connects to the server, returns object that can be used to interact with network."
  [host port]
  (let [socket (Socket. host port)
        in     (.getInputStream socket)
        out    (.getOutputStream socket)]
    {:socket socket
     :in     (io/reader in)
     :out    (io/writer out)}))

(defn disconnect
  "Drops the connection to server."
  [{socket :socket
    in     :in
    out    :out}]
  (.close in)
  (.close out)
  (.close socket))

(defn send-message
  "Serializes and sends the message to the server."
  [{writer :out} message]
  (json/write message writer))

(defn receive-message
  "Waits for the next message from server, deserializes and returns it."
  [connetion timeout]
  nil)
