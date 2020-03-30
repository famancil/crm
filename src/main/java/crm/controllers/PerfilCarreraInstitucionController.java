package crm.controllers;

import crm.entities.Carrera;
import crm.entities.CarreraInstitucion;
import crm.entities.Institucion;
import crm.services.CarreraInstitucionService;
import crm.services.CarreraService;
import crm.services.EntidadesTipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controlador que contiene los metodos de visualizar el perfil, modificacion y eliminacion de carreraInstitucion
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class PerfilCarreraInstitucionController {

    /**
     * Servicio relacionado a la entidades Tipo (Ej: TipoVigencia, TipoGrado, etc).
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio relacionado a la entidad {@link CarreraInstitucion}.
     */
    @Autowired
    private CarreraInstitucionService carreraInstitucionService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;




    /**
     * Despliega la vista de administración de {@link CarreraInstitucion}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de administracionCarrerasInstitucion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     * TODO Agregar en la vista el numero de Ofertas y Antecedentes asociados a cada CarreraInstitucion (Requerimiento Futuro)
     */
    @RequestMapping(value = "/carreraInstitucion")
    public String administracionCarrerasInstitucion(Model model) {

        return "/carreraInstitucion/administracionCarrerasInstitucion";
    }



    /**
     * Busca las {@link CarreraInstitucion} asociadas a la {@link Institucion} seleccionada
     * en la vista
     *
     * @param codInstitucion Identificador de la  {@link Institucion} sobre la cual se desean buscar las
     *                    {@link Carrera} asociadas
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorno a la vista para de administracionCarrerasInstitucion con el listado de manera paginada de las
     *          {@link Carrera} de una {@link Institucion}
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     *     TODO revisar el manejo de la busqueda, ver si se muestran todas (incluyendo las eliminadas (eliminacion logica)) o no
     */
    @RequestMapping(value = "/carreraInstitucion/buscar", method = RequestMethod.GET)
    public String buscarCarrerasIntitucion(@RequestParam ("institucion") String codInstitucion,
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

        Page<CarreraInstitucion> carrerasInstitucion = carreraInstitucionService.buscarCarreraInstitucionPorCodInstitucion(codInstitucion, PAGE_SIZE, numeroPagina - 1);

        this.generatePagination(model, carrerasInstitucion);

        model.addAttribute("carrerasInstitucion", carrerasInstitucion);
        model.addAttribute("codInstitucion", codInstitucion);

        return "/carreraInstitucion/administracionCarrerasInstitucion";
    }




    /**
     * Despliega la vista para modificar atributos de una {@link CarreraInstitucion} en particular
     *
     * @param codCarrera Identificador de la {@link Carrera} asociada a la {@link CarreraInstitucion}
     * @param codInstitucion Identificador de la {@link Institucion} asociada a la {@link CarreraInstitucion}
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de modificarCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carreraInstitucion/modificar/{codInstitucion}/{codCarrera}", method = RequestMethod.GET)
    public String modificarCarreraInstitucion( @PathVariable("codCarrera") String codCarrera,
                                                @PathVariable("codInstitucion") String codInstitucion,
                                                Model model,
                                                HttpServletRequest request) {

        CarreraInstitucion carreraInstitucion = carreraInstitucionService.buscarCarreraInstitucionPorCodCarreraYCodInstitucion(Long.parseLong(codCarrera), Short.parseShort(codInstitucion));

        // Carga de datos a la vista
        cargarDatosParaVistaCarreraInstitucion(carreraInstitucion, model);
        model.addAttribute("codInstitucion", codInstitucion);
        model.addAttribute("codCarrera", codCarrera);

        // obtencion de pagina previa, para hacer la redirección
        String paginaPrevia = request.getHeader("Referer");
        model.addAttribute("paginaPrevia", paginaPrevia);

        return "/carreraInstitucion/modificarCarreraInstitucion";
    }



    /**
     * Recepción de los datos desde el formulario de la vista que modifica {@link Carrera},
     * actualizando la BD los valores con los valores ingresados, retornando la misma vista tanto en caso que
     * exista error como en caso de modificación con exito
     *
     * @param carreraInstitucion {@link CarreraInstitucion} con los datos a modificar
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de modificarCarrera si existen errores. En caso contrario, se redirecciona a la modificación
     *          de carrera, para desplegar los datos de la carrera recién modificada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carreraInstitucion/modificar", method = RequestMethod.POST)
    public String modificarCarreraInstitucion(@ModelAttribute(value="carreraInstitucion") CarreraInstitucion carreraInstitucion,
                                               RedirectAttributes redirectAttributes,
                                               Model model,
                                               @RequestParam ("paginaPrevia") String paginaPrevia) {

        // modificacion de la CarreraInstitucion
        carreraInstitucionService.modificarCarreraInstitucion(carreraInstitucion);

        // Carga de datos a la vista
        cargarDatosParaVistaCarreraInstitucion(carreraInstitucion, model);

        redirectAttributes.addFlashAttribute("flash.success", "La Carrera asociada a la Institucion ha sido modificada correctamente.");

        if (paginaPrevia.isEmpty()) {
            return "redirect:/";
        }
        else {
            return "redirect:"+paginaPrevia;
        }
    }




    /**
     * Recepción desde la vista de la eliminación en la vista de una {@link CarreraInstitucion},
     * actualizando en la BD su valor de vigencia, retornando la vista del administracionCarreras
     *
     * @param codCarrera Identificador de la {@link Carrera} asociada a {@link CarreraInstitucion} a eliminar
     * @param codInstitucion Identificador de la {@link Institucion} asociada a {@link CarreraInstitucion} a eliminar
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Redirección a la administracion de carrerasInstitucion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carreraInstitucion/eliminar/{codInstitucion}/{codCarrera}")
    public String eliminarCarreraInstitucion(@PathVariable("codCarrera") String codCarrera,
                                             @PathVariable("codInstitucion") String codInstitucion,
                                             RedirectAttributes redirectAttributes,
                                             Model model,
                                             HttpServletRequest request) {

        carreraInstitucionService.eliminarCarreraInstitucion(codInstitucion, codCarrera);

        redirectAttributes.addFlashAttribute("flash.success", "La carrera ha sido desasignada correctamente de la institucion.");

        // obtencion de pagina previa, para hacer la redirección
        String paginaPrevia = request.getHeader("Referer");

        return "redirect:"+paginaPrevia;
    }




    /**
     * Carga al {@link Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link CarreraInstitucion}
     *
     * @param carreraInstitucion {@link CarreraInstitucion} a utilizar en la vista.
     * @param model {@link Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVistaCarreraInstitucion(CarreraInstitucion carreraInstitucion, Model model) {
        model.addAttribute("carreraInstitucion", carreraInstitucion);
        model.addAttribute("tiposVigencia", entidadesTipoService.buscarTodosTiposVigencia());
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

}
