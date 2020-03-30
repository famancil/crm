package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link AdminOfertaLaboralUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class AdminOfertaLaboralUsmempleoPK implements Serializable {

    /**
     * Identificador del {@link UsuarioUsmempleoEmpresa} asociado al {@link AdminOfertaLaboralUsmempleo}
     */
    private Long idUsuarioUsmempleoEmpresa;

    /**
     * Identificador de la {@link OfertaLaboralUsmempleo} asociado al {@link AdminOfertaLaboralUsmempleo}
     */
    private Long idOfertaLaboralUsmempleo;





    @Id
    @Column(name = "usuempempusm_id")
    public Long getIdUsuarioUsmempleoEmpresa() {
        return idUsuarioUsmempleoEmpresa;
    }

    public void setIdUsuarioUsmempleoEmpresa(Long idUsuarioUsmempleoEmpresa) {
        this.idUsuarioUsmempleoEmpresa = idUsuarioUsmempleoEmpresa;
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
