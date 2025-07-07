package domain.repositorios;

import domain.colecciones.fuentes.Fuente;

import java.util.List;

public interface RepositorioDeFuentesCustom {
    void saveIfNotExists(Fuente nuevaFuente);

    void saveAllIfNotExists(List<Fuente> nuevasFuentes);
}
