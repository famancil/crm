package crm.controllers;

import crm.entities.Carrera;
import crm.services.CarreraService;
import crm.services.EntidadesTipoService;
import crm.validators.CarreraValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Controlador que maneja la creacion de {@link crm.entities.Carrera}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class CrearCarreraController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Carrera}.
     */
    @Autowired
    private CarreraService carreraService;

    /**
     * Servicio relacionado a la entidades Tipo (Ej: TipoVigencia, TipoGrado, etc).
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Validador de la entidad {@link crm.entities.Carrera} para el caso de modificar carrera
     */
    @Autowired
    private CarreraValidator carreraValidator;


    /**
     * InitBinder utilizado para setear el validador correspondiente a la clase {@link crm.entities.Carrera}
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(carreraValidator);
    }



    /**
     * Despliega la vista para registrar una nueva {@link crm.entities.Carrera}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de registrarCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/registrar", method = RequestMethod.GET)
    public String registrarCarrera(Model model) {

        // Carga de datos a la vista
        cargarDatosParaVistaCarrera(new Carrera(), model);

        return "/carrera/registrarCarrera";
    }



    /**
     * Recepción de los datos desde el formulario de la vista que agrega una {@link crm.entities.Carrera},
     * ingresando sus valores en la BD, retornando la vista del administrador de carreras, o la misma vista
     * en caso que existan errores
     *
     * @param carrera {@link crm.entities.Carrera} a registrar
     * @param error Errores presentes en el formulario (si es que existen).
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de registrarCarrera si existen errores. En caso contrario, redirecciona a la modificación
     *          de carrera, para desplegar los datos de la carrera recien ingresada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/registrar", method = RequestMethod.POST)
    public String registrarCarrera(@Valid @ModelAttribute(value="carrera") @Validated Carrera carrera,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // Revisa si vista posee errores
        if(error.hasErrors()){
            // Carga de datos a la vista
            cargarDatosParaVistaCarrera(carrera, model);

            return "/carrera/registrarCarrera";
        }

        // caso en que ya exista en la BD un nombre de carrera identico al que se quiere ingresar
        if ( carreraService.buscarCarrerasPorNombreEspecifico(carrera.getNombreCarrera()) != null) {

            String errorNombreCarrera = "El nombre de carrera ingresado ya se encuentra previamente registrado";
            model.addAttribute("errorNombreCarrera", errorNombreCarrera);

            // Carga de datos a la vista
            cargarDatosParaVistaCarrera(carrera, model);

            return "/carrera/registrarCarrera";
        }

        // Si no posee problemas
        carrera = carreraService.registrarCarrera(carrera);

        redirectAttributes.addFlashAttribute("flash.success", "La carrera sido registrada correctamente.");

        return "redirect:/carrera";
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
