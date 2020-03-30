package crm.controllers;

import crm.entities.Colegio;
import crm.entities.Institucion;
import crm.services.ColegioService;
import crm.services.EntidadesTipoService;
import crm.services.InstitucionService;
import crm.validators.ColegioValidator;
import crm.validators.InstitucionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * Controlador que maneja la mezcla (fusion) de {@link crm.entities.Institucion}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class MezclarInstitucionController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Institucion}.
     */
    @Autowired
    private InstitucionService institucionService;

    /**
     * Servicio relacionado a la entidades Tipo (Ej: TipoVigencia, TipoGrado, etc).
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Validador de la entidad {@link crm.entities.Institucion}
     */
    @Autowired
    private InstitucionValidator institucionValidator;






    /**
     * InitBinder utilizado para setear el validador correspondiente a la clase {@link crm.entities.Institucion}
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(institucionValidator);
    }






    /**
     * Despliega la vista para realizar la mezcla (fusión) de  {@link crm.entities.Institucion}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de mezclarInstitucion
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/institucion/buscarMezcladorInstitucion", method = RequestMethod.GET)
    public String buscarMezcladorInstitucion(Model model) {
        model.addAttribute("institucion", new Institucion());

        return "/institucion/mezclarInstitucion";
    }




    /**
     * Recepción de los datos desde el formulario de la vista que busca una {@link crm.entities.Institucion},
     * para la seleccion de las {@link crm.entities.Institucion} que serán mezcladas
     *
     * @param nomInstitucionBuscar Nombre de las {@link crm.entities.Institucion} a buscar
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de mezclarColegio con los colegios que calcen en la busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/institucion/buscarMezcladorInstitucion", method = RequestMethod.POST)
    public String buscarMezcladorColegio( @RequestParam ("nomInstitucionBuscar") String nomInstitucionBuscar,
                                          Model model) {

        List<Institucion> instituciones = institucionService.buscarInstitucionesPorCalceNombre(nomInstitucionBuscar);

        // Carga de datos a la vista
        model.addAttribute("instituciones", instituciones);
        model.addAttribute("nomInstitucionBuscar", nomInstitucionBuscar);

        cargarDatosParaVista(new Institucion(), model);

        return "/institucion/mezclarInstitucion";
    }




    /**
     * Recepción de los datos desde la vista, con los id de los {@link crm.entities.Institucion} a mezclar, y atributos que
     * se desea que tenga el {@link crm.entities.Institucion} que fusionará a todas
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/institucion/mezclar", method = RequestMethod.POST)
    public String mezclarInstitucion( @RequestParam(value = "nomInstitucionBuscar") String nomInstitucionBuscar,
                                      @RequestParam(value = "idsInstitucionesMezclar", required = false) String idsInstitucionesMezclar,
                                      @Valid @ModelAttribute(value="institucion") Institucion institucion,
                                      BindingResult error,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {


        String[] idsInstituciones = new String[0];

        // id de carreras seleccionadas en los checkbox de mezclar
        if (idsInstitucionesMezclar != null) {
            idsInstituciones = idsInstitucionesMezclar.split(",");
        }

        Institucion institucionPrevia = institucionService.buscarInstitucionPorNombreEspecifico(nomInstitucionBuscar);

        // Revisa si vista posee errores del formulario, o que no se seleccionó institucion o que ya existe una institucion con el mismo nombre
        if(error.hasErrors() || idsInstitucionesMezclar == null || institucionPrevia != null ){

            List<Institucion> instituciones = institucionService.buscarInstitucionesPorCalceNombre(nomInstitucionBuscar);

            // Carga de datos a la vista
            model.addAttribute("instituciones", instituciones);
            model.addAttribute("nomInstitucionBuscar", nomInstitucionBuscar);

            if (idsInstitucionesMezclar == null) {
                model.addAttribute("errorCheckbox", "Debe seleccionar instituciones a mezclar");
            }

            if (institucionPrevia != null) {
                model.addAttribute("errorNomInstitucion", "El nombre de la institucion ingresado ya se encuentra previamente registrado");
            }

            cargarDatosParaVista(institucion, model);

            return "/institucion/mezclarInstitucion";
        }

        // si no existieron problemas se realiza la mezcla
        institucionService.mezclarInstitucion(institucion, idsInstituciones);

        redirectAttributes.addFlashAttribute("flash.success", "La institucion sido mezclada exitosamente.");

        return "redirect:/institucion/buscarMezcladorInstitucion";
    }






    /**
     * Carga al {@link Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link crm.entities.Institucion}
     *
     * @param institucion {@link crm.entities.Institucion} a utilizar en la vista.
     * @param model {@link Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVista(Institucion institucion, Model model) {
        model.addAttribute("institucion", institucion);
        model.addAttribute("tiposVigencia", entidadesTipoService.buscarTodosTiposVigencia());
    }

}
