# The Secure RESTful API for User Management

RESTful API for User Management with Spring Boot, H2 DataBase, and Docker.
Below you can view the documentation for running the application with/without docker containers.

## Technologies Used

- Java 11
-  Maven
- Spring Boot
- H2 Database
- JUnit
- Mockito
- Docker


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

## API Documentation, Endpoints and Usage Examples

### Create New User
`POST /api/users`
  - Creates New User
  - Requires Username, Password, First name, and Surname
  - Returns Newly created User with Api Key(Token) which will be used for authentication later
  - If the Username or Password is in invalid format, or the Username is taken, returns according error message
  
Example with curl:
 ```bash
 curl -X POST -H "Content-Type: application/json" -d '{
  "username": "beso123",
  "password": "password",
  "firstname": "Beso",
  "surname": "Managadze"
}' http://localhost:8080/api/users
 ```
### Authentication
`GET /api/users?username={username}&password={password}`
  - Requires username and password for authentication
  - Returns User with Api Key(Token) which will be used for authentication later
  - If the Username does not exist or the password is incorrect, return according to error message
  
Example with curl:
 ```bash
curl -X GET "http://localhost:8080/api/users?username=beso123&password=password"
 ```
### Retrieve all users
`GET /api/users`
  - Requires API key (Token) for authorization purposes, it should be passed in a header.
  - Retrieves all users from the Database
  - If the API key (token) is not valid, return according error message

Example with curl:
 ```bash
curl -H "Authorization: 782e060c3ff5883fa82a1fc7306a2481 "http://localhost:8080/api/users"
 ```
### Retrieve User with specific ID
`GET /api/users?userId={userId}`
  - Requires API key (Token) for authorization purposes, it should be passed in a header.
  - Returns User with according userId
  - If Usern does not exist or the API key (token) is not valid, returns according error message
  
Example with curl:
 ```bash
curl -H "Authorization: 782e060c3ff5883fa82a1fc7306a2481 "http://localhost:8080/api/users?userId=253"
 ```
  

  
