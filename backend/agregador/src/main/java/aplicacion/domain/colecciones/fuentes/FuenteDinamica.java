package aplicacion.domain.colecciones.fuentes;

import aplicacion.domain.conexiones.Conexion;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FuenteDinamica extends Fuente {
    public FuenteDinamica(String id, Conexion conexion) {
        super(id, conexion, "Fuente Dinamica " + id.substring(0, 8));
    }

    public FuenteDinamica(String id) {
        super(id);
    }

    @Override
    public String pathIntermedio() {
        return "fuentesDinamicas";
    }
}
