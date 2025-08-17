# Social Media REST API

A simple social media backend API built with Spring Boot and PostgreSQL that allows users to create posts, follow other users, and like posts.

## Features

- User Management: Register, update, and view user profiles

- Post System: Create, read, update, and delete posts

- Following System: Follow/unfollow other users

- Like System: Like/unlike posts

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

### 1. Start PostgreSQL with Docker
```bash

docker pull postgres:latest
docker run --name social_media_db -e POSTGRES_USER=root -e POSTGRES_PASSWORD=password -e POSTGRES_DB=social_media_db -p 5433:5432 -d postgres:latest
```

Verify it's running:

```bash
docker ps
```

### 2. Configure the Application

Edit `src/main/resources/application.properties`:

```bash
properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/social_media_db
spring.datasource.username=root
spring.datasource.password=password

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080  
```

### 3. Run the Spring Boot Application

**Option A: In Eclipse**

- Right-click project → `Run As` → `Spring Boot App`

- Check console for:
`Started SocialMediaApplication  in X seconds`

**Option B: Command Line**

```bash
mvn spring-boot:run
```

### 4. Test the API

**Postman** 

1. Setup Postman Environment
First, create a new Postman environment called "Social Media API" with these variables:

    - base_url: http://localhost:8080/api

    - user_id: (leave empty - will be set after user creation)

    - post_id: (leave empty - will be set after post creation)

2. User Endpoints


    **Create User (POST)**

    ```text
    {{base_url}}/users
    ```
    - Body (raw JSON):

    ```json
    {
        "username": "testuser",
        "email": "test@example.com",
        "password": "password123",
        "bio": "Test user bio"
    }
    ```
    What to check:

    - Status code 201 (Created)

    - Save the id from response to your user_id variable

    **Get All Users (GET)**

    ```text
    {{base_url}}/users?page=0&size=10
    ```
    What to check:

    - Status code 200 (OK)

    - Paginated list of users

    **Get Single User (GET)**

    ```text
    {{base_url}}/users/{{user_id}}
    ```
    What to check:

    - Status code 200 (OK)

    - Correct user details returned

3. Post Endpoints

    **Create Post (POST)**

    ```text
    {{base_url}}/posts/user/{{user_id}}
    ```
    Body (raw JSON):

    ```json
    {
        "title": "My First Post",
        "body": "This is the content of my first post!"
    }
    ```
    What to check:

    - Status code 201 (Created)

    - Save the id from response to your post_id variable

    **Get All Posts (GET)**

    ```text
    {{base_url}}/posts?page=0&size=10
    ```
    What to check:

    - Status code 200 (OK)

    - Paginated list of posts

    **Get Single Post (GET)**

    ```text
    {{base_url}}/posts/{{post_id}}
    ```
    What to check:

    - Status code 200 (OK)

    - Correct post details returned

4. Follow Endpoints

    **Follow User (POST)**

    First, create a second user (user2) and note its ID, then:

    ```text
    {{base_url}}/follows/{{user_id}}/follow/{user2_id}
    ```
    What to check:

    - Status code 200 (OK)

    - Follow relationship created

    **Get Followers (GET)**

    ```text
    {{base_url}}/users/{user2_id}/followers

    ```
    What to check:

    - Status code 200 (OK)

    - Your test user should appear in followers list

5. Like Endpoints

    **Like a Post (POST)**

    ```text
    {{base_url}}/likes/users/{{user_id}}/posts/{{post_id}}
    ```

    What to check:

    - Status code 200 (OK)

    **Get Like Count (GET)**

    ```text
    {{base_url}}/likes/posts/{{post_id}}/count
    ```

    What to check:

    - Status code 200 (OK)

    - Count should be 1

**Database Management**
Access PostgreSQL Shell in Docker:

```bash
docker exec -it social-media-db psql -U postgres -d social_media_db
```

Stop/remove container:

```bash
docker stop social-media-db && docker rm social-media-db
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

---

**Final Notes**
- JaCoCo will track coverage for each package:
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