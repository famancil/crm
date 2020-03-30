package crm.controllers;

import crm.entities.*;
import crm.security.CrmPermissionEvaluator;
import crm.services.EntidadesTipoService;
import crm.services.FiltroOfertaLaboralService;
import crm.services.GeograficoService;
import crm.services.UsuarioService;
import crm.validators.UsuarioValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja el perfil de un {@link crm.entities.Usuario}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class PerfilUsuarioController {

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de entidades Tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de entidades Geograficas (Pais, Comuna, etc)
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo del FiltroOfertaLaboral
     */
    @Autowired
    private FiltroOfertaLaboralService filtroOfertaLaboralService;

    /**
     * Clase encargada de la logica de permisos y seguridad.
     */
    @Autowired
    private CrmPermissionEvaluator crmPermissionEvaluator;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(PerfilUsuarioController.class);

    /**
     * InitBinder utilizado para setear el formato de fecha ingresada por formulario y para
     * setear el validador correspondiente de la clase Usuario
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        //Setea el validador de usuario en binder desde la clase UsuarioValidator
        binder.setValidator(new UsuarioValidator());
    }

    /**
     * Muestra el perfil de un {@link crm.entities.Usuario}
     *
     * @param model Modelo utilizado por la vista.
     * @param id id del {@link crm.entities.Usuario} del cual se desea ver el perfil
     *
     * @return Retorna una vista
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value ="/usuario/perfil/{id}")
    public String verPerfilUsuario(Model model, @PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Usuario perfil = usuarioService.getUsuarioById(id);
        if(perfil==null){
            redirectAttributes.addFlashAttribute("flash.error", "El usuario ingresado no existe, favor verificar");
            return "redirect:/busquedas/usuarios";
        }
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal,perfil,PermisoAcceso.LEER);
        if(autorizado) {
            model.addAttribute("perfil", perfil);
            model.addAttribute("antecedentesUsm", usuarioService.getAntecedentesEducacionalesUsm(id));
            model.addAttribute("antecedentes", usuarioService.getAntecedentesEducacionales(id));
            model.addAttribute("actividades", usuarioService.getActividadExalumno(id));
            model.addAttribute("dias", usuarioService.getDiasRestantes(perfil.getFechaNacimiento()));
            model.addAttribute("colegios", usuarioService.getAntecedentesColegio(id));
            model.addAttribute("edad", perfil.getEdad());
            model.addAttribute("nombre", perfil.getNombreCompleto());
            return "/usuario/perfilUsuario";
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "Usted no está autorizado para ver el perfil del usuario ingresado");
            return "redirect:/busquedas/usuarios";
        }
    }




    /**
     * Accion que despliega la interfaz que posibilita la edicion del perfil (datos basicos y personales) de un
     * {@link crm.entities.Usuario} en particular.
     *
     * @param model Modelo utilizado en la vista.
     * @param id Id del {@link crm.entities.Usuario} a modificar.
     *
     * @return Retorna la vista de modificar usuario.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/perfil/{id}/modificar", method = RequestMethod.GET)
    public String mostrarModificarFichaUsuario(Model model, @PathVariable Long id, Principal principal,RedirectAttributes redirectAttributes) {
        Usuario usuario =  usuarioService.getUsuarioById(id);
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal,usuario,PermisoAcceso.EDITAR);
        if(!autorizado){
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para editar información del usuario ingresado");
            redirectAttributes.addAttribute("id", id);
            return "redirect:/usuario/perfil/{id}";
        }
        model.addAttribute("usuario", usuario);
        cargarDatosParaModificarUsuario(model);
        return "/usuario/modificarUsuario";
    }




    /**
     * Toma el formulario desde la vista modificarUsuario, actualiza la base de datos con los valores
     * ingresados y retorna a la misma vista.
     *
     * @param usuario {@link crm.entities.Usuario} a modificar.
     * @param confirmPassword Atributo que permite confirmar la modificacion de la password del {@link crm.entities.Usuario}.
     * @param error Errores presentes en el formulario (si es que existen).
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista modificarUsuario
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/perfil/modificar", method = RequestMethod.POST)
    public String modificarFichaUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, Principal principal, BindingResult error,
                                        RedirectAttributes redirectAttributes, Model model, @RequestParam String confirmPassword) {
        Usuario usuarioAntiguo = usuarioService.getUsuarioById(usuario.getId());
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal, usuarioAntiguo, PermisoAcceso.EDITAR);
        if(!autorizado){
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para editar información del usuario ingresado");
            redirectAttributes.addAttribute("id", usuarioAntiguo.getId());
            return "redirect:/";
        }
        if(error.hasErrors()) {
            model.addAttribute("usuario", usuario);
            cargarDatosParaModificarUsuario(model);
            return "/usuario/modificarUsuario";
        }
        // si contraseña ha sido modificada y si contraseña son distintas, arroja error por contraseña
        if(usuarioAntiguo.getCredencialesAcceso() != null && usuarioAntiguo.getPassword().compareTo(usuario.getPassword())!=0  && confirmPassword.compareTo(usuario.getPassword())!=0){
            model.addAttribute("usuario", usuario);
            cargarDatosParaModificarUsuario(model);
            model.addAttribute("passwordMatchError", "Las contraseñas no coinciden.");
            return "/usuario/modificarUsuario";
        }
        System.out.println(usuario.getEstado());
        usuario.setEstado(usuario.getEstado());
        usuarioService.saveUsuario(usuario);
        redirectAttributes.addFlashAttribute("flash.success", "El perfil del usuario ha sido actualizado correctamente.");
        return "redirect:/usuario/perfil/"+usuario.getId();
    }




    /**
     * Elimina fisicamente una {@link crm.entities.Usuario}
     *
     * @param id identificador del {@link crm.entities.Usuario} a eliminar
     * @return La vista de perfil del usuario.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/perfil/{id}/eliminar", method = RequestMethod.POST)
    public String eliminarUsuario(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal, usuarioService.getUsuarioById(id),PermisoAcceso.ELIMINAR);
        if(autorizado) {
            usuarioService.eliminarUsuarioById(id);
            redirectAttributes.addFlashAttribute("flash.success", "El usuario ha sido eliminado exitosamente.");
            return "redirect:/busquedas/usuarios";
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para eliminar este usuario");
            redirectAttributes.addAttribute("id", id);
            return "redirect:/";
        }
    }

    /**
     * Recibe los datos del formulario de edición de los FiltroOfertaLaboral, almacenando los valores ingresados.
     *
     * @param idUsuario Id del usuario al que pertenece el filtro
     * @param idFiltroOfertaLaboral Id del FiltroOfertaLaboral a modificar
     * @param nombreFiltro Nombre del FiltroOfertaLaboral que se desea guardar
     * @param region Id del Region del FiltroOfertaLaboral que se desea guardar
     * @param tipoCargo Id del TipoCargo del FiltroOfertaLaboral que se desea guardar
     * @param carrera Id del Carrera del FiltroOfertaLaboral que se desea guardar
     * @param tipoArea Id del TipoArea del FiltroOfertaLaboral que se desea guardar
     * @param tipoEstudio Id del TipoEstudio del FiltroOfertaLaboral que se desea guardar
     * @param rangoAniosExperiencia Rango de Experiencia del FiltroOfertaLaboral que se desea guardar
     * @param aniosExperiencia Años de experiencia del FiltroOfertaLaboral que se desea guardar
     * @param tipoMoneda Id del TipoMoneda del FiltroOfertaLaboral que se desea guardar
     * @param salarioLimiteInferior Limite inferior del salario del FiltroOfertaLaboral que se desea guardar
     * @param salarioLimiteSuperior Limite superior del salario del FiltroOfertaLaboral que se desea guardar
     * @param diaEnvio Dia de la semana en que se desea enviar
     * @param momentoEnvio Momento del dia en que se desea enviar
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/{idUsuario}/filtroOfertaLaboral", method = RequestMethod.POST)
    public String editarFiltroLaboralUsuario(@PathVariable String idUsuario,
                                             @RequestParam("idFiltroOfertaLaboral") String idFiltroOfertaLaboral,
                                             @RequestParam("nombreFiltro") String nombreFiltro,
                                             @RequestParam("region") String region,
                                             @RequestParam("tipoCargo") String tipoCargo,
                                             @RequestParam("carrera") String carrera,
                                             @RequestParam("tipoArea") String tipoArea,
                                             @RequestParam("tipoEstudio") String tipoEstudio,
                                             @RequestParam("rangoAniosExperiencia") String rangoAniosExperiencia,
                                             @RequestParam("aniosExperiencia") String aniosExperiencia,
                                             @RequestParam("tipoMoneda") String tipoMoneda,
                                             @RequestParam("salarioLimiteInferior") String salarioLimiteInferior,
                                             @RequestParam("salarioLimiteSuperior") String salarioLimiteSuperior,
                                             @RequestParam(value = "enviarEmail", required = false) String enviarEmail,
                                             @RequestParam("diaEnvio") String diaEnvio,
                                             @RequestParam("momentoEnvio") String momentoEnvio,
                                             Principal principal,
                                             RedirectAttributes redirectAttributes,
                                             Model model) {
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal, usuarioService.getUsuarioById(Long.parseLong(idUsuario)), PermisoAcceso.EDITAR);
        if(autorizado) {
            filtroOfertaLaboralService.editarFiltroLaboralUsuario(idUsuario, idFiltroOfertaLaboral, nombreFiltro, region, tipoCargo, carrera, tipoArea, tipoEstudio, rangoAniosExperiencia, aniosExperiencia, tipoMoneda, salarioLimiteInferior, salarioLimiteSuperior, enviarEmail, diaEnvio, momentoEnvio);

            redirectAttributes.addFlashAttribute("flash.success", "El filtro a sido modificado exitosamente");

            return "redirect:/usuario/perfil/" + idUsuario.toString();
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para editar la información del usuario ingresado (Editar filtro de ofertas laborales)");
            redirectAttributes.addAttribute("id", idUsuario);
            return "redirect:/usuario/perfil/{id}";
        }
    }




    /**
     * Elimina fisicamente una {@link crm.entities.FiltroOfertaLaboral} asociado a un usuario
     *
     * @param idUsuario identificador del {@link crm.entities.Usuario} asociado al {@link crm.entities.FiltroOfertaLaboral}
     * @param idFiltroOfertaLaboral identificador del{@link crm.entities.FiltroOfertaLaboral} a eliminar
     *
     * @return La vista de perfil del usuario.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/{idUsuario}/filtroOfertaLaboral/{idFiltroOfertaLaboral}/eliminar", method = RequestMethod.GET)
    public String eliminarFiltroOfertaLaboral(@PathVariable String idUsuario,
                                              @PathVariable String idFiltroOfertaLaboral,
                                              Principal principal,
                                              RedirectAttributes redirectAttributes) {
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal, usuarioService.getUsuarioById(Long.parseLong(idUsuario)), PermisoAcceso.EDITAR);
        if(autorizado) {
            filtroOfertaLaboralService.eliminarFiltroLaboralUsuario(idFiltroOfertaLaboral);

            redirectAttributes.addFlashAttribute("flash.success", "El filtro ha sido eliminado exitosamente.");

            return "redirect:/usuario/perfil/" + idUsuario;
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para editar la información del usuario ingresado (Eliminar filtro de oferta laboral)");
            redirectAttributes.addAttribute("id", idUsuario);
            return "redirect:/usuario/perfil/{id}";
        }
    }




    /**
     *
     * @param idUsuario identificador del {@link crm.entities.Usuario}
     *
     * @return La vista de perfil del usuario.
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/usuario/{idUsuario}/actualizarTrayectoria", method = RequestMethod.GET)
    public String actualizarTrayectoria(@PathVariable String idUsuario,
                                        Principal principal,
                                          RedirectAttributes redirectAttributes) {
        Boolean autorizado = crmPermissionEvaluator.hasImplicitPermissionOverUser((Authentication) principal, usuarioService.getUsuarioById(Long.parseLong(idUsuario)), PermisoAcceso.EDITAR);
        if (autorizado) {
            usuarioService.actualizarTrayectoria(idUsuario);

            redirectAttributes.addFlashAttribute("flash.success", "Se ha realizado la actualizacion de la trayectoria.");

            return "redirect:/usuario/perfil/" + idUsuario + "#informacionPersonal";
        }else{
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para editar la información del usuario ingresado (Actualizar trayectoria)");
            redirectAttributes.addAttribute("id", idUsuario);
            return "redirect:/usuario/perfil/{id}";
        }
    }





    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de modificar usuario.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author dacuna <diego.acuna@usm.cl>
     */
    private void cargarDatosParaModificarUsuario(Model model) {
        model.addAttribute("estados", entidadesTipoService.listaTipoEstadoCivil());
        model.addAttribute("razones", entidadesTipoService.listaTipoComoSupoDeRedExalumnos());
        model.addAttribute("comunas", geograficoService.getComunas());
        model.addAttribute("formatoCv", usuarioService.getFormatos());
        model.addAttribute("monedas", entidadesTipoService.getMonedas());
        model.addAttribute("disponibilidad",entidadesTipoService.getDisponibilidades());
        model.addAttribute("dominioComp",entidadesTipoService.getDominiosComputacionales());
        model.addAttribute("situacion",entidadesTipoService.getsituacionesLaborales());
        model.addAttribute("vigencias",entidadesTipoService.buscarTodosTiposVigencia());
    }

}
