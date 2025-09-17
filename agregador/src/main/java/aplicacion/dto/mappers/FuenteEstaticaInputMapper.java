package aplicacion.dto.mappers;

import aplicacion.domain.colecciones.fuentes.FuenteEstatica;
import aplicacion.dto.input.FuenteInputDto;
import org.springframework.stereotype.Component;

@Component
public class FuenteEstaticaInputMapper implements Mapper<FuenteInputDto, FuenteEstatica> {
    public FuenteEstatica map(FuenteInputDto fuenteInputDto){
        return new FuenteEstatica(
                fuenteInputDto.getId(),
                fuenteInputDto.getIp(),
                fuenteInputDto.getPuerto()
        );
    }
}
