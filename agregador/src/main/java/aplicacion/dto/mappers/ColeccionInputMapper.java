package aplicacion.dto.mappers;

import aplicacion.domain.colecciones.Coleccion;
import aplicacion.dto.input.ColeccionInputDTO;

public class ColeccionInputMapper {
    public ColeccionInputMapper() {}
    public Coleccion map(ColeccionInputDTO coleccionInputDTO) {
        return new Coleccion(
                coleccionInputDTO.getTitulo(),
                coleccionInputDTO.getDescripcion(),
                coleccionInputDTO.getCriteriosDePertenencia(),
                coleccionInputDTO.getFuentes(),
                coleccionInputDTO.getAlgoritmoConsenso()
        );
    }
}
