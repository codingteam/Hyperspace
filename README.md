Hyperspace [![Status Umbra][status-umbra]][andivionian-status-classifier] [![Build Status][travis-build-status]][travis-build]
==========

Hyperspace is a [Slingshot][slingshot] clone written in Clojure.

![Gameplay Footage][gameplay]

## Development requirements

- JDK 8 (not a newer one, yet)
- Leiningen 2.9.2

## Game client

To run the standalone game client use one of the following commands:

```console
$ lein run client
$ lein run
```

Alternatively:

```console
$ ./gradlew run client
```

## Game server

### Configuring
All configuration data is stored in the `config.edn` file.

### Running
Use `lein ring server` to run game server.

Alternatively:

```console
$ ./gradlew run swagger
```

### Protocol
Please check the API documentation in your browser. Visit the URL `http://localhost:3000/index.html` after the server
was started.

## Testing

Execute the tests with `lein`:

    lein test

### Logging
For debugging purposes you might want to enable trace logging. To do that, open file `src/log4j.properties` and replace
`DEBUG` with `TRACE`.

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-umbra-
[travis-build]: https://travis-ci.org/codingteam/Hyperspace
[slingshot]: https://web.archive.org/web/20120226132228/http://slingshot.wikispot.org/

[status-umbra]: https://img.shields.io/badge/status-umbra-red.svg
[travis-build-status]: https://travis-ci.org/codingteam/Hyperspace.svg?branch=develop

[gameplay]: docs/footage.gif
