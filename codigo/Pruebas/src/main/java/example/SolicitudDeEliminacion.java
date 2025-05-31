package example;

import java.time.LocalDateTime;


// SOLICITUD DE ELIMINACION
public class SolicitudEliminacion implements DetectorDeSpam{
    private Estado estado;
    private Contribuyente solicitante;
    private Contribuyente administrador;
    private LocalDateTime fecha_subida;
    private LocalDateTime fecha_resolucion;
    private Hecho hecho;
    private String motivo;

    public void setFechaResolucion(LocalDateTime fecha){
        this.fecha_resolucion = fecha;
    }

    public void setAdministrador(Administrador administrador){
        this.administrador = administrador;
    }

    public void aceptar(){
        // TODO
    }

    public void rechazar(){
        // TODO
    }

    public Boolean esSpam(String texto){
        // TODO : NO SABEMOS AÚN CÓMO SE IMPLEMENTA
    }
}