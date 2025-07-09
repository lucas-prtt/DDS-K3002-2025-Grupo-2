package domain.repositorios;

import domain.colecciones.fuentes.Fuente;
import domain.colecciones.fuentes.FuenteId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// Implementación del repositorio personalizado de fuentes
@Repository
public class RepositorioDeFuentesImpl implements RepositorioDeFuentesCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveIfNotExists(Fuente nuevaFuente) {
        if (nuevaFuente == null) return;

        // Verificar si la fuente ya existe
        Fuente existente = em.find(Fuente.class, nuevaFuente.getId());

        // Buscar la fuente existente por ID
        if (existente == null) {
            em.persist(nuevaFuente);
        } // Si no existe, se guarda la nueva fuente
    }

    @Override
    public void saveAllIfNotExists(List<Fuente> nuevasFuentes) {
        if (nuevasFuentes == null || nuevasFuentes.isEmpty()) return;

        for (Fuente fuente : nuevasFuentes) {
            saveIfNotExists(fuente); // Solo guardar las fuentes que no existen
        }
    }
}