package domain.dashboardDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContribuyenteDTO {
    private String contribuyenteId;
    private boolean esAdministrador;
}