package aplicacion.config;

import aplicacion.services.FuenteProxyService;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MetadataAutoUpdater {

    @Autowired
    private ApplicationInfoManager appInfoManager;

    @Autowired
    private FuenteProxyService fuenteProxyService;

    // Actualiza automáticamente cada 30 segundos (podés ajustar)
    @Scheduled(fixedRateString = "${eureka.metadata.refresh-interval:30000}")
    public void actualizarMetadata() {
        Map<String, String> metadata = appInfoManager.getInfo().getMetadata();

        List<String> fuentes = fuenteProxyService.obtenerFuentesDisponibles();
        String fuentesConcatenadas = String.join(",", fuentes);

        metadata.put("fuentesDisponibles", fuentesConcatenadas);
        appInfoManager.registerAppMetadata(metadata);
        appInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }
}
