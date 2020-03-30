package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla preferencia_usuario_usmempleo. Contiene informacion
 * sobre las preferencias sobre el portal de empleos que tiene un usuario
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "preferencia_usuario_usmempleo", schema = "empleo")
public class PreferenciaUsuarioUsmempleo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia actual
     */
    private Long usuarioId;

    /**
     * Formato del curriculum del usuario
     */
    private FormatoCvUsmempleo formatoCvUsmempleo;

    /**
     * Booleano que indica si el usuario quiere recibir ofertas por correo
     */
    private Boolean ofertasPorCorreo;

    /**
     * TODO comentar
     */
    private Boolean busqueda;

    /**
     * TODO comentar
     */
    private Boolean confirmarPostulacion;

    /**
     * TODO comentar
     */
    private Boolean resumenPorCorreo;

    /**
     * Booleano que indica si el usuario quiere mostrar su foto
     */
    private Boolean mostrarFoto;

    /**
     * Booleano que indica si el usuario quiere mostrar su expectativa salarial
     */
    private Boolean mostrarExpectativaSalario;

    /**
     * Booleano que indica si el usuario quiere mostrar su estado civil
     */
    private Boolean estadoCivil;

    /**
     * Booleano que indica si el usuario acepta las condiciones
     */
    private Boolean aceptoCondiciones;

    /**
     * Rut del usuario que realizo la ultima modificacion
     */
    private Integer rutUsuario;

    /**
     * Fecha en que se produjo la ultima modificacion del sistema
     */
    private Date fechaModificacion;

    /**
     * Booleano que indica si el usuario quiere dejar su curriculum visible
     */
    private Boolean cvVisible;

    /**
     * Fecha de inicio en que se autoriza a un usuario a postular a ciertas
     * ofertas a las que normalmente no podría acceder
     */
    private Date inicioPermisoPost;

    /**
     * Fecha de termino en que se autoriza a un usuario a postular a ciertas
     * ofertas a las que normalmente no podria acceder
     */
    private  Date finPermisoPost;

    /**
     * Booleano que indica si el usuario esta habilitado para grabar un
     * video curriculum
     */
    private Boolean habilitadoParaGrabar;

    /**
     * Encabezado que contiene una breve descripcion del profesional
     */
    private String encabezado;

    /**
     * Booleano que indica si el usuario debe actualizar sus datos en la base de datos
     */
    private Boolean actualizarDatos;

    /**
     * Booleano que indica si el usuario quiere estar suscrito a la lista de correos
     */
    private Boolean newsletter;

    /**
     * Informacion personal del exalumno; apunta mas a una descripcion propia de la persona,
     * mas que a su perfil profesional
     */
    private String infoPersonal;

    /**
     * Representa si el usuario esta disponible para realizar video conferencias
     */
    private Integer estadoVideoConferencia;

    /**
     * Usuario al cual pertenecen las preferencias de empleo
     */
    private Usuario usuario;


    public PreferenciaUsuarioUsmempleo() {
    }

    public PreferenciaUsuarioUsmempleo(PreferenciaUsuarioUsmempleo preferencia){

    }

    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuaexId) {
        this.usuarioId = usuaexId;
    }

    @JoinColumn(name = "forcvusm_id", referencedColumnName = "forcvusm_id")
    @ManyToOne
    public FormatoCvUsmempleo getFormatoCvUsmempleo() {
        return formatoCvUsmempleo;
    }

    public void setFormatoCvUsmempleo(FormatoCvUsmempleo formatoCvUsmempleo) {
        this.formatoCvUsmempleo = formatoCvUsmempleo;
    }

    @Column(name = "preusuusm_ofertas_por_correo")
    public Boolean getOfertasPorCorreo() {
        return ofertasPorCorreo;
    }

    public void setOfertasPorCorreo(Boolean preusuusmOfertasPorCorreo) {
        this.ofertasPorCorreo = preusuusmOfertasPorCorreo;
    }

    @Column(name = "preusuusm_busqueda")
    public Boolean getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(Boolean busqueda) {
        this.busqueda = busqueda;
    }

    @Column(name = "preusuusm_confir_postulacion")
    public Boolean getConfirmarPostulacion() {
        return confirmarPostulacion;
    }

    public void setConfirmarPostulacion(Boolean confirmarPostulacion) {
        this.confirmarPostulacion = confirmarPostulacion;
    }

    @Column(name = "preusuusm_resumen_por_correo")
    public Boolean getResumenPorCorreo() {
        return resumenPorCorreo;
    }

    public void setResumenPorCorreo(Boolean resumenPorCorreo) {
        this.resumenPorCorreo = resumenPorCorreo;
    }

    @Column(name = "preusuusm_mostrar_foto")
    public Boolean getMostrarFoto() {
        return mostrarFoto;
    }

    public void setMostrarFoto(Boolean preusuusmMostrarFoto) {
        this.mostrarFoto = preusuusmMostrarFoto;
    }

    @Column(name = "preusuusm_mostrar_expect_sal")
    public Boolean getMostrarExpectativaSalario() {
        return mostrarExpectativaSalario;
    }

    public void setMostrarExpectativaSalario(Boolean preusuusmMostrarExpectSal) {
        this.mostrarExpectativaSalario = preusuusmMostrarExpectSal;
    }

    @Column(name = "preusuusm_estado_civil")
    public Boolean getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Boolean preusuusmEstadoCivil) {
        this.estadoCivil = preusuusmEstadoCivil;
    }

    @Column(name = "preusuusm_acepto_condiciones")
    public Boolean getAceptoCondiciones() {
        return aceptoCondiciones;
    }

    public void setAceptoCondiciones(Boolean preusuusmAceptoCondiciones) {
        this.aceptoCondiciones = preusuusmAceptoCondiciones;
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

    @Column(name = "preusuusm_cv_visible")
    public Boolean getCvVisible() {
        return cvVisible;
    }

    public void setCvVisible(Boolean preusuusmCvVisible) {
        this.cvVisible = preusuusmCvVisible;
    }

    @Column(name = "preusuusm_inicio_permiso_post")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getInicioPermisoPost() {
        return inicioPermisoPost;
    }

    public void setInicioPermisoPost(Date inicioPermisoPost) {
        this.inicioPermisoPost = inicioPermisoPost;
    }

    @Column(name = "preusuusm_fin_permiso_post")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFinPermisoPost() {
        return finPermisoPost;
    }

    public void setFinPermisoPost(Date finPermisoPost) {
        this.finPermisoPost = finPermisoPost;
    }

    @Column(name = "preusuusm_habilitado_grabar_video_curriculos")
    public Boolean getHabilitadoParaGrabar() {
        return habilitadoParaGrabar;
    }

    public void setHabilitadoParaGrabar(Boolean habilitadoParaGrabar) {
        this.habilitadoParaGrabar = habilitadoParaGrabar;
    }

    @Column(name = "preusuusm_encabezado")
    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    @Column(name = "preusuusm_newsletter")
    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    @Column(name = "preusuusm_debe_actualizar_datos")
    public Boolean getActualizarDatos() {
        return actualizarDatos;
    }

    public void setActualizarDatos(Boolean actualizarDatos) {
        this.actualizarDatos = actualizarDatos;
    }

    @Column(name = "preusuusm_info_personal")
    public String getInfoPersonal() {
        return infoPersonal;
    }

    public void setInfoPersonal(String infoPersonal) {
        this.infoPersonal = infoPersonal;
    }

    @Column(name = "preusuusm_estado_video_conferencia")
    public Integer getEstadoVideoConferencia() {
        return estadoVideoConferencia;
    }

    public void setEstadoVideoConferencia(Integer estadoVideoConferencia) {
        this.estadoVideoConferencia = estadoVideoConferencia;
    }

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = true)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaUsuarioUsmempleo)) {
            return false;
        }
        PreferenciaUsuarioUsmempleo other = (PreferenciaUsuarioUsmempleo) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.utfsm.redexalumnos.crm.entities.PreferenciaUsuarioUsmempleoEntityRepository[usuaexId=" + usuarioId + "]";
    }

    @Transient
    public void setDefaultValues(Long id, FormatoCvUsmempleo f){
        this.usuarioId = id;
        this.inicioPermisoPost = null;
        this.finPermisoPost = null;
        this.formatoCvUsmempleo = f;
        this.estadoVideoConferencia = 0;
        this.ofertasPorCorreo = true;
        this.busqueda = false;
        this.confirmarPostulacion = false;
        this.mostrarFoto = true;
        this.mostrarExpectativaSalario = false;
        this.estadoCivil = false;
        this.resumenPorCorreo = false;
        this.aceptoCondiciones = true;
        this.cvVisible = true;
        this.actualizarDatos = false;
        this.newsletter = true;
        this.habilitadoParaGrabar = true;

    }
}