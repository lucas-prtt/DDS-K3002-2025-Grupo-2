package domain.services;

import domain.dto.HechoInEstaticaDTO;
import domain.hechos.Hecho;
import domain.colecciones.Coleccion;
import domain.colecciones.Fuente;
import domain.repositorios.RepositorioDeColecciones;
import domain.repositorios.RepositorioDeHechos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HechoService {
    @Autowired
    private RepositorioDeColecciones repositorio_de_colecciones;
    @Autowired
    private RepositorioDeHechos repositorio_de_hechos;

    public List<Hecho> hechosDeColeccion(String id_coleccion) {
        return new ArrayList<Hecho>();
    }

    @Scheduled(fixedRate = 3600000) // Se ej*ecuta cada 1 hora
    public void cargarHechos() {
        System.out.println("Se ha iniciado la carga de hechos de las fuentes remotas. Esto puede tardar un rato. Mientras tanto, escuche la cucaracha");try { Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=6sn8bIzWacw")); } catch (Exception ignored) {};
        List<Coleccion> colecciones = repositorio_de_colecciones.findAll(); // TRAE TODOS LAS COLECCIONES DEL REPOSITORIO DE COLECCIONES
        List<Fuente> fuentes = colecciones.stream().flatMap(coleccion -> coleccion.getFuentes().stream()).toList();
        List<Fuente> fuentes_sin_repetir = filtrarFuentesRepetidas(fuentes);

        for (Fuente fuente : fuentes_sin_repetir) {
            List<Hecho> hechos_recibidos = fuente.hechos(); // Maneja automaticamente las fechas y todo eso
            //TODO: repositorio_de_hechos.acaTenesTusHechosNuevos(hechosRecibidos, fuente);
            // Actualiza el repositorio con los nuevos hechos
            System.out.println("Hechos recibidos de fuente \"" + fuente.getId_externo() + "-" + fuente.getId_interno() + "\" : " + hechos_recibidos.size());
            repositorio_de_hechos.saveByFuente(hechos_recibidos, fuente);
        }
    }

    private List<Fuente> filtrarFuentesRepetidas(List<Fuente> fuentes) {
    List<Fuente> filtrado = new ArrayList<Fuente>();
    HashSet<String> vistos = new HashSet<String>(); // Hashset reduce complejidad. Mejor que una lista
    for (Fuente fuente : fuentes) { // Agrega los no vistos a la lista Filtrado. Los vistos son ignorados
        String clave = fuente.getId_externo() + "-"+ fuente.getId_interno();
        if(!vistos.contains(clave)) {
            vistos.add(clave);
            filtrado.add(fuente);
        }
    }
    return filtrado;
    }
}

//GIGA TODO: TENEMOS UN REPOSITORIO POR CADA TABLA DEL DER. EL PROBLEMA ESTA EN COMO HACES EL SELECT Y EL JOIN SIN TENER UNA BASE DE DATOS LEVANTADA
// TODO ENTONCES HAY QUE LEVANTAR LOS REPOS LOCALMENTE, QUE SE CONECTEN POR PK, FK Y QUE HAGAMOS EL SELECT A MANO DENTRO DE MEMORIA
// TODO LOS REPOSITORIOS DEBERIAN CONOCERSE ENTRE SI, O SEA QUE EL REPOSITORIO DE HECHOS CONOZCA AL REPOSITORIO DE COLECCIONES Y ASI PODER HACER EL JOIN