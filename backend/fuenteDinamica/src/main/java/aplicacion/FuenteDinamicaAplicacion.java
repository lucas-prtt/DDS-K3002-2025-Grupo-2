package aplicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FuenteDinamicaAplicacion {
  public static void main(String[] args) {
    SpringApplication.run(FuenteDinamicaAplicacion.class, args);
  }
}