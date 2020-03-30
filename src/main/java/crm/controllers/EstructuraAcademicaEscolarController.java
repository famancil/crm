package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.Colegio;
import crm.entities.Pais;
import crm.entities.Institucion;
import crm.entities.Carrera;
import crm.repositories.*;
import crm.services.CarreraInstitucionService;
import crm.services.CarreraService;
import crm.services.ColegioService;
import crm.services.InstitucionService;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que funciona como una API JSON para retornar un listado con las estructuras academicas de colegios con los
 * datos disponibles en la DB de la Red de Ex Alumnos.
 *
 * @author  Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@RestController
public class EstructuraAcademicaEscolarController {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Colegio}.
     */
    @Autowired
    private ColegioRepository colegioRepository;
    /**
     * Servicio relacionado a la entidad {@link crm.entities.Colegio}.
     */
    @Autowired
    private ColegioService colegioService;
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstudio}.
     */
    @Autowired
    private TipoEstudioRepository tipoEstudioRepository;

    /**
     * Retorna una lista de los colegios de un determinado {@link crm.entities.Pais} expresada segun su clave
     * primaria.
     *
     * @return {@link java.util.List} de {@link crm.entities.Colegio}.
     */
    @RequestMapping(value = "/edu/pais/{id}/colegios")
    @JsonView(ResponseView.MainView.class)
    public List<Colegio> listaColegios(@PathVariable("id") Short paisId) {
        List<Colegio> li = colegioService.getColegiosPorPais(paisId);
        return li;
    }
}
