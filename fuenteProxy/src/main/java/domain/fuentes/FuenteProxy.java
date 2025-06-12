package domain.fuentes;

import domain.hechos.Hecho;
import java.util.List;

// FUENTE PROXY
public abstract class FuenteProxy extends Fuente {
    public abstract List<Hecho> importarHechos();

    public FuenteProxy(Long id) {
        super(id);
    }
}