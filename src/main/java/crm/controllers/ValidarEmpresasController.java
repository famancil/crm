package crm.controllers;

import java.beans.PropertyEditorSupport;
import java.io.*;
import java.util.Date;
import java.util.List;

import crm.entities.ActividadExalumno;
import crm.entities.Empresa;
import crm.entities.TipoVigencia;
import crm.services.EmailService;
import crm.services.EmpresaService;
import crm.services.EntidadesTipoService;
import crm.utils.EncodingUtil;
import crm.validators.EmpresaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Controlador encargado de gestionar la logica de validacion de empresas y sucursales.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */

@Controller
public class ValidarEmpresasController {
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

    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;

    /**
     * Entidad que permite la validacion de un usuario
     */
    @Autowired
    private EmpresaValidator empresaValidator;


    /**
     * InitBinder utilizado para setear el validador correspondiente de la clase RegistrarEmpresaForm.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void validarEmpresaBinder(WebDataBinder binder) {
        //Setea el validador de empresa en binder desde la clase EmpresaValidator
        binder.setValidator(empresaValidator);
    }

    /**
     * Accion que despliega la interfaz que posibilita la edicion del perfil de una
     * {@link crm.entities.Empresa} para completar sus datos.
     *
     * @param model Modelo utilizado en la vista.
     * @param id Id de la {@link crm.entities.Empresa} a modificar.
     * @param from Indica si la empresa fue ingresada por un alumno o por un publicador
     *
     * @return Retorna la vista de completar datos de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/{id}/{from}/completar-datos", method = RequestMethod.GET)
    public String mostrarCompletarDatosEmpresaIngresadaPorAlumno(Model model, @PathVariable("id") Long id, @PathVariable("from") String from) {
        model.addAttribute("empresa", empresaService.getEmpresaById(id));
        model.addAttribute("from", from);
        cargarDatosParaModificarEmpresa(model);
        return "/empresa/completarDatosEmpresa";
    }

    /**
     * Toma el formulario desde la vista empresa/{id}/completar-datos, actualiza la base de datos con los valores
     * ingresados y retorna a la vista de validar empresas.
     *
     * @param empresa {@link crm.entities.Empresa} a modificar.
     * @param error Errores presentes en el formulario (si es que existen).
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista validarEmpresas
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/empresa/completar-datos", method = RequestMethod.POST)
    public String completarDatosEmpresa(@ModelAttribute("empresa") @Valid Empresa empresa,
                                        BindingResult error,
                                        @RequestParam("from") String from,
                                        RedirectAttributes redirectAttributes,
                                        Model model){
        if(error.hasErrors()) {
            model.addAttribute("empresa", empresa);
            cargarDatosParaModificarEmpresa(model);
            return "/empresa/completarDatosEmpresa";
        }else {
            empresa.setVigencia(new TipoVigencia(TipoVigencia.ID_VIGENTE,TipoVigencia.TIPO_VIGENTE));
            empresaService.saveEmpresa(empresa);
            redirectAttributes.addFlashAttribute("flash.success", "La empresa ha sido validada correctamente.");
            if(from.compareTo("por-alumno") == 0) return "redirect:/busquedas/empresas/por-validar/alumnos/";
            else return "redirect:/busquedas/empresas/por-validar/publicadores/";
        }
    }

    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista de completar datos de una empresa.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    private void cargarDatosParaModificarEmpresa(Model model) {
        model.addAttribute("paises", empresaService.cargarPaises());
        model.addAttribute("facturaciones", empresaService.cargarFacturaciones());
        model.addAttribute("sectores", empresaService.cargarSectores());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
    }

    /**
     * Metodo que obtiene los indices de inicio, final y actual para la pagin}acion de una busqueda en particular.
     *
     * @param model Modelo donde se cargaran los datos de la paginacion.
     * @param page Resultados de busqueda paginados para una entidad en particular.
     */
    private void generatePagination(Model model, Page page) {
        int actual = page.getNumber() + 1;
        int inicio = Math.max(1, actual - 5);
        int fin = Math.min(inicio + 10, page.getTotalPages());
        model.addAttribute("beginIndex", inicio);
        model.addAttribute("endIndex", fin);
        model.addAttribute("currentIndex", actual);
    }
}
