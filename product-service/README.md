# Product Service

Manages the product catalog for the e-commerce platform.

## Features
- CRUD operations for products.
- MySQL database (`product_db`).
- Registers with Eureka Server.
- Integrates with Inventory Service to fetch stock quantities.

## Prerequisites
- Java 17
- Maven
- MySQL
- Eureka Server (`http://localhost:8761`)
- Inventory Service (`http://localhost:8082`)

## Setup
1. Create MySQL database:
   ```sql
   CREATE DATABASE product_db;