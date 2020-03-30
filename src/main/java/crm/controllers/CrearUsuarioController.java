package crm.controllers;

import crm.entities.InfoProfesionalExalumno;
import crm.entities.PermisoAcceso;
import crm.entities.PreferenciaUsuarioUsmempleo;
import crm.entities.Usuario;
import crm.security.CrmPermissionEvaluator;
import crm.services.GeograficoService;
import crm.services.UsuarioService;
import crm.services.EntidadesTipoService;
import crm.validators.UsuarioValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador que maneja la creacion de un {@link crm.entities.Usuario}
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class CrearUsuarioController {

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo de las entidades correspondientes a las tablas tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(CrearUsuarioController.class);

    /**
     * Entidad que permite la validacion de un usuario
     */
    @Autowired
    private UsuarioValidator usuarioValidator;

    /**
     * InitBinder utilizado para setear el formato de fecha ingresada por formulario y para
     * setear el validador correspondiente de la clase Usuario.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
        //Setea el validador de usuario en binder desde la clase UsuarioValidator
        binder.setValidator(usuarioValidator);
    }

    /**
     * Muestra el form para registrar un nuevo {@link crm.entities.Usuario}.
     *
     * @param model Modelo utilizado por la vista.
     *
     * @return Una vista con el form para el registro de un {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/usuario/registrar", method = RequestMethod.GET)
    public String registrarUsuario(Model model) {
        Usuario user = new Usuario();

        PreferenciaUsuarioUsmempleo pref = new PreferenciaUsuarioUsmempleo();
        pref.setUsuario(user);
        user.setPreferenciaUsuarioUsmempleo(pref);

        InfoProfesionalExalumno info = new InfoProfesionalExalumno();
        info.setUsuario(user);
        user.setInformacionProfesional(info);
        user.getPreferenciaUsuarioUsmempleo().setActualizarDatos(true);

        model.addAttribute("usuario", user);
        cargarDatosParaModificarUsuario(model);
        return "/usuario/registrarUsuario";
    }
    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * @param usuario Entidad {@link crm.entities.Usuario} a registrar
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/usuario/registrar", method = RequestMethod.POST)
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model, @RequestParam("confirmPassword") String confirmPassword) {
        if(error.hasErrors()){
            model.addAttribute("usuario", usuario);
            cargarDatosParaModificarUsuario(model);
            return "/usuario/registrarUsuario";
        }
        if(confirmPassword.compareTo(usuario.getPassword())!=0){
            model.addAttribute("usuario", usuario);
            cargarDatosParaModificarUsuario(model);
            model.addAttribute("passwordMatchError", "Las contraseñas no coinciden.");
            return "/usuario/registrarUsuario";
        }

        try {
            usuarioService.guardarUsuario(usuario);
        }catch(AccessDeniedException e){
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para registrar usuarios");
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("flash.success", "El nuevo usuario se ha registrado exitosamente.");
        return "redirect:/usuario/registrar";
    }




    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de modificar usuario.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author dacuna <diego.acuna@usm.cl>
     */
    private void cargarDatosParaModificarUsuario(Model model) {
        model.addAttribute("tipos", usuarioService.getTiposUsuario());
        model.addAttribute("comoSupo", usuarioService.getComoSupo());
        model.addAttribute("estados", entidadesTipoService.listaTipoEstadoCivil());
        model.addAttribute("credenciales", entidadesTipoService.getEstadosCredenciales());
        model.addAttribute("razones", entidadesTipoService.listaTipoComoSupoDeRedExalumnos());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());

    }

}
