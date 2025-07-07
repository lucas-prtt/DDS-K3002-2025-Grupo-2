package domain.controllers;

import domain.hechos.Hecho;
import domain.services.FuenteMetamapaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fuentesMetamapa")
public class FuenteMetamapaController {
    private final FuenteMetamapaService fuenteMetamapaService;

    public FuenteMetamapaController(FuenteMetamapaService fuenteMetamapaService) {
        this.fuenteMetamapaService = fuenteMetamapaService;
    }


    @GetMapping("/hechos")
    public List<Hecho> obtenerHechos(){
        return fuenteMetamapaService.importarHechos();
    }

    @GetMapping("/colecciones/{id}/hechos")
    public List<Hecho> obtenerHechosPorColeccion(@PathVariable("id") Long idColeccion) {
        return fuenteMetamapaService.obtenerHechosColeccion(idColeccion);
    }
    //TODO implmentar el post para solicitudes de eliminacion
}
