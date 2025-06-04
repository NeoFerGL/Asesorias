
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;

@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        String matricula = request.getParameter("matricula");
        String rol = request.getParameter("rol");

        try {
            conn = ConectaDB.obtenConexion();
            String sql = "SELECT Maestro FROM usuario WHERE matricula = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, matricula);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String esMaestro = rs.getString("Maestro");
                if (esMaestro.equals("1") && rol.equals("1")) {
                    request.setAttribute("matricula", matricula);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("profesor");
                    dispatcher.forward(request, response);
                } else if (esMaestro.equals("0") && rol.equals("0")) {
                    response.sendRedirect("alumnos?matricula=" + matricula);
                } else {
                    // Código HTML de respuesta para acceso no válido
                    PrintWriter out = response.getWriter();
                    out.println("<html><head><title>Inicio de Sesión</title></head><body>");
                    out.println("<h2>Acceso no válido</h2>");
                    out.println("<p>No puedes iniciar sesión desde este formulario. Por favor, utiliza el formulario correspondiente.</p>");
                    out.println("</body></html>");
                }
            } else {
                // Código HTML de respuesta para matrícula no encontrada
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Inicio de Sesión</title></head><body>");
                out.println("<h2>Matrícula no encontrada</h2>");
                out.println("<p>La matrícula ingresada no se encuentra registrada. Por favor, inténtalo de nuevo.</p>");
                out.println("</body></html>");
            }

            conn.close();
        } catch (SQLException e) {
            // Manejo de excepciones
            e.printStackTrace();  // Esto imprime el rastreo de la excepción en la consola del servidor
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Inicio de Sesión</title></head><body>");
            out.println("<h2>Error al obtener los datos de la base de datos: " + e.getMessage() + "</h2>");
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
