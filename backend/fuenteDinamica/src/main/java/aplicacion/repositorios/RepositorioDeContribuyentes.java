package aplicacion.repositorios;

import aplicacion.domain.usuarios.Contribuyente;
import aplicacion.domain.usuarios.IdentidadContribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioDeContribuyentes extends JpaRepository<Contribuyente, Long> {
    boolean existsByMail(String mail);

    Optional<Contribuyente> findByMail(String mail);
}
