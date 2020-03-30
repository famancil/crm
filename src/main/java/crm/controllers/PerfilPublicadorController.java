package crm.controllers;

import crm.entities.*;
import crm.services.*;
import crm.validators.ContactoHistoricoEmpresaValidator;
import crm.validators.UsuarioEmpresaUsmempleoValidator;
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

/**
 * Controlador que maneja acciones sobre entidades del tipo {@link crm.entities.UsuarioEmpresaUsmempleo}
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Controller
public class PerfilPublicadorController {

    /**
     * Servicio utilizado para el manejo de los tipos del sistema
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de una entidad empresa
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Servicio utilizado para el manejo de una entidad UsuarioEmpresaUsmempleo
     */
    @Autowired
    private UsuarioEmpresaUsmempleoService usuarioEmpresaUsmempleoService;

    /**
     * Entidad que permite la validacion de un usuarioEmpresaUsmempleo
     */
    @Autowired
    private UsuarioEmpresaUsmempleoValidator usuarioEmpresaUsmempleoValidator;

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
        binder.setValidator(usuarioEmpresaUsmempleoValidator);
    }


    /**
     * Despliega la vista para registrar un perfil publicador.
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista para registrar un perfil publicador.
     *
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/registrar-perfil-publicador", method = RequestMethod.GET)
    public String mostrarRegistrarPerfilPublicador(Model model) {

        UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo = new UsuarioEmpresaUsmempleo();
        String nombreEmpresa = "";
        Long codSucursal= Long.valueOf(-1);
        Pais pais = new Pais();
        Comuna comuna = new Comuna();

        pais.setCodigo((short)56);

        usuarioEmpresaUsmempleo.setPais(pais);
        usuarioEmpresaUsmempleo.setComuna(comuna);

        model.addAttribute("nombreEmpresa", nombreEmpresa);
        model.addAttribute("codSucursal", codSucursal);
        model.addAttribute("usuarioEmpresaUsmempleo", usuarioEmpresaUsmempleo);
        model.addAttribute("paises", empresaService.cargarPaises());
        return "/empresa/registrarPerfilPublicador";
    }

    /**
     * Recibe desde la vista los datos del {@link crm.entities.UsuarioEmpresaUsmempleo}, el nombre de la
     * {@link crm.entities.Empresa}  y el codigo de la {@link crm.entities.SucursalEmpresa} a la cual pertenece el
     * {@link crm.entities.UsuarioEmpresaUsmempleo} registrado.
     *
     * @param usuarioEmpresaUsmempleo {@link crm.entities.UsuarioEmpresaUsmempleo} que se va a registrar.
     * @param nombreEmpresa nombre de la {@link crm.entities.Empresa} a la cual pertenece el {@link crm.entities.UsuarioEmpresaUsmempleo}.
     * @param codSucursal codigo de la sucursal de la empresa a la cual pertenece el {@link crm.entities.UsuarioEmpresaUsmempleo}.
     * @param confirmPassword confirmacion de la contraseña.
     * @param error Errores presentes en el formulario (si es que existen).
     * @param redirectAttributes
     * @param model Modelo utilizado en la vista.
     *
     * @return Redirecciona a la url /empresas/registrar-perfil-publicador
     *
     * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresas/registrar-perfil-publicador", method = RequestMethod.POST)
    public String registrarPerfilPublicador(@Valid @ModelAttribute UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo,
                                              BindingResult error,
                                              RedirectAttributes redirectAttributes,
                                              Model model,
                                              @RequestParam("nombreEmpresa") String nombreEmpresa,
                                              @RequestParam(value = "codSucursal", required = false) Long codSucursal,
                                              @RequestParam("confirmPassword") String confirmPassword) {
        if (error.hasErrors()) {
            model.addAttribute("nombreEmpresa", nombreEmpresa);
            model.addAttribute("codSucursal", codSucursal);
            model.addAttribute("usuarioEmpresaUsmempleo", usuarioEmpresaUsmempleo);
            model.addAttribute("paises", empresaService.cargarPaises());
            return "/empresa/registrarPerfilPublicador";
        } else if(confirmPassword.compareTo(usuarioEmpresaUsmempleo.getPassword())!=0){
            model.addAttribute("nombreEmpresa", nombreEmpresa);
            model.addAttribute("codSucursal", codSucursal);
            model.addAttribute("usuarioEmpresaUsmempleo", usuarioEmpresaUsmempleo);
            model.addAttribute("paises", empresaService.cargarPaises());
            model.addAttribute("passwordMatchError", "Las contraseñas no coinciden.");
            return "/empresa/registrarPerfilPublicador";
        } else if(empresaService.buscarEmpresasPorCalceRazonSocialONombreFantasiaOSigla(nombreEmpresa).size()==0){
            model.addAttribute("nombreEmpresa", nombreEmpresa);
            model.addAttribute("codSucursal", codSucursal);
            model.addAttribute("usuarioEmpresaUsmempleo", usuarioEmpresaUsmempleo);
            model.addAttribute("paises", empresaService.cargarPaises());
            model.addAttribute("noExisteEmpresa", true);
            return "/empresa/registrarPerfilPublicador";
        }else {
            usuarioEmpresaUsmempleoService.guardarPerfilPublicador(usuarioEmpresaUsmempleo, nombreEmpresa, codSucursal);
            redirectAttributes.addFlashAttribute("flash.success", "El perfil publicador ha sido registrado exitosamente.");
            return "redirect:/empresas/registrar-perfil-publicador";
        }
    }
}
