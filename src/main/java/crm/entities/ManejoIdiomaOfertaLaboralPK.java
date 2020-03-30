package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link ManejoIdiomaOfertaLaboral}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class ManejoIdiomaOfertaLaboralPK implements Serializable {

    /**
     * Identificador de la {@link OfertaLaboralUsmempleo} asociada a la {@link ManejoIdiomaOfertaLaboral}
     */
    private Long idOfertaLaboralUsmepleo;

    /**
     * Identificador del {@link Idioma} asociada a la {@link ManejoIdiomaOfertaLaboral}
     */
    private Short codIdioma;


    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmepleo() {
        return idOfertaLaboralUsmepleo;
    }
    public void setIdOfertaLaboralUsmepleo(Long idOfertaLaboralUsmepleo) {
        this.idOfertaLaboralUsmepleo = idOfertaLaboralUsmepleo;
    }

    @Id
    @Column(name = "cod_idioma")
    public Short getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(Short codIdioma) {
        this.codIdioma = codIdioma;
    }
}
