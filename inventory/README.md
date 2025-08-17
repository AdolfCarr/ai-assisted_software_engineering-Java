# Inventory Management System

A Spring Boot application for managing product inventory with RESTful API endpoints, MySQL database persistence, documentation.

## Features

- CRUD operations for products
- MySQL database integration
- Input validation
- Global exception handling
- Unit and integration tests
- Docker support for database

## Technologies

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA (Hibernate)
- MySQL 8
- Maven
- Docker

## Prerequisites
- Java 17+
- Docker (for MySQL)
- Maven
- Postman/cURL (for testing)

## Setup & Run

### 1. Start MySQL with Docker
```bash

docker pull mysql:8.0
docker run --name inventory-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=inventory_db -p 3306:3306 -d mysql:8.0
```

Verify it's running:

```bash
docker ps
```

### 2. Configure the Application

Edit `src/main/resources/application.properties`:

```bash
properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=password  # ← Matches Docker setup
```

### 3. Run the Spring Boot Application

**Option A: In Eclipse**

- Right-click project → `Run As` → `Spring Boot App`

- Check console for:
`Started InventoryApplication in X seconds`

**Option B: Command Line**

```bash
mvn spring-boot:run
```

### 4. Test the API

**Postman** 

Testing Your ProductController in Postman
1. GET All Products (/api/products)
Request:

```text
GET http://localhost:8080/api/products
```
Expected Responses:

Empty database: Status 200 OK with empty array []

With products: Status 200 OK with array of products:

```json
[
    {
        "id": 1,
        "name": "Product 1",
        "description": "Description 1",
        "price": 10.99,
        "quantity": 5
    }
]
```
2. GET Single Product (/api/products/{id})
Request:

```text
GET http://localhost:8080/api/products/1
```
Expected Responses:

Found: Status 200 OK with product:

```json
{
    "id": 1,
    "name": "Product 1",
    "description": "Description 1",
    "price": 10.99,
    "quantity": 5
}
```
Not found: Status 404 Not Found with empty body

3. POST Create Product (/api/products)
Request:

```text
POST http://localhost:8080/api/products
```
Headers:
  Content-Type: application/json
Body:
{
    "name": "New Product",
    "description": "New Description",
    "price": 15.99,
    "quantity": 10
}
Expected Responses:

Success: Status 200 OK with created product (including generated ID):

```json
{
    "id": 2,
    "name": "New Product",
    "description": "New Description",
    "price": 15.99,
    "quantity": 10
}
```
Validation error: Your current implementation doesn't have validation, but if added, would return 400 Bad Request

4. PUT Update Product (/api/products/{id})
Request:

```text
PUT http://localhost:8080/api/products/1
```
Headers:
  Content-Type: application/json
Body:
{
    "name": "Updated Product",
    "description": "Updated Description",
    "price": 20.99,
    "quantity": 3
}
Expected Responses:

Success: Status 200 OK with updated product:

```json
{
    "id": 1,
    "name": "Updated Product",
    "description": "Updated Description",
    "price": 20.99,
    "quantity": 3
}
```
Not found: Status 404 Not Found (when ID doesn't exist)

5. DELETE Product (/api/products/{id})
Request:

```text
DELETE http://localhost:8080/api/products/1
```
Expected Responses:

Success: Status 204 No Content with empty body

Not found: Still returns 204 No Content (your implementation doesn't check existence before delete)

**cURL:**

Create a todo:

```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"title":"Docker test","description":"Testing with MySQL"}' \
http://localhost:8080/api/products
```
Get all todos:

```bash
curl http://localhost:8080/api/products
```

Database Management
Access MySQL shell:

```bash
docker exec -it inventory-mysql mysql -uroot -ppassword inventory_db
```

Stop/remove container:

```bash
docker stop inventory-mysql && docker rm inventory-mysql
```
Troubleshooting
MySQL connection issues:
Verify Docker container is running (docker ps) and credentials match application.properties.

Port conflicts:
Change Docker's host port (e.g., -p 3307:3306) and update spring.datasource.url.

Lombok errors:
Install the Lombok plugin in Eclipse.

### 5. Code Coverage with JaCoCo

1. Generate report:

   ```bash
   mvn clean test
   ```

2. View coverage:

Open target/site/jacoco/index.html in a browser

3. Enforce thresholds (optional):

Configure limits in pom.xml (see JaCoCo docs)

text

---

**Final Notes**
- JaCoCo will track coverage for:
  - Controllers (`InventoryController.java`)
  - Services (`InventoryService.java`)
  - Repositories (`InventoryRepository.java`)
- **Exclusions**: By default, it ignores getters/setters (thanks to Lombok).

## Feedback 
Was it easy to complete the task using AI? Yes

How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics) 1 to 1.5 hrs

Was the code ready to run after generation? No 

What did you have to change to make it usable? fix the package structure

Which challenges did you face during completion of the task? I need to fine tuning the prompts to get a usable answear

Which specific prompts you learned as a good practice to complete the task?

- Chain-of-Thought - Breaking down the pom.xml generation
- Few-Shot Learning - ToDoList project of the last activity as example