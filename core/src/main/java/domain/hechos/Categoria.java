package domain.hechos;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Boolean esIdenticaA(String categoria_nombre) {
        return this.nombre.equals(categoria_nombre);
    }
}

