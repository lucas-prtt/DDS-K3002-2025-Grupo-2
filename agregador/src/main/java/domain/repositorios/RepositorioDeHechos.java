package domain.repositorios;

import domain.hechos.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositorioDeHechos extends JpaRepository<Hecho, Long>, RepositorioDeHechosCustom {

}