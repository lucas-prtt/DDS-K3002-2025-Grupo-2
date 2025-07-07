package domain.colecciones;

import domain.hechos.Hecho;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class HechoXColeccion {

    // Getters y Setters
    @Setter
    @EmbeddedId
    private HechoXColeccionId id = new HechoXColeccionId();

    @ManyToOne
    @MapsId("hechoId")
    private Hecho hecho;

    @ManyToOne
    @MapsId("coleccionId")
    private Coleccion coleccion;

    @Setter
    private boolean consensuado;

    public void setHecho(Hecho hecho) {
        this.hecho = hecho;
        this.id.setHechoId(hecho.getId());
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
        this.id.setColeccionId(coleccion.getIdentificadorHandle());
    }
}

//esta clase representa la relación entre un hecho y una colección
// contiene un identificador compuesto que incluye el ID del hecho y el ID de la colección