# 📚 Comparativa de Arquitecturas: 3 Enfoques para un Sistema de Biblioteca

Este repositorio contiene **tres implementaciones diferentes** del mismo dominio de negocio (gestión de biblioteca con préstamos, reservas y multas). Cada una representa un estilo arquitectónico distinto, con el objetivo de analizar sus ventajas, desventajas y el impacto en la mantenibilidad, testabilidad y evolución del software.

## 📁 Estructura del Repositorio

```
SumativeExam1/
├── feature/EnfoqueDDD/              # DDD + Arquitectura Hexagonal (final)
├── feature/Espagueti/               # Código Espagueti (todo en un solo Main)
├── feature/MonolitoCapas/           # Monolito por Capas (presentación, lógica, datos)
└── README.md                # Este archivo
```

---

## 1️⃣ Proyecto: `Espagueti` – Código Espagueti (Spaghetti Code)

### Descripción
Implementación inicial, totalmente acoplada y sin separación de responsabilidades. Todo el sistema (entrada de usuario, lógica de negocio, persistencia en memoria) reside en una única clase `Main.java`. Se utilizan `ArrayList` paralelos como pseudo-tablas.

### Características
- Sin modelos de dominio explícitos (solo arrays y variables primitivas).
- Las reglas de negocio están mezcladas con código de presentación (`Scanner`) y persistencia.
- Dificultad extrema para testear, mantener o evolucionar.
- La interfaz es por consola.

### ¿Qué aprendemos?
- **Problemas**: Alto acoplamiento, duplicación, baja cohesión.
- **Indicador**: Un simple cambio en una regla obliga a modificar múltiples partes.

---

## 2️⃣ Proyecto: `MonolitoCapas` – Monolito por Capas Tradicional

### Descripción
Sigue la arquitectura clásica de tres capas: **Presentación (consola/web)**, **Lógica de negocio (servicios)** y **Datos (repositorios en memoria o JDBC)**. Aunque hay separación de paquetes, las dependencias fluyen hacia abajo y la lógica de dominio suele filtrarse a las capas superiores.

### Características
- Capas bien definidas (controlador, servicio, repositorio).
- Los modelos son anémicos (solo getters/setters).
- La lógica de negocio reside en los servicios (transaccional).
- Dependencia fuerte de la base de datos y del framework.

### ¿Qué aprendemos?
- **Ventajas**: Separación básica, más fácil de testear que espagueti.
- **Problemas**: La lógica de dominio se dispersa, se viola el principio de inversión de dependencias (DIP), difícil de escalar a dominios complejos.

---

## 3️⃣ Proyecto: `EnfoqueDDD` – Domain‑Driven Design + Arquitectura Hexagonal (Final)

### Descripción
Aplicación completa que aplica **DDD estratégico y táctico** junto con **Arquitectura Hexagonal (Ports & Adapters)**. El sistema está dividido en tres **Bounded Contexts** (Catálogo, Usuarios, Préstamos). Cada contexto tiene su propio modelo de dominio, con **Entidades**, **Value Objects**, **Agregados**, **Repositorios como puertos** y **Servicios de Dominio**. La infraestructura (persistencia JPA, controladores REST) es un detalle que se conecta mediante puertos.

### Características
- **Lenguaje Ubicuo**: Términos como `Préstamo`, `Reserva`, `Multa`, `Stock` tienen significado exacto en el código.
- **Core Domain**: El contexto de Préstamos contiene la lógica más valiosa (límite de 3, multas, reservas).
- **Arquitectura Hexagonal**: Cada capa (dominio, aplicación, infraestructura) está desacoplada mediante puertos.
- **Value Objects inmutables**: `CodigoLibro`, `UserId`, `MontoMulta` con validaciones y comportamientos.
- **Eventos de Dominio**: `LibroDevueltoEvent` notifica la generación de multas.
- **Frontend en HTML/CSS/JS puro** que consume la API REST.

### ¿Qué aprendemos?
- **Ventajas**: Código muy mantenible, testeable (mockear puertos), adaptable a cambios de negocio.
- **Complejidad inicial**: Mayor curva de aprendizaje, más archivos/clases.
- **Resultado**: Sistema preparado para evolucionar a microservicios si se desea.

---

## 🧪 Comparativa Rápida

| Aspecto                     | Espagueti        | Monolito Capas   | DDD + Hexagonal         |
|-----------------------------|------------------|------------------|--------------------------|
| Separación de responsabilidades | ❌ Ninguna      | ⚠️ Parcial       | ✅ Total (puertos)       |
| Testabilidad                | ❌ Muy difícil   | ⚠️ Media         | ✅ Alta (mockear puertos)|
| Mantenibilidad              | ❌ Muy baja      | ⚠️ Regular       | ✅ Alta                  |
| Modelado del dominio        | ❌ Anémico       | ❌ Anémico        | ✅ Rico (Value Objects)  |
| Independencia de framework  | ❌ Dependiente   | ⚠️ Parcial       | ✅ Total (adaptadores)   |
| Facilidad para agregar funcionalidad | ❌ Muy difícil | ⚠️ Media | ✅ Alta (nuevo caso de uso) |
| Curva de aprendizaje        | ✅ Baja          | ✅ Baja          | ⚠️ Alta                  |

---

## 🚀 Cómo Ejecutar Cada Proyecto

### Espagueti
```bash
cd Espagueti
javac Main.java
java Main
```
(Interfaz por consola)

### MonolitoCapas
(La estructura puede variar; normalmente requiere compilar y ejecutar la clase principal de presentación. Si es web, usar Tomcat/Jetty.)

### EnfoqueDDD (backend + frontend)
```bash
cd EnfoqueDDD/bibliotecaddd/backend
mvn spring-boot:run
```
Luego acceder a `http://localhost:8080/index.html` (frontend en `static`).

---
