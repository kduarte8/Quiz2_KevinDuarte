# README.md
# Quiz2_KevinDuarte – Programación 2

Proyecto CRUD completo con arquitectura **MVC + DAO** en Java conectado a Oracle Database (SQLplus).

## Arquitectura MVC

```
src/
├── Main.java                        ← Punto de entrada
├── modelo/
│   ├── Cliente.java                 ← Entidad Cliente
│   └── Servicio.java                ← Entidad Servicio
├── dao/
│   ├── ClienteDAO.java              ← CRUD BD - Cliente
│   └── ServicioDAO.java             ← CRUD BD - Servicio
├── controlador/
│   ├── ClienteControlador.java      ← Lógica de negocio
│   └── ServicioControlador.java
├── vista/
│   ├── ClienteVista.java            ← Menú consola
│   └── ServicioVista.java
└── util/
    └── ConexionDB.java              ← Singleton conexión Oracle
```

## 🗄️ Base de datos

Oracle Database XE – ejecutar `database.sql` en SQLplus:

```sql
@database.sql
```

Tablas creadas:
- **cliente** (id_cliente, nombre, correo, telefono, direccion, fecha_registro)
- **servicios** (id_servicio, nombre, descripcion, precio, disponible, id_cliente FK)

## ▶️ Cómo ejecutar

1. Instalar Oracle XE y configurar `util/ConexionDB.java` con tu usuario/password.
2. Ejecutar `database.sql` en SQLplus.
3. Agregar `ojdbc11.jar` al classpath.
4. Compilar y ejecutar `Main.java`.

```bash
javac -cp ojdbc11.jar -d out src/**/*.java src/Main.java
java  -cp out:ojdbc11.jar Main
```

## 📦 Dependencias

- JDK 11+
- Oracle JDBC Driver (`ojdbc11.jar`)
- Oracle Database XE

## 📋 Operaciones CRUD

| Operación | SQL           |
|-----------|---------------|
| Create    | INSERT INTO   |
| Read      | SELECT        |
| Update    | UPDATE SET    |
| Delete    | DELETE FROM   |

## 👤 Autor

Kevin Duarte – Universidad de Investigación y Desarrollo (UDI)  
Quiz 2 – Programación 2
