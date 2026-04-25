//src/controlador/ClienteControlador.java
package controlador;

import dao.ClienteDAO;
import modelo.Cliente;
import java.util.List;

/**
 * Controlador - Lógica de negocio para Cliente
 * Intermediario entre Vista y DAO
 */
public class ClienteControlador {

    private ClienteDAO dao;

    public ClienteControlador() {
        this.dao = new ClienteDAO();
    }

    public boolean agregarCliente(String nombre, String correo, String telefono, String direccion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio.");
            return false;
        }
        Cliente c = new Cliente(nombre.trim(), correo, telefono, direccion);
        boolean ok = dao.insertar(c);
        if (ok) System.out.println("Cliente registrado: " + nombre);
        return ok;
    }

    public List<Cliente> obtenerClientes() {
        return dao.listar();
    }

    public Cliente obtenerClientePorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean modificarCliente(int id, String nombre, String correo, String telefono, String direccion) {
        Cliente c = new Cliente(id, nombre, correo, telefono, direccion);
        boolean ok = dao.actualizar(c);
        if (ok) System.out.println("Cliente actualizado: " + id);
        return ok;
    }

    public boolean borrarCliente(int id) {
        boolean ok = dao.eliminar(id);
        if (ok) System.out.println("Cliente eliminado: " + id);
        return ok;
    }
}
