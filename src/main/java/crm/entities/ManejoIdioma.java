package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla dbo.manejo_idioma.
 * Contiene los Idiomas que manejan los usuarios y que han registrado en el sistema
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "manejo_idioma")
public class ManejoIdioma {


    /*
    * Identificador del Manejo de Idioma
    */
    private Long id;

    /*
    * Nivel de manejo de Conversación del Idioma
    */
    private Short nivelConversacion;

    /*
    * Nivel de manejo de Escritura del Idioma
    */
    private Short nivelEscritura;

    /*
    * Nivel de manejo de Traducción del Idioma
    */
    private Short nivelTraduccion;

    /*
    * Existencia de certificación del Idioma
    */
    private boolean poseeCertificado;

    /*
    * Manejo Nativo del Idioma
    */
    private boolean nativo;

    /*
    * Idioma Asociado al Manejo Idioma
    */
    private Idioma idioma;

    /*
    * Usuario asociado al antecedente educacional
    */
    private Usuario usuario;

    /**
     * Fecha de modificacion del antecedente educacional en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica el antecedente educacional en la BD
     */
    private Integer rutUsuario;

    @Id
    @Column(name = "manidi_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "manidi_nivel_conversacion")
    public Short getNivelConversacion() {
        return nivelConversacion;
    }

    public void setNivelConversacion(Short nivelConversacion) {
        this.nivelConversacion = nivelConversacion;
    }

    @Column(name = "manidi_nivel_escritura")
    public Short getNivelEscritura() {
        return nivelEscritura;
    }

    public void setNivelEscritura(Short nivelEscritura) {
        this.nivelEscritura = nivelEscritura;
    }

    @Column(name = "manidi_nivel_traduccion")
    public Short getNivelTraduccion() {
        return nivelTraduccion;
    }

    public void setNivelTraduccion(Short nivelTraduccion) {
        this.nivelTraduccion = nivelTraduccion;
    }

    @Column(name = "manidi_certificado")
    public boolean getPoseeCertificado() {
        return poseeCertificado;
    }

    public void setPoseeCertificado(boolean poseeCertificado) {
        this.poseeCertificado = poseeCertificado;
    }

    @Column(name = "manidi_nativo")
    public boolean getNativo() {
        return nativo;
    }

    public void setNativo(boolean nativo) {
        this.nativo = nativo;
    }

    @JoinColumn(name = "cod_idioma", referencedColumnName = "cod_idioma")
    @ManyToOne(optional = false)
    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
