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
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Controller
public class PerfilAntecedenteEducacionalController {

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
     * Servicio utilizado para el manejo de la entidad pais
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo de la entidad colegio
     */
    @Autowired
    private ColegioService colegioService;

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
     * Retorna la vista de los {@link crm.entities.AntecedenteEducacional} de educacion superior
     * de un {@link crm.entities.Usuario}
     *
     * @param model Modelo utilizado por la vista.
     * @param id id del {@link crm.entities.Usuario} al cual se le mostraran los antecedentes educacionales
     *
     * @return Retorna una vista
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value ="/antecedente-educacional/perfil/{id}/educacion-superior")
    public String verPerfilAntecedenteEducacionSuperior(Model model, @PathVariable Long id) {
        Usuario perfil = usuarioService.getUsuarioById(id);

        model.addAttribute("perfil", perfil);
        model.addAttribute("idPais", 56);
        model.addAttribute("codInstitucion", 25);
        model.addAttribute("antecedentesUsm", usuarioService.getAntecedentesEducacionalesUsm(id));
        model.addAttribute("antecedentes", usuarioService.getAntecedentesEducacionales(id));
        model.addAttribute("nombre", perfil.getNombreCompleto());
        model.addAttribute("estudios", entidadesTipoService.getEstudios());
        model.addAttribute("estados", entidadesTipoService.getEstadoEstudios());
        model.addAttribute("antecedente", new AntecedenteEducacional());
        model.addAttribute("dias", usuarioService.getDiasRestantes(perfil.getFechaNacimiento()));
        return "/antecedentes/perfilAntecedenteEducacionSuperior";
    }

    /**
     * Retorna la vista de los {@link crm.entities.AntecedenteEducacional} de educacion escolar
     * de un {@link crm.entities.Usuario}
     *
     * @param model Modelo utilizado por la vista.
     * @param id id del {@link crm.entities.Usuario} al cual se le mostraran los antecedentes educacionales
     *
     * @return Retorna una vista
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value ="/antecedente-educacional/perfil/{id}/educacion-escolar")
    public String verPerfilAntecedenteEducacionEscolar(Model model, @PathVariable Long id) {
        Usuario perfil = usuarioService.getUsuarioById(id);

        model.addAttribute("perfil", perfil);
        model.addAttribute("antecedenteColegios", usuarioService.getAntecedentesColegio(id));
        model.addAttribute("nombre", perfil.getNombreCompleto());
        model.addAttribute("estudios", entidadesTipoService.getEstudios());
        model.addAttribute("estados", entidadesTipoService.getEstadoEstudios());
        model.addAttribute("dias", usuarioService.getDiasRestantes(perfil.getFechaNacimiento()));
        return "/antecedentes/perfilAntecedenteEducacionEscolar";
    }

    /**
     * Accion que despliega la interfaz que posibilita la edicion de un antecedente educacional de un usuario
     *
     * @param model Modelo utilizado en la vista.
     * @param idUsuario Id del usuario propietario del antecedente a modificar
     * @param idAntecedente Id del antecedente a modificar.
     *
     * @return Retorna la vista de modificar antecedente educacional.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
    **/
    @RequestMapping(value = "/antecedente-educacional/perfil/{idUsuario}/educacion-superior/{idAntecedente}/modificar", method = RequestMethod.GET)
    public String mostrarModificarAntecedenteEducacionSuperior(Model model, @PathVariable Long idUsuario, @PathVariable Long idAntecedente, RedirectAttributes redirectAttributes) {
        AntecedenteEducacional antecedente = entidadesAntecedenteService.getAntecedenteEducacionalById(idAntecedente);
        if(antecedente.getUsuario().getId().compareTo(idUsuario)!=0){
            redirectAttributes.addFlashAttribute("flash.error", "El antecedente no pertenece al usuario ingresado");
            return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-superior";
        }
        model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteEducacionalById(idAntecedente).getPais().getCodigo());
        model.addAttribute("codInstitucion", entidadesAntecedenteService.getAntecedenteEducacionalById(idAntecedente).getInstitucion().getCodInstitucion());
        model.addAttribute("codCarrera", entidadesAntecedenteService.getAntecedenteEducacionalById(idAntecedente).getCarrera().getCodCarrera());
        model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
        model.addAttribute("antecedenteEducacional", entidadesAntecedenteService.getAntecedenteEducacionalById(idAntecedente));
        model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
        model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
        model.addAttribute("instituciones", institucionService.getInstituciones());
        model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
        List<Integer> anios = new ArrayList<>();
        for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
            anios.add(i);
        }
        model.addAttribute("anios", anios);
        return "/antecedentes/modificarAntecedenteEducacionSuperior";
    }

    /**
     * Modifica un {@link crm.entities.AntecedenteEducacional}
     *
     * @param antecedenteEducacional {@link crm.entities.AntecedenteEducacional}.
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna una vista con el resultado de la operacion modificar
     * {@link crm.entities.AntecedenteEducacional}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/antecedente-ducacional/educacion-superior/modificar", method = RequestMethod.POST)
    public String modificarAntecedenteEducacional(@Valid @ModelAttribute("antecedenteEducacional") AntecedenteEducacional antecedenteEducacional,
                                                  @RequestParam("id") Long id,
                                                  @RequestParam("codInstitucion") Short codInstitucion,
                                                  @RequestParam("codCarrera") Long codCarrera,
                                                  @RequestParam("idUsuario") Long idUsuario,
                                                  BindingResult error,
                                                  RedirectAttributes redirectAttributes,
                                                  Model model){
        if(error.hasErrors()){
            model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getPais().getCodigo());
            model.addAttribute("codInstitucion", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getInstitucion().getCodInstitucion());
            model.addAttribute("codCarrera", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getCarrera().getCodCarrera());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            model.addAttribute("antecedenteEducacional", entidadesAntecedenteService.getAntecedenteEducacionalById(id));
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
            model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
            model.addAttribute("instituciones", institucionService.getInstituciones());
            model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/modificarAntecedenteEducacionSuperior";

        }
        if(antecedenteEducacional.getAnioEgreso()<antecedenteEducacional.getAnioIngreso() || antecedenteEducacional.getAnioTitulo() < antecedenteEducacional.getAnioIngreso()){
            model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getPais().getCodigo());
            model.addAttribute("codInstitucion", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getInstitucion().getCodInstitucion());
            model.addAttribute("codCarrera", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getCarrera().getCodCarrera());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            model.addAttribute("antecedenteEducacional", entidadesAntecedenteService.getAntecedenteEducacionalById(id));
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
            model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
            model.addAttribute("instituciones", institucionService.getInstituciones());
            model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            model.addAttribute("anioIngresoError", "El año de ingreso no puede ser superior al año de egreso o de titulación.");
            return "/antecedentes/modificarAntecedenteEducacionSuperior";
        }
        if(antecedenteEducacional.getAnioTitulo() < antecedenteEducacional.getAnioEgreso()){
            model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getPais().getCodigo());
            model.addAttribute("codInstitucion", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getInstitucion().getCodInstitucion());
            model.addAttribute("codCarrera", entidadesAntecedenteService.getAntecedenteEducacionalById(id).getCarrera().getCodCarrera());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            model.addAttribute("antecedenteEducacional", entidadesAntecedenteService.getAntecedenteEducacionalById(id));
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosUniversitarios());
            model.addAttribute("estadoEstudio", entidadesTipoService.getEstadoEstudios());
            model.addAttribute("instituciones", institucionService.getInstituciones());
            model.addAttribute("carreras", carreraService.buscarTodasCarrerasOrdenNombreCarreraAsc());
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
                anios.add(i);
            }
            model.addAttribute("anios", anios);

            model.addAttribute("anioEgresoError", "El año de egreso no puede ser superior al año de titulación.");
            return "/antecedentes/modificarAntecedenteEducacionSuperior";
        }
        Date actual = new java.util.Date();
        antecedenteEducacional.setCarrera(carreraService.buscarCarreraPorCodCarrera(codCarrera));
        antecedenteEducacional.setInstitucion(institucionService.buscarInstitucionPorCodInstitucion(codInstitucion));
        antecedenteEducacional.setTipoEstudio(entidadesTipoService.buscarTipoEstudioPorCodEstudio(antecedenteEducacional.getTipoEstudio().getCodEstudio()));
        antecedenteEducacional.setTipoEstadoEstudio(entidadesTipoService.buscarTipoEstadoEstudioPorCodEstadoEstudio(antecedenteEducacional.getTipoEstadoEstudio().getCodEstadoEstudio()));
        antecedenteEducacional.setUsuario(usuarioService.getUsuarioById(idUsuario));
        antecedenteEducacional.setFechaModificacion(actual);
        antecedenteEducacional.setRutUsuario(usuarioService.getCurrentUser().getRut());
        entidadesAntecedenteService.guardarAntecedenteEducacional(antecedenteEducacional);

        redirectAttributes.addFlashAttribute("flash.success", "El antecedente educacional ha sido modificado exitosamente.");
        return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-superior";
    }


    /**
     * Elimina un {@link crm.entities.AntecedenteEducacional} de la base de datos.
     *
     * @param idAntecedente Id del {@link crm.entities.AntecedenteEducacional} a eliminar.
     *
     * @return Retorna una vista de exito.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @RequestMapping(value = "/antecedente-educacional/perfil/{idUsuario}/educacion-superior/{idAntecedente}/eliminar", method = RequestMethod.POST)
    public String eliminarAntecedenteEducacional(@PathVariable("idAntecedente") Long idAntecedente,@PathVariable("idUsuario") Long idUsuario,RedirectAttributes redirectAttributes){
        entidadesAntecedenteService.eliminarAntecedenteEducacional(idAntecedente);
        redirectAttributes.addFlashAttribute("flash.success", "El antecedente ha sido eliminada correctamente.");
        return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-superior";
    }


    /**
     * Accion que despliega la interfaz que posibilita la edicion de un antecedente educacional escolar de un usuario
     *
     * @param model Modelo utilizado en la vista.
     * @param idUsuario Id del usuario propietario del antecedente a modificar
     * @param idAntecedente Id del antecedente a modificar.
     *
     * @return Retorna la vista de modificar antecedente educacional.
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     **/
    @RequestMapping(value = "/antecedente-educacional/perfil/{idUsuario}/educacion-escolar/{idAntecedente}/modificar", method = RequestMethod.GET)
    public String mostrarModificarAntecedenteEducacionEscolar(Model model, @PathVariable Long idUsuario, @PathVariable Long idAntecedente, RedirectAttributes redirectAttributes) {
        AntecedenteColegio antecedente = entidadesAntecedenteService.getAntecedenteColegioById(idAntecedente);
        if(antecedente.getUsuario().getId().compareTo(idUsuario)!=0){
            redirectAttributes.addFlashAttribute("flash.error", "El antecedente no pertenece al usuario ingresado");
            return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-superior";
        }
        model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteColegioById(idAntecedente).getColegio().getPais().getCodigo());
        model.addAttribute("codigo", entidadesAntecedenteService.getAntecedenteColegioById(idAntecedente).getColegio().getCodigo());
        model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
        model.addAttribute("antecedenteColegio", entidadesAntecedenteService.getAntecedenteColegioById(idAntecedente));
        model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosEscolares());
        List<Integer> anios = new ArrayList<>();
        for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
            anios.add(i);
        }
        model.addAttribute("anios", anios);
        return "/antecedentes/modificarAntecedenteEducacionEscolar";
    }

    /**
     * Modifica un {@link crm.entities.AntecedenteColegio}
     *
     * @param antecedenteColegio {@link crm.entities.AntecedenteColegio}.
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param redirectAttributes Atributos para cuando se redirige la pagina.
     * @param model Modelo utilizado por la vista.
     *
     * @return Retorna una vista con el resultado de la operacion modificar
     * {@link crm.entities.AntecedenteColegio}.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @RequestMapping(value = "/antecedente-educacional/educacion-escolar/modificar", method = RequestMethod.POST)
    public String modificarAntecedenteEducacionEscolar(@Valid @ModelAttribute("antecedenteColegio") AntecedenteColegio antecedenteColegio,
                                                  @RequestParam("id") Long id,
                                                  @RequestParam("idUsuario") Long idUsuario,
                                                  @RequestParam("codigo") Short codigo,
                                                  BindingResult error,
                                                  RedirectAttributes redirectAttributes,
                                                  Model model){
        if(error.hasErrors()){
            model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteColegioById(id).getColegio().getPais().getCodigo());
            model.addAttribute("codigo", entidadesAntecedenteService.getAntecedenteColegioById(id).getColegio().getCodigo());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            model.addAttribute("antecedenteColegio", entidadesAntecedenteService.getAntecedenteColegioById(id));
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosEscolares());
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/modificarAntecedenteEducacionEscolar";

        }
        if(antecedenteColegio.getAnoEgreso()<antecedenteColegio.getAnoIngreso()){
            model.addAttribute("idPais", entidadesAntecedenteService.getAntecedenteColegioById(id).getColegio().getPais().getCodigo());
            model.addAttribute("codigo", entidadesAntecedenteService.getAntecedenteColegioById(id).getColegio().getCodigo());
            model.addAttribute("perfil", usuarioService.getUsuarioById(idUsuario));
            model.addAttribute("antecedenteColegio", entidadesAntecedenteService.getAntecedenteColegioById(id));
            model.addAttribute("tipoEstudio", entidadesTipoService.getEstudiosEscolares());
            List<Integer> anios = new ArrayList<>();
            for(int i=1920; i <= Calendar.getInstance().get(Calendar.YEAR);i++){
                anios.add(i);
            }
            model.addAttribute("anios", anios);
            return "/antecedentes/modificarAntecedenteEducacionEscolar";
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
    /**
     * Elimina un {@link crm.entities.AntecedenteColegio} de la base de datos.
     *
     * @param idAntecedente Id del {@link crm.entities.AntecedenteColegio} a eliminar.
     *
     * @return Retorna una vista de exito.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @RequestMapping(value = "/antecedente-educacional/perfil/{idUsuario}/educacion-escolar/{idAntecedente}/eliminar", method = RequestMethod.POST)
    public String eliminarAntecedenteEducacionalEscolar(@PathVariable("idAntecedente") Long idAntecedente,@PathVariable("idUsuario") Long idUsuario,RedirectAttributes redirectAttributes){
        entidadesAntecedenteService.eliminarAntecedenteColegio(idAntecedente);
        redirectAttributes.addFlashAttribute("flash.success", "El antecedente ha sido eliminada correctamente.");
        return "redirect:/antecedente-educacional/perfil/"+idUsuario+"/educacion-escolar";
    }



}
