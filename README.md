Hyperspace [![Status Umbra][status-umbra]][andivionian-status-classifier]
==========
Hyperspace is a network multiplayer game designed for two players, a re-implementation of [Slingshot][slingshot]. It consists of a dedicated server application and a client application.

Game Description
----------------
![Gameplay Footage][gameplay]

Hyperspace is a turn-based physic simulator game. There are two players and a number of planets on the game field. Each player shoot a missile during each turn (but he could choose to skip the turn; that could also happen due to turn timeout); missiles behave as a bodies of mass in a static environment with Newtonian physics. Only missiles and planets have mass; missiles, planets and players also have a collision radius that should be taken into account by the simulation.

If the player is hit by enemy's missile, he's dead. If the missile hits the planet surface, then the missile explodes. If the missile hits the game world boundaries, it's considered "lost".

Player controls the shot power (in some diapason allowed by server) and direction.

The player turns are performed simultaneously. If both of the players are hit by enemy missiles at the same turn, the game is a tie.

Current Project Status
----------------------
- [ ] Server implementation (ongoing)
- [ ] Client re-implementation
- [x] Obsolete local client (to be removed after the re-implementation)

Usage
-----
For now, only run from sources is supported. See the [Contributor Guide][docs.contributing] for the information on that.

Documentation
-------------
- [Contributor Guide][docs.contributing]
- [License (MIT)][docs.license]

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-umbra-
[docs.contributing]: CONTRIBUTING.md
[docs.license]: LICENSE.md
[gameplay]: docs/footage.gif
[slingshot]: https://web.archive.org/web/20120226132228/http://slingshot.wikispot.org/
[status-umbra]: https://img.shields.io/badge/status-umbra-red.svg
