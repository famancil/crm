package crm.entities;



import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla aexa.recado_contacto
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "recado_contacto", schema = "aexa")
@IdClass(RecadoContactoPK.class)
public class RecadoContacto {

    /**
     * Identificador del {@link crm.entities.Usuario} asociado al {@link crm.entities.RecadoContacto}
     */
    private Long usuarioId;

    /**
     * Fecha del {@link crm.entities.RecadoContacto}
     */
    private Date fecha;

    /**
     * {@link Usuario} asociado al {@link RecadoContacto}
     */
    private Usuario usuario;

    /**
     * Tipo de mensaje del Recado
     */
    private TipoMensajeRecado tipoMensajeRecado;

    /**
     * Tipo de vigencia del Recado
     */
    private TipoVigencia tipoVigencia;

    /**
     * Tipo de Recado
     */
    private TipoRecado tipoRecado;

    /**
     * Privacidad Recado
     */
    private PrivacidadRecado privacidadRecado;

    /*
    * TODO comentar
    */
    private Usuario usuUsuario;

    /**
     * Descripción del recado
     */
    private String descripcion;

    /**
     * TODO comentar
     */
    private Date futuroContactoFecha;

    /**
     * TODO comentar
     */
    private String futuroContactoComentario;

    /**
     * Fecha de modificacion del Recado en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica el Recado en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "reccon_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @JoinColumn(name = "usu_usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne
    public Usuario getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(Usuario usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "cod_mensaje_recado", referencedColumnName = "cod_mensaje_recado")
    @ManyToOne(optional = false)
    public TipoMensajeRecado getTipoMensajeRecado() {
        return tipoMensajeRecado;
    }

    public void setTipoMensajeRecado(TipoMensajeRecado tipoMensajeRecado) {
        this.tipoMensajeRecado = tipoMensajeRecado;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne(optional = false)
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @JoinColumn(name = "cod_recado", referencedColumnName = "cod_recado")
    @ManyToOne(optional = false)
    public TipoRecado getTipoRecado() {
        return tipoRecado;
    }

    public void setTipoRecado(TipoRecado tipoRecado) {
        this.tipoRecado = tipoRecado;
    }

    @JoinColumn(name = "prirec_id", referencedColumnName = "prirec_id")
    @ManyToOne
    public PrivacidadRecado getPrivacidadRecado() {
        return privacidadRecado;
    }

    public void setPrivacidadRecado(PrivacidadRecado privacidadRecado) {
        this.privacidadRecado = privacidadRecado;
    }

    @Column(name = "reccon_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "reccon_futcontac_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFuturoContactoFecha() {
        return futuroContactoFecha;
    }

    public void setFuturoContactoFecha(Date futuroContactoFecha) {
        this.futuroContactoFecha = futuroContactoFecha;
    }

    @Column(name = "reccon_futcontac_comentario")
    public String getFuturoContactoComentario() {
        return futuroContactoComentario;
    }

    public void setFuturoContactoComentario(String futuroContactoComentario) {
        this.futuroContactoComentario = futuroContactoComentario;
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
