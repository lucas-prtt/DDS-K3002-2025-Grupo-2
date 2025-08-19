package domain.reenviarCosas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Reenviar {
    private final RestTemplate restTemplate;

    public Reenviar(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public <T> ResponseEntity<T> get(String path, Class<T> responseType) {
        return ResponseEntity.ok(restTemplate.getForObject(path, responseType));
    }

    public <T> ResponseEntity<T> post(String path, Object body, Class<T> responseType) {
        return restTemplate.postForEntity(path, body, responseType);
    }

    public void delete(String path) {
        restTemplate.delete(path);
    }

    public void patch(String path, Object body) {
        restTemplate.patchForObject(path, body, Void.class);
    }
}
