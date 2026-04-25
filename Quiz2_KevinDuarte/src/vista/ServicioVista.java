//src/vista/ServicioVista.java
package vista;

import controlador.ServicioControlador;
import modelo.Servicio;
import java.util.List;
import java.util.Scanner;

/**
 * Vista - Menú de consola para gestión de Servicios
 */
public class ServicioVista {

    private ServicioControlador ctrl;
    private Scanner scanner;

    public ServicioVista() {
        this.ctrl = new ServicioControlador();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     GESTIÓN DE SERVICIOS     ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Registrar servicio         ║");
            System.out.println("║ 2. Listar servicios           ║");
            System.out.println("║ 3. Buscar servicio por ID     ║");
            System.out.println("║ 4. Actualizar servicio        ║");
            System.out.println("║ 5. Eliminar servicio          ║");
            System.out.println("║ 0. Volver al menú principal   ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("→ Opción: ");
            opcion = leerInt();

            switch (opcion) {
                case 1: registrar();  break;
                case 2: listar();     break;
                case 3: buscar();     break;
                case 4: actualizar(); break;
                case 5: eliminar();   break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void registrar() {
        System.out.println("\n── Nuevo Servicio ──");
        System.out.print("Nombre      : "); String nombre = scanner.nextLine();
        System.out.print("Descripción : "); String desc   = scanner.nextLine();
        System.out.print("Precio      : "); double precio = leerDouble();
        System.out.print("ID Cliente  : "); int idCliente = leerInt();
        ctrl.agregarServicio(nombre, desc, precio, idCliente);
    }

    private void listar() {
        List<Servicio> lista = ctrl.obtenerServicios();
        if (lista.isEmpty()) { System.out.println("No hay servicios registrados."); return; }
        System.out.printf("\n%-5s %-20s %-12s %-10s %-10s%n", "ID", "Nombre", "Precio", "Disp.", "ClienteID");
        System.out.println("─".repeat(60));
        for (Servicio s : lista) {
            System.out.printf("%-5d %-20s %-12.2f %-10c %-10d%n",
                s.getId(), s.getNombre(), s.getPrecio(), s.getDisponible(), s.getIdCliente());
        }
    }

    private void buscar() {
        System.out.print("ID del servicio: "); int id = leerInt();
        Servicio s = ctrl.obtenerServicioPorId(id);
        if (s != null) {
            System.out.println("\n── Datos del Servicio ──");
            System.out.println("ID          : " + s.getId());
            System.out.println("Nombre      : " + s.getNombre());
            System.out.println("Descripción : " + s.getDescripcion());
            System.out.println("Precio      : $" + s.getPrecio());
            System.out.println("Disponible  : " + s.getDisponible());
            System.out.println("Cliente ID  : " + s.getIdCliente());
        } else {
            System.out.println("Servicio no encontrado.");
        }
    }

    private void actualizar() {
        System.out.print("ID del servicio a actualizar: "); int id = leerInt();
        Servicio s = ctrl.obtenerServicioPorId(id);
        if (s == null) { System.out.println("Servicio no encontrado."); return; }
        System.out.print("Nuevo nombre      [" + s.getNombre()      + "]: "); String nombre = scanner.nextLine();
        System.out.print("Nueva descripción [" + s.getDescripcion() + "]: "); String desc   = scanner.nextLine();
        System.out.print("Nuevo precio      [" + s.getPrecio()      + "]: "); String pStr   = scanner.nextLine();
        System.out.print("Disponible (S/N)  [" + s.getDisponible()  + "]: "); String dispStr = scanner.nextLine();
        System.out.print("ID Cliente        [" + s.getIdCliente()   + "]: "); String idCliStr = scanner.nextLine();

        if (nombre.isBlank())  nombre  = s.getNombre();
        if (desc.isBlank())    desc    = s.getDescripcion();
        double precio = pStr.isBlank()    ? s.getPrecio()     : Double.parseDouble(pStr);
        char disp     = dispStr.isBlank() ? s.getDisponible() : dispStr.toUpperCase().charAt(0);
        int idCli     = idCliStr.isBlank()? s.getIdCliente()  : Integer.parseInt(idCliStr);

        ctrl.modificarServicio(id, nombre, desc, precio, disp, idCli);
    }

    private void eliminar() {
        System.out.print("ID del servicio a eliminar: "); int id = leerInt();
        System.out.print("¿Confirmar eliminación? (s/n): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("s")) ctrl.borrarServicio(id);
        else System.out.println("Cancelado.");
    }

    private int leerInt() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { return -1; }
    }

    private double leerDouble() {
        try { return Double.parseDouble(scanner.nextLine()); }
        catch (NumberFormatException e) { return 0.0; }
    }
}
