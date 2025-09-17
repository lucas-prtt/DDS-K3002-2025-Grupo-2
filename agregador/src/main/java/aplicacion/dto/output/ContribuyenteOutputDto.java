package aplicacion.dto.output;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ContribuyenteOutputDto {
    private Long id;
    private Boolean esAdministrador;
    private IdentidadContribuyenteOutputDto identidad;
}
