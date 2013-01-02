(ns hyperspace.client.network)

(defn connect
  "Connects to the server, returns object that can be used to interact with network."
  [host port]
  {:socket nil
   :in     nil
   :out    nil})

(defn disconnect
  "Drops the connection to server."
  [connection]
  nil)

(defn send
  "Serializes and sends the message to the server."
  [connection message]
  nil)

(defn receive
  "Waits for the next message from server, deserializes and returns it."
  [connetion timeout]
  nil)
