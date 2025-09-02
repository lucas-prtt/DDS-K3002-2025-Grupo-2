package aplicacion.domain.criterios;

import aplicacion.domain.hechos.Hecho;
import aplicacion.domain.hechos.Ubicacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// CRITERIO DE DISTANCIA
@Entity
@DiscriminatorValue("distancia")
@NoArgsConstructor
@Getter
@Setter
public class CriterioDeDistancia extends CriterioDePertenencia {
    @ManyToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacionBase;
    private Double distanciaMinima;

    public CriterioDeDistancia(Ubicacion ubicacionBase, Double distanciaMinima) {
        this.ubicacionBase = ubicacionBase;
        this.distanciaMinima = distanciaMinima;
    }

    @Override
    public Boolean cumpleCriterio(Hecho hecho){
        Ubicacion ubicacionHecho = hecho.getUbicacion();
        return ubicacionBase.distanciaA(ubicacionHecho) >= distanciaMinima;
    }
}
