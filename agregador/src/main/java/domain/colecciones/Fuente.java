package domain.colecciones;

import lombok.Getter;

public class Fuente{
    @Getter
    private String id_interno;
    @Getter
    private String id_externo;
    private String tipo;
    public Fuente(String id_interno, String id_externo, String tipo) {
        this.id_interno = id_interno;
        this.id_externo = id_externo;
        this.tipo = tipo;
    }
    public String GetURL() {
        return "https://www.fuentes.com/" + this.id_externo;
    }//todo: cambiar por la url de la fuente real

}
