package domain.hechos;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

//CATEGORIA
public class Categoria {
    private String nombre;
    private LocalDate fecha_creacion;

    @JsonCreator
    public Categoria(@JsonProperty("nombre") String nombre) {
        this.nombre = nombre;
        this.fecha_creacion = LocalDate.now();
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean esIdenticaA(Categoria categoria) {
        return this.nombre.equals(categoria.getNombre());
    }
}