package aplicacion.services;

import aplicacion.repositorios.RepositorioDeContribuyentes;
import aplicacion.domain.usuarios.Contribuyente;
import aplicacion.domain.usuarios.IdentidadContribuyente;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContribuyenteService {
    private final RepositorioDeContribuyentes repositorioDeContribuyentes;

    public ContribuyenteService(RepositorioDeContribuyentes repositorioDeContribuyentes) {
        this.repositorioDeContribuyentes = repositorioDeContribuyentes;
    }

    @Transactional
    public void guardarContribuyente(Contribuyente contribuyente) {
        if (!repositorioDeContribuyentes.existsById(contribuyente.getId())) {
            repositorioDeContribuyentes.save(contribuyente);
        }
    }
    @Transactional
    public Contribuyente obtenerContribuyentePorId(Long id) {
        return repositorioDeContribuyentes.findById(id)
                .map(contribuyente -> {
                    Hibernate.initialize(contribuyente.getId());
                    Hibernate.initialize(contribuyente.getUltimaIdentidad());
                    Hibernate.initialize(contribuyente.getSolicitudesEliminacion());
                    return contribuyente;
                })
                .orElseGet(() -> { // TODO: Cambiar esto
                    // Si no existe, lo creamos
                    Contribuyente nuevo = new Contribuyente(false);
                    nuevo.setId(id);
                    return repositorioDeContribuyentes.save(nuevo);
                });
    }
    @Transactional
    public Contribuyente agregarIdentidadAContribuyente(Long id, IdentidadContribuyente identidad) {
        Contribuyente contribuyente = obtenerContribuyentePorId(id);
        contribuyente.agregarIdentidad(identidad);
        return repositorioDeContribuyentes.save(contribuyente);
    }
}
