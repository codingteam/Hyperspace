Contributor Guide
=================

## Development requirements

- JDK 16

## Desktop client

To run the standalone game client, use the following shell command:

```console
$ ./gradlew client:run
```

## Web client

To run the web version of the game, use the following shell command:

```console
$ ./gradlew web:frontend:build
```

Then open a file `web/frontend/build/www/index.html` using a web browser.

## Game server

To run the game server, use the following shell command:

```console
$ ./gradlew web:run
```

## Testing

To execute the test suite, run the following command in your shell:

```console
$ ./gradlew test
```

### Logging
For debugging purposes you might want to enable trace logging. To do that, open file `src/log4j.properties` and replace
`DEBUG` with `TRACE`.
