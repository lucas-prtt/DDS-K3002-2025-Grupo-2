package domain.hechos.multimedias;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

// VIDEO
@Entity
@NoArgsConstructor
public class Video extends Multimedia {
    private Integer resolucion;
    private Integer duracion;

    public Video(String formato, Integer tamanio, Integer resolucion, Integer duracion) {
        super(formato, tamanio);
        this.resolucion = resolucion;
        this.duracion = duracion;
    }

    @Override
    public void reproducir() {
        // TODO
    }
}