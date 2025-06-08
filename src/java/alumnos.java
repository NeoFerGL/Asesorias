import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/alumnos")
public class alumnos extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String matriculaAlumno = request.getParameter("matricula");
            if (matriculaAlumno == null || matriculaAlumno.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matrícula requerida");
                return;
            }
            
            int matricula;
            try {
                matricula = Integer.parseInt(matriculaAlumno);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matrícula inválida");
                return;
            }
            
            // Generar HTML inicial
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\">");
            out.println(generarHead());
            out.println("<body>");
            out.println(generarBarraNavegacion());
            
            // Procesar cada tipo de asesoría
            procesarAsesorias(out, matricula, 0, "Pendientes", "block");
            procesarAsesorias(out, matricula, 1, "Aceptados", "none");
            procesarAsesorias(out, matricula, 2, "Denegados", "none");
            
            out.println("<script src=\"script.js\"></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private String generarHead() {
        return "<head>" +
               "<meta charset=\"UTF-8\">" +
               "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
               "<link rel=\"stylesheet\" href=\"styles.css\">" +
               "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
               "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"></script>" +
               "<title>Asesorías</title>" +
               "</head>";
    }
    
    private String generarBarraNavegacion() {
        return "<div class=\"container-fluid text-center justify-content-center barra p-3\">" +
               "<div class=\"row align-items-center\">" +
               "<div class=\"col-3 text-light\"><h1>Asesorias</h1></div>" +
               "<div class=\"col-3\">" +
               "<div class=\"tablinks btn text-light\" onclick=\"desplegar('Pendientes')\"><span>Pendientes</span></div>" +
               "</div>" +
               "<div class=\"col-3\">" +
               "<div class=\"tablinks btn text-light\" onclick=\"desplegar('Aceptados')\"><span>Aceptados</span></div>" +
               "</div>" +
               "<div class=\"col-3\">" +
               "<div class=\"tablinks btn text-light\" onclick=\"desplegar('Denegados')\"><span>Denegada</span></div>" +
               "</div>" +
               "</div>" +
               "</div>";
    }
    
    private void procesarAsesorias(PrintWriter out, int matricula, int estado, String tipo, String displayStyle) {
        GestorDB gestorDB = new GestorDB();
        
        out.println("<div id=\"" + tipo + "\" class=\"despliege\" style=\"display: " + displayStyle + ";\">");
        out.println("<div class=\"container-fluid pt-4\">");
        
        // Asesorías siendo alumno de la clase
        ArrayList<DetallesAsesoria> detalles = gestorDB.detAsesoria(matricula, estado);
        mostrarAsesorias(out, detalles, "Eres alumno de la clase");
        
        // Asesorías NO siendo alumno de la clase
        ArrayList<DetallesAsesoria> detallesNo = gestorDB.detAsesoriaNo(matricula, estado);
        mostrarAsesorias(out, detallesNo, "No eres alumno de la clase");
        
        out.println("</div>");
        out.println("</div>");
    }
    
    private void mostrarAsesorias(PrintWriter out, ArrayList<DetallesAsesoria> detalles, String titulo) {
        if (detalles == null || detalles.isEmpty()) {
            return;
        }
        
        out.println("<h2>" + titulo + "</h2>");
        for (DetallesAsesoria detalle : detalles) {
            out.println("<div class=\"card p-2 m-4\">");
            out.println("<div class=\"row ps-2\">");
            out.println("<div class=\"col-10 row\">");
            
            out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
            out.println("<div class=\"col-11\">" + escapeHtml(detalle.getAsunto()) + "</div>");
            
            out.println("<div class=\"col-1 pe-0\">Materia:</div>");
            out.println("<div class=\"col-11\">" + escapeHtml(detalle.getMateria()) + "</div>");
            
            out.println("<div class=\"col-1 pe-0\">Docente:</div>");
            out.println("<div class=\"col-11\">" + escapeHtml(detalle.getDocente()) + "</div>");
            
            out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
            out.println("<div class=\"col-5\">" + escapeHtml(detalle.getFecha()) + "</div>");
            
            out.println("<div class=\"col-1 pe-0\">Hora:</div>");
            out.println("<div class=\"col-5\">" + escapeHtml(detalle.getHora()) + "</div>");
            
            out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
            out.println("<div class=\"col-11\">" + escapeHtml(detalle.getComentario()) + "</div>");
            
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
        }
    }
    
    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Vista de asesorías para alumnos";
    }
}