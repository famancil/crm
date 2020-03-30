package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla crawler_ofertas.oferta_crawled.
 * TODO comentar
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "oferta_crawled", schema = "crawler_ofertas")
public class OfertaCrawled {

    /**
     * Id de OfertaCrawled
     */
    private Long id;

    /**
     * TODO comentar
     */
    private String titulo;

    /**
     * TODO comentar
     */
    private String urlPortal;

    /**
     * TODO comentar
     */
    private String empresa;

    /**
     * TODO comentar
     */
    private String lugar;

    /**
     * TODO comentar
     */
    private String jornada;

    /**
     * TODO comentar
     */
    private String fecha;

    /**
     * TODO comentar
     */
    private String imagen;

    /**
     * TODO comentar
     */
    private String portal;

    /**
     * TODO comentar
     */
    private Boolean procesada;

    /**
     * TODO comentar
     */
    private Boolean descartada;

    /**
     * TODO comentar
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private Date fechaRegistro;

    /**
     * TODO comentar
     */
    private Date fechaActualizacion;

    /**
     * TODO comentar
     */
    private String comentario;

    /**
     * TODO comentar
     */
    private TipoProcesamientoOferta tipoProcesamientoOferta;





    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oferta_crawled_seq_gen")
    @SequenceGenerator(name = "oferta_crawled_seq_gen", sequenceName = "crawler_ofertas.oferta_crawled_id_seq", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "url_portal")
    public String getUrlPortal() {
        return urlPortal;
    }

    public void setUrlPortal(String urlPortal) {
        this.urlPortal = urlPortal;
    }

    @Column(name = "empresa")
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Column(name = "lugar")
    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Column(name = "jornada")
    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Column(name = "fecha")
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Column(name = "imagen")
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Column(name = "portal")
    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    @Column(name = "procesada")
    public Boolean getProcesada() {
        return procesada;
    }

    public void setProcesada(Boolean procesada) {
        this.procesada = procesada;
    }

    @Column(name = "descartada")
    public Boolean getDescartada() {
        return descartada;
    }

    public void setDescartada(Boolean descartada) {
        this.descartada = descartada;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Column(name = "comentario")
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @JoinColumn(name = "cod_tipo_procesamiento_oferta", referencedColumnName = "cod_tipo_procesamiento_oferta", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public TipoProcesamientoOferta getTipoProcesamientoOferta() {
        return tipoProcesamientoOferta;
    }

    public void setTipoProcesamientoOferta(TipoProcesamientoOferta tipoProcesamientoOferta) {
        this.tipoProcesamientoOferta = tipoProcesamientoOferta;
    }
}
