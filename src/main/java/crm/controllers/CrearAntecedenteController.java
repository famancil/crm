package crm.controllers;

import crm.entities.*;
import crm.services.*;
import crm.validators.AntecedenteEducacionalValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Controlador que maneja el perfil de un {@link crm.entities.AntecedenteEducacional}.
 *
 * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
 */
@Controller
public class CrearAntecedenteController {
    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio utilizado para el manejo de las entidades tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de la entidad carrera
     */
    @Autowired
    private CarreraService carreraService;

    /**
     * Servicio utilizado para el manejo de la entidad institucion
     */
    @Autowired
    private InstitucionService institucionService;

    /**
     * Servicio utilizado para el manejo de la entidad colegio
     */
    @Autowired
    private ColegioService colegioService;

    /**
     * Servicio utilizado para el manejo de la entidad pais
     */
    @Autowired
    private GeograficoService geograficoService;


    /**
     * Servicio utilizado para el manejo de las entidades que son antecedentes
     */
    @Autowired
    private EntidadesAntecedenteService entidadesAntecedenteService;

    /**
     * Servicio utilizado para el manejo de la entidad CarreraInstitucion
     */
    @Autowired
    private CarreraInstitucionService carreraInstitucionService;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(PerfilUsuarioController.class);

    /**
     * InitBinder utilizado para setear el formato de fecha ingresada por formulario y para
     * setear el validador correspondiente de la clase Usuario
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder("antecedenteEducacional")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(new AntecedenteEducacionalValidator());
    }

    /**
    * Muestra el form para registrar un nuevo {@link crm.entities.AntecedenteEducacional}.
    *
    * @param model Modelo utilizado en la vista.
    * @param idUsuario del usuario que desea agregar su antecedente.
    *
    * @return Una vista con el form para el registro de un {@link crm.entities.AntecedenteEducacional}.
    *
    * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
    */
    @RequestMapping(value ="/antecedente-educacional/perfil/{idUsuario}/educacion-superior/agregar", method = RequestMethod.GET)
    public String AgregarAntecedente(Model model, @PathVariable Long idUsuario) {
        AntecedenteEducacional antecedente = new AntecedenteEducacional();
        model.addAttribute("antecedenteEducacional", antecedente);
        model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
        model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
        model.addAttribute("instituciones", institucionService.getInstituciones());
        model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
        model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
        List<Integer> anios = new ArrayList<>();
        for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
            anios.add(i);
        }
        model.addAttribute("anios", anios);
        return "/antecedentes/AgregarAntecedenteEducacionSuperior";
    }
    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * * @param antecedenteEducacional {@link crm.entities.AntecedenteEducacional}.
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.AntecedenteEducacional}.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @RequestMapping(value = "/antecedente-educacional/perfil/educacion-superior/agregar", method = RequestMethod.POST)
    public String registrarAntecedenteEducacional(@Valid @ModelAttribute AntecedenteEducacional antecedenteEducacional,
                                                  @RequestParam("idUsuario") Long idUsuario,
                                                  @RequestParam("codInstitucion") Short codInstitucion,
                                                  @RequestParam("codCarrera") Long codCarrera,
                                                  BindingResult error, RedirectAttributes redirectAttributes,
                                                  Model model){
        if(error.hasErrors()){
            model.addAttribute("antecedenteEducacional", antecedenteEducacional);
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
            model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
            model.addAttribute("instituciones", institucionService.getInstituciones());
            model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++) {
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/AgregarAntecedenteEducacionSuperior";
        }
        if(antecedenteEducacional.getAnioEgreso()<antecedenteEducacional.getAnioIngreso() || antecedenteEducacional.getAnioTitulo() < antecedenteEducacional.getAnioIngreso()){
            model.addAttribute("antecedenteEducacional", antecedenteEducacional);
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
            model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
            model.addAttribute("instituciones", institucionService.getInstituciones());
            model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++) {
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/AgregarAntecedenteEducacionSuperior";
        }
        if(antecedenteEducacional.getAnioTitulo() < antecedenteEducacional.getAnioEgreso()){
            model.addAttribute("antecedenteEducacional", antecedenteEducacional);
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
            model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
            model.addAttribute("instituciones", institucionService.getInstituciones());
            model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++) {
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/AgregarAntecedenteEducacionSuperior";
        }
        Date actual = new java.util.Date();

        antecedenteEducacional.setCarrera(carreraService.buscarCarreraPorCodCarrera(codCarrera));
        antecedenteEducacional.setInstitucion(institucionService.buscarInstitucionPorCodInstitucion(codInstitucion));
        antecedenteEducacional.setTipoEstudio(entidadesTipoService.buscarTipoEstudioPorCodEstudio(antecedenteEducacional.getTipoEstudio().getCodEstudio()));
        antecedenteEducacional.setTipoEstadoEstudio(entidadesTipoService.buscarTipoEstadoEstudioPorCodEstadoEstudio(antecedenteEducacional.getTipoEstadoEstudio().getCodEstadoEstudio()));
        antecedenteEducacional.setFechaModificacion(actual);
        antecedenteEducacional.setUsuario(usuarioService.getUsuarioById(idUsuario));
        antecedenteEducacional.setRutUsuario(usuarioService.getCurrentUser().getRut());
        AntecedenteEducacional antecedente = entidadesAntecedenteService.guardarAntecedenteEducacional(antecedenteEducacional);
        redirectAttributes.addFlashAttribute("flash.success", "El antecedente ha sido agregado correctamente.");
        return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-superior";
    }

    /**
     * Muestra el form para registrar un nuevo {@link crm.entities.AntecedenteColegio}.
     *
     * @param model Modelo utilizado en la vista.
     * @param idUsuario del usuario que desea agregar su antecedente de colegio.
     *
     * @return Una vista con el form para el registro de un {@link crm.entities.AntecedenteColegio}.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @RequestMapping(value ="/antecedente-educacional/perfil/{idUsuario}/educacion-escolar/agregar", method = RequestMethod.GET)
    public String AgregarAntecedenteEscolar(Model model, @PathVariable Long idUsuario) {
        AntecedenteColegio antecedente = new AntecedenteColegio();
        model.addAttribute("antecedenteColegio", antecedente);
        model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosEscolares());
        model.addAttribute("colegios", colegioService.getColegios());
        model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
        List<Integer> anios = new ArrayList<>();
        for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
            anios.add(i);
        }
        model.addAttribute("anios", anios);
        return "/antecedentes/AgregarAntecedenteEducacionEscolar";
    }
    /**
     * Muestra el retorno de una vez que se envia el form por el metodo POST.
     *
     * * @param antecedenteEducacional {@link crm.entities.AntecedenteEducacional}.
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna una vista con el resultado de la operacion ingresar
     * {@link crm.entities.AntecedenteEducacional}.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @RequestMapping(value = "/antecedente-educacional/perfil/educacion-escolar/agregar", method = RequestMethod.POST)
    public String registrarAntecedenteEducacionalEscolar(@Valid @ModelAttribute AntecedenteColegio antecedenteColegio,
                                                  @RequestParam("idUsuario") Long idUsuario,
                                                  @RequestParam("codigo") Short codigo,
                                                  BindingResult error, RedirectAttributes redirectAttributes,
                                                  Model model){
        if(error.hasErrors()){
            model.addAttribute("antecedenteColegio", antecedenteColegio);
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosEscolares());
            model.addAttribute("colegios", colegioService.getColegios());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++) {
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/AgregarAntecedenteEducacionSuperior";
        }
        if(antecedenteColegio.getAnoEgreso()<antecedenteColegio.getAnoIngreso()){
            model.addAttribute("antecedenteColegio", antecedenteColegio);
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosEscolares());
            model.addAttribute("colegios", colegioService.getColegios());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++) {
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/AgregarAntecedenteEducacionSuperior";
        }
        Date actual = new java.util.Date();

        antecedenteColegio.setColegio(colegioService.getColegioByCodigo(codigo.toString()));
        antecedenteColegio.setTipoEstudio(entidadesTipoService.buscarTipoEstudioPorCodEstudio(antecedenteColegio.getTipoEstudio().getCodEstudio()));
        antecedenteColegio.setFechaModificacion(actual);
        antecedenteColegio.setUsuario(usuarioService.getUsuarioById(idUsuario));
        antecedenteColegio.setRutUsuario(usuarioService.getCurrentUser().getRut());
        AntecedenteColegio antecedente = entidadesAntecedenteService.guardarAntecedenteColegio(antecedenteColegio);
        redirectAttributes.addFlashAttribute("flash.success", "El antecedente ha sido agregado correctamente.");
        return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-escolar";
    }
}
