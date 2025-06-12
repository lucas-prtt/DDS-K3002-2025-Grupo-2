package domain.fuentes.fuentesMetamapa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.hechos.Hecho;
import domain.fuentes.FuenteProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

// METAMAPA EXTERNO
public class FuenteMetamapa extends FuenteProxy {
    public FuenteMetamapa(Long id) {
        super(id);
    }

    public List<Hecho> importarHechos() {
        String url = "https://mocki.io/v1/66ea9586-9ada-4bab-a974-58abbe005292";
        RestTemplate restTemplate = new RestTemplate();
        List<Hecho> hechos = List.of(restTemplate.getForObject(url, Hecho[].class));    //NO TOCAR
        return hechos;
    }


    public List<Hecho> obtenerHechosColeccion(String identificador) {
        String url = "https://mocki.io/v1/536a4049-29e5-4cb2-89c7-76c1b068a1a1";
        RestTemplate restTemplate = new RestTemplate();
        List<Hecho> hechos = List.of(restTemplate.getForObject(url, Hecho[].class));    //EL QUE LO TOCA ES PUTO
        return hechos;
    }
/*
    public void postearSolicitud(String json) {
        //TODO
    }

    public List<Hecho> filtrarHechos(List<Hecho> hechos,List<CriterioDePertenencia> filtros) {
        //TODO
    }*/
}
