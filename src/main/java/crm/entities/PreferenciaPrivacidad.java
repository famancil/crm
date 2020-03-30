package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla preferencia_privacidad. Contiene informacion
 * referente a las preferencias sobre compartir los datos personales de un usuario.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "preferencia_privacidad")
@IdClass(PreferenciaPrivacidadPK.class)
public class PreferenciaPrivacidad implements Serializable {

    /**
     * Identificador de la instancia actual
     */
    private Long usuarioId;

    /**
     * Codigo de institucion
     */
    private Short codInstitucion;

    /**
     * {@link Usuario} asociado a las presentes preferencias de privacidad.
     */
    private Usuario usuario;

    /**
     * {@link Institucion} ...? (Por completar).
     */
    private Institucion institucion;

    /**
     * Booleano que indica si el usuario desea o no compartir sus datos con instituciones
     * de educación superior.
     */
    private Boolean ComDatIes;

    /**
     * Booleano que indica si el usuario desea o no compartir sus datos con su departamento
     * de origen.
     */
    /*
    private Boolean ComDatDepto;
    */

    /**
     * Booleano que indica si el usuario desea o no compartir sus datos con sus colegas.
     */
    //private Boolean ComDatColega;

    /**
     * Fecha de creacion del registro.
     */
    private Date fechaCreacion;

    /**
     * Fecha de modificacion de esta instancia.
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;



    public PreferenciaPrivacidad() {
    }

    public PreferenciaPrivacidad(PreferenciaPrivacidad clone){

    }

    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "cod_institucion")
    public Short getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Short codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    @Column(name = "prepri_comdat_ies")
    public Boolean getComDatIes() {
        return ComDatIes;
    }

    public void setComDatIes(Boolean comDatIes) {
        ComDatIes = comDatIes;
    }

    /*
    @Column(name = "prepri_comdat_depto")
    public Boolean getComDatDepto() {
        return ComDatDepto;
    }

    public void setComDatDepto(Boolean comDatDepto) {
        ComDatDepto = comDatDepto;
    }
    /*

    /*
    @Column(name = "prepri_comdat_colega")
    public Boolean getComDatColega() {
        return ComDatColega;
    }

    public void setComDatColega(Boolean comDatColega) {
        ComDatColega = comDatColega;
    }
    */

    @Column(name = "fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Column(name = "fecha_modificacion")
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

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = true)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion", insertable = false, updatable = false)
    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        PreferenciaPrivacidad pref = (PreferenciaPrivacidad) super.clone();
        return pref;
    }
}
