package crm.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Entidad correspondiente a la tabla crm.autorizacion_usuario. Corresponde a las autorizaciones que posee un
 * {@link crm.entities.Usuario} sobre algun objeto del sistema asignado a uno de sus roles (del usuario). Por ejemplo,
 * un usuario con el rol ROL_AYUDANTE puede tener una autorizacion global sobre el objeto OfertaLaboral lo que indicaria
 * que dicho usuario posee permisos sin restriccion (sobre todas las carreras)
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "autorizacion_usuario", schema = "seguridad")
public class AutorizacionUsuario {

    /**
     * Identificador de la instancia
     */
    private Long id;

    /**
     * {@link crm.entities.Usuario} que posee la autorizacion actual.
     */
    private Usuario usuario;

    /**
     * Nombre del objeto en el cual se estan asignando permisos. Por ejemplo: usuario.
     */
    private String nombreObjeto;

    /**
     * Indica si existe o no una restriccion para el objeto segun nombreObjeto. Si es false entonces se debe buscar
     * la restriccion en la propiedad restriccion.
     */
    private Boolean global;

    /**
     * Restriccion asociada al permiso asignado al usuario que posee la autorizacion. Por ejemplo: usuario.id = 1
     * quiere decir que el usuario solo tiene permisos para el usuario  con id = 1.
     */
    private String restriccion;

    /**
     * Listado con todos los permisos (como {@link crm.entities.AutorizacionUsuarioPermisoAcceso}) asignados a esta
     * autorizacion en particular.
     */
    private List<AutorizacionUsuarioPermisoAcceso> permisosAsignados;

    /**
     * Usuario que realiza la insercion o modificacion de la instancia actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion de la instancia actual.
     */
    private Date fechaCreacion;

    /**
     * Nombre del rol de acceso del cual proviene la autorizacion
     */
    private Short rolAccesoId;

    /**
     * Nombre del usuario que realizo la ultima modificacion (se obtiene a partir de rutUsuario)
     */
    private String rutUsuarioNombre;

    /**
     * Nombre de la institucion correspondiente a la que hace referencia la restriccion (en caso de existir, se obtiene a partir de la informacion proporcionada en la propiedad restriccion)
     */
    private String restriccionNombre;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_user_seq_gen")
    @SequenceGenerator(name = "auth_user_seq_gen", sequenceName = "seguridad.autorizacion_usuario_id_seq", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "usuario_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Basic
    @Column(name = "nombre_objeto")
    public String getNombreObjeto() {
        return nombreObjeto;
    }

    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }

    @Basic
    @Column(name = "global")
    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    @Basic
    @Column(name = "restriccion")
    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "autorizacionUsuario", fetch = FetchType.EAGER)
    public List<AutorizacionUsuarioPermisoAcceso> getPermisosAsignados() {
        return permisosAsignados;
    }

    public void setPermisosAsignados(List<AutorizacionUsuarioPermisoAcceso> permisosAsignados) {
        this.permisosAsignados = permisosAsignados;
    }

    @Basic
    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Basic
    @Column(name = "fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Basic
    @Column(name = "id_rol_acceso")
    public Short getRolAccesoId() {
        return rolAccesoId;
    }

    public void setRolAccesoId(Short rolAccesoId) {
        this.rolAccesoId = rolAccesoId;
    }

    @Transient
    public String getRutUsuarioNombre() {
        return rutUsuarioNombre;
    }

    public void setRutUsuarioNombre(String rutUsuarioNombre) {
        this.rutUsuarioNombre = rutUsuarioNombre;
    }

    @Transient
    public String getRestriccionNombre() {
        return restriccionNombre;
    }

    public void setRestriccionNombre(String restriccionNombre) {
        this.restriccionNombre = restriccionNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutorizacionUsuario that = (AutorizacionUsuario) o;

        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (global != null ? !global.equals(that.global) : that.global != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nombreObjeto != null ? !nombreObjeto.equals(that.nombreObjeto) : that.nombreObjeto != null) return false;
        if (restriccion != null ? !restriccion.equals(that.restriccion) : that.restriccion != null) return false;
        if (rutUsuario != null ? !rutUsuario.equals(that.rutUsuario) : that.rutUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombreObjeto != null ? nombreObjeto.hashCode() : 0);
        result = 31 * result + (global != null ? global.hashCode() : 0);
        result = 31 * result + (restriccion != null ? restriccion.hashCode() : 0);
        result = 31 * result + (rutUsuario != null ? rutUsuario.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        return result;
    }
}
