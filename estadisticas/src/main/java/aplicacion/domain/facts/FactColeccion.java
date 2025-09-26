package aplicacion.domain.facts;

import aplicacion.domain.dimensiones.DimensionCategoria;
import aplicacion.domain.dimensiones.DimensionColeccion;
import aplicacion.domain.dimensiones.DimensionTiempo;
import aplicacion.domain.dimensiones.DimensionUbicacion;
import aplicacion.domain.id.FactColeccionId;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeExclude;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class FactColeccion {
    @EmbeddedId
    @HashCodeExclude
    private FactColeccionId id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("coleccionId")
    @JoinColumn(name = "coleccionId")
    private DimensionColeccion dimensionColeccion;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("ubicacionId")
    @JoinColumn(name = "ubicacionId")
    private DimensionUbicacion dimensionUbicacion;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("tiempoId")
    @JoinColumn(name = "tiempoId")
    private DimensionTiempo dimensionTiempo;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("categoriaId")
    @JoinColumn(name = "categoriaId")
    private DimensionCategoria dimensionCategoria;

    private Long cantidadHechos;
}
