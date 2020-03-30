package crm.controllers;

import crm.entities.Institucion;
import crm.services.EntidadesTipoService;
import crm.services.GeograficoService;
import crm.services.InstitucionService;
import crm.services.UsuarioService;
import crm.utils.EncodingUtil;
import crm.validators.InstitucionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

/**
 * Controlador que maneja la administración de {@link crm.entities.Institucion}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class BusquedaInstitucionesController {

    /**
     * Servicio utilizado para el manejo de una institucion y sus entidades asociadas
     */
    @Autowired
    private InstitucionService institucionService;

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo.
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;

    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;





    /**
     * Muestra el form donde se busca una {@link crm.entities.Institucion}
     * segun el pais o el nombre.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Una vista con el form para buscar la {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion")
    public String busquedaInstitucion(Model model){

        model.addAttribute("paises", geograficoService.getPaises());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());

        return "/institucion/busquedaInstituciones";
    }




    /**
     * Muestra el resultado de la busqueda de una {@link crm.entities.Institucion}.
     *
     * @param model Modelo utilizado en la vista.
     * @param nombreInstitucion  nombre de la {@link crm.entities.Institucion} a buscar, si criterio es por nombre
     * @param idPais  id del {@link crm.entities.Pais} a buscar, si criterio es por pais
     * @param tipoVigencia  id del {@link crm.entities.TipoVigencia} a buscar
     * @param pagina numero de la pagina actual.
     *
     * @return Una vista con los resultados de la busqueda.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion/buscar", method= RequestMethod.GET)
    public String resultadoBusquedas(Model model,
                                     @RequestParam ("nombreInstitucion") String nombreInstitucion,
                                     @RequestParam("idPais") String idPais,
                                     @RequestParam("tipoVigencia") String tipoVigencia,
                                     @RequestParam(value = "pagina") String pagina) {

        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }
        catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }

        nombreInstitucion = encodingUtil.decodeURIComponent(nombreInstitucion);

        Page<Institucion> instituciones = institucionService.busquedaInstitucionPorCalceNombreYPaisYVigencia(nombreInstitucion, idPais, tipoVigencia, PAGE_SIZE, numeroPagina - 1);

        model.addAttribute("instituciones", instituciones);

        model.addAttribute("pagina", pagina);
        model.addAttribute("nombreInstitucion", nombreInstitucion);
        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("idPais", idPais);

        model.addAttribute("paises", geograficoService.getPaises());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());

        this.generatePagination(model, instituciones);

        return "/institucion/busquedaInstituciones";
    }




    /**
     * Metodo utilizado para realizar la paginacion de la busqueda.
     *
     * @param model Modelo utilizado en la vista.
     * @param page Objeta de la entidad {@link org.springframework.data.domain.Page}
     */
    private void generatePagination(Model model, Page page) {
        int actual = page.getNumber() + 1;
        int inicio = Math.max(1, actual - 5);
        int fin = Math.min(inicio + 10, page.getTotalPages());
        model.addAttribute("beginIndex", inicio);
        model.addAttribute("endIndex", fin);
        model.addAttribute("currentIndex", actual);
    }


}
