package aplicacion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agregador {
    @Id
    private String agregadorID;

    private boolean tieneApiAdministrativa;

    private boolean tieneApiPublica;

}
