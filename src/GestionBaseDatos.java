import java.sql.*;
import java.util.ArrayList;

public class GestionBaseDatos {

    public static ArrayList<Object[]> mostrarDatosTabla() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Object[]> datos = new ArrayList<>();

        try {
            conn = main.connect();

            String sql = "SELECT * FROM habitos ORDER BY id";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[4];

                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("frecuencia");

                datos.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Debug: Error al consultar datos "+e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                main.disconnect(conn);
            } catch (SQLException e) {
                System.out.println("Debug: "+e.getMessage());
            }
        }
        return datos;
    }

    public static void insertarDatos(String nombre, String frecuencia, String descripcion) {
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = main.connect();

            String sql = "INSERT INTO habitos (nombre, frecuencia, descripcion) VALUES (?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, frecuencia);
            pstmt.setString(3, descripcion);

            int columnasInsertadas = pstmt.executeUpdate();
            if (columnasInsertadas > 0) {
                System.out.println("Debug: Datos insertados");
            }
        } catch (SQLException e) {
            System.out.println("Debug: Error al insertar datos "+e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                main.disconnect(conn);
            } catch (SQLException e) {
                System.out.println("Debug: "+e.getMessage());
            }
        }
    }

    public static boolean modificarDatos(String nombre, String descripcion, String frecuencia, int id) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        boolean exito = false;

        try {
            conn = main.connect();

            String sql = "UPDATE habitos SET nombre = ?, descripcion = ?, frecuencia = ? WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setString(3, frecuencia);
            pstmt.setInt(4, id);

            int columnasModificadas = pstmt.executeUpdate();
            exito = (columnasModificadas > 0);

        } catch (SQLException e) {
            System.out.println("Debug: Error al modificar datos "+e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                main.disconnect(conn);
            } catch (SQLException e) {
                System.out.println("Debug: "+e.getMessage());
            }
        }
        return exito;
    }

    public static boolean eliminarDatos(int id) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        boolean exito = false;

        try {
            conn = main.connect();

            String sql = "DELETE FROM habitos WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int columnasEliminadas = pstmt.executeUpdate();

            exito = (columnasEliminadas > 0);
        } catch (SQLException e) {
            System.out.println("Debug: Error al eliminar datos "+e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                main.disconnect(conn);
            } catch (SQLException e) {
                System.out.println("Debug: "+e.getMessage());
            }
        }
        return exito;
    }
}
