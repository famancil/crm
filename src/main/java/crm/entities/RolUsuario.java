package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla seguridad.rol_usuario. Realiza la relacion entre que {@link crm.entities.RolAcceso} tiene asignado un
 * {@link crm.entities.Usuario}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "rol_usuario", schema = "seguridad")
public class RolUsuario {
    /**
     * Clave primaria compuesta de la entidad.
     */
    private RolUsuarioPK compositeId;

    /**
     * Rol de acceso que se esta asignando.
     */
    private RolAcceso rolAcceso;

    /**
     * Usuario que posee acceso al sistema correspondiente.
     */
    private Usuario usuario;

    /**
     * Rut del usuario que realizo la ultima modificacion en una instancia de la clase.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion realizada a la instancia de una clase.
     */
    private Date fechaCreacion;

    /**
     * Nombre del usuario que realizo la ultima modificacion (se obtiene a partir de rutUsuario)
     */
    private String rutUsuarioNombre;

    public RolUsuario(){

    }

    @EmbeddedId
    public RolUsuarioPK getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(RolUsuarioPK compositeId) {
        this.compositeId = compositeId;
    }

    @JoinColumn(name = "rol_acceso_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public RolAcceso getRolAcceso() {
        return rolAcceso;
    }

    public void setRolAcceso(RolAcceso rolAcceso) {
        this.rolAcceso = rolAcceso;
    }

    @JoinColumn(name = "usuario_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Transient
    public String getRutUsuarioNombre() {
        return rutUsuarioNombre;
    }

    public void setRutUsuarioNombre(String rutUsuarioNombre) {
        this.rutUsuarioNombre = rutUsuarioNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolUsuario)) return false;

        RolUsuario that = (RolUsuario) o;

        if (compositeId != null ? !compositeId.equals(that.compositeId) : that.compositeId != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (rolAcceso != null ? !rolAcceso.equals(that.rolAcceso) : that.rolAcceso != null) return false;
        if (rutUsuario != null ? !rutUsuario.equals(that.rutUsuario) : that.rutUsuario != null) return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = compositeId != null ? compositeId.hashCode() : 0;
        result = 31 * result + (rolAcceso != null ? rolAcceso.hashCode() : 0);
        result = 31 * result + (usuario != null ? usuario.hashCode() : 0);
        result = 31 * result + (rutUsuario != null ? rutUsuario.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        return result;
    }
}
