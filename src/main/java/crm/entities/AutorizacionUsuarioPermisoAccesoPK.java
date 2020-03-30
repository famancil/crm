package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de la entidad {@link crm.entities.AutorizacionUsuarioPermisoAcceso}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public class AutorizacionUsuarioPermisoAccesoPK implements Serializable {

    /**
     * Identificador del {@link crm.entities.PermisoAcceso} asociado a la autorizacion asignada a un usuario
     */
    private Short permisoAccesoId;

    /**
     * Identificador de la {@link crm.entities.AutorizacionUsuario} asociada a la asignacion de autorizacion de un
     * usuario.
     */
    private Long autorizacionUsuarioId;

    @Column(name = "permiso_acceso_id")
    @Id
    public Short getPermisoAccesoId() {
        return permisoAccesoId;
    }

    public void setPermisoAccesoId(Short permisoAccesoId) {
        this.permisoAccesoId = permisoAccesoId;
    }

    @Column(name = "autorizacion_usuario_id")
    @Id
    public Long getAutorizacionUsuarioId() {
        return autorizacionUsuarioId;
    }

    public void setAutorizacionUsuarioId(Long autorizacionUsuarioId) {
        this.autorizacionUsuarioId = autorizacionUsuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutorizacionUsuarioPermisoAccesoPK that = (AutorizacionUsuarioPermisoAccesoPK) o;

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
