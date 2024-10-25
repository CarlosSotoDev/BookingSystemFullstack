### **Paso 1: Crear un nuevo proyecto Spring Boot**

1. **Ir a Spring Initializr** (https://start.spring.io/).
2. Configura el proyecto:
    - **Project**: Maven Project
    - **Language**: Java
    - **Spring Boot**: 2.7.x o superior (recomendado)
    - **Group**: com.tcs
    - **Artifact**: packageservice
    - **Name**: packageservice
    - **Description**: Microservicio de Paquetes
    - **Packaging**: Jar
    - **Java Version**: 17 (o la versión que prefieras)

3. **Dependencias**:
    - **Spring Web**: Para crear APIs REST.
    - **OpenFeign**: Para comunicarse con otros microservicios.
    - **Lombok**: Para simplificar la creación de clases.
    - (Opcional) **Spring Boot DevTools**: Para reinicios automáticos durante el desarrollo.

4. **Genera el proyecto** y descárgalo.

### **Paso 2: Agregar dependencias adicionales en `pom.xml`**

Abre el archivo `pom.xml` del proyecto generado y asegúrate de que tienes las siguientes dependencias necesarias para el uso de Feign, Lombok y otros componentes:

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Feign for inter-service communication -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

    <!-- Lombok to reduce boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.22</version>
        <scope>provided</scope>
    </dependency>

    <!-- Spring Boot DevTools (Optional) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
    </dependency>

</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2021.0.4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### **Paso 3: Habilitar Feign Clients**

En tu clase principal del proyecto, habilita **Feign Clients** con la anotación `@EnableFeignClients`. Esto permite que Spring gestione y detecte los Feign Clients que vas a definir.

**PackageServiceApplication.java**:
```java
package com.tcs.packageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PackageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PackageServiceApplication.class, args);
    }
}
```

### **Paso 4: Crear los DTOs**

Ahora crea las clases **DTO (Data Transfer Objects)** que recibirás de los microservicios de hoteles y vuelos.

#### 4.1. Crear los DTOs en un paquete `dto`:

**HotelDTO.java**:
```java
package com.tcs.packageservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class HotelDTO {
    private Long id;
    private String hotelName;
    private String city;
    private BigDecimal pricePerNight;
}
```

**FlightDTO.java**:
```java
package com.tcs.packageservice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightDTO {
    private Long id;
    private String cityOrigin;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private BigDecimal price;
}
```

### **Paso 5: Crear los Feign Clients**

Crea un paquete llamado `client` para tus Feign Clients que van a comunicarse con los microservicios de **hoteles** y **vuelos**.

#### 5.1. Definir los Feign Clients:

**HotelClient.java**:
```java
package com.tcs.packageservice.client;

import com.tcs.packageservice.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "hotelservice")
public interface HotelClient {

    @GetMapping("/api/v1/hotels")
    List<HotelDTO> getAllHotels();
}
```

**FlightClient.java**:
```java
package com.tcs.packageservice.client;

import com.tcs.packageservice.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "flightservice")
public interface FlightClient {

    @GetMapping("/api/v1/flights")
    List<FlightDTO> getAllFlights();
}
```

### **Paso 6: Crear el Servicio (Service Layer)**

Crea un paquete llamado `service` para definir el servicio que va a consumir los datos de los microservicios de hoteles y vuelos y los combinará.

#### 6.1. Definir el Servicio:

**PackageService.java**:
```java
package com.tcs.packageservice.service;

import com.tcs.packageservice.client.HotelClient;
import com.tcs.packageservice.client.FlightClient;
import com.tcs.packageservice.dto.HotelDTO;
import com.tcs.packageservice.dto.FlightDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final HotelClient hotelClient;
    private final FlightClient flightClient;

    public PackageService(HotelClient hotelClient, FlightClient flightClient) {
        this.hotelClient = hotelClient;
        this.flightClient = flightClient;
    }

    public String createPackage() {
        List<HotelDTO> hotels = hotelClient.getAllHotels();
        List<FlightDTO> flights = flightClient.getAllFlights();

        // Lógica para combinar los hoteles y vuelos en un "paquete"
        StringBuilder packageDetails = new StringBuilder();
        packageDetails.append("Paquete de Viaje:\n");

        packageDetails.append("Hoteles:\n");
        hotels.forEach(hotel -> packageDetails.append(hotel.getHotelName()).append(" en ").append(hotel.getCity()).append("\n"));

        packageDetails.append("Vuelos:\n");
        flights.forEach(flight -> packageDetails.append("Vuelo a ").append(flight.getDestination()).append(" desde ").append(flight.getCityOrigin()).append("\n"));

        return packageDetails.toString();
    }
}
```

### **Paso 7: Crear el Controlador (Controller Layer)**

Crea un paquete llamado `controller` para definir el controlador que expondrá el API para que los clientes puedan acceder a la funcionalidad del microservicio de paquetes.

#### 7.1. Definir el Controlador:

**PackageController.java**:
```java
package com.tcs.packageservice.controller;

import com.tcs.packageservice.service.PackageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public String getPackage() {
        return packageService.createPackage();
    }
}
```

### **Paso 8: Configurar el API Gateway o Discovery Server**

Si estás usando **Eureka** para el descubrimiento de servicios o un **API Gateway**, asegúrate de que tu microservicio de paquetes esté correctamente registrado y que las rutas hacia los microservicios de hoteles y vuelos estén configuradas en el Gateway.

### **Paso 9: Probar el Microservicio**

1. Levanta tu microservicio de paquetes junto con los microservicios de **hoteles** y **vuelos**.
2. Ve a tu navegador o herramienta como **Postman** y realiza una solicitud **GET** a la ruta:

```
http://localhost:8080/api/v1/packages
```

Esto debería devolver los detalles combinados de los hoteles y vuelos obtenidos de los microservicios correspondientes.

### **Resumen**

Hemos creado un microservicio de paquetes en Spring Boot que:
- Utiliza **Feign Clients** para consumir datos de otros microservicios.
- Utiliza **DTOs** para mapear los datos.
- Contiene un **servicio** que combina la información de los microservicios de hoteles y vuelos.
- Expone un **API REST** mediante un controlador que permite acceder a los paquetes.
