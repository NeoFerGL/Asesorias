
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/profesor"})
public class profesor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String idProfesor = request.getAttribute("matricula").toString();
        List<Object[]> lista = new ArrayList<>();

        try {
            Connection conn = ConectaDB.obtenConexion();
            if (conn != null) {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT a.id, a.asunto, b.materia, CONCAT(c.nombre, ' ', c.apellidoP, ' ', c.apellidoM) AS nombre_completo, a.fechasolicitud, a.horasolicitud, a.comentario \n"
                        + "FROM asesoria a\n"
                        + "INNER JOIN maestros_materias b ON a.nrc = b.nrc \n"
                        + "INNER JOIN usuario c ON a.matricula = c.matricula \n"
                        + "WHERE a.estado = 0 AND b.matricula = " + idProfesor + ";");
                while (rs.next()) {
                    Object[] producto = new Object[6];
                    producto[0] = rs.getInt("id");
                    producto[1] = rs.getString("asunto");
                    producto[2] = rs.getString("materia");
                    producto[3] = rs.getString("nombre_completo");
                    producto[4] = rs.getString("fechasolicitud");
                    producto[5] = rs.getString("horasolicitud");
                    lista.add(producto);
                }
                ConectaDB.cerrarConexion();
            } else {
                System.out.println("Error: no se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"es\">\n"
                    + "  <head>\n"
                    + "    <meta charset=\"UTF-8\" />\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
                    + "    <title>Asesorías</title>\n"
                    + "    <link\n"
                    + "      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\"\n"
                    + "      rel=\"stylesheet\"\n"
                    + "    />\n"
                    + "\n"
                    + "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"></script>\n"
                    + "\n"
                    + "    <link\n"
                    + "      rel=\"stylesheet\"\n"
                    + "      href=\"https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200\"\n"
                    + "    />\n"
                    + "\n"
                    + "    <script src=\"js/asesoria.js\"></script>\n"
                    + "    <link rel=\"stylesheet\" href=\"css/estilos.css\" />\n"
                    + "  </head>\n"
                    + "  <body>\n"
                    + "    <div class=\"container-fluid p-3 shadow bar\">\n"
                    + "      <div class=\"row p-2 ps-3\">\n"
                    + "        <h2 class=\"col-10 text-light\">Asesorías</h2>\n"
                    + "        <div class=\"col-2 text-light text-center align-content-center\"><a href=\"index.html\" class=\"btn btn-secondary\">Cerrar Sesión<a/></div>\n"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "\n"
                    + "    <div class=\"container-fluid pt-4\" id=\"asesoria\">\n");

            for (Object[] producto : lista) {
                out.println("<div class=\"card p-2 m-4\">\n"
                        + "  <div class=\"row ps-2\">\n"
                        + "    <div class=\"col-10 row\">\n"
                        + "      <div class=\"col-1 pe-0\">Asunto:</div>\n"
                        + "      <div class=\"col-11\">" + producto[1] + "</div>\n" // Asunto
                        + "      <div class=\"col-1 pe-0\">Materia:</div>\n"
                        + "      <div class=\"col-11\">" + producto[2] + "</div>\n" // Materia
                        + "      <div class=\"col-1 pe-0\">Alumno:</div>\n"
                        + "      <div class=\"col-11\">" + producto[3] + "</div>\n" // Nombre completo del maestro
                        + "      <div class=\"col-1 pe-0\">Fecha:</div>\n"
                        + "      <div class=\"col-5\">" + producto[4] + "</div>\n" // Fecha de solicitud
                        + "      <div class=\"col-1 pe-0\">Hora:</div>\n"
                        + "      <div class=\"col-5\">" + producto[5] + "</div>\n" // Hora de solicitud
                        + "    </div>\n"
                        + "    <div class=\"col-2 align-content-center text-center\">\n"
                        + "      <button class=\"material-symbols-outlined btn btn-success\"\n"
                        + "        onclick=\"abreModal('" + producto[0] + "')\"\n" // Id de la asesoría en la base de datos
                        + "        data-bs-toggle=\"modal\" data-bs-target=\"#modalAceptar\">\n"
                        + "        check\n"
                        + "      </button>\n"
                        + "      <button class=\"material-symbols-outlined btn btn-danger\"\n"
                        + "        onclick=\"eliminar('" + producto[0] + "')\"\n" // Id de la asesoría en la base de datos
                        + "        data-bs-toggle=\"modal\" data-bs-target=\"#modalRechazar\">\n"
                        + "        close\n"
                        + "      </button>\n"
                        + "    </div>\n"
                        + "  </div>\n"
                        + "</div>\n");
            }

            out.println("</div>\n"
                    + "\n"
                    + "    <!-- Modal Aceptar-->\n"
                    + "    <div class=\"modal\" id=\"modalAceptar\">\n"
                    + "      <div class=\"modal-dialog\">\n"
                    + "        <div class=\"container modal-content\">\n"
                    + "          <form action=\"aceptarAsesoria\" method=\"post\">\n"
                    + "            <!-- Modal body -->\n"
                    + "            <div class=\"modal-body m-3\">\n"
                    + "              <div class=\"row\">\n"
                    + "                <div class=\"col-12\">Aceptar Asesoría</div>\n"
                    + "                <div class=\"col-12 pt-4\">Comentarios:</div>\n"
                    + "                <div class=\"col-12 pt-2\">\n"
                    + "                  <textarea\n"
                    + "                    class=\"form-control\"\n"
                    + "                    id=\"comentarioAceptar\" name=\"comentarioAceptar\" \n"
                    + "                  required></textarea>\n"
                    + "                  <input type=\"hidden\" id=\"IdModalAceptar\" name=\"IdModalAceptar\" value=\"\" />\n"
                    + "                  <input type=\"hidden\" name=\"idProfesor\" value=\"" + idProfesor + "\" />\n"
                    + "                </div>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "            <!-- Modal footer -->\n"
                    + "            <div class=\"modal-footer\">\n"
                    + "              <button type=\"submit\" type=\"button\" class=\"btn btn-success\">Aceptar</button>\n"
                    + "              <button\n"
                    + "                type=\"button\"\n"
                    + "                class=\"btn btn-danger\"\n"
                    + "                data-bs-dismiss=\"modal\"\n"
                    + "              >\n"
                    + "                Cancelar\n"
                    + "              </button>\n"
                    + "            </div>\n"
                    + "          </form>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "    <!-- Fin Modal -->\n"
                    + "\n"
                    + "    <!-- Modal Rechazar -->\n"
                    + "    <div class=\"modal\" id=\"modalRechazar\">\n"
                    + "      <div class=\"modal-dialog\">\n"
                    + "        <div class=\"container modal-content\">\n"
                    + "          <form action=\"rechazarAsesoria\" method=\"post\">\n"
                    + "            <!-- Modal body -->\n"
                    + "            <div class=\"modal-body m-3\">\n"
                    + "              <div class=\"row\">\n"
                    + "                <div class=\"col-12\">Eliminar Solicitud de Asesoría</div>\n"
                    + "                <div class=\"col-12 pt-4\">Comentarios:</div>\n"
                    + "                <div class=\"col-12 pt-2\">\n"
                    + "                  <textarea\n"
                    + "                    class=\"form-control\"\n"
                    + "                    id=\"comentarioRechazar\" name=\"comentarioRechazar\" \n"
                    + "                  required></textarea>\n"
                    + "                  <input type=\"hidden\" id=\"IdModalRechazar\" name=\"IdModalRechazar\" value=\"\" />\n"
                    + "                  <input type=\"hidden\" name=\"idProfesor\" value=\"" + idProfesor + "\" />\n"
                    + "                </div>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "            <!-- Modal footer -->\n"
                    + "            <div class=\"modal-footer\">\n"
                    + "              <button type=\"submit\" class=\"btn btn-danger\">\n"
                    + "                Eliminar Solicitud\n"
                    + "              </button>\n"
                    + "              <button\n"
                    + "                type=\"button\"\n"
                    + "                class=\"btn btn-light\"\n"
                    + "                data-bs-dismiss=\"modal\"\n"
                    + "              >\n"
                    + "                Cancelar\n"
                    + "              </button>\n"
                    + "            </div>\n"
                    + "          </form>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "    <!-- Fin Modal -->\n"
                    + "  </body>\n"
                    + "</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
