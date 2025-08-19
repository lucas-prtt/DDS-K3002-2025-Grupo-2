package domain.controllers;

import domain.dto.SolicitudDTO;
import domain.services.HechoService;
import domain.services.SolicitudService;
import domain.solicitudes.SolicitudEliminacion;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiAdministrativa")
public class SolicitudController {
    @Transactional
    @PatchMapping ("/solicitudes/{id}/estado")
    public ResponseEntity<Void> actualizarEstadoSolicitud(
            @PathVariable("id") Long id,
            @RequestBody String nuevoEstado) {
        List<SolicitudEliminacion> solis;
        SolicitudEliminacion sol;
        try {
            solis = solicitudService.solicitudesRelacionadas(id);
            sol = solicitudService.obtenerSolicitud(id); // El primero es la solicitud a cambiar
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        solicitudService.actualizarEstadoSolicitud(sol, nuevoEstado);

        for (SolicitudEliminacion solicitud : solis) {
            solicitudService.guardarSolicitud(solicitud);
        }
        hechoService.guardarHecho(sol.getHecho()); // Actualizamos el hecho (visible)

        System.out.println("Solicitud actualizada: " + sol.getId() + " a estado: " + nuevoEstado);
        return ResponseEntity.ok().build();
    }
}