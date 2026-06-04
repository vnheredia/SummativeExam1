# 📚 Sistema de Gestión de Biblioteca – Monolito por Capas

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![Swing](https://img.shields.io/badge/GUI-Swing-orange)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

## 📖 Descripción

Sistema de escritorio para la gestión integral de una biblioteca.  
Permite administrar **libros**, **usuarios**, **préstamos**, **reservas** y **multas**, siguiendo una arquitectura **monolítica por capas** que separa claramente la lógica de negocio, la persistencia en memoria y la interfaz de usuario.

Este proyecto es la **refactorización completa** de un código espagueti original, aplicando buenas prácticas de diseño y mantenibilidad.

---

## 🏗️ Arquitectura

El sistema se organiza en **4 capas** bien diferenciadas:

| Capa            | Propósito                                                                 |
|-----------------|---------------------------------------------------------------------------|
| **Modelo**      | Entidades de negocio (`Libro`, `Usuario`, `Prestamo`, `Reserva`, `Multa`) |
| **Repositorio** | Almacenamiento en memoria (Singleton) y operaciones CRUD básicas          |
| **Servicio**    | Lógica de negocio (reglas: máx. 3 préstamos, multas, reservas, etc.)      |
| **UI**          | Interfaz gráfica con Swing (pestañas, tablas, diálogos modales)           |

**Tecnologías usadas:**
- Java 17+
- Swing (JFrame, JTable, JTabbedPane, JOptionPane)
- java.util.UUID (identificadores únicos)
- java.time (LocalDate, ChronoUnit)

---

## ✨ Funcionalidades

### 📕 Libros
- Registrar (código, título, autor, stock inicial)
- Listar, buscar por código, editar y eliminar
- Eliminación solo si no tiene préstamos ni reservas activas

### 👥 Usuarios
- Registrar (nombre + ID automático o manual)
- Listar, buscar, editar y eliminar
- Eliminación solo si no tiene préstamos activos ni multas pendientes

### 📖 Préstamos
- Prestar libro (valida stock, límite de 3 activos, multas pendientes, reservas de terceros)
- Generación automática de fecha límite (14 días)
- Devolución con cálculo de multas (1€/día de retraso)
- Vista de todos los préstamos con estado

### 🔖 Reservas
- Reservar libro disponible (incluso si no hay stock)
- Cancelar reserva activa
- Cuando se presta el libro al usuario que lo reservó, la reserva pasa a “COMPLETADA”

### 💰 Multas
- Acumulación por cada día de retraso en devolución
- Consulta de multa pendiente por usuario
- Pago total de la multa

### 🖥️ Interfaz gráfica
- Ventana principal con 5 pestañas (Libros, Usuarios, Préstamos, Reservas, Multas)
- Tablas actualizables en tiempo real
- Diálogos con combos para seleccionar usuario/libro (no se necesitan copiar IDs manualmente)

---

## 🚀 Instalación y ejecución

### Requisitos previos
- JDK 17 o superior
- Git (opcional)

### Pasos

1. **Clonar el repositorio** (rama `feature/Monolito-Capas`):
   ```bash
   git clone -b feature/Monolito-Capas https://github.com/vnheredia/SumativeExam1.git
   ```

2. **Compilar** (desde la raíz del proyecto):
   ```bash
   javac -d out src/com/biblioteca/**/*.java src/com/biblioteca/Main.java
   ```

3. **Ejecutar**:
   ```bash
   java -cp out com.biblioteca.Main
   ```

También puedes abrir el proyecto en **IntelliJ IDEA**, **Eclipse** o **VS Code** con la extensión de Java y ejecutar directamente la clase `Main`.

---

## 🗂️ Estructura de paquetes

```
MonolitoCapas/
└── src/
    └── com/
        └── biblioteca/
            ├── Main.java                  # Punto de entrada 
            ├── model/                     # Entidades
            ├── repository/                # Singleton repositorios en memoria
            ├── service/                   # Lógica de negocio
            └── ui/
                └── BibliotecaGUI.java     # Interfaz gráfica principal
```

---

## 🔧 Decisiones técnicas destacadas

### ✅ Uso de `UUID` en lugar de contadores manuales
- Cada libro, usuario, préstamo y reserva recibe un `UUID.randomUUID()`.
- Evita colisiones, reinicios de contadores y permite una futura migración a BD distribuidas.

### ✅ Separación estricta de capas
- La UI **no** conoce los repositorios, solo los servicios.
- Los servicios inyectan sus dependencias (otros servicios y repositorios).

### ✅ Interfaz amigable sin necesidad de recordar UUIDs
- Al prestar o reservar, se muestran **combos** con nombres/títulos legibles.
- El usuario nunca escribe un UUID manualmente (aunque internamente se usen).

### ✅ Persistencia en memoria (volátil)
- Ideal para demostración y pruebas. Para entornos productivos, se podría reemplazar `Repository` por JDBC/JPA sin afectar las otras capas.

---

