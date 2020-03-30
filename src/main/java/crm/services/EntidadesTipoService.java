package crm.services;

import crm.entities.*;
import crm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades pequenias (Tipo), como
 * {@link crm.entities.TipoMoneda}, {@link crm.entities.TipoDisponibilidad}, etc.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class EntidadesTipoService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoMoneda}
     */
    @Autowired
    private TipoMonedaRepository tipoMonedaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoDisponibilidad}
     */
    @Autowired
    private TipoDisponibilidadRepository tipoDisponibilidadRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoDominioCompu}
     */
    @Autowired
    private TipoDominioCompuRepository tipoDominioCompuRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSituacionLaboral}
     */
    @Autowired
    private TipoSituacionLaboralRepository tipoSituacionLaboralRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    private TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoPago}
     */
    @Autowired
    private TipoEstadoPagoRepository tipoEstadoPagoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoComprobante}
     */
    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoGrado}
     */
    @Autowired
    private TipoGradoRepository tipoGradoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoContacto}
     */
    @Autowired
    private TipoContactoRepository tipoContactoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoOportunidad}
     */
    @Autowired
    private TipoOportunidadRepository tipoOportunidadRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCompromiso}
     */
    @Autowired
    private TipoCompromisoRepository tipoCompromisoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstado}
     */
    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EstadoSolicitudCredencial}
     */
    @Autowired
    private EstadoSolicitudCredencialRepository estadoSolicitudCredencialRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstudio}
     */
    @Autowired
    private TipoEstudioRepository tipoEstudioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoEstudio}
     */
    @Autowired
    private TipoEstadoEstudioRepository tipoEstadoEstudioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoActividadExalumno}
     */
    @Autowired
    private TipoActividadExalumnoRepository tipoActividadExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCargo}
     */
    @Autowired
    private TipoCargoRepository tipoCargoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Idioma}
     */
    @Autowired
    private IdiomaRepository idiomaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCampana}
     */
    @Autowired
    private TipoCampanaRepository tipoCampanaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ComoSupoDeRedExalumnos}
     */
    @Autowired
    private ComoSupoDeRedExalumnoRepository comoSupoDeRedExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoCivil}
     */
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoUsuario}.
     */
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoOferta}.
     */
    @Autowired
    private TipoOfertaRepository tipoOfertaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCompetencia}
     */
    @Autowired
    private TipoCompetenciaRepository tipoCompetenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCompetencia}
     */
    @Autowired
    private TipoNivelInteresRepository tipoNivelInteresRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompetenciaUsmempleo}
     */
    @Autowired
    private CompetenciaUsmempleoRepository competenciaUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoArea}
     */
    @Autowired
    private TipoAreaRepository tipoAreaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoFormaPago}
     */
    @Autowired
    private TipoFormaPagoRepository tipoFormaPagoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSector}
     */
    @Autowired
    private TipoSectorRepository tipoSectorRepository;





    /**
     * Obtiene una instancia de {@link crm.entities.TipoMoneda} segun un id dado.
     *
     * @param cod Codigo de la {@link crm.entities.TipoMoneda} que se desea encontrar.
     *
     * @return Objeto de la clase {@link crm.entities.TipoMoneda}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public TipoMoneda getTipoMoneda(Short cod){

        return tipoMonedaRepository.findByCodigo(cod);
    }

    /**
     * Obtiene una instancia de {@link crm.entities.TipoDisponibilidad} segun un id dado.
     *
     * @param id Id del {@link crm.entities.TipoDisponibilidad}
     *
     * @return Objeto de la clase {@link crm.entities.TipoDisponibilidad}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public TipoDisponibilidad getTipoDisponibilidad(Short id){

        return tipoDisponibilidadRepository.findByCodigo(id);
    }

    /**
     * Obtiene una instancia de {@link crm.entities.TipoDominioCompu} segun un id dado.
     *
     * @param cod Codigo del {@link crm.entities.TipoDominioCompu} que se desea encontrar
     *
     * @return Objeto de la clase {@link crm.entities.TipoDominioCompu}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public TipoDominioCompu getDominioComputacional(Short cod){

        return tipoDominioCompuRepository.findByCodigo(cod);
    }

    /**
     * Obtiene una instancia de {@link crm.entities.TipoSituacionLaboral} segun un id dado.
     *
     * @param cod Codigo del {@link crm.entities.TipoSituacionLaboral}.
     *
     * @return Objeto de la clase {@link crm.entities.TipoSituacionLaboral}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public TipoSituacionLaboral getSituacionLaboral(Short cod){

        return tipoSituacionLaboralRepository.findByCodigo(cod);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.TipoMoneda}.
     *
     * @return Collection ({@link java.util.Iterator}) de {@link crm.entities.TipoMoneda}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<TipoMoneda> getMonedas(){
        return tipoMonedaRepository.findAll();
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.TipoDisponibilidad}.
     *
     * @return Collection ({@link java.util.Iterator}) de {@link crm.entities.TipoDisponibilidad}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<TipoDisponibilidad> getDisponibilidades(){
        return tipoDisponibilidadRepository.findAll();
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.TipoDominioCompu}.
     *
     * @return Collection ({@link java.util.Iterator}) de {@link crm.entities.TipoDominioCompu}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<TipoDominioCompu> getDominiosComputacionales(){
        return tipoDominioCompuRepository.findAll();
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.TipoSituacionLaboral}.
     *
     * @return Collection ({@link java.util.Iterator}) de {@link crm.entities.TipoSituacionLaboral}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<TipoSituacionLaboral> getsituacionesLaborales(){
        return tipoSituacionLaboralRepository.findAll();
    }

     /** Obtiene un listado de todos los {@link crm.entities.TipoGrado}.
     *
     * @return Lista de todos las {@link crm.entities.TipoGrado}.
      *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
     //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoGrado> buscarTodosTiposGrados() {
        return tipoGradoRepository.findAll();
    }


    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoVigencia},
     *
     * @return Lista de todos los {@link crm.entities.TipoVigencia}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoVigencia> buscarTodosTiposVigencia() {
        return tipoVigenciaRepository.findAll();
    }

    /**
     * Obtiene una instancia de {@link crm.entities.TipoVigencia} segun un id dado.
     *
     * @param codVigencia id del TipoVigencia a buscar.
     *
     * @return Objeto de la clase {@link crm.entities.TipoVigencia}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Ver')")
    public TipoVigencia buscarTipoVigenciaPorId(Short codVigencia){

        return tipoVigenciaRepository.findByCodVigencia(codVigencia);
    }

    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoEstadoPago},
     *
     * @return Lista de todos los {@link crm.entities.TipoEstadoPago}.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoEstadoPago> buscarTodosTiposEstadoPago() {
        return tipoEstadoPagoRepository.findAll();
    }

    /**
     * Obtiene una instancia de {@link crm.entities.TipoEstadoPago} segun un id dado.
     *
     * @param codEstado id del TipoEstadoPago a buscar.
     *
     * @return Objeto de la clase {@link crm.entities.TipoEstadoPago}.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Ver')")
    /*public TipoEstadoPago buscarTipoEstadoPagoPorId(Short codEstado){

        return tipoEstadoPagoRepository.findByCodEstado(codEstado);
    }*/

    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoComprobante},
     *
     * @return Lista de todos los {@link crm.entities.TipoComprobante}.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoComprobante> buscarTodosTiposComprobante() {
        return tipoComprobanteRepository.findAll();
    }


    /**
     * Obtiene una instancia de {@link crm.entities.TipoGrado} segun un id dado.
     *
     * @param codGrado id del TipoGrado a buscar.
     *
     * @return Objeto de la clase {@link crm.entities.TipoVigencia}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Ver')")
    public TipoGrado buscarTipoGradoPorId(Short codGrado){

        return tipoGradoRepository.findByCodGrado(codGrado);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @return Collection ({@link java.util.Iterator}) de {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<EstadoSolicitudCredencial> getEstadosCredenciales(){
        return estadoSolicitudCredencialRepository.findAll();
    }


    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoContacto},
     *
     * @return Lista de todos los {@link crm.entities.TipoContacto}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoContacto> buscarTodosTipoContacto() {
        return tipoContactoRepository.findAll();
    }


    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoOportunidad},
     *
     * @return Lista de todos los {@link crm.entities.TipoOportunidad}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoOportunidad> buscarTodosTipoOportunidad() {
        return tipoOportunidadRepository.findAll();
    }

    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoOportunidad},
     *
     * @return Lista de todos los {@link crm.entities.TipoOportunidad}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    public List<TipoCompromiso> buscarTodosTipoCompromiso() {
        return tipoCompromisoRepository.findAll();
    }

    /**
     * Obtiene una instantcia de {@link crm.entities.TipoCompromiso},
     *
     * @return Instancia de {@link crm.entities.TipoCompromiso}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    public TipoCompromiso buscarTipoCompromisoPorCodCompromiso(short codCompromiso) {
        return tipoCompromisoRepository.findOne(codCompromiso);
    }

    public List<String> buscarTodosTipoInteresOportunidad() {
        return tipoOportunidadRepository.findAllTipoInteres();
    }




    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoEstado}, en los que puede estar un contacto
     * (reunión) con una {@link crm.entities.Empresa}
     *
     * @return Lista de todos los {@link crm.entities.TipoEstado}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoEstado> buscarTodosTipoEstado() {
        return tipoEstadoRepository.findAll();
    }

    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoNivelInteres}, en los que puede estar un contacto
     * (reunión) con una {@link crm.entities.Empresa}
     *
     * @return Lista de todos los {@link crm.entities.TipoNivelInteres}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoNivelInteres> buscarTodosTipoNivelInteres() {
        return tipoNivelInteresRepository.findAllByOrderByCodNivelInteresAsc();
    }

    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoCompetencia} que posee el sistema
     *
     * @return Lista de todos los {@link crm.entities.TipoCompetencia}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoCompetencia> buscarTodosTipoCompetencia() {
        return tipoCompetenciaRepository.findAll();
    }





    public Iterable<TipoEstudio> getEstudios(){
        return tipoEstudioRepository.findAll();
    }

    public Iterable<TipoEstadoEstudio> getEstadoEstudios(){
        return tipoEstadoEstudioRepository.findAll();
    }

    public Iterable<TipoActividadExalumno> getTipoActividades() {
        return tipoActividadExalumnoRepository.findAll();
    }

    public Iterable<TipoCargo> getCargos(){
        return tipoCargoRepository.findAll();
    }

    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoSituacionLaboral}, que puede tener un exalumno
     * a traves de la entidad {@link crm.entities.InfoProfesionalExalumno}.
     *
     * @return Lista de todos los {@link crm.entities.TipoSituacionLaboral}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     *
     */
    public List<TipoSituacionLaboral> buscarTodosTipoSituacionLaboral() { return (List<TipoSituacionLaboral>) tipoSituacionLaboralRepository.findAll(); }




    /** Obtiene un listado de todos los {@link crm.entities.Idioma}.
     *
     * @return Lista de todos los {@link crm.entities.Idioma}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<Idioma> buscarIdiomas() {
        return idiomaRepository.findAll();
    }



    /** Obtiene un listado de todos los {@link crm.entities.TipoCampana}.
     *
     * @return Lista de todos los {@link crm.entities.TipoCampana}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoCampana> listaTipoCampana() {
        return tipoCampanaRepository.findAll();
    }



    /**
     * Obtiene todos los {@link crm.entities.TipoEstadoCivil} de la base de datos
     *
     * @return todos los {@link crm.entities.TipoEstadoCivil} de la base de datos.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    public List<TipoEstadoCivil> listaTipoEstadoCivil() {
        return estadoCivilRepository.findAll();
    }



    /** Obtiene un listado de todos los {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @return Lista de todos los {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<ComoSupoDeRedExalumnos> listaTipoComoSupoDeRedExalumnos() {
        return comoSupoDeRedExalumnoRepository.findAll();
    }



    /** Obtiene un listado de todos los {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @return Lista de todos los {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<EstadoSolicitudCredencial> listaTipoEstadosSolicitudCredencial() {
        return estadoSolicitudCredencialRepository.findAll();
    }



    /** Obtiene un listado de todos los {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @return Lista de todos los {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    public List<TipoUsuario> listaTipoUsuarios() {
        return tipoUsuarioRepository.findAllByOrderByNombreAsc();
    }


    public TipoEstudio buscarTipoEstudioPorCodEstudio(Short codEstudio) {
        return tipoEstudioRepository.findOne(codEstudio);
    }

    public TipoEstadoEstudio buscarTipoEstadoEstudioPorCodEstadoEstudio(Short codEstadoEstudio) {
        return tipoEstadoEstudioRepository.findOne(codEstadoEstudio);
    }

    public List<TipoEstudio> getEstudiosUniversitarios() {
        return tipoEstudioRepository.getEstudiosUniversitarios();
    }

    public List<TipoEstudio> getEstudiosEscolares() {
        return tipoEstudioRepository.getEstudiosEscolares();
    }



    public List<TipoOferta> listaTipoOfertas() {
        return tipoOfertaRepository.findAll();
    }



    /** Obtiene un listado de todos los {@link crm.entities.TipoCargo}, ordenadas por nombre
     *
     * @return Lista de todos los {@link crm.entities.TipoCargo}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoCargo> listaTipoCargoOrdenPorNombreAsc() {
        return tipoCargoRepository.findAllByOrderByNomCargoAsc();
    }



    /** Obtiene un listado de todos los {@link crm.entities.TipoArea}, ordenadas por nombre
     *
     * @return Lista de todos los {@link crm.entities.TipoArea}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoArea> listaTipoAreaOrdenPorNombreAsc() {
        return tipoAreaRepository.findAllByOrderByNombreAsc();
    }



    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoEstudio} correspondientes a los utilizados en los
     *  {@link crm.entities.FiltroOfertaLaboral} (Pregrado, Doctorado, Postdoctorado, Magister), ordenadas por nombre
     *
     * @return Lista de todos los {@link crm.entities.TipoEstudio} ordenadas por nombre
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoEstudio> listaTipoEstudioDeFiltroOfertaLaboralOrdenPorNombreAsc() {
        return tipoEstudioRepository.listaTipoEstudioDeFiltroOfertaLaboralOrdenPorNombreAsc();
    }

    /** Obtiene un listado de todos los {@link crm.entities.TipoMoneda}, ordenadas por nombre
     *
     * @return Lista de todos los {@link crm.entities.TipoMoneda}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoMoneda> listaTipoMonedaOrdenPorNombreAsc() {
        return tipoMonedaRepository.findAllByOrderByNombreAsc();
    }

    /** Obtiene un listado de todos los {@link crm.entities.TipoFormaPago}, ordenadas por nombre
     *
     * @return Lista de todos los {@link crm.entities.TipoFormaPago}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<TipoFormaPago> listaTipoFormaPago() {
        return tipoFormaPagoRepository.findAll();
    }

    public TipoFormaPago buscarTipoFormaPagoPorCodFormaPago(Short codFormaPago) {
        return tipoFormaPagoRepository.findOne(codFormaPago);
    }

}
