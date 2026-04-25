// src/controlador/ServicioControlador.java
package controlador;

import dao.ServicioDAO;
import modelo.Servicio;
import java.util.List;

/**
 * Controlador - Lógica de negocio para Servicio
 */
public class ServicioControlador {

    private ServicioDAO dao;

    public ServicioControlador() {
        this.dao = new ServicioDAO();
    }

    public boolean agregarServicio(String nombre, String descripcion, double precio, int idCliente) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre del servicio es obligatorio.");
            return false;
        }
        if (precio < 0) {
            System.out.println("El precio no puede ser negativo.");
            return false;
        }
        Servicio s = new Servicio(nombre.trim(), descripcion, precio, idCliente);
        boolean ok = dao.insertar(s);
        if (ok) System.out.println("Servicio registrado: " + nombre);
        return ok;
    }

    public List<Servicio> obtenerServicios() {
        return dao.listar();
    }

    public Servicio obtenerServicioPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean modificarServicio(int id, String nombre, String descripcion, double precio, char disponible, int idCliente) {
        Servicio s = new Servicio(id, nombre, descripcion, precio, disponible, idCliente);
        boolean ok = dao.actualizar(s);
        if (ok) System.out.println("Servicio actualizado: " + id);
        return ok;
    }

    public boolean borrarServicio(int id) {
        boolean ok = dao.eliminar(id);
        if (ok) System.out.println("Servicio eliminado: " + id);
        return ok;
    }
}
