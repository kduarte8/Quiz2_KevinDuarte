# Manual de Usuario – Quiz2_KevinDuarte
## Sistema de Gestión de Clientes y Servicios
### Programación 2 – Kevin Duarte | UDI

---

## 1. Introducción

Este documento describe el proceso de construcción, configuración y uso del sistema **Quiz2_KevinDuarte**, una aplicación Java de consola que implementa un CRUD completo sobre dos entidades: **Cliente** y **Servicio**, utilizando arquitectura MVC y el patrón DAO para acceder a una base de datos Oracle 10g.

---

## 2. Requisitos del sistema

| Componente        | Versión utilizada            |
|-------------------|------------------------------|
| Java JDK          | 25 (compilación) / target 8  |
| Oracle Database   | 10g Enterprise 10.2.0.1.0    |
| Oracle JDBC       | ojdbc14.jar                  |
| IDE               | Visual Studio Code           |
| Sistema operativo | Windows 11                   |

---

## 3. Configuración de la base de datos

### 3.1 Conectarse a Oracle desde SQLplus

Abrir CMD y ejecutar:

```
sqlplus admin/admin@orcl
```

Esto conecta al servidor Oracle en la red del salón (192.168.254.215:1521).

### 3.2 Crear las tablas con secuencias y triggers

Dado que el servidor usa Oracle 10g (no soporta IDENTITY), se usan secuencias y triggers para el auto-incremento. Pegar y ejecutar en SQLplus:

```sql
-- Eliminar si existen
BEGIN EXECUTE IMMEDIATE 'DROP TABLE servicios CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE cliente CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_cliente'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_servicio'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- Secuencias para auto-incremento
CREATE SEQUENCE seq_cliente  START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_servicio START WITH 1 INCREMENT BY 1;

-- Tabla CLIENTE
CREATE TABLE cliente (
    id_cliente     NUMBER PRIMARY KEY,
    nombre         VARCHAR2(80)  NOT NULL,
    correo         VARCHAR2(100),
    telefono       VARCHAR2(20),
    direccion      VARCHAR2(150),
    fecha_registro DATE DEFAULT SYSDATE
);

-- Trigger auto-incremento cliente
CREATE OR REPLACE TRIGGER trg_cliente
BEFORE INSERT ON cliente
FOR EACH ROW
BEGIN
  SELECT seq_cliente.NEXTVAL INTO :NEW.id_cliente FROM dual;
END;
/

-- Tabla SERVICIOS
CREATE TABLE servicios (
    id_servicio  NUMBER PRIMARY KEY,
    nombre       VARCHAR2(80)   NOT NULL,
    descripcion  VARCHAR2(200),
    precio       NUMBER(10,2)   DEFAULT 0,
    disponible   CHAR(1)        DEFAULT 'S' CHECK (disponible IN ('S','N')),
    id_cliente   NUMBER,
    CONSTRAINT fk_servicio_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
        ON DELETE SET NULL
);

-- Trigger auto-incremento servicios
CREATE OR REPLACE TRIGGER trg_servicio
BEFORE INSERT ON servicios
FOR EACH ROW
BEGIN
  SELECT seq_servicio.NEXTVAL INTO :NEW.id_servicio FROM dual;
END;
/

-- Datos de prueba
INSERT INTO cliente (nombre, correo, telefono, direccion)
VALUES ('Juan Perez', 'juan@mail.com', '3001234567', 'Calle 10 #5-20');

INSERT INTO cliente (nombre, correo, telefono, direccion)
VALUES ('Maria Lopez', 'maria@mail.com', '3109876543', 'Carrera 15 #8-45');

INSERT INTO servicios (nombre, descripcion, precio, id_cliente)
VALUES ('Mantenimiento PC', 'Revisión y limpieza de equipo', 80000, 1);

INSERT INTO servicios (nombre, descripcion, precio, id_cliente)
VALUES ('Diseño Web', 'Página web empresarial', 350000, 2);

COMMIT;
```

### 3.3 Verificar las tablas

```sql
SELECT * FROM cliente;
SELECT * FROM servicios;
```

---

## 4. Configuración del proyecto Java

### 4.1 Estructura de carpetas

```
Quiz2_KevinDuarte/
├── src/
│   ├── Main.java
│   ├── modelo/      (Cliente.java, Servicio.java)
│   ├── dao/         (ClienteDAO.java, ServicioDAO.java)
│   ├── controlador/ (ClienteControlador.java, ServicioControlador.java)
│   ├── vista/       (ClienteVista.java, ServicioVista.java)
│   └── util/        (ConexionDB.java)
├── out/             (clases compiladas - generado automáticamente)
├── database.sql
├── MANUAL_USUARIO.md
└── README.md
```

### 4.2 Driver Oracle JDBC

El driver usado es `ojdbc14.jar`, ubicado en:

```
C:\Oracle\product\10.1.0\Client_1\jdbc\lib\ojdbc14.jar
```

### 4.3 Configurar la conexión en ConexionDB.java

Abrir `src/util/ConexionDB.java` y verificar:

```java
private static final String URL      = "jdbc:oracle:thin:@192.168.254.215:1521:ORCL";
private static final String USUARIO  = "admin";
private static final String PASSWORD = "admin";
```

> **Nota:** La IP `192.168.254.215` corresponde al servidor Oracle del salón de clases UDI. El puerto es 1521 y el SID es ORCL.

---

## 5. Compilación y ejecución

### Desde CMD (Windows)

```cmd
cd C:\Users\SALA-404\Desktop\Quiz2_KevinDuarte

rmdir /s /q out
mkdir out

javac -encoding UTF-8 -source 8 -target 8 -cp "C:\Oracle\product\10.1.0\Client_1\jdbc\lib\ojdbc14.jar" -d out src\util\ConexionDB.java src\modelo\Cliente.java src\modelo\Servicio.java src\dao\ClienteDAO.java src\dao\ServicioDAO.java src\controlador\ClienteControlador.java src\controlador\ServicioControlador.java src\vista\ClienteVista.java src\vista\ServicioVista.java src\Main.java

"C:\Program Files\Common Files\Oracle\Java\javapath\java.exe" -cp "out;C:\Oracle\product\10.1.0\Client_1\jdbc\lib\ojdbc14.jar" Main
```

> **Importante:** Se usa el `java.exe` de la ruta de Oracle Java porque el CMD del sistema tiene Java 1.4 que no es compatible con el bytecode generado.

---

## 6. Uso del sistema

### 6.1 Menú principal

Al iniciar la aplicación aparece:

```
╔═════════════════════════════════════╗
║   QUIZ2 - KEVIN DUARTE | PROG 2     ║
║   Sistema de Clientes y Servicios    ║
╠═════════════════════════════════════╣
║  1. Gestión de Clientes              ║
║  2. Gestión de Servicios             ║
║  0. Salir                            ║
╚═════════════════════════════════════╝
→ Seleccione una opción:
```

### 6.2 Módulo de Clientes

| Opción | Operación | Descripción |
|--------|-----------|-------------|
| 1 | CREATE | Solicita nombre, correo, teléfono y dirección para insertar un nuevo cliente |
| 2 | READ   | Muestra tabla con todos los clientes registrados |
| 3 | READ   | Busca y muestra un cliente por su ID |
| 4 | UPDATE | Permite modificar los datos de un cliente existente |
| 5 | DELETE | Elimina un cliente tras confirmación |
| 0 | SALIR  | Vuelve al menú principal |

### 6.3 Módulo de Servicios

| Opción | Operación | Descripción |
|--------|-----------|-------------|
| 1 | CREATE | Solicita nombre, descripción, precio e ID de cliente |
| 2 | READ   | Lista todos los servicios con estado y precio |
| 3 | READ   | Busca un servicio por ID |
| 4 | UPDATE | Modifica los datos del servicio |
| 5 | DELETE | Elimina el servicio tras confirmación |
| 0 | SALIR  | Vuelve al menú principal |

---

## 7. Diagrama de flujo del sistema

```
Usuario → Vista → Controlador → DAO → Oracle DB
                               ← DAO ← Datos
         ← Vista ← Controlador
```

---

## 8. Subir el proyecto a GitHub

Desde la interfaz web de GitHub (github.com/kduarte8/Quiz2_KevinDuarte):

1. Clic en **"uploading an existing file"**
2. Subir archivos — mensaje: `feat: agregar script SQL y README del proyecto`
3. Segunda carga — mensaje: `feat: agregar modelos Cliente/Servicio y DAOs con CRUD`
4. Tercera carga — mensaje: `feat: agregar controladores, vistas y Main - MVC completo`

---

## 9. Preguntas conceptuales

### ¿Qué es un CRUD?
CRUD son las siglas de **Create, Read, Update y Delete**. Representan las cuatro operaciones esenciales sobre datos en cualquier sistema de información. En este proyecto, cada DAO implementa estos cuatro métodos: `insertar()`, `listar()/buscarPorId()`, `actualizar()` y `eliminar()`.

### ¿Qué es el DAO y para qué sirve?
El **Data Access Object** es un patrón de diseño que encapsula toda la lógica de acceso a la base de datos en clases separadas (`ClienteDAO`, `ServicioDAO`). Su propósito es desacoplar la capa de negocio de la capa de persistencia, facilitando el mantenimiento y la posibilidad de cambiar la base de datos sin modificar el resto de la aplicación.

---

## 10. Autor

**Kevin Duarte** – kduarte8  
Universidad de Investigación y Desarrollo – UDI  
Quiz 2 – Programación 2  
Repositorio: https://github.com/kduarte8/Quiz2_KevinDuarte
