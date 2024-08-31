# Spring and Mockito

[![Java CI with Maven](https://github.com/luizgustavocosta/spring-and-mockito/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/luizgustavocosta/spring-and-mockito/actions/workflows/maven.yml)

This project provides a set of examples illustrating how to use Mockito for unit testing Spring applications. It showcases best practices for mocking dependencies, testing controllers, repositories, and services in a modern Java environment.

## Features

- **Dependency Injection:** Demonstrates how to mock dependencies injected into Spring beans.
- **Controller Testing:** Shows how to mock external services and perform end-to-end testing of controllers.
- **Repository Testing:** Illustrates how to mock database interactions and test repository classes.
- **Service Testing:** Provides examples of mocking dependencies and testing business logic in service layers.
- **Java 21:** Leverages the latest features of Java 21 for enhanced performance and modern development practices.
- **RabbitMQ Integration:** Demonstrates how to test applications utilizing RabbitMQ for asynchronous communication.

## Getting Started

1. **Clone the repository:**

```bash
git clone https://github.com/luizgustavocosta/spring-and-mockito.git
```

2. **Navigate to the project directory:**

```bash
cd spring-and-mockito
```

3. **Build the project:**

```bash
mvn clean verify
```

4. **Run the tests:**

```bash
mvn test
```

5. **Run the application:**

```bash
mvn spring-boot:run
```

6. **Run with docker:**

```bash
docker build -t 16bits-tech/spring-and-mockito . 
&& docker run -p 8080:8080 16bits-tech/spring-and-mockito
```

7. **Access the application:**

   7.1. **Via Swagger UI:**

   Open the Swagger UI in your browser:
   Navigate to the `http://localhost:8080/swagger-ui.html` endpoint.
   Explore the available endpoints and test the functionality of the application.

   7.2. **Via H2:**

   Open the H2 console in your browser:
   Navigate to the `http://localhost:8080/h2-console` endpoint.
   Explore the available tables and test the functionality of the application.
   Use the following credentials to connect to the H2 database:
    - Username: `sa`
    - Password: `sa`
    - URL: `jdbc:h2:mem:post;MODE=MYSQL`

8. **Tech Stack**

![](https://img.shields.io/badge/-Java%2021-black?logo=java)
![](https://img.shields.io/badge/-Spring%20Boot-black?logo=spring)
![](https://img.shields.io/badge/-Mockito-black?logo=Mockito)
![](https://img.shields.io/badge/-JUnit-black?logo=JUnit5)
![](https://img.shields.io/badge/-RabbitMQ-black?logo=RabbitMQ)
![](https://img.shields.io/badge/-H2-black?logo=H2)
![](https://img.shields.io/badge/-Docker-black?logo=Docker)
![](https://img.shields.io/badge/-Swagger-black?logo=Swagger)
![](https://img.shields.io/badge/-Maven-black?logo=Maven)
![](https://img.shields.io/badge/-GitHub%20Actions-black?logo=GitHub)


## Examples

The project includes several examples organized by functionality:

- **dependency-injection:** Demonstrates how to mock dependencies injected into Spring beans.
- **controller-testing:** Shows how to mock external services and perform end-to-end testing of controllers.
- **repository-testing:** Illustrates how to mock database interactions and test repository classes.
- **service-testing:** Provides examples of mocking dependencies and testing business logic in service layers.
- **rabbitmq-integration:** Shows how to effectively test applications that utilize RabbitMQ for asynchronous communication.

Each example folder contains a `pom.xml` file with the necessary dependencies and a `src/test/java` directory with the test classes.

## Usage

The examples are designed to be easy to understand and adapt to your own projects. You can use them as a starting point for writing your own unit tests for Spring applications leveraging Java 21 and RabbitMQ integration.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request if you have any suggestions or improvements.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Acknowledgements

This project is inspired by the following resources:

- [Mockito](https://site.mockito.org/)
- [Spring Framework](https://spring.io/)
- [RabbitMQ](https://www.rabbitmq.com/)

## References
- [CloudAMQP](https://customer.cloudamqp.com/login)
- [RabbitMQ (Mensageria Robusta pros seus Softwares) // Dicionário do Programador](https://www.youtube.com/watch?v=_Uo14nxB_iA)
- [RabbitMQ in 100 Seconds](https://www.youtube.com/watch?v=NQ3fZtyXji0)
- [RabbitMQ Simulator](https://tryrabbitmq.com/)
- [Codium](https://codium.ai)
- [Refact.ai](https://refact.ai)
- [Cursor](https://www.cursor.com)