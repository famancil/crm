package crm.entities;



import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.encuesta_postulacion_laboral.
 * Guarda la opinión de los Usuarios acerca de sus postulaciones laborales
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "encuesta_postulacion_laboral", schema = "empleo")
@IdClass(EncuestaPostulacionLaboralPK.class)
public class EncuestaPostulacionLaboral {


    /**
     * Identificador del {@link crm.entities.Usuario} asociada en {@link crm.entities.EncuestaPostulacionLaboral}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link crm.entities.OfertaLaboralUsmempleo} asociada en {@link crm.entities.EncuestaPostulacionLaboral}
     */
    private Long idOfertaLaboralUsmempleo;

    /**
     * TODO comentar
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Postulacion asociada a la respuesta de la pregunta
     */
    private PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboralUsmempleo;

    /**
     * TODO comentar
     */
    private Boolean contratado;

    /**
     * TODO comentar
     */
    private Short evaluacionProcesoEmpresa;

    /**
     * TODO comentar
     */
    private Short duracionProceso;

    /**
     * TODO comentar
     */
    private String comentario;

    /**
     * TODO comentar
     */
    private Boolean opina;



    public EncuestaPostulacionLaboral() {

    }

    public EncuestaPostulacionLaboral(EncuestaPostulacionLaboral encuesta) {
        this.idUsuario = encuesta.getIdUsuario();
        this.idOfertaLaboralUsmempleo = encuesta.getIdOfertaLaboralUsmempleo();
        this.usuario = encuesta.getUsuario();
        this.ofertaLaboralUsmempleo = encuesta.getOfertaLaboralUsmempleo();
        this.contratado = encuesta.getContratado();
        this.evaluacionProcesoEmpresa = encuesta.getEvaluacionProcesoEmpresa();
        this.duracionProceso = encuesta.getDuracionProceso();
        this.comentario = encuesta.getComentario();
        this.opina = encuesta.getOpina();
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
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmempleo() {
        return idOfertaLaboralUsmempleo;
    }

    public void setIdOfertaLaboralUsmempleo(Long idOfertaLaboralUsmempleo) {
        this.idOfertaLaboralUsmempleo = idOfertaLaboralUsmempleo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @Column(name = "encposlab_contratado")
    public Boolean getContratado() {
        return contratado;
    }

    public void setContratado(Boolean contratado) {
        this.contratado = contratado;
    }

    @Column(name = "encposlab_evaluacion_proceso_empresa")
    public Short getEvaluacionProcesoEmpresa() {
        return evaluacionProcesoEmpresa;
    }

    public void setEvaluacionProcesoEmpresa(Short evaluacionProcesoEmpresa) {
        this.evaluacionProcesoEmpresa = evaluacionProcesoEmpresa;
    }

    @Column(name = "encposlab__duracion_proceso")
    public Short getDuracionProceso() {
        return duracionProceso;
    }

    public void setDuracionProceso(Short duracionProceso) {
        this.duracionProceso = duracionProceso;
    }

    @Column(name = "encposlab_comentario")
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Column(name = "encposlab_opina")
    public Boolean getOpina() {
        return opina;
    }

    public void setOpina(Boolean opina) {
        this.opina = opina;
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
}
