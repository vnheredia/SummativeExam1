# 📚 Biblioteca DDD – Sistema de Gestión con EnfoqueDDD

## 📌 Descripción del Proyecto

Aplicación web para la gestión de bibliotecas que implementa los principios de **Domain-Driven Design (DDD)** y **Arquitectura Hexagonal (Ports & Adapters)**. El sistema permite gestionar usuarios, catálogo de libros, préstamos, reservas y multas, con reglas de negocio como:

- Máximo 3 préstamos activos por usuario.
- Plazo de 14 días para devolución.
- Multa de 5$ por día de retraso.
- Reserva de libros cuando no hay stock.
- Validación de multas pendientes antes de prestar.

El backend está desarrollado con **Spring Boot** y el frontend con **HTML, CSS y JavaScript puro**, ambos comunicándose a través de una API REST.

---

## 🧱 Estructura del Proyecto (Arquitectura Hexagonal)

```
EnfoqueDDD/
├── bibliotecaddd/
│   ├── backend/
│   │   └── src/main/java/com/biblioteca/bibliotecaddd/
│   │       ├── catalog/          # Contexto de Catálogo (Libros)
│   │       ├── users/            # Contexto de Usuarios (Usuarios y Multas)
│   │       ├── loans/            # Contexto de Préstamos (Core Domain)
│   │       └── shared/           # Código común (Value Objects, excepciones, eventos)
│   └── frontend/                 # HTML, CSS, JS puro
└── README.md
```

Cada contexto sigue la separación hexagonal:
- **domain**: Entidades, Value Objects, servicios de dominio, puertos (repositorios).
- **application**: Casos de uso (puertos de entrada), DTOs, servicios de aplicación.
- **infrastructure**: Adaptadores (controladores REST, persistencia JPA, adaptadores para otros contextos).

---

## ⚙️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **H2 Database** (en memoria, para demostración)
- **Maven**
- **HTML5 / CSS3 / JavaScript (ES6)**

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/vnheredia/biblioteca-ddd.git
cd biblioteca-ddd/EnfoqueDDD
```

### 2. Ejecutar el backend (Spring Boot)

#### Opción A: Desde el IDE (IntelliJ, Eclipse)
- Abrir el proyecto como Maven.
- Localizar la clase `BibliotecadddApplication` en `com.biblioteca.bibliotecaddd`.
- Ejecutar como aplicación Java.

#### Opción B: Desde la terminal (Maven)

```bash
cd bibliotecaddd/backend
./mvnw spring-boot:run   # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

El backend arrancará en `http://localhost:8080`.

### 3. Servir el frontend

Los archivos estáticos se encuentran en `frontend/`. Copie todo su contenido dentro de `src/main/resources/static/` del backend. Una vez hecho, acceda a:

```
http://localhost:8080/index.html
```

Si prefiere servir el frontend por separado (requiere habilitar CORS en Spring Boot), puede usar Live Server o `python -m http.server`.

---

## 📡 Endpoints Principales de la API REST

| Método | Endpoint                        | Descripción                        |
|--------|--------------------------------|------------------------------------|
| GET    | /api/usuarios                  | Listar todos los usuarios          |
| POST   | /api/usuarios                  | Registrar nuevo usuario            |
| PUT    | /api/usuarios/{id}             | Editar usuario                     |
| DELETE | /api/usuarios/{id}             | Eliminar usuario                   |
| GET    | /api/libros                    | Listar todos los libros            |
| POST   | /api/libros                    | Registrar nuevo libro              |
| PUT    | /api/libros/{codigo}           | Editar libro                       |
| DELETE | /api/libros/{codigo}           | Eliminar libro                     |
| GET    | /api/prestamos/prestamos       | Listar todos los préstamos         |
| POST   | /api/prestamos/prestar         | Realizar un préstamo               |
| PUT    | /api/prestamos/devolver        | Devolver un libro                  |
| GET    | /api/prestamos/reservas        | Listar reservas                    |
| POST   | /api/prestamos/reservar        | Crear reserva                      |
| PUT    | /api/prestamos/cancelar-reserva| Cancelar reserva                   |
| POST   | /api/prestamos/pagar-multas    | Pagar multas de un usuario         |

> Consultar el código de los controladores para ver los DTOs exactos.

---


## 📁 Decisiones Arquitectónicas Clave

- **Bounded Contexts**: separación en Catálogo, Usuarios y Préstamos para mantener el dominio desacoplado.
- **Core Domain**: Préstamos, donde reside la lógica de negocio más valiosa (límite de préstamos, multas, reservas).
- **Puertos y Adaptadores**: cada contexto define puertos de entrada (use cases) y salida (repositorios). Las dependencias se invierten mediante inyección.
- **Value Objects**: `CodigoLibro`, `UserId`, `MontoMulta` encapsulan validaciones y comportamientos.
- **Eventos de Dominio**: `LibroDevueltoEvent` notifica la generación de multas al contexto de Usuarios.

---

