Hyperspace
==========
Hyperspace is a [Slingshot](http://slingshot.wikispot.org/) clone written in Clojure.

There is a desktop client and a web client.

Running desktop client
----------------------
Use the following command to download all dependencies and run the game:

    $ lein run

Running web client
------------------
First, compile the clojurescript files:

    $ lein cljsbuild auto

Then, run the development `ring` server:

    $ lein ring server-headless

Point your browser to URL `http://localhost:3000`, and you will see Hyperspace web client.

Running tests
-------------
Use the following command to run all current tests:

    $ lein midje
