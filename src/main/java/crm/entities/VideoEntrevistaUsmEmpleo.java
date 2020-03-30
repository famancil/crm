package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa una video entrevista realizada en el sistema. Para que exista una video entrevista debe
 * existir una invitacion por parte de un {@link UsuarioEmpresaUsmempleo} hacia un
 * {@link Usuario} y este ultimo debe aceptar dicha invitacion.
 *
 * @author dacuna
 * @since 1.0
 */
@Entity
@Table(name = "video_entrevista_usmempleo", schema="video_curriculos_empleos")
public class VideoEntrevistaUsmEmpleo {

    /**
     * Identificador de la video entrevista
     */
    private Long id;

    /**
     * Usuario invitado a la video entrevista (postulante a la oferta laboral de la empresa)
     */
    private Usuario usuario;

    /**
     * Usuario representante de la empresa quien invita al postulante a una video entrevista
     */
    private UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo;

    /**
     * Oferta laboral a la que postulo el usuario postulante para ser contactado por la empresa
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Invitacion que dio origen a la video entrevista. Pueden existir video entrevistas que no provengan de una
     * invitacion como es el caso de una video entrevista inmediata.
     */
    private InvitacionVideoEntrevistaUsmEmpleo invitacionVideoEntrevistaUsmEmpleo;

    /**
     * Estado de la video entrevista. Permite reconocer si es que alguno de los usuarios accedio a la entrevista para
     * llevarla a cabo o si es que ambos accedieron.
     */
    private VideoEntrevistaUsmEmpleoEstado estado;

    /**
     * Si el usuario invitado rechaza la invitacion puede indicar un motivo del porque realizo el rechazo
     */
    private String motivoRechazo;

    /**
     * Nota de la evaluacion realizada en la video entrevista segun escala proveniente desde la entidad
     * {@link TipoEvaluacionVideoEntrevista}.
     */
    private TipoEvaluacionVideoEntrevista tipoEvalucionVideoEntrevista;

    /**
     * Observacion relacionada con la evaluacion de la video entrevista realizada por el usuario empresa.
     */
    private String observacionEvaluacion;

    /**
     * Fecha en que se llevo a cabo la video entrevista
     */
    private Date fechaCreacion;

    public VideoEntrevistaUsmEmpleo() {
        this.estado = VideoEntrevistaUsmEmpleoEstado.CREADA;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_entrevista_usmempleo_id_seq")
    @SequenceGenerator(name = "video_entrevista_usmempleo_id_seq", sequenceName = "video_entrevista_usmempleo_id_seq", allocationSize=1)
    @Column(name = "videntr_id")
    public Long getId() {
        return id;
    }

    public void setId(Long invitacion) {
        this.id = invitacion;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "usuempusm_id", referencedColumnName = "usuempusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public UsuarioEmpresaUsmempleo getUsuarioEmpresaUsmempleo() {
        return usuarioEmpresaUsmempleo;
    }

    public void setUsuarioEmpresaUsmempleo(UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo) {
        this.usuarioEmpresaUsmempleo = usuarioEmpresaUsmempleo;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @JoinColumn(name = "invvidentrusmemp_id", referencedColumnName = "invvidentrusmemp_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public InvitacionVideoEntrevistaUsmEmpleo getInvitacionVideoEntrevistaUsmEmpleo() {
        return invitacionVideoEntrevistaUsmEmpleo;
    }

    public void setInvitacionVideoEntrevistaUsmEmpleo(InvitacionVideoEntrevistaUsmEmpleo invitacionVideoEntrevistaUsmEmpleo) {
        this.invitacionVideoEntrevistaUsmEmpleo = invitacionVideoEntrevistaUsmEmpleo;
    }

    @Column(name = "videntr_estado")
    public VideoEntrevistaUsmEmpleoEstado getEstado() {
        return estado;
    }

    public void setEstado(VideoEntrevistaUsmEmpleoEstado estado) {
        this.estado = estado;
    }

    @Column(name = "videntr_motivo_rechazo")
    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    @JoinColumn(name = "videntr_nota_evaluacion", referencedColumnName = "codigo_evaluacion")
    @ManyToOne(fetch = FetchType.EAGER)
    public TipoEvaluacionVideoEntrevista getTipoEvalucionVideoEntrevista() {
        return tipoEvalucionVideoEntrevista;
    }

    public void setTipoEvalucionVideoEntrevista(TipoEvaluacionVideoEntrevista tipoEvalucionVideoEntrevista) {
        this.tipoEvalucionVideoEntrevista = tipoEvalucionVideoEntrevista;
    }

    @Column(name = "videntr_observacion_evaluacion")
    public String getObservacionEvaluacion() {
        return observacionEvaluacion;
    }

    public void setObservacionEvaluacion(String observacionEvaluacion) {
        this.observacionEvaluacion = observacionEvaluacion;
    }

    @Column(name = "videntr_fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
