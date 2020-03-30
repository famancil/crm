package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import crm.entities.*;
import crm.services.*;
import crm.utils.ResponseView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja los {@link crm.entities.AporteEmpresa}.
 *
 * @author  Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
 */

@Controller
public class AporteEmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private SucursalEmpresaService sucursalEmpresaService;

    @Autowired
    private EntidadesTipoService entidadesTipoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private InstitucionService institucionService;

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
        //Setea el validador de usuario en binder desde la clase {@link crm.entities.ContactoHistoricoEmpresa}.
    }

    @RequestMapping(value = "/empresas/{idEmpresa}/registrar-aporte/{idCompromiso}", method = RequestMethod.GET)
    public String registrarCompromisoEmpresaPorIdEmpresa(Model model, @PathVariable Long idEmpresa, @PathVariable Long idCompromiso ) {

        AporteEmpresa aporteEmpresa = new AporteEmpresa();

        model.addAttribute("aporte", aporteEmpresa);
        if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
        if(idCompromiso!=0)model.addAttribute("compromiso", empresaService.getCompromisoEmpresaPorId(idCompromiso));
        model.addAttribute("sucursales", empresaService.getSucursalesEmpresa(idEmpresa));
        model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
        model.addAttribute("tipoEstadoPagos", entidadesTipoService.buscarTodosTiposEstadoPago());
        model.addAttribute("tipoComprobantes", entidadesTipoService.buscarTodosTiposComprobante());
        /*String usuariosUsmempleoEmpresaParticipantes = "";
        if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
        //model.addAttribute("compromiso", compromiso);
        model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);
        model.addAttribute("idEmpresa", idEmpresa);
        model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
        model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
        model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());*/
        return "/empresa/registrarAporteEmpresa";
    }

    @RequestMapping(value = "/empresas/registrarAporte", method = RequestMethod.POST)
    public String registrarAporteEmpresa(@Valid @ModelAttribute AporteEmpresa aporte, BindingResult error,
                                         RedirectAttributes redirectAttributes,
                                         @RequestParam("idEmpresa") Long idEmpresa,
                                         Model model) {

        if(error.hasErrors()) {
            model.addAttribute("aporte", aporte);
            if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
            if(idEmpresa!=0)model.addAttribute("compromisos", empresaService.getCompromisoEmpresa(idEmpresa));
            model.addAttribute("sucursales", empresaService.getSucursalesEmpresa(idEmpresa));
            model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
            model.addAttribute("tipoEstadoPagos", entidadesTipoService.buscarTodosTiposEstadoPago());
            model.addAttribute("tipoComprobantes", entidadesTipoService.buscarTodosTiposComprobante());
            return "/empresa/registrarAporteEmpresa";
        }

        Institucion institucion = institucionService.buscarInstitucionPorCodInstitucion(Short.parseShort("25"));
        aporte.setInstitucion(institucion);

        empresaService.agregarAporteEmpresa(aporte);

        redirectAttributes.addFlashAttribute("flash.success", "El Aporte con la Empresa se ha registrado exitosamente.");
        return "redirect:/empresa/"+idEmpresa;
    }

    @RequestMapping(value = "/empresa/{idEmpresa}/modificar-aporte/{idAporte}", method = RequestMethod.GET)
    public String modificarCompromisoEmpresa(@PathVariable Long idAporte, @PathVariable Long idEmpresa, Model model, RedirectAttributes redirectAttributes) {

        AporteEmpresa aporteEmpresa = empresaService.getAporteEmpresaPorId(idAporte);

        if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
        if(idEmpresa!=0)model.addAttribute("compromisos", empresaService.getCompromisoEmpresa(idEmpresa));
        model.addAttribute("sucursales", empresaService.getSucursalesEmpresa(idEmpresa));
        model.addAttribute("aporte", aporteEmpresa);
        cargarDatosParaModificarCompromiso(model);
        /*cargarDatosParaModificarAporteCompromiso(model);
        model.addAttribute("sucursalEmpresas", empresaService.getSucursalesEmpresa(idEmpresa));
        model.addAttribute("nombreOperador", compromiso.getOperadorACargo().getNombreCompleto());
        model.addAttribute("compromiso", compromiso);
        model.addAttribute("idEmpresa", idEmpresa);
        model.addAttribute("idCompromiso", idCompromiso);*/
        return "/empresa/modificarAporteEmpresa";
    }

    @RequestMapping(value = "/empresas/modificarAporte", method = RequestMethod.POST)
    public String modificarFichaCompromisoEmpresa(@Valid @ModelAttribute AporteEmpresa aporte, BindingResult error,
                                                  @RequestParam Long id,@RequestParam Long idEmpresa,
                                                  RedirectAttributes redirectAttributes, Model model) {
        AporteEmpresa apo = empresaService.getAporteEmpresaPorId(id);


        aporte.setInstitucion(apo.getInstitucion());
        aporte.setRutUsuario(usuarioService.getCurrentUser().getRut());
        aporte.setFechaModificacion(new Date());

        if(error.hasErrors()) {
            model.addAttribute("aporte", aporte);
            if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
            if(idEmpresa!=0)model.addAttribute("compromisos", empresaService.getCompromisoEmpresa(idEmpresa));
            cargarDatosParaModificarCompromiso(model);
            redirectAttributes.addFlashAttribute("flash.error", "Datos erroneos, favor revisar");
            return "redirect:/empresa/"+idEmpresa+"/modificar-aporte/"+id;
        }

        aporte = empresaService.updateAporteEmpresa(aporte);
        redirectAttributes.addFlashAttribute("flash.success", "El Aporte de la empresa se ha sido actualizado correctamente.");
        return "redirect:/empresa/"+aporte.getSucursalEmpresa().getEmpresa().getId();
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
    @RequestMapping(value = "/empresas/buscarCompromisosEmpresa")
    @JsonView(ResponseView.MainView.class)
    public @ResponseBody
    List<CompromisoEmpresa> buscarCompromisosEmpresa(@RequestParam ("valorBuscado") String valorBuscado,
                                                                 @RequestParam ("tipoBusqueda") String tipoBusqueda) {

        List<CompromisoEmpresa> compromisos = new ArrayList<CompromisoEmpresa>();

        // caso que la busqueda sea por el calce de un nombre de usuario
        //if ( tipoBusqueda.equals("usuario") ) {
          //  usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresaService.buscarUsuariosUsmempleoEmpresaPorCalceNombreUsuario(valorBuscado);
        //}

        // caso que la busqueda sea según una empresa especifica
        if ( tipoBusqueda.equals("empresa") ) {

            // busqueda de una empresa que posea la misma razon social, nombre fantasia o sigla
            Empresa empresa = empresaService.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(valorBuscado);

            compromisos = empresaService.getCompromisoEmpresa(empresa.getId());
        }

        for(CompromisoEmpresa comp : compromisos){
            System.out.println(comp.getTipoCompromiso().getNomCompromiso());
        }

        return compromisos;
    }

    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de modificar compromiso.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author Felipe Mancilla S <felipe.mancilla.mancilla@alumnos.usm.cl>
     */
    private void cargarDatosParaModificarCompromiso(Model model) {
        model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
        model.addAttribute("tipoEstadoPagos", entidadesTipoService.buscarTodosTiposEstadoPago());
        model.addAttribute("tipoComprobantes", entidadesTipoService.buscarTodosTiposComprobante());
    }
}
