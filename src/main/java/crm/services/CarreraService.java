package crm.services;


import crm.entities.*;
import crm.repositories.*;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.Carrera}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class CarreraService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoGrado}
     */
    @Autowired
    TipoGradoRepository tipoGradoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    private CarreraInstitucionRepository carreraInstitucionRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.AntecedenteEducacional}.
     */
    @Autowired
    private AntecedenteEducacionalRepository antecedenteEducacionalRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.FiltroOfertaLaboral}.
     */
    @Autowired
    private FiltroOfertaLaboralRepository filtroOfertaLaboralRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.OfertaCarreraUsmempleo}.
     */
    @Autowired
    private OfertaCarreraUsmempleoRepository ofertaCarreraUsmempleoRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.CarreraGrupo}.
     */
    @Autowired
    private CarreraGrupoRepository carreraGrupoRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.SimilitudProfesionalCarreras}.
     */
    @Autowired
    private SimilitudProfesionalCarrerasRepository similitudProfesionalCarrerasRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;


    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(CarreraService.class);




    /**
     * Obtiene un listado de todas las {@link crm.entities.Carrera},
     *
     * @return Coleccion ({@link java.util.List}) de todas las {@link crm.entities.Carrera}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public List<Carrera> buscarTodasCarrerasOrdenNombreCarreraAsc() {
        return carreraRepository.findAllByOrderByNombreCarreraAsc();
    }


    /**
     * Obtiene una {@link crm.entities.Carrera} específica, según el codCarrera asociado
     *
     * @codCarrera Identificador de la {@link crm.entities.Carrera} buscada
     *
     * @return Objeto {@link crm.entities.Carrera} buscada
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public Carrera buscarCarreraPorCodCarrera(Long codCarrera) {
        return carreraRepository.findByCodCarrera(codCarrera);
    }


    /**
     * Modifica la {@link crm.entities.Carrera} en la Base de Datos, según el objeto {@link crm.entities.Carrera}
     * pasado como parámetro que contiene los datos modificados
     *
     * @param carreraDatosModificar objeto carrera a guardar en el base de datos
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public void modificarCarrera(Carrera carreraDatosModificar) {

        Carrera carreraEnBD = carreraRepository.findByCodCarrera(carreraDatosModificar.getCodCarrera());
        Boolean modificado = false;

        // verifica cambios en el nombre de la carrera
        if ((carreraEnBD.getNombreCarrera() == null) || (carreraEnBD.getNombreCarrera() != null && carreraDatosModificar.getNombreCarrera() != null && carreraDatosModificar.getNombreCarrera().compareTo(carreraEnBD.getNombreCarrera()) != 0)){
            carreraEnBD.setNombreCarrera(carreraDatosModificar.getNombreCarrera().toUpperCase());
            modificado = true;
        }

        // verifica cambios en la abreviación de la carrera
        if ((carreraEnBD.getAbreviacion() == null) || (carreraEnBD.getAbreviacion() != null && carreraDatosModificar.getAbreviacion() != null && carreraDatosModificar.getAbreviacion().compareTo(carreraEnBD.getAbreviacion()) != 0)) {
            carreraEnBD.setAbreviacion(carreraDatosModificar.getAbreviacion());
            modificado = true;
        }

        // verifica cambios en el titulo de la carrera
        if ((carreraEnBD.getTitulo() == null) || (carreraEnBD.getTitulo() != null && carreraDatosModificar.getTitulo() != null && carreraDatosModificar.getTitulo().compareTo(carreraEnBD.getTitulo()) != 0)) {
            carreraEnBD.setTitulo(carreraDatosModificar.getTitulo());
            modificado = true;
        }

        // verifica cambios en la mencion de la carrera
        if ((carreraEnBD.getMencion() == null) || (carreraEnBD.getMencion() != null && carreraDatosModificar.getMencion() != null && carreraDatosModificar.getMencion().compareTo(carreraEnBD.getMencion()) != 0)) {
            carreraEnBD.setMencion(carreraDatosModificar.getMencion());
            modificado = true;
        }

        // verifica cambios en la duración de la carrera
        if ((carreraEnBD.getDuracion() == null) || (carreraEnBD.getDuracion() != null && carreraDatosModificar.getDuracion() != null && carreraDatosModificar.getDuracion() != carreraEnBD.getDuracion())) {
            carreraEnBD.setDuracion(carreraDatosModificar.getDuracion());
            modificado = true;
        }

        // verifica cambios en el vigencia de la carrera
        if ((carreraEnBD.getTipoVigencia() == null) || (carreraEnBD.getTipoVigencia() != null && carreraDatosModificar.getTipoVigencia() != null && carreraDatosModificar.getTipoVigencia().getCodVigencia() != carreraEnBD.getTipoVigencia().getCodVigencia())) {
            TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia(carreraDatosModificar.getTipoVigencia().getCodVigencia());

            carreraEnBD.setTipoVigencia(tipoVigencia);

            Date fechaActual = new java.util.Date();

            // cambia de estado de vigencia a las carrerasInstitucion asociadas a la Carrera
            for(CarreraInstitucion carreraInstitucion : carreraInstitucionRepository.buscarPorCodCarrera( carreraEnBD.getCodCarrera() )){

                carreraInstitucion.setTipoVigencia( tipoVigencia );

                // almacena quien modificó el registro
                carreraInstitucion.setRutUsuario( usuarioService.getCurrentUser().getRut());
                carreraInstitucion.setFechaModificacion(fechaActual);

                carreraInstitucionRepository.save(carreraInstitucion);
            }

            modificado = true;
        }

        // verifica cambios en el grado de la carrera
        if ((carreraEnBD.getTipoGrado() == null) || (carreraEnBD.getTipoGrado() != null && carreraDatosModificar.getTipoGrado() != null && carreraDatosModificar.getTipoGrado().getCodGrado() != carreraEnBD.getTipoGrado().getCodGrado())) {
            carreraEnBD.setTipoGrado(carreraDatosModificar.getTipoGrado());
            modificado = true;
        }

        // si se realizó una modificación en un atribudo de la carrera, se registra el rut y fecha de quien modificó
        if (modificado) {
            Date fechaActual = new java.util.Date();

            carreraEnBD.setRutUsuario( usuarioService.getCurrentUser().getRut() );
            carreraEnBD.setFechaModificacion(fechaActual);

            carreraRepository.save(carreraEnBD);
        }
    }







    /**
     * Agrega una nueva {@link crm.entities.Carrera} en la Base de Datos, según el objeto {@link crm.entities.Carrera}
     * pasado como parámetro, que contiene los datos a ser agregados
     *
     * @param carreraNueva objeto {@link crm.entities.Carrera} a agregar en el base de datos
     *
     * @return Retorna la {@link crm.entities.Carrera} con los datos ingresados en la Base de Datos
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public Carrera registrarCarrera(Carrera carreraNueva) {

        carreraNueva.setNombreCarrera(carreraNueva.getNombreCarrera().toUpperCase());

        TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia( carreraNueva.getTipoVigencia().getCodVigencia() );
        TipoGrado tipoGrado = tipoGradoRepository.findByCodGrado( carreraNueva.getTipoGrado().getCodGrado());

        // seteo de los objetos tipoVigencia y tipoGrado
        carreraNueva.setTipoVigencia( tipoVigencia );
        carreraNueva.setTipoGrado( tipoGrado );

        // almacena quien agregó el registro y la fecha actual
        Date fechaActual = new java.util.Date();
        carreraNueva.setRutUsuario( usuarioService.getCurrentUser().getRut() );
        carreraNueva.setFechaCreacion(fechaActual);
        carreraNueva.setFechaModificacion(fechaActual);

        carreraNueva = carreraRepository.save(carreraNueva);

        return carreraNueva;
    }



    /**
     * Elimina logicamente (cambio en el tipo de vigencia) una {@link crm.entities.Carrera},
     * según el identificador asociada a ella. Además elimina logícamente las {@link crm.entities.CarreraInstitucion}
     * asociadas a la carrera
     *
     * @param codCarrera identificador de  de la {@link crm.entities.Carrera} a eliminar logicamente
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public Carrera eliminarLogicamenteCarrera(String codCarrera) {

        Date fechaActual = new java.util.Date();

        Carrera carrera = this.buscarCarreraPorCodCarrera(Long.parseLong(codCarrera));

        // almacena quien modificó el registro
        carrera.setRutUsuario( usuarioService.getCurrentUser().getRut());
        carrera.setFechaModificacion(fechaActual);

        // eliminacion lógica de la carrera
        carrera.setTipoVigencia( tipoVigenciaRepository.findByCodVigencia( TipoVigencia.ID_ELIMINACION_LOGICA ));

        // eliminacion lógica de las carrerasInstitucion asociadas a la Carrera
        for(CarreraInstitucion carreraInstitucion : carreraInstitucionRepository.buscarPorCodCarrera(Long.parseLong(codCarrera))){
            carreraInstitucion.setTipoVigencia( tipoVigenciaRepository.findByCodVigencia( TipoVigencia.ID_ELIMINACION_LOGICA ) );

            // almacena quien modificó el registro
            carreraInstitucion.setRutUsuario( usuarioService.getCurrentUser().getRut());
            carreraInstitucion.setFechaModificacion(fechaActual);

            carreraInstitucionRepository.save(carreraInstitucion);
        }
        return carreraRepository.save(carrera);
    }



    /**
     * Obtiene una lista de  manera paginada de {@link crm.entities.Carrera}, seleccionandola por el calce de
     * nombre de carrera o abreviacion o titulo. (El calce de ellos)
     *
     * @param nombreCarrera nombre, abreviatura o titulo de una carrera que se desea calzar con la carrera buscada
     * @param tamanoPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Lista paginada con las {@link crm.entities.Carrera} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public Page<Carrera> buscarCarrerasPorCalceNombreOAbreviacionOTituloYVigencia(String nombreCarrera, String tipoVigencia, Integer tamanoPagina, Integer numeroPagina) {

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        if (tipoVigencia.compareTo("0") == 0) {
            tipoVigencia = "%";
        }

        return carreraRepository.buscarCarrerasPorCalceNombreOAbreviacionOTituloYVigencia(nombreCarrera, tipoVigencia, pageRequest);
    }



    /**
     * Obtiene una lista de  {@link crm.entities.Carrera}, seleccionandola por el calce de
     * nombre de carrera o abreviacion o titulo. (El calce de ellos)
     *
     * @param nombreCarrera nombre, abreviatura o titulo de una carrera que se desea calzar con la carrera buscada
     *
     * @return Lista paginada con las {@link crm.entities.Carrera} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public List<Carrera> buscarCarrerasPorCalceNombreOAbreviacionOTitulo(String nombreCarrera) {
        return carreraRepository.buscarCarrerasPorCalceNombreOAbreviacionOTitulo(nombreCarrera);
    }



    /**
     * Obtiene una {@link crm.entities.Carrera} registrada en la tabla academia.carrera, según un nombre específico
     * dado como parámetro
     *
     * @param nombreCarrera Nombre especifico (calce perfecto) de la carrera buscada
     *
     * @return {@link crm.entities.Carrera} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public Carrera buscarCarrerasPorNombreEspecifico(String nombreCarrera) {
        return carreraRepository.buscarCarrerasPorNombreEspecifico(nombreCarrera);
    }




    /**
     * Obtiene un listado de todas las {@link crm.entities.Carrera},
     * que esten asociadas a una {@link crm.entities.Institucion} en la tabla academia.carrera_institucion
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} que no se quieren mostrar sus carreras asociadas
     *
     * @return Coleccion ({@link java.util.List}) de todas las {@link crm.entities.Carrera} no asociadas a la
     *          {@link crm.entities.Institucion} .
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public List<Carrera> buscarCarrerasNoAsociadasAInstitucionPorCodInstitucion(String codInstitucion) {
        return carreraRepository.buscarCarrerasNoAsociadasAInstitucionPorCodInstitucion(Short.parseShort(codInstitucion));
    }




    /**
     * Obtiene un listado de todas las {@link crm.entities.Carrera}, que esten asociadas a una
     * {@link crm.entities.Institucion}
     *
     * @param idInstitucion Identificador de la {@link crm.entities.Institucion} a la cual se le quieren mostrar sus carreras asociadas
     *
     * @return Coleccion ({@link java.util.List}) de todas las {@link crm.entities.Carrera} asociadas a la
     *          {@link crm.entities.Institucion} .
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl)
     */
    public List<Carrera> buscarCarrerasPorCodInstitucion(Short idInstitucion) {
        return carreraRepository.buscarCarrerasPorCodInstitucion(idInstitucion);
    }




    /**
     * Realiza la mezcla de carreras seleccionadas. Para esto crea una nueva carrera con los datos ingresados, y actualiza
     * todos las tablas relacionadas a esta nueva carrera. Finalmente elimina las carreras que se han seleccionado para mezclar
     *
     * @param carrera Datos de la nueva {@link crm.entities.Carrera} que contendrá los datos de las carreras mezcladas
     * @param idsCarrerasMezclar Ids de las {@link crm.entities.Carrera} seleccionadas para mezclar
     *
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transactional
    //@PreAuthorize("hasPermission(#carrera, 'Editar') && hasPermission(carrera, 'Eliminar')")
    public void mezclarCarrera (Carrera carrera, String[] idsCarrerasMezclar) {

        Date fechaActual = new java.util.Date();

        // seteo de datos de usuario y fecha de la modificacion
        carrera.setRutUsuario(usuarioService.getCurrentUser().getRut());
        carrera.setFechaModificacion(fechaActual);
        carrera.setFechaCreacion(fechaActual);

        carreraRepository.save(carrera);


        //---> SIN PK (entidades asociadas que no posee como clave primaria el id de la carrera. Actualizacion directa)

        // actualizacion de AntecedenteEducacional
        for (String idCarrera : idsCarrerasMezclar) {

            List<AntecedenteEducacional> listaAntecedentesActualizar = antecedenteEducacionalRepository.buscarPorCodCarrera(Long.parseLong(idCarrera));

            for (AntecedenteEducacional antecedenteEducacional : listaAntecedentesActualizar) {
                antecedenteEducacional.setCarrera(carrera);
                antecedenteEducacional.setRutUsuario(usuarioService.getCurrentUser().getRut());
                antecedenteEducacional.setFechaModificacion(fechaActual);
            }

            antecedenteEducacionalRepository.save(listaAntecedentesActualizar);
        }


        //--> actualizacion de FiltroOfertaLaboral
        for (String idCarrera : idsCarrerasMezclar) {

            List<FiltroOfertaLaboral> listaFiltrosActualizar = filtroOfertaLaboralRepository.buscarPorCodCarrera(Long.parseLong(idCarrera));

            for (FiltroOfertaLaboral filtroOfertaLaboral : listaFiltrosActualizar) {
                filtroOfertaLaboral.setCarrera(carrera);
                filtroOfertaLaboral.setRutUsuario(usuarioService.getCurrentUser().getRut());
                filtroOfertaLaboral.setFechaModificacion(fechaActual);
            }

            filtroOfertaLaboralRepository.save(listaFiltrosActualizar);
        }



        //---> CON PK (entidades asociadas que posee como clave primaria el id de la carrera. Actualizacion segun el caso, debido a
        // la posibilidad de conflicto con la clave primaria si se hace el cambio)


        // actualizacion de CarreraInstitucion
        List<CarreraInstitucion> listaCarrerasInstitucionActualizar = new ArrayList<CarreraInstitucion>();

        for (String idCarrera : idsCarrerasMezclar) {
            listaCarrerasInstitucionActualizar.addAll(carreraInstitucionRepository.buscarPorCodCarrera(Long.parseLong(idCarrera)));
        }

        // Map que permitirá fusionar carreras asociadas a una misma Institucion (evitará problemas con llave primaria si se hace el cambio)
        Map<Short, CarreraInstitucion> carreraInstitucionMap = new HashMap<>();

        // agrega al map una CarreraInstitucion por Institucion (para evitar que en la fusion de la carrera se produzca problemas con llave primaria)
        for (CarreraInstitucion carreraInstitucion : listaCarrerasInstitucionActualizar) {

            // se agrega al map. En caso que no se agregó (debido a que existe otro), entonces se elimina
            if ( !carreraInstitucionMap.containsKey(carreraInstitucion.getCodInstitucion()) ) {
                carreraInstitucionMap.put(carreraInstitucion.getCodInstitucion(), carreraInstitucion);
            }
            else {
                carreraInstitucionRepository.eliminar(carreraInstitucion.getCodCarrera(), carreraInstitucion.getCodInstitucion());
            }
        }

        // iteracion en cada carreraInstitucion para cambiar la carrera
        for (CarreraInstitucion carreraInstitucion: carreraInstitucionMap.values()) {
            carreraInstitucionRepository.actualizarCodCarrera(carreraInstitucion.getCodCarrera(), carreraInstitucion.getCodInstitucion(), carrera.getCodCarrera());
        }



        // actualizacion de OfertaCarreraUsmempleo
        List<OfertaCarreraUsmempleo> listaOfertasCarreraActualizar = new ArrayList<OfertaCarreraUsmempleo>();

        for (String idCarrera : idsCarrerasMezclar) {
            listaOfertasCarreraActualizar.addAll(ofertaCarreraUsmempleoRepository.buscarPorCodCarrera(Long.parseLong(idCarrera)));
        }

        // Map que permitirá fusionar carreras asociadas a una misma OfertaLaboral (evitará problemas con llave primaria si se hace el cambio)
        Map<Long, OfertaCarreraUsmempleo> ofertaCarreraMap = new HashMap<>();

        // agrega al map una OfertaCarreraUsmempleo por OfertaLaboral (para evitar que en la fusion de la carrera se produzca problemas con llave primaria)
        for (OfertaCarreraUsmempleo ofertaCarrera : listaOfertasCarreraActualizar) {

            // se agrega al map. En caso que no se agregó (debido a que existe otro), entonces se elimina
            if ( !ofertaCarreraMap.containsKey(ofertaCarrera.getOfertaLaboralUsmempleoId()) ) {
                ofertaCarreraMap.put(ofertaCarrera.getOfertaLaboralUsmempleoId(), ofertaCarrera);
            }
            else {
                ofertaCarreraUsmempleoRepository.eliminar(ofertaCarrera.getCodCarrera(), ofertaCarrera.getOfertaLaboralUsmempleoId());
            }
        }

        // iteracion en cada OfertaCarreraUsmempleo para cambiar la carrera
        for (OfertaCarreraUsmempleo ofertaCarreraUsmempleo : ofertaCarreraMap.values())
        {
            ofertaCarreraUsmempleoRepository.actualizarCodCarrera(ofertaCarreraUsmempleo.getCodCarrera(), ofertaCarreraUsmempleo.getOfertaLaboralUsmempleoId(), carrera.getCodCarrera());
        }



        // actualizacion de CarreraGrupo
        List<CarreraGrupo> listaCarreraGrupoActualizar = new ArrayList<CarreraGrupo>();

        for (String idCarrera : idsCarrerasMezclar) {
            listaCarreraGrupoActualizar.addAll( carreraGrupoRepository.buscarPorCodCarrera(Long.parseLong(idCarrera)) );
        }

        // Map que permitirá fusionar carreras asociadas a una misma CarreraGrupo (evitará problemas con llave primaria si se hace el cambio)
        Map<Short, CarreraGrupo> carreraGrupoMap = new HashMap<>();

        // agrega al map una CarreraGrupo por Grupo (para evitar que en la fusion de la carrera se produzca problemas con llave primaria)
        for (CarreraGrupo carreraGrupo : listaCarreraGrupoActualizar) {

            // se agrega al map, en caso que no exista un id que pueda causar conflicto. Caso contrario se elimina
            if ( !carreraGrupoMap.containsKey(carreraGrupo.getIdGrupoUsmempleo()) ) {
                carreraGrupoMap.put(carreraGrupo.getIdGrupoUsmempleo(), carreraGrupo);
            }
            else {
                carreraGrupoRepository.eliminar( carreraGrupo.getCodCarrera(), carreraGrupo.getIdGrupoUsmempleo() );
            }
        }

        // iteracion en cada CarreraGrupo para cambiar la carrera
        for (CarreraGrupo carreraGrupo :carreraGrupoMap.values())
        {
            carreraGrupoRepository.actualizarCodCarrera(carreraGrupo.getCodCarrera(), carreraGrupo.getIdGrupoUsmempleo(), carrera.getCodCarrera());
        }



        // actualizacion de SimilitudProfesionalCarreras
        List<SimilitudProfesionalCarreras> similitudCarrerasActualizar = new ArrayList<SimilitudProfesionalCarreras>();

        for (String idCarrera : idsCarrerasMezclar) {
            similitudCarrerasActualizar.addAll( similitudProfesionalCarrerasRepository.buscarPorCodCarrera(Long.parseLong(idCarrera)) );
        }

        // Map que permitirá fusionar carreras asociadas a una misma SimilitudProfesionalCarreras (evitará problemas con llave primaria si se hace el cambio)
        // El map tiene como key a la institucion y la similitud de la SimilitudProfesionalCarreras, en forma de string "cod_institucion,similitud"
        Map<String, SimilitudProfesionalCarreras> similitudCarrerasMap = new HashMap<>();

        // agrega al map una SimilitudProfesionalCarreras por Institucion y Similitud (para evitar que en la fusion de la carrera se produzca problemas con llave primaria)
        for (SimilitudProfesionalCarreras similitudCarrera : similitudCarrerasActualizar) {

            // se agrega al map, en caso que no exista un id que pueda causar conflicto. Caso contrario se elimina
            if ( !similitudCarrerasMap.containsKey( similitudCarrera.getCodInstitucion().toString() + "," + similitudCarrera.getSimilitud()) ) {
                similitudCarrerasMap.put( similitudCarrera.getCodInstitucion().toString() + "," + similitudCarrera.getSimilitud(), similitudCarrera);
            }
            else {
                similitudProfesionalCarrerasRepository.eliminar( similitudCarrera.getCodCarrera(), similitudCarrera.getCodInstitucion(), similitudCarrera.getSimilitud() );
            }
        }

        // iteracion en cada SimilitudProfesionalCarreras para cambiar la carrera
        for (SimilitudProfesionalCarreras similitudCarrera : similitudCarrerasMap.values())
        {
            similitudProfesionalCarrerasRepository.actualizarCodCarrera(similitudCarrera.getCodCarrera(), similitudCarrera.getCodInstitucion(), similitudCarrera.getSimilitud(), carrera.getCodCarrera());
        }








        // eliminacion de las carreras seleccionadas en la mezcla
        for (String idCarrera : idsCarrerasMezclar) {
            Carrera carreraEliminar = carreraRepository.findByCodCarrera(Long.parseLong(idCarrera));
            carreraRepository.delete(carreraEliminar);
        }

    }

    public Carrera buscarCarreraPorCodCarreraString(String codCarreraString) {
        Long codCarrera = Long.parseLong(codCarreraString);
        return carreraRepository.findByCodCarrera(codCarrera);
    }
}
