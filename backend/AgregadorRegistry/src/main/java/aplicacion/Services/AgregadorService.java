package aplicacion.Services;

import aplicacion.Repositories.AgregadorRepository;
import aplicacion.domain.Agregador;
import org.springframework.stereotype.Service;

@Service
public class AgregadorService {

    public AgregadorRepository agregadorRepository;

    public AgregadorService(AgregadorRepository agregadorRepository) {
        this.agregadorRepository = agregadorRepository;
    }

    public Boolean chequearAgregadorExistente(String agregadorID) {
        return agregadorRepository.existsById(agregadorID);
    }


    public String crearAgregador() {
        Agregador agregador = new Agregador();
        agregadorRepository.save(agregador);
        return agregador.getAgregadorID();
    }
}
