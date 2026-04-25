//src/Main.java
import vista.ClienteVista;
import vista.ServicioVista;
import util.ConexionDB;
import java.util.Scanner;

/**
 * Punto de entrada – Quiz2_KevinDuarte
 * Sistema CRUD con MVC + DAO + Oracle
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteVista  clienteVista  = new ClienteVista();
        ServicioVista servicioVista = new ServicioVista();

        int opcion;
        do {
            System.out.println("\n╔═════════════════════════════════════╗");
            System.out.println("║   QUIZ2 - KEVIN DUARTE | PROG 2     ║");
            System.out.println("║   Sistema de Clientes y Servicios    ║");
            System.out.println("╠═════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Clientes              ║");
            System.out.println("║  2. Gestión de Servicios             ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚═════════════════════════════════════╝");
            System.out.print("→ Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1: clienteVista.mostrarMenu();  break;
                case 2: servicioVista.mostrarMenu(); break;
                case 0:
                    System.out.println("Cerrando aplicación...");
                    ConexionDB.cerrar();
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
