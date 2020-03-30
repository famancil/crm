package crm.controllers;

import crm.entities.*;
import crm.services.EmailService;
import crm.services.EmpresaService;
import crm.services.EntidadesTipoService;
import crm.services.SucursalEmpresaService;
import crm.utils.MailUtil;
import crm.validators.SucursalEmpresaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja el perfil de una {@link crm.entities.SucursalEmpresa}.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Controller
public class PerfilSucursalEmpresaController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.SucursalEmpresa}.
     */
    @Autowired
    private SucursalEmpresaService sucursalEmpresaService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    @Autowired
    private MailUtil mailUtil;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.EmailMensaje}.
     */
    @Autowired
    private EmailService emailService;


    /**
     * InitBinder utilizado para setear el validador correspondiente de la clase SucursalEmpresa.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
  /*  @InitBinder
    private void perfilSucursalEmpresaBinder(WebDataBinder binder) {
        //Setea el validador de sucursal de empresa en binder desde la clase SucursalEmpresaValidator
        binder.setValidator(new SucursalEmpresaValidator());
    }*/

    /**
     * Accion que despliega la interfaz que posibilita la edicion del perfil de una
     * {@link crm.entities.SucursalEmpresa} en particular.
     *
     * @param model Modelo utilizado en la vista.
     * @param idSucursal Id de la {@link crm.entities.SucursalEmpresa} a modificar.
     * @param idEmpresa Id de la {@link crm.entities.Empresa} dueña de la {@link crm.entities.SucursalEmpresa} a modificar.
     *
     * @return Retorna la vista de modificar empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/sucursal/{idSucursal}/modificar", method = RequestMethod.GET)
    public String mostrarModificarFichaSucursal(Model model, @PathVariable Long idSucursal, @PathVariable Long idEmpresa, RedirectAttributes redirectAttributes) {
        SucursalEmpresa sucursal = sucursalEmpresaService.getSucursalEmpresaById(idSucursal);
        if(sucursal.getEmpresa().getId().compareTo(idEmpresa)!=0){
            redirectAttributes.addFlashAttribute("flash.error", "La Sucursal no pertenece a la Empresa ingresada");
            return "redirect:/empresa/"+idEmpresa;
        }
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
        model.addAttribute("sucursal", sucursal);
        return "/empresa/sucursalEmpresa/modificarSucursal";
    }

    /**
     * Toma el formulario desde la vista modificarSucursal, actualiza la base de datos con los valores
     * ingresados y retorna a la misma vista.
     *
     * @param sucursal {@link crm.entities.SucursalEmpresa} a modificar.
     * @param error Errores presentes en el formulario (si es que existen).
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista modificarSucursal
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/sucursal/perfil/modificar/", method = RequestMethod.POST)
    public String modificarFichaSucursalEmpresa(@Valid @ModelAttribute SucursalEmpresa sucursal, BindingResult error,
                                                @RequestParam Long id, RedirectAttributes redirectAttributes, Model model) {
        if(error.hasErrors()) {
            SucursalEmpresa suc = sucursalEmpresaService.getSucursalEmpresaById(id);
            sucursal.setEmpresa(suc.getEmpresa());
            sucursal.setPais(suc.getPais());
            model.addAttribute("sucursal",sucursal);
            model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
            return "/empresa/sucursalEmpresa/modificarSucursal";
        }
        sucursal.setSucursalCodigo(id);
        sucursal = sucursalEmpresaService.saveSucursalEmpresa(sucursal);
        redirectAttributes.addFlashAttribute("flash.success", "El perfil de la sucursal ha sido actualizado correctamente.");
        return "redirect:/empresa/"+sucursal.getEmpresa().getId()+"/sucursales";
    }

    /**
     * Accion que despliega la interfaz que lista las sucursales de una empresa en particular.
     *
     * @param model Modelo utilizado en la vista.
     * @param idEmpresa Id de la {@link crm.entities.Empresa} dueña de la {@link crm.entities.SucursalEmpresa} a modificar.
     *
     * @return Retorna la vista de lista de sucursales.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/sucursales", method = RequestMethod.GET)
    public String mostrarListaSucursalesEmpresa(Model model, @PathVariable Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresaById(idEmpresa);
        empresa.setCantidadSucursales(empresaService.getCantidadSucursalesEmpresa(empresa.getId()));
        List<SucursalEmpresa> sucursales = empresaService.getSucursalesEmpresa(idEmpresa);
        model.addAttribute("empresa", empresa);
        model.addAttribute("sucursales", sucursales);
        return "/empresa/sucursalEmpresa/listaSucursalesEmpresa";
    }

    /**
     * Elimina fisicamente una sucursal segun el idSucursal y el idEmpresa otorgado
     *
     * @param idSucursal identificador de la {@link crm.entities.SucursalEmpresa} a eliminar.
     * @param idEmpresa identificador de la {@link crm.entities.Empresa} dueña de la {@link crm.entities.SucursalEmpresa} a eliminar.
     *
     * @return Retorna la vista de lista de sucursales de una empresa
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/sucursal/{idSucursal}/eliminar", method = RequestMethod.POST)
    public String eliminarSucursalEmpresa(@PathVariable Long idEmpresa, @PathVariable Long idSucursal, RedirectAttributes redirectAttributes) {
        SucursalEmpresa sucursal = sucursalEmpresaService.getSucursalEmpresaById(idSucursal);
        sucursalEmpresaService.eliminarSucursal(sucursal);
        redirectAttributes.addFlashAttribute("flash.success", "La sucursal ha sido eliminada correctamente.");
        return "redirect:/empresa/"+idEmpresa+"/sucursales";
    }

    /**
     * Accion que despliega la interfaz que lista las sucursales de una empresa en particular.
     *
     * @param model Modelo utilizado en la vista.
     * @param idEmpresa Id de la {@link crm.entities.Empresa} dueña de la {@link crm.entities.SucursalEmpresa} a modificar.
     *
     * @return Retorna la vista de lista de sucursales.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/sucursales/por-validar/{from}", method = RequestMethod.GET)
    public String mostrarListaSucursalesPorValidarEmpresa(Model model, @PathVariable("idEmpresa") Long idEmpresa, @PathVariable("from") String from) {
        Empresa empresa = empresaService.getEmpresaById(idEmpresa);
        empresa.setSucursalesPorValidar(empresaService.getSucursalesEmpresaPorValidar(idEmpresa));
        String emailRechazo = emailService.getContenidoEmailMensajeById(16);
        emailRechazo = emailRechazo.replace("[nombre-empresa]", empresa.getNombreFantasiaEmpresa());
        model.addAttribute("from", from);
        model.addAttribute("emailRechazo", emailRechazo);
        model.addAttribute("empresa", empresa);
        return "/empresa/sucursalEmpresa/sucursalesPorValidarEmpresa";
    }

    /**
     * Accion que despliega la interfaz que posibilita la edicion del perfil de una
     * {@link crm.entities.SucursalEmpresa} para completar sus datos.
     *
     * @param model Modelo utilizado en la vista.
     * @param idEmpresa Id de la {@link crm.entities.Empresa} que es dueña de la sucursal a modificar.
     * @param idSucursal Id de la {@link crm.entities.SucursalEmpresa} a modificar.
     * @param from Indica si la sucursal fue ingresada por un alumno o por un publicador
     *
     * @return Retorna la vista de completar datos de una sucursal.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/sucursal/{idSucursal}/{from}/completar-datos", method = RequestMethod.GET)
    public String mostrarCompletarDatosEmpresaIngresadaPorAlumno(Model model,
                                                                 @PathVariable("idEmpresa") Long idEmpresa,
                                                                 @PathVariable("idSucursal") Long idSucursal,
                                                                 @PathVariable("from") String from,
                                                                 RedirectAttributes redirectAttributes) {
        SucursalEmpresa sucursal = sucursalEmpresaService.getSucursalEmpresaById(idSucursal);
        if(sucursal.getEmpresa().getId().compareTo(idEmpresa)!=0){
            redirectAttributes.addFlashAttribute("flash.error", "La Sucursal no pertenece a la Empresa ingresada");
            return "redirect:/empresa/"+idEmpresa+"/sucursales/por-validar/por-alumno";
        }
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("from", from);
        return "/empresa/sucursalEmpresa/completarDatosSucursal";
    }

    /**
     * Toma el formulario desde la vista completarDatosSucursal, actualiza la base de datos con los valores
     * ingresados, valida la sucursal y retorna a la vista correspondiente.
     *
     * @param sucursal {@link crm.entities.SucursalEmpresa} a modificar.
     * @param error Errores presentes en el formulario (si es que existen).
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista modificarSucursal
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/sucursal/completar-datos/", method = RequestMethod.POST)
    public String completarDatosSucursalEmpresa(@Valid @ModelAttribute SucursalEmpresa sucursal,
                                                BindingResult error,
                                                @RequestParam("idSucursal") Long idSucursal,
                                                @RequestParam("from") String from,
                                                RedirectAttributes redirectAttributes,
                                                Model model) {
        if(error.hasErrors()) {
            SucursalEmpresa s = sucursalEmpresaService.getSucursalEmpresaById(idSucursal);
            if(sucursal.getEmpresa()!= null) sucursal.setEmpresa(s.getEmpresa());
            if(sucursal.getPais()!=null)sucursal.setPais(s.getPais());
            model.addAttribute("sucursal",sucursal);
            return "/empresa/sucursalEmpresa/completarDatosSucursal";
        }
        sucursal.setSucursalCodigo(idSucursal);
        sucursal.setTipoVigencia(new TipoVigencia(TipoVigencia.ID_VIGENTE,TipoVigencia.TIPO_VIGENTE));
        sucursal = sucursalEmpresaService.saveSucursalEmpresa(sucursal);
        redirectAttributes.addFlashAttribute("flash.success", "La sucursal ha sido validada correctamente.");
        return "redirect:/empresa/"+sucursal.getEmpresa().getId()+"/sucursales/por-validar/"+from;
    }

    /**
     * Corrige una sucursal, asociando los antecedentes laborales asociados a la sucursal a corregir a otra sucursal.
     *
     * @param idSucursalACorregir id de la {@link crm.entities.SucursalEmpresa} que se va a corregir.
     * @param idSucursalReasignar id de la sucursal que va a reemplazar a la sucursal a corregir
     *
     * @return Retorna la vista correspondiente a sucursales por validar de empresas
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/sucursal/corregir", method = RequestMethod.POST)
    public String corregirSucursal(@RequestParam("idSucursalReasignar") Long idSucursalReasignar,
                                  @RequestParam("idSucursalACorregir") Long idSucursalACorregir,
                                  @RequestParam("from") String from,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {
        SucursalEmpresa sucursalACorregir = sucursalEmpresaService.getSucursalEmpresaById(idSucursalACorregir);
        if(idSucursalReasignar == 0 || idSucursalACorregir == 0 || idSucursalACorregir == null || idSucursalReasignar == null){
            redirectAttributes.addFlashAttribute("flash.error", "Debe seleccionar una sucursal.");
            return "redirect:/empresa/"+sucursalACorregir.getEmpresa().getId()+"/sucursales/por-validar/"+from;
        }
        SucursalEmpresa sucursalReasignar = sucursalEmpresaService.getSucursalEmpresaById(idSucursalReasignar);
        sucursalEmpresaService.corregirSucursal(sucursalReasignar, sucursalACorregir);
        List<String> destinatarios = new ArrayList<>();
        EmailMensaje emailMensaje = emailService.getEmailMensajeById(14);
        emailMensaje.setMensaje(emailMensaje.getMensaje().replace("[nombreSucursalEnlace]",sucursalReasignar.getSucSucursal())
                .replace("[nombreSucursalRechazada]",sucursalACorregir.getSucSucursal())
                .replace("[nombreEmpresa]",sucursalACorregir.getEmpresa().getNombreFantasiaEmpresa()));
        if(from.compareTo("por-alumno") == 0) {
            List<ActividadExalumno> actividadesExalumnos = sucursalEmpresaService.getActividadesExalumnoPorSucursalCodigo(idSucursalACorregir);
            for (ActividadExalumno act : actividadesExalumnos) {
                destinatarios.add(act.getUsuario().getEmail());
            }
        }
        else if(from.compareTo("por-publicador") == 0){
            List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresas = sucursalEmpresaService.getContactoEmpresaPorSucursalCodigo(idSucursalACorregir);
            for (UsuarioUsmempleoEmpresa usu: usuarioUsmempleoEmpresas) {
                destinatarios.add(usu.getUsuarioEmpresaUsmempleo().getEmail());
            }
        }
        try {
            mailUtil.sendMail(mailUtil.getMailSender().getUsername(), destinatarios, emailMensaje.getAsunto(), emailMensaje.getMensaje());
        } catch (MessagingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/empresa/"+sucursalACorregir.getEmpresa().getId()+"/sucursales/por-validar/"+from;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/empresa/"+sucursalACorregir.getEmpresa().getId()+"/sucursales/por-validar/"+from;
        }
        redirectAttributes.addFlashAttribute("flash.success", "La sucursal se ha corregido correctamente.");
        return "redirect:/empresa/"+sucursalACorregir.getEmpresa().getId()+"/sucursales/por-validar/"+from;
    }

    /**
     * Rechaza una sucursal, eliminando todos los antecedentes laborales y otras entidades asociadas a ella.
     *
     * @param idSucursal id de la {@link crm.entities.SucursalEmpresa} que se va a rechazar.
     *
     * @return Retorna la vista de sucursales por validar de empresas
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/sucursal/rechazar", method = RequestMethod.POST)
    public String rechazarSucursal(@RequestParam("idSucursal") Long idSucursal,
                                  @RequestParam("mailContent") String mailContent,
                                  @RequestParam("from") String from,
                                  RedirectAttributes redirectAttributes) {
        SucursalEmpresa sucursalEmpresaARechazar = sucursalEmpresaService.getSucursalEmpresaById(idSucursal);
        sucursalEmpresaService.rechazarSucursal(sucursalEmpresaARechazar);
        List<String> destinatarios = new ArrayList<>();
        EmailMensaje emailMensaje = new EmailMensaje();
        if(from.compareTo("por-alumno") == 0) {
            List<ActividadExalumno> actividadesExalumnos = sucursalEmpresaService.getActividadesExalumnoPorSucursalCodigo(idSucursal);
            for (ActividadExalumno act : actividadesExalumnos) {
                destinatarios.add(act.getUsuario().getEmail());
            }
            emailMensaje = emailService.getEmailMensajeById(16);
        }else if( from.compareTo("por-publicador") == 0 ){
            List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresas = sucursalEmpresaService.getContactoEmpresaPorSucursalCodigo(idSucursal);
            for(UsuarioUsmempleoEmpresa usu: usuarioUsmempleoEmpresas){
                destinatarios.add(usu.getUsuarioEmpresaUsmempleo().getEmail());
            }
            emailMensaje = emailService.getEmailMensajeById(16);
        }
        emailMensaje.setMensaje(mailContent);
        try {
            mailUtil.sendMail(mailUtil.getMailSender().getUsername(), destinatarios, emailMensaje.getAsunto(), emailMensaje.getMensaje());
        } catch (MessagingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/empresa/"+sucursalEmpresaARechazar.getEmpresa().getId()+"/sucursales/por-validar/"+from;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flash.error", "Ha ocurrido un error al enviar el e-mail.");
            return "redirect:/empresa/"+sucursalEmpresaARechazar.getEmpresa().getId()+"/sucursales/por-validar/"+from;
        }
        redirectAttributes.addFlashAttribute("flash.success", "La sucursal se ha eliminado correctamente.");
        return "redirect:/empresa/"+sucursalEmpresaARechazar.getEmpresa().getId()+"/sucursales/por-validar/"+from;
    }

}
