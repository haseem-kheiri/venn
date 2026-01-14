## Prerequisites

Docker must be available on the build and runtime host.
Tests use Testcontainers with PostgreSQL.
Docker Compose is also provided to run PostgreSQL for the application.

## Build
Run:
`
mvn clean install 
`
To skip tests:
`
mvn clean install -DskipTests
`
## Module
The project consists of three modules:
* common-utils
* load-fund-service
* load-fund-app

load-fund-app is a thin Spring Boot wrapper that exposes load-fund-service.
load-fund-service contains all domain logic, including:

* repository
* service
* REST controller

All tests are located in the load-fund-service module.
Database migration script are pushed using Flyway.
