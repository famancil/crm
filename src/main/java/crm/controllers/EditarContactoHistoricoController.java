package crm.controllers;

import crm.entities.ContactoHistoricoEmpresaPersonaParticipante;
import crm.entities.Empresa;
import crm.services.EmpresaService;
import crm.validators.ContactoHistoricoEmpresaPersonaParticipanteValidator;
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
 * Created by rocio on 02-06-16.
 */
@Controller
public class EditarContactoHistoricoController {


    /**
     * Servicio relacionado a la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaService empresaService;

    /**
     * Entidad que permite la validacion de un {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}.
     */
    @Autowired
    private ContactoHistoricoEmpresaPersonaParticipanteValidator contactoHistoricoEmpresaPersonaParticipanteValidator;


    @InitBinder("contacto")
    private void validatorBinder(WebDataBinder binder) {
        binder.setValidator(contactoHistoricoEmpresaPersonaParticipanteValidator);
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value= "/empresa/{idEmpresa}/contactoHistorico/{contactoHistoricoEmpresaId}/{usuarioUsmempleoEmpresaId}/editar", method = RequestMethod.GET)
    public String editarContactoEmpresa(Model model, @PathVariable Long idEmpresa, @PathVariable Long contactoHistoricoEmpresaId,
                                        @PathVariable Long usuarioUsmempleoEmpresaId){
        model.addAttribute("contacto", empresaService.mostrarContactoHistoricoEmpresa(usuarioUsmempleoEmpresaId,contactoHistoricoEmpresaId));
        model.addAttribute("tipoContactos", empresaService.mostrarTipoContactos());
        model.addAttribute("tipoOportunidad", empresaService.mostrarTipoOportunidad());
        model.addAttribute("tipoNivelInteres", empresaService.mostrarTipoNivelInteres());
        model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
        return "/empresa/editarContactoHistorico";
    }

    @RequestMapping(value = "/empresa/{idEmpresa}/contactoHistorico/{contactoHistoricoEmpresaId}/{usuarioUsmempleoEmpresaId}/editar", method = RequestMethod.POST)
    public String procesarEditarContacto(@Valid @ModelAttribute("contacto") ContactoHistoricoEmpresaPersonaParticipante contacto, BindingResult error, @PathVariable Long idEmpresa,
                                         @PathVariable Long contactoHistoricoEmpresaId,
                                         @PathVariable Long usuarioUsmempleoEmpresaId, RedirectAttributes redirectAttributes, Model model){
        if(error.hasErrors()) {
            ContactoHistoricoEmpresaPersonaParticipante contactoOriginal = empresaService.mostrarContactoHistoricoEmpresa(usuarioUsmempleoEmpresaId,contactoHistoricoEmpresaId);
            contacto.getContactoHistoricoEmpresa().setUsuario(contactoOriginal.getContactoHistoricoEmpresa().getUsuario());
            contacto.setUsuarioUsmempleoEmpresa(contactoOriginal.getUsuarioUsmempleoEmpresa());
            model.addAttribute("contacto", contacto);
            model.addAttribute("error", error);
            model.addAttribute("tipoContactos", empresaService.mostrarTipoContactos());
            model.addAttribute("tipoOportunidad", empresaService.mostrarTipoOportunidad());
            model.addAttribute("tipoNivelInteres", empresaService.mostrarTipoNivelInteres());
            model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
            return "/empresa/editarContactoHistorico";
        }
        empresaService.modificarContactoHistoricoEmpresaParticipante(contacto);
        empresaService.saveContactoHistoricoEmpresaParticipante(contacto);
        redirectAttributes.addFlashAttribute("flash.success", "Se ha realizado la actualizacion del contacto.");
        return "redirect:/empresa/" + idEmpresa;
    }


}