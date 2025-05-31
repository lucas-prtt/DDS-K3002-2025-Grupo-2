package example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// HECHO
public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion_acontecimiento;
    private LocalDateTime fecha_acontecimiento;
    private LocalDateTime fecha_carga;
    private LocalDateTime fecha_ultimaModificacion;
    private Origen origen;
    private String contenidoTexto;
    private Optional<List<Multimedia>> contenidoMultimedia;
    private List<Etiqueta> etiquetas;
    private Boolean visible;
    private Boolean anonimato;
    private LocalDateTime fecha;
    private IdentidadContribuyente autor;

    public String getTitulo() {
        return titulo;
    }

    public boolean contieneEtiqueta(String etiqueta) {
        return contenidoEtiquetas.contains(etiqueta);
    }

    public Estado evaluarEstado() {
        return estado;
    }
}
