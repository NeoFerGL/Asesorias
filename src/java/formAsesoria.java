import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;

@WebServlet("/formAsesoria")
public class formAsesoria extends HttpServlet {

    // Configuración de la base de datos
    private static final String DB_URL = "jdbc:mysql://localhost:3306/asesoria?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Bloque estático para registrar el driver de MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DEBUG - Driver de MySQL registrado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR - No se pudo registrar el driver de MySQL");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("DEBUG - Servlet formAsesoria inicializado");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Obtener parámetros del formulario
            String matricula = request.getParameter("matricula");
            String asunto = request.getParameter("message");
            String fecha = request.getParameter("fechaSolicitud");
            String hora = request.getParameter("horaSolicitud");
            String nrc = request.getParameter("nrc");
            String profesor = request.getParameter("matricula_profesor");

            // Validar parámetros
            if (matricula == null || matricula.isEmpty() ||
                asunto == null || asunto.isEmpty() ||
                fecha == null || fecha.isEmpty() ||
                hora == null || hora.isEmpty() ||
                nrc == null || nrc.isEmpty() ||
                profesor == null || profesor.isEmpty()) {
                throw new ServletException("Todos los campos son requeridos");
            }

            // Convertir tipos
            int matriculaInt = Integer.parseInt(matricula);
            int nrcInt = Integer.parseInt(nrc);
            int profesorInt = Integer.parseInt(profesor);

            // Insertar en la base de datos
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO asesoria (matricula, asunto, fechasolicitud, horasolicitud, nrc, estado, comentario) " +
                             "VALUES (?, ?, ?, ?, ?, 0, 'Pendiente')";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, matriculaInt);
                    stmt.setString(2, asunto);
                    stmt.setString(3, fecha);
                    stmt.setString(4, hora);
                    stmt.setInt(5, nrcInt);
                    stmt.executeUpdate();
                }

                // Registrar éxito
                System.out.println("DEBUG - Asesoría registrada para matrícula: " + matricula);
            }

            // Respuesta exitosa
            out.println("<script>alert('Asesoría solicitada correctamente'); window.location.href='formAsesoria.html';</script>");

        } catch (NumberFormatException e) {
            out.println("<script>alert('Error: Los campos numéricos tienen formato incorrecto'); history.back();</script>");
            System.err.println("ERROR - Formato numérico incorrecto: " + e.getMessage());
        } catch (SQLException e) {
            out.println("<script>alert('Error de base de datos: " + escapeJavaScript(e.getMessage()) + "'); history.back();</script>");
            System.err.println("ERROR SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            out.println("<script>alert('Error: " + escapeJavaScript(e.getMessage()) + "'); history.back();</script>");
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        System.out.println("DEBUG - Petición GET recibida. Acción: " + action);

        try {
            if ("materias".equals(action)) {
                int matricula = Integer.parseInt(request.getParameter("matricula"));
                System.out.println("DEBUG - Solicitando materias para matrícula: " + matricula);
                String jsonResponse = getMateriasAlumno(matricula);
                out.print(jsonResponse);
                System.out.println("DEBUG - Respuesta materias: " + jsonResponse);

            } else if ("maestros".equals(action)) {
                int nrc = Integer.parseInt(request.getParameter("nrc"));
                System.out.println("DEBUG - Solicitando maestros para NRC: " + nrc);
                String jsonResponse = getMaestrosPorMateria(nrc);
                out.print(jsonResponse);
                System.out.println("DEBUG - Respuesta maestros: " + jsonResponse);

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Acción no válida\"}");
                System.out.println("DEBUG - Acción GET no reconocida: " + action);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Formato de parámetro incorrecto\"}");
            System.err.println("ERROR - Formato numérico incorrecto en GET: " + e.getMessage());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error en la base de datos: " + escapeJson(e.getMessage()) + "\"}");
            System.err.println("ERROR SQL en GET: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            System.err.println("ERROR en GET: " + e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    private String getMateriasAlumno(int matricula) throws SQLException {
        List<Map<String, Object>> materias = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT DISTINCT mm.NRC, mm.materia " +
                         "FROM materia_alumnos ma " +
                         "JOIN maestros_materias mm ON ma.nrc = mm.NRC " +
                         "WHERE ma.matricula = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, matricula);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Map<String, Object> materia = new HashMap<>();
                    materia.put("NRC", rs.getInt("NRC"));
                    materia.put("materia", rs.getString("materia"));
                    materias.add(materia);
                }

                if (materias.isEmpty()) {
                    return "{\"error\":\"No se encontraron materias para esta matrícula\"}";
                }
            }
        }
        return new Gson().toJson(materias);
    }

    private String getMaestrosPorMateria(int nrc) throws SQLException {
        List<Map<String, Object>> maestros = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT u.matricula, u.nombre, u.apellidoP " +
                         "FROM maestros_materias mm " +
                         "JOIN usuario u ON mm.matricula = u.matricula " +
                         "WHERE mm.NRC = ? AND u.Maestro = 1";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, nrc);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Map<String, Object> maestro = new HashMap<>();
                    maestro.put("matricula", rs.getInt("matricula"));
                    maestro.put("nombre", rs.getString("nombre"));
                    maestro.put("apellidoP", rs.getString("apellidoP"));
                    maestros.add(maestro);
                }

                if (maestros.isEmpty()) {
                    return "{\"error\":\"No hay maestros asignados a esta materia\"}";
                }
            }
        }
        return new Gson().toJson(maestros);
    }

    // Métodos auxiliares para escape de strings
    private String escapeJavaScript(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("'", "\\'")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de solicitudes de asesoría académica";
    }
}