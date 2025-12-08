package aplicacion.domain.colecciones.fuentes;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FuenteProxy extends Fuente{
    public FuenteProxy(String id) {
        super(id, "Fuente Proxy " + id.substring(0, 10) );
    }







}
