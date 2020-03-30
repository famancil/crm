package crm.controllers;

import crm.entities.Colegio;
import crm.services.*;
import crm.validators.ColegioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * Controlador que maneja la mezcla (fusion) de {@link crm.entities.Colegio}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class MezclarColegioController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Colegio}.
     */
    @Autowired
    private ColegioService colegioService;

    /**
     * Servicio relacionado a la entidades Tipo (Ej: TipoVigencia, TipoGrado, etc).
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Validador de la entidad {@link Colegio} para el caso de modificar colegio
     */
    @Autowired
    private ColegioValidator colegioValidator;






    /**
     * InitBinder utilizado para setear el validador correspondiente a la clase {@link Colegio}
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(colegioValidator);
    }






    /**
     * Despliega la vista para realizar la mezcla (fusión) de {@link crm.entities.Colegio}
     *
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de mezclarColegio
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/colegio/buscarMezcladorColegio", method = RequestMethod.GET)
    public String buscarMezcladorColegio(Model model) {
        model.addAttribute("colegio", new Colegio());

        return "/colegio/mezclarColegio";
    }




    /**
     * Recepción de los datos desde el formulario de la vista que busca una {@link crm.entities.Colegio},
     * para la seleccion de los {@link crm.entities.Colegio} que serán mezcladas
     *
     * @param nombreOficialBuscar Nombre de los {@link crm.entities.Colegio} a buscar
     * @param model Modelo utilizado en la vista.
     *
     * @return Retorna la vista de mezclarColegio con los colegios que calcen en la busqueda
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/colegio/buscarMezcladorColegio", method = RequestMethod.POST)
    public String buscarMezcladorColegio( @RequestParam ("nombreOficialBuscar") String nombreOficialBuscar,
                                          Model model) {

        List<Colegio> colegios = colegioService.buscarColegioPorCalceNombre(nombreOficialBuscar);

        // Carga de datos a la vista
        model.addAttribute("colegios", colegios);
        model.addAttribute("nombreOficialBuscar", nombreOficialBuscar);
        model.addAttribute("mostrar", "mostrar");

        cargarDatosParaVista(new Colegio(), model);

        return "/colegio/mezclarColegio";
    }




    /**
     * Recepción de los datos desde la vista, con los id de los {@link crm.entities.Colegio} a mezclar, y atributos que
     * se desea que tenga eñ {@link crm.entities.Colegio} que fusionará a todas
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @RequestMapping(value = "/colegio/mezclar", method = RequestMethod.POST)
    public String mezclarColegio( @RequestParam(value = "nombreOficialBuscar") String nombreOficialBuscar,
                                  @RequestParam(value = "idsColegiosMezclar", required = false) String idsColegiosMezclar,
                                  @Valid @ModelAttribute(value="colegio") Colegio colegio,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {


        String[] idsColegios = new String[0];

        // id de carreras seleccionadas en los checkbox de mezclar
        if (idsColegiosMezclar != null) {
            idsColegios = idsColegiosMezclar.split(",");
        }

        Colegio colegioPrevio = colegioService.buscarColegioPorNombreEspecifico(colegio.getNombreOficial());

        // Revisa si vista posee errores del formulario, o que no se seleccionó carrera o que ya existe una carrera con el mismo nombre
        if(error.hasErrors() || idsColegiosMezclar == null || colegioPrevio != null ){

            List<Colegio> colegios = colegioService.buscarColegioPorCalceNombre(nombreOficialBuscar);

            // Carga de datos a la vista
            model.addAttribute("colegios", colegios);
            model.addAttribute("nombreOficialBuscar", nombreOficialBuscar);
            model.addAttribute("idsColegios", idsColegios);
            model.addAttribute("mostrar", "mostrar");

            if (idsColegiosMezclar == null) {
                model.addAttribute("errorCheckbox", "Debe seleccionar colegios a mezclar");
            }

            if (colegioPrevio != null) {
                model.addAttribute("errorNombreOficial", "El nombre del colegio ingresado ya se encuentra previamente registrado");
            }

            cargarDatosParaVista(colegio, model);

            return "/colegio/mezclarColegio";
        }

        colegioService.mezclarColegio(colegio, idsColegios);

        redirectAttributes.addFlashAttribute("flash.success", "La carrera sido mezclada exitosamente.");

        return "redirect:/colegio/buscarMezcladorColegio";
    }






    /**
     * Despliega la vista para modificar atributos de una {@link Colegio} en parti

    /**
     * Carga al {@link Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link Colegio}
     *
     * @param colegio {@link Colegio} a utilizar en la vista.
     * @param model {@link Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVista(Colegio colegio, Model model) {
        model.addAttribute("colegio", colegio);
        model.addAttribute("tiposVigencia", entidadesTipoService.buscarTodosTiposVigencia());
    }

}
