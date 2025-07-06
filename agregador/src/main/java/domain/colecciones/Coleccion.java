package domain.colecciones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.algoritmos.Algoritmo;
import domain.hechos.Hecho;
import domain.criterios.CriterioDePertenencia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import domain.repositorios.RepositorioHechosXColeccion;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// COLECCION
@Entity
public class Coleccion{
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criterios_pertenencia;
    @Getter
    private List<Fuente> fuentes;
    @Getter
    @Id
    private String identificador_handle;
    private Algoritmo algoritmo_consenso;
    private RepositorioHechosXColeccion repositorio_hechos;
    //tiene todos los hechos que de las fuentes de esta coleccion
    //TODO: SUPONEMOS QUE REPOSITORIO HECHOS X COLECCION ES UN REPOSITORIO QUE CONTIENE TODOS LOS HECHOS DE ESTA COLECCION
    //JUNTO CON SUS RESPECTIVOS ATRIBUTOS.

    // La base de datos nos pasa una tupla:
            //          (Hecho, EsConsensuado)
    // Como sabemos establecemos el valor de EsConsensuado en la BD?
            // Se corre un proceso a las 2 de la mañana que actualiza la BD
    // Como se sabe que algoritmo usar?

    // TODO: Ver que no se cargue dos veces el mismo hecho si dos colecciones comparten la fuente


    public Coleccion(String titulo, String descripcion, Algoritmo algoritmo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.criterios_pertenencia = new ArrayList<>();
        this.fuentes = new ArrayList<>();
        this.identificador_handle = UUID.randomUUID().toString().replace("-", "");
        this.algoritmo_consenso = algoritmo;
        // Tal vez convenga delegar esto en otra clase
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Leemos config.json desde el classpath (src/main/resources)
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.json");
            if (input == null) {
                throw new RuntimeException("No se encontró config.json en resources");
            }

            // Parseamos el JSON y extraemos el campo "fuente"
            JsonNode root = mapper.readTree(input);
            JsonNode fuentesNode = root.path("fuentes");
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar config.json", e);
        }
    }

    public List<Hecho> mostrarHechosIrrestrictos(List<CriterioDePertenencia> filtros) {
        // logica de buscar los hechos del repositorio
        return repositorio_hechos.findAll().stream().filter(hecho -> cumpleCriterios(filtros, (Hecho)(hecho)).collect(Collectors.toList());
    }

    public void cargarHechos(){
        List<Hecho> todosLosHechos = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            RestTemplate restTemplate = new RestTemplate();

            for (Fuente fuente : fuentes) {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(fuente.GetURL(), String.class);
                    String json = response.getBody();

                    List<Hecho> hechos = mapper.readValue(json, new TypeReference<List<Hecho>>() {});
                    todosLosHechos.addAll(hechos);
                } catch (Exception e) {
                    System.err.println("Error al consumir la API en " + fuente.getId_externo() + ": " + e.getMessage());
                    // Seguir con la siguiente fuente aunque haya error
                }
            }
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        // Agrega todos los hechos que cumplen con los filtros pasados
        repositorio_hechos.agregarTodos(todosLosHechos,identificador_handle);
    }

    // Metodo sobrecargado sin parámetros que usa una lista vacía por defecto
    public List<Hecho> mostrarHechosIrrestrictos() {
        return mostrarHechosIrrestrictos(criterios_pertenencia);
    }

    public List<Hecho> mostrarHechosCurados(List<CriterioDePertenencia> filtros){

    }

    // Metodo sobrecargado sin parámetros que usa una lista vacía por defecto
    public List<Hecho> mostrarHechosCurados() {
        return mostrarHechosCurados(new ArrayList<>());
    }

    public Boolean cumpleCriterios(List<CriterioDePertenencia> criterios_pertenencia, Hecho hecho){
        return criterios_pertenencia.stream().allMatch(criterio -> criterio.cumpleCriterio(hecho));
    }
}
