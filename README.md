# The Secure RESTful API for User Management

RESTful API for User Management with Spring Boot, H2 DataBase, and Docker.
Below you can view the documentation for running the application with/without docker containers.

## Technologies Used

- Java 11
- Maven
- Spring Boot
- H2 Database
- JUnit
- Mockito
- Docker
- Swagger UI


## Docker Documentation for usage

First, Clone the repository on your system with the following command:
```bash
git clone https://github.com/bmana21/User-Management-API.git
```

Navigate to the project directory:
```bash
cd User-Management-API
```

Build the application using Maven (This will also run some of the tests):
```bash
mvn clean package
```

Build the Docker image from the docker file:
```bash
docker build -t user-management-app .
```

And finally, we will run the Docker container:
```bash
docker run -p 8080:8080 user-management-app:latest
```

The API will now be accessible at http://localhost:8080

## API Documentation, Endpoints, and Usage Examples

You can interact with the API on this URL: http://localhost:8080/swagger-ui/index.html


I also included the API documentation in this file.
### Create New User
`POST /api/users`
- Creates New User
- Requires fields: `username`, `password`, `firstname`, and `surname`
- Returns Newly created User with Api Key(Token) which will be used for authentication later
- If the Username or Password is in invalid format, or the Username is taken, returns according error message

Example with curl:
 ```bash
curl -X 'POST' \
  'http://localhost:8080/api/users' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "string",
  "password": "string",
  "firstname": "string",
  "surname": "string"
}'
 ```
### Authentication
`GET /api/users/auth`
- Requires `username` and `password` fields for authentication
- Returns User with Api Key(Token) which will be used for authentication later)
- If the Username does not exist or the password is incorrect, return according to error message

Example with curl:
 ```bash
curl -X 'GET' \
  'http://localhost:8080/api/users/auth?username=string&password=string' \
  -H 'accept: application/json'
 ```
### Retrieve all users
`GET /api/users`
- Requires API key (`X-API-KEY` as the header) for authorization purposes, it should be passed in a header.
- Retrieves all users from the Database
- If the API key (token) is not valid, return according error message

Example with curl:
 ```bash
curl -X 'GET' \
  'http://localhost:8080/api/users' \
  -H 'accept: application/json' \
  -H 'X-API-KEY: string'
 ```
### Retrieve User with the specific ID
`GET /api/users/{userId}`
- Requires API key (`X-API-KEY` as the header) for authorization purposes, it should be passed in a header.
- Returns User with according userId
- If Usern does not exist or the API key (token) is not valid, returns according error message

Example with curl:
 ```bash
curl -X 'GET' \
  'http://localhost:8080/api/users/1' \
  -H 'accept: application/json' \
  -H 'X-API-KEY: string'
 ```

  