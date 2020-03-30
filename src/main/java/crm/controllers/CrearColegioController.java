package crm.controllers;

import crm.entities.Colegio;
import crm.entities.Pais;
import crm.services.ColegioService;
import crm.services.EntidadesTipoService;
import crm.services.GeograficoService;
import crm.services.UsuarioService;
import crm.validators.ColegioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

/**
 * Controlador que maneja la creacion de un {@link crm.entities.Colegio}
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class CrearColegioController {

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo del colegio
     */
    @Autowired
    private ColegioService colegioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * InitBinder utilizado para setear el validador correspondiente a {@link crm.entities.Colegio}.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @InitBinder
    public void initBinder (WebDataBinder binder){
        binder.setValidator(new ColegioValidator());
    }




    /**
     * Muestra el form para registrar un nuevo {@link crm.entities.Colegio}.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Una vista con el form para el registro de un {@link crm.entities.Colegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio/registrar", method = RequestMethod.GET)
    public String registrarColegio(Model model){
        cargarDatosParaVistaColegio(new Colegio(), model);

        return "/colegio/registrarColegio";
    }

    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * @param colegio {@link crm.entities.Colegio}
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param model Modelo utilizado en la vista.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.Colegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio/registrar", method = RequestMethod.POST)
    public String registrarColegio(@Valid @ModelAttribute Colegio colegio, BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model){

        if (error.hasErrors()){
            cargarDatosParaVistaColegio(colegio, model);

            return "/colegio/registrarColegio";
        }

        // caso en que ya exista en la BD un nombre de colegio identico al que se quiere ingresar
        if ( colegioService.buscarColegioPorNombreEspecifico(colegio.getNombreOficial()) != null) {

            String errorNombreColegio = "El nombre de colegio ingresado ya se encuentra previamente registrado";
            model.addAttribute("errorNombreColegio", errorNombreColegio);

            // Carga de datos a la vista
            cargarDatosParaVistaColegio(colegio, model);

            return "/colegio/registrarColegio";
        }

        if( colegioService.existeCodigoRBD(colegio.getRbd()) ){

            String errorCodigoRBD = "El código RBD ingresado ya se encuentra registrado";
            model.addAttribute("errorCodigoRBD", errorCodigoRBD);
            cargarDatosParaVistaColegio(colegio, model);
            return "colegio/registrarColegio";
        }

        // Si no posee errores
        colegioService.registrarColegio(colegio);;

        redirectAttributes.addFlashAttribute("flash.success", "El nuevo colegio se ha registrado exitosamente.");
        return "redirect:/colegio";
    }





    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link crm.entities.Carrera}
     *
     * @param colegio {@link crm.entities.Colegio} a utilizar en la vista.
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVistaColegio(Colegio colegio, Model model) {
        model.addAttribute("colegio", colegio);
        model.addAttribute("paises", geograficoService.getPaises());
        model.addAttribute("comunas", geograficoService.getComunas());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
    }
}
