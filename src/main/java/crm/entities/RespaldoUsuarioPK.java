package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Clase que maneja la clave compuesta de la entidad {@link RespaldoUsuario}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class RespaldoUsuarioPK implements Serializable {

    /**
     * Identificador del {@link Usuario} asociada al {@link RespaldoUsuario}
     */
    private Long idUsuario;

    /**
     * Fecha de modificacion del {@link RespaldoUsuario}
     */
    private Date fechaModificacion;


    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
