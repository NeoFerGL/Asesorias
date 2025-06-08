import java.sql.*;
import java.util.ArrayList;

public class GestorDB {
    
    public ArrayList<DetallesAsesoria> detAsesoria(int matricula, int estado) {
        ArrayList<DetallesAsesoria> detallesA = new ArrayList<>();
        String query = "SELECT a.id, a.asunto, mm.materia, " +
                       "CONCAT(u.nombre, ' ', u.apellidoP) as docente, " +
                       "DATE_FORMAT(a.fechasolicitud, '%d/%m/%Y') as fecha, " +
                       "TIME_FORMAT(a.horasolicitud, '%H:%i') as hora, " +
                       "a.comentario " +
                       "FROM asesoria a " +
                       "JOIN maestros_materias mm ON a.nrc = mm.NRC " +
                       "JOIN usuario u ON mm.matricula = u.matricula " +
                       "JOIN materia_alumnos ma ON a.nrc = ma.nrc AND a.matricula = ma.matricula " +
                       "WHERE a.matricula = ? AND a.estado = ?";
        
        try (Connection conn = ConectaDB.obtenConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, matricula);
            stmt.setInt(2, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DetallesAsesoria detalle = new DetallesAsesoria(
                    rs.getString("asunto"),
                    rs.getString("materia"),
                    rs.getString("docente"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("comentario")
                );
                detallesA.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error en detAsesoria: " + e.getMessage());
            e.printStackTrace();
        }
        return detallesA;
    }
    
    public ArrayList<DetallesAsesoria> detAsesoriaNo(int matricula, int estado) {
        ArrayList<DetallesAsesoria> detallesA = new ArrayList<>();
        String query = "SELECT a.id, a.asunto, mm.materia, " +
                       "CONCAT(u.nombre, ' ', u.apellidoP) as docente, " +
                       "DATE_FORMAT(a.fechasolicitud, '%d/%m/%Y') as fecha, " +
                       "TIME_FORMAT(a.horasolicitud, '%H:%i') as hora, " +
                       "a.comentario " +
                       "FROM asesoria a " +
                       "JOIN maestros_materias mm ON a.nrc = mm.NRC " +
                       "JOIN usuario u ON mm.matricula = u.matricula " +
                       "WHERE a.matricula = ? AND a.estado = ? " +
                       "AND NOT EXISTS (SELECT 1 FROM materia_alumnos ma " +
                       "WHERE ma.nrc = a.nrc AND ma.matricula = a.matricula)";
        
        try (Connection conn = ConectaDB.obtenConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, matricula);
            stmt.setInt(2, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DetallesAsesoria detalle = new DetallesAsesoria(
                    rs.getString("asunto"),
                    rs.getString("materia"),
                    rs.getString("docente"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("comentario")
                );
                detallesA.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error en detAsesoriaNo: " + e.getMessage());
            e.printStackTrace();
        }
        return detallesA;
    }
}