package domain.hechos;

import lombok.Getter;

// ETIQUETA
public class Etiqueta {
    @Getter
    private String nombre;
    private String descripcion;

    public Etiqueta(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public boolean esIdenticaA(String etiqueta_nombre) {
        return this.nombre.equals(etiqueta_nombre);
    }
}