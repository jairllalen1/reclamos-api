# Reclamos API

API REST desarrollada con Java y Spring Boot para gestionar la tabla `entity_type` de un sistema de reclamos.

El objetivo del módulo es implementar un CRUD completo para los tipos de entidad que pueden participar en el sistema, por ejemplo: PyME, ONG, empresa multinacional, institución educativa, comercio, entre otros.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- MySQL
- Jakarta Validation
- Lombok
- Maven
- JUnit
- Mockito
- MockMvc
- IntelliJ IDEA HTTP Client / Postman

---

## Estructura del proyecto

```text
src/main/java/com/jairllalen/reclamosapi
│
├── controller
│   └── EntityTypeController.java
│
├── dto
│   ├── EntityTypeCreateRequestDTO.java
│   ├── EntityTypeUpdateRequestDTO.java
│   └── EntityTypeResponseDTO.java
│
├── entity
│   └── EntityType.java
│
├── exception
│   ├── EntityTypeNotFoundException.java
│   └── GlobalExceptionHandler.java
│
├── repository
│   └── EntityTypeRepository.java
│
├── service
│   └── EntityTypeService.java
│
└── ReclamosApiApplication.java
```

Tests:

```text
src/test/java/com/jairllalen/reclamosapi
│
├── controller
│   └── EntityTypeControllerTest.java
│
└── service
    └── EntityTypeServiceTest.java
```

---

## Descripción de capas

### `controller`

Expone los endpoints REST. Recibe las peticiones HTTP y delega la lógica al service.

### `service`

Contiene la lógica de negocio del CRUD.

### `repository`

Se comunica con la base de datos usando Spring Data JPA.

### `entity`

Representa la tabla `entity_type` de MySQL.

### `dto`

Define los objetos de entrada y salida de la API.

### `exception`

Centraliza el manejo de errores de la aplicación.

---

## Tabla principal

La API trabaja sobre la tabla:

```sql
entity_type
```

Campos principales:

| Campo | Descripción |
|---|---|
| `id_entity_type` | Identificador del tipo de entidad |
| `name_type_entity` | Nombre del tipo de entidad |
| `entity_size` | Tamaño de la entidad |
| `sector` | Sector al que pertenece |
| `id_user_create` | Usuario que creó el registro |
| `id_user_update` | Usuario que actualizó el registro |
| `date_create` | Fecha de creación |
| `date_update` | Fecha de actualización |

---

## Script de base de datos

El proyecto incluye un script de referencia en:

```text
database/schema.sql
```

Ese archivo contiene la estructura mínima de la tabla `entity_type` necesaria para ejecutar el CRUD.

---

## Configuración de base de datos

Crear una base de datos MySQL local:

```sql
CREATE DATABASE reclamos_dev;
```

Ejecutar el script SQL ubicado en:

```text
database/schema.sql
```

La aplicación usa variables de entorno para no guardar credenciales reales en el proyecto.

Variables necesarias:

```text
DB_USER=tu_usuario
DB_PASSWORD=tu_password
```

Opcionalmente puede configurarse la URL:

```text
DB_URL=jdbc:mysql://localhost:3306/reclamos_dev?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true
```

---

## `application.properties`

Ejemplo de configuración:

```properties
spring.application.name=reclamos-api

spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/reclamos_dev?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

spring.web.error.include-stacktrace=never
spring.web.error.include-message=always
spring.web.error.include-binding-errors=always

server.port=8080
```

---

## Endpoints

### Obtener todos los tipos de entidad

```http
GET /api/entity-types
```

Respuesta exitosa:

```http
200 OK
```

---

### Obtener un tipo de entidad por ID

```http
GET /api/entity-types/{id}
```

Respuesta exitosa:

```http
200 OK
```

Respuesta si no existe:

```http
404 Not Found
```

---

### Crear un tipo de entidad

```http
POST /api/entity-types
```

Body:

```json
{
  "nameTypeEntity": "Comercio",
  "entitySize": "Mediana",
  "sector": "Comercio",
  "idUserCreate": 1
}
```

Respuesta exitosa:

```http
201 Created
```

---

### Actualizar un tipo de entidad

```http
PUT /api/entity-types/{id}
```

Body:

```json
{
  "nameTypeEntity": "Institución educativa",
  "entitySize": "Grande",
  "sector": "Educación",
  "idUserUpdate": 1
}
```

Respuesta exitosa:

```http
200 OK
```

---

### Eliminar un tipo de entidad

```http
DELETE /api/entity-types/{id}
```

Respuesta exitosa:

```http
204 No Content
```

---

## Ejemplo de respuesta exitosa

```json
{
  "idEntityType": 20,
  "nameTypeEntity": "Comercio",
  "entitySize": "Mediana",
  "sector": "Comercio",
  "idUserCreate": 1,
  "idUserUpdate": null,
  "dateCreate": "2026-05-03",
  "dateUpdate": null
}
```

---

## Manejo de errores

### ID inexistente

Ejemplo:

```http
GET /api/entity-types/9999
```

Respuesta:

```json
{
  "timestamp": "2026-05-03T22:38:09",
  "status": 404,
  "error": "Not Found",
  "message": "No existe un tipo de entidad con ID: 9999"
}
```

---

### Error de validación

Ejemplo de body inválido:

```json
{
  "nameTypeEntity": "",
  "entitySize": "",
  "sector": "",
  "idUserCreate": 1
}
```

Respuesta:

```json
{
  "timestamp": "2026-05-03T22:38:21",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": {
    "nameTypeEntity": "El nombre del tipo de entidad es obligatorio",
    "sector": "El sector es obligatorio",
    "entitySize": "El tamaño de la entidad es obligatorio"
  }
}
```

---

## Pruebas manuales

El proyecto incluye el archivo:

```text
requests.http
```

Este archivo permite probar los endpoints desde IntelliJ IDEA Ultimate sin usar Postman.

---

## Ejecución de tests

Para ejecutar los tests en Windows:

```bash
mvnw.cmd test
```

O, si Maven está instalado globalmente:

```bash
mvn test
```

El proyecto incluye:

- Tests de service con Mockito.
- Tests de controller con MockMvc.

---

## Cómo ejecutar el proyecto

1. Crear la base de datos `reclamos_dev`.
2. Ejecutar el script `database/schema.sql`.
3. Configurar las variables de entorno `DB_USER` y `DB_PASSWORD`.
4. Ejecutar la clase principal:

```text
ReclamosApiApplication.java
```

5. Probar los endpoints desde `requests.http` o Postman.

---

## Buenas prácticas aplicadas

- Separación por capas.
- Uso de DTOs para entrada y salida.
- DTO separado para creación.
- DTO separado para actualización.
- Validaciones con Jakarta Validation.
- Excepción propia para recurso no encontrado.
- Manejo global de errores con `@RestControllerAdvice`.
- Respuestas HTTP correctas.
- `spring.jpa.hibernate.ddl-auto=validate` para no modificar la base automáticamente.
- `spring.jpa.open-in-view=false` para evitar consultas tardías desde la capa web.
- Credenciales fuera del código fuente mediante variables de entorno.
- Tests unitarios de service.
- Tests de controller con MockMvc.