package crm.controllers;

import crm.entities.Comuna;
import crm.entities.Empresa;
import crm.entities.SucursalEmpresa;
import crm.entities.Pais;
import crm.repositories.ComunaRepository;
import crm.services.*;
import crm.validators.SucursalEmpresaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador que maneja la creacion de una {@link crm.entities.SucursalEmpresa}
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Controller
public class CrearSucursalEmpresaController {

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo de una entidad SucursalEmpresa
     */
    @Autowired
    private SucursalEmpresaService sucursalEmpresaService;

    /**
     * Servicio utilizado para el manejo de una entidad empresa
     */
    @Autowired
    private EmpresaService empresaService;


    /**
     * Servicio utilizado para el manejo de una entidad usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * InitBinder utilizado para setear el validador correspondiente de la clase SucursalEmpresa.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void crearSucursalEmpresaBinder(WebDataBinder binder) {
        //Setea el validador de sucursal de empresa en binder desde la clase SucursalEmpresaValidator
        binder.setValidator(new SucursalEmpresaValidator());
    }

    /**
     * Muestra el form para registrar una nueva {@link crm.entities.SucursalEmpresa}.
     *
     * @param model Modelo utilizado en la vista.
     * @param idEmpresa id de la empresa dueña de la sucursal a ingresar.
     *
     * @return Una vista con el form para el registro de una {@link crm.entities.SucursalEmpresa}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{idEmpresa}/sucursal/registrar", method = RequestMethod.GET)
    public String registrarSucursal(Model model, @PathVariable Long idEmpresa){
        SucursalEmpresa s = new SucursalEmpresa();
        s.setEmpresa(empresaService.getEmpresaById(idEmpresa));
        model.addAttribute("sucursal", s);
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
        return "/empresa/sucursalEmpresa/registrarSucursal";
    }

    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * @param sucursal {@link crm.entities.SucursalEmpresa}
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.SucursalEmpresa}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/sucursal/registrar", method = RequestMethod.POST)
    public String registrarSucursalEmpresa(@Valid @ModelAttribute SucursalEmpresa sucursal, BindingResult error, RedirectAttributes redirectAttributes, Model model, @RequestParam("idEmpresa") Long idEmpresa){
        if(error.hasErrors()){
            sucursal.setEmpresa(empresaService.getEmpresaById(idEmpresa));
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
            return "/empresa/sucursalEmpresa/registrarSucursal";
        }
        Date actual = new java.util.Date();
        Empresa empresa = empresaService.getEmpresaById(idEmpresa);
        if(sucursal.getComuna()==null) sucursal.setComuna(sucursalEmpresaService.setComunaSucursalByCodComuna((short)0));
        sucursal.setEmpresa(empresa);
        sucursal.setFechaModificacion(actual);
        sucursal.setRutUsuario(usuarioService.getCurrentUser().getRut());
        SucursalEmpresa s = sucursalEmpresaService.guardarSucursal(sucursal);
        redirectAttributes.addFlashAttribute("flash.success", "La sucursal ha sido agregada correctamente.");
        return "redirect:/empresa/" + s.getEmpresa().getId();
    }
}
