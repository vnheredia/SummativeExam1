# 📚 Sistema de Gestión de Biblioteca - Código Espagueti

Sistema en Java (monolítico) que implementa reglas de negocio para la gestión de libros, usuarios, préstamos, reservas y multas.

## 🚀 Funcionalidades

- **Libros**: registrar, editar, buscar, listar, eliminar (con control de stock y préstamos activos).
- **Usuarios**: registrar, editar, buscar, listar, eliminar (con control de préstamos activos y multas).
- **Préstamos**: máximo 3 activos, reduce stock, fechas (préstamo a 14 días), evita duplicados.
- **Devoluciones**: aumenta stock, calcula multa (1€/día de retraso).
- **Reservas**: solo usuarios registrados, prioridad en préstamo, una activa por par (usuario, libro).
- **Multas**: se acumulan, impiden nuevos préstamos hasta pagar.

## 🛠️ Tecnologías

- Java 11+
- Solo librerías estándar: `java.util.ArrayList`, `java.util.Scanner`, `java.time.LocalDate`, `java.time.temporal.ChronoUnit`

## 📂 Estructura (todo en un único archivo)

```
Main.java
├── Datos globales (ArrayLists paralelos)
├── Métodos de Libros
├── Métodos de Usuarios
├── Métodos de Préstamos
├── Métodos de Reservas
├── Métodos de Multas
└── Utilidades
```

## ▶️ Cómo ejecutar

### Opción 1: Terminal
```bash
javac Main.java
java Main
```
## 📋 Menú principal

```
1. Registrar Libro
2. Mostrar Libros
3. Buscar Libro
4. Editar Libro
5. Eliminar Libro
6. Registrar Usuario
7. Mostrar Usuarios
8. Buscar Usuario
9. Editar Usuario
10. Eliminar Usuario
11. Prestar Libro
12. Devolver Libro
13. Mostrar Prestamos
14. Reservar Libro
15. Cancelar Reserva
16. Mostrar Reservas
17. Pagar Multas
18. Salir
```
