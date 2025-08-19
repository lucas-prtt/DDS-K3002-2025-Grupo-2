package domain.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.colecciones.Coleccion;
import domain.colecciones.fuentes.Fuente;
import domain.colecciones.fuentes.FuenteId;
import domain.config.ConfigService;
import domain.hechos.Hecho;
import domain.services.ColeccionService;
import domain.services.FuenteService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ObjectInputFilter;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/apiAdministrativa")
public class ColeccionController {
    private ConfigService configService;
    private final String urlBaseAgregador;
    private final RestTemplate restTemplate;

    public ColeccionController(RestTemplateBuilder builder, ConfigService configService) {
        this.configService = configService;
        this.urlBaseAgregador = configService.getUrl();
        this.restTemplate = builder.build();
    }

    // Operaciones CREATE sobre Colecciones
    @PostMapping("/colecciones")
    public ResponseEntity<Object> crearColeccion(@RequestBody Coleccion coleccion) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> respuesta = restTemplate.postForEntity(urlBaseAgregador, coleccion, Object.class);
        return respuesta;
    }

    // Operaciones READ sobre Colecciones
    @GetMapping("/colecciones")
    public ResponseEntity<String> mostrarColecciones() {
        String url = urlBaseAgregador + "/colecciones";
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/colecciones/{id}")
    public ResponseEntity<String> mostrarColeccion(@PathVariable("id") String idColeccion) {
        String url = urlBaseAgregador + "/colecciones/" + idColeccion;
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }

    // Operaciones UPDATE sobre Colecciones
    @PatchMapping("/colecciones/{id}/algoritmo")
    public ResponseEntity<Void> modificarAlgoritmo(@PathVariable("id") String idColeccion,
                                                   @RequestBody String nuevoAlgoritmo) {
        coleccionService.modificarAlgoritmoDeColeccion(idColeccion, nuevoAlgoritmo);
        System.out.println("Coleccion: " + idColeccion + ", nuevo algoritmo: " + nuevoAlgoritmo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/colecciones/{id}/fuentes")
    public ResponseEntity<Void> agregarFuente(@PathVariable("id") String idColeccion,
                                              @RequestBody Fuente fuente) {
        fuenteService.guardarFuente(fuente);
        Coleccion coleccion = coleccionService.obtenerColeccion(idColeccion);
        coleccionService.agregarFuenteAColeccion(coleccion, fuente);
        coleccionService.guardarColeccion(coleccion);
        System.out.println("Coleccion: " + idColeccion + ", nueva fuente: id: " + fuente.getId().getIdExterno() + " tipo: " + fuente.getId().getTipo());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/colecciones/{id}/fuentes")
    public ResponseEntity<Void> quitarFuente(@PathVariable("id") String idColeccion,
                                             @RequestBody FuenteId fuenteId) {
        Coleccion coleccion = coleccionService.obtenerColeccion(idColeccion);
        coleccionService.quitarFuenteDeColeccion(coleccion, fuenteId);
        System.out.println("Coleccion: " + idColeccion + ", fuente quitada: id: " + fuenteId.getIdExterno() + " tipo: " + fuenteId.getTipo());
        return ResponseEntity.ok().build();
    }

    // Operaciones DELETE sobre Colecciones
    @DeleteMapping("/colecciones/{id}")
    public ResponseEntity<Void> eliminarColeccion(@PathVariable("id") String idColeccion) {
        // logica de eliminar una coleccion del repositorio
        coleccionService.eliminarColeccion(idColeccion);
        System.out.println("Coleccion: " + idColeccion + " eliminada");
        return ResponseEntity.ok().build();
    }
}