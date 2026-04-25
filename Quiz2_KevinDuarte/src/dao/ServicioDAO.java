//src/dao/ServicioDAO.java
package dao;

import modelo.Servicio;
import util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO - Acceso a datos para Servicio
 * Implementa las 4 operaciones CRUD
 */
public class ServicioDAO {

    private Connection conn;

    public ServicioDAO() {
        this.conn = ConexionDB.getConexion();
    }

    // ─── CREATE ────────────────────────────────────────────
    public boolean insertar(Servicio s) {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio, disponible, id_cliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getDescripcion());
            ps.setDouble(3, s.getPrecio());
            ps.setString(4, String.valueOf(s.getDisponible()));
            ps.setInt(5, s.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar servicio: " + e.getMessage());
            return false;
        }
    }

    // ─── READ (todos) ──────────────────────────────────────
    public List<Servicio> listar() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT id_servicio, nombre, descripcion, precio, disponible, id_cliente FROM servicios ORDER BY id_servicio";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Servicio s = new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("disponible").charAt(0),
                    rs.getInt("id_cliente")
                );
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar servicios: " + e.getMessage());
        }
        return lista;
    }

    // ─── READ (por id) ─────────────────────────────────────
    public Servicio buscarPorId(int id) {
        String sql = "SELECT id_servicio, nombre, descripcion, precio, disponible, id_cliente FROM servicios WHERE id_servicio = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("disponible").charAt(0),
                    rs.getInt("id_cliente")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar servicio: " + e.getMessage());
        }
        return null;
    }

    // ─── UPDATE ────────────────────────────────────────────
    public boolean actualizar(Servicio s) {
        String sql = "UPDATE servicios SET nombre=?, descripcion=?, precio=?, disponible=?, id_cliente=? WHERE id_servicio=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getDescripcion());
            ps.setDouble(3, s.getPrecio());
            ps.setString(4, String.valueOf(s.getDisponible()));
            ps.setInt(5, s.getIdCliente());
            ps.setInt(6, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar servicio: " + e.getMessage());
            return false;
        }
    }

    // ─── DELETE ────────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE id_servicio = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar servicio: " + e.getMessage());
            return false;
        }
    }
}
