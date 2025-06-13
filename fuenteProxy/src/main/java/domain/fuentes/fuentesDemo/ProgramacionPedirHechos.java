package domain.fuentes.fuentesDemo;

import domain.FuenteProxyController;
import domain.fuentes.FuenteProxy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProgramacionPedirHechos {
    private Map<Long, FuenteProxy> fuentes;
    @Scheduled(cron = "0 0 * * * *")  // Ejecuta al minuto 0 de cada hora
    public void pedirHechosCadaUnaHora() {
        fuentes.forEach( (id, fuente) -> {fuente.pedirHechos();});
    }
    public ProgramacionPedirHechos(Map<Long, FuenteProxy> fuentes) {//todo resolver el autowired, tengo sueño y no tiempo como para terminarlo
        this.fuentes = fuentes;
    }
}
