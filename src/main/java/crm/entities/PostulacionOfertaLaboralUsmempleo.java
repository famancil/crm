package crm.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entidad correspondiente a la tabla empleo.postulacion_ofe_lab_usmempleo
 * Contiene las {@link crm.entities.PostulacionOfertaLaboralUsmempleo} a las {@link crm.entities.OfertaLaboralUsmempleo}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 * TODO cambiar nombre de los atributos
 */
@Entity
@Table(name = "postulacion_ofe_lab_usmempleo", schema = "empleo")
@IdClass(PostulacionOfertaLaboralUsmempleoPK.class)
public class PostulacionOfertaLaboralUsmempleo {

    /**
     * Identificador del {@link crm.entities.OfertaLaboralUsmempleo} asociada a la {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     */
    private Long ofertaLaboralUsmempleoId;

    /**
     * Id del {@link crm.entities.Usuario} asociado a la {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     */
    private Long usuarioId;

    /**
     * Instancia de la entidad {@link crm.entities.OfertaLaboralUsmempleo} asociada a la instancia actual
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Instancia de la entidad {@link crm.entities.Usuario} asociada a la instancia actual
     */
    private Usuario usuario;

    /**
     * Instancia de la entidad {@link crm.entities.Usuario} asociada a la instancia actual
     */
    private FormatoCvUsmempleo formatoCvUsmempleo;

    /**
     *
     */
    private String cartaPres;

    /**
     * TODO preguntar
     */
    private Integer expecSalarial;

    /**
     * TODO preguntar
     */
    private Boolean excesoRentaEsp;

    /**
     * TODO preguntar
     */
    private String descripcion;

    /**
     * TODO preguntar
     */
    private Boolean visto;

    /**
     * TODO preguntar
     */
    private Boolean vistoContacto;

    /**
     * TODO preguntar
     */
    private Date fechaPostulacion;

    /**
     * TODO preguntar
     */
    private Boolean entrevista;

    /**
     * TODO preguntar
     */
    private Boolean cumplePerfil;

    /**
     * TODO preguntar
     */
    private Boolean contratado;

    /**
     * TODO preguntar
     */
    private Integer aptitud;

    /**
     * TODO preguntar
     */
    private String comentario;

    /**
     * TODO preguntar
     */
    private Short duracionProceso;

    /**
     * TODO preguntar
     */
    private Short evaluacion;

    /**
     * TODO preguntar
     */
    private Boolean fueraRango;

    /**
     * TODO preguntar
     */
    private Boolean sobrecalificado;

    /**
     * TODO preguntar
     */
    private Boolean subcalificado;

    /**
     * TODO preguntar
     */
    private Boolean malCurriculum;

    /**
     * TODO preguntar
     */
    private Short matchSitEst;

    /**
     * TODO preguntar
     */
    private Short matchDisponi;

    /**
     * TODO preguntar
     */
    private Short matchExperiencia;

    /**
     * TODO preguntar
     */
    private Short matchExpectativa;

    /**
     * TODO preguntar
     */
    private Short matchIdioma;

    /**
     * Fecha en que la {@link crm.entities.Empresa} revisó el Curriculum
     */
    private Date cvVistoEmpresa;

    /**
     * Respuesta a la pregunta: ¿Esta satisfecho con el proceso de reclutamiento hecho por la empresa?
     */
    private Short evaluacionProcesoEmpresa;

    /**
     * Rut del {@link crm.entities.Usuario} que realizo la ultima modificacion en la instancia actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */
    private Date fechaModificacion;

    /**
     * Respuestas a las Preguntas de la Oferta Laboral
     */
    private List<RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaOfertaLaboralExalumnoList;

    /**
     * Lista de las EncuestaPostulacionLaboral asociadas
     */
    private EncuestaPostulacionLaboral encuestaPostulacionLaboral;

    /**
     * Respuestas a las Preguntas de la Oferta Laboral
     */
    private List<PostulacionArchivosAdjuntos> PostulacionArchivosAdjuntosList;





    public PostulacionOfertaLaboralUsmempleo() {
    }


    public PostulacionOfertaLaboralUsmempleo(PostulacionOfertaLaboralUsmempleo postulacion) {
        this.ofertaLaboralUsmempleoId = postulacion.getOfertaLaboralUsmempleoId();
        this.usuarioId = postulacion.getUsuarioId();
        this.formatoCvUsmempleo = postulacion.getFormatoCvUsmempleo();
        this.cartaPres = postulacion.getCartaPres();
        this.expecSalarial = postulacion.getExpecSalarial();
        this.excesoRentaEsp = postulacion.getExcesoRentaEsp();
        this.descripcion = postulacion.getDescripcion();
        this.visto = postulacion.getVisto();
        this.vistoContacto = postulacion.getVistoContacto();
        this.fechaPostulacion = postulacion.getFechaPostulacion();
        this.entrevista = postulacion.getEntrevista();
        this.cumplePerfil = postulacion.getCumplePerfil();
        this.contratado = postulacion.getContratado();
        this.aptitud = postulacion.getAptitud();
        this.comentario = postulacion.getComentario();
        this.duracionProceso = postulacion.getDuracionProceso();
        this.evaluacion = postulacion.getEvaluacion();
        this.fueraRango = postulacion.getFueraRango();
        this.sobrecalificado = postulacion.getSobrecalificado();
        this.subcalificado = postulacion.getSubcalificado();
        this.malCurriculum = postulacion.getMalCurriculum();
        this.matchSitEst = postulacion.getMatchSitEst();
        this.matchDisponi = postulacion.getMatchDisponi();
        this.matchExperiencia = postulacion.getMatchExperiencia();
        this.matchExpectativa = postulacion.getMatchExpectativa();
        this.matchIdioma = postulacion.getMatchIdioma();
        this.cvVistoEmpresa = postulacion.getCvVistoEmpresa();
        this.evaluacionProcesoEmpresa = postulacion.getEvaluacionProcesoEmpresa();
        this.rutUsuario = postulacion.getRutUsuario();
        this.fechaModificacion = postulacion.getFechaModificacion();

    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getOfertaLaboralUsmempleoId() {
        return ofertaLaboralUsmempleoId;
    }

    public void setOfertaLaboralUsmempleoId(Long ofertaLaboralUsmempleoId) {
        this.ofertaLaboralUsmempleoId = ofertaLaboralUsmempleoId;
    }

    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "forcvusm_id", referencedColumnName = "forcvusm_id", insertable = false, updatable = false)
    @ManyToOne
    public FormatoCvUsmempleo getFormatoCvUsmempleo() {
        return formatoCvUsmempleo;
    }

    public void setFormatoCvUsmempleo(FormatoCvUsmempleo formatoCvUsmempleo) {
        this.formatoCvUsmempleo = formatoCvUsmempleo;
    }

    @Column(name = "posofelabusm_carta_pres")
    public String getCartaPres() {
        return cartaPres;
    }

    public void setCartaPres(String cartaPres) {
        this.cartaPres = cartaPres;
    }

    @Column(name = "posofelabusm_expec_salarial")
    public Integer getExpecSalarial() {
        return expecSalarial;
    }

    public void setExpecSalarial(Integer expecSalarial) {
        this.expecSalarial = expecSalarial;
    }

    @Column(name = "posofelabusm_exceso_renta_esp")
    public Boolean getExcesoRentaEsp() {
        return excesoRentaEsp;
    }

    public void setExcesoRentaEsp(Boolean excesoRentaEsp) {
        this.excesoRentaEsp = excesoRentaEsp;
    }

    @Column(name = "posofelabusm_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "posofelabusm_visto")
    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    @Column(name = "posofelabusm_visto_contacto")
    public Boolean getVistoContacto() {
        return vistoContacto;
    }

    public void setVistoContacto(Boolean vistoContacto) {
        this.vistoContacto = vistoContacto;
    }

    @Column(name = "posofelabusm_fecha_postulacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(Date fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    @Column(name = "posofelabusm_entrevista")
    public Boolean getEntrevista() {
        return entrevista;
    }

    public void setEntrevista(Boolean entrevista) {
        this.entrevista = entrevista;
    }

    @Column(name = "posofelabusm_cumple_perfil")
    public Boolean getCumplePerfil() {
        return cumplePerfil;
    }

    public void setCumplePerfil(Boolean cumplePerfil) {
        this.cumplePerfil = cumplePerfil;
    }

    @Column(name = "posofelabusm_contratado")
    public Boolean getContratado() {
        return contratado;
    }

    public void setContratado(Boolean contratado) {
        this.contratado = contratado;
    }

    @Column(name = "posofelabusm_aptitud")
    public Integer getAptitud() {
        return aptitud;
    }

    public void setAptitud(Integer aptitud) {
        this.aptitud = aptitud;
    }

    @Column(name = "posofelabusm_comentario")
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Column(name = "posofelabusm_duracion_proceso")
    public Short getDuracionProceso() {
        return duracionProceso;
    }

    public void setDuracionProceso(Short duracionProceso) {
        this.duracionProceso = duracionProceso;
    }

    @Column(name = "posofelabusm_evaluacion")
    public Short getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Short evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Column(name = "posofelabusm_fuera_rango")
    public Boolean getFueraRango() {
        return fueraRango;
    }

    public void setFueraRango(Boolean fueraRango) {
        this.fueraRango = fueraRango;
    }

    @Column(name = "posofelabusm_sobrecalificado")
    public Boolean getSobrecalificado() {
        return sobrecalificado;
    }

    public void setSobrecalificado(Boolean sobrecalificado) {
        this.sobrecalificado = sobrecalificado;
    }

    @Column(name = "posofelabusm_subcalificado")
    public Boolean getSubcalificado() {
        return subcalificado;
    }

    public void setSubcalificado(Boolean subcalificado) {
        this.subcalificado = subcalificado;
    }

    @Column(name = "posofelabsum_mal_curriculum")
    public Boolean getMalCurriculum() {
        return malCurriculum;
    }

    public void setMalCurriculum(Boolean malCurriculum) {
        this.malCurriculum = malCurriculum;
    }

    @Column(name = "posofelabusm_match_sit_est")
    public Short getMatchSitEst() {
        return matchSitEst;
    }

    public void setMatchSitEst(Short matchSitEst) {
        this.matchSitEst = matchSitEst;
    }

    @Column(name = "posofelabusm_match_disponi")
    public Short getMatchDisponi() {
        return matchDisponi;
    }

    public void setMatchDisponi(Short matchDisponi) {
        this.matchDisponi = matchDisponi;
    }

    @Column(name = "posofelabusm_match_experiencia")
    public Short getMatchExperiencia() {
        return matchExperiencia;
    }

    public void setMatchExperiencia(Short matchExperiencia) {
        this.matchExperiencia = matchExperiencia;
    }

    @Column(name = "posofelabusm_match_expectativa")
    public Short getMatchExpectativa() {
        return matchExpectativa;
    }

    public void setMatchExpectativa(Short matchExpectativa) {
        this.matchExpectativa = matchExpectativa;
    }

    @Column(name = "posofelabusm_match_idioma")
    public Short getMatchIdioma() {
        return matchIdioma;
    }

    public void setMatchIdioma(Short matchIdioma) {
        this.matchIdioma = matchIdioma;
    }

    @Column(name = "posofelabusm_cv_visto_empresa")
    public Date getCvVistoEmpresa() {
        return cvVistoEmpresa;
    }

    public void setCvVistoEmpresa(Date cvVistoEmpresa) {
        this.cvVistoEmpresa = cvVistoEmpresa;
    }

    @Column(name = "posofelabusm_evaluacion_proceso_empresa")
    public Short getEvaluacionProcesoEmpresa() {
        return evaluacionProcesoEmpresa;
    }

    public void setEvaluacionProcesoEmpresa(Short evaluacionProcesoEmpresa) {
        this.evaluacionProcesoEmpresa = evaluacionProcesoEmpresa;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postulacionOfertaLaboralUsmempleo")
    public List<RespuestaPreguntaOfertaLaboralExalumno> getRespuestaPreguntaOfertaLaboralExalumnoList() {
        return respuestaPreguntaOfertaLaboralExalumnoList;
    }

    public void setRespuestaPreguntaOfertaLaboralExalumnoList(List<RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaOfertaLaboralExalumnoList) {
        this.respuestaPreguntaOfertaLaboralExalumnoList = respuestaPreguntaOfertaLaboralExalumnoList;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "postulacionOfertaLaboralUsmempleo")
    public EncuestaPostulacionLaboral getEncuestaPostulacionLaboral() {
        return encuestaPostulacionLaboral;
    }

    public void setEncuestaPostulacionLaboral(EncuestaPostulacionLaboral encuestaPostulacionLaboral) {
        this.encuestaPostulacionLaboral = encuestaPostulacionLaboral;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postulacionOfertaLaboralUsmempleo")
    public List<PostulacionArchivosAdjuntos> getPostulacionArchivosAdjuntosList() {
        return PostulacionArchivosAdjuntosList;
    }

    public void setPostulacionArchivosAdjuntosList(List<PostulacionArchivosAdjuntos> postulacionArchivosAdjuntosList) {
        PostulacionArchivosAdjuntosList = postulacionArchivosAdjuntosList;
    }
}
