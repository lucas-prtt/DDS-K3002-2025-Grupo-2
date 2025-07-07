package domain.repositorios;

import domain.colecciones.fuentes.Fuente;
import domain.hechos.Hecho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioDeHechosImpl implements RepositorioDeHechosCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveByFuente(List<Hecho> hechos, Fuente fuente) {
        //this.saveAll(hechos); TODO: Usar el entity manager para guardar los hechos
        // TODO hacer que el repositorio de hechosXColeccion guarde de cada hecho, de que coleccion viene
    }
}