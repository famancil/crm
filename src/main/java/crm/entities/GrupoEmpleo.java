package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.grupo_usmempleo.
 * Contiene los grupos y subgrupos del portal de empleos';
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "grupo_usmempleo", schema = "empleo")
public class GrupoEmpleo {

    /**
     * Id del grupo
     */
    private Short id;

    /**
     * {@link TipoTemaUsmempleo} del grupo
     */
    private TipoTemaUsmempleo tipoTemaUsmempleo;

    /**
     * {@link Institucion} del grupo
     */
    private Institucion institucion;

    /**
     * Nombre del grupo
     */
    private String nombre;

    /**
     * Descripcion del grupo
     */
    private String descripcion;

    /**
     * Keyword del grupo
     */
    private String keywords;

    /**
     * Nombre del grupo
     */
    private String bannerEfecto;

    /**
     * Nombre del grupo
     */
    private Integer bannerTiempo;

    /**
     * Nombre del grupo
     */
    private String urlCss;

    /**
     * Nombre del grupo
     */
    private String urlLogoDer;

    /**
     * Nombre del grupo
     */
    private String nombreUnico;

    /**
     * Fecha de modificacion de la carrera en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica la carrera en la BD
     */
    private Integer rutUsuario;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_usmempleo_seq")
    @SequenceGenerator(name = "grupo_usmempleo_seq", sequenceName = "empleo.grupo_usmempleo_seq", allocationSize = 1)
    @Column(name = "gruusm_id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_tema_usmempleo", referencedColumnName = "cod_tema_usmempleo", insertable = false, updatable = false)
    @ManyToOne
    public TipoTemaUsmempleo getTipoTemaUsmempleo() {
        return tipoTemaUsmempleo;
    }

    public void setTipoTemaUsmempleo(TipoTemaUsmempleo tipoTemaUsmempleo) {
        this.tipoTemaUsmempleo = tipoTemaUsmempleo;
    }

    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion", insertable = false, updatable = true)
    @ManyToOne(optional = false)
    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    @Column(name = "gruusm_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "gruusm_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "gruusm_keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "gruusm_banner_efecto")
    public String getBannerEfecto() {
        return bannerEfecto;
    }

    public void setBannerEfecto(String bannerEfecto) {
        this.bannerEfecto = bannerEfecto;
    }

    @Column(name = "gruusm_banner_tiempo")
    public Integer getBannerTiempo() {
        return bannerTiempo;
    }

    public void setBannerTiempo(Integer bannerTiempo) {
        this.bannerTiempo = bannerTiempo;
    }

    @Column(name = "gruusm_url_css")
    public String getUrlCss() {
        return urlCss;
    }

    public void setUrlCss(String urlCss) {
        this.urlCss = urlCss;
    }

    @Column(name = "gruusm_url_logo_der")
    public String getUrlLogoDer() {
        return urlLogoDer;
    }

    public void setUrlLogoDer(String urlLogoDer) {
        this.urlLogoDer = urlLogoDer;
    }

    @Column(name = "gruusm_nombre_unico")
    public String getNombreUnico() {
        return nombreUnico;
    }

    public void setNombreUnico(String nombreUnico) {
        this.nombreUnico = nombreUnico;
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
