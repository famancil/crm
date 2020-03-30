package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa una invitacion a una video entrevista realizada por una empresa representada por un usuario
 * dentro del portal de empleos ({@link UsuarioEmpresaUsmempleo}) hacia un usuario
 * postulante a una oferta laboral publicada por dicha empresa. La invitacion debe ser aceptada por el usuario antes
 * de llevar a cabo la entrevista.
 *
 * @author dacuna
 * @since 1.0
 */
@Entity
@Table(name = "invitacion_video_entrevista_usmempleo", schema="video_curriculos_empleos")
public class InvitacionVideoEntrevistaUsmEmpleo {

    /**
     * Identificador de la invitacion
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
     * Fecha de la invitacion a la video entrevista
     */
    private Date fechaInvitacion;

    /**
     * Estado de la invitacion. Un usuario invitado puede aceptar o rechazar una invitacion realizada.
     */
    private InvitacionVideoEntrevistaUsmEmpleoEstado estadoInvitacion;

    /**
     * Fecha en la que el usuario invitado contesto la invitacion
     */
    private Date fechaRespuesta;

    /**
     * Si el usuario invitado rechaza la invitacion puede indicar un motivo del porque realizo el rechazo
     */
    private String motivoRechazo;

    /**
     * Si el usuario invitado rechaza la invitacion ademas de un motivo debe agregar una observacion al rechazo
     */
    private String observacion;

    public InvitacionVideoEntrevistaUsmEmpleo() {

    }

    public InvitacionVideoEntrevistaUsmEmpleo(Usuario usuario, UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo,
                                     OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.usuario = usuario;
        this.usuarioEmpresaUsmempleo = usuarioEmpresaUsmempleo;
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitacion_video_entrevista_id_seq")
    @SequenceGenerator(name = "invitacion_video_entrevista_id_seq", sequenceName = "invitacion_video_entrevista_id_seq", allocationSize=1)
    @Column(name = "invvidentrusmemp_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "invvidentrusmemp_fecha_invitacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInvitacion() {
        return fechaInvitacion;
    }

    public void setFechaInvitacion(Date fechaInvitacion) {
        this.fechaInvitacion = fechaInvitacion;
    }

    @Column(name = "invvidentrusmemp_estado_invitacion")
    public InvitacionVideoEntrevistaUsmEmpleoEstado getEstadoInvitacion() {
        return estadoInvitacion;
    }

    public void setEstadoInvitacion(InvitacionVideoEntrevistaUsmEmpleoEstado estadoInvitacion) {
        this.estadoInvitacion = estadoInvitacion;
    }

    @Column(name = "invvidentrusmemp_fecha_respuesta")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    @Column(name = "invvidentrusmemp_motivo_rechazo")
    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    @Column(name = "invvidentrusmemp_observacion")
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }



}
