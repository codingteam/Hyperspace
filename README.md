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
Please check the API documentation in your browser. Visit the URL `http://localhost:3000/index.html` after the server
was started.

## Testing
`lein midje` will run all the tests.

### Logging
For debugging purposes you might want to enable trace logging. To do that, open file `src/log4j.properties` and replace
`DEBUG` with `TRACE`.
