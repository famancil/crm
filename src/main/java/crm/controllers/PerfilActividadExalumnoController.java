package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.*;
import crm.services.*;
import crm.utils.ResponseView;
import crm.validators.ActividadExalumnoValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja el perfil de un {@link crm.entities.ActividadExalumno}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class PerfilActividadExalumnoController {

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de las entidades que son antecedentes
     */
    @Autowired
    private EntidadesAntecedenteService entidadesAntecedenteService;

    /**
     * Servicio utilizado para el manejo de las entidades que son antecedentes
     */
    @Autowired
    private EmpresaService empresaService;

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
    @InitBinder("actividadExalumno")
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        binder.setValidator(new ActividadExalumnoValidator());
    }

    /**
     * Muestra el perfil de un {@link crm.entities.AntecedenteEducacional}
     *
     * @param model Modelo utilizado por la vista.
     * @param id id del {@link crm.entities.AntecedenteEducacional} del cual se desea ver el perfil
     *
     * @return Retorna una vista
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value ="/antecedenteLaboral/perfil/{id}")
    public String verPerfilAntecedenteLaboral(Model model, @PathVariable Long id) {
        Usuario perfil = usuarioService.getUsuarioById(id);
        model.addAttribute("actividades", usuarioService.getActividadExalumno(id));
        model.addAttribute("perfil", perfil);
        model.addAttribute("nombre", perfil.getNombreCompleto());
        model.addAttribute("actividad", new ActividadExalumno());
        model.addAttribute("monedas", entidadesTipoService.getMonedas());
        model.addAttribute("tipos", entidadesTipoService.getTipoActividades());
        model.addAttribute("cargos", entidadesTipoService.getCargos());

        return "/antecedentes/perfilAntecedenteLaboral";
    }

    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * @param actividadExalumno {@link crm.entities.ActividadExalumno}
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.ActividadExalumno'}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/antecedenteLaboral/registrar", method = RequestMethod.POST)
    public String registrarAntecedenteLaboral(@Valid @ModelAttribute ActividadExalumno actividadExalumno, BindingResult error,
                                              RedirectAttributes redirectAttributes,
                                              Model model,
                                              @RequestParam("idUsuario") Long idUsuario){
        if(error.hasErrors()){
            Usuario perfil = usuarioService.getUsuarioById(idUsuario);
            model.addAttribute("actividades", usuarioService.getActividadExalumno(idUsuario));
            model.addAttribute("perfil", perfil);
            model.addAttribute("nombre", perfil.getNombreCompleto());
            model.addAttribute("actividad", new ActividadExalumno());
            model.addAttribute("monedas", entidadesTipoService.getMonedas());
            model.addAttribute("tipos", entidadesTipoService.getTipoActividades());
            model.addAttribute("cargos", entidadesTipoService.getCargos());
            return "/antecedentes/perfilAntecedenteLaboral";
        }
        actividadExalumno.setEmpresa(empresaService.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(actividadExalumno.getEmpresa().getRazonSocial()));
        Date actual = new java.util.Date();
        actividadExalumno.setFechaModificacion(actual);
        actividadExalumno.setRutUsuario(usuarioService.getCurrentUser().getRut());
        usuarioService.guardarActividadExalumno(actividadExalumno);

        redirectAttributes.addFlashAttribute("flash.success", "El nuevo antecedente laboral se ha registrado exitosamente.");
        return "redirect:/antecedenteLaboral/perfil/"+idUsuario;
    }

    /**
     * Retorna una lista de las {@link crm.entities.Empresa de un {@link crm.entities.Pais}.
     *
     * @param idPais Id del pais en el que se desean encontrar empresas.
     *
     * @return {@link java.util.List} de {@link crm.entities.Empresa}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/opcion/empresa/{idPais}")
    @JsonView(ResponseView.MainView.class)
    public @ResponseBody List<Empresa> listaEmpresas(@PathVariable("idPais") Short idPais) {
        return empresaService.getEmpresasPorPais(idPais);
    }

    /**
     * Retorna una lista de las {@link crm.entities.SucursalEmpresa} de una {@link crm.entities.Empresa}.
     *
     * @param nombreEmpresa
     *
     * @return {@link java.util.List} de {@link crm.entities.SucursalEmpresa}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/opciones/sucursal/{nombreEmpresa}", method = RequestMethod.GET)
    @JsonView(ResponseView.MainView.class)
    public  @ResponseBody List<SucursalEmpresa> opcionesSucursal(@PathVariable("nombreEmpresa") String nombreEmpresa){
        return empresaService.getSucursalesEmpresaByNombre(nombreEmpresa);
    }

    /**
     * Elimina una {@link crm.entities.ActividadExalumno} de la base de datos.
     *
     * @param id Id de la {@link crm.entities.ActividadExalumno} a eliminar.
     *
     * @return Retorna una vista de exito.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/antecedenteLaboral/eliminar")
    public String eliminarAntecedenteLaboral(@RequestParam("id") Long id){
        entidadesAntecedenteService.eliminarAntecedenteLaboral(id);
        return "/usuario/test";
    }
}
