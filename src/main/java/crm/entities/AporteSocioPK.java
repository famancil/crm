package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.AporteSocio}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class AporteSocioPK implements Serializable {

    /**
     * Identificador del {@link Usuario} asociado al {@link AporteSocio}
     */
    private Long idUsuario;

    /**
     * Identificador del {@link CompromisoSocio} asociado al {@link AporteSocio}
     */
    private Long idCompromisoSocio;

    /**
     * Fecha del {@link AporteSocio}
     */
    private Date fecha;



    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "comsoc_id")
    public Long getIdCompromisoSocio() {
        return idCompromisoSocio;
    }

    public void setIdCompromisoSocio(Long idCompromisoSocio) {
        this.idCompromisoSocio = idCompromisoSocio;
    }

    @Id
    @Column(name = "aposoc_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
