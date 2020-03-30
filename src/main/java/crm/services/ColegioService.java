package crm.services;

import crm.entities.*;
import crm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.Colegio} y con otras
 * entidades relacionadas.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Component
public class ColegioService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Colegio}
     */
    @Autowired
    private ColegioRepository colegioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    private TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteColegio}
     */
    @Autowired
    private AntecedenteColegioRepository antecedenteColegioRepository;


    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;



    /**
     * Guarda un {@link crm.entities.Colegio} nuevo en la base de datos.
     *
     * @param colegio {@link crm.entities.Colegio} a guardar en la base de datos.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void registrarColegio(Colegio colegio){

        colegio.setNombreOficial(colegio.getNombreOficial().toUpperCase());

        TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia(colegio.getVigencia().getCodVigencia());

        // seteo de los objetos tipoVigencia
        colegio.setVigencia(tipoVigencia);

        // caso que no se seleccionó chile se agrega la comuna "sin informacion"
        if (colegio.getPais().getId().compareTo(Short.parseShort("56")) != 0) {
            Comuna comuna = comunaRepository.findByCodigo(Short.parseShort("0"));
            colegio.setComuna(comuna);
        }

        // almacena quien agregó el registro y la fecha actual
        Date fechaActual = new java.util.Date();
        colegio.setRutUsuario( usuarioService.getCurrentUser().getRut());
        colegio.setFechaCreacion(fechaActual);
        colegio.setFechaModificacion(fechaActual);

        colegioRepository.save(colegio);
    }

    /**
     * Obtiene una lista de {@link crm.entities.Colegio} segun el pais.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.Colegio}.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    public List<Colegio> getColegiosPorPais(Short id){
        return colegioRepository.findColegioPorPais(id);
    }

    /**
     * Obtiene el {@link crm.entities.Colegio} segun el codigo que se pase como parametro
     *
     * @param id Id del colegio a buscar.
     *
     * @return el {@link crm.entities.Colegio} o null si es que no se encontraron resultados.
     *
     * @author renata mella <renata.mella.12@sansano.usm.cl>
     */
    public Colegio getColegioByCodigo(String id){

        Integer idColegio = Integer.parseInt(id);

        return colegioRepository.findByCodigo(idColegio);
    }

    /**
     * Obtiene un listado de manera paginada {@link crm.entities.Colegio}, segun los valores a buscar.
     *
     * @param nombreColegio Nombre del colegio que se desea buscar.
     * @param codigoRbd Codigo rbd del colegio que se desea buscar.
     * @param idPais Id del pais que se desea buscar.
     * @param idComuna Id de la comuna que se desea buscar
     * @param tipoVigencia Tipo de Vigencia de las institucion a buscar
     * @param tamanoPagina Tamano de la pagina.
     * @param numPagina Numero de la pagina actual.
     *
     * @return Lista de {@link crm.entities.Colegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public Page<Colegio> busquedaColegioPorCalceNombreYRbdYPaisYComunaYVigencia(String nombreColegio, String codigoRbd, String idPais, String idComuna, String tipoVigencia, Integer tamanoPagina, Integer numPagina){

        PageRequest page = new PageRequest(numPagina, tamanoPagina);

        if (codigoRbd.compareTo("") == 0) {
            codigoRbd = "%";
        }

        if (idPais.compareTo("0") == 0) {
            idPais = "%";
        }

        if (idComuna == null || idComuna.compareTo("0") == 0) {
            idComuna = "%";
        }

        if (tipoVigencia.compareTo("0") == 0) {
            tipoVigencia = "%";
        }

        return colegioRepository.busquedaColegioPorCalceNombreYRbdYPaisYComunaYVigencia(nombreColegio, codigoRbd, idPais, idComuna, tipoVigencia, page);


    }

    /**
     * Actualiza el {@link crm.entities.Colegio} que se pase como parametro en la base de datos.
     *
     * @param colegio Objeto {@link crm.entities.Colegio} que se desea actualizar.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void modificarColegio(Colegio colegio){

        Colegio colegioEnBD = colegioRepository.findByCodigo(colegio.getCodigo());
        Boolean modificado = false;

        if((colegioEnBD.getNombreOficial() == null) || (colegioEnBD.getNombreOficial()!= null && colegio.getNombreOficial() != null && colegio.getNombreOficial().compareTo(colegioEnBD.getNombreOficial()) != 0)){
            colegioEnBD.setNombreOficial(colegio.getNombreOficial());
            modificado = true;
        }

        if((colegioEnBD.getDireccion() == null) || (colegioEnBD.getDireccion()!= null && colegio.getDireccion() != null && colegio.getDireccion().compareTo(colegioEnBD.getDireccion()) != 0)){
            colegioEnBD.setDireccion(colegio.getDireccion());
            modificado = true;
        }

        if((colegioEnBD.getFonoPrincipal() == null) || (colegioEnBD.getFonoPrincipal()!= null && colegio.getFonoPrincipal() != null && colegio.getFonoPrincipal().compareTo(colegioEnBD.getFonoPrincipal()) != 0)){
            colegioEnBD.setFonoPrincipal(colegio.getFonoPrincipal());
            modificado = true;
        }

        if((colegioEnBD.getPais() == null) || (colegioEnBD.getPais().getId()!= null && colegio.getPais().getId() != null && colegio.getPais().getId().compareTo(colegioEnBD.getPais().getId()) != 0)){
            Pais pais = paisRepository.findById(colegio.getPais().getId());
            colegioEnBD.setPais(pais);
            modificado = true;
        }

        // caso que se seleccionó chile se agrega la comuna, sino se setea como null
        if (colegio.getPais().getId().compareTo(Short.parseShort("56")) == 0) {
            if((colegioEnBD.getComuna() == null) || (colegioEnBD.getComuna().getCodigo()!= null && colegio.getComuna() != null && colegio.getComuna().getCodigo().compareTo(colegioEnBD.getComuna().getCodigo()) != 0)){
                Comuna comuna = comunaRepository.findByCodigo(colegio.getComuna().getCodigo());
                colegioEnBD.setComuna(comuna);
                modificado = true;
            }
        }
        else {
            Comuna comuna = comunaRepository.findByCodigo(Short.parseShort("0"));
            colegioEnBD.setComuna(comuna);
            modificado = true;
        }

        if((colegioEnBD.getEmail() == null) || (colegioEnBD.getEmail()!= null && colegio.getEmail() != null && colegio.getEmail().compareTo(colegioEnBD.getEmail()) != 0)){
            colegioEnBD.setEmail(colegio.getEmail());
            modificado = true;
        }

        if((colegioEnBD.getRbd() == null) || (colegioEnBD.getRbd()!= null && colegio.getRbd() != null && colegio.getRbd().compareTo(colegioEnBD.getRbd()) != 0)){
            colegioEnBD.setRbd(colegio.getRbd());
            modificado = true;
        }

        if ((colegioEnBD.getVigencia() == null) || (colegioEnBD.getVigencia().getCodVigencia()!= null && colegio.getVigencia().getCodVigencia() != null && colegio.getVigencia().getCodVigencia().compareTo(colegioEnBD.getVigencia().getCodVigencia()) != 0)){
            TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia(colegio.getVigencia().getCodVigencia());
            colegioEnBD.setVigencia(tipoVigencia);
            modificado = true;
        }

        // si se realizó una modificación en un atributo del colegio, se registra el rut y fecha de quien modificó
        if (modificado) {
            Date fechaActual = new java.util.Date();

            colegioEnBD.setRutUsuario(usuarioService.getCurrentUser().getRut());
            colegioEnBD.setFechaModificacion(fechaActual);

            colegioRepository.save(colegioEnBD);
        }
    }




    /**
     * Obtiene una {@link crm.entities.Colegio} registrado en el sistema, según un nombre específico
     * dado como parámetro
     *
     * @param nombreColegio Nombre especifico (calce perfecto) del Colegio buscado
     *
     * @return {@link crm.entities.Colegio} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    public Colegio buscarColegioPorNombreEspecifico(String nombreColegio) {
        return colegioRepository.buscarColegioPorNombreEspecifico(nombreColegio);
    }




    /**
     * Verifica si existe el codigo rbd otorgado como parametro
     *
     * @param rbd codigo rbd que se quiere comprobar
     *
     * @return true o false dependiendo si existe o no el codigo ingresado
     *
     */
    public boolean existeCodigoRBD(Integer rbd) {
        List<Colegio> colegio = colegioRepository.buscarColegioPorCodigoRBD(rbd);
        if(colegio == null || colegio.size() == 0) return false;
        else return true;
    }




    /**
     * Obtiene una lista de {@link crm.entities.Colegio}, seleccionandola por el calce de
     * nombre
     *
     * @param nombreColegio nombre de una {@link crm.entities.Colegio} que se desea calzar con la {@link crm.entities.Colegio} buscada
     *
     * @return Lista paginada con las {@link crm.entities.Colegio} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO Agregar seguridad
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public List<Colegio> buscarColegioPorCalceNombre(String nombreColegio){
        return colegioRepository.buscarColegioPorCalceNombre(nombreColegio);
    }




    /**
     * Obtiene todas los colegios de la base de datos.
     *
     * @return {@link java.util.Iterator} de {@link crm.entities.Colegio}.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public Iterable<Colegio> getColegios(){
        return colegioRepository.findAll();
    }





    /**
     * Realiza la mezcla de los {@link crm.entities.Colegio} seleccionados. Para esto crea un nuevo {@link crm.entities.Colegio}
     * con los datos ingresados, y actualiza todos las tablas relacionadas al id de este nuevo {@link crm.entities.Colegio}.
     * Finalmente elimina los {@link crm.entities.Colegio} que se han seleccionado para mezcla
     *
     * @param colegio Datos del nuevo {@link crm.entities.Colegio} que contendrá los datos de las colegios mezcladas
     * @param idsColegiosMezclar Ids de las {@link crm.entities.Colegio} seleccionadas para mezclar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transactional
    //@PreAuthorize("hasPermission(#colegio, 'Editar') && hasPermission(colegio, 'Eliminar')")
    public void mezclarColegio (Colegio colegio, String[] idsColegiosMezclar) {

        Date fechaActual = new java.util.Date();

        // seteo de datos de usuario y fecha de la modificacion
        colegio.setRutUsuario(usuarioService.getCurrentUser().getRut());
        colegio.setFechaModificacion(fechaActual);
        colegio.setFechaCreacion(fechaActual);

        colegioRepository.save(colegio);


        //---> SIN PK (entidades asociadas que no posee como clave primaria el id del colegio. Actualizacion directa)

        // actualizacion de AntecedenteEducacional
        for (String idColegio : idsColegiosMezclar) {

            List<AntecedenteColegio> listaAntecedentesActualizar = antecedenteColegioRepository.buscarPorCodColegio(Integer.parseInt(idColegio));

            for (AntecedenteColegio antecedenteColegio : listaAntecedentesActualizar) {
                antecedenteColegio.setColegio(colegio);
                antecedenteColegio.setRutUsuario(usuarioService.getCurrentUser().getRut());
                antecedenteColegio.setFechaModificacion(fechaActual);
            }

            antecedenteColegioRepository.save(listaAntecedentesActualizar);
        }

        // eliminacion de los colegios seleccionadas en la mezcla
        for (String idColegio : idsColegiosMezclar) {
            Colegio colegioEliminar = colegioRepository.findByCodigo(Integer.parseInt(idColegio));
            colegioRepository.delete(colegioEliminar);
        }
    }


}

