package aplicacion.controllers;

import aplicacion.config.ConfigService;
import domain.peticiones.ResponseWrapper;
import domain.peticiones.SolicitudesHttp;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SolicitudController {
    private final ConfigService configService;
    private final SolicitudesHttp solicitudesHttp;

    public SolicitudController(@Lazy ConfigService configService) {
        this.configService = configService;
        this.solicitudesHttp = new SolicitudesHttp(new RestTemplateBuilder());
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<?> crearSolicitud(@RequestBody String body) {
        ResponseEntity<String> response = solicitudesHttp.post(configService.getUrlAgregador() + "/solicitudes", body, String.class);
        return ResponseWrapper.wrapResponse(response);
    }
}