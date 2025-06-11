package domain.colecciones;

import domain.hechos.Hecho;
import domain.criterios.CriterioDePertenencia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// COLECCION
public class Coleccion{

    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criterios_pertenencia;
    private String fuente;
    private String identificador_handle;

    public Coleccion(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.criterios_pertenencia = new ArrayList<>();
        this.identificador_handle = UUID.randomUUID().toString().replace("-", "");
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
            this.fuente = root.get("fuente").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar config.json", e);
        }
    }

    public List<Hecho> mostrarHechos(){
        // TODO: llamado de api fuente
        return null;
    }

    public Boolean cumpleCriterios(Hecho hecho){
        return criterios_pertenencia.stream().allMatch(criterio -> criterio.cumpleCriterio(hecho));
    }

    public List<Hecho> mostrarHechosFiltrados(){
        return mostrarHechos().stream().filter(hecho -> criterios_pertenencia.stream().allMatch(criterio -> criterio.cumpleCriterio(hecho))).collect(Collectors.toList());
    }
}
