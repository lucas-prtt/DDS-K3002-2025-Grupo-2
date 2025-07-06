package domain.mappers;

import domain.dto.HechoInEstaticaDTO;
import domain.hechos.Hecho;

public class HechoInEstaticaDTOToHecho implements Mapper<HechoInEstaticaDTO, Hecho> {
    public Hecho map(HechoInEstaticaDTO hecho_dto) {
        Hecho hecho = new Hecho();
        hecho.setTitulo(hecho_dto.getTitulo());
        hecho.setDescripcion(hecho_dto.getDescripcion());
        hecho.setCategoria(hecho_dto.getCategoria());
        hecho.setUbicacion(hecho_dto.getUbicacion());
        hecho.setFecha_acontecimiento(hecho_dto.getFecha_acontecimiento());
        hecho.setOrigen(hecho_dto.getOrigen());
        hecho.setVisible(hecho_dto.getVisible());
        hecho.setEtiquetas(hecho_dto.getEtiquetas());
        return hecho;
    }
}
