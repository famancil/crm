package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.CompetenciaExalumno}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class CompetenciaExalumnoPK implements Serializable {

    /**
     * Identificador de la {@link crm.entities.NivelCompetenciaUsmempleo} asociada a la {@link crm.entities.CompetenciaExalumno}
     */
    private Long nivelCompetenciaUsmempleoId;

    /**
     * Identificador del {@link crm.entities.Usuario} asociada a la {@link crm.entities.CompetenciaExalumno}
     */
    private Long usuarioId;


    @Id
    @Column(name = "nivcomusm_id")
    public Long getNivelCompetenciaUsmempleoId() {
        return nivelCompetenciaUsmempleoId;
    }

    public void setNivelCompetenciaUsmempleoId(Long nivelCompetenciaUsmempleoId) {
        this.nivelCompetenciaUsmempleoId = nivelCompetenciaUsmempleoId;
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
