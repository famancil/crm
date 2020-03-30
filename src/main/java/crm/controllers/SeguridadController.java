package crm.controllers;

import crm.services.*;
import crm.utils.EncodingUtil;
import crm.entities.*;
import crm.validators.UsuarioValidator;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador encargado de gestionar la logica de seguridad.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Controller
public class SeguridadController {


    /**
     * Clase utilizada para codificar y decodificar URIs
     */
    EncodingUtil encodingUtil;

    /**
     * Servicio utilizado para el manejo de la entidad usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
    * Servicio utilizado para el manejo de entidades de tipo geograficas
    */
    @Autowired
    private GeograficoService geograficoService;

    /**
     * Servicio utilizado para el manejo de la entidad institucion
     */
    @Autowired
    private InstitucionService institucionService;

    /**
     * Servicio utilizado para el manejo de la entidad Carrera
     */
    @Autowired
    private CarreraService carreraService;

    /**
     * Servicio utilizado para el manejo de entidades Tipo
     */
    @Autowired
    private EntidadesTipoService entidadesTipoService;

    /**
     * Servicio utilizado para el manejo de entidades relacionadas al sistema de seguridad
     */
    @Autowired
    private SeguridadService seguridadService;

    /**
     * Accion que despliega la interfaz que posibilita la busqueda de permisos y roles de un usuario en particular
     *
     * @return Retorna la vista de permisos de un usuario.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/busquedas/permisos-usuario", method = RequestMethod.GET)
    public String mostrarPermisosUsuario(Model model) {
        model.addAttribute("nombre", null);
        model.addAttribute("rut", null);
        model.addAttribute("sistemas", seguridadService.getSistemas());
        model.addAttribute("roles", seguridadService.getRolesAcceso());
        model.addAttribute("paises", geograficoService.getPaises());
        model.addAttribute("instituciones", institucionService.getInstitucionesPorPais(Short.parseShort(String.valueOf(56))));
        return "/admin/permisosUsuario";
    }

    /**
     * Obtiene un {@link crm.entities.Usuario}, segun criterio y valor de busqueda especificados en la URL;
     * para desplegar la información de permisos correspondiente al usuario buscado.
     *
     * @param model {@link org.springframework.ui.Model} Modelo a utilizar en la vista.
     * @param criterio Criterio de busqueda utilizado (por ejemplo: buscar por nombre, apellidos, etc.).
     * @param nombre Parametros de busqueda relacionado al criterio de busqueda por nombre.
     * @param rut Parametros de busqueda relacionados al criterio de busqueda por rut.
     *
     * @return La vista de resultado de la busqueda realizada.
     */
    @RequestMapping(value="/busquedas/permisos-usuario/resultado",method = RequestMethod.GET)
    public String resultadoBusqueda(Model model,
                                    RedirectAttributes redirectAttributes,
                                     @RequestParam("criterio") String criterio,
                                     @RequestParam("nombre") String nombre,
                                     @RequestParam("rut") String rut){
        Usuario usuario = null;
        Boolean esAdministradorGeneral = false;
        nombre = encodingUtil.decodeURIComponent(nombre);
        if(criterio.equals("nombre")){
            if(usuarioService.buscarUsuariosPorCalceNombreCompleto(nombre).size()!=0){
                usuario = usuarioService.buscarUsuariosPorCalceNombreCompleto(nombre).get(0);
            }
        }
        if(criterio.equals("rut")){
            usuario = usuarioService.getUsuarioByRut(rut);
        }
        if(usuario == null){
            redirectAttributes.addFlashAttribute("flash.error", "No se han encontrado usuarios con ese "+ criterio +".");
            return "redirect:/busquedas/permisos-usuario";
        }else {
            List<AccesoSistema> accesosSistema = seguridadService.getAccesosUsuarioSistemaByUsuaexId(usuario.getId());
            List<RolUsuario> rolesUsuario = seguridadService.getRolesUsuarioByUsuaexId(usuario.getId());
            List<AutorizacionUsuario> autorizacionesUsuario = seguridadService.getAutorizacionesUsuarioByUsuaexId(usuario.getId());
            for(int i=0;i<rolesUsuario.size();i++){
                if(rolesUsuario.get(i).getRolAcceso().getNombre().compareTo("ROLE_SUPER_ADMIN")==0){
                    esAdministradorGeneral = true;
                }
            }
            for(int i=0;i<accesosSistema.size();i++){
                if(usuarioService.getUsuarioByRut(String.valueOf(accesosSistema.get(i).getRutUsuario()))!=null)
                accesosSistema.get(i).setRutUsuarioNombre(usuarioService.getUsuarioByRut(String.valueOf(accesosSistema.get(i).getRutUsuario())).getNombreCompleto());
            }
            for(int i=0;i<rolesUsuario.size();i++){
                if(usuarioService.getUsuarioByRut(String.valueOf(rolesUsuario.get(i).getRutUsuario()))!=null)
                rolesUsuario.get(i).setRutUsuarioNombre(usuarioService.getUsuarioByRut(String.valueOf(rolesUsuario.get(i).getRutUsuario())).getNombreCompleto());
            }
            model.addAttribute("accesosUsuarioSistema", accesosSistema);
            model.addAttribute("rolesUsuario", rolesUsuario);
            if(esAdministradorGeneral) model.addAttribute("autorizacionesUsuario", null);
            else{
                for(int i=0;i<autorizacionesUsuario.size();i++){
                    if(seguridadService.getRolAccesoById(autorizacionesUsuario.get(i).getRolAccesoId()).getNombre().compareTo("ROLE_ADMIN_CARRERA")==0){
                        autorizacionesUsuario.get(i).setRestriccionNombre(WordUtils.capitalizeFully(carreraService.buscarCarreraPorCodCarreraString(autorizacionesUsuario.get(i).getRestriccion().split(";")[0].split("=")[1]).getNombreCarrera())+" ("+WordUtils.capitalizeFully(institucionService.getInstitucionPorCodigo(autorizacionesUsuario.get(i).getRestriccion().split(";")[1].split("=")[1]).getNomInstitucion()+")"));
                    }else{
                        autorizacionesUsuario.get(i).setRestriccionNombre(WordUtils.capitalizeFully(institucionService.getInstitucionPorCodigo(autorizacionesUsuario.get(i).getRestriccion().split("=")[1]).getNomInstitucion()));
                    }
                    if(usuarioService.getUsuarioByRut(String.valueOf(autorizacionesUsuario.get(i).getRutUsuario()))!=null)
                    autorizacionesUsuario.get(i).setRutUsuarioNombre(usuarioService.getUsuarioByRut(String.valueOf(autorizacionesUsuario.get(i).getRutUsuario())).getNombreCompleto());
                }
                model.addAttribute("autorizacionesUsuario", autorizacionesUsuario);
            }
            model.addAttribute("usuario", usuario);
            model.addAttribute("criterio", criterio);
            model.addAttribute("nombre", nombre);
            model.addAttribute("rut", rut);
            model.addAttribute("sistemas", seguridadService.getSistemas());
            model.addAttribute("roles", seguridadService.getRolesAcceso());
            model.addAttribute("paises", geograficoService.getPaises());
            model.addAttribute("instituciones", institucionService.getInstitucionesPorPais(Short.parseShort(String.valueOf(56))));
            return "/admin/permisosUsuario";
        }
    }

    /**
     * Obtiene un {@link crm.entities.AccesoSistema} a partir de un formulario y se lo asigna al usuario que posea
     * el id recibido como parametro.
     *
     * @param codSistema codigo del sistema al cual se brindara acceso
     * @param idUsuario id del usuario al cual se le asigna el rol
     * @param redirectAttributes {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} Atributos de redireccionamiento a utilizar en la vista..
     *
     * @return En caso de ser exitosa la operacion se redirecciona a la vista de búsqueda de usuarios para asignar
     * un rol o un permiso, en caso contrario, se redirecciona a la vista de asignación de rol de usuario.
     */
    @RequestMapping(value = "/seguridad/asignar-acceso-sistema", method = RequestMethod.POST)
    public String asignarAccesoSistemaUsuario(@RequestParam("codSistema") Short codSistema,
                                              @RequestParam(value="codSistemaAntiguo", required = false) Short codSistemaAntiguo,
                                              @RequestParam("idUsuario") Long idUsuario,
                                              RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        List<AccesoSistema> accesoSistemas = seguridadService.getAccesosUsuarioSistemaByUsuaexId(idUsuario);
        if(codSistemaAntiguo != null){
            for (int i = 0; i < accesoSistemas.size(); i++) {
                if (accesoSistemas.get(i).getSistema().getCodigo() == codSistema) {
                    redirectAttributes.addFlashAttribute("flash.error", "El usuario ya posee acceso a ese sistema.");
                    redirectAttributes.addAttribute("criterio", "nombre");
                    redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
                    redirectAttributes.addAttribute("rut", "");
                    return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
                }
            }
            seguridadService.asignarAccesoSistemaUsuario(usuario,seguridadService.getSistemaById(codSistema));
            seguridadService.eliminarAccesoSistemaByUsuaexIdYCodSistema(idUsuario,codSistemaAntiguo);
            redirectAttributes.addFlashAttribute("flash.success", "El acceso se ha modificado exitosamente.");
            redirectAttributes.addAttribute("criterio", "nombre");
            redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
            redirectAttributes.addAttribute("rut", "");
            return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
        }else{

            for (int i = 0; i < accesoSistemas.size(); i++) {
                if (accesoSistemas.get(i).getSistema().getCodigo() == codSistema) {
                    redirectAttributes.addFlashAttribute("flash.error", "El usuario ya posee acceso a ese sistema.");
                    redirectAttributes.addAttribute("criterio", "nombre");
                    redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
                    redirectAttributes.addAttribute("rut", "");
                    return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
                }
            }
            seguridadService.asignarAccesoSistemaUsuario(usuario, seguridadService.getSistemaById(codSistema));
            redirectAttributes.addFlashAttribute("flash.success", "El acceso ha sido asignado exitosamente.");
            redirectAttributes.addAttribute("criterio", "nombre");
            redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
            redirectAttributes.addAttribute("rut", "");
            return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
        }
    }

    /**
     * Obtiene un {@link crm.entities.RolAcceso} a partir de un formulario y se lo asigna al usuario que posea
     * el id recibido como parametro junto con los permisos asociados a este rol.
     *
     * @param nombreRolAcceso nombre del Rol de acceso escogido para asignar al usuario
     * @param nombreRolAccesoAntiguo nombre del Rol de acceso antiguo que se va a modificar (en caso que sea una modificacion)
     * @param codInstitucion codigo de la institucion a la que se asignara permisos en caso que RolAcceso sea ROLE_ADMIN_INSTITUCION, ROLE_AYUDANTE_INSTITUCION, ROLE_LEER_INSTITUCION o ROLE_ADMIN_CARRERA
     * @param codCarrera codigo de la carrera perteneciente a la institucion con codInstitucion a la que se le asignara permisos en caso que RolAcceso sea ROLE_ADMIN_CARRERA
     * @param idUsuario id del usuario al cual se le asigna el rol
     * @param redirectAttributes {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} Atributos de redireccionamiento a utilizar en la vista..
     *
     * @return Redirecciona a la vista de búsqueda de usuarios para asignar un rol de acceso o un acceso a sistema.
     */
    @RequestMapping(value = "/seguridad/asignar-rol-usuario", method = RequestMethod.POST)
    public String asignarRolUsuario(@RequestParam("idRolAcceso") String nombreRolAcceso,
                                    @RequestParam(value="idRolAccesoAntiguo", required=false) String nombreRolAccesoAntiguo,
                                    @RequestParam("codInstitucion") Short codInstitucion,
                                    @RequestParam("codCarrera") Long codCarrera,
                                    @RequestParam("idUsuario") Long idUsuario,
                                    RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        List<RolUsuario> rolesUsuario = seguridadService.getRolesUsuarioByUsuaexId(idUsuario);
        if(nombreRolAccesoAntiguo.compareTo("")!=0&&nombreRolAccesoAntiguo!=null){
            seguridadService.modificarRolUsuario(usuario,seguridadService.getRolAccesoByNombre(nombreRolAccesoAntiguo),seguridadService.getRolAccesoByNombre(nombreRolAcceso),codInstitucion,codCarrera);
            redirectAttributes.addFlashAttribute("flash.success", "El rol ha sido modificado exitosamente.");
            redirectAttributes.addAttribute("criterio", "nombre");
            redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
            redirectAttributes.addAttribute("rut", "");
            return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
        }
        for(int i = 0 ; i<rolesUsuario.size() ; i++){
            if(rolesUsuario.get(i).getRolAcceso().getId() == 1) {
                redirectAttributes.addFlashAttribute("flash.error", "El usuario posee rol de administrador general, no se pueden asignar mas roles.");
                redirectAttributes.addAttribute("criterio", "nombre");
                redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
                redirectAttributes.addAttribute("rut", "");
                return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
            }
            if(rolesUsuario.get(i).getRolAcceso().getNombre().compareTo(nombreRolAcceso)==0){
                redirectAttributes.addFlashAttribute("flash.error", "No se pudo asignar el rol, el usuario ya posee ese rol asignado.");
                redirectAttributes.addAttribute("criterio", "nombre");
                redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
                redirectAttributes.addAttribute("rut", "");
                return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
            }
        }
        seguridadService.asignarRolUsuario(usuario,seguridadService.getRolAccesoByNombre(nombreRolAcceso),codInstitucion,codCarrera);
        redirectAttributes.addFlashAttribute("flash.success", "El rol ha sido asignado exitosamente.");
        redirectAttributes.addAttribute("criterio", "nombre");
        redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
        redirectAttributes.addAttribute("rut", "");
        return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
    }

    /**
     * Agrega permisos ({@link crm.entities.AutorizacionUsuario}) a un rol de un usuario en particular
     */
    @RequestMapping(value = "/seguridad/agregar-permiso-rol-usuario", method = RequestMethod.POST)
    public String agregarPermisoRolAcceso(@RequestParam("idUsuario") Long idUsuario,
                                          @RequestParam("idRolAcceso") String nombreRolAcceso,
                                          @RequestParam("codInstitucion") Short codInstitucion,
                                          @RequestParam("codCarrera") Long codCarrera,
                                          RedirectAttributes redirectAttributes){
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        seguridadService.asignarPermisosUsuario(nombreRolAcceso,codInstitucion,codCarrera,usuario);
        redirectAttributes.addFlashAttribute("flash.success", "El permiso ha sido agregado correctamente.");
        redirectAttributes.addAttribute("criterio", "nombre");
        redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
        redirectAttributes.addAttribute("rut", "");
        return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
    }

    /**
     * Elimina fisicamente un {@link crm.entities.AccesoSistema}
     *
     * @param idUsuario identificador del {@link crm.entities.Usuario} que posee el {@link crm.entities.AccesoSistema} a eliminar.
     * @param codSistema identificador del {@link crm.entities.Sistema} en el {@link crm.entities.AccesoSistema} a eliminar.
     *
     * @return La vista de permisos del usuario.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/seguridad/eliminar-acceso-sistema/{idUsuario}/{codSistema}", method = RequestMethod.POST)
    public String eliminarAccesoSistema(@PathVariable("idUsuario") Long idUsuario, @PathVariable("codSistema") Short codSistema, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        seguridadService.eliminarAccesoSistemaByUsuaexIdYCodSistema(idUsuario, codSistema);
        redirectAttributes.addFlashAttribute("flash.success", "El acceso ha sido eliminado correctamente.");
        redirectAttributes.addAttribute("criterio", "nombre");
        redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
        redirectAttributes.addAttribute("rut", "");
        return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
    }

    /**
     * Elimina fisicamente un {@link crm.entities.RolUsuario}
     *
     * @param idUsuario identificador del {@link crm.entities.Usuario} que posee el {@link crm.entities.RolUsuario} a eliminar.
     * @param idRolAcceso identificador del {@link crm.entities.RolAcceso} en el {@link crm.entities.RolUsuario} a eliminar.
     *
     * @return La vista de permisos del usuario.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @RequestMapping(value = "/seguridad/eliminar-rol-usuario/{idUsuario}/{idRolAcceso}", method = RequestMethod.POST)
    public String eliminarRolUsuario(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idRolAcceso") Short idRolAcceso, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        seguridadService.eliminarRolUsuarioByUsuaexIdYIdRolAcceso(idUsuario, idRolAcceso);
        redirectAttributes.addFlashAttribute("flash.success", "El rol ha sido eliminado correctamente.");
        redirectAttributes.addAttribute("criterio", "nombre");
        redirectAttributes.addAttribute("nombre", usuario.getNombreCompletoCodificado());
        redirectAttributes.addAttribute("rut", "");
        return "redirect:/busquedas/permisos-usuario/resultado?criterio={criterio}&nombre={nombre}&rut={rut}";
    }
}
