package domain.repositorios;

import domain.colecciones.Fuente;
import domain.hechos.Hecho;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioDeHechosImpl implements RepositorioDeHechosCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RepositorioDeHechos repositorio_de_hechos;

    @Override
    public void saveByFuente(List<Hecho> hechos, Fuente fuente) {
        repositorio_de_hechos.saveAll(hechos);
        // TODO hacer que el repositorio de hechosXColeccion guarde de cada hecho, de que coleccion viene
    }
}