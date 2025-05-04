# Inventory Service

Manages product stock levels for the e-commerce platform.

## Features
- CRUD operations for inventory.
- MySQL database (`inventory_db`).
- Registers with Eureka Server.

## Prerequisites
- Java 17
- Maven
- MySQL
- Eureka Server (`http://localhost:8761`)

## Setup
1. Create MySQL database:
   ```sql
   CREATE DATABASE inventory_db;