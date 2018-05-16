# General Type Service
Service that generates a type from a given string, date, phone number, string, integer, etc...

## Seeded Patterns
No patterns are seeded yet.

## Features
* Create Pattern
* Update Pattern
* Get Pattern
* Delete Pattern
* Resolve String Type

# Run
The project is a Spring Boot application, thus it is started via the usual Spring Bootstrap Gradle method.
`Gradle -> bootRun`

# Test
The test coverage, unit, and integration tests can be run with the following gradle method.
`Gradle -> test`

Note, you will need to have Mongo running for the integration tests.

# Docker
The project includes a Docker-Compose script for running the entire application. Swagger documentation and seeded DB will be added in the future to help run out of the box.
