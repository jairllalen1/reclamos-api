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

| Campo              | Descripción                       |
|--------------------|-----------------------------------|
| `id_entity_type`   | Identificador del tipo de entidad |
| `name_type_entity` | Nombre del tipo de entidad        |
| `entity_size`      | Tamaño de la entidad              |
| `sector`           | Sector al que pertenece           |
| `id_user_create`   | Usuario que creó el registro      |
| `id_user_update`   | Usuario que actualizó el registro |
| `date_create`      | Fecha de creación                 |
| `date_update`      | Fecha de actualización            |

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

## Ejemplos de respuestas exitosas

### Ejemplo de respuesta - GET `/api/entity-types`

```json
[
  {
    "idEntityType": 1,
    "nameTypeEntity": "Comercio",
    "entitySize": "Mediana",
    "sector": "Comercio",
    "idUserCreate": 1,
    "idUserUpdate": null,
    "dateCreate": "2026-05-03",
    "dateUpdate": null
  }
]
```

---

### Ejemplo de respuesta - GET `/api/entity-types/{id}`

```json
{
  "idEntityType": 1,
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

### Ejemplo de respuesta - POST `/api/entity-types`

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

### Ejemplo de respuesta - PUT `/api/entity-types/{id}`

```json
{
  "idEntityType": 1,
  "nameTypeEntity": "Institución educativa",
  "entitySize": "Grande",
  "sector": "Educación",
  "idUserCreate": 1,
  "idUserUpdate": 1,
  "dateCreate": "2026-05-03",
  "dateUpdate": "2026-05-13"
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

## Demostración con Postman

Para cumplir con la demostración manual de los endpoints, se utilizó Postman como cliente HTTP.

La API debe estar ejecutándose localmente en:

```text
http://localhost:8080
```

Antes de probar los endpoints en Postman, se debe verificar que:

- MySQL esté corriendo.
- La base de datos `reclamos_dev` exista.
- El script `database/schema.sql` haya sido ejecutado.
- Las variables de entorno `DB_USER` y `DB_PASSWORD` estén configuradas.
- La aplicación Spring Boot esté levantada en el puerto `8080`.

---

### Colección de Postman

Se creó una colección llamada:

```text
Reclamos API
```

Dentro de la colección se cargaron las requests necesarias para demostrar el CRUD completo de la tabla `entity_type`.

---

### Variable de entorno

Opcionalmente, se puede crear un environment llamado:

```text
Reclamos Local
```

Con la siguiente variable:

| Variable  | Valor                   |
|-----------|-------------------------|
| `baseUrl` | `http://localhost:8080` |

De esta forma, las URLs pueden escribirse usando:

```text
{{baseUrl}}/api/entity-types
```

También se puede usar la URL completa directamente:

```text
http://localhost:8080/api/entity-types
```

---

## Requests cargadas en Postman

### 1. Listar todos los tipos de entidad

```http
GET {{baseUrl}}/api/entity-types
```

Respuesta esperada:

```http
200 OK
```

Ejemplo de respuesta:

```json
[
  {
    "idEntityType": 1,
    "nameTypeEntity": "Comercio",
    "entitySize": "Mediana",
    "sector": "Comercio",
    "idUserCreate": 1,
    "idUserUpdate": null,
    "dateCreate": "2026-05-03",
    "dateUpdate": null
  }
]
```

---

### 2. Buscar un tipo de entidad por ID

```http
GET {{baseUrl}}/api/entity-types/{id}
```

Ejemplo:

```http
GET {{baseUrl}}/api/entity-types/1
```

Respuesta esperada:

```http
200 OK
```

Ejemplo de respuesta:

```json
{
  "idEntityType": 1,
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

### 3. Crear un tipo de entidad

```http
POST {{baseUrl}}/api/entity-types
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

Respuesta esperada:

```http
201 Created
```

Ejemplo de respuesta:

```json
{
  "idEntityType": 20,
  "nameTypeEntity": "Comercio",
  "entitySize": "Mediana",
  "sector": "Comercio",
  "idUserCreate": 1,
  "idUserUpdate": null,
  "dateCreate": "2026-05-13",
  "dateUpdate": null
}
```

---

### 4. Actualizar un tipo de entidad

```http
PUT {{baseUrl}}/api/entity-types/{id}
```

Ejemplo:

```http
PUT {{baseUrl}}/api/entity-types/20
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

Respuesta esperada:

```http
200 OK
```

Ejemplo de respuesta:

```json
{
  "idEntityType": 20,
  "nameTypeEntity": "Institución educativa",
  "entitySize": "Grande",
  "sector": "Educación",
  "idUserCreate": 1,
  "idUserUpdate": 1,
  "dateCreate": "2026-05-13",
  "dateUpdate": "2026-05-13"
}
```

---

### 5. Eliminar un tipo de entidad

```http
DELETE {{baseUrl}}/api/entity-types/{id}
```

Ejemplo:

```http
DELETE {{baseUrl}}/api/entity-types/20
```

Respuesta esperada:

```http
204 No Content
```

Esta respuesta no devuelve cuerpo, ya que el registro fue eliminado correctamente.

---

## Pruebas de errores controlados

Además del CRUD principal, se probaron errores controlados para verificar el manejo de excepciones y validaciones.

---

### 6. Error 404: ID inexistente

```http
GET {{baseUrl}}/api/entity-types/9999
```

Respuesta esperada:

```http
404 Not Found
```

Ejemplo de respuesta:

```json
{
  "timestamp": "2026-05-13T20:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "No existe un tipo de entidad con ID: 9999"
}
```

Este caso demuestra que la API responde correctamente cuando se consulta un recurso inexistente.

---

### 7. Error 400: campos obligatorios vacíos

```http
POST {{baseUrl}}/api/entity-types
```

Body inválido:

```json
{
  "nameTypeEntity": "",
  "entitySize": "",
  "sector": "",
  "idUserCreate": 1
}
```

Respuesta esperada:

```http
400 Bad Request
```

Ejemplo de respuesta:

```json
{
  "timestamp": "2026-05-13T20:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": {
    "nameTypeEntity": "El nombre del tipo de entidad es obligatorio",
    "entitySize": "El tamaño de la entidad es obligatorio",
    "sector": "El sector es obligatorio"
  }
}
```

Este caso demuestra que la API valida los datos de entrada antes de guardar información en la base de datos.

---

## Orden recomendado para la demostración en clase

Para exponer el funcionamiento del CRUD, se recomienda ejecutar las requests en este orden:

1. `POST /api/entity-types` — crear un nuevo tipo de entidad.
2. `GET /api/entity-types` — listar todos los tipos de entidad y verificar que el nuevo registro aparece.
3. `GET /api/entity-types/{id}` — consultar el registro creado por ID.
4. `PUT /api/entity-types/{id}` — actualizar el registro.
5. `GET /api/entity-types/{id}` — verificar que los cambios se aplicaron correctamente.
6. `DELETE /api/entity-types/{id}` — eliminar el registro.
7. `GET /api/entity-types/{id}` — verificar que el registro eliminado ya no existe.
8. `GET /api/entity-types/9999` — mostrar el error controlado `404 Not Found`.
9. `POST /api/entity-types` con campos vacíos — mostrar el error de validación `400 Bad Request`.

---

## Conclusión de la prueba con Postman

La demostración en Postman permite verificar que la API cumple con el CRUD completo:

| Operación          | Método | Endpoint                 | Resultado         |
|--------------------|--------|--------------------------|-------------------|
| Listar             | GET    | `/api/entity-types`      | `200 OK`          |
| Buscar por ID      | GET    | `/api/entity-types/{id}` | `200 OK`          |
| Crear              | POST   | `/api/entity-types`      | `201 Created`     |
| Actualizar         | PUT    | `/api/entity-types/{id}` | `200 OK`          |
| Eliminar           | DELETE | `/api/entity-types/{id}` | `204 No Content`  |
| ID inexistente     | GET    | `/api/entity-types/9999` | `404 Not Found`   |
| Validación fallida | POST   | `/api/entity-types`      | `400 Bad Request` |

Con estas pruebas se demuestra el funcionamiento completo de los endpoints asignados, incluyendo casos exitosos y errores controlados.
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