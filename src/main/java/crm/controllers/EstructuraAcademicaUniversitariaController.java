package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.Colegio;
import crm.entities.Pais;
import crm.entities.Institucion;
import crm.entities.Carrera;
import crm.repositories.PaisRepository;
import crm.repositories.InstitucionRepository;
import crm.repositories.CarreraRepository;
import crm.services.CarreraInstitucionService;
import crm.services.CarreraService;
import crm.services.ColegioService;
import crm.services.InstitucionService;
import crm.utils.EncodingUtil;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que funciona como una API JSON para retornar un listado con las estructuras academicas universitarias con los
 * datos disponibles en la DB de la Red de Ex Alumnos.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@RestController
public class EstructuraAcademicaUniversitariaController {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Institucion}.
     */
    @Autowired
    private InstitucionRepository institucionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraService carreraService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    private CarreraInstitucionService carreraInstitucionService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Institucion}.
     */
    @Autowired
    private InstitucionService institucionService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Colegio}.
     */
    @Autowired
    private ColegioService colegioService;

    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;




    /**
     * Retorna una lista de los paises del mundo.
     *
     * @return {@link java.util.List} de {@link crm.entities.Pais}.
     */
    @RequestMapping(value = "/edu/paises")
    public List<Pais> listaPaises() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

    /**
     * Retorna una lista de las universidades de un determinado {@link crm.entities.Pais} expresada segun su clave
     * primaria.
     *
     * @return {@link java.util.List} de {@link crm.entities.Institucion}.
     */
    @RequestMapping(value = "/edu/pais/{id}/instituciones")
    @JsonView(ResponseView.MainView.class)
    public List<Institucion> listaInstituciones(@PathVariable("id") Short paisId) {
        List<Institucion> li = institucionService.getInstitucionesPorPais(paisId);
        return li;
    }


    /**
     * Obtiene una lista con los nombres de las  {@link crm.entities.Institucion} en que exista un calce en nombre
     * con el parametro pasado
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una vista de una lista de {@link crm.entities.Institucion}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/opciones/instituciones", method = RequestMethod.GET)
    public List<String> buscarNombreInstituciones(@RequestParam("term") String tagName) {

        tagName = encodingUtil.decodeURIComponent(tagName);

        List<Institucion> listaInstitucion= institucionService.buscarInstitucionesPorCalceNombre(tagName);

        List<String> instituciones = new ArrayList<String>();

        // Obtiene el nombre de la institucion, dependiendo de cual no esté nulo en la base de datos
        for (Institucion institucion: listaInstitucion){
            if (institucion.getNomInstitucion() != null) {
                instituciones.add(institucion.getNomInstitucion());
            }
        }
        return instituciones;
    }



    /**
     * Retorna una lista de las carreras de una determinada {@link crm.entities.Institucion} expresada segun su clave
     * primaria.
     *
     * @return {@link java.util.List} de {@link crm.entities.Carrera}.
     */
    @RequestMapping(value = "/edu/pais/institucion/{id}/carreras")
    @JsonView(ResponseView.MainView.class)
    public List<Carrera> listaCarrerasInstitucion(@PathVariable("id") Short institucionId) {
        List<Carrera> carreras = carreraService.buscarCarrerasPorCodInstitucion(institucionId);
        return carreras;
    }


    /**
     * Obtiene una lista con las {@link crm.entities.Carrera}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/edu/carrera", method = RequestMethod.GET)
    public List<Carrera> listaCarreras() {

        List<Carrera> listaCarreras= carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc();

        return listaCarreras;
    }


    /**
     * Obtiene una lista con los nombre {@link crm.entities.Carrera} en que exista un calce en nombre, abreviacion o titulo
     * de la Carrera con el parametro pasado
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una vista de una lista de {@link crm.entities.Empresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/opciones/carreras", method = RequestMethod.GET)
    public List<String> buscarNombreCarreras(@RequestParam("term") String tagName) {

        tagName = encodingUtil.decodeURIComponent(tagName);

        List<Carrera> listaCarreras= carreraService.buscarCarrerasPorCalceNombreOAbreviacionOTitulo(tagName);

        List<String> carreras = new ArrayList<String>();

        // Obtiene el nombre de la carrera, dependiendo de cual no esté nulo en la base de datos
        for (Carrera carrera: listaCarreras){
            if (carrera.getNombreCarrera() != null) {
                carreras.add(carrera.getNombreCarrera());
            }
        }
        return carreras;
    }



    /**
     * Obtiene una lista de {@link crm.entities.Institucion} segun el Identificador de {@link crm.entities.Pais};
     *
     * @param codigoPais ide del {@link crm.entities.Pais} en el cual buscar las {@link crm.entities.Institucion}
     * @param model
     *
     * @return Colleccion {@Link java.util.List} de {@link crm.entities.Institucion} de un {@link crm.entities.Pais}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     * TODO ver donde dejar esta funcion despues
     */
    @RequestMapping(value="/opciones/pais/{codigoPais}/instituciones", method = RequestMethod.GET)
    public @ResponseBody List<Institucion> opcionesInstituciones(@PathVariable ("codigoPais") String codigoPais,
                                                                 Model model) {

        List<Institucion> lala = institucionService.getInstitucionesPorPais(Short.parseShort(codigoPais));
        return lala;
    }


    /**
     * Obtiene una lista con los nombre {@link crm.entities.Colegio} en que exista un calce en nombre
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una vista de una lista de {@link crm.entities.Colegio}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/opciones/colegios", method = RequestMethod.GET)
    public List<String> buscarNombreColegios(@RequestParam("term") String tagName) {

        tagName = encodingUtil.decodeURIComponent(tagName);

        List<Colegio> listaColegios= colegioService.buscarColegioPorCalceNombre(tagName);

        List<String> colegios = new ArrayList<String>();

        // Obtiene el nombre del colegio, dependiendo de cual no esté nulo en la base de datos
        for (Colegio colegio: listaColegios){
            if (colegio.getNombreOficial() != null) {
                colegios.add(colegio.getNombreOficial());
            }
        }
        return colegios;
    }

}
