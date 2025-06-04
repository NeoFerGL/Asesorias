import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = {"/formAsesoria"})
public class formAsesoria extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String matricula = request.getParameter("matricula");
        String asunto = request.getParameter("message");
        String fechaSolicitudStr = request.getParameter("fechaSolicitud");
        String horaSolicitudStr = request.getParameter("horaSolicitud");
        String nrcM = request.getParameter("maestrosSelect");
        int nrc = Integer.parseInt(nrcM);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asesoria", "root", "");

            // Formatear la fecha y hora
            LocalDate fechaSolicitud = LocalDate.parse(fechaSolicitudStr, DateTimeFormatter.ISO_DATE);
            LocalTime horaSolicitud = LocalTime.parse(horaSolicitudStr, DateTimeFormatter.ISO_TIME);

            String sql = "INSERT INTO asesoria (matricula, asunto, fechasolicitud, horasolicitud, nrc, estado) VALUES (?, ?, ?, ?, ?, 0)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, matricula);
            statement.setString(2, asunto);
            statement.setString(3, fechaSolicitud.toString());
            statement.setString(4, horaSolicitud.toString());
            statement.setInt(5, nrc);
            statement.executeUpdate();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Solicitud de Asesoría</title></head><body>");
            out.println("<h2>Solicitud de Asesoría enviada correctamente</h2>");
            out.println("<p>Tu solicitud de asesoría ha sido enviada. Recibirás una notificación cuando sea atendida.</p>");
            out.println("</body></html>");

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Solicitud de Asesoría</title></head><body>");
            out.println("<h2>Error al enviar la solicitud de asesoría: " + e.getMessage() + "</h2>");
            out.println("</body></html>");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
