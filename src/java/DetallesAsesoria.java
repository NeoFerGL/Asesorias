public class DetallesAsesoria {
    public String asunto;
    public String carrera;
    public String materia;
    public String docente;
    public String fecha;
    public String hora;
    public String comentario;

    public DetallesAsesoria(String asunto, String carrera, String materia, String docente, String fecha, String hora, String comentario) {
        this.asunto = asunto;
        this.carrera = carrera;
        this.materia = materia;
        this.docente = docente;
        this.fecha=fecha;
        this.hora=hora;
        this.comentario=comentario;
    }

    public String getAsunto() { return asunto;}

    public String getCarrera() { return carrera;}
    
    public String getMateria() { return materia; }
    
    public String getDocente() { return docente; }
    
    public String getFecha() { return fecha; }
    
    public String getHora() { return hora; }
    
    public String getComentario() { return comentario; }
   
}
