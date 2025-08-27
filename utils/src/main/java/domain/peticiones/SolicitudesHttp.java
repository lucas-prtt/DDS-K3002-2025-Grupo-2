package domain.peticiones;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SolicitudesHttp {
    private final RestTemplate restTemplate;

    public SolicitudesHttp(RestTemplateBuilder builder) {
        this.restTemplate = builder.requestFactory(()->new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault())).build();
    }

    public <T> ResponseEntity<T> get(String path, Class<T> responseType) {
        try {
            return ResponseEntity.ok(restTemplate.getForObject(path, responseType));
        }catch (Exception e) {
            System.out.println("Error en la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }

    public <T> ResponseEntity<T> post(String path, Object body, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            return restTemplate.postForEntity(path, entity, responseType);
        } catch (Exception e) {
            System.out.println("Error en POST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }

    public <T> ResponseEntity<T> delete(String path) {
        try {
            restTemplate.delete(path);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            System.out.println("Error en la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }

    public <T> ResponseEntity<T> patch(String path, Object body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            restTemplate.patchForObject(path, entity, Void.class);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error en PATCH: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }
}
