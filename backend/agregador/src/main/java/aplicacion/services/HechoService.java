package aplicacion.services;

import aplicacion.domain.colecciones.Coleccion;
import aplicacion.clasesIntermedias.HechoXColeccion;
import aplicacion.domain.colecciones.fuentes.Fuente;
import aplicacion.domain.hechos.Etiqueta;
import aplicacion.domain.hechos.Hecho;
import aplicacion.domain.usuarios.Contribuyente;
import aplicacion.dto.input.HechoInputDto;
import aplicacion.dto.mappers.HechoInputMapper;
import aplicacion.dto.mappers.HechoOutputMapper;
import aplicacion.dto.output.HechoOutputDto;
import aplicacion.excepciones.CategoriaYaPresenteException;
import aplicacion.excepciones.ContribuyenteNoConfiguradoException;
import aplicacion.excepciones.EtiquetaNoEncontradaException;
import aplicacion.repositorios.RepositorioDeEtiquetas;
import aplicacion.repositorios.RepositorioDeHechos;
import aplicacion.repositorios.RepositorioDeHechosXColeccion;
import aplicacion.excepciones.HechoNoEncontradoException;
import aplicacion.services.normalizador.NormalizadorDeHechos;
import aplicacion.utils.Md5Hasher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class HechoService {
    private final RepositorioDeHechos repositorioDeHechos;
    private final RepositorioDeHechosXColeccion repositorioDeHechosXColeccion;
    private final HechoOutputMapper hechoOutputMapper;
    private final NormalizadorDeHechos normalizadorDeHechos;
    private final HechoInputMapper hechoInputMapper;
    private final ContribuyenteService contribuyenteService;
    private final EtiquetaService etiquetaService;
    private final JdbcTemplate jdbcTemplate;
    public HechoService(RepositorioDeHechos repositorioDeHechos, RepositorioDeHechosXColeccion repositorioDeHechosXColeccion, HechoOutputMapper hechoOutputMapper, NormalizadorDeHechos normalizadorDeHechos, HechoInputMapper hechoInputMapper, ContribuyenteService contribuyenteService, EtiquetaService etiquetaService, JdbcTemplate jdbcTemplate) {
        this.repositorioDeHechos = repositorioDeHechos;
        this.repositorioDeHechosXColeccion = repositorioDeHechosXColeccion;
        this.hechoOutputMapper = hechoOutputMapper;
        this.normalizadorDeHechos = normalizadorDeHechos;
        this.hechoInputMapper = hechoInputMapper;
        this.contribuyenteService = contribuyenteService;
        this.etiquetaService = etiquetaService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void guardarHechos(List<Hecho> hechos) {
        for (Hecho hecho : hechos) {
            guardarOActualizarContribuyenteDeHecho(hecho);
        }
        repositorioDeHechos.saveAll(hechos);
    }

    public List<Hecho> obtenerHechos() {
        return repositorioDeHechos.findAll();
    }

    public Page<HechoOutputDto> obtenerHechosPorTextoLibreDto(String categoria,
                                                              LocalDateTime fechaReporteDesde,
                                                              LocalDateTime fechaReporteHasta,
                                                              LocalDateTime fechaAcontecimientoDesde,
                                                              LocalDateTime fechaAcontecimientoHasta,
                                                              Double latitud,
                                                              Double longitud,
                                                              String textoLibre,
                                                              Pageable pageable) {
        return repositorioDeHechos.filtrarHechosPorTextoLibre(categoria, fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, textoLibre, pageable).map(hechoOutputMapper::map);
    }

    public Page<Hecho> obtenerHechosPorTextoLibre(String textoLibre, Pageable pageable) {
        return repositorioDeHechos.findByTextoLibre(textoLibre, pageable);
    }

    public Page<HechoOutputDto> obtenerHechosAsDTO(String categoria,
                                                   LocalDateTime fechaReporteDesde,
                                                   LocalDateTime fechaReporteHasta,
                                                   LocalDateTime fechaAcontecimientoDesde,
                                                   LocalDateTime fechaAcontecimientoHasta,
                                                   Double latitud,
                                                   Double longitud,
                                                   Pageable pageable) {
        return repositorioDeHechos.filtrarHechos(categoria, fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, pageable).map(hechoOutputMapper::map);
    }

    public void guardarHechoPorColeccion(HechoXColeccion hechoPorColeccion) {
        Hecho hecho = hechoPorColeccion.getHecho();
        Coleccion coleccion = hechoPorColeccion.getColeccion();
        // Si el hecho cumple los criterios de pertenencia de la colección, entonces se guarda, en caso contrario no
        if (coleccion.cumpleCriterios(hecho)) {
            repositorioDeHechosXColeccion.save(hechoPorColeccion);
        }
    }

    @Transactional
    public List<HechoOutputDto> obtenerHechosDeContribuyente( Long contribuyenteId ) throws ContribuyenteNoConfiguradoException {
        contribuyenteService.obtenerContribuyente(contribuyenteId);
        return repositorioDeHechos.findByAutorId(contribuyenteId).stream().map(hechoOutputMapper::map).toList();
    }

    public Hecho obtenerHechoPorId(String idHecho)  throws HechoNoEncontradoException{
        return repositorioDeHechos.findById(idHecho).orElseThrow(() -> new HechoNoEncontradoException("No se encontro el hecho con id: " + idHecho));
    }

    public HechoOutputDto obtenerHechoDto(String idHecho) throws HechoNoEncontradoException {
        Hecho hecho = obtenerHechoPorId(idHecho);
        return hechoOutputMapper.map(hecho);
    }

    public Page<Hecho> obtenerHechosPorColeccion(String idColeccion,
                                                 String categoria,
                                                 LocalDateTime fechaReporteDesde,
                                                 LocalDateTime fechaReporteHasta,
                                                 LocalDateTime fechaAcontecimientoDesde,
                                                 LocalDateTime fechaAcontecimientoHasta,
                                                 Double latitud,
                                                 Double longitud,
                                                 Pageable pageable) {
        return repositorioDeHechos.findByFiltrosYColeccion(idColeccion, categoria, fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, pageable);
    }

    public Page<Hecho> obtenerHechosPorColeccionYTextoLibre(String idColeccion,
                                                            String categoria,
                                                            LocalDateTime fechaReporteDesde,
                                                            LocalDateTime fechaReporteHasta,
                                                            LocalDateTime fechaAcontecimientoDesde,
                                                            LocalDateTime fechaAcontecimientoHasta,
                                                            Double latitud,
                                                            Double longitud,
                                                            String textoLibre,
                                                            Pageable pageable) {
        return repositorioDeHechos.findByFiltrosYColeccionYTextoLibre(idColeccion, categoria, fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, textoLibre, pageable);
    }

    public Page<Hecho> obtenerHechosCuradosPorColeccion(String idColeccion,
                                                        String categoria,
                                                        LocalDateTime fechaReporteDesde,
                                                        LocalDateTime fechaReporteHasta,
                                                        LocalDateTime fechaAcontecimientoDesde,
                                                        LocalDateTime fechaAcontecimientoHasta,
                                                        Double latitud,
                                                        Double longitud,
                                                        Pageable pageable) {
        return repositorioDeHechos.findByFiltrosYColeccionCurados(idColeccion, categoria, fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, pageable);
    }

    public Page<Hecho> obtenerHechosCuradosPorColeccionYTextoLibre(String idColeccion,
                                                                   String categoria,
                                                                   LocalDateTime fechaReporteDesde,
                                                                   LocalDateTime fechaReporteHasta,
                                                                   LocalDateTime fechaAcontecimientoDesde,
                                                                   LocalDateTime fechaAcontecimientoHasta,
                                                                   Double latitud,
                                                                   Double longitud,
                                                                   String textoLibre,
                                                                   Pageable pageable) {
        return repositorioDeHechos.findByFiltrosYColeccionYTextoLibreCurados(idColeccion, categoria, fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, textoLibre, pageable);
    }

    public void guardarHecho(Hecho hecho) {
        guardarOActualizarContribuyenteDeHecho(hecho);
        repositorioDeHechos.save(hecho);
    }

    private void guardarOActualizarContribuyenteDeHecho(Hecho hecho) {
        if (hecho.getAutor() != null) {
            Contribuyente contribuyente = contribuyenteService.guardarOActualizar(hecho.getAutor());
            hecho.setAutor(contribuyente);
        }
    }

    public HechoOutputDto agregarHecho(HechoInputDto hechoInputDTO) {
        Hecho hecho = hechoInputMapper.map(hechoInputDTO);
        normalizadorDeHechos.normalizar(hecho);
        hecho = repositorioDeHechos.save(hecho);
        return hechoOutputMapper.map(hecho);
    }

    public void borrarHechosPorColeccion(Coleccion coleccion) {
        repositorioDeHechosXColeccion.deleteAllByColeccionId(coleccion.getId());
    }

    public void quitarHechosDeSublista(List<Hecho> listaOriginal, List<Hecho> hechosAQuitar){
        listaOriginal.removeIf(hechoA -> hechosAQuitar.stream().anyMatch(hechoB -> hechoA == hechoB));
    }

    public List<Hecho> hallarHechosDuplicadosDeBD(List<Hecho> hechosAEvaluar) {
        List<byte[]> hashes = hechosAEvaluar.stream().map(Hecho::getMd5Hash).toList();
        if (hashes.isEmpty()) return Collections.emptyList();

        // Crea una tabla temporal con una sola columna de Hashes y los inserta en la columna
        jdbcTemplate.execute("CREATE TEMPORARY TABLE tmp_hashes (hash BINARY(16) PRIMARY KEY)");
        String sqlInsert = "INSERT INTO tmp_hashes(hash) VALUES (?)";
        jdbcTemplate.batchUpdate(sqlInsert, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setBytes(1, hashes.get(i));
            }

            @Override
            public int getBatchSize() {
                return hashes.size();
            }
        });
        // Agarra los hechos repetidos
        String sqlQuery = "SELECT h.* FROM hecho h JOIN tmp_hashes t ON h.md5hash = t.hash";
        List<Hecho> resultados = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Hecho.class));
        // Borra los hashes que ya no importan
        jdbcTemplate.execute("DROP TEMPORARY TABLE tmp_hashes");

        return resultados;
    }

    public Map<Hecho, Long> contarHechosPorFuente(Coleccion coleccion) {
        List<Fuente> fuentes = coleccion.getFuentes();
        List<Hecho> hechos = fuentes.stream().flatMap(fuente -> fuente.getHechos().stream()).toList();
        Set<Hecho> hechosUnicos = new HashSet<>(hechos);
        Map<Hecho, Long> returnMap = new HashMap<>();
        for(Hecho hechoUnico : hechosUnicos){
            Long ocurrencias = 0L;
            for(Fuente fuente : fuentes){
                if(fuente.getHechos().contains(hechoUnico))
                    ocurrencias++;
            }
            returnMap.put(hechoUnico, ocurrencias);
        }
        return returnMap;
    }

    public List<Hecho> hallarHechosDuplicadosDeLista(List<Hecho> hechosAEvaluar){
        List<Hecho> hechosDuplicados = new ArrayList<>();
        Set<String> vistos = new HashSet<>();

        for (Hecho hecho : hechosAEvaluar) {
            if (!vistos.add(hecho.getClaveUnica())) {
                hechosDuplicados.add(hecho);
            }
        }
        return hechosDuplicados;
    }
    @Transactional
    public Etiqueta agregarEtiqueta(String hechoId, String etiquetaName) throws HechoNoEncontradoException {
        Etiqueta etiqueta;
        Optional<Hecho> hecho = repositorioDeHechos.findById(hechoId);
        if(hecho.isEmpty())
            throw new HechoNoEncontradoException("Hecho " + hechoId + " no encontrado");
        etiquetaName = normalizadorDeHechos.normalizarEtiqueta(etiquetaName);
        String finalEtiquetaName = etiquetaName;
        if(hecho.get().getEtiquetas().stream().anyMatch(etiqueta1 -> Objects.equals(etiqueta1.getNombre(), finalEtiquetaName))){
            throw new CategoriaYaPresenteException(hecho.get(), etiquetaName);
        }
        try {
            etiqueta = etiquetaService.obtenerEtiquetaPorNombre(etiquetaName);
        } catch (EtiquetaNoEncontradaException e) {
            etiqueta = etiquetaService.agregarEtiqueta(etiquetaName);
        }
        hecho.get().getEtiquetas().add(etiqueta);
        repositorioDeHechos.save(hecho.get());
        return etiqueta;
    }

    public HechoOutputDto eliminarEtiqueta(String hechoId, String etiquetaName) throws HechoNoEncontradoException, EtiquetaNoEncontradaException {
        Optional<Hecho> hecho = repositorioDeHechos.findById(hechoId);
        if(hecho.isEmpty())
            throw new HechoNoEncontradoException("Hecho " + hechoId + " no encontrado");
        List<Etiqueta> etiquetas = hecho.get().getEtiquetas();
        if(etiquetas.stream().anyMatch(etiqueta -> Objects.equals(etiqueta.getNombre(), etiquetaName))) {
            etiquetas.removeIf(etiqueta -> Objects.equals(etiqueta.getNombre(), etiquetaName));
            repositorioDeHechos.save(hecho.get());
            return hechoOutputMapper.map(hecho.get());
        }
        throw new EtiquetaNoEncontradaException("No se encontro la etiqeuta" + etiquetaName);

    }
}