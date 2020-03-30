package crm.controllers;

import crm.entities.Carrera;
import crm.entities.CarreraInstitucion;
import crm.entities.Institucion;
import crm.services.CarreraInstitucionService;
import crm.services.CarreraService;
import crm.services.EntidadesTipoService;
import crm.services.InstitucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controlador que maneja la creacion de {@link crm.entities.CarreraInstitucion}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class CrearCarreraInstitucionController {

    /**
     * Servicio relacionado a la entidad {@link Carrera}.
     */
    @Autowired
    private CarreraService carreraService;

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
     * Servicio relacionado a la entidad {@link Institucion}.
     */
    @Autowired
    private InstitucionService institucionService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;




    /**
     * Despliega la vista de asignacion de una {@link Carrera} a una {@link Institucion},
     *
     * @param codCarrera Identificador de la {@link Carrera} que se desea asociar (si viene de vista carrera
     *                   tendrá el id de la carrera, si viene de vista del perfil de institucion tendrá valor "0" por defecto)
     * @param codInstitucion Identificador de la {@link Institucion} que se desea asociar (si viene de vista institucion
     *                   tendrá el id de la carrera, si viene de vista del perfil de carrera tendrá valor "0" por defecto)
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de registrarCarreraInstitucion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carreraInstitucion/registrar/{codInstitucion}/{codCarrera}", method = RequestMethod.GET)
    public String registrarCarrerasIntitucion(@PathVariable("codInstitucion") String codInstitucion,
                                              @PathVariable("codCarrera") String codCarrera,
                                              Model model,
                                              HttpServletRequest request) {

        // Paises, Instituciones, son cargadas en la misma vista con javascript
        cargarDatosParaVistaCarreraInstitucion(new CarreraInstitucion(), model);

        Institucion institucion = institucionService.getInstitucionPorCodigo( codInstitucion );

        List<CarreraInstitucion> carrerasAsociadasAInstitucion = carreraInstitucionService.buscarCarreraInstitucionPorCodInstitucion(Short.parseShort(codInstitucion));

        // Carga de id de institucion y carrera para que aparescan preseleccionados en la vista del registro
        model.addAttribute("codInstitucion", codInstitucion);
        model.addAttribute("codCarrera", codCarrera);
        model.addAttribute("codPais", institucion.getPais().getCodigo());
        model.addAttribute("carrerasInstitucion", carrerasAsociadasAInstitucion);

        // obtencion de pagina previa, para hacer la redirección
        String paginaPrevia = request.getHeader("Referer");
        model.addAttribute("paginaPrevia", paginaPrevia);

        return "/carreraInstitucion/registrarCarreraInstitucion";
    }




    /**
     * Recepción de los datos desde el formulario de la vista que agrega una {@link CarreraInstitucion},
     * ingresando sus valores en la BD, retornando la vista del administrador de carrerasInstitucion
     *
     * @param carreraInstitucion objeto {@link CarreraInstitucion} a registrar en la BD
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     * @param paginaPrevia pagina previa, permite hacer la redireccion.
     *
     * @return Redirección a la administracion de carreraInstitucion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carreraInstitucion/registrar", method = RequestMethod.POST)
    public String guardarCarrerasIntitucion(@ModelAttribute CarreraInstitucion carreraInstitucion,
                                            RedirectAttributes redirectAttributes,
                                            Model model,
                                            @RequestParam ("paginaPrevia") String paginaPrevia) {

        carreraInstitucionService.registrarCarreraInstitucion(carreraInstitucion);

        redirectAttributes.addFlashAttribute("flash.success", "La carrera ha sido asignada correctamente a la institucion.");

        if (paginaPrevia.isEmpty()) {
            return "redirect:/";
        }
        else {
            return "redirect:"+paginaPrevia;
        }
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


}
