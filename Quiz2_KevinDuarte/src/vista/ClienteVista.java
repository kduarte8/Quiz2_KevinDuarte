//src/vista/ClienteVista.java
package vista;

import controlador.ClienteControlador;
import modelo.Cliente;
import java.util.List;
import java.util.Scanner;

/**
 * Vista - Menú de consola para gestión de Clientes
 */
public class ClienteVista {

    private ClienteControlador ctrl;
    private Scanner scanner;

    public ClienteVista() {
        this.ctrl = new ClienteControlador();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     GESTIÓN DE CLIENTES      ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Registrar cliente          ║");
            System.out.println("║ 2. Listar clientes            ║");
            System.out.println("║ 3. Buscar cliente por ID      ║");
            System.out.println("║ 4. Actualizar cliente         ║");
            System.out.println("║ 5. Eliminar cliente           ║");
            System.out.println("║ 0. Volver al menú principal   ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("→ Opción: ");
            opcion = leerInt();

            switch (opcion) {
                case 1: registrar(); break;
                case 2: listar();    break;
                case 3: buscar();    break;
                case 4: actualizar(); break;
                case 5: eliminar();  break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void registrar() {
        System.out.println("\n── Nuevo Cliente ──");
        System.out.print("Nombre   : "); String nombre   = scanner.nextLine();
        System.out.print("Correo   : "); String correo   = scanner.nextLine();
        System.out.print("Teléfono : "); String telefono = scanner.nextLine();
        System.out.print("Dirección: "); String dir      = scanner.nextLine();
        ctrl.agregarCliente(nombre, correo, telefono, dir);
    }

    private void listar() {
        List<Cliente> lista = ctrl.obtenerClientes();
        if (lista.isEmpty()) { System.out.println("No hay clientes registrados."); return; }
        System.out.printf("\n%-5s %-20s %-25s %-15s%n", "ID", "Nombre", "Correo", "Teléfono");
        System.out.println("─".repeat(68));
        for (Cliente c : lista) {
            System.out.printf("%-5d %-20s %-25s %-15s%n",
                c.getId(), c.getNombre(), c.getCorreo(), c.getTelefono());
        }
    }

    private void buscar() {
        System.out.print("ID del cliente: "); int id = leerInt();
        Cliente c = ctrl.obtenerClientePorId(id);
        if (c != null) {
            System.out.println("\n── Datos del Cliente ──");
            System.out.println("ID       : " + c.getId());
            System.out.println("Nombre   : " + c.getNombre());
            System.out.println("Correo   : " + c.getCorreo());
            System.out.println("Teléfono : " + c.getTelefono());
            System.out.println("Dirección: " + c.getDireccion());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void actualizar() {
        System.out.print("ID del cliente a actualizar: "); int id = leerInt();
        Cliente c = ctrl.obtenerClientePorId(id);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }
        System.out.println("Datos actuales: " + c);
        System.out.print("Nuevo nombre   [" + c.getNombre()   + "]: "); String nombre   = scanner.nextLine();
        System.out.print("Nuevo correo   [" + c.getCorreo()   + "]: "); String correo   = scanner.nextLine();
        System.out.print("Nuevo teléfono [" + c.getTelefono() + "]: "); String telefono = scanner.nextLine();
        System.out.print("Nueva dirección[" + c.getDireccion()+ "]: "); String dir      = scanner.nextLine();
        if (nombre.isBlank())   nombre   = c.getNombre();
        if (correo.isBlank())   correo   = c.getCorreo();
        if (telefono.isBlank()) telefono = c.getTelefono();
        if (dir.isBlank())      dir      = c.getDireccion();
        ctrl.modificarCliente(id, nombre, correo, telefono, dir);
    }

    private void eliminar() {
        System.out.print("ID del cliente a eliminar: "); int id = leerInt();
        System.out.print("¿Confirmar eliminación? (s/n): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("s")) ctrl.borrarCliente(id);
        else System.out.println("Cancelado.");
    }

    private int leerInt() {
        try { int v = Integer.parseInt(scanner.nextLine()); return v; }
        catch (NumberFormatException e) { return -1; }
    }
}
