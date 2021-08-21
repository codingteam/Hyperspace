Hyperspace [![Status Umbra][status-umbra]][andivionian-status-classifier]
==========

Hyperspace is a [Slingshot][slingshot] clone written in Clojure.

![Gameplay Footage][gameplay]

## Development requirements

- JDK 8 (not a newer one, yet)

## Desktop client

To run the standalone game client use the following command:

```console
$ ./gradlew client:run
```

## Web client

To run the web version of the game, use the following commands:

```console
$ ./gradlew web:frontend:build
```

Then open a file `web/frontend/build/www/index.html` using a web browser.

## Game server

### Configuring
All configuration data is stored in the `config.edn` file.

### Running

To start the game server web interface, run the following command in your shell:

```console
$ ./gradlew run --args swagger
```

### Protocol
Please check the API documentation in your browser. Visit the URL http://localhost:3030/ after the server
was started.

## Testing

To execute the test suite, run the following command in your shell:

```console
$ ./gradlew test
```

### Logging
For debugging purposes you might want to enable trace logging. To do that, open file `src/log4j.properties` and replace
`DEBUG` with `TRACE`.

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-umbra-
[slingshot]: https://web.archive.org/web/20120226132228/http://slingshot.wikispot.org/

[status-umbra]: https://img.shields.io/badge/status-umbra-red.svg

[gameplay]: docs/footage.gif
