# StreamWise
Semester project for SFWE 510 at University of Arizona, Fall 2025. Microservice focused.

## To build:
`mvn clean package dockerfile:build -DskipTests`

## To run:
`docker compose -f .\docker\docker-compose.yml up`

## To change profiles:
edit `docker-compose.yml`, replacing "dev" with "prod"

## To stop:
`docker compose -f .\docker\docker-compose.yml down -v`

## Testing:
See included postman collection of example endpoints when running through `docker compose`.

## Notes:
An dependency exists on its own repository for configuration: [makani-rain - StreamWise](https://github.com/makani-rain/StreamWise)

This project was implemented using Java 17, Maven 3.9.11, Spring Boot 3.4.10, and Spring Cloud 2024.0.2.