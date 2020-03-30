package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.oferta_carrera_usmempleo.
 * Relaciona la {@link crm.entities.OfertaLaboralUsmempleo} con las {@link crm.entities.Carrera}
 * que pueden postular a la oferta.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "oferta_carrera_usmempleo", schema = "empleo")
@IdClass(OfertaCarreraUsmempleoPK.class)
public class OfertaCarreraUsmempleo {

    /**
     * Identificador de {@link crm.entities.Carrera} asociado a {@link crm.entities.OfertaCarreraUsmempleo}
     */
    private Long codCarrera;

    /**
     * Identificador del {@link crm.entities.OfertaLaboralUsmempleo} asociada a {@link crm.entities.OfertaCarreraUsmempleo}
     */
    private Long ofertaLaboralUsmempleoId;

    /**
     * Instancia de la entidad {@link crm.entities.Carrera} asociada a asociada a {@link crm.entities.OfertaCarreraUsmempleo}
     */
    private Carrera carrera;

    /**
     * Instancia de la entidad {@link crm.entities.OfertaLaboralUsmempleo} asociada a {@link crm.entities.OfertaCarreraUsmempleo}
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Rut del usuario que realizó la ultima modificacion en la Base de Datos.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion en la Base de Datos.
     */
    private Date fechaModificacion;



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

    @JoinColumn(name = "cod_carrera", referencedColumnName = "cod_carrera", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_modificacion")
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

}
