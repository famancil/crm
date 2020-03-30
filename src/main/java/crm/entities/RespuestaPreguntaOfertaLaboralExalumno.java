package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Respuestas de un usuario_aexa al postular a una oferta laboral
 * del portal de empleos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "respuesta_pre_ofe_exalumno", schema="empleo")
@IdClass(RespuestaPreguntaOfertaLaboralExalumnoPK.class)
public class RespuestaPreguntaOfertaLaboralExalumno {


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

    /**
     *  {@link crm.entities.PreguntaOfertaLaboralUsmEmpleo} asociada en {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     */
    private PreguntaOfertaLaboralUsmEmpleo preguntaOfertaLaboralUsmEmpleo;

    /**
     * Usuario que responde la pregunta
     */
    private Usuario usuario;

    /**
     * Postulacion asociada a la respuesta de la pregunta
     */
    private PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboralUsmempleo;

    /**
     * Respuesta del usuario
     */
    private String respuesta;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica de la institucion en la BD
     */
    private Integer rutUsuario;


    public RespuestaPreguntaOfertaLaboralExalumno() {
    }


    public RespuestaPreguntaOfertaLaboralExalumno(RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta) {
        this.idUsuario = respuestaPregunta.getIdUsuario();
        this.idOfertaLaboralUsmEmpleo = respuestaPregunta.getIdOfertaLaboralUsmEmpleo();
        this.idPreguntaOfertaLaboralUsmEmpleo = respuestaPregunta.getIdPreguntaOfertaLaboralUsmEmpleo();
        this.respuesta = respuestaPregunta.getRespuesta();
        this.rutUsuario = respuestaPregunta.getRutUsuario();
        this.fechaModificacion = respuestaPregunta.getFechaModificacion();
    }

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

    @JoinColumn(name = "preofelabusm_id", referencedColumnName = "preofelabusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public PreguntaOfertaLaboralUsmEmpleo getPreguntaOfertaLaboralUsmEmpleo() {
        return preguntaOfertaLaboralUsmEmpleo;
    }

    public void setPreguntaOfertaLaboralUsmEmpleo(PreguntaOfertaLaboralUsmEmpleo preguntaOfertaLaboralUsmEmpleo) {
        this.preguntaOfertaLaboralUsmEmpleo = preguntaOfertaLaboralUsmEmpleo;
    }

    @JoinColumns({
            @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false),
            @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public PostulacionOfertaLaboralUsmempleo getPostulacionOfertaLaboralUsmempleo() {

        return postulacionOfertaLaboralUsmempleo;
    }

    public void setPostulacionOfertaLaboralUsmempleo(PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboralUsmempleo) {
        this.postulacionOfertaLaboralUsmempleo = postulacionOfertaLaboralUsmempleo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "respreofeexa_respuesta")
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
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
