package aplicacion.domain.colecciones.fuentes;

import aplicacion.domain.hechos.Hecho;
import aplicacion.dto.input.HechoInputDto;
import aplicacion.domain.conexiones.Conexion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public abstract class Fuente {

    @Id
    private String id;
    private String alias;
    private LocalDateTime ultimaPeticion;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hecho_fuente",
            joinColumns = @JoinColumn(name = "fuente_id"),
            inverseJoinColumns = @JoinColumn(name = "hecho_id"))
    private List<Hecho> hechos = new ArrayList<>();




    public Fuente(String id) {
        this.id = id;
        this.ultimaPeticion = null;
        this.alias = "Fuente sin título";
    }



    public void agregarHechos(List<Hecho> hechosAAgregar) {
        this.hechos.addAll(hechosAAgregar);
    }

    public void eliminarTodosLosHechos() {
        this.hechos.clear();
    }

}
