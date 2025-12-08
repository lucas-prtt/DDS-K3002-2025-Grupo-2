package aplicacion.domain.colecciones.fuentes;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FuenteDinamica extends Fuente {
    public FuenteDinamica(String id) {
        super(id, "Fuente Dinamica " + id.substring(0, 8));
    }
}



