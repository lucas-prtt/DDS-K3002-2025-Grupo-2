package aplicacion.dto.output;

import lombok.Setter;

@Setter
public class CriterioDeDistanciaOutputDto extends CriterioDePertenenciaOutputDto{
    private UbicacionOutputDto ubicacionBase;
    private Double distanciaMinima;

    public CriterioDeDistanciaOutputDto(Long id, UbicacionOutputDto ubicacionBase, Double distanciaMinima) {
        super(id);
        this.ubicacionBase = ubicacionBase;
        this.distanciaMinima = distanciaMinima;
    }
}
