package domain.fuentes.fuentesDemo;

import domain.fuentes.FuenteProxy;
import domain.hechos.Hecho;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


// FUENTE DEMO
public class FuenteDemo extends FuenteProxy {
    private LocalDate ultima_consulta;
    private Conexion biblioteca;
    private List<Hecho> hechos;

    public FuenteDemo(Long id, Conexion biblioteca) {
        super(id);
        this.ultima_consulta = LocalDate.now();
        this.biblioteca = biblioteca;
    }

    public void pedirHechos() {
        // todo: basicamente pide hechos hasta que el map que llega esta vacio. Es la logica de negocio que indica el enunciado y es lo que hay que seguir
        // delegar peticion de hechos a la biblioteca
    }

    @Override
    public List<Hecho> importarHechos() {
        return hechos;
    }
}
