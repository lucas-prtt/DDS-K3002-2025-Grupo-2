package aplicacion.config;

import aplicacion.services.FuenteProxyService;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Configuration
public class EurekaMetadataConfig {

    @Autowired
    private ApplicationInfoManager appInfoManager;

    @Autowired
    private FuenteProxyService fuenteProxyService; // tu servicio

    @PostConstruct
    public void inicializarMetadata() {
        List<String> fuentes = fuenteProxyService.obtenerFuentesDisponibles();

        String fuentesConcatenadas = String.join(",", fuentes);
        appInfoManager.getInfo().getMetadata().put("fuentesDisponibles", fuentesConcatenadas);

        appInfoManager.registerAppMetadata(appInfoManager.getInfo().getMetadata());
        appInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }
}
