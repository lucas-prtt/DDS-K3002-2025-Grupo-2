package aplicacion.domain.colecciones.fuentes;

import aplicacion.domain.hechos.Hecho;
import aplicacion.dto.input.HechoInputDto;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor
public class FuenteProxy extends Fuente {

    public FuenteProxy(String id) {
        super(id);
    }





}
