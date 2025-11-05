package aplicacion.services.depurador;

import aplicacion.domain.colecciones.fuentes.Fuente;
import aplicacion.domain.colecciones.fuentes.FuenteDinamica;
import aplicacion.domain.hechos.Hecho;
import aplicacion.services.FuenteService;
import aplicacion.services.HechoService;
import aplicacion.excepciones.HechoNoEncontradoException;
import aplicacion.utils.ProgressBar;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DepuradorDeHechos {
    private final HechoService hechoService;
    private final FuenteService fuenteService;
    public DepuradorDeHechos(HechoService hechoService, FuenteService fuenteService) {
        this.hechoService = hechoService;
        this.fuenteService = fuenteService;
    }

    public void depurar(Map<Fuente, List<Hecho>> hechosPorFuente) {
        ProgressBar progressBar = new ProgressBar(hechosPorFuente.size()+1, "  fuentes");
        long inicio = System.nanoTime();
        long totalDeHechos = 0;
        for (Map.Entry<Fuente, List<Hecho>> entry: hechosPorFuente.entrySet()) {
            Fuente fuente = entry.getKey();
            List<Hecho> hechos = new ArrayList<>(entry.getValue());
            totalDeHechos+=hechos.size();
            progressBar.avanzar( " fuente: " + fuente.getId() );

            long inicioFuente = System.nanoTime();
            List<Hecho> hechosDuplicadosDeBD = hechoService.hallarHechosDuplicadosDeBD(hechos);
            List<Hecho> hechosDuplicadosDeLista = hechoService.hallarHechosDuplicadosDeLista(hechos);



            // Saca los que ya estan en la BD de lista "hechos"
            hechoService.quitarHechosDeSublista(hechos, hechosDuplicadosDeBD);
            hechoService.quitarHechosDeSublista(hechos, hechosDuplicadosDeLista);
            hechos.addAll(hechosDuplicadosDeLista); // Los agrega 1 vez para que estén.

            // No guarda los repetidos
            hechoService.guardarHechos(hechos);

            // Usa los que se van a crear
            fuente.agregarHechos(hechos);

            // Usa los de la BD
            fuente.agregarHechos(hechosDuplicadosDeBD);


            fuenteService.guardarFuente(fuente);

            }
        long fin = System.nanoTime();
        long totalTiempo = fin - inicio;
        System.out.printf("\nFin depuración de hechos \n Tiempo: %.2fs \n %.2f hechos/s \n\n", totalTiempo /1_000_000_000.0 , totalDeHechos / (totalTiempo/1_000_000_000.0));


    }
}
