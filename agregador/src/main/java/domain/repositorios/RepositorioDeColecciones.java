package domain.repositorios;

import domain.colecciones.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RepositorioDeColecciones extends JpaRepository<Coleccion, String> {
    // REPOSITORIO DE COLECCIONES

}
