(ns hyperspace.client.network
  (:import java.net.Socket))

(defn connect
  "Connects to the server, returns object that can be used to interact with network."
  [host port]
  (let [socket (Socket. host port)
        in     (.getInputStream socket)
        out    (.getOutputStream socket)]
    {:socket socket
     :in     in
     :out    out}))

(defn disconnect
  "Drops the connection to server."
  [connection]
  nil)

(defn send-message
  "Serializes and sends the message to the server."
  [connection message]
  nil)

(defn receive-message
  "Waits for the next message from server, deserializes and returns it."
  [connetion timeout]
  nil)
