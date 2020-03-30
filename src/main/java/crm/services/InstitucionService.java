package crm.services;

import crm.entities.*;
import crm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo
 * {@link crm.entities.Institucion} y las entidades relacionadas con esta.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class InstitucionService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Institucion}
     */
    @Autowired
    InstitucionRepository institucionRepository;


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}
     */
    @Autowired
    PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CarreraInstitucion}
     */
    @Autowired
    CarreraInstitucionRepository carreraInstitucionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.GrupoEmpleo}
     */
    @Autowired
    GrupoEmpleoRepository grupoEmpleoRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.SimilitudProfesionalCarreras}.
     */
    @Autowired
    private SimilitudProfesionalCarrerasRepository similitudProfesionalCarrerasRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.AntecedenteEducacional}.
     */
    @Autowired
    private AntecedenteEducacionalRepository antecedenteEducacionalRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.PreferenciaPrivacidad}.
     */
    @Autowired
    private PreferenciaPrivacidadRepository preferenciaPrivacidadRepository;


    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;


    /**
     * Obtiene una lista de las {@link crm.entities.Institucion} segun el pais.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<Institucion> getInstitucionesPorPais(Short id){
        return institucionRepository.findInstitucionPorPais(id);
    }


    /**
     * Obtiene una instancia de {@link crm.entities.Institucion} segun un id dado.
     *
     * @param id id de la {@link crm.entities.Institucion} a buscar.
     *
     * @return Objeto de la clase {@link crm.entities.Institucion}
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Institucion getInstitucionPorCodigo(String id){
        Short idInstitucion = Short.parseShort(id);
        return institucionRepository.findByCodInstitucion(idInstitucion);
    }




    /**
     * Guarda la {@link crm.entities.Institucion} que se pase como parametro en la base de datos.
     *
     * @param institucionNueva Objeto {@link crm.entities.Institucion} que se desea guardar.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Institucion registrarInstitucion(Institucion institucionNueva){

        institucionNueva.setNomInstitucion(institucionNueva.getNomInstitucion().toUpperCase());

        TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia(institucionNueva.getVigencia().getCodVigencia());

        // seteo de los objetos tipoVigencia
        institucionNueva.setVigencia(tipoVigencia);

        // almacena quien agregó el registro y la fecha actual
        Date fechaActual = new java.util.Date();
        institucionNueva.setRutUsuario( usuarioService.getCurrentUser().getRut());
        institucionNueva.setFechaCreacion(fechaActual);
        institucionNueva.setFechaModificacion(fechaActual);

        Institucion institucion = institucionRepository.save(institucionNueva);

        return institucion;
    }




    /**
     * Actualiza la {@link crm.entities.Institucion} que se pase como parametro en la base de datos.
     *
     * @param institucion Objeto {@link crm.entities.Institucion} que se desea actualizar.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void modificarInstitucion(Institucion institucion){

        Institucion institucionEnBD = institucionRepository.findByCodInstitucion(institucion.getCodInstitucion());
        Boolean modificado = false;

        if((institucionEnBD.getNomInstitucion() == null) || (institucionEnBD.getNomInstitucion()!= null && institucion.getNomInstitucion() != null && institucion.getNomInstitucion().compareTo(institucionEnBD.getNomInstitucion()) != 0)){
            institucionEnBD.setNomInstitucion(institucion.getNomInstitucion());
            modificado = true;
        }

        if((institucionEnBD.getDireccion() == null) || (institucionEnBD.getDireccion()!= null && institucion.getDireccion() != null && institucion.getDireccion().compareTo(institucionEnBD.getDireccion()) != 0)){
            institucionEnBD.setDireccion(institucion.getDireccion());
            modificado = true;
        }

        if((institucionEnBD.getTelefono() == null) || (institucionEnBD.getTelefono()!= null && institucion.getTelefono() != null && institucion.getTelefono().compareTo(institucionEnBD.getTelefono()) != 0)){
            institucionEnBD.setTelefono(institucion.getTelefono());
            modificado = true;
        }

        if((institucionEnBD.getPais().getId() == null) || (institucionEnBD.getPais().getId()!= null && institucion.getPais().getId() != null && institucion.getPais().getId().compareTo(institucionEnBD.getPais().getId()) != 0)){
            institucionEnBD.setPais(paisRepository.findById(institucion.getPais().getId()));
            modificado = true;
        }

        if((institucionEnBD.getVigencia().getCodVigencia() == null) || (institucionEnBD.getVigencia().getCodVigencia()!= null && institucion.getVigencia().getCodVigencia() != null && institucion.getVigencia().getCodVigencia().compareTo(institucionEnBD.getVigencia().getCodVigencia()) != 0)){
            TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia(institucion.getVigencia().getCodVigencia());

            institucionEnBD.setVigencia(tipoVigencia);

            Date fechaActual = new java.util.Date();

            // cambia de estado de vigencia a las carrerasInstitucion asociadas a la Institucion
            for(CarreraInstitucion carreraInstitucion : carreraInstitucionRepository.buscarPorCodInstitucion(institucionEnBD.getCodInstitucion())){

                carreraInstitucion.setTipoVigencia( tipoVigencia );

                // almacena quien modificó el registro
                carreraInstitucion.setRutUsuario( usuarioService.getCurrentUser().getRut());
                carreraInstitucion.setFechaModificacion(fechaActual);

                carreraInstitucionRepository.save(carreraInstitucion);
            }

            modificado = true;
        }

        // si se realizó una modificación en un atributo de la institucion, se registra el rut y fecha de quien modificó
        if (modificado) {
            Date fechaActual = new java.util.Date();

            institucionEnBD.setRutUsuario(usuarioService.getCurrentUser().getRut());
            institucionEnBD.setFechaModificacion(fechaActual);

            institucionRepository.save(institucionEnBD);
        }
    }



    /**
     * Elimina la {@link crm.entities.Institucion} que se pase como parametro en la base de datos.
     *
     * @param institucion Objeto {@link crm.entities.Institucion} que se desea eliminar.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void borrarInstitucion(Institucion institucion){
        institucionRepository.delete(institucion);
    }




    /**
     * Busca las instituciones segun un nombre, pais y vigencia
     *
     * @param nombreInstitucion Nombre de la institucion a buscar
     * @param tipoVigencia Tipo de Vigencia de las institucion a buscar
     * @param tamanoPagina Tamano de la pagina.
     * @param numPagina Numero de la pagina actual.
     *
     * @return Retorna una lista paginada de {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Page<Institucion> busquedaInstitucionPorCalceNombreYPaisYVigencia(String nombreInstitucion, String idPais, String tipoVigencia, Integer tamanoPagina, Integer numPagina){

        PageRequest page = new PageRequest(numPagina, tamanoPagina);

        if (tipoVigencia.compareTo("0") == 0) {
            tipoVigencia = "%";
        }

        if (idPais.compareTo("0") == 0) {
            idPais = "%";
        }

        return institucionRepository.busquedaInstitucionPorCalceNombreYPaisYVigencia(nombreInstitucion, idPais, tipoVigencia, page);
    }




    /**
     * Obtiene todas las instituciones de la base de datos.
     *
     * @return {@link java.util.Iterator} de {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<Institucion> getInstituciones(){
        return institucionRepository.findAll();
    }



    /**
     * Obtiene una lista de {@link crm.entities.Institucion}, seleccionandola por el calce de
     * nombre
     *
     * @param nombreInstitucion nombre de una Institucion que se desea calzar con la Institucion buscada
     *
     * @return Lista paginada con las {@link crm.entities.Institucion} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Listar')")
    public List<Institucion> buscarInstitucionesPorCalceNombre(String nombreInstitucion) {
        return institucionRepository.buscarInstitucionPorCalceNombre(nombreInstitucion);
    }



    /**
     * Obtiene una {@link crm.entities.Institucion} registrada en la tabla de carreras, según un nombre específico
     * dado como parámetro
     *
     * @param nombreInstitucion Nombre especifico (calce perfecto) de la Institucion buscada
     *
     * @return {@link crm.entities.Institucion} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostAuthorize("hasPermission(returnObject, 'Ver')")
    public Institucion buscarInstitucionPorNombreEspecifico(String nombreInstitucion) {
        return institucionRepository.buscarInstitucionPorNombreEspecifico(nombreInstitucion);
    }



    /**
     * Elimina logicamente (cambio en el tipo de vigencia) una {@link crm.entities.Institucion},
     * según el identificador asociada a ella. Además elimina logícamente las {@link crm.entities.CarreraInstitucion}
     * asociadas a la institucion
     *
     * @param codInstitucion identificador de  de la {@link crm.entities.Institucion} a eliminar logicamente
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     *     TODO Agregar seguridad
     */
    //@PostAuthorize("hasPermission(returnObject, 'Eliminar')")
    public void eliminarLogicamenteInstitucion(String codInstitucion) {

        Date fechaActual = new java.util.Date();

        Institucion institucion = this.getInstitucionPorCodigo(codInstitucion);

        // almacena quien modificó el registro
        institucion.setRutUsuario( usuarioService.getCurrentUser().getRut());
        institucion.setFechaModificacion(fechaActual);

        // eliminacion lógica de la Institucion
        institucion.setVigencia(tipoVigenciaRepository.findByCodVigencia(TipoVigencia.ID_ELIMINACION_LOGICA));

        institucionRepository.save(institucion);

        // eliminacion lógica de las carrerasInstitucion asociadas a la Institucion
        for(CarreraInstitucion carreraInstitucion : carreraInstitucionRepository.buscarPorCodInstitucion(Short.parseShort(codInstitucion))){
            carreraInstitucion.setTipoVigencia( tipoVigenciaRepository.findByCodVigencia( TipoVigencia.ID_ELIMINACION_LOGICA ) );

            // almacena quien modificó el registro
            carreraInstitucion.setRutUsuario( usuarioService.getCurrentUser().getRut());
            carreraInstitucion.setFechaModificacion(fechaActual);

            carreraInstitucionRepository.save(carreraInstitucion);
        }
    }

    /**
     * Obtiene una {@link crm.entities.Institucion} según el codigo
     * dado como parámetro
     *
     * @param codInstitucion codigo de la Institucion buscada
     *
     * @return {@link crm.entities.Institucion} buscada
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public Institucion buscarInstitucionPorCodInstitucion(Short codInstitucion) {
        return institucionRepository.findByCodInstitucion(codInstitucion);
    }





    /**
     * Realiza la mezcla de los {@link crm.entities.Institucion} seleccionados. Para esto crea una nueva {@link crm.entities.Institucion}
     * con los datos ingresados, y actualiza todos las tablas relacionadas al id de este nuevo {@link crm.entities.Institucion}.
     * Finalmente elimina las {@link crm.entities.Institucion} que se han seleccionado para mezcla
     *
     * @param institucion Datos del nuevo {@link crm.entities.Institucion} que contendrá los datos de las instituciones mezcladas
     * @param idsInstitucionesMezclar Ids de las {@link crm.entities.Institucion} seleccionadas para mezclar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transactional
    //@PreAuthorize("hasPermission(#institucion, 'Editar') && hasPermission(institucion, 'Eliminar')")
    public void mezclarInstitucion (Institucion institucion, String[] idsInstitucionesMezclar) {

        Date fechaActual = new java.util.Date();

        // seteo de datos de usuario y fecha de la modificacion
        institucion.setRutUsuario(usuarioService.getCurrentUser().getRut());
        institucion.setFechaModificacion(fechaActual);
        institucion.setFechaCreacion(fechaActual);

        institucionRepository.save(institucion);





        //---> SIN PK (entidades asociadas que no posee como clave primaria el id de la Institucion. Actualizacion directa)


        // actualizacion de GrupoEmpleo
        for (String idInstitution : idsInstitucionesMezclar) {

            List<GrupoEmpleo> listaGrupoEmpleoActualizar = grupoEmpleoRepository.buscarPorCodInstitucion(Short.parseShort(idInstitution));

            for (GrupoEmpleo grupoEmpleo : listaGrupoEmpleoActualizar) {
                grupoEmpleo.setInstitucion(institucion);
                grupoEmpleo.setRutUsuario(usuarioService.getCurrentUser().getRut());
                grupoEmpleo.setFechaModificacion(fechaActual);
            }

            grupoEmpleoRepository.save(listaGrupoEmpleoActualizar);
        }



        // actualizacion de AntecedenteEducacional
        for (String idInstitucion : idsInstitucionesMezclar) {

            List<AntecedenteEducacional> listaAntecedentesActualizar = antecedenteEducacionalRepository.buscarPorCodInstitucion(Short.parseShort(idInstitucion));

            for (AntecedenteEducacional antecedenteEducacional : listaAntecedentesActualizar) {
                antecedenteEducacional.setInstitucion(institucion);
                antecedenteEducacional.setRutUsuario(usuarioService.getCurrentUser().getRut());
                antecedenteEducacional.setFechaModificacion(fechaActual);
            }

            antecedenteEducacionalRepository.save(listaAntecedentesActualizar);
        }











        //---> CON PK (entidades asociadas que posee como clave primaria el id de la Institucion. Actualizacion segun el caso, debido a
        // la posibilidad de conflicto con la clave primaria si se hace el cambio)


        // actualizacion de CarreraInstitucion
        List<CarreraInstitucion> listaCarrerasInstitucionActualizar = new ArrayList<CarreraInstitucion>();

        for (String idInstitucion : idsInstitucionesMezclar) {
            listaCarrerasInstitucionActualizar.addAll(carreraInstitucionRepository.buscarPorCodInstitucion(Short.parseShort(idInstitucion)));
        }

        // Map que permitirá fusionar carreras asociadas a una misma Institucion (evitará problemas con llave primaria si se hace el cambio)
        Map<Long, CarreraInstitucion> carreraInstitucionMap = new HashMap<>();

        // agrega al map una CarreraInstitucion por Carrera (para evitar que en la fusion de la Institucion se produzca problemas con llave primaria)
        for (CarreraInstitucion carreraInstitucion : listaCarrerasInstitucionActualizar) {

            // se agrega al map sino hay conflicto con una CarreraInstitucion que ya posea la misma Carrera. En caso que no se agregó (debido a que existe otro), entonces se elimina
            if ( !carreraInstitucionMap.containsKey(carreraInstitucion.getCodCarrera()) ) {
                carreraInstitucionMap.put(carreraInstitucion.getCodCarrera(), carreraInstitucion);
            }
            else {
                carreraInstitucionRepository.eliminar(carreraInstitucion.getCodCarrera(), carreraInstitucion.getCodInstitucion());
            }
        }

        // iteracion en cada carreraInstitucion para cambiar la Institucion
        for (CarreraInstitucion carreraInstitucion: carreraInstitucionMap.values()) {
            carreraInstitucionRepository.actualizarCodInstitucion(carreraInstitucion.getCodCarrera(), carreraInstitucion.getCodInstitucion(), institucion.getCodInstitucion());
        }




        // actualizacion de SimilitudProfesionalCarreras
        List<SimilitudProfesionalCarreras> similitudCarrerasActualizar = new ArrayList<SimilitudProfesionalCarreras>();

        for (String idInstitucion : idsInstitucionesMezclar) {
            similitudCarrerasActualizar.addAll(similitudProfesionalCarrerasRepository.buscarPorCodInstitucion(Short.parseShort(idInstitucion)));
        }

        // Map que permitirá fusionar SimilitudProfesionalCarreras asociadas a una misma Institucion (evitará problemas con llave primaria si se hace el cambio)
        // El map tiene como key a la Carrera y la similitud de la SimilitudProfesionalCarreras, en forma de string "cod_carrera,similitud"
        Map<String, SimilitudProfesionalCarreras> similitudCarrerasMap = new HashMap<>();

        // agrega al map una SimilitudProfesionalCarreras por Carrera y Similitud (para evitar que en la fusion de la Institucion se produzca problemas con llave primaria)
        for (SimilitudProfesionalCarreras similitudCarrera : similitudCarrerasActualizar) {

            // se agrega al map, en caso que no exista un id que pueda causar conflicto. Caso contrario se elimina
            if ( !similitudCarrerasMap.containsKey( similitudCarrera.getCodCarrera().toString() + "," + similitudCarrera.getSimilitud()) ) {
                similitudCarrerasMap.put( similitudCarrera.getCodCarrera().toString() + "," + similitudCarrera.getSimilitud(), similitudCarrera);
            }
            else {
                similitudProfesionalCarrerasRepository.eliminar( similitudCarrera.getCodCarrera(), similitudCarrera.getCodInstitucion(), similitudCarrera.getSimilitud() );
            }
        }

        // iteracion en cada SimilitudProfesionalCarreras para cambiar la Institucion
        for (SimilitudProfesionalCarreras similitudCarrera : similitudCarrerasMap.values())
        {
            similitudProfesionalCarrerasRepository.actualizarCodInstitucion(similitudCarrera.getCodCarrera(), similitudCarrera.getCodInstitucion(), similitudCarrera.getSimilitud(), institucion.getCodInstitucion());
        }




        // actualizacion de PreferenciaPrivacidad
        List<PreferenciaPrivacidad> preferenciasPrivacidadActualizar = new ArrayList<PreferenciaPrivacidad>();

        for (String idInstitucion : idsInstitucionesMezclar) {
            preferenciasPrivacidadActualizar.addAll(preferenciaPrivacidadRepository.buscarPorCodInstitucion(Short.parseShort(idInstitucion)));
        }

        // Map que permitirá fusionar Instituciones asociadas a una misma SimilitudProfesionalCarreras (evitará problemas con llave primaria si se hace el cambio)
        // El map tiene como key a el id de Usuario en forma de string "id_usuario"
        Map<String, PreferenciaPrivacidad> preferenciasPrivacidadMap = new HashMap<>();

        // agrega al map una SimilitudProfesionalCarreras por Carrera y Similitud (para evitar que en la fusion de la Institucion se produzca problemas con llave primaria)
        for (PreferenciaPrivacidad preferenciaPrivacidad : preferenciasPrivacidadActualizar) {

            // se agrega al map, en caso que no exista un id que pueda causar conflicto. Caso contrario se elimina
            if ( !preferenciasPrivacidadMap.containsKey( preferenciaPrivacidad.getUsuarioId() )) {
                preferenciasPrivacidadMap.put( preferenciaPrivacidad.getUsuarioId().toString(), preferenciaPrivacidad);
            }
            else {
                preferenciaPrivacidadRepository.eliminar(preferenciaPrivacidad.getCodInstitucion(), preferenciaPrivacidad.getUsuarioId());
            }
        }

        // iteracion en cada PreferenciaPrivacidad para cambiar la Institucion
        for (PreferenciaPrivacidad preferenciaPrivacidad : preferenciasPrivacidadMap.values())
        {
            preferenciaPrivacidadRepository.actualizarCodInstitucion(preferenciaPrivacidad.getCodInstitucion(), preferenciaPrivacidad.getUsuarioId(), institucion.getCodInstitucion());
        }






        // eliminacion de los Instituciones seleccionadas en la mezcla
        for (String idInstitucion : idsInstitucionesMezclar) {
            Institucion institucionEliminar = institucionRepository.findByCodInstitucion(Short.parseShort(idInstitucion));
            institucionRepository.delete(institucionEliminar);
        }
    }
}
