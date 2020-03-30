package crm.entities;


import javax.persistence.*;
import java.util.Date;

/**
 * Preguntas que debe responder un usuario_aexa por postular
 * a una oferta laboral en el portal de empleos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "pregunta_ofe_lab_usmempleo", schema="empleo")
public class PreguntaOfertaLaboralUsmEmpleo {

    /**
     * Identificador primario de cada pregunta
     */
    private Long id;

    /**
     * Oferta laboral relacionada con la pregunta realizada
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Texto que contiene la pregunta realizada
     */
    private String pregunta;

    /**
     * Rut de quien crea/modifica de la institucion en la BD
     */
    private Integer rutUsuario;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;


    @Id
    @Column(name = "preofelabusm_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @Column(name = "preofelabusm_pregunta")
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
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
