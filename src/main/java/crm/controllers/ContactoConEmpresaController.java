package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.*;
import crm.services.*;
import crm.utils.ResponseView;
import crm.validators.ContactoHistoricoEmpresaValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador que maneja acciones sobre entidades del tipo {@link crm.entities.UsuarioUsmempleoEmpresa}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class ContactoConEmpresaController {


    /**
     * Servicio utilizado para el manejo de los tipos del sistema
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaService usuarioUsmempleoEmpresaService;

    /**
     * Servicio relacionado a las entidades {@link crm.entities.ContactoHistoricoEmpresa} y
     * {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     */
    @Autowired
    private ContactoHistoricoService contactoHistoricoService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Entidad que permite la validacion de un {@link crm.entities.ContactoHistoricoEmpresa}.
     */
    @Autowired
    private ContactoHistoricoEmpresaValidator contactoHistoricoEmpresaValidator;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(ContactoConEmpresaController.class);

    /**
     * InitBinder utilizado para setear el formato de fecha ingresada por formulario y para
     * setear el validador correspondiente de la clase {@link crm.entities.ContactoHistoricoEmpresa}.
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
        //Setea el validador de usuario en binder desde la clase {@link crm.entities.ContactoHistoricoEmpresa}.
        binder.setValidator(contactoHistoricoEmpresaValidator);
    }

    /**
     * Despliega la vista para registrar un contacto con una empresa.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista para registrarContactoConEmpresa
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/registrar-contacto", method = RequestMethod.GET)
    public String contactoConEmpresa(Model model) {

        cargarDatosParaRegistrarContacto(model);

        ContactoHistoricoEmpresa contactoHistoricoEmpresa = new ContactoHistoricoEmpresa();
        String usuariosUsmempleoEmpresaParticipantes = "";
        String nombreUsuarioLogueado = usuarioService.getCurrentUser().getNombres()+" "+usuarioService.getCurrentUser().getApellidoPaterno()+" "+usuarioService.getCurrentUser().getApellidoMaterno();

        model.addAttribute("nombreUsuarioEncargado", nombreUsuarioLogueado);
        model.addAttribute("contactoHistoricoEmpresa", contactoHistoricoEmpresa);
        model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);

        return "/empresa/registrarContactoConEmpresa";
    }



    /**
     * Recibe desde la vista los datos del {@link crm.entities.ContactoHistoricoEmpresa} y los id de los
     * {@link crm.entities.UsuarioUsmempleoEmpresa} participantes del contacto, para ser guardados en la base de datos.
     *
     * @param contactoHistoricoEmpresa objecto ContactoHistoricoEmpresa que contiene los datos ingresaddos en la vista
     *                                 (asunto, tipoContacto, etc).
     * @param error Errores presentes en el formulario (si es que existen).
     * @param redirectAttributes
     * @param usuariosUsmempleoEmpresaParticipantes String con los datos de UsuarioUsmempleoEmpresa ingresados en la vista
     *                                              como participantes del contacto  (en la vista se separa cada usuario
     *                                              con el caracter ";", y cada dato del usuario con el caracter "|")
     * @param model Modelo utilizado en la vista.
     *
     * @return Redirecciona a la url /empresas/registrarContacto
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/registrarContacto", method = RequestMethod.POST)
    public String registrarContactoConEmpresa(@Valid @ModelAttribute ContactoHistoricoEmpresa contactoHistoricoEmpresa,
                                              BindingResult error,
                                              RedirectAttributes redirectAttributes,
                                              @RequestParam("nombreUsuarioEncargado") String nombreUsuarioEncargado,
                                              @RequestParam ("listaUsuariosUsmempleoEmpresaParticipantes") String usuariosUsmempleoEmpresaParticipantes,
                                               Model model) {

        cargarDatosParaRegistrarContacto(model);

        if(error.hasErrors()) {
            // retorna el listado con los usuarios que ya ha registrado
            model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);

            return "/empresa/registrarContactoConEmpresa";
        }

        else {
            List<Usuario> usuario = usuarioService.buscarUsuariosPorCalceNombreCompleto(nombreUsuarioEncargado);
            if(usuario == null || usuario.size() == 0){
                redirectAttributes.addFlashAttribute("flash.error", "El usuario encargado ingresado no existe");
                return "redirect:/empresas/registrar-contacto";
            }
            contactoHistoricoEmpresa.setUsuario(usuario.get(0));
            contactoHistoricoService.agregarContactoHistoricoEmpresa(contactoHistoricoEmpresa,
                    usuariosUsmempleoEmpresaParticipantes);

            redirectAttributes.addFlashAttribute("flash.success", "El Contacto con la Empresa se ha registrado exitosamente.");
            return "redirect:/empresas/registrar-contacto";
        }


    }






    /**
     * Obtiene un listado con {@link crm.entities.UsuarioUsmempleoEmpresa}, según el tipo de busqueda deseado
     * (por nombre de empresa, o nomnbre de usuario) retornando dicho listado a la vista registrarContactoConEmpresa
     * para ser agregados al contacto con la empresa.
     *
     * @param tipoBusqueda tipo de busqueda seleccionado en la busqueda, sobre el cual se ve como manejar el parametro
     *                     valorBuscado
     * @param valorBuscado Valor buscado, conteniendo el nombre de la empresa, o nombre del usuario según sea el caso
     *
     * @return Retorna la vista para registrarContactoConEmpresa
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/buscarUsuarioUsmempleoEmpresa")
    @JsonView(ResponseView.MainView.class)
    public @ResponseBody List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresa(@RequestParam ("valorBuscado") String valorBuscado,
                                                 @RequestParam ("tipoBusqueda") String tipoBusqueda) {

        List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresa = new ArrayList<UsuarioUsmempleoEmpresa>();

        // caso que la busqueda sea por el calce de un nombre de usuario
        if ( tipoBusqueda.equals("usuario") ) {
            usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresaService.buscarUsuariosUsmempleoEmpresaPorCalceNombreUsuario(valorBuscado);
        }

        // caso que la busqueda sea según una empresa especifica
        if ( tipoBusqueda.equals("empresa") ) {

            // busqueda de una empresa que posea la misma razon social, nombre fantasia o sigla
            Empresa empresa = empresaService.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(valorBuscado);

            usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresaService.buscarUsuariosUsmempleoEmpresaPorIdEmpresa(empresa.getId());
        }

        return usuarioUsmempleoEmpresa;
    }





    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de registrarContactoConEmpresa
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaRegistrarContacto(Model model) {
        model.addAttribute("tipoContactos", entidadesTipoService.buscarTodosTipoContacto());
        model.addAttribute("tipoOportunidades", entidadesTipoService.buscarTodosTipoOportunidad());
        model.addAttribute("tipoEstados", entidadesTipoService.buscarTodosTipoEstado());
        model.addAttribute("tipoNivelInteres", entidadesTipoService.buscarTodosTipoNivelInteres());
    }

}
