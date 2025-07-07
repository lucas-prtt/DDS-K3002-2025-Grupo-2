package domain.colecciones;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HechoXColeccionId implements Serializable {

    private Long hechoId;
    private String coleccionId;

    public HechoXColeccionId() {}

    public HechoXColeccionId(Long hechoId, String coleccionId) {
        this.hechoId = hechoId;
        this.coleccionId = coleccionId;
    }

    // Getters y Setters
    public Long getHechoId() { return hechoId; }
    public void setHechoId(Long hechoId) { this.hechoId = hechoId; }

    public String getColeccionId() { return coleccionId; }
    public void setColeccionId(String coleccionId) { this.coleccionId = coleccionId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HechoXColeccionId)) return false;
        HechoXColeccionId that = (HechoXColeccionId) o;
        return Objects.equals(hechoId, that.hechoId) &&
                Objects.equals(coleccionId, that.coleccionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hechoId, coleccionId);
    }
}