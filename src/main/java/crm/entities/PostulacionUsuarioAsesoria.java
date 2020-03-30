package crm.entities;



import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla asesoria.postulacion_usuario_asesoria.
 * Contiene a los alumnos o exalumnos que el usuario de la empresa ha
 * elegido como postulantes para realizar una asesoria
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "postulacion_usuario_asesoria", schema = "asesoria")
@IdClass(PostulacionUsuarioAsesoriaPK.class)
public class PostulacionUsuarioAsesoria {


    /**
     * Identificador del {@link Usuario} asociada en {@link PostulacionUsuarioAsesoria}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link OfertaAsesoria} asociada en {@link PostulacionUsuarioAsesoria}
     */
    private Long idOfertaAsesoria;

    /**
     * TODO Comentar
     */
    private Usuario usuario;

    /**
     * TODO Comentar
     */
    private OfertaAsesoria ofertaAsesoria;

    /**
     * TODO Comentar
     */
    private TipoEstadoAsesoria tipoEstadoAsesoria;

    /**
     * TODO Comentar
     */
    private Short evaluacion;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
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
    @Column(name = "oferta_asesoria_id")
    public Long getIdOfertaAsesoria() {
        return idOfertaAsesoria;
    }

    public void setIdOfertaAsesoria(Long idOfertaAsesoria) {
        this.idOfertaAsesoria = idOfertaAsesoria;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "oferta_asesoria_id", referencedColumnName = "oferta_asesoria_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public OfertaAsesoria getOfertaAsesoria() {
        return ofertaAsesoria;
    }

    public void setOfertaAsesoria(OfertaAsesoria ofertaAsesoria) {
        this.ofertaAsesoria = ofertaAsesoria;
    }

    @JoinColumn(name = "cod_estado_asesoria", referencedColumnName = "cod_estado_asesoria", insertable = false, updatable = false)
    @ManyToOne
    public TipoEstadoAsesoria getTipoEstadoAsesoria() {
        return tipoEstadoAsesoria;
    }

    public void setTipoEstadoAsesoria(TipoEstadoAsesoria tipoEstadoAsesoria) {
        this.tipoEstadoAsesoria = tipoEstadoAsesoria;
    }

    @Column(name = "evaluacion")
    public Short getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Short evaluacion) {
        this.evaluacion = evaluacion;
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
