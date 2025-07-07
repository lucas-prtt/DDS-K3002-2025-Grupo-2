package domain.services;

import domain.hechos.Hecho;
import domain.repositorios.RepositorioDeHechos;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Analizar si tal vez conviene separar algunos metodos a un ColeccionService por ejemplo
@Service
public class HechoService {
    private final RepositorioDeHechos repositorioDeHechos;

    public HechoService(RepositorioDeHechos repositorioDeHechos) {
        this.repositorioDeHechos = repositorioDeHechos;
    }

    public void guardarHechos(List<Hecho> hechos) {
        repositorioDeHechos.saveAll(hechos);
        //COMO LAS FUENTES NO ESTAN REPETIDAS, NO HACE FALTA HACER UN SAVE ALL POR FUENTE
        //ADEMAS, SI UN HECHO SE REPITE EN DISTINTAS FUENTES, SE GUARDA VARIAS VECES
        // ESTO ESTA BIEN PORQUE ES UN TEMA DE CONSENSO. NOS SIRVE
    }

    public List<Hecho> obtenerHechosPorColeccion(String idColeccion) {
        return repositorioDeHechos.findByCollectionId(idColeccion);
    }

    public List<Hecho> obtenerHechosCuradosPorColeccion(String idColeccion) {
        return repositorioDeHechos.findCuredByCollectionId(idColeccion);
    }

}

//GIGA TODO: TENEMOS UN REPOSITORIO POR CADA TABLA DEL DER. EL PROBLEMA ESTA EN COMO HACES EL SELECT Y EL JOIN SIN TENER UNA BASE DE DATOS LEVANTADA
// TODO ENTONCES HAY QUE LEVANTAR LOS REPOS LOCALMENTE, QUE SE CONECTEN POR PK, FK Y QUE HAGAMOS EL SELECT A MANO DENTRO DE MEMORIA
// TODO LOS REPOSITORIOS DEBERIAN CONOCERSE ENTRE SI, O SEA QUE EL REPOSITORIO DE HECHOS CONOZCA AL REPOSITORIO DE COLECCIONES Y ASI PODER HACER EL JOIN