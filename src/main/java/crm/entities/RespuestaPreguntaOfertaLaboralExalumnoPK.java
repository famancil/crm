package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link RespuestaPreguntaOfertaLaboralExalumno}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class RespuestaPreguntaOfertaLaboralExalumnoPK implements Serializable {

    /**
     * Identificador de la {@link crm.entities.OfertaLaboralUsmempleo} asociada en {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     */
    private Long idOfertaLaboralUsmEmpleo;

    /**
     * Identificador de la {@link crm.entities.Usuario} asociada en {@link RespuestaPreguntaOfertaLaboralExalumno}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link crm.entities.PreguntaOfertaLaboralUsmEmpleo} asociada en {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     */
    private Long idPreguntaOfertaLaboralUsmEmpleo;

    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmEmpleo() {
        return idOfertaLaboralUsmEmpleo;
    }

    public void setIdOfertaLaboralUsmEmpleo(Long idOfertaLaboralUsmEmpleo) {
        this.idOfertaLaboralUsmEmpleo = idOfertaLaboralUsmEmpleo;
    }

    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "preofelabusm_id")
    public Long getIdPreguntaOfertaLaboralUsmEmpleo() {
        return idPreguntaOfertaLaboralUsmEmpleo;
    }

    public void setIdPreguntaOfertaLaboralUsmEmpleo(Long idPreguntaOfertaLaboralUsmEmpleo) {
        this.idPreguntaOfertaLaboralUsmEmpleo = idPreguntaOfertaLaboralUsmEmpleo;
    }
}
