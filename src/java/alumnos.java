import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/alumnos"})
public class alumnos extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String matriculaAlumno = request.getParameter("matricula");
            GestorDB gestorBD = new GestorDB(); 
            int matricula = Integer.parseInt(matriculaAlumno);
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"></script>");
            out.println("<title>Asesorías</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container-fluid text-center justify-content-center barra p-3\">");
            out.println("<div class=\"row align-items-center\">");
            out.println("<div class=\"col-3 text-light\"><h1>Asesorias</h1></div>");
            out.println("<div class=\"col-3\">");
            out.println("<div class=\"tablinks btn text-light\" onclick=\"desplegar('Pendientes')\"><span>Pendientes</span></div>");
            out.println("</div>");
            out.println("<div class=\"col-3\">");
            out.println("<div class=\"tablinks btn text-light\" onclick=\"desplegar('Aceptados')\"><span>Aceptados</span></div>");
            out.println("</div>");
            out.println("<div class=\"col-3\">");
            out.println("<div class=\"tablinks btn text-light\" onclick=\"desplegar('Denegados')\"><span>Denegada</span></div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            
            
            ArrayList<DetallesAsesoria> detalles;
            GestorDB gestorDB = new GestorDB();
            detalles = gestorDB.detAsesoria(matricula, 0);
            out.println("<div id=\"Pendientes\" class=\"despliege\" style=\"display: block;\">");
            out.println("<div class=\"container-fluid pt-4\" id=\"pendientes\">");
            out.println("<h2>Eres alumno de la clase</h2>");
            if (detalles != null) {
                request.setAttribute("Detalles", detalles);
                detalles = (ArrayList<DetallesAsesoria>) request.getAttribute("Detalles");
                for (DetallesAsesoria detalle : detalles) {
                    out.println("<div class=\"card p-2 m-4\">");
                    out.println("<div class=\"row ps-2\">");
                    out.println("<div class=\"col-10 row\">");
                    out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
                    out.println("<div class=\"col-11\">"+detalle.getAsunto()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Materia:</div>");
                    out.println("<div class=\"col-11\">"+detalle.getMateria()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Docente:</div>");
                    out.println("<div class=\"col-11\">"+detalle.getDocente()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
                    out.println("<div class=\"col-5\">"+detalle.getFecha()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Hora:</div>");
                    out.println("<div class=\"col-5\">"+detalle.getHora()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
                    out.println("<div class=\"col-11\">"+detalle.getComentario()+"</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
            }
            ArrayList<DetallesAsesoria> detallesNo;
            detallesNo = gestorDB.detAsesoriaNo(matricula, 0);
            out.println("<h2>No eres alumno de la clase</h2>");
            if (detallesNo != null) {
                request.setAttribute("DetallesN", detallesNo);
                detallesNo = (ArrayList<DetallesAsesoria>) request.getAttribute("DetallesN");
                for (DetallesAsesoria detalleNo : detallesNo) {
                    out.println("<div class=\"card p-2 m-4\">");
                    out.println("<div class=\"row ps-2\">");
                    out.println("<div class=\"col-10 row\">");
                    out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
                    out.println("<div class=\"col-11\">"+detalleNo.getAsunto()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Materia:</div>");
                    out.println("<div class=\"col-11\">"+detalleNo.getMateria()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Docente:</div>");
                    out.println("<div class=\"col-11\">"+detalleNo.getDocente()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
                    out.println("<div class=\"col-5\">"+detalleNo.getFecha()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Hora:</div>");
                    out.println("<div class=\"col-5\">"+detalleNo.getHora()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
                    out.println("<div class=\"col-11\">"+detalleNo.getComentario()+"</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
                out.println("</div>");
                out.println("</div>");
            }
            
            //Asesorías aceptadas
            ArrayList<DetallesAsesoria> detallesA;
            detallesA = gestorDB.detAsesoria(matricula, 1);
            out.println("<div id=\"Aceptados\" class=\"despliege\" style=\"display: none;\">");
            out.println("<div class=\"container-fluid pt-4\" id=\"aceptados\">");
            out.println("<h2>Eres alumno de la clase</h2>");
            if (detallesA != null) {
                request.setAttribute("Detalles", detallesA);
                detallesA = (ArrayList<DetallesAsesoria>) request.getAttribute("Detalles");
                for (DetallesAsesoria detalleA : detallesA) {
                    out.println("<div class=\"card p-2 m-4\">");
                    out.println("<div class=\"row ps-2\">");
                    out.println("<div class=\"col-10 row\">");
                    out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
                    out.println("<div class=\"col-11\">"+detalleA.getAsunto()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Materia:</div>");
                    out.println("<div class=\"col-11\">"+detalleA.getMateria()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Docente:</div>");
                    out.println("<div class=\"col-11\">"+detalleA.getDocente()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
                    out.println("<div class=\"col-5\">"+detalleA.getFecha()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Hora:</div>");
                    out.println("<div class=\"col-5\">"+detalleA.getHora()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
                    out.println("<div class=\"col-11\">"+detalleA.getComentario()+"</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
            }
            ArrayList<DetallesAsesoria> detallesNoA;
            detallesNoA = gestorDB.detAsesoriaNo(matricula, 1);
            out.println("<h2>No eres alumno de la clase</h2>");
            if (detallesNoA != null) {
                request.setAttribute("DetallesN", detallesNoA);
                detallesNo = (ArrayList<DetallesAsesoria>) request.getAttribute("DetallesN");
                for (DetallesAsesoria detalleNoA : detallesNo) {
                    out.println("<div class=\"card p-2 m-4\">");
                    out.println("<div class=\"row ps-2\">");
                    out.println("<div class=\"col-10 row\">");
                    out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoA.getAsunto()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Materia:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoA.getMateria()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Docente:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoA.getDocente()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
                    out.println("<div class=\"col-5\">"+detalleNoA.getFecha()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Hora:</div>");
                    out.println("<div class=\"col-5\">"+detalleNoA.getHora()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoA.getComentario()+"</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
                out.println("</div>");
                out.println("</div>");
            }
           
            //Asesorías denegadas
            ArrayList<DetallesAsesoria> detallesD;
            detallesD = gestorDB.detAsesoria(matricula, 2);
            out.println("<div id=\"Denegados\" class=\"despliege\" style=\"display: none;\">");
            out.println("<div class=\"container-fluid pt-4\" id=\"aceptados\">");
            out.println("<h2>Eres alumno de la clase</h2>");
            if (detallesD != null) {
                request.setAttribute("Detalles", detallesD);
                detallesD = (ArrayList<DetallesAsesoria>) request.getAttribute("Detalles");
                for (DetallesAsesoria detalleD : detallesD) {
                    out.println("<div class=\"card p-2 m-4\">");
                    out.println("<div class=\"row ps-2\">");
                    out.println("<div class=\"col-10 row\">");
                    out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
                    out.println("<div class=\"col-11\">"+detalleD.getAsunto()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Materia:</div>");
                    out.println("<div class=\"col-11\">"+detalleD.getMateria()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Docente:</div>");
                    out.println("<div class=\"col-11\">"+detalleD.getDocente()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
                    out.println("<div class=\"col-5\">"+detalleD.getFecha()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Hora:</div>");
                    out.println("<div class=\"col-5\">"+detalleD.getHora()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
                    out.println("<div class=\"col-11\">"+detalleD.getComentario()+"</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
            }
            ArrayList<DetallesAsesoria> detallesNoD;
            detallesNoD = gestorDB.detAsesoriaNo(matricula, 2);
            out.println("<h2>No eres alumno de la clase</h2>");
            if (detallesNoD != null) {
                request.setAttribute("DetallesN", detallesNoD);
                detallesNoD = (ArrayList<DetallesAsesoria>) request.getAttribute("DetallesN");
                for (DetallesAsesoria detalleNoD : detallesNoD) {
                    out.println("<div class=\"card p-2 m-4\">");
                    out.println("<div class=\"row ps-2\">");
                    out.println("<div class=\"col-10 row\">");
                    out.println("<div class=\"col-1 pe-0\">Asunto:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoD.getAsunto()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Materia:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoD.getMateria()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Docente:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoD.getDocente()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Fecha:</div>");
                    out.println("<div class=\"col-5\">"+detalleNoD.getFecha()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Hora:</div>");
                    out.println("<div class=\"col-5\">"+detalleNoD.getHora()+"</div>");
                    out.println("<div class=\"col-1 pe-0\">Comentario:</div>");
                    out.println("<div class=\"col-11\">"+detalleNoD.getComentario()+"</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
                out.println("</div>");
                out.println("</div>");
            } 
            out.println("<script src=\"script.js\"></script>");
            out.println("</body>");
            out.println("</html>");
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
