# Getting Started

### CLI 
- Installing Dependencies: ```mvn dependency:resolve```
- Starting the Embedded Web Server: ```mvn spring-boot:run```
- Running Tests: ```mvn test```

### Endpoints 
- OPTIONS http://localhost:3000
- GET http://localhost:3000/api/users
- POST http://localhost:3000/api/users
- GET http://localhost:3000/api/users/:id
- PUT http://localhost:3000/api/users/:id
- DELETE http://localhost:3000/api/users/:id

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/maven-plugin/build-image.html)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/3.4.4/reference/data/nosql.html#data.nosql.mongodb)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.4/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [MongoDB Java Driver](https://www.mongodb.com/docs/drivers/java/sync/current/)
* [Get Started with the Java Driver](https://www.mongodb.com/docs/drivers/java/sync/current/get-started/#std-label-java-get-started)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


### Configuration 
![](./rsc/project.jpg)