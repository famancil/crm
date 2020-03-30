package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.EncuestaPostulacionLaboral}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class EncuestaPostulacionLaboralPK implements Serializable {

    /**
     * Identificador del {@link crm.entities.Usuario} asociada en {@link crm.entities.EncuestaPostulacionLaboral}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link crm.entities.OfertaLaboralUsmempleo} asociada en {@link crm.entities.EncuestaPostulacionLaboral}
     */
    private Long idOfertaLaboralUsmempleo;


    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmempleo() {
        return idOfertaLaboralUsmempleo;
    }

    public void setIdOfertaLaboralUsmempleo(Long idOfertaLaboralUsmempleo) {
        this.idOfertaLaboralUsmempleo = idOfertaLaboralUsmempleo;
    }
}