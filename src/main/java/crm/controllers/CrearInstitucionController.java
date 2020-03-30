package crm.controllers;

import crm.entities.Institucion;
import crm.services.EntidadesTipoService;
import crm.services.GeograficoService;
import crm.services.InstitucionService;
import crm.services.UsuarioService;
import crm.validators.InstitucionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

/**
 * Controlador que maneja la administración de {@link crm.entities.Institucion}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class CrearInstitucionController {

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
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;



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
     * Muestra el form para registrar un nuevo {@link crm.entities.Institucion}.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Una vista con el form para el registro de un {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion/registrar", method = RequestMethod.GET)
    public String registrarInstitucion(Model model){
        cargarDatosParaVistaInstitucion(new Institucion(), model);

        return "/institucion/registrarInstitucion";
    }



    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * @param institucion {@link crm.entities.Institucion}.
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado en las vistas.
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.Institucion}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/institucion/registrar", method = RequestMethod.POST)
    public String registrarInstitucion(@Valid @ModelAttribute Institucion institucion, BindingResult error,
                                       RedirectAttributes redirectAttributes,
                                       Model model){
        if (error.hasErrors()){
            cargarDatosParaVistaInstitucion(institucion, model);

            return "/institucion/registrarInstitucion";
        }

        // caso en que ya exista en la BD un nombre de institucion identico al que se quiere ingresar
        if ( institucionService.buscarInstitucionPorNombreEspecifico(institucion.getNomInstitucion()) != null) {

            String errorNombreInstitucion = "El nombre de institucion ingresado ya se encuentra previamente registrado";
            model.addAttribute("errorNombreInstitucion", errorNombreInstitucion);

            // Carga de datos a la vista
            cargarDatosParaVistaInstitucion(institucion, model);

            return "/institucion/registrarInstitucion";
        }

        institucionService.registrarInstitucion(institucion);

        redirectAttributes.addFlashAttribute("flash.success", "La nueva institucion se ha registrado exitosamente.");
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
