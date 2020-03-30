package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de la entidad {@link crm.entities.AccesoSistema}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class AccesoSistemaPK implements Serializable {

    /**
     * Codigo del sistema al que se posee acceso.
     */
    private Short codigoSistema;

    /**
     * Id del usuario que posee acceso al sistema correspondiente.
     */
    private Long usuarioId;

    public AccesoSistemaPK() {

    }
    public AccesoSistemaPK(Long usuarioId, Short codigoSistema){
        this.usuarioId = usuarioId;
        this.codigoSistema = codigoSistema;
    }

    @Column(name = "cod_sistema")
    public Short getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(Short codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    @Column(name = "usuario_id")
    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccesoSistemaPK that = (AccesoSistemaPK) o;

        if (!codigoSistema.equals(that.codigoSistema)) return false;
        if (!usuarioId.equals(that.usuarioId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoSistema.hashCode();
        result = 31 * result + usuarioId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AccesoSistemaPK{" +
                "codigoSistema=" + codigoSistema +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
