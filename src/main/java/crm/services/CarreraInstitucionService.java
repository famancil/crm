package crm.services;


import crm.entities.*;
import crm.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.Carrera}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class CarreraInstitucionService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    private CarreraInstitucionRepository carreraInstitucionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteEducacional}.
     */
    @Autowired
    private AntecedenteEducacionalRepository antecedenteEducacionalRepository;


    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;


    /**
     * Repositorio relacionado a la entidad {@link crm.entities.Institucion}.
     */
    @Autowired
    private InstitucionRepository institucionRepository;


    /**
     * Repositorio relacionado a la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    TipoVigenciaRepository tipoVigenciaRepository;



    /**
     * Obtiene un listado de  {@link crm.entities.CarreraInstitucion}, según un id de {@link crm.entities.Institucion}
     *
     * @param codInstitucion id de la institucion de la carreraInsticion buscada
     *
     * @return {@link crm.entities.Carrera} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public List<CarreraInstitucion> buscarCarreraInstitucionPorCodInstitucion(Short codInstitucion) {
        return carreraInstitucionRepository.buscarPorCodInstitucion(codInstitucion);
    }



    /**
     * Obtiene un listado de  {@link crm.entities.CarreraInstitucion}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} de la carreraInsticion buscada
     *
     * @return {@link crm.entities.Carrera} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public List<CarreraInstitucion> buscarCarreraInstitucionPorCodCarrera(Long codCarrera) {
        return carreraInstitucionRepository.buscarPorCodCarrera(codCarrera);
    }



    /**
     * Obtiene un listado de manera paginada de   {@link crm.entities.Carrera} asociadas a una
     * {@link crm.entities.Institucion} en {@link crm.entities.CarreraInstitucion}, según un identificador de
     * {@link crm.entities.Institucion} específico
     *
     * @param codInstitucion id de la institucion de la carreraInsticion buscada
     * @param tamanoPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Coleccion {@Link java.util.Page} de {@link crm.entities.Carrera} asociadas a una {@link crm.entities.Institucion}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    //@PostFilter("hasPermission(filterObject, 'Leer')")
    public Page<CarreraInstitucion> buscarCarreraInstitucionPorCodInstitucion(String codInstitucion ,
                                                                              Integer tamanoPagina,
                                                                              Integer numeroPagina) {

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        return carreraInstitucionRepository.buscarCarreraInstitucionPorCodInstitucion(Short.parseShort(codInstitucion), pageRequest);
    }




    /**
     * Guarda una nueva {@link crm.entities.CarreraInstitucion} en la Base de Datos
     *
     * @param carreraInstitucion CarreraInstitucion a guardar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    public void registrarCarreraInstitucion(CarreraInstitucion carreraInstitucion){

        Date fechaActual = new java.util.Date();

        // setea entidades relacionadas Carrera e Institucion
        Institucion institucion = institucionRepository.findByCodInstitucion(carreraInstitucion.getCodInstitucion());
        Carrera carrera = carreraRepository.findByCodCarrera(carreraInstitucion.getCodCarrera());

        carreraInstitucion.setInstitucion(institucion);
        carreraInstitucion.setCarrera(carrera);

        // seteo valores nulos en el caso que no se haya ingresado en la vista
        if (carreraInstitucion.getLinkMalla().isEmpty()) {
            carreraInstitucion.setLinkMalla(null);
        }
        if (carreraInstitucion.getSitioOficial().isEmpty()) {
            carreraInstitucion.setSitioOficial(null);
        }

        // obtiene y setea quien agregó el registro y la fecha
        carreraInstitucion.setRutUsuario( usuarioService.getCurrentUser().getRut() );
        carreraInstitucion.setFechaCreacion(fechaActual);
        carreraInstitucion.setFechaModificacion(fechaActual);

        carreraInstitucionRepository.save(carreraInstitucion);
    }


    /**
     * Modifica la {@link crm.entities.CarreraInstitucion} en la Base de Datos, según el objeto
     * pasado como parámetro que contiene los datos modificados, solo si efectivamente se modificó algun dato.
     *
     * @param carreraInstitucionDatosModificar objeto {@link crm.entities.CarreraInstitucion} que contiene los datos a
     *                                         modificar en la base de datos
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public void modificarCarreraInstitucion(CarreraInstitucion carreraInstitucionDatosModificar) {
        Boolean modificado = false;

        // busca la carreraInstitucion a modificar
        CarreraInstitucion carreraInstitucionEnBD = carreraInstitucionRepository.findByCodCarreraAndCodInstitucion(carreraInstitucionDatosModificar.getCodCarrera(), carreraInstitucionDatosModificar.getCodInstitucion());

        // seteo valores nulos en el caso que no se haya ingresado en la vista
        if (carreraInstitucionDatosModificar.getLinkMalla().isEmpty()) {
            carreraInstitucionDatosModificar.setLinkMalla(null);
        }
        if (carreraInstitucionDatosModificar.getSitioOficial().isEmpty()) {
            carreraInstitucionDatosModificar.setSitioOficial(null);
        }

        // verifica cambios en la malla de la carreraInstitucion
        if ((carreraInstitucionEnBD.getLinkMalla() == null) || (carreraInstitucionEnBD.getLinkMalla() != null && carreraInstitucionDatosModificar.getLinkMalla() != null && carreraInstitucionEnBD.getLinkMalla().compareTo(carreraInstitucionDatosModificar.getLinkMalla()) != 0)){
            carreraInstitucionEnBD.setLinkMalla(carreraInstitucionDatosModificar.getLinkMalla());
            modificado = true;
        }

        // verifica cambios en el sitio web de la carreraInstitucion
        if ((carreraInstitucionEnBD.getSitioOficial() == null) || (carreraInstitucionEnBD.getSitioOficial() != null && carreraInstitucionDatosModificar.getSitioOficial() != null && carreraInstitucionEnBD.getSitioOficial().compareTo(carreraInstitucionDatosModificar.getSitioOficial()) != 0)){
            carreraInstitucionEnBD.setSitioOficial(carreraInstitucionDatosModificar.getSitioOficial());
            modificado = true;
        }

        // verifica cambios en la vigencia de la carreracarreraInstitucion
        if ((carreraInstitucionEnBD.getTipoVigencia() == null) || (carreraInstitucionEnBD.getTipoVigencia() != null && carreraInstitucionDatosModificar.getTipoVigencia() != null && carreraInstitucionDatosModificar.getTipoVigencia().getCodVigencia() != carreraInstitucionEnBD.getTipoVigencia().getCodVigencia())) {

            // buscar el objeto tipo vigencia
            TipoVigencia tipoVigencia = tipoVigenciaRepository.findByCodVigencia( carreraInstitucionDatosModificar.getTipoVigencia().getCodVigencia() );

            carreraInstitucionEnBD.setTipoVigencia(tipoVigencia);

            modificado = true;
        }

        // si se realizó una modificación en un atribudo de la carreraInstitucion, se registra el rut y fecha de quien modificó
        if (modificado) {
            Date fechaActual = new java.util.Date();

            // almacena quien modificó el registro
            carreraInstitucionEnBD.setRutUsuario(usuarioService.getCurrentUser().getRut());
            carreraInstitucionEnBD.setFechaModificacion(fechaActual);

            carreraInstitucionRepository.save(carreraInstitucionEnBD);
        }
    }




    /**
     * Elimina una {@link crm.entities.CarreraInstitucion} con un id de Carrera e Institucion específicos,
     * y sus relaciones a tablas asociadas
     *
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} asociada a {@link crm.entities.CarreraInstitucion} a eliminar
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} asociada a {@link crm.entities.CarreraInstitucion} a eliminar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public void eliminarCarreraInstitucion(String codInstitucion, String codCarrera) {

        CarreraInstitucion carreraInstitucion = buscarCarreraInstitucionPorCodCarreraYCodInstitucion(Long.parseLong(codCarrera), Short.parseShort(codInstitucion));

        List<AntecedenteEducacional> antecedentesEducacionalesRelacionados = antecedenteEducacionalRepository.buscarPorCodInstitucionYCodCarrera(Short.parseShort(codInstitucion), Long.parseLong(codCarrera));

        // elimina AntecedenteEducacional relacionados a la CarreraInstitucion
        antecedenteEducacionalRepository.delete(antecedentesEducacionalesRelacionados);

        // elimina la CarreraInstitucion especificada
        carreraInstitucionRepository.delete(carreraInstitucion);
    }





    /**
     * Obtiene una {@link crm.entities.CarreraInstitucion} específica, según el identificador de carrera e institucion
     * asociados a ella.
     *
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} asociada a {@link crm.entities.CarreraInstitucion} a buscar
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} asociada a {@link crm.entities.CarreraInstitucion} a buscar
     *
     * @return Retorna la {@link crm.entities.Carrera} buscada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    public CarreraInstitucion buscarCarreraInstitucionPorCodCarreraYCodInstitucion(Long codCarrera, Short codInstitucion) {
        return carreraInstitucionRepository.findByCodCarreraAndCodInstitucion(codCarrera, codInstitucion);
    }


    /**
     * Obtiene un listado de todas las {@link crm.entities.CarreraInstitucion},
     * que esten asociadas a una {@link crm.entities.Institucion} en la tabla academia.carrera_institucion
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} que no se quieren mostrar sus carreras asociadas
     *
     * @return Coleccion ({@link java.util.List}) de todas las {@link crm.entities.CarreraInstitucion} no asociadas a la
     *          {@link crm.entities.Institucion} .
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public List<CarreraInstitucion> buscarCarrerasInstitucionAsociadasAInstitucionPorCodInstitucion(String codInstitucion) {
        return carreraInstitucionRepository.buscarPorCodInstitucion(Short.parseShort(codInstitucion));
    }
}
