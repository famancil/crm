package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.OfertaCarreraUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Embeddable
public class OfertaCarreraUsmempleoPK implements Serializable {

    /**
     * Identificador de {@link crm.entities.Carrera} asociado a {@link crm.entities.OfertaCarreraUsmempleo}
     */
    private Long codCarrera;

    /**
     * Identificador del {@link crm.entities.OfertaLaboralUsmempleo} asociada a {@link crm.entities.OfertaCarreraUsmempleo}
     */
    private Long ofertaLaboralUsmempleoId;

    @Id
    @Column(name = "cod_carrera")
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getOfertaLaboralUsmempleoId() {
        return ofertaLaboralUsmempleoId;
    }

    public void setOfertaLaboralUsmempleoId(Long ofertaLaboralUsmempleoId) {
        this.ofertaLaboralUsmempleoId = ofertaLaboralUsmempleoId;
    }
}