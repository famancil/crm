package crm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Entidad correspondiente a la tabla crm.autorizacion_usuario_permiso_acceso. Contiene la asignacion de que permisos
 * se corresponden con que entidades de tipo {@link crm.entities.AutorizacionUsuario}. Esta entidad en realidad es una
 * relacion muchos a muchos entre permisos de acceso y autorizaciones de usuarios pero como posee mas atributos se
 * decidio mantenerla como una entidad.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "autorizacion_usuario_permiso_acceso", schema = "seguridad")
@IdClass(AutorizacionUsuarioPermisoAccesoPK.class)
public class AutorizacionUsuarioPermisoAcceso {

    /**
     * Identificador del {@link crm.entities.PermisoAcceso} asociado a la autorizacion asignada a un usuario
     */
    private Short permisoAccesoId;

    /**
     * Identificador de la {@link crm.entities.AutorizacionUsuario} asociada a la asignacion de autorizacion de un
     * usuario.
     */
    private Long autorizacionUsuarioId;

    /**
     * Permiso de acceso asociado a una autorizacion de usuario.
     */
    private PermisoAcceso permisoAcceso;

    /**
     * Autorizacion de usuario asociada a un permiso de acceso.
     */
    private AutorizacionUsuario autorizacionUsuario;

    @Id
    @Column(name = "permiso_acceso_id")
    public Short getPermisoAccesoId() {
        return permisoAccesoId;
    }

    public void setPermisoAccesoId(Short permisoAccesoId) {
        this.permisoAccesoId = permisoAccesoId;
    }

    @Id
    @Column(name = "autorizacion_usuario_id")
    public Long getAutorizacionUsuarioId() {
        return autorizacionUsuarioId;
    }

    public void setAutorizacionUsuarioId(Long autorizacionUsuarioId) {
        this.autorizacionUsuarioId = autorizacionUsuarioId;
    }

    @JoinColumn(name = "permiso_acceso_id", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public PermisoAcceso getPermisoAcceso() {
        return permisoAcceso;
    }

    public void setPermisoAcceso(PermisoAcceso permisoAcceso) {
        this.permisoAcceso = permisoAcceso;
    }

    @JoinColumn(name = "autorizacion_usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public AutorizacionUsuario getAutorizacionUsuario() {
        return autorizacionUsuario;
    }

    public void setAutorizacionUsuario(AutorizacionUsuario autorizacionUsuario) {
        this.autorizacionUsuario = autorizacionUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutorizacionUsuarioPermisoAcceso that = (AutorizacionUsuarioPermisoAcceso) o;

        if (autorizacionUsuarioId != null ? !autorizacionUsuarioId.equals(that.autorizacionUsuarioId) : that.autorizacionUsuarioId != null)
            return false;
        if (permisoAccesoId != null ? !permisoAccesoId.equals(that.permisoAccesoId) : that.permisoAccesoId != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = permisoAccesoId != null ? permisoAccesoId.hashCode() : 0;
        result = 31 * result + (autorizacionUsuarioId != null ? autorizacionUsuarioId.hashCode() : 0);
        return result;
    }
}
