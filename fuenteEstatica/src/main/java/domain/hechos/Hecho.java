package domain.hechos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// HECHO
public class Hecho {
    @Getter
    private String titulo;
    @Getter private String descripcion;
    @Getter private Categoria categoria;
    @Getter private Ubicacion ubicacion;
    @JsonProperty("fechaAcontecimiento")
    @Getter private LocalDate fecha_acontecimiento;
    private LocalDate fecha_carga;
    private Origen origen;
    private List<Etiqueta> etiquetas;
    private Boolean visible;

    public Hecho() {} // Constructor vacio para que se pueda deserealizar el JSON

    public Hecho(String titulo, String descripcion, Categoria categoria, Double latitud, Double longitud, LocalDate fecha_acontecimiento, Origen origen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ubicacion = new Ubicacion(latitud, longitud);
        this.fecha_acontecimiento = fecha_acontecimiento;
        this.fecha_carga = LocalDate.now();
        this.origen = origen;
        this.etiquetas = new ArrayList<>();
        this.visible = true;
    }

    public void ocultar() {
        visible = false;
    }

    public Boolean esVisible() {
        return visible;
    }

    public void mostrar() { visible = true; }

    public void editar(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion, LocalDate fecha) {
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (descripcion != null) {
            this.descripcion = descripcion;
        }
        if (categoria != null) {
            this.categoria = categoria;
        }
        if (ubicacion != null) {
            this.ubicacion = ubicacion;
        }
        if (fecha != null) {
            this.fecha_acontecimiento = fecha;
        }
    }

    public Boolean tieneMismoTitulo(String otro_titulo) {
        return titulo.equals(otro_titulo);
    }

    public void etiquetar(Etiqueta etiqueta) {
        etiquetas.add(etiqueta);
    }

    public boolean contieneEtiqueta(Etiqueta etiqueta) {
        return etiquetas.contains(etiqueta);
    }

    public Boolean ocurrioEntre(LocalDate fecha_inicial, LocalDate fecha_final) {
        return ocurrioDespuesDe(fecha_inicial) && ocurrioAntesDe(fecha_final);
    }

    public Boolean ocurrioAntesDe(LocalDate fecha)
    {
        return fecha_acontecimiento.isBefore(fecha);
    }

    public Boolean ocurrioDespuesDe(LocalDate fecha)
    {
        return fecha_acontecimiento.isAfter(fecha);
    }

    public Boolean seCargoAntesDe(LocalDate fecha) {
        return fecha_carga.isBefore(fecha);
    }

    public Boolean seCargoDespuesDe(LocalDate fecha) {
        return fecha_carga.isAfter(fecha);
    }
}
