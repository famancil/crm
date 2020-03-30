package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.Colegio;
import crm.entities.FiltroOfertaLaboral;
import crm.entities.Pais;
import crm.repositories.ColegioRepository;
import crm.repositories.PaisRepository;
import crm.repositories.TipoEstudioRepository;
import crm.services.ColegioService;
import crm.services.FiltroOfertaLaboralService;
import crm.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que funciona como una API JSON para manejar los metodos de apoyo en las vistas referentes a {@link crm.entities.Usuario}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@RestController
public class EstructuraApoyoUsuarioController {

    /**
     * Servicio utilizado para el manejo de {@link crm.entities.FiltroOfertaLaboral}
     */
    @Autowired
    private FiltroOfertaLaboralService filtroOfertaLaboralService;





    /**
     * TODO comentar
     *
     * @param id Id de la {@link crm.entities.OfertaLaboralUsmempleo} a obtener.
     *
     * @return Retorna la vista de modificar usuario.
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/filtroOfertaLaboral/{id}", method = RequestMethod.GET)
    @JsonView(ResponseView.MainView.class)
    public FiltroOfertaLaboral cargarFiltroOfertaLaboral(@PathVariable Integer id) {
        FiltroOfertaLaboral filtroOfertaLaboral = null;

        if (id == 0){     // caso que se desea registrar un nuevo filtro
            filtroOfertaLaboral = new FiltroOfertaLaboral();
        }
        else {
            filtroOfertaLaboral = filtroOfertaLaboralService.findById(id);
        }

        return filtroOfertaLaboral;
    }
}
