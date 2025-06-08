public class DetallesAsesoria {
    public String asunto;
    public String materia;
    public String docente;
    public String fecha;
    public String hora;
    public String comentario;

    // Constructor actualizado con 6 parámetros
    public DetallesAsesoria(String asunto, String materia, String docente, 
                          String fecha, String hora, String comentario) {
        this.asunto = asunto;
        this.materia = materia;
        this.docente = docente;
        this.fecha = fecha;
        this.hora = hora;
        this.comentario = comentario;
    }

    // Métodos getter (elimina getCarrera() si ya no es necesario)
    public String getAsunto() { return asunto; }
    public String getMateria() { return materia; }
    public String getDocente() { return docente; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getComentario() { return comentario; }
}