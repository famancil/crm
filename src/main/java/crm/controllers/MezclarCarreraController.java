package crm.controllers;

import crm.entities.Carrera;
import crm.entities.CarreraInstitucion;
import crm.services.CarreraInstitucionService;
import crm.services.CarreraService;
import crm.services.EntidadesAntecedenteService;
import crm.services.EntidadesTipoService;
import crm.validators.CarreraValidator;
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
 * Controlador que maneja la mezcla (fusion) de {@link Carrera}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class MezclarCarreraController {

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
     * Servicio utilizado para el manejo de la asociacion {@link CarreraInstitucion}.
     */
    @Autowired
    CarreraInstitucionService carreraInstitucionService;

    /**
     * Servicio utilizado para el manejo de la asociacion {@link CarreraInstitucion}.
     */
    @Autowired
    EntidadesAntecedenteService entidadesAntecedenteService;

    /**
     * Validador de la entidad {@link Carrera} para el caso de modificar carrera
     */
    @Autowired
    private CarreraValidator carreraValidator;


    /**
     * InitBinder utilizado para setear el validador correspondiente a la clase {@link Carrera}
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(carreraValidator);
    }









    /**
     * Despliega la vista para realizar la mezcla (fusión) de {@link crm.entities.Carrera}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de modificarCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/buscarMezcladorCarrera", method = RequestMethod.GET)
    public String buscarMezcladorCarrera(Model model) {

        return "/carrera/mezclarCarrera";
    }




    /**
     * Recepción de los datos desde el formulario de la vista que busca una {@link crm.entities.Carrera},
     * para la seleccion de las carreras que serán mezcladas
     *
     * @param nombreCarreraBuscar Nombre de las {@link crm.entities.Carrera} a buscar
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de mezclarCarrera con las carreras que calcen en la busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/buscarMezcladorCarrera", method = RequestMethod.POST)
    public String buscarMezcladorCarrera( @RequestParam ("nombreCarreraBuscar") String nombreCarreraBuscar,
                                          Model model) {


        List<Carrera> carreras = carreraService.buscarCarrerasPorCalceNombreOAbreviacionOTitulo(nombreCarreraBuscar);

        // Carga de datos a la vista
        model.addAttribute("carreras", carreras);
        model.addAttribute("nombreCarreraBuscar", nombreCarreraBuscar);

        cargarDatosParaVistaCarrera(new Carrera(), model);

        return "/carrera/mezclarCarrera";
    }




    /**
     * Recepción de los datos desde la vista, con los id de las {@link crm.entities.Carrera} a mezclar, y atributos que
     * se desea que tenga la {@link crm.entities.Carrera} que fusionará a todas
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/mezclar", method = RequestMethod.POST)
    public String mezclarCarrera( @RequestParam(value = "nombreCarreraBuscar") String nombreCarreraBuscar,
                                  @RequestParam(value = "idsCarrerasMezclar", required = false) String idsCarrerasMezclar,
                                  @Valid @ModelAttribute(value="carrera") Carrera carrera,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        String[] idsCarreras = new String[0];

        // id de carreras seleccionadas en los checkbox de mezclar
        if (idsCarrerasMezclar != null) {
            idsCarreras = idsCarrerasMezclar.split(",");
        }

        Carrera carreraPrevia = carreraService.buscarCarrerasPorNombreEspecifico(carrera.getNombreCarrera());

        // Revisa si vista posee errores del formulario, o que no se seleccionó carrera o que ya existe una carrera con el mismo nombre
        if(error.hasErrors() || idsCarrerasMezclar == null || carreraPrevia != null ){

            List<Carrera> carreras = carreraService.buscarCarrerasPorCalceNombreOAbreviacionOTitulo(nombreCarreraBuscar);

            // Carga de datos a la vista
            model.addAttribute("carreras", carreras);
            model.addAttribute("nombreCarreraBuscar", nombreCarreraBuscar);
            model.addAttribute("idsCarreras", idsCarreras);

            if (idsCarrerasMezclar == null) {
                model.addAttribute("errorCheckbox", "Debe seleccionar carreras a mezclar");
            }

            if (carreraPrevia != null) {
                model.addAttribute("errorNombreCarrera", "El nombre de carrera ingresado ya se encuentra previamente registrado");
            }

            cargarDatosParaVistaCarrera(carrera, model);

            return "/carrera/mezclarCarrera";
        }

        carreraService.mezclarCarrera(carrera, idsCarreras);

        redirectAttributes.addFlashAttribute("flash.success", "La carrera sido mezclada exitosamente.");

        return "redirect:/carrera/buscarMezcladorCarrera";
    }




    /**
     * Carga al {@link Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link Carrera}
     *
     * @param carrera {@link Carrera} a utilizar en la vista.
     * @param model {@link Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVistaCarrera(Carrera carrera, Model model) {
        model.addAttribute("carrera", carrera);
        model.addAttribute("tiposGrados", entidadesTipoService.buscarTodosTiposGrados());
        model.addAttribute("tiposVigencia", entidadesTipoService.buscarTodosTiposVigencia());
    }

}
