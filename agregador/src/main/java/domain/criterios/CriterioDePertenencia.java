package domain.criterios;

import domain.hechos.Hecho;

// CRITERIO DE PERTENENCIA
public interface CriterioDePertenencia {
    Boolean cumpleCriterio(Hecho hecho);
}