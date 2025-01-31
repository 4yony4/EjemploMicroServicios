# Configuración de Microservicios con Spring Boot y Gradle en Debian

Este proyecto demuestra cómo configurar un entorno de **microservicios con Spring Boot** utilizando **Gradle** en un servidor Debian. 

## **1. Instalación de Dependencias**

### **1.1 Instalar Java (JDK 17 o superior)**
```sh
sudo apt update && sudo apt install openjdk-17-jdk -y
java -version
```

### **1.2 Instalar Gradle**
```sh
sudo apt install gradle -y
gradle -v
```

---

## **2. Creación de Microservicios**
Se crearán los siguientes servicios:
1. **user-service** → Gestiona usuarios.
2. **order-service** → Gestiona pedidos.
3. **api-gateway** → Enruta solicitudes API.

### **2.1 Crear la estructura del proyecto**
```sh
mkdir spring-microservices && cd spring-microservices
gradle init
```
Seleccionar:
- **Tipo:** **basic**
- **DSL:** **Groovy** (o Kotlin, según preferencia)

---

## **3. Creación de user-service**
```sh
mkdir user-service && cd user-service
gradle init
```
Modificar `build.gradle`:
```gradle
plugins {
    id 'org.springframework.boot' version '3.2.2'
    id 'io.spring.dependency-management' version '1.1.3'
    id 'java'
    id 'application' // Add this plugin
}

group = 'com.example'
version = '1.0.0'
sourceCompatibility = '17'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}

// Explicitly set the main class
application {
    mainClass = 'com.example.Application'
}
```

### **3.0 Crear el Main Application **
Crear `src/main/java/com/example/Application.java`:
```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### **3.1 Crear el controlador REST**
Crear `src/main/java/com/example/UserController.java`:
```java
package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id) {
        return "User " + id;
    }
}
```

Ejecutar el servicio:
```sh
gradle bootRun
```
---

## **4. Creación de order-service**
Repetir los mismos pasos para `order-service`:
```sh
mkdir ../order-service && cd ../order-service
gradle init
```
Modificar `build.gradle` y crear `OrderController.java`:
```java
package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/{id}")
    public String getOrder(@PathVariable String id) {
        return "Order " + id;
    }
}
```
Ejecutar:
```sh
gradle bootRun
```
---

## **5. Creación del API Gateway**
```sh
mkdir ../api-gateway && cd ../api-gateway
gradle init
```
Modificar `build.gradle`:
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
}
```
Configurar `application.yml`:
```yaml
server:
  port: 8080

spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://localhost:8081
          predicates:
            - Path=/users/**
        - id: order-service
          uri: http://localhost:8082
          predicates:
            - Path=/orders/**
```
Ejecutar:
```sh
gradle bootRun
```
---

## **6. Dockerizar los Microservicios**
Crear `Dockerfile` en cada microservicio:
```Dockerfile
FROM openjdk:17
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Crear `docker-compose.yml` en la carpeta principal:
```yaml
version: "3.8"
services:
  user-service:
    build: ./user-service
    ports:
      - "8081:8081"

  order-service:
    build: ./order-service
    ports:
      - "8082:8082"

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - user-service
      - order-service
```
Ejecutar todo:
```sh
docker-compose up -d
```
---

## **Próximos Pasos**
Ahora que los microservicios están configurados, se puede integrar **Spring Cloud Eureka** para la detección de servicios o **Spring Config Server** para la gestión centralizada de configuraciones. 

¡Listo para escalar el sistema! 🚀
