package domain.hechos.multimedias;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

//AUDIO
@Entity
@NoArgsConstructor
public class Audio extends Multimedia {
    private Integer duracion;

    public Audio(String formato, Integer tamanio, Integer duracion) {
        super(formato, tamanio);
        this.duracion = duracion;
    }

    @Override
    public void reproducir() {
        // TODO
    }
}