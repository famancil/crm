package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.Comuna;
import crm.entities.Pais;
import crm.entities.Provincia;
import crm.entities.Region;
import crm.repositories.ComunaRepository;
import crm.repositories.PaisRepository;
import crm.repositories.ProvinciaRepository;
import crm.repositories.RegionRepository;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador que funciona como una API JSON para retornar un listado con las estructuras geograficas de Chile y los
 * paises disponibles en la DB de la Red de Ex Alumnos.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@RestController
public class EstructuraGeograficaController {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Region}.
     */
    @Autowired
    private RegionRepository regionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Provincia}.
     */
    @Autowired
    private ProvinciaRepository provinciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}.
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Retorna una lista de los paises del mundo.
     *
     * @return {@link java.util.List} de {@link crm.entities.Pais}.
     */
    @RequestMapping(value = "/geo/paises")
    public List<Pais> listaPaises() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

    /**
     * Retorna una lista de las regiones de Chile.
     *
     * @return {@link java.util.List} de {@link crm.entities.Region}.
     */
    @RequestMapping(value = "/geo/regiones")
    @JsonView(ResponseView.MainView.class)
    public List<Region> listaRegiones() {
        return regionRepository.findAll();
    }



    /**
     * Retorna una lista de las provincias de una determinada {@link crm.entities.Region} expresada segun su clavemostrarOpcionesComunas
     * primaria.
     *
     * @return {@link java.util.List} de {@link crm.entities.Provincia}.
     */
    @RequestMapping(value = "/geo/region/{id}/provincias")
    @JsonView(ResponseView.MainView.class)
    public List<Provincia> listaProvincias(@PathVariable("id") Short regionId) {
        return provinciaRepository.findByRegionId(regionId);
    }

    /**
     * Retorna una lista con todas las comunas {@link crm.entities.Comuna}
     *
     * @return {@link java.util.List} de {@link crm.entities.Comuna}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/geo/comunas")
    @JsonView(ResponseView.MainView.class)
    public List<Comuna> listaComunas() {
        return comunaRepository.findAllByOrderByNombreAsc();
    }

    /**
     * Retorna una lista de comunas {@link crm.entities.Comuna} de un determinado pais
     *
     * @param id El id del pais
     *
     * @return {@link java.util.List} de {@link crm.entities.Comuna}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @RequestMapping(value = "/geo/pais/{id}/comunas")
    @JsonView(ResponseView.MainView.class)
    public List<Comuna> listaComunasPorPais(@PathVariable("id") Short id) {
        return comunaRepository.findComunaByIdPais(id);
    }

    /**
     * Retorna una lista de provincias {@link crm.entities.Provincia} que pertenecen a un pais
     *
     * @param id La id del pais
     *
     * @return {@link java.util.List} de {@link crm.entities.Provincia}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>}
     */
    @RequestMapping(value = "/geo/pais/{id}/provincias")
    @JsonView(ResponseView.MainView.class)
    public List<Provincia> listaProvinciasPorPais(@PathVariable("id") Short id) {
        return provinciaRepository.findByIdPais(id);
    }


    /**
     * Retorna una lista con todas las comunas {@link crm.entities.Comuna} que pertenecen a una  {@link crm.entities.Provincia}
     *
     * @return {@link java.util.List} de {@link crm.entities.Comuna}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/geo/provincia/{id}/comunas")
    @JsonView(ResponseView.MainView.class)
    public List<Comuna> listaComunasPorIdProvincia(@PathVariable("id") Short idProvincia) {
        return comunaRepository.buscarPorIdProvincia(idProvincia);
    }


    /**
     * Retorna una lista de comunas {@link crm.entities.Comuna} que pertenecen a una region
     *
     * @param idRegion La id de la region
     *
     * @return {@link java.util.List} de {@link crm.entities.Comuna}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @RequestMapping(value = "/geo/region/{id}/comunas")
    @JsonView(ResponseView.MainView.class)
    public List<Comuna> listaComunasPorIdRegion(@PathVariable("id") Short idRegion) {
        return comunaRepository.buscarPorIdRegion(idRegion);
    }

}
