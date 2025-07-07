package domain.repositorios;

import domain.colecciones.fuentes.Fuente;
import domain.colecciones.fuentes.FuenteId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioDeFuentesImpl implements RepositorioDeFuentesCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveIfNotExists(Fuente nuevaFuente) {
        if (nuevaFuente == null) return;

        // Verificar si la fuente ya existe
        FuenteId id = nuevaFuente.getId();

        // Buscar la fuente existente por ID
        findById(id).orElseGet(() -> save(nuevaFuente)); // Si no existe, se guarda la nueva fuente
    }

    @Override
    public void saveAllIfNotExists(List<Fuente> nuevasFuentes) {
        if (nuevasFuentes == null || nuevasFuentes.isEmpty()) return;

        // Obtener ids de las nuevas fuentes
        List<FuenteId> idsNuevos = nuevasFuentes.stream()
                .map(Fuente::getId)
                .toList();

        // Obtener los ids existentes en la base de datos
        List<Fuente> existentes = findAllById(idsNuevos);
        var idsExistentes = existentes.stream()
                .map(Fuente::getId)
                .collect(java.util.stream.Collectors.toSet());

        // Filtrar solo las fuentes que no están en la BD
        List<Fuente> soloNuevas = nuevasFuentes.stream()
                .filter(f -> !idsExistentes.contains(f.getId()))
                .toList();

        // Solo guardar las fuentes que no existen
        saveAll(soloNuevas);
    }
}
