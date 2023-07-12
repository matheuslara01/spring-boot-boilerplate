# Spring Boot Boilerplate - JWT Authentication, MySQL, Swagger, JUnit

https://medium.com/@mateus.delara65/spring-boot-boilerplate-jwt-authentication-mysql-swagger-junit-56bd75bef708

This project is a boilerplate (initial model) in Spring Boot for REST APIs that includes basic configurations for authentication with JWT (JSON Web Token), integration with MySQL database, API documentation with Swagger, unit tests with JUnit, basic registration of user, exception handling through the Exception Handler and logs using SLF4J.

![Spring Boot](https://img.icons8.com/color/48/000000/spring-logo.png)
![JWT](https://img.icons8.com/color/48/java-web-token.png)
![MySQL](https://img.icons8.com/color/48/mysql-logo.png)
![Swagger](https://img.icons8.com/color/48/cloud-function.png) 

## Requisites

Before running the project, make sure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Maven
- MySQL Server

## Settings

1. Clone this repository on your local machine:
- ``git clone 'https://github.com/matheuslara01/spring-boot-boilerplate.git'``

2. Navigate to the project directory:
- ``cd spring-boot-boilerplate``

3. Create a MySQL database for the project.

4. In the `application.properties` file (located in `src/main/resources`), update the following properties according to your MySQL configuration:
- ``spring.datasource.url=jdbc:mysql://localhost:3306/name_db``
- ``spring.datasource.username=your_username``
- ``spring.datasource.password=your_password``

5. Run the following command to build and run the project:
- ``mvn spring-boot:run``

## Use

After starting the project, you can access the API documentation using Swagger:

``http://localhost:8080/base/swagger-ui/index.html``

To authenticate and access the protected routes, you will need to generate an authentication JWT token. To do this, make a POST request to the `/base/auth` endpoint with the username and password credentials. The JWT token will be returned in the response. Then include the JWT token in subsequent requests in the `Authorization` header.

## User registration

The project includes a basic user registry with creation, reading, updating and deleting (CRUD) operations. The endpoint for user operations is `/base/user`.

## Tests

The project includes unit testing using JUnit. To run the tests, run the following command:

- ``mvn test``

The tests are run and the results will be displayed on the console.

## Exception Handling

The project has an Exception Handler to handle exceptions centrally. This allows you to return standardized and appropriate responses in case errors or exceptions occur during the execution of the API.

## Logs

The project uses SLF4J for logging. Logs are logged and can be configured according to project needs.

## Contribution

Feel free to contribute improvements to this project. Feel free to open issues (issues) or submit pull requests with your suggestions.

---

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
