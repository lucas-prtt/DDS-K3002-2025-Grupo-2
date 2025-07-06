package domain.colecciones;

import domain.hechos.Hecho;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Fuente{
    @Getter
    private String id_interno;
    @Getter
    private String id_externo;
    private TipoFuente tipo;

    public Fuente(String id_externo, TipoFuente tipo) {
        this.id_interno = UUID.randomUUID().toString();
        this.id_externo = id_externo;
        this.tipo = tipo;
    }

    public String getUrl() {
        String url = "http://localhost:";
        switch (tipo) {
            case ESTATICA:
                url += "8080/fuentesEstaticas/";
                break;
            case DINAMICA:
                url += "8081/fuentesDinamicas/";
                break;
            case PROXY:
                url += "8082/fuentesProxy/";
                break;
        }
        url += id_externo;
        return url;
    }

    public List<Hecho> hechos() {
        String url = getUrl() + "/hechos";
        RestTemplate restTemplate = new RestTemplate();
        return List.of(Objects.requireNonNull(restTemplate.getForObject(url, Hecho[].class)));

        //ResponseEntity<HechoInEstaticaDTO[]> response = restTemplate.getForEntity(url,HechoInEstaticaDTO[].class);
        //HechoInEstaticaDTO[] hechosDTO = response.getBody();
       //return Arrays.stream(hechosDTO).map(restTemplate.getForObject(url, HechoInEstaticaDTO.class));
        // consultar a la api -> mappear resultado a clase Hecho
        // para mappear el resultado habria que hacer un package mapper donde tengamos los mappers para construir un Hecho

        //String url = "https://mocki.io/v1/66ea9586-9ada-4bab-a974-58abbe005292";
        //RestTemplate restTemplate = new RestTemplate();
        //List<Hecho> hechos = List.of(restTemplate.getForObject(url, Hecho[].class));
        //return hechos;
    }
}
