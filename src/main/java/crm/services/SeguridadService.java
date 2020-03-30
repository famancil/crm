package crm.services;

import crm.entities.*;
import crm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.springframework.orm.hibernate3.SessionFactoryUtils.getSession;

/**
 * Servicio que contiene los metodos para interactuar con entidades pertenecientes al schema de seguridad y con otras
 * entidades relacionadas.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class SeguridadService {

    /**
     * Servicio utilizado para el manejo del usuario
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AccesoSistema}.
     */
    @Autowired
    private AccesoSistemaRepository accesoSistemaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RolUsuario}.
     */
    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RolAcceso}.
     */
    @Autowired
    private RolAccesoRepository rolAccesoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AutorizacionUsuario}.
     */
    @Autowired
    private AutorizacionUsuarioRepository autorizacionUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AutorizacionUsuarioPermisoAcceso}.
     */
    @Autowired
    private AutorizacionUsuarioPermisoAccesoRepository autorizacionUsuarioPermisoAccesoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PermisoAcceso}.
     */
    @Autowired
    private PermisoAccesoRepository permisoAccesoRepository;


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoObjetoPermiso}.
     */
    @Autowired
    private TipoObjetoPermisoRepository tipoObjetoPermisoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Sistema}.
     */
    @Autowired
    private SistemaRepository sistemaRepository;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<AccesoSistema> getAccesosUsuarioSistemaByUsuaexId(Long id) {
        return accesoSistemaRepository.buscarPorIdUsuario(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<RolUsuario> getRolesUsuarioByUsuaexId(Long id) {
        return rolUsuarioRepository.buscarPorIdUsuario(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<AutorizacionUsuario> getAutorizacionesUsuarioByUsuaexId(Long id) {
        return autorizacionUsuarioRepository.buscarPorIdUsuario(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<RolAcceso> getRolesAcceso() {
        return rolAccesoRepository.findAllByOrderByIdAsc();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public RolAcceso getRolAccesoById(Short id) {
        return rolAccesoRepository.findOne(id);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void asignarRolUsuario(Usuario usuario, RolAcceso rolAcceso, Short codInstitucion, Long codCarrera) {
        if(rolAcceso.getNombre().compareTo("ROLE_SUPER_ADMIN")==0){
            List<RolUsuario> rolesUsuario = rolUsuarioRepository.buscarPorIdUsuario(usuario.getId());
            for(int i=0;i<rolesUsuario.size();i++){
                rolUsuarioRepository.delete(rolesUsuario.get(i));
            }
            List<AutorizacionUsuario> autorizacionesUsuario = autorizacionUsuarioRepository.buscarPorIdUsuario(usuario.getId());
            if(autorizacionesUsuario != null) {
                for (int i = 0; i < autorizacionesUsuario.size(); i++) {
                    if (!autorizacionesUsuario.get(i).getGlobal()) {
                        for (AutorizacionUsuarioPermisoAcceso ap : autorizacionesUsuario.get(i).getPermisosAsignados())
                            autorizacionUsuarioPermisoAccesoRepository.delete(ap);
                    }
                    autorizacionUsuarioRepository.delete(autorizacionesUsuario.get(i));
                }
            }
        }
        RolUsuarioPK rolUsuarioPK = new RolUsuarioPK();
        rolUsuarioPK.setUsuarioId(usuario.getId());
        rolUsuarioPK.setIdRolAcceso(rolAcceso.getId());
        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setCompositeId(rolUsuarioPK);
        rolUsuario.setFechaCreacion(new Date());
        rolUsuario.setRutUsuario(usuarioService.getCurrentUser().getRut());
        rolUsuario.setRolAcceso(rolAcceso);
        rolUsuario.setUsuario(usuario);
        rolUsuarioRepository.save(rolUsuario);
        if(rolAcceso.getNombre().compareTo("ROLE_SUPER_ADMIN")!=0) asignarPermisosUsuario(rolAcceso.getNombre(),codInstitucion,codCarrera,usuario);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void asignarPermisosUsuario(String nombreRolAcceso,Short codInstitucion,Long codCarrera,Usuario usuario){
        AutorizacionUsuario autorizacionUsuario = new AutorizacionUsuario();
        AutorizacionUsuarioPermisoAcceso autorizacionUsuarioPermisoAcceso = new AutorizacionUsuarioPermisoAcceso();
        RolAcceso rolAcceso = rolAccesoRepository.findByNombre(nombreRolAcceso);
        autorizacionUsuario.setUsuario(usuario);
        autorizacionUsuario.setRutUsuario(usuarioService.getCurrentUser().getRut());
        autorizacionUsuario.setFechaCreacion(new Date());
        autorizacionUsuario.setRolAccesoId(rolAcceso.getId());
        switch(nombreRolAcceso){
            case "ROLE_ADMIN_INSTITUCION":
                autorizacionUsuario.setNombreObjeto("Institucion");
                autorizacionUsuario.setRestriccion("codInstitucion="+codInstitucion);
                autorizacionUsuario.setGlobal(true);
                autorizacionUsuarioRepository.save(autorizacionUsuario);
                break;
            case "ROLE_AYUDANTE_INSTITUCION":
                autorizacionUsuario.setNombreObjeto("Institucion");
                autorizacionUsuario.setRestriccion("codInstitucion="+codInstitucion);
                autorizacionUsuario.setGlobal(false);
                autorizacionUsuario = autorizacionUsuarioRepository.save(autorizacionUsuario);
                autorizacionUsuarioPermisoAcceso.setAutorizacionUsuario(autorizacionUsuario);
                autorizacionUsuarioPermisoAcceso.setAutorizacionUsuarioId(autorizacionUsuario.getId());
                autorizacionUsuarioPermisoAcceso.setPermisoAcceso(permisoAccesoRepository.findByNombre("Leer"));
                autorizacionUsuarioPermisoAcceso.setPermisoAccesoId(autorizacionUsuarioPermisoAcceso.getPermisoAcceso().getCodigo());
                autorizacionUsuarioPermisoAccesoRepository.save(autorizacionUsuarioPermisoAcceso);
                autorizacionUsuarioPermisoAcceso.setPermisoAcceso(permisoAccesoRepository.findByNombre("Editar"));
                autorizacionUsuarioPermisoAcceso.setPermisoAccesoId(autorizacionUsuarioPermisoAcceso.getPermisoAcceso().getCodigo());
                autorizacionUsuarioPermisoAccesoRepository.save(autorizacionUsuarioPermisoAcceso);
                break;
            case "ROLE_LEER_INSTITUCION":
                autorizacionUsuario.setNombreObjeto("Institucion");
                autorizacionUsuario.setRestriccion("codInstitucion="+codInstitucion);
                autorizacionUsuario.setGlobal(false);
                autorizacionUsuario = autorizacionUsuarioRepository.save(autorizacionUsuario);
                autorizacionUsuarioPermisoAcceso.setAutorizacionUsuario(autorizacionUsuario);
                autorizacionUsuarioPermisoAcceso.setAutorizacionUsuarioId(autorizacionUsuario.getId());
                autorizacionUsuarioPermisoAcceso.setPermisoAcceso(permisoAccesoRepository.findByNombre("Leer"));
                autorizacionUsuarioPermisoAcceso.setPermisoAccesoId(autorizacionUsuarioPermisoAcceso.getPermisoAcceso().getCodigo());
                autorizacionUsuarioPermisoAccesoRepository.save(autorizacionUsuarioPermisoAcceso);
                break;
            case "ROLE_ADMIN_CARRERA":
                autorizacionUsuario.setNombreObjeto("Carrera");
                autorizacionUsuario.setRestriccion("codCarrera="+codCarrera+";codInstitucion="+codInstitucion);
                autorizacionUsuario.setGlobal(true);
                autorizacionUsuarioRepository.save(autorizacionUsuario);
                break;
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<Sistema> getSistemas() {
        return (List<Sistema>) sistemaRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public Sistema getSistemaById(Short codigo) {
        return sistemaRepository.findOne(codigo);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void asignarAccesoSistemaUsuario(Usuario usuario, Sistema sistema) {
        AccesoSistemaPK accesoSistemaPK = new AccesoSistemaPK();
        accesoSistemaPK.setUsuarioId(usuario.getId());
        accesoSistemaPK.setCodigoSistema(sistema.getCodigo());
        AccesoSistema accesoSistema = new AccesoSistema();
        accesoSistema.setCompositeId(accesoSistemaPK);
        accesoSistema.setFechaCreacion(new Date());
        accesoSistema.setRutUsuario(usuarioService.getCurrentUser().getRut());
        accesoSistema.setSistema(sistema);
        accesoSistema.setUsuario(usuario);
        accesoSistemaRepository.save(accesoSistema);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void eliminarAccesoSistemaByUsuaexIdYCodSistema(Long idUsuario, Short idSistema) {
        AccesoSistemaPK accesoSistemaPK = new AccesoSistemaPK();
        accesoSistemaPK.setUsuarioId(idUsuario);
        accesoSistemaPK.setCodigoSistema(idSistema);
        accesoSistemaRepository.delete(accesoSistemaPK);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void eliminarRolUsuarioByUsuaexIdYIdRolAcceso(Long idUsuario, Short idRolAcceso) {
        RolUsuarioPK rolUsuarioPK = new RolUsuarioPK();
        rolUsuarioPK.setUsuarioId(idUsuario);
        rolUsuarioPK.setIdRolAcceso(idRolAcceso);
        RolAcceso rolAcceso = rolAccesoRepository.findOne(idRolAcceso);
        if(rolAcceso.getNombre().compareTo("ROLE_SUPER_ADMIN")!=0) {
            List<AutorizacionUsuario> autorizaciones = autorizacionUsuarioRepository.buscarPorIdRolAccesoYIdUsuario(rolAcceso.getId(),idUsuario);
            if(autorizaciones!=null) {
                for (AutorizacionUsuario a : autorizaciones) {
                    if (!a.getGlobal()) {
                        for (AutorizacionUsuarioPermisoAcceso ap : a.getPermisosAsignados()) {
                            autorizacionUsuarioPermisoAccesoRepository.delete(ap);
                        }
                    }
                    autorizacionUsuarioRepository.delete(a);
                }
            }
        }
        rolUsuarioRepository.delete(rolUsuarioPK);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public RolAcceso getRolAccesoByNombre(String nombreRolAcceso) {
            return rolAccesoRepository.findByNombre(nombreRolAcceso);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void modificarRolUsuario(Usuario usuario, RolAcceso rolAccesoAntiguo, RolAcceso rolAccesoNuevo, Short codInstitucion, Long codCarrera) {
        eliminarRolUsuarioByUsuaexIdYIdRolAcceso(usuario.getId(),rolAccesoAntiguo.getId());
        asignarRolUsuario(usuario,rolAccesoNuevo,codInstitucion,codCarrera);
    }
}
