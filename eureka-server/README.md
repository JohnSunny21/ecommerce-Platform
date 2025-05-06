# Eureka Server

Service discovery server for the e-commerce platform using Spring Cloud Netflix Eureka.

## Features
- Registers and discovers microservices dynamically.
- Runs on `localhost:8761` with a web dashboard.

## Prerequisites
- Java 17
- Maven

## Setup
1. Build and run:
   ```bash
   cd eureka-server
   mvn clean install
   java -jar target/eureka-server-0.0.1-SNAPSHOT.jar