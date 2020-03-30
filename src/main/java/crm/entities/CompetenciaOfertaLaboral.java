package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.competencia_exalumno
 * Contiene las competencias que han registrado los usuarios.

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "competencia_ofertalaboral", schema="empleo")
@IdClass(CompetenciaOfertaLaboralPK.class)
public class CompetenciaOfertaLaboral {

    /**
     * Identificador de la {@link NivelCompetenciaUsmempleo} asociada a la {@link CompetenciaOfertaLaboral}
     */
    private Long idNivelCompetencia;

    /**
     * Identificador del {@link OfertaLaboralUsmempleo} asociada a la {@link CompetenciaOfertaLaboral}
     */
    private Long idOfertaLaboralUsmepleo;

    /**
     * {@link NivelCompetenciaUsmempleo} asociada a la {@link CompetenciaOfertaLaboral}
     */
    private NivelCompetenciaUsmempleo nivelCompetenciaUsmempleo;

    /**
     * {@link OfertaLaboralUsmempleo} asociada a la {@link CompetenciaOfertaLaboral}
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Rut de quien crea/modifica en la BD
     */

    private Integer rutUsuario;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;







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

    @JoinColumn(name = "nivcomusm_id", referencedColumnName = "nivcomusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public NivelCompetenciaUsmempleo getNivelCompetenciaUsmempleo() {
        return nivelCompetenciaUsmempleo;
    }

    public void setNivelCompetenciaUsmempleo(NivelCompetenciaUsmempleo nivelCompetenciaUsmempleo) {
        this.nivelCompetenciaUsmempleo = nivelCompetenciaUsmempleo;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @Column(name = "fecha_modificacion")
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }
}
