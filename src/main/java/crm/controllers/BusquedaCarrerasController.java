package crm.controllers;

import crm.entities.Carrera;
import crm.services.CarreraService;
import crm.services.EntidadesTipoService;
import crm.utils.EncodingUtil;
import crm.validators.CarreraValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador que maneja la busqueda de {@link crm.entities.Carrera}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class BusquedaCarrerasController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraService carreraService;

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
     * Despliega la vista para el administrador de {@link crm.entities.Carrera}
     *fa-paste
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de administracionCarreras
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera")
    public String administracionCarreras(Model model) {

        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());

        return "/carrera/busquedaCarreras";
    }


    /**
     * Busca una {@link crm.entities.Carrera} según el calce por de nombre, abreviación o el titulo,
     * utilizando para esto el nombre ingresado en la vista.
     *
     * @param nombreCarrera Nombre, abreviación o titulo de la {@link crm.entities.Carrera} a buscar
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista para del administracionCarreras
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/buscar", method = RequestMethod.GET)
    public String buscarCarrera(@RequestParam ("nombreCarrera") String nombreCarrera,
                                @RequestParam("tipoVigencia") String tipoVigencia,
                                @RequestParam("pagina") String pagina,
                                Model model) {

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

        nombreCarrera = encodingUtil.decodeURIComponent(nombreCarrera);

        Page<Carrera> carreras = carreraService.buscarCarrerasPorCalceNombreOAbreviacionOTituloYVigencia(nombreCarrera, tipoVigencia, PAGE_SIZE, numeroPagina - 1);

        model.addAttribute("carreras", carreras);
        model.addAttribute("pagina", pagina);
        model.addAttribute("nombreCarrera", nombreCarrera);
        model.addAttribute("tipoVigencia", tipoVigencia);
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());

        this.generatePagination(model, carreras);

        return "/carrera/busquedaCarreras";
    }




    /**
     * Metodo que obtiene los indices de inicio, final y actual para la paginacion de una busqueda en particular.
     *
     * @param model Modelo donde se cargaran los datos de la paginacion.
     * @param page Resultados de busqueda paginados para una entidad en particular.
     */
    private void generatePagination(Model model, Page page) {
        int actual = page.getNumber() + 1;
        int inicio = Math.max(1, actual - 5);
        int fin = Math.min(inicio + 10, page.getTotalPages());
        model.addAttribute("beginIndex", inicio);
        model.addAttribute("endIndex", fin);
        model.addAttribute("currentIndex", actual);
    }




    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link crm.entities.Carrera}
     *
     * @param carrera {@link crm.entities.Carrera} a utilizar en la vista.
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVistaCarrera(Carrera carrera, Model model) {
        model.addAttribute("carrera", carrera);
        model.addAttribute("tiposGrados", entidadesTipoService.buscarTodosTiposGrados());
        model.addAttribute("tiposVigencia", entidadesTipoService.buscarTodosTiposVigencia());
    }

}
