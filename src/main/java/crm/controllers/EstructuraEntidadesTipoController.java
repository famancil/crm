package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.*;
import crm.repositories.*;
import crm.services.EntidadesTipoService;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador que funciona como una API JSON para retornar un listado de las entidades tipo
 * (Ejemplo: {@link crm.entities.TipoEstadoCivil} , {@link crm.entities.TipoCampana}  entre otras)
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@RestController
public class EstructuraEntidadesTipoController {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoCivil}.
     */
    @Autowired
    private TipoEstadoCivilRepository tipoEstadoCivilRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCampana}.
     */
    @Autowired
    private TipoCampanaRepository tipoCampanaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ComoSupoDeRedExalumnos}.
     */
    @Autowired
    private ComoSupoDeRedExalumnoRepository comoSupoDeRedExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EstadoSolicitudCredencial}.
     */
    @Autowired
    private EstadoSolicitudCredencialRepository estadoSolicitudCredencialRepository;

    /**
     * Servicio relacionado a la entidades Tipo (Ej: TipoVigencia, TipoGrado, etc) y entidades pequeñas (ej: Idioma)
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;






    /**
     * Retorna una lista de los {@link crm.entities.TipoEstadoCivil}.
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoEstadoCivil}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/estadoCivil")
    public List<TipoEstadoCivil> listaTiposEstadosCiviles() {
        return tipoEstadoCivilRepository.findAllByOrderByNombreAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.TipoCampana}.
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoCampana}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/campanas")
    public List<TipoCampana> listaTiposCampanas() {
        return tipoCampanaRepository.findAllByOrderByNombreAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @return {@link java.util.List} de {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/comoSupoDeRedExalumnos")
    public List<ComoSupoDeRedExalumnos> listaComoSupoDeRedExalumnos() {
        return comoSupoDeRedExalumnoRepository.findAllByOrderByTituloAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @return {@link java.util.List} de {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/estadoSolicitudCredencial")
    public List<EstadoSolicitudCredencial> listaEstadoSolicitudCredencial() {
        return estadoSolicitudCredencialRepository.findAllByOrderByNombreAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.TipoUsuario}.
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoUsuario}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/usuario")
    public List<TipoUsuario> listaTipoUsuario() {
        return entidadesTipoService.listaTipoUsuarios();
    }




    /**
     * Obtiene una lista de {@link crm.entities.Idioma}  registrados en el sistema
     *
     * @param model
     *
     * @return Lista de {@link crm.entities.Idioma}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl
     */
    @RequestMapping(value="/tipo/idiomas",method = RequestMethod.GET)
    public List<Idioma> opcionesIdioma(Model model) {

        // busca carreras que no estén asociadas a una institucion, según el codInstitucion
        List<Idioma> idiomas = entidadesTipoService.buscarIdiomas();

        return idiomas;
    }



    /**
     * Retorna una lista de los {@link crm.entities.TipoCargo}.
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoCargo}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/cargo")
    public List<TipoCargo> listaTipoCargo() {
        return entidadesTipoService.listaTipoCargoOrdenPorNombreAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.TipoArea}.
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoArea}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/area")
    public List<TipoArea> listaTipoArea() {
        return entidadesTipoService.listaTipoAreaOrdenPorNombreAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.TipoEstudio}, de caracter universitario
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoEstudio}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/estudio/universitarioFiltroOfertaLaboral")
    public List<TipoEstudio> listaTipoEstudioUniversitarioFiltroOfertaLaboral() {
        return entidadesTipoService.listaTipoEstudioDeFiltroOfertaLaboralOrdenPorNombreAsc();
    }



    /**
     * Retorna una lista de los {@link crm.entities.TipoMoneda}.
     *
     * @return {@link java.util.List} de {@link crm.entities.TipoMoneda}.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/tipo/moneda")
    public List<TipoMoneda> listaTipoMoneda() {
        return entidadesTipoService.listaTipoMonedaOrdenPorNombreAsc();
    }

}
