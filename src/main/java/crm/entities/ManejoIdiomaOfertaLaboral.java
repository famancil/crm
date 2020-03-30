package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.manejo_idioma_ofe_exalumno.
 * Contiene los requisitos de idioma para una oferta laboral del portal de empleos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "manejo_idioma_ofe_exalumno", schema="empleo")
@IdClass(ManejoIdiomaOfertaLaboralPK.class)
public class ManejoIdiomaOfertaLaboral {

    /**
     * Identificador de la {@link OfertaLaboralUsmempleo} asociada a la {@link ManejoIdiomaOfertaLaboral}
     */
    private Long idOfertaLaboralUsmepleo;

    /**
     * Identificador del {@link Idioma} asociada a la {@link ManejoIdiomaOfertaLaboral}
     */
    private Short codIdioma;

    /**
     * {@link crm.entities.OfertaLaboralUsmempleo} requerido en la {@link crm.entities.ManejoIdiomaOfertaLaboral}
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * {@link crm.entities.Idioma} requerido en la {@link crm.entities.ManejoIdiomaOfertaLaboral}
     */
    private Idioma idioma;

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
    * Requiere de certificación del Idioma
    */
    private boolean requiereCertificado;

    /**
     * Fecha de modificacion del antecedente educacional en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica el antecedente educacional en la BD
     */
    private Integer rutUsuario;





    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmepleo() {
        return idOfertaLaboralUsmepleo;
    }
    public void setIdOfertaLaboralUsmepleo(Long idOfertaLaboralUsmepleo) {
        this.idOfertaLaboralUsmepleo = idOfertaLaboralUsmepleo;
    }

    @Id
    @Column(name = "cod_idioma")
    public Short getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(Short codIdioma) {
        this.codIdioma = codIdioma;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @JoinColumn(name = "cod_idioma", referencedColumnName = "cod_idioma", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }


    @Column(name = "manidiofeexa_conversacion")
    public Short getNivelConversacion() {
        return nivelConversacion;
    }

    public void setNivelConversacion(Short nivelConversacion) {
        this.nivelConversacion = nivelConversacion;
    }

    @Column(name = "manidiofeexa_escritura")
    public Short getNivelEscritura() {
        return nivelEscritura;
    }

    public void setNivelEscritura(Short nivelEscritura) {
        this.nivelEscritura = nivelEscritura;
    }

    @Column(name = "manidiofeexa_traduccion")
    public Short getNivelTraduccion() {
        return nivelTraduccion;
    }

    public void setNivelTraduccion(Short nivelTraduccion) {
        this.nivelTraduccion = nivelTraduccion;
    }

    @Column(name = "manidiofeexa_certificado")
    public boolean requiereCertificado() {
        return requiereCertificado;
    }

    public void setRequiereCertificado(boolean requiereCertificado) {
        this.requiereCertificado = requiereCertificado;
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
