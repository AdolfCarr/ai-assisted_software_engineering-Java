# Todo List REST API with Spring Boot, Hibernate, and MySQL (Docker)

A simple RESTful API for managing todo items with full CRUD operations, built with:
- **Spring Boot 3**
- **Hibernate/JPA** (for database operations)
- **MySQL 8** (running in Docker)
- **Maven** (for dependency management)

## Features
- Create, read, update, and delete todo items
- Each item has:
  - `id` (auto-generated)
  - `title` (required)
  - `description` (optional)
- Validation for required fields
- Hibernate auto-creates database tables

## API Endpoints
| Method | Endpoint          | Description                     | Sample Request Body             |
|--------|-------------------|---------------------------------|---------------------------------|
| POST   | `/api/todos`      | Create a new todo               | `{"title":"Task","description":"..."}` |
| GET    | `/api/todos`      | Get all todos                   | -                               |
| GET    | `/api/todos/{id}` | Get a todo by ID                | -                               |
| PUT    | `/api/todos/{id}` | Update a todo                   | `{"title":"Updated",...}`       |
| DELETE | `/api/todos/{id}` | Delete a todo                   | -                               |

## Prerequisites
- Java 17+
- Docker (for MySQL)
- Maven
- Postman/cURL (for testing)

## Setup & Run

### 1. Start MySQL with Docker
```bash
docker run --name todo-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tododb -p 3306:3306 -d mysql:8.0
```

Verify it's running:

```bash
docker ps
```

### 2. Configure the Application

Edit `src/main/resources/application.properties`:

```bash
properties
spring.datasource.url=jdbc:mysql://localhost:3306/tododb
spring.datasource.username=root
spring.datasource.password=password  # ← Matches Docker setup
```

### 3. Run the Spring Boot Application

**Option A: In Eclipse**

- Right-click project → `Run As` → `Spring Boot App`

- Check console for:
`Started TodoApplication in X seconds`

**Option B: Command Line**

```bash
mvn spring-boot:run
```

### 4. Test the API
Use Postman or cURL:

Create a todo:

```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"title":"Docker test","description":"Testing with MySQL"}' \
http://localhost:8080/api/todos
```
Get all todos:

```bash
curl http://localhost:8080/api/todos
```

Database Management
Access MySQL shell:

```bash
docker exec -it todo-mysql mysql -uroot -ppassword tododb
```

Stop/remove container:

```bash
docker stop todo-mysql && docker rm todo-mysql
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
  - Controllers (`TodoController.java`)
  - Services (`TodoService.java`)
  - Repositories (`TodoRepository.java`)
- **Exclusions**: By default, it ignores getters/setters (thanks to Lombok).

## Feedback 
Was it easy to complete the task using AI? Yes

How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics) 3 to 4 hrs

Was the code ready to run after generation? What did you have to change to make it usable? No

Which challenges did you face during completion of the task? I need to fine tuning the prompts to get a usable answear

Which specific prompts you learned as a good practice to complete the task?

- Chain-of-Thought - Breaking down the pom.xml generation
- Few-Shot Learning - Entity class example