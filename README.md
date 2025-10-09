# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Sequence Diagram Link
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9QAqgMqiIVHcYFBMtkwJQABQZLI5SgZACOqRyAEpMDoccAANYwAByKBQHBgM2ghiQaAAZtBjgQCtdqqI7gjqs8YABZbKqPn2WTyACiAA8VNgRYUbpRJZViuZ6oEnE5huM5upgPZ5vUxqqoN46jBUfqOEhOWgIMxsN4qJ7DMAEAhsShKUgcZz+agEBxVJczJwxRjDPcVYplGpVPVbWA3VACW8PihaRmlCp1HdKsZagAxPmcgsZmA6SwwYuzMT0lBM11ozZIMDxDsDEswYMMjjtlDqvEactZqulKWplEDDMpgOYleVPUuhQIKeWdpM9AarU6rf63fwZDGmCm4IWsZW1Q2u3LR3O+qduYTo9e2nGB5EZdAoSTDhMEXSsE1vREVHqNAfBDLcJXTbQK2zWoQAZfECyLDdtDLTClzgmsdFqBQOCbIj5BAiAJ37fUMz0AwYGGAAqP8UC435-g4LiwVONCd0qGDsNw3t8QUHxhwJYA5PiDMSPkLDlwoqiaJgWThwzLi3hgcwrBgbIO2wLR8U5SBTLQEAUg4IMlOgJAAC88gKGAYxQONoNI2Dq3RbcUF-McuwnTgJyU9oIDA3VxR3e4YSeF1iTxMk1BQrBkrhA0gplV4ws+GB7WBZZFOHGK4qWUqIP3PKjQwepwjNF8eJq5YyrGCr4iq8CSs6iD0CgzxvD8fxoHYW0YjrOBVWkOAFBgAAZCAskKRrmFXGp6maNoul6Ax1HyNALR4tZ+J2K56qSx44ReCYirtfYQUugEvmhO6bz3BKQuYqAELTGAEDWvkCVW9aKSpMBaUBwKJPUXMUHzAZCKmLsLv0P4dlUzMAvuWsG0i5ttFbdseLpBlmQLIwIDUGBvWYWc8T8tSyLywGkJQhBRLTbaZRZCACzrXxOAvFBtRO694XuTaTVa0ZX2zD8Fi-J1oCQxjDMFHxIsHYcYD9CAA0czl2quYbKd7ZkmixgTabUNAAHJmGFEARwphG4O2pF-s3OH4OlF0AHVvAcW2th2cXJdFG7ZfvJrHycABGF83xV+1vw12yqGDT1DbtnYhuTL2Od+9dKH9374f8yS8JQXT4gUpSVNZvHs0C2sQdi8zsQgTYydHdG5jb9TyKMSi-EswxG-0tBuLRYz2zMqe5A5BjbPsnxHIZlAB56hi4tH9mfcQqLKti9BebynKZQhvkMlULLMFvmWfuoArHuH+YBpe8rosvmgDq+xrpBwagnMAzUFaFW-sArqPU+pAN-omS2o1fABC8OyNAMQ4iJEwegFaa1fBYE2oFfcu1pCqmWqqdoqpug9COoqAowwEGAOloFV+9R4hAU-qw6qtVPqwm+kFX2NNObAyIXJcGkiwBQzUDDa+GE2awXqDUJA-JLCN0Qc3C+cVcZj07pRQhPc-Dn16mwnsfYACSaBc7IE5Hwq+pdA5rgZtzRR-MXSCy0YApo-pAwcGjleOOhoIHy1TordOtpVYTGmM5KAbkOTFygs40+f1HHxVTDXZR2FHLcBkkpcwhAPJoG0Rk-RJ9NIyB8sjGehSMDFJ1DARBwwYCcW7syUxS8YBZBoNgnqRSQAlMPugVpnE8m1JgAUBA7ZhyGBABAFIMxsH6xHBk4++N37BS5qhAOnj6gQ2IY-Z+IS7wlEgTAFq5pRgW2TGg8aUYYjYD5MyZaeIYAAHEuwaFIfsxoHyaH0PsF2FhACj43UqJwmA3DgCORdOMDJMTEyvzIeXT54VxHIByF8q04M8Q4rUHImkijxK10RjANRGifFxR0eYvRGyO4EyMatEx2AzGIKtn2GmHA6YJkZkZOcOQGXLjSTsnmeytkyhsXYz01LzyaglsEsBt45ZJwiZaZW0T7S6x6i5VySTbkpLJd7LZvt1kB1JTkxGWKwCHgQAS1QRKwAEgqZsieSNbUhgdTAAk-JvAzBAtw9F-5WL6FLMK8e4i3k5AADwOvKB4yVLpo1gDjd8hNpzVVXItMsYFVpVYNHGHmlAVjpCq2TuEYIgQQSbHiEOFABYSx7G+MkUAjJG1dmbSCYtLIuyLChDATohrTBeHQf4DgAB2NwTgUBOBiKqYIcA5oADZ4D12DYYIoEDUUfxdHtDoQKQVxN0aM0YPauygN3Rwr6LoYVwoeoigRwwdAQGnA9AlvbPj9sESlHd2yN0iLPlJNeBKCRwHrgSp1sNq5KPbuSylmiwXoFpYg11jKqkss6Wy9ZljqZoh5fTflzMhWpNNWfZCuyYN-OlXnDgcq0BBKlpmsJaq06as-LE3VCT9W7GHaRwD6SkOZOCtkuDOYYDuxQCARkcAfBOnMA6iNhj6gdN7sWhMvr-WBsMASmQ2g2LhucWRv6FHxUwctWJ+owH8Q9K7FYo1Vrx61gnNpYtG9LK6DDVxAwvoEDAEsPqfQPSkB9Ik4s6A3nka2f-CWLi3BgA0CmXM2Ahk+SCigMKE6RkZjanbEp0VbjKNZJcTtDd4HpIcmOQgLAzHzlQLnSMXNdmy31ArVWmAw77kBACyGfuOCkAJDAD1kGA8ABSEA+QAf8K26TW7zl-plM0ZEB0ejFtBSe06oxsB+YC1AOA4WoBrGLaWy9txbpCNvTw+F3UhNIqhM+19lh6gAEhGs-B25QfbIMoB7GDiwKxtCegACFloKDgAAaW+MdlrMA2uBA6y-G9f7fa6fEQAKwm2gUDGO+SQfDNDaDxWLNj1UZQdRiGNsocAWhjS7rjFYfZRYqm-0HZ8p9AKlm-HxGmcTflF0NH7H0cY7HZV8c6usciexmJOr4mJN45BfLBXzXmb0450nCSNEEvs-lqpqnTHqcHlrzkmmIABrmQB0NBhaS4dZIxNzqzguhe11ztFPOJV8-qALz043cddmF8Jm8YuHymmuRq60Wrljemi4YHWnBkmcuZLpoZaB+U6EMDjtAHJFfGdzF2KuxXqO2Noz7rHfuFUxwD2-M5wenDPkl+HjjbnJxAXbPEILL6Dbbf85QVQ8ebdOvXgs77DN2cJfBLobgMBs8Cdz3MfPImStSqL-YkvBL-fsJVSxp8bGG8xK77trk-RmAGG4OAJiHp+T8koOYMLw-oAwAz+f9RV+cSkGLfHozM-o-z-QsTsi9RYVOR999QbICUdc6dV4bM3NJsPMDMp9P9ud3F3dyEN0S9KtMpqsN8g9E4rkYARhOtR1xovB-N+tBtiD2xEBexYBgBsBttGkss5sb8-kGhKFqFaF6FjAN9IUb0XgLgfUo8X1gJM8OQORaQUUCtLdDAA4jAal8RZADMnUXUSVVdLMelkZJDwDnMJkbNYCw1TBkCg56hpBZCToqsatRdQlxcrl8CFcgA

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
