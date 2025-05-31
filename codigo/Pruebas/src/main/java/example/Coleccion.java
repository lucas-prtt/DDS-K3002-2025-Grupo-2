package example;
import java.util.List;

// COLECCION
public class Coleccion{
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criterios_pertenencia;
    private Fuente fuente;
    private String identificadorHandle;

    public List<Hecho> mostrarHechos(){
        //TODO
    }

    public Boolean cumpleCriterios(Hecho hecho){
        //TODO
    }

    public List<Hecho> mostrarHechosFiltrados(List<CriterioDePertenencia> filtros){
        //TODO
    }

    public void validarIdentificador(String identificado){
        //TODO
    }
}