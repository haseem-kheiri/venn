## Prerequistes
Docker should be available of the system. Test make use containers for PostgreSQL.
Docker compose is also provided to deploy PostgreSQL the you will need for the application to run.

## Please download and build the project
run
`
mvn clean install 
`
if you want to skip the test run
`
mvn clean install -DskipTests
`
## Module
The three main modules are 
* common-utils
* load-fund-service
* load-fund-app


load-fund-app is a thin wrapper to provide load-fund-service as a spring boot application. 
load-fund-service is where all the domain specific artifacts are including

* repository
* service
* REST controller

All test are available with the load-fund-service module. 
Please feel free to contact me if you have any issues running the service.
