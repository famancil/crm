package crm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import crm.entities.*;
import crm.repositories.UsuarioRepository;
import crm.services.*;
import crm.utils.ResponseView;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.util.calendar.BaseCalendar;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja los {@link crm.entities.CompromisoEmpresa}.
 *
 * @author  Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
 */
@Controller
public class CompromisoEmpresaController {

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



    @RequestMapping(value = "/empresas/registrar-compromiso/{idEmpresa}", method = RequestMethod.GET)
    public String registrarCompromisoEmpresaPorIdEmpresa(Model model, @PathVariable Long idEmpresa ) {

        CompromisoEmpresa compromiso = new CompromisoEmpresa();
        String usuariosUsmempleoEmpresaParticipantes = "";
        if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
        model.addAttribute("compromiso", compromiso);
        model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);
        model.addAttribute("idEmpresa", idEmpresa);
        model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
        model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
        model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
        return "/empresa/registrarCompromisoEmpresa";
    }



    @RequestMapping(value = "/empresas/registrarCompromiso", method = RequestMethod.POST)
    public String registrarFichaCompromisoEmpresa(@Valid @ModelAttribute CompromisoEmpresa compromiso, BindingResult error,
                                                  RedirectAttributes redirectAttributes,
                                                  @RequestParam("idEmpresa") Long idEmpresa,
                                                  @RequestParam("nombreOperador") String nombreOperador,
                                                  @RequestParam("nombreEmpresa") String nombreEmpresa,
                                                  @RequestParam("checkNoAgendoInicio") boolean noAgendo,
                                                  @RequestParam("checkIndefinido") boolean indefinido,
                                                  @RequestParam("checkNoAgendoSgte") boolean noAgendoSgte,
                                                  @RequestParam(value = "codSucursal",required = false) Long codSucursal,
                                                  Model model) {

        if(error.hasErrors() ) {
            String usuariosUsmempleoEmpresaParticipantes = "";
            if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
            model.addAttribute("compromiso", compromiso);
            model.addAttribute("idEmpresa", idEmpresa);
            model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);
            model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
            model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
            model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
            return "/empresa/registrarCompromisoEmpresa";
        }
        else if(idEmpresa==null){
            String usuariosUsmempleoEmpresaParticipantes = "";
            model.addAttribute("compromiso", compromiso);
            model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);
            model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
            model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
            model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
            model.addAttribute("empresaError", "Empresa vacia, favor ingresar.");
            return "/empresa/registrarCompromisoEmpresa";
        }
        else if(codSucursal==null){
            String usuariosUsmempleoEmpresaParticipantes = "";
            if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
            model.addAttribute("compromiso", compromiso);
            model.addAttribute("idEmpresa", idEmpresa);
            model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);
            model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
            model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
            model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
            model.addAttribute("sucursalError", "Sucursal Invalida o Vacia, favor revisar");
            return "/empresa/registrarCompromisoEmpresa";
        }
        else{
            if(compromiso.getFechaInicio()==null){
                redirectAttributes.addFlashAttribute("flash.error", "Fecha Inicio Vacia, debe ingresar una fecha");
                return "redirect:/empresas/registrar-compromiso/"+sucursalEmpresaService.getSucursalEmpresaById(codSucursal).getEmpresa().getId();
            }
            if(compromiso.getFechaTermino()==null && !noAgendo && !indefinido){
                redirectAttributes.addFlashAttribute("flash.error", "Eliga una opcion para 'Fecha Termino'");
                return "redirect:/empresas/registrar-compromiso/"+sucursalEmpresaService.getSucursalEmpresaById(codSucursal).getEmpresa().getId();
            }
            if(compromiso.getFechaSiguienteCompromiso()==null && !noAgendoSgte){
                redirectAttributes.addFlashAttribute("flash.error", "Eliga una opcion para 'Fecha Siguiente Compromiso'");
                return "redirect:/empresas/registrar-compromiso/"+sucursalEmpresaService.getSucursalEmpresaById(codSucursal).getEmpresa().getId();
            }

            compromiso.setTipoFormaPago(entidadesTipoService.buscarTipoFormaPagoPorCodFormaPago(compromiso.getTipoFormaPago().getCodFormaPago()));
            compromiso.setSucursalEmpresa(sucursalEmpresaService.getSucursalEmpresaById(codSucursal));
            compromiso.setTipoVigencia(entidadesTipoService.buscarTipoVigenciaPorId(compromiso.getTipoVigencia().getCodVigencia()));
            compromiso.setTipoCompromiso(entidadesTipoService.buscarTipoCompromisoPorCodCompromiso(compromiso.getTipoCompromiso().getCodCompromiso()));

            List<Usuario> usuario = usuarioService.buscarUsuariosPorCalceNombreCompleto(nombreOperador);
            if(usuario == null || usuario.size() == 0){
                String usuariosUsmempleoEmpresaParticipantes = "";
                if(idEmpresa!=0)model.addAttribute("empresa", empresaService.getEmpresaById(idEmpresa));
                model.addAttribute("idEmpresa", idEmpresa);
                model.addAttribute("compromiso", compromiso);
                model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);
                model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
                model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
                model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
                model.addAttribute("usuarioError", "El usuario operador ingresado no existe");
                return "/empresa/registrarCompromisoEmpresa";
            }
            Institucion institucion = institucionService.buscarInstitucionPorCodInstitucion(Short.parseShort("25"));
            compromiso.setInstitucion(institucion);
            compromiso.setOperadorACargo(usuario.get(0));
            Date date= null,date1 = null, date2= null;
            if(noAgendo){
                Calendar cal= Calendar.getInstance();
                cal.set(Calendar.YEAR, 1);
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                date = cal.getTime();
                compromiso.setFechaTermino(date);
            }
            else if(indefinido){
                Calendar cale= Calendar.getInstance();
                cale.set(Calendar.YEAR, 9999);
                cale.set(Calendar.MONTH, Calendar.JANUARY);
                cale.set(Calendar.DAY_OF_MONTH, 1);
                date1 = cale.getTime();
                compromiso.setFechaTermino(date1);
            }
            if(noAgendoSgte){
                Calendar cal1= Calendar.getInstance();
                cal1.set(Calendar.YEAR, 1);
                cal1.set(Calendar.MONTH, Calendar.JANUARY);
                cal1.set(Calendar.DAY_OF_MONTH, 2);
                date2 = cal1.getTime();
                compromiso.setFechaSiguienteCompromiso(date2);
            }
            if(compromiso.getSucursalEmpresa()==null){
                redirectAttributes.addFlashAttribute("flash.error", "La sucursal ingresada no es valida");
                return "redirect:/empresas/registrar-compromiso/"+sucursalEmpresaService.getSucursalEmpresaById(codSucursal).getEmpresa().getId();
            }
            compromiso.setRutUsuario(usuarioService.getCurrentUser().getRut());
            empresaService.agregarCompromisoEmpresa(compromiso);

            redirectAttributes.addFlashAttribute("flash.success", "El Compromiso con la Empresa se ha registrado exitosamente.");
            return "redirect:/empresa/"+sucursalEmpresaService.getSucursalEmpresaById(codSucursal).getEmpresa().getId();
        }
    }


    @RequestMapping(value = "/empresa/{idEmpresa}/modificar-compromiso/{idCompromiso}", method = RequestMethod.GET)
    public String modificarCompromisoEmpresa(@PathVariable Long idCompromiso, @PathVariable Long idEmpresa, Model model, RedirectAttributes redirectAttributes) {

        CompromisoEmpresa compromiso = empresaService.getCompromisoEmpresaPorId(idCompromiso);

        boolean noAgendo=false,indefinido=false,noAgendoSgte=false;
        String fechaTermino=compromiso.getFechaTermino().toString().split(" ")[0];
        if(fechaTermino.equals("0001-01-01")){
            compromiso.setFechaTermino(null);
            noAgendo=true;
        }
        if(fechaTermino.equals("9999-01-01")){
            compromiso.setFechaTermino(null);
            indefinido=true;
        }

        String time=compromiso.getFechaSiguienteCompromiso().toString().split(" ")[0];
        if(time.equals("0001-01-02")){
            compromiso.setFechaSiguienteCompromiso(null);
            noAgendoSgte=true;
        }
        model.addAttribute("noAgendo",noAgendo);
        model.addAttribute("indefinido",indefinido);
        model.addAttribute("noAgendoSgte",noAgendoSgte);

        cargarDatosParaModificarCompromiso(model);
        model.addAttribute("sucursalEmpresas", empresaService.getSucursalesEmpresa(idEmpresa));
        model.addAttribute("nombreOperador", compromiso.getOperadorACargo().getNombreCompleto());
        model.addAttribute("compromiso", compromiso);
        model.addAttribute("idEmpresa", idEmpresa);
        model.addAttribute("idCompromiso", idCompromiso);
        return "/empresa/modificarCompromisoEmpresa";
    }

    @RequestMapping(value = "/empresas/modificarCompromiso", method = RequestMethod.POST)
    public String modificarFichaCompromisoEmpresa(@Valid @ModelAttribute CompromisoEmpresa compromiso, BindingResult error,
                                                  @RequestParam Long id,@RequestParam Long idEmpresa,
                                                  @RequestParam("nombreOperador") String nombreOperador,
                                                  @RequestParam("checkNoAgendoInicio") boolean noAgendo,
                                                  @RequestParam("checkIndefinido") boolean indefinido,
                                                  @RequestParam("checkNoAgendoSgte") boolean noAgendoSgte,
                                                  RedirectAttributes redirectAttributes, Model model) {
        CompromisoEmpresa comp = empresaService.getCompromisoEmpresaPorId(id);

        Date date= null,date1 = null, date2= null;
        if(noAgendo){
            Calendar cal= Calendar.getInstance();
            cal.set(Calendar.YEAR, 1);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
            compromiso.setFechaTermino(date);
        }
        else if(indefinido){
            Calendar cale= Calendar.getInstance();
            cale.set(Calendar.YEAR, 9999);
            cale.set(Calendar.MONTH, Calendar.JANUARY);
            cale.set(Calendar.DAY_OF_MONTH, 1);
            date1 = cale.getTime();
            compromiso.setFechaTermino(date1);
        }
        if(noAgendoSgte){
            Calendar cal1= Calendar.getInstance();
            cal1.set(Calendar.YEAR, 1);
            cal1.set(Calendar.MONTH, Calendar.JANUARY);
            cal1.set(Calendar.DAY_OF_MONTH, 2);
            date2 = cal1.getTime();
            compromiso.setFechaSiguienteCompromiso(date2);
        }
        compromiso.setInstitucion(comp.getInstitucion());
        compromiso.setRutUsuario(usuarioService.getCurrentUser().getRut());
        compromiso.setFechaModificacion(new Date());
        List<Usuario> usuario = usuarioService.buscarUsuariosPorCalceNombreCompleto(nombreOperador);
        if(error.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.error", "Datos erroneos, favor revisar");
            return "redirect:/empresa/"+idEmpresa+"/modificar-compromiso/"+id;
        }
        if(usuario == null || usuario.size() == 0){
            /*compromiso.setTipoFormaPago(comp.getTipoFormaPago());
            compromiso.setTipoOportunidad(comp.getTipoOportunidad());
            compromiso.setTipoVigencia(comp.getTipoVigencia());
            model.addAttribute("tipoInteresOportunidad",entidadesTipoService.buscarTodosTipoInteresOportunidad());
            model.addAttribute("tipoOportunidadCompromiso",entidadesTipoService.buscarTodosTipoOportunidad());
            model.addAttribute("compromiso",compromiso);
            model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());*/
            redirectAttributes.addFlashAttribute("flash.error", "El usuario operador ingresado no existe");
            return "redirect:/empresa/"+compromiso.getSucursalEmpresa().getEmpresa().getId()+"/modificar-compromiso/"+compromiso.getId();
        }
        compromiso.setOperadorACargo(usuario.get(0));
        compromiso = empresaService.updateCompromisoEmpresa(compromiso);
        redirectAttributes.addFlashAttribute("flash.success", "El compromiso de la empresa se ha sido actualizado correctamente.");
        return "redirect:/empresa/"+compromiso.getSucursalEmpresa().getEmpresa().getId();
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
    @RequestMapping(value = "/empresas/buscarSucursalEmpresas")
    @JsonView(ResponseView.MainView.class)
    public @ResponseBody List<SucursalEmpresa> buscarUsuariosUsmempleoEmpresa(@RequestParam ("valorBuscado") String valorBuscado,
                                                              @RequestParam ("tipoBusqueda") String tipoBusqueda) {

        List<SucursalEmpresa> usuarioUsmempleoEmpresa = new ArrayList<SucursalEmpresa>();

        // caso que la busqueda sea por el calce de un nombre de usuario
        /*if ( tipoBusqueda.equals("usuario") ) {
            usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresaService.buscarUsuariosUsmempleoEmpresaPorCalceNombreUsuario(valorBuscado);
        }*/

        // caso que la busqueda sea según una empresa especifica
        if ( tipoBusqueda.equals("empresa") ) {

            // busqueda de una empresa que posea la misma razon social, nombre fantasia o sigla
            Empresa empresa = empresaService.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(valorBuscado);

            usuarioUsmempleoEmpresa = empresaService.getSucursalesEmpresa(empresa.getId());
        }

        return usuarioUsmempleoEmpresa;
    }

    @RequestMapping(value = "/empresas/registrar-comp", method = RequestMethod.GET)
    public String contactoConEmpresa(Model model) {

        CompromisoEmpresa compromiso = new CompromisoEmpresa();
        TipoVigencia vigencia= new TipoVigencia();
        TipoFormaPago formaPago = new TipoFormaPago();
        compromiso.setTipoVigencia(vigencia);
        compromiso.setTipoFormaPago(formaPago);
        cargarDatosParaRegistrarCompromiso(model);

        ContactoHistoricoEmpresa contactoHistoricoEmpresa = new ContactoHistoricoEmpresa();
        String usuariosUsmempleoEmpresaParticipantes = "";
        String nombreOperador = "";
        Long codSucursal= Long.valueOf(-1);
        String nombreUsuarioLogueado = usuarioService.getCurrentUser().getNombres()+" "+usuarioService.getCurrentUser().getApellidoPaterno()+" "+usuarioService.getCurrentUser().getApellidoMaterno();

        model.addAttribute("nombreOperador", nombreOperador);
        model.addAttribute("compromiso", compromiso);
        model.addAttribute("codSucursal", codSucursal);
        model.addAttribute("nombreUsuarioEncargado", nombreUsuarioLogueado);
        model.addAttribute("contactoHistoricoEmpresa", contactoHistoricoEmpresa);
        model.addAttribute("usuariosUsmempleoEmpresaParticipantes", usuariosUsmempleoEmpresaParticipantes);

        return "/empresa/registrarCompromisoEmpresa";
    }

    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de registrar compromiso.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author Felipe Mancilla S <felipe.mancilla.mancilla@alumnos.usm.cl>
     */
    private void cargarDatosParaRegistrarCompromiso(Model model) {
        model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
        model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
        model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
    }

    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de modificar compromiso.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author Felipe Mancilla S <felipe.mancilla.mancilla@alumnos.usm.cl>
     */
    private void cargarDatosParaModificarCompromiso(Model model) {
        model.addAttribute("tipoInteresOportunidad",entidadesTipoService.buscarTodosTipoInteresOportunidad());
        model.addAttribute("tipoCompromisos", entidadesTipoService.buscarTodosTipoCompromiso());
        model.addAttribute("tipoVigencias", entidadesTipoService.buscarTodosTiposVigencia());
        model.addAttribute("tipoFormaPagos", entidadesTipoService.listaTipoFormaPago());
    }




}
