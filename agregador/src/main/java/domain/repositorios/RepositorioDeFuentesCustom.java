package domain.repositorios;

import domain.colecciones.fuentes.Fuente;

import java.util.List;

// Repositorio de Fuentes con métodos personalizados
public interface RepositorioDeFuentesCustom {
    void saveIfNotExists(Fuente nuevaFuente);

    void saveAllIfNotExists(List<Fuente> nuevasFuentes);
}
