package crm.controllers;


import crm.entities.Usuario;
import crm.entities.UsuarioEmpresaUsmempleo;
import crm.services.UsuarioEmpresaUsmempleoService;
import crm.services.UsuarioService;
import crm.utils.EncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


/**
 * Controlador que contiene los metodos para la realizacion de busquedas de usuarios
 * en la vista correspondiente
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Controller
public class BusquedaUsuariosController {

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.UsuarioEmpresaUsmempleo}.
     */
     @Autowired
     private UsuarioEmpresaUsmempleoService usuarioEmpresaUsmempleoService;

    /**
     * Tamano de las paginas de listados a mostrar
     */
    private static final int PAGE_SIZE = 20;

    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;



    /**
     * Retorna la vista para las busqueda
     *
     * @param model Modelo utilizado en la vista
     *
     * @return La vista para las busquedas.
     */
    @RequestMapping(value = "/busquedas/usuarios")
    public String busquedas(Model model, RedirectAttributes redirectAttributes) {
        HashMap<String,String> valoresBusqueda = new HashMap<>();
        valoresBusqueda.put("nombres", "");
        valoresBusqueda.put("apellido-paterno", "");
        valoresBusqueda.put("apellido-materno", "");
        valoresBusqueda.put("rut", "");
        valoresBusqueda.put("email", "");
        valoresBusqueda.put("institucion", "");
        valoresBusqueda.put("carrera", "");
        valoresBusqueda.put("ano-ingreso", "");
        valoresBusqueda.put("ano-egreso", "");
        valoresBusqueda.put("ano-titulo", "");
        valoresBusqueda.put("empresa", "");
        valoresBusqueda.put("pais", "");
        valoresBusqueda.put("region", "");
        valoresBusqueda.put("provincia", "");
        valoresBusqueda.put("comuna", "");
        try {
            Page<Usuario> usuarios = usuarioService.busquedaUsuarioPorCriterio("nombres", valoresBusqueda, PAGE_SIZE, 1);
            this.generatePagination(model, usuarios);
            model.addAttribute("par", valoresBusqueda);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("criterio", "nombres");
            return "/usuario/busquedaUsuarios";
        }catch(AccessDeniedException e){
            redirectAttributes.addFlashAttribute("flash.error","Usted no está autorizado para buscar usuarios");
            return "redirect:/";
        }
    }

    /**
     * Obtiene un listado de {@link crm.entities.Usuario}, segun criterio y valor de busqueda especificados en la URL;
     * retornando el numero pagina de la paginacion del listado, especificada en la URL.
     *
     * @param model {@link org.springframework.ui.Model} Modelo a utilizar en la vista.
     * @param criterio Criterio de busqueda utilizado (por ejemplo: buscar por nombre, apellidos, etc.).
     * @param nombres Parametros de busqueda relacionados al criterio de busqueda por nombre.
     * @param apellidoPaterno Parametros de busqueda relacionados al criterio de busqueda por apellidos.
     * @param apellidoMaterno Parametros de busqueda relacionados al criterio de busqueda por apellidos.
     * @param rut Parametros de busqueda relacionados al criterio de busqueda por rut.
     * @param email Parametros de busqueda relacionados al criterio de busqueda por email.
     * @param codInstitucion Parametros de busqueda relacionados al criterio de busqueda por carrera.
     * @param codCarrera Parametros de busqueda relacionados al criterio de busqueda por carrera.
     * @param anoIngreso Parametros de busqueda relacionados al criterio de busqueda por carrera.
     * @param anoEgreso Parametros de busqueda relacionados al criterio de busqueda por carrera.
     * @param anoTitulo Parametros de busqueda relacionados al criterio de busqueda por carrera.
     * @param empresa Parametros de busqueda relacionados al criterio de busqueda por empresa.
     * @param pagina Numero de pagina a obtener para las busquedas.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/usuarios/resultados",method = RequestMethod.GET)
    public String resultadoBusquedas(Model model,
                                     @RequestParam("criterio") String criterio,
                                     @RequestParam("nombres") String nombres,
                                     @RequestParam("apellido-paterno") String apellidoPaterno,
                                     @RequestParam("apellido-materno") String apellidoMaterno,
                                     @RequestParam("rut") String rut,
                                     @RequestParam("email") String email,
                                     @RequestParam("institucion") String codInstitucion,
                                     @RequestParam("carrera") String codCarrera,
                                     @RequestParam("ano_ingreso") String anoIngreso,
                                     @RequestParam("ano_egreso") String anoEgreso,
                                     @RequestParam("ano_titulo") String anoTitulo,
                                     @RequestParam("empresa") String empresa,
                                     @RequestParam("pagina") String pagina,
                                     @RequestParam(value = "pais", required = false) String pais,
                                     @RequestParam(value = "region", required = false) String region,
                                     @RequestParam(value = "provincia", required = false) String provincia,
                                     @RequestParam(value = "comuna", required = false) String comuna,
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

        nombres = encodingUtil.decodeURIComponent(nombres);
        apellidoPaterno = encodingUtil.decodeURIComponent(apellidoPaterno);
        apellidoMaterno = encodingUtil.decodeURIComponent(apellidoMaterno);
        empresa = encodingUtil.decodeURIComponent(empresa);

        Map<String, String> valoresBusqueda = new HashMap<>();
        valoresBusqueda.put("nombres", nombres);
        valoresBusqueda.put("apellido-paterno", apellidoPaterno);
        valoresBusqueda.put("apellido-materno", apellidoMaterno);
        valoresBusqueda.put("rut", rut);
        valoresBusqueda.put("email", email);
        valoresBusqueda.put("institucion", codInstitucion);
        valoresBusqueda.put("carrera", codCarrera);
        valoresBusqueda.put("ano-ingreso", anoIngreso);
        valoresBusqueda.put("ano-egreso", anoEgreso);
        valoresBusqueda.put("ano-titulo", anoTitulo);
        valoresBusqueda.put("empresa", empresa);
        valoresBusqueda.put("pais", pais);
        valoresBusqueda.put("region", region);
        valoresBusqueda.put("provincia", provincia);
        valoresBusqueda.put("comuna", comuna);

        try {
            Page<Usuario> usuarios = usuarioService.busquedaUsuarioPorCriterio(criterio, valoresBusqueda, PAGE_SIZE, numeroPagina - 1);
            this.generatePagination(model, usuarios);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("criterio", criterio);
            model.addAttribute("par", valoresBusqueda); //contiene todos los parametros de busqueda
            return "/usuario/busquedaUsuarios";
        }catch(AccessDeniedException e){
            redirectAttributes.addFlashAttribute("flash.error","Usted no está autorizado para realizar esta búsqueda");
            return "redirect:/";
        }
    }

    /**
     * Obtiene una lista con los nombres completos de los {@link crm.entities.Usuario} que posean como nombre
     * el parámetro tagName entregado
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una lista de strings de los nombres encontrados
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/opciones/usuarios", method = RequestMethod.GET)
    public @ResponseBody List<String> buscarNombreUsuarios(@RequestParam("term") String tagName) {

        List<Usuario> listaUsuarios = usuarioService.buscarUsuariosPorCalceNombreCompleto(tagName);

        List<String> nombres = new ArrayList<String>();

        // Obtiene el nombre de la carrera, dependiendo de cual no esté nulo en la base de datos
        for (Usuario usuario: listaUsuarios){
            if (usuario.getNombres() != null && usuario.getApellidoPaterno() != null) {
                nombres.add((usuario.getNombres() + " " + usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno()).toUpperCase());
            }
        }
        return nombres;
    }

    /**
     * Obtiene una lista con los nombres completos de los {@link crm.entities.UsuarioEmpresaUsmempleo} que posean como nombre
     * el parámetro tagName entregado
     *
     * @param tagName nombre (o porción de nombre) sobra la cual se desea realizar la busqueda
     *
     * @return Una lista de strings de los nombres encontrados
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/opciones/usuariosEmpresa", method = RequestMethod.GET)
    public @ResponseBody List<String> buscarNombreUsuariosEmpresa(@RequestParam("term") String tagName) {
        return usuarioEmpresaUsmempleoService.buscarUsuariosEmpresaPorCalceNombreCompleto(tagName);
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