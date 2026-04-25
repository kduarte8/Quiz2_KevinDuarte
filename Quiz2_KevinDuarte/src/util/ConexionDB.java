//src/util/ConexionDB.java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitario - Conexión a Oracle DB
 * Patrón Singleton para la conexión
 */
public class ConexionDB {

    private static final String URL      = "jdbc:oracle:thin:@192.168.254.215:1521:ORCL";
    private static final String USUARIO  = "admin";
    private static final String PASSWORD = "admin";

    private static Connection conexion = null;

    // Constructor privado - Singleton
    private ConexionDB() {}

    /**
     * Retorna una conexión activa a la BD.
     * Si ya existe y está abierta, la reutiliza.
     */
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida con Oracle.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Oracle no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔌 Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }
}
