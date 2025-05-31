package example;
import java.time.LocalDate;

//CATEGORIA
public class Categoria {
    private String nombre;
    private LocalDate fechaCreacion;

    public Boolean esIdentica(Categoria otraCategoria) {
        return this.nombre.equals(otraCategoria.nombre);
    }
}