# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

sequence diagram


https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5T9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTJgvL5-AFoOxyTAADIQaJJAJpDJZbt5IvFEvVOpNVoGdQJNBD0WjWYvN4cA7FqAFfOXD0b4oB++ivEsALnMCBQJigpQIIePKwgeR6ouisTYrBGphm65TTuYhB6gayGIWgaEYiaRLhhaHLkAAonudFwPUMA3Fo8izJy4Q1AAsqxHSzAAQgAmvxI7iq60qymqcFiVA8ZyoYLo4Sy5Set6Qa0uWo6UUybo0eUACSAByZB0eELHToyswmfUNQwNOoZUfpSZXjJeFBrO6COqIrkAWUwGzNOXloJg-mub+VyBQ5nmNugP7XiCpzZD2MD9k4MDdKW0XBXFaBLpwnjeH4gReCg6D7oevjMCe6SZJgKWXkU1A3tIDF0fUdHNC0j6qM+3S5XO7Z-vk-keXWeVhVBlAwYp8FVb65GxAGsVzphinYc5qkwCUSAAGaWLG-qDegulmhGhSWvRjHMTFcbaJx3F8UdMAiXdM6TZJ5rSU68rvQ6phYQUX3ukYKDcJkR0rRNa1OXpUmXbRbWMZ1-3aDAXG8e9r2iSdoUg52sEKkqKqwX501lIqyqmOFs0tZTJPDYWjVgH2A5mIVa7FZukK2nu0IwAA4qOrK1WeDUXswP03oLHXdfYo4Dat3m-v+FOlHjU1AjN+RE8gsTC6MqjIdChtqEtWI+YmBIqaSO2UPth2aXjZ3UZGtFmTdllBtZGNPTAL1vZrLQtCw5l0XD51skTaPyIDinA7boP62AZvG67boFFdntMfUABUftYwrowOiDEVzZUPTFyghnSLMp6ZAadyzN0OgIKADZN6OszV8Zo57NMLhD40VsoOT2tlBUVejrX9d1fq2nvplaBtx3XdL73-eD8PWupuX9OlFP1ezzADcL+JIHL6vICd4vl+b6MA9Dy4I+qwULNs4OvR9NXqjjJXx866n3nuvS+rd2431AT3UcfdH7bxfgVFcXMNyBGwD4KA2BuDwF1JkIWo4UjzwljkKWbkSjlFvA0eWitgjKxfL0B+KAEr0zVhPcaH05xDgYZBCedNfpwXUpkM2FtobsPQNA0YsCUDrRkpteGdtdoHShi7SObtEblBzrdKyD1C7PSDDjd6IUVEuVIeCWOwBR68PBAIlAZtYQMOkXwxOW1SQ8g6KnUcdjRwhjLvkK6JkzIWRgNXQUtl7LV00DoLOHJKRVGkAoVGgCImuRBqURJZdLF-USRYlhqZUkz2kLvaCvDLiJNVvkD+aUBzL2nqMWuiCiooICJYcGCFkgwAAFIQB5Hg0YgRr4NiIeYaOiVyFVEpPeFo1clYw3QEOFmcAIAISgOImu0gmElBycCDWtC5mSwWUslZdTCk6yJgAKy6WgIRKA0QYhESFWYIA9mLOgIc6QDjnQ22caDBRTs-R3LyhnBG7J1HtU0T7bRmNdF+n0ZrAmP1TFaLjmTfIKSgkUlsYAwF6gokgq9kErx6NIX4t6d0QSYQ0lJ2Ge5YlKAAbIqJn4dihhgnaHjjJJxcj3Qsrjj4q6MS4kJIJfIGAJkaX6IpV8-efDibUzJpsy4VMVS011iMxVw136S0-hzJB64SoBC8MAMIiAvSwGANgTBhECCJAIeLFmVKyGVGRh1LqrRjDqtGurR5JrYTYnCiq9yXq8A+osSipOaluBBqxaoHFMh2qowLkSm4RiWQxqdfGnR71k3Yt8UjONdEYAJv9uE9J-rpUwDVXKj1rC1XKsihWxKrkKnpVMMuTAQA
