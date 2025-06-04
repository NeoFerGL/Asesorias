import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;

public class GestorDB {
    Connection conn = null;
    Statement stm = null;
    ResultSet productResSet;
    DetallesAsesoria detalles;
    String asunto, carrera, materia, docente, fecha, hora, comentario;
    
    public ArrayList<DetallesAsesoria> detAsesoria(int matricula, int estado) {
    ArrayList<DetallesAsesoria> detallesA = new ArrayList<>();
    Connection conn = null; // Declara la variable de conexión aquí
    try {
        conn = ConectaDB.obtenConexion();
        if (conn != null) { // Verifica si la conexión es válida
            Statement stm = conn.createStatement(); // Crea el statement solo si la conexión es válida
            ResultSet productResSet = stm.executeQuery("SELECT a.asunto AS Asunto_Asesoria, u.nombre AS nombre_maestro, mm.materia AS Materia, c.nombre AS Carrera, a.fechasolicitud AS Fecha, a.horasolicitud AS Hora, a.comentario AS Comentario FROM asesoria a INNER JOIN maestros_materias mm ON a.nrc = mm.NRC INNER JOIN usuario u ON mm.matricula = u.matricula INNER JOIN materia_alumnos ma ON a.nrc = ma.nrc AND a.matricula = ma.matricula INNER JOIN carrera c ON u.matricula = c.id_carrera WHERE a.estado = " + estado + " AND a.matricula =" + matricula + ";");
            while (productResSet.next()) {
                String asunto = productResSet.getString("Asunto_Asesoria");
                String carrera = productResSet.getString("Carrera");
                String materia = productResSet.getString("Materia");
                String docente = productResSet.getString("nombre_Maestro");
                String fecha = productResSet.getString("Fecha");
                String hora = productResSet.getString("Hora");
                String comentario = productResSet.getString("Comentario");
                DetallesAsesoria detalles = new DetallesAsesoria(asunto, carrera, materia, docente, fecha, hora, comentario);
                detallesA.add(detalles);
            }
            ConectaDB.cerrarConexion();
        } else {
            System.out.println("Error: no se pudo establecer la conexión a la base de datos.");
        }
    } catch (SQLException e) {
        System.out.println("Error en la base de datos: " + e.getMessage());
    } finally {
        try {
            if (conn != null) conn.close(); // Cierra la conexión si es diferente de null
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
    return detallesA;
}
    
    public ArrayList<DetallesAsesoria> detAsesoriaNo(int matricula, int estado) {
    ArrayList<DetallesAsesoria> detallesA = new ArrayList<>();
    Connection conn = null; 
    try {
        conn = ConectaDB.obtenConexion();
        if (conn != null) { 
            Statement stm = conn.createStatement();
            ResultSet productResSet = stm.executeQuery("SELECT a.id AS ID_Asesoria, a.asunto AS Asunto_Asesoria, u.nombre AS Nombre_Maestro, mm.materia AS Materia, c.nombre AS Carrera, a.fechasolicitud AS Fecha, a.horasolicitud AS Hora, a.comentario AS Comentario FROM asesoria a INNER JOIN maestros_materias mm ON a.nrc = mm.NRC INNER JOIN usuario u ON mm.matricula = u.matricula INNER JOIN carrera c ON u.matricula = c.id_carrera WHERE a.estado = " + estado + " AND a.matricula = " + matricula + " AND NOT EXISTS (SELECT 1 FROM materia_alumnos ma WHERE ma.nrc = a.nrc AND ma.matricula = a.matricula);");
            while (productResSet.next()) {
                String asunto = productResSet.getString("Asunto_Asesoria");
                String carrera = productResSet.getString("Carrera");
                String materia = productResSet.getString("Materia");
                String docente = productResSet.getString("Nombre_Maestro");
                String fecha = productResSet.getString("Fecha");
                String hora = productResSet.getString("Hora");
                String comentario = productResSet.getString("Comentario");
                DetallesAsesoria detalles = new DetallesAsesoria(asunto, carrera, materia, docente, fecha, hora, comentario);
                detallesA.add(detalles);
            }
            ConectaDB.cerrarConexion();
        } else {
            System.out.println("Error: no se pudo establecer la conexión a la base de datos.");
        }
    } catch (SQLException e) {
        System.out.println("Error en la base de datos: " + e.getMessage());
    } finally {
        try {
            if (conn != null) conn.close(); // Cierra la conexión si es diferente de null
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
    return detallesA;
}

}

