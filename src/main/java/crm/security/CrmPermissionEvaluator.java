package crm.security;

import crm.entities.*;
import crm.repositories.AutorizacionUsuarioRepository;
import crm.repositories.PermisoAccesoRepository;
import crm.repositories.RolUsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Clase que implementa el sistema de ACL utilizado en el CRM. La idea es que cada usuario posea un rol asociado a su
 * cuenta y para cada rol existiran distintos permisos asociados. Estos permisos se podran verificar contra objetos sobre
 * los cuales se ejecutaran acciones en el sistema.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class CrmPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private PermisoAccesoRepository permisoAccesoRepository;

    @Autowired
    private AutorizacionUsuarioRepository autorizacionUsuarioRepository;

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(CrmPermissionEvaluator.class);


    /**
     * @param authentication     represents the user in question. Should not be null.
     * @param targetDomainObject the domain object for which permissions should be checked. May be null
     *                           in which case implementations should return false, as the null condition can be checked explicitly
     *                           in the expression.
     * @param permission         a representation of the permission object as supplied by the expression system. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication!=null && permission instanceof String) {
            //se verifica si es que el usuario es SUPER_ADMIN o no
            Usuario enSesion = (Usuario)authentication.getPrincipal();
            List<RolUsuario> rolesUsuario = rolUsuarioRepository.buscarPorIdUsuario(enSesion.getId());
            for(RolUsuario rolUsuario : rolesUsuario){
                if(rolUsuario.getRolAcceso().getNombre().compareTo("ROLE_SUPER_ADMIN") == 0){
                    return true;
                }
            }
            //Se obtiene el permiso deseado
            PermisoAcceso permisoAcceso = permisoAccesoRepository.findByNombre(permission.toString());
            //ahora se necesitan obtener todas las autorizaciones que posee el usuario para el objeto especificado en
            //targetDomainObject
            List<AutorizacionUsuario> autorizaciones = autorizacionUsuarioRepository.findByNombreObjetoAndUsuario(
                    targetDomainObject.getClass().getSimpleName(), enSesion);
            //y buscamos la autorizacion del usuario para el permiso especificado en el objeto permission, ademas, si
            //es que el ROL que posee en la autorizacion no es explicito entonces tiene permisos inmediatamente
            for (AutorizacionUsuario autorizacion : autorizaciones) {
                if (autorizacion.getGlobal())
                    return true;
                //si es explicito entonces hay que buscar los permisos
                for (AutorizacionUsuarioPermisoAcceso autorizacionUsuarioPermisoAcceso : autorizacion.getPermisosAsignados()) {
                    //si es que el permiso asociado a la autorizacion es equivalente al permisoAcceso entonces tenemos
                    //una autorizacion entregada.
                    if (autorizacionUsuarioPermisoAcceso.getPermisoAcceso().equals(permisoAcceso)) {
                        if (autorizacion.getRestriccion() != null && !autorizacion.getRestriccion().isEmpty()) {
                            try {
                                String[] restrictionProperties = autorizacion.getRestriccion().split("=");
                                Field f = targetDomainObject.getClass().getDeclaredField(restrictionProperties[0]);
                                f.setAccessible(true);
                                //se obtiene el valor como string
                                String fieldValue = f.get(targetDomainObject).toString();
                                if (restrictionProperties[1].split(";")[0].compareTo(fieldValue) == 0)
                                    return true;
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                logger.warn("HA OCURRIDO UN ERROR AL VALIDAR EL PERMISO PARA EL USUARIO " + enSesion.getId());
                                logger.warn("EL PERMISO A VALIDAR CORRESPONDE A " + permisoAcceso.getNombre());
                                logger.warn("SOBRE EL OBJETO " + targetDomainObject.toString());
                                logger.warn("EL ERROR FUE: " + e);
                                return false;
                            }
                        } else
                            return true;
                    }
                }
            }
        }
        logger.info("EL USUARIO QUE SOLICITO EL ACCESO NO POSEE LOS PERMISOS SUFICIENTES PARA EJECUTAR LA ACCION.");
        return false;
    }

    /**
     * Alternative method for evaluating a permission where only the identifier of the target object
     * is available, rather than the target instance itself.
     *
     * @param authentication represents the user in question. Should not be null.
     * @param targetId       the identifier for the object instance (usually a Long)
     * @param targetType     a String representing the target's type (usually a Java classname). Not null.
     * @param permission     a representation of the permission object as supplied by the expression system. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new RuntimeException("Permisos por ID y tipo de Clase no estan soportados en estos momentos.");
    }

    /**
     * Verifica si el usuario en sesion posee acceso implicito sobre un usuario en particular a través de las restricciones
     * en sus autorizaciones de usuario (permisos asignados). Ejemplo: Si el usuario en sesion posee permisos de acceso sobre
     * los usuarios pertenecientes a una institucion en particular el metodo retornara 'true' si y solo si targetUser corresponde
     * a un usuario de esa institucion, lo mismo para usuarios de una carrera de una institucion.
     *
     * @param authentication     represents the user in question. Should not be null.
     * @param targetUser the user for which implicit permissions should be checked. May be null
     *                           in which case implementations should return false, as the null condition can be checked explicitly
     *                           in the expression.
     * @return true if the permission is granted, false otherwise
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public boolean hasImplicitPermissionOverUser(Authentication authentication, Usuario targetUser, Object permission){
        if (authentication!=null&&targetUser!=null){
            Usuario enSesion = (Usuario)authentication.getPrincipal();
            List<RolUsuario> rolesUsuario = rolUsuarioRepository.buscarPorIdUsuario(enSesion.getId());
            for(RolUsuario rolUsuario : rolesUsuario){
                if(rolUsuario.getRolAcceso().getNombre().compareTo("ROLE_SUPER_ADMIN") == 0){
                    return true;
                }
            }
            List<AntecedenteEducacional> antecedentesPerfil = targetUser.getAntecedenteEducacionalList();
            List<AutorizacionUsuario> autorizaciones = enSesion.getAutorizacionesUsuario();
            for(AutorizacionUsuario au: autorizaciones){
                if(au.getRestriccion().split(";")[0].split("=")[0].compareTo("codInstitucion")==0){
                    Short codInstitucion = Short.parseShort(au.getRestriccion().split(";")[0].split("=")[1]);
                    for(AntecedenteEducacional a: antecedentesPerfil){
                        if(a.getInstitucion().getCodInstitucion()==codInstitucion){
                            if(au.getGlobal()) return true;
                            else{
                                for(AutorizacionUsuarioPermisoAcceso pa: au.getPermisosAsignados()){
                                    if(permission instanceof String && pa.getPermisoAcceso().getNombre().compareTo(permission.toString())==0) return true;
                                }
                            }
                        }
                    }
                }
                else if(au.getRestriccion().split(";")[0].split("=")[0].compareTo("codCarrera")==0){
                    Long codCarrera = Long.parseLong(au.getRestriccion().split(";")[0].split("=")[1]);
                    Short codInstitucion = Short.parseShort(au.getRestriccion().split(";")[1].split("=")[1]);
                    for(AntecedenteEducacional a: antecedentesPerfil){
                        if(a.getCarrera().getCodCarrera() == codCarrera && a.getInstitucion().getCodInstitucion()==codInstitucion){
                            if(au.getGlobal()) return true;
                            else{
                                for(AutorizacionUsuarioPermisoAcceso pa: au.getPermisosAsignados()){
                                    if(permission instanceof String && pa.getPermisoAcceso().getNombre().compareTo(permission.toString())==0) return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
