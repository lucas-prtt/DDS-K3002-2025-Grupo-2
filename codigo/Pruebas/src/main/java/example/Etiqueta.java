package example;

// ETIQUETA
public class Etiqueta {
    private String nombre;
    private String descripcion;

    public boolean esIdentica(Etiqueta otra) {
        return this.nombre.equals(otra.nombre);
    }
}