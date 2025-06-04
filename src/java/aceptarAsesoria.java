
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class aceptarAsesoria extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String idAsesoria = request.getParameter("IdModalAceptar");
        String texto = request.getParameter("comentarioAceptar");
        String idProfesor = request.getParameter("idProfesor");

        try {
            Connection conn = ConectaDB.obtenConexion();
            if (conn != null) {
                String consulta = "UPDATE asesoria SET comentario = ?, estado = ? WHERE id = ?";

                PreparedStatement sql = conn.prepareStatement(consulta);

                sql.setString(1, texto);
                sql.setString(2, "1");
                sql.setString(3, idAsesoria);

                int filasActualizadas = sql.executeUpdate();
                sql.close();
                ConectaDB.cerrarConexion();

                // Verifica si se actualizaron filas y muestra un mensaje adecuado
                if (filasActualizadas > 0) {
                    System.out.println("La asesoría se actualizó correctamente.");
                } else {
                    System.out.println("No se encontró ninguna asesoría con el ID especificado.");
                }
            } else {
                System.out.println("Error: no se pudo establecer la conexión a la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } finally {
            request.setAttribute("matricula", idProfesor);
            RequestDispatcher dispatcher = request.getRequestDispatcher("profesor");
            dispatcher.forward(request, response);
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
