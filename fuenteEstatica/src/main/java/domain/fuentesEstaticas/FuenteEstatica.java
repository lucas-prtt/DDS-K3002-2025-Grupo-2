package domain.fuentesEstaticas;

import domain.hechos.Hecho;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// FUENTE ESTATICA
public class FuenteEstatica implements Fuente {
    private List<String> archivos;
    private LectorCsv lector_archivo;

    public FuenteEstatica(LectorCsv lector_archivo){
        this.archivos = new ArrayList<>();
        this.lector_archivo = lector_archivo;
    }

    public void agregarArchivo(String archivo){
        archivos.add(archivo);
    }

    public List<Hecho> importarHechos() {
        List<Hecho> hechos = new ArrayList<>();
        archivos.forEach(archivo -> hechos.addAll(lector_archivo.leerHechos(archivo)));
        return hechos;
    }
}