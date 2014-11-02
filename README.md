Hyperspace
==========

Hyperspace is a [Slingshot](http://slingshot.wikispot.org/) clone written in Clojure.

## Disclaimer
The game is under heavy development. Nothing works here. Move away!

## Game client
Use `lein run client` to run the standalone game client.

## Game server

### Configuring
All configuration data is stored in the `config.edn` file.

### Running
Use `lein ring server` to run game server.

### Protocol
Hyperspace protocol is very simple. All messages are transferred in the JSON format.

1. Client connects to the server.
2. Server searches for the game for client.
3. Main game loop:
* server sends the game state to every client;
* every client sends their turns to server.

#### Server message format
TODO.

#### Client message format
TODO.

## Testing
`lein midje` will run all the tests.

### Logging
For debugging purposes you might want to enable trace logging. To do that, open file `src/log4j.properties` and replace
`DEBUG` with `TRACE`.
