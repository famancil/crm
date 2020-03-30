package crm.controllers;


import crm.entities.Comuna;
import crm.entities.Pais;
import crm.entities.TipoEstadoCivil;
import crm.entities.Usuario;
import crm.services.EntidadesTipoService;
import crm.services.GeograficoService;
import crm.services.UsuarioService;
import crm.validators.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Controlador que contiene los metodos para la realizacion de la mezcla de {@link crm.entities.Usuario}
 * repetidos
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class MezcladorUsuarioController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio relacionado con entidades de caracter geografico como {@link crm.entities.Pais},
     * {@link crm.entities.Provincia}, entre otras
     */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio relacionado a la entidades "Tipo" como {@link crm.entities.TipoCargo},
     * {@link crm.entities.TipoCampana}, entre otras
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;


    /**
     * InitBinder utilizado para setear el formato de fecha ingresada por formulario y para
     * setear el validador correspondiente de la clase Usuario.
     *
     * @param binder binder de parametros en el request a objectos JavaBean.
     */
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }


    /**
     * Retorna la vista para las busqueda
     *
     * @param model Modelo utilizado en la vista
     *
     * @return La vista para las busquedas.
     */
    @RequestMapping(value = "/busquedas/mezclador")
    public String busquedaMezcladorUsuario(Model model) {

        model.addAttribute("criterio", "rut"); // criterio seleccionado por DEFAULT

        return "/usuario/busquedaMezcladorUsuarios";
    }

    /**
     * Obtiene un listado de {@link crm.entities.Usuario}, segun criterio y valor de busqueda especificados en la URL;
     * retornando el numero pagina de la paginacion del listado, especificada en la URL.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @param criterio Criterio de busqueda utilizado (por ejemplo: buscar por nombre, apellidos, etc.).
     * @param pagina Numero de pagina a obtener para las busquedas.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/mezclador/resultados",method = RequestMethod.GET)
    public String resultadoBusquedaMezclador(Model model,
                                             @RequestParam("criterio") String criterio,
                                             @RequestParam("pagina") String pagina,
                                             RedirectAttributes redirectAttributes) {

        Integer numeroPagina;
        try {
            numeroPagina = Integer.parseInt(pagina);
        }
        catch(Exception e){
            numeroPagina = 1;
        }
        if (numeroPagina <= 0) {
            numeroPagina = 1;
        }
        try {
            Page<List<String>> usuarios = usuarioService.busquedaUsuarioMezclador(criterio, PAGE_SIZE, numeroPagina - 1);
            this.generatePagination(model, usuarios);

            model.addAttribute("usuarios", usuarios);
            model.addAttribute("criterio", criterio);

            return "/usuario/busquedaMezcladorUsuarios";
        }catch (AccessDeniedException e){
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para mezclar usuarios");
            return "redirect:/";
        }

    }




    /**
     * Metodo que muestra la vista donde se seleccionan los datos que se desean mezclar.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     * @param valor Corresponde al email o al nombre del usuario, dependiendo del criterio de busqueda.
     * @param paterno Apellido paterno del usuario a mezclar.
     * @param materno Apellido materno del usuario a mezclar.
     * @param criterio Criterio de busqueda de los usuarios repetidos.
     *
     * @return Vista que muestra los datos a elegir del {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value = "/usuario/mezclar", method = RequestMethod.GET)
    public String mezclarUsuario(Model model,
                                 @RequestParam(value = "valor", required = false) String valor,
                                 @RequestParam(value = "paterno", required = false) String paterno,
                                 @RequestParam(value = "materno", required = false) String materno,
                                 @RequestParam("criterio") String criterio){

        List<Usuario> usuariosRepetidos = usuarioService.getUsuariosByCriterio(criterio, valor, paterno, materno);

        // comprueba que existen al menos dos usuarios repetidos segun el criterio de busqueda
        if (usuariosRepetidos.size() >= 2) {
            model.addAttribute("usuario", new Usuario());
            Usuario usuario1 = usuariosRepetidos.get(0);
            Usuario usuario2 = usuariosRepetidos.get(1);

            model.addAttribute("usuario1", usuario1);
            model.addAttribute("usuario2", usuario2);

            cargarDatosVista(model);
        }

        return "/usuario/mezclarUsuarios";
    }




    /**
     * Muestra el retorno de una vez que se envian los datos a mezclar por el metodo POST.
     *
     * @param usuario Entidad {@link crm.entities.Usuario} a mezclar.
     * @param error Entidad {@link org.springframework.validation.BindingResult} que contiene los errores
     *        de ligado de entidades.
     * @param idUsuario1 id del usuario 1 a mezclar.
     * @param idUsuario2 id del usuario 2 a mezclar.
     *
     * @return Retorna una vista con el resultado de la operacion mezclar
     * {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @RequestMapping(value="/usuario/mezclar", method = RequestMethod.POST)
    public String mezclarUsuario(@Valid @ModelAttribute Usuario usuario,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(value = "idUsuario1") Long idUsuario1,
                                 @RequestParam(value = "idUsuario2") Long idUsuario2,
                                 @RequestParam(value = "checkBoxManejoIdiomas", required = false) String checkBoxManejoIdiomas,
                                 @RequestParam(value = "checkBoxCompromisosUsuario", required = false) String checkBoxCompromisosUsuario,
                                 @RequestParam(value = "checkBoxAntecedenteEducacional", required = false) String checkBoxAntecedenteEducacional,
                                 @RequestParam(value = "checkBoxAntecedenteColegio", required = false) String checkBoxAntecedenteColegio,
                                 @RequestParam(value = "checkBoxArchivosAdjuntos", required = false) String checkBoxArchivosAdjuntos,
                                 @RequestParam(value = "checkBoxCampanaExalumno", required = false) String checkBoxCampanaExalumno,
                                 @RequestParam(value = "checkBoxPaginasUsuario", required = false) String checkBoxPaginasUsuario,
                                 @RequestParam(value = "checkBoxRoles", required = false) String checkBoxRoles,
                                 @RequestParam(value = "checkBoxAutorizaciones", required = false) String checkBoxAutorizaciones,
                                 @RequestParam(value = "checkBoxConocimientoInformatico", required = false) String checkBoxConocimientoInformatico,
                                 @RequestParam(value = "checkBoxCapacitacionExalumno", required = false) String checkBoxCapacitacionExalumno,
                                 @RequestParam(value = "checkBoxActividadExalumno", required = false) String checkBoxActividadExalumno,
                                 @RequestParam(value = "checkBoxContactoHistoricoEmpresa", required = false) String checkBoxContactoHistoricoEmpresa,
                                 @RequestParam(value = "checkBoxInvitacionVideoEntrevistaUsmEmpleo", required = false) String checkBoxInvitacionVideoEntrevistaUsmEmpleo,
                                 @RequestParam(value = "checkBoxVideoEntrevistaUsmEmpleo", required = false) String checkBoxVideoEntrevistaUsmEmpleo,
                                 @RequestParam(value = "checkBoxVideoCurriculoUsuario", required = false) String checkBoxVideoCurriculoUsuario,
                                 @RequestParam(value = "checkBoxAptitudUsuario", required = false) String checkBoxAptitudUsuario,
                                 @RequestParam(value = "checkBoxFiltroOfertaLaboral", required = false) String checkBoxFiltroOfertaLaboral,
                                 @RequestParam(value = "checkBoxPagoAsesoria", required = false) String checkBoxPagoAsesoria,
                                 @RequestParam(value = "checkBoxCategoriaAsesoriaUsuario", required = false) String checkBoxCategoriaAsesoriaUsuario,
                                 @RequestParam(value = "checkBoxTestPsicologicoExalumno", required = false) String checkBoxTestPsicologicoExalumno,
                                 @RequestParam(value = "checkBoxOfertasCrawled", required = false) String checkBoxOfertasCrawled,
                                 @RequestParam(value = "checkBoxStickynotesAexaUsuario", required = false) String checkBoxStickynotesAexaUsuario,
                                 @RequestParam(value = "checkBoxStickynotesAexaUsuUsuario", required = false) String checkBoxStickynotesAexaUsuUsuario,
                                 @RequestParam(value = "checkBoxPostulacionOfertaLaboral", required = false) String checkBoxPostulacionOfertaLaboral,
                                 @RequestParam(value = "checkBoxAccesosSistemas", required = false) String checkBoxAccesosSistemas,
                                 @RequestParam(value = "checkBoxCompetencias", required = false) String checkBoxCompetencias,
                                 @RequestParam(value = "checkBoxAporteSocio", required = false) String checkBoxAporteSocio,
                                 @RequestParam(value = "checkBoxUsuarioVistoUsmEmpleo", required = false) String checkBoxUsuarioVistoUsmEmpleo,
                                 @RequestParam(value = "checkBoxDuenoEmpresa", required = false) String checkBoxDuenoEmpresa,
                                 @RequestParam(value = "checkBoxPostulanteFavorito", required = false) String checkBoxPostulanteFavorito,
                                 @RequestParam(value = "checkBoxPostulacionUsuarioAsesoria", required = false) String checkBoxPostulacionUsuarioAsesoria,
                                 @RequestParam(value = "checkBoxEncuestaPostulacionLaboral", required = false) String checkBoxEncuestaPostulacionLaboral,
                                 @RequestParam(value = "checkBoxRespuestaUsuario", required = false) String checkBoxRespuestaUsuario,
                                 @RequestParam(value = "checkBoxRecadosContactoUsuario", required = false) String checkBoxRecadosContactoUsuario,
                                 @RequestParam(value = "checkBoxRecadosContactoUsuUsuario", required = false) String checkBoxRecadosContactoUsuUsuario,
                                 @RequestParam(value = "checkBoxCorreosValidados", required = false) String checkBoxCorreosValidados,
                                 @RequestParam(value = "checkBoxRespaldoUsuario", required = false) String checkBoxRespaldoUsuario,
                                 @RequestParam(value = "checkBoxPostulacionArchivosAdjuntos", required = false) String checkBoxPostulacionArchivosAdjuntos,
                                 @RequestParam(value = "checkBoxRespuestaPreguntaOfertaLaboral", required = false) String checkBoxRespuestaPreguntaOfertaLaboral,
                                 Model model){
        if(error.hasErrors()){
           System.out.println(error);
        }

        Map<String, String> checkboxs = new HashMap<>();
        checkboxs.put("checkBoxManejoIdiomas", checkBoxManejoIdiomas);
        checkboxs.put("checkBoxCompromisosUsuario", checkBoxCompromisosUsuario);
        checkboxs.put("checkBoxAntecedenteEducacional", checkBoxAntecedenteEducacional);
        checkboxs.put("checkBoxAntecedenteColegio", checkBoxAntecedenteColegio);
        checkboxs.put("checkBoxArchivosAdjuntos", checkBoxArchivosAdjuntos);
        checkboxs.put("checkBoxCampanaExalumno", checkBoxCampanaExalumno);
        checkboxs.put("checkBoxPaginasUsuario", checkBoxPaginasUsuario);
        checkboxs.put("checkBoxRoles", checkBoxRoles);
        checkboxs.put("checkBoxAutorizaciones", checkBoxAutorizaciones);
        checkboxs.put("checkBoxConocimientoInformatico", checkBoxConocimientoInformatico);
        checkboxs.put("checkBoxCapacitacionExalumno", checkBoxCapacitacionExalumno);
        checkboxs.put("checkBoxActividadExalumno", checkBoxActividadExalumno);
        checkboxs.put("checkBoxContactoHistoricoEmpresa", checkBoxContactoHistoricoEmpresa);
        checkboxs.put("checkBoxInvitacionVideoEntrevistaUsmEmpleo", checkBoxInvitacionVideoEntrevistaUsmEmpleo);
        checkboxs.put("checkBoxVideoEntrevistaUsmEmpleo", checkBoxVideoEntrevistaUsmEmpleo);
        checkboxs.put("checkBoxVideoCurriculoUsuario", checkBoxVideoCurriculoUsuario);
        checkboxs.put("checkBoxAptitudUsuario", checkBoxAptitudUsuario);
        checkboxs.put("checkBoxFiltroOfertaLaboral", checkBoxFiltroOfertaLaboral);
        checkboxs.put("checkBoxPagoAsesoria", checkBoxPagoAsesoria);
        checkboxs.put("checkBoxCategoriaAsesoriaUsuario", checkBoxCategoriaAsesoriaUsuario);
        checkboxs.put("checkBoxTestPsicologicoExalumno", checkBoxTestPsicologicoExalumno);
        checkboxs.put("checkBoxOfertasCrawled", checkBoxOfertasCrawled);
        checkboxs.put("checkBoxStickynotesAexaUsuario", checkBoxStickynotesAexaUsuario);
        checkboxs.put("checkBoxStickynotesAexaUsuUsuario", checkBoxStickynotesAexaUsuUsuario);
        checkboxs.put("checkBoxPostulacionOfertaLaboral", checkBoxPostulacionOfertaLaboral);
        checkboxs.put("checkBoxAccesosSistemas", checkBoxAccesosSistemas);
        checkboxs.put("checkBoxCompetencias", checkBoxCompetencias);
        checkboxs.put("checkBoxAporteSocio", checkBoxAporteSocio);
        checkboxs.put("checkBoxUsuarioVistoUsmEmpleo", checkBoxUsuarioVistoUsmEmpleo);
        checkboxs.put("checkBoxDuenoEmpresa", checkBoxDuenoEmpresa);
        checkboxs.put("checkBoxPostulanteFavorito", checkBoxPostulanteFavorito);
        checkboxs.put("checkBoxPostulacionUsuarioAsesoria", checkBoxPostulacionUsuarioAsesoria);
        checkboxs.put("checkBoxEncuestaPostulacionLaboral", checkBoxEncuestaPostulacionLaboral);
        checkboxs.put("checkBoxRespuestaUsuario", checkBoxRespuestaUsuario);
        checkboxs.put("checkBoxRecadosContactoUsuario", checkBoxRecadosContactoUsuario);
        checkboxs.put("checkBoxRecadosContactoUsuUsuario", checkBoxRecadosContactoUsuUsuario);
        checkboxs.put("checkBoxCorreosValidados", checkBoxCorreosValidados);
        checkboxs.put("checkBoxRespaldoUsuario", checkBoxRespaldoUsuario);
        checkboxs.put("checkBoxPostulacionArchivosAdjuntos", checkBoxPostulacionArchivosAdjuntos);
        checkboxs.put("checkBoxRespuestaPreguntaOfertaLaboral", checkBoxRespuestaPreguntaOfertaLaboral);




        try {
            usuarioService.mezclarUsuario(usuario, idUsuario1, idUsuario2, checkboxs);

            redirectAttributes.addFlashAttribute("flash.success", "El Usuario ha sido mezclado exitosamente.");

            return "redirect:/busquedas/mezclador/";
        }catch (AccessDeniedException e){
            redirectAttributes.addFlashAttribute("flash.error", "Usted no tiene permisos para mezclar usuarios");
            return "redirect:/";
        }
    }







    /**
     * Metodo que obtiene los indices de inicio, final y actual para la paginacion de una busqueda en particular.
     *
     * @param model {@link org.springframework.ui.Model} donde se cargaran los datos de la paginacion.
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



    /**
     * Carga al {@link org.springframework.ui.Model} los datos necesarios para desplegar la vista del mezclador de usuario.
     *
     * @param model {@link org.springframework.ui.Model} a utilizar en la vista.
     */
    private void cargarDatosVista(Model model) {
        model.addAttribute("listadoPaises", geograficoService.getPaises());
        model.addAttribute("listadoComunas", geograficoService.getComunas());
        model.addAttribute("listadoEstadosCiviles", entidadesTipoService.listaTipoEstadoCivil());
        model.addAttribute("listadoSolicitudCredencial", entidadesTipoService.listaTipoEstadosSolicitudCredencial());
        model.addAttribute("listadoComoSupo", entidadesTipoService.listaTipoComoSupoDeRedExalumnos());
        model.addAttribute("listadoVigencia", entidadesTipoService.buscarTodosTiposVigencia());
    }

}