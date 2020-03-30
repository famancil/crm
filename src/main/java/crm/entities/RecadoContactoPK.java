package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.RecadoContacto}.
 *p
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class RecadoContactoPK implements Serializable {

    /**
     * Identificador del {@link crm.entities.Usuario} asociado al {@link crm.entities.RecadoContacto}
     */
    private Long usuarioId;

    /**
     * Fecha del {@link crm.entities.RecadoContacto}
     */
    private Date fecha;


    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "reccon_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
