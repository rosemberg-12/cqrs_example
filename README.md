# Demo patrón CQRS Biblioteca 

# Grupo 9. Integrantes:
Angie Lorena Prieto Dominguez

Rosemberg Porras Mancilla

Ivan Felipe Vera Triana

Juan Felipe Gonzalez Ortiz

Leonardo Pérez Ramírez

---


## Descripción

Este proyecto es una demostración académica de la arquitectura CQRS (Command Query Responsibility Segregation) aplicada sobre Clean Architecture usando Spring Boot. El objetivo es mostrar de forma didáctica cómo separar completamente el modelo de escritura (commands) del modelo de lectura (queries) en un sistema de gestión de biblioteca.

---

## ¿Qué es CQRS?

CQRS es un patrón arquitectónico que propone separar las operaciones de escritura (Command) de las operaciones de lectura (Query) en un sistema. Esto permite que cada lado evolucione de manera independiente, optimizando la lógica de negocio y la consulta de datos.

---

## ¿Por qué CQRS en este proyecto?

- Permite demostrar la separación de responsabilidades de forma clara.
- Facilita la explicación académica de los flujos de modificación y consulta.
- Mejora la mantenibilidad y escalabilidad del sistema.
- Evidencia cómo la lógica de negocio vive en el dominio y no en los controladores.

---

## Estructura del Proyecto

```
com.biblioteca.cqrs
├── application
│   ├── command        # DTOs de escritura
│   ├── query          # DTOs de consulta
│   └── handler        # Orquestadores de comandos y queries
├── domain
│   └── Libro          # Lógica de negocio y entidades del dominio
└── infrastructure
    ├── http           # Adaptador HTTP (controllers REST)
    └── repository     # Repositorios en memoria (HashMap)

```

### Domain

Contiene la lógica de negocio pura. Ejemplo: métodos prestar(), devolver(), estaDisponible() en la entidad Libro.

### Application

Contiene los DTOs de Command y Query, y los handlers que orquestan el flujo de cada operación.

### Infrastructure

Implementa los repositorios en memoria, sin lógica de negocio, solo almacenamiento.

### HTTP Adapter

Expone los endpoints REST, delegando la lógica a los handlers. No contiene lógica de negocio.

---

## Flujo de Ejecución

### Registrar libro

1. Controller recibe RegistrarLibroCommand.
2. Handler crea la entidad Libro y la guarda en el repositorio.
3. Logging: [COMMAND] → [DOMAIN]

### Prestar libro

1. Controller recibe PrestarLibroCommand.
2. Handler busca el libro, llama prestar() y guarda el estado.
3. Logging: [COMMAND] → [DOMAIN]

### Devolver libro

1. Controller recibe DevolverLibroCommand.
2. Handler busca el libro, llama devolver() y guarda el estado.
3. Logging: [COMMAND] → [DOMAIN]

### Consultar libro

1. Controller recibe ObtenerLibroQuery.
2. Handler busca el libro y retorna un DTO de lectura.
3. Logging: [QUERY]

---

## Separación entre Command y Query

- Los Commands modifican el estado del sistema.
- Los Queries solo leen datos, sin modificar el estado.
- Cada handler tiene una responsabilidad única.
- No se mezclan flujos de lectura y escritura.

---

## Comparación CQRS vs CRUD tradicional

| CQRS                        | CRUD tradicional           |
|-----------------------------|---------------------------|
| Separación Command/Query    | Métodos combinados        |
| Lógica de negocio en dominio| Lógica en controller      |
| Handlers orquestan          | Controladores gestionan   |
| Escalabilidad didáctica     | Simplicidad, menos clara  |

---

## Diagrama de Clases (Mermaid)

```mermaid
classDiagram
    %% === HANDLERS ===
    class RegistrarLibroCommandHandler
    class PrestarLibroCommandHandler
    class DevolverLibroCommandHandler
    class ObtenerLibroQueryHandler
    <<handler>> RegistrarLibroCommandHandler
    <<handler>> PrestarLibroCommandHandler
    <<handler>> DevolverLibroCommandHandler
    <<handler>> ObtenerLibroQueryHandler

    %% === COMMANDS ===
    class RegistrarLibroCommand
    class PrestarLibroCommand
    class DevolverLibroCommand
    <<command>> RegistrarLibroCommand
    <<command>> PrestarLibroCommand
    <<command>> DevolverLibroCommand

    %% === QUERIES ===
    class ObtenerLibroQuery
    class LibroResponse
    <<query>> ObtenerLibroQuery
    <<query>> LibroResponse

    %% === DOMAIN & REPOSITORY ===
    class Libro {
        +String id
        +String titulo
        +boolean prestado
        +String usuarioId
        +prestar(usuarioId)
        +devolver()
        +estaDisponible()
    }
    <<domain>> Libro
    class LibroRepository
    <<repository>> LibroRepository

    %% === RELACIONES (de arriba hacia abajo) ===
    %% Command Handlers
    RegistrarLibroCommandHandler --> RegistrarLibroCommand : ejecuta
    RegistrarLibroCommandHandler --> Libro : crea
    RegistrarLibroCommandHandler --> LibroRepository : guarda

    PrestarLibroCommandHandler --> PrestarLibroCommand : ejecuta
    PrestarLibroCommandHandler --> Libro : modifica
    PrestarLibroCommandHandler --> LibroRepository : consulta/guarda

    DevolverLibroCommandHandler --> DevolverLibroCommand : ejecuta
    DevolverLibroCommandHandler --> Libro : modifica
    DevolverLibroCommandHandler --> LibroRepository : consulta/guarda

    %% Query Handler
    ObtenerLibroQueryHandler --> ObtenerLibroQuery : ejecuta
    ObtenerLibroQueryHandler --> LibroRepository : consulta
    ObtenerLibroQueryHandler --> LibroResponse : retorna

    %% === Agrupación visual (comentarios) ===
    %% Handlers arriba, Commands/Queries al centro, Dominio/Repositorio abajo
    %% Flechas solo hacia abajo para evitar cruces
```

---

## Diagrama de Flujo (Mermaid)

```mermaid
flowchart TD
    A[Controller] --> B[CommandHandler]
    B --> C[Domain]
    C --> D[Repository]

    A[Controller] --> E[QueryHandler]
    E --> D[Repository]
```

---

## Ejemplo de logs reales

```
[COMMAND] Ejecutando RegistrarLibroCommand: 123 - Clean Architecture
[DOMAIN] Libro marcado como prestado: 123 por usuario 456
[COMMAND] Ejecutando DevolverLibroCommand para libro 123
[DOMAIN] Libro marcado como devuelto: 123
[QUERY] Consultando libro 123
```

---
