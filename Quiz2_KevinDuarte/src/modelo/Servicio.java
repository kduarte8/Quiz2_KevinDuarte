//src/modelo/Servicio.java
package modelo;

/**
 * Modelo - Entidad Servicio
 */
public class Servicio {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private char disponible;
    private int idCliente;

    public Servicio() {}

    public Servicio(String nombre, String descripcion, double precio, int idCliente) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = 'S';
        this.idCliente = idCliente;
    }

    public Servicio(int id, String nombre, String descripcion, double precio, char disponible, int idCliente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public char getDisponible() { return disponible; }
    public void setDisponible(char disponible) { this.disponible = disponible; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Servicio{id=" + id + ", nombre='" + nombre + "', precio=" + precio + "}";
    }
}
