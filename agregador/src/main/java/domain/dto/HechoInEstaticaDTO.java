package domain.dto;

import domain.hechos.Categoria;
import domain.hechos.Etiqueta;
import domain.hechos.Origen;
import domain.hechos.Ubicacion;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class HechoInEstaticaDTO {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDate fecha_acontecimiento;
    private LocalDate fecha_carga;
    private Origen origen;
    private Boolean visible;
    private List<Etiqueta> etiquetas;
}
