package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link CompetenciaOfertaLaboral}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class CompetenciaOfertaLaboralPK implements Serializable {

    /**
     * Identificador de la {@link NivelCompetenciaUsmempleo} asociada a la {@link CompetenciaOfertaLaboral}
     */
    private Long idNivelCompetencia;

    /**
     * Identificador del {@link OfertaLaboralUsmempleo} asociada a la {@link CompetenciaOfertaLaboral}
     */
    private Long idOfertaLaboralUsmepleo;


    @Id
    @Column(name = "nivcomusm_id")
    public Long getIdNivelCompetencia() {
        return idNivelCompetencia;
    }

    public void setIdNivelCompetencia(Long idNivelCompetencia) {
        this.idNivelCompetencia = idNivelCompetencia;
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmepleo() {
        return idOfertaLaboralUsmepleo;
    }

    public void setIdOfertaLaboralUsmepleo(Long idOfertaLaboralUsmepleo) {
        this.idOfertaLaboralUsmepleo = idOfertaLaboralUsmepleo;
    }
}
