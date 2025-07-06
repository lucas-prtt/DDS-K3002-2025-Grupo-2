package domain.controllers;

import domain.colecciones.Coleccion;
import domain.hechos.Hecho;
import domain.repositorios.RepositorioDeColecciones;
import domain.repositorios.RepositorioDeHechos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agregador")
public class ColeccionController {
    private final RepositorioDeColecciones repositorio_de_colecciones;
    private final RepositorioDeHechos repositorio_hechos;

    public ColeccionController(RepositorioDeColecciones repositorioDeColecciones, RepositorioDeHechos repositorioHechos) {
        repositorio_de_colecciones = repositorioDeColecciones;
        repositorio_hechos = repositorioHechos;
    }

    // Operaciones CREATE sobre Colecciones
    @PostMapping("/colecciones")
    public ResponseEntity<Coleccion> crearColeccion(Coleccion coleccion) {
        // logica de crear una coleccion en el repositorio //todo
        repositorio_de_colecciones.save(coleccion);
        return ResponseEntity.ok(coleccion);
    }

    // Operaciones READ sobre Colecciones
    @GetMapping("/colecciones")
    public List<Coleccion> mostrarColecciones() {
        // logica de buscar las colecciones del repositorio //todo
        return repositorio_de_colecciones.findAll();
    }

    @GetMapping("/colecciones/{id}/hechosIrrestrictos")
    public List<Hecho> mostrarHechosIrrestrictos(@PathVariable("id") String id_coleccion) {
        // logica de buscar los hechos del repositorio //todo
        return repositorio_hechos.findByColeccionId(id_coleccion);
    }

    @GetMapping("/colecciones/{id}/hechosCurados")
    public List<Hecho> mostrarHechosCurados(){
        return null; // TODO
    }

    // Operaciones UPDATE sobre Colecciones

    // Operaciones DELETE sobre Colecciones
}
