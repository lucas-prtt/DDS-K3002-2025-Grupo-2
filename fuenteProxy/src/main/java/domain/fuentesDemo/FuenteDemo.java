package domain.fuentesDemo;

import java.time.LocalDate;
import java.util.Map;


// FUENTE DEMO
public class FuenteDemo extends FuenteProxy {
    private LocalDate ultima_consulta;
    private Conexion biblioteca;

    public FuenteDemo(Long id, Conexion biblioteca) {
        super(id);
        this.ultima_consulta = LocalDate.now();
        this.biblioteca = biblioteca;
    }

    public  Map<String,Object> siguienteHecho(String url, LocalDate fecha_ultima_consulta) {
        //TODO
    }
}