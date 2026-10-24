# API de Tipo de Cambio

Convierte montos entre monedas usando ExchangeRate-API y guarda cada conversión en H2.

**Stack:** Java 17, Spring Boot 4, OpenFeign, JPA, H2.

## Ejecutar

```bash
./mvnw spring-boot:run
```

## Endpoint

`POST http://localhost:8080/api/conversion`

```json
{ "monto": 100, "monedaOrigen": "USD", "monedaDestino": "PEN" }
```

Respuestas: `200` conversión correcta, `400` datos inválidos, `503` servicio externo no disponible.

Consola H2: `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:testdb`, usuario `sa`, password `password`).

## Arquitectura (DDD)

- **domain:** entidad, value objects, interfaces de repositorio y servicio, excepciones. Sin dependencias de framework.
- **application:** controller, servicio, DTOs, mapper y manejo de errores.
- **infrastructure:** cliente Feign y persistencia JPA.

## Pruebas

```bash
./mvnw test
```
