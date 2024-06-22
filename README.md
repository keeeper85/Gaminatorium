# What is Gaminatorium?

**To put it simply:**

Our project is a website/webapplication that focuses on the aggregation, sharing and evaluation of minigames played in the browser.

Gaminatorium is a place where you can come for a moment and play various implementations of card games, board games or any other games, solo or multiplayer.

### But Gaminatorium is also something more.

It's a portal created for programmers, a place where you can view the code of any game and contact its creator. The motto of Gaminatorium is:

### "Experiment. Learn. Play."

We want our website to become a place where you can play with technology, see how something is created, ask about technical details, evaluate the creations of other developers and learn from each other.

---

## Functionalities

- The user can create his own account and personalize profile elements.
- The user can browse the list of currently available games, and after clicking on the selected item, receives information such as: game description, screenshot, link to the game code (GitHub), contact to the author (e.g. LinkedIn) and game's current average rating.
- While logged in, users can run the selected game, after which they will be redirected and served by the website of the given game.
- A logged in user can add their own game to our database using the attached form (provided that the game passes moderation).
- Games provided by Gaminatorium may be stand-alone productions and have no connection with our website. But they can also use the public Gaminatorium API, e.g. to download information about a new player.
- Games subordinated to Gaminatorium can be created as separate microservices, independent of the technologies used in the main website.
- Regular use of the website is rewarded with points that can be exchanged for certain profile decoration elements (i.e. skins).
- Possibilities of interaction between users: adding to friends, sending invitations to play together, etc.

---

## Technicals

The backend of the main Gaminatorium website is created using Java 21, Spring Boot 3.2.6 and Gradle 8.7 (Groovy). Additionally:

- database on Postgres + Hibernate + Liquibase
- Keycloak takes care of the authentication procedures
- standard test stack, i.e. JUnit/Mockito supplemented by Spock and WireMock
- containerization: Docker + Docker Compose
- messaging: RabbitMQ
- CI/CD: Jenkins
- REST API exposed using Swagger 3 (OpenAPI)

The Gaminatorium frontend repository is available [here](https://github.com/keeeper85/Gaminatorium-frontend).

---

## Project status: *In development*

Work on Gaminatorium started in May 2024, the project is managed in Jira and currently 5 people work on it. You can see the current version of the application on the website [gaminatorium.eu](https://www.gaminatorium.eu)

The easiest way to view our project locally is to copy the docker-compose.yml file (it is in the root directory of this repository) and run it with the command:

`docker-compose up -d`

The prerequisite is to have Docker installed with the Docker Compose plugin.

---

## Creators

- Backend/DevOps: [Jakub Raczkowski](https://www.linkedin.com/in/jakub-raczkowski-81401b231)
- Backend: [Michał Grot]()
- Frontend: [Michał Wojnowski]()
- Frontend: [Kacper Heluszka](https://www.linkedin.com/in/krystian-heluszka/)
- Frontend: [Michał Momot](https://www.linkedin.com/in/micha%C5%82-momot-8381222a1)

---

## License

Gaminatorium is an open-source project licensed under the [GPL v3.0](https://choosealicense.com/licenses/gpl-3.0/).