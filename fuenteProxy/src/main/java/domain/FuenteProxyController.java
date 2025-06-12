package domain;

import domain.fuentes.fuentesMetamapa.FuenteMetamapa;
import domain.fuentes.FuenteProxy;
import domain.hechos.Hecho;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;


@RestController
@RequestMapping("/fuentesProxy")
public class FuenteProxyController {
    private final Map<Long, FuenteProxy> fuentes = new HashMap<>();

    public FuenteProxyController() {
        FuenteMetamapa fuente = new FuenteMetamapa(1L);

        fuentes.put(fuente.getId(), fuente);
    }


    @GetMapping("/{id}/hechos")
    public List<Hecho> obtenerHechos(@PathVariable("id") Long id,
                                     @RequestParam(required=false) String categoria_buscada,
                                     @RequestParam(required=false) LocalDate fecha_reporte_desde,
                                     @RequestParam(required=false) LocalDate fecha_reporte_hasta,
                                     @RequestParam(required=false) LocalDate fecha_acontecimiento_desde,
                                     @RequestParam(required=false) LocalDate fecha_acontecimiento_hasta,
                                     @RequestParam(required=false) Double latitud,
                                     @RequestParam(required=false) Double longitud) {
        return fuentes.get(id).importarHechos().stream()
                .filter(h -> categoria_buscada == null || h.getCategoria().getNombre().equalsIgnoreCase(categoria_buscada))
                .filter(h -> fecha_reporte_desde == null ||  h.seCargoDespuesDe(fecha_reporte_desde))
                .filter(h -> fecha_reporte_hasta == null || h.seCargoAntesDe(fecha_reporte_hasta))
                .filter(h -> fecha_acontecimiento_desde == null || h.ocurrioDespuesDe(fecha_acontecimiento_desde))
                .filter(h -> fecha_acontecimiento_hasta == null || h.ocurrioAntesDe(fecha_acontecimiento_hasta))
                .filter(h -> latitud == null || h.getUbicacion().getLatitud().equals(latitud))
                .filter(h -> longitud == null || h.getUbicacion().getLongitud().equals(longitud))
                .collect(Collectors.toList()); //convierte el stream de elementos (después de aplicar los .filter(...), .map(...), etc.) en una lista (List<T>) de resultados.
    }

    //http://localhost:8082/hechos?categoria=robo&ubicacion={123222,123232}

    //http://localhost:8080/fuentesEstaticas/1/hechos
/*
    @GetMapping("/colecciones/{identificador}/hechos")
    public Hecho obtenerHechosColeccion(@PathVariable String identificador) {
        return fuente.obtenerHechosColeccion(identificador);
    }*/
}