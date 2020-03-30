package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.PostulacionOfertaLaboralUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class PostulacionOfertaLaboralUsmempleoPK implements Serializable{

    /**
     * Identificador del {@link crm.entities.OfertaLaboralUsmempleo} asociada a la {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     */
    private Long ofertaLaboralUsmempleoId;

    /**
     * Id del {@link crm.entities.Usuario} asociado a la {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     */
    private Long usuarioId;


    @Id
    @Column(name = "ofelabusm_id")
    public Long getOfertaLaboralUsmempleoId() {
        return ofertaLaboralUsmempleoId;
    }

    public void setOfertaLaboralUsmempleoId(Long ofertaLaboralUsmempleoId) {
        this.ofertaLaboralUsmempleoId = ofertaLaboralUsmempleoId;
    }

    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}