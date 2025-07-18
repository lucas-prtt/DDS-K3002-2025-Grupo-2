package domain.services;

import domain.dto.SolicitudDTO;
import domain.hechos.Hecho;
import domain.mappers.SolicitudMapper;
import domain.repositorios.RepositorioDeHechos;
import domain.repositorios.RepositorioDeSolicitudes;
import domain.solicitudes.SolicitudEliminacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SolicitudService {
    private final RepositorioDeSolicitudes repositorioDeSolicitudes;
    private final RepositorioDeHechos repositorioDeHechos;

    public SolicitudService(RepositorioDeSolicitudes repositorioDeSolicitudes, RepositorioDeHechos repositorioDeHechos) {
        this.repositorioDeSolicitudes = repositorioDeSolicitudes;
        this.repositorioDeHechos = repositorioDeHechos;
    }

    public void guardarSolicitud(SolicitudEliminacion solicitud) {
        repositorioDeSolicitudes.save(solicitud);
    }

    public void guardarSolicitudDto(SolicitudDTO solicitudDto) {
        Hecho hecho = repositorioDeHechos.findById(solicitudDto.getHechoId())
                .orElseThrow(() -> new NoSuchElementException("Hecho no encontrado con ID: " + solicitudDto.getHechoId()));
        SolicitudEliminacion solicitud = new SolicitudMapper().map(solicitudDto, hecho);
        guardarSolicitud(solicitud);
    }

    public List<SolicitudEliminacion> obtenerSolicitudes() {
        return repositorioDeSolicitudes.findAll();
    }

    public SolicitudEliminacion obtenerSolicitud(Long id) {
        return repositorioDeSolicitudes.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada con ID: " + id));
    }

    public void eliminarSolicitud(Long id) {
        if (repositorioDeSolicitudes.existsById(id)) {
            repositorioDeSolicitudes.deleteById(id);
        } else {
            throw new NoSuchElementException("Solicitud no encontrada con ID: " + id);
        }
    }
}
