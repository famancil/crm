package crm.controllers;

import crm.entities.CarreraInstitucion;
import crm.entities.Institucion;
import crm.services.*;
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
 * Controlador que maneja el perfil de una {@link crm.entities.Institucion}
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class PerfilInstitucionController {

    /**
     * Servicio utilizado para el manejo de una institucion y sus entidades asociadas
     */
    @Autowired
    InstitucionService institucionService;

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo.
     */
    @Autowired
    EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de la asociacion {@link crm.entities.CarreraInstitucion}.
     */
    @Autowired
    CarreraInstitucionService carreraInstitucionService;




    /**
     * InitBinder utilizado para setear el validador correspondiente a {@link crm.entities.Institucion}.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.setValidator(new InstitucionValidator());
    }




    /**
     * Muestra el perfil de una {@link crm.entities.Institucion}.
     *
     * @param model Modelo utilizado por la vista.
     * @param codInstitucion id del {@link crm.entities.Institucion} del cual se desea ver el perfil.
     *
     * @return Retorna una vista con el perfil de una {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion/perfil/{codInstitucion}")
    public String verPerfilInstitucion(Model model, @PathVariable String codInstitucion){

        Institucion institucion = institucionService.getInstitucionPorCodigo(codInstitucion);
        List<CarreraInstitucion> carrerasInstitucion = carreraInstitucionService.buscarCarreraInstitucionPorCodInstitucion(Short.parseShort(codInstitucion));

        model.addAttribute("institucion", institucion);
        model.addAttribute("carrerasInstitucion", carrerasInstitucion);

        return "/institucion/perfilInstitucion";
    }




    /**
     * Metodo usado para acceder a la vista modificarInstitucion desde el perfil de una institucion.
     *
     * @param codInstitucion Objeto {@link crm.entities.Institucion}
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna la vista modificarInstitucion con el form para buscar una
     * {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion/modificar/{codInstitucion}", method = RequestMethod.GET)
    public String mostraModificarInstitucion(@PathVariable("codInstitucion") String codInstitucion,
                                             Model model){

        Institucion institucion = institucionService.getInstitucionPorCodigo(codInstitucion);

        cargarDatosParaVistaInstitucion(institucion, model);

        return "/institucion/modificarInstitucion";
    }




    /**
     * Toma el formulario desde la vista modificarInstitucion, actualiza la base de datos con los valores
     * ingresados y retorna a la misma vista.
     *
     * @param institucion Objeto {@link crm.entities.Institucion}
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna la vista modificarInstitucion con los resultados de la busqueda de una
     * {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion/modificar", method = RequestMethod.POST)
    public String modificarInstitucion(@Valid @ModelAttribute Institucion institucion, BindingResult error,
                                       RedirectAttributes redirectAttributes, Model model){

        if (error.hasErrors()){
            cargarDatosParaVistaInstitucion(institucion, model);

            return "/institucion/modificarInstitucion";
        }

        // caso en que ya exista en la BD un nombre de institucion identico al que
        // se quiere ingresar y distinto al que ya estaba registrado antes de modificar
        Institucion institucionYaRegistradaMismoNombre = institucionService.buscarInstitucionPorNombreEspecifico(institucion.getNomInstitucion());

        if ( institucionYaRegistradaMismoNombre != null && !institucionYaRegistradaMismoNombre.getCodInstitucion().equals( institucionYaRegistradaMismoNombre.getCodInstitucion() )) {

            String errorNombreInstitucion = "El nombre de institucion ingresado ya se encuentra previamente registrado";
            model.addAttribute("errorNombreInstitucion", errorNombreInstitucion);

            // Carga de datos a la vista
            cargarDatosParaVistaInstitucion(institucion, model);

            return "/institucion/registrarInstitucion";
        }

        // Si no posee errores
        institucionService.modificarInstitucion(institucion);

        redirectAttributes.addFlashAttribute("flash.success", "La institucion ha sido modificada correctamente.");

        return "redirect:/institucion";
    }




    /**
     * Recepción desde la vista de la eliminación en la vista de una {@link crm.entities.Institucion} y sus
     * {@link crm.entities.CarreraInstitucion}, actualizando en la BD su valor de vigencia (eliminacion logica)
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion} a eliminar
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Redirección a la administracion de instituciones, desplegando un mensaje de eliminación exitosa
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/institucion/eliminar/{codInstitucion}")
    public String eliminarInstitucion(@PathVariable("codInstitucion") String codInstitucion,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {

        institucionService.eliminarLogicamenteInstitucion(codInstitucion);

        redirectAttributes.addFlashAttribute("flash.success", "La Institucion se ha eliminado exitosamente.");

        return "redirect:/institucion";
    }





    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link crm.entities.Carrera}
     *
     * @param institucion {@link crm.entities.Institucion} a utilizar en la vista.
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVistaInstitucion(Institucion institucion, Model model) {
        model.addAttribute("institucion", institucion);
        model.addAttribute("paises", geograficoService.getPaises());
        model.addAttribute("tiposVigencia", entidadesTipoService.buscarTodosTiposVigencia());
    }
}
