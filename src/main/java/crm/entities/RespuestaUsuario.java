package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.respuesta_usu_aexa.
 * Tabla en la cual se registran las respuestas alas preguntas
 * seleccionadas por el usuario, las cuales son 3 obligatorias
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "respuesta_usu_aexa")
@IdClass(RespuestaUsuarioPK.class)
public class RespuestaUsuario {


    /**
     * Identificador de la {@link Usuario} asociada en {@link RespuestaUsuario}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link crm.entities.PreguntasUsuario} asociada en {@link RespuestaUsuario}
     */
    private Long codPreguntaUsuario;

    /**
     * TODO comentar
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private PreguntasUsuario preguntasUsuario;

    /**
     * Fecha de modificacion de la carrera en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica la carrera en la BD
     */
    private Integer rutUsuario;




    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "cod_pregunta_usu_aexa")
    public Long getCodPreguntaUsuario() {
        return codPreguntaUsuario;
    }

    public void setCodPreguntaUsuario(Long codPreguntaUsuario) {
        this.codPreguntaUsuario = codPreguntaUsuario;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "cod_pregunta_usu_aexa", referencedColumnName = "cod_pregunta_usu_aexa", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public PreguntasUsuario getPreguntasUsuario() {
        return preguntasUsuario;
    }

    public void setPreguntasUsuario(PreguntasUsuario preguntasUsuario) {
        this.preguntasUsuario = preguntasUsuario;
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
