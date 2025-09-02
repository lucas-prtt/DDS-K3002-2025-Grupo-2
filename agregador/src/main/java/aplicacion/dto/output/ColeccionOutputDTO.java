package aplicacion.dto.output;

import aplicacion.domain.algoritmos.AlgoritmoConsenso;
import aplicacion.domain.colecciones.fuentes.Fuente;
import aplicacion.domain.criterios.CriterioDePertenencia;
import lombok.Getter;

import java.util.List;
@Getter
public class ColeccionOutputDTO {
    private String id;
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criteriosDePertenencia;
    private List<Fuente> fuentes;
    private AlgoritmoConsenso algoritmoConsenso;

    public ColeccionOutputDTO(String id, String titulo, String descripcion, List<CriterioDePertenencia> criteriosDePertenencia, List<Fuente> fuentes, AlgoritmoConsenso algoritmoConsenso) {
    }
}
