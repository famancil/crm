package crm.controllers;

import crm.entities.Carrera;
import crm.entities.CarreraInstitucion;
import crm.services.CarreraInstitucionService;
import crm.services.CarreraService;
import crm.services.EntidadesAntecedenteService;
import crm.services.EntidadesTipoService;
import crm.validators.CarreraValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador que maneja la modificacion y eliminación de {@link crm.entities.Carrera}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class PerfilCarreraController {

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
     * Servicio utilizado para el manejo de la asociacion {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    CarreraInstitucionService carreraInstitucionService;

    /**
     * Servicio utilizado para el manejo de la asociacion {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    EntidadesAntecedenteService entidadesAntecedenteService;

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
     * Muestra el perfil de una {@link crm.entities.Institucion}, y sus carreras asociadas
     *
     * @param model Modelo utilizado por la vista.
     * @param codCarrera id del {@link crm.entities.Carrera} del cual se desea ver el perfil.
     *
     * @return Retorna una vista con el perfil de una {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/carrera/perfil/{codCarrera}")
    public String verPerfilCarrera(Model model, @PathVariable String codCarrera){

        Carrera carrera = carreraService.buscarCarreraPorCodCarrera(Long.parseLong(codCarrera));
        List<CarreraInstitucion> carrerasInstitucion = carreraInstitucionService.buscarCarreraInstitucionPorCodCarrera(Long.parseLong(codCarrera));

        Long numeroAntecedentesAsociados = entidadesAntecedenteService.buscarCantidadAntecedenteEducacionalPorCodCarrera(Long.parseLong(codCarrera));

        model.addAttribute("carrera", carrera);
        model.addAttribute("carrerasInstitucion", carrerasInstitucion);
        model.addAttribute("numeroInstitucionesAsociadas", carrerasInstitucion.size());
        model.addAttribute("numeroAntecedentesAsociados", numeroAntecedentesAsociados);

        return "/carrera/perfilCarrera";
    }




    /**
     * Despliega la vista para modificar atributos de una {@link crm.entities.Carrera} en particular
     *
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} a modificar
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de modificarCarrera
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/modificar/{codCarrera}", method = RequestMethod.GET)
    public String modificarCarrera(@PathVariable("codCarrera") String codCarrera,
                                   Model model) {

        Carrera carrera = carreraService.buscarCarreraPorCodCarrera(Long.parseLong(codCarrera));

        // Carga de datos a la vista
        cargarDatosParaVistaCarrera(carrera, model);

        return "/carrera/modificarCarrera";
    }




    /**
     * Recepción de los datos desde el formulario de la vista que modifica {@link crm.entities.Carrera},
     * actualizando la BD los valores con los valores ingresados, retornando la misma vista tanto en caso que
     * exista error como en caso de modificación con exito
     *
     * @param carrera {@link crm.entities.Carrera} a modificar
     * @param error Errores presentes en el formulario (si es que existen).
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de modificarCarrera si existen errores. En caso contrario, se redirecciona a la modificación
     *          de carrera, para desplegar los datos de la carrera recién modificada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/modificar", method = RequestMethod.POST)
    public String modificarCarrera(@Valid @ModelAttribute(value="carrera") Carrera carrera,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // Revisa si vista posee errores
        if(error.hasErrors()){
            // Carga de datos a la vista
            cargarDatosParaVistaCarrera(carrera, model);
        }

        // caso en que ya exista en la BD un nombre de carrera identico al que
        // se quiere ingresar y distinto al que ya estaba registrado antes de modificar
        Carrera carreraYaRegistradaMismoNombre = carreraService.buscarCarrerasPorNombreEspecifico(carrera.getNombreCarrera());

        if (  carreraYaRegistradaMismoNombre != null && !carreraYaRegistradaMismoNombre.getCodCarrera().equals( carrera.getCodCarrera() )  ) {

            String errorNombreCarrera = "El nombre de carrera ingresado ya se encuentra registrado";
            model.addAttribute("errorNombreCarrera", errorNombreCarrera);

            // Carga de datos a la vista
            cargarDatosParaVistaCarrera(carrera, model);

            return "/carrera/modificarCarrera";
        }

        // Si no posee errores
        carreraService.modificarCarrera(carrera);

        redirectAttributes.addFlashAttribute("flash.success", "La carrera sido modificada correctamente.");
        return "redirect:/carrera";
    }




    /**
     * Recepción desde la vista de la eliminación en la vista de una {@link crm.entities.Carrera} y sus
     * {@link crm.entities.CarreraInstitucion}, actualizando en la BD su valor de vigencia (eliminacion logica)
     * retornando la vista del administrador de carreras
     *
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} a modificar
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Redirección a la administracion de carreras, desplegando un mensaje de eliminación exitosa
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/carrera/eliminar/{codCarrera}")
    public String eliminarCarrera(@PathVariable("codCarrera") String codCarrera,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        carreraService.eliminarLogicamenteCarrera(codCarrera);

        redirectAttributes.addFlashAttribute("flash.success", "La Carrera se ha eliminado exitosamente.");

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
