package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de la entidad {@link crm.entities.RolUsuario}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class RolUsuarioPK implements Serializable {

    /**
     * id del rol al que se posee acceso.
     */
    private Short idRolAcceso;

    /**
     * Id del usuario que posee acceso al rol correspondiente.
     */
    private Long usuarioId;

    public RolUsuarioPK() {

    }

    @Column(name = "usuario_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Column(name = "rol_acceso_id")
    public Short getIdRolAcceso() {
        return idRolAcceso;
    }

    public void setIdRolAcceso(Short idRolAcceso) {
        this.idRolAcceso = idRolAcceso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolUsuarioPK)) return false;

        RolUsuarioPK that = (RolUsuarioPK) o;

        if (idRolAcceso != null ? !idRolAcceso.equals(that.idRolAcceso) : that.idRolAcceso != null)
            return false;
        if (usuarioId != null ? !usuarioId.equals(that.usuarioId) : that.usuarioId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRolAcceso != null ? idRolAcceso.hashCode() : 0;
        result = 31 * result + (usuarioId != null ? usuarioId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RolUsuarioPK{" +
                "idRolAcceso=" + idRolAcceso +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
