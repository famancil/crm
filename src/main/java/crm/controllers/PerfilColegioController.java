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
import java.util.Date;

/**
 * Controlador que maneja el perfil de una {@link crm.entities.Colegio}
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class PerfilColegioController {

    /**
     * Servicio utilizado para el manejo de una institucion y sus entidades asociadas
     */
    @Autowired
    InstitucionService institucionService;

    /**
     * Servicio utilizado para el manejo de las entidades relacionadas con la localizacion geografica
     */
    @Autowired
    GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo.
     */
    @Autowired
    EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de la entidad colegio y sus entidades relacionadas.
     */
    @Autowired
    ColegioService colegioService;

    /**
     * InitBinder utilizado para setear el validador correspondiente a {@link crm.entities.Colegio}.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @InitBinder
    public void initBinder (WebDataBinder binder){
        binder.setValidator(new ColegioValidator());
    }

    /**
     * Muestra el perfil de una {@link crm.entities.Colegio}.
     *
     * @param model Modelo utilizado por la vista.
     * @param idColegio id del {@link crm.entities.Colegio} del cual se desea ver el perfil.
     *
     * @return Retorna una vista con el perfil del colegio.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio/perfil/{idColegio}")
    public String verPerfilColegio(Model model, @PathVariable String idColegio){

        model.addAttribute("colegio", colegioService.getColegioByCodigo(idColegio));

        return "/colegio/perfilColegio";
    }




    /**
     * Metodo usado para acceder a la vista modificarColegio desde el perfil de un colegio.
     *
     * @param idColegio Objeto {@link crm.entities.Colegio}
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna la vista modificarColegio con el form para buscar una
     * {@link crm.entities.Colegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio/modificar/{idColegio}", method = RequestMethod.GET)
    public String mostraModificarColegio(@PathVariable("idColegio") String idColegio,
                                         Model model){

        Colegio colegio = colegioService.getColegioByCodigo(idColegio);

        cargarDatosParaVistaColegio(colegio, model);

        return "/colegio/modificarColegio";
    }




    /**
     * Toma el formulario desde la vista modificarColegio, actualiza la base de datos con los valores
     * ingresados y retorna a la misma vista.
     *
     * @param colegio Objeto {@link crm.entities.Colegio}
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna la vista modificarInstitucion con los resultados de la busqueda de una
     * {@link crm.entities.Colegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/colegio/modificar", method = RequestMethod.POST)
    public String modificarColegio(@Valid @ModelAttribute Colegio colegio, BindingResult error,
                                   RedirectAttributes redirectAttributes, Model model){

        if (error.hasErrors()){
            cargarDatosParaVistaColegio(colegio, model);

            return "/colegio/modificarColegio";
        }

        // caso en que ya exista en la BD un nombre de colegio identico al que
        // se quiere ingresar y distinto al que ya estaba registrado antes de modificar
        Colegio colegioYaRegistradaMismoNombre = colegioService.buscarColegioPorNombreEspecifico(colegio.getNombreOficial());

        if ( colegioYaRegistradaMismoNombre != null && !colegioYaRegistradaMismoNombre.getCodigo().equals( colegioYaRegistradaMismoNombre.getCodigo() )) {

            String errorNombreColegio = "El nombre del colegio ingresado ya se encuentra previamente registrado";
            model.addAttribute("errorNombreColegio", errorNombreColegio);

            // Carga de datos a la vista
            cargarDatosParaVistaColegio(colegio, model);

            return "/colegio/modificarColegio";
        }

        // Si no posee errores
        colegioService.modificarColegio(colegio);

        redirectAttributes.addFlashAttribute("flash.success", "El colegio ha sido modificada correctamente.");

        return "redirect:/colegio";
    }





    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vistas relacionadas con
     * {@link crm.entities.Carrera}
     *
     * @param colegio {@link crm.entities.Colegio} a utilizar en la vista.
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     *
     * @author <hector.calquin@alumnos.usm.cl>
     */
    private void cargarDatosParaVistaColegio(Colegio colegio, Model model) {
        model.addAttribute("colegio", colegio);
        model.addAttribute("paises", geograficoService.getPaises());
        model.addAttribute("comunas", geograficoService.getComunas());
        model.addAttribute("vigencias", entidadesTipoService.buscarTodosTiposVigencia());
    }
}
