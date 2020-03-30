package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad que representa un video curriculo dentro del sistema. Los video curriculos son utilizados para que los
 * usuarios puedan responder tanto a las preguntas generales para la postulacion a un empleo como tambien a preguntas
 * especificas que las empresas puedan requerir que los usuarios respondan para una oferta laboral en particular.
 *
 * @author dacuna
 * @since 1.0
 */
@Entity
@Table(name = "video_curriculo_usuario_aexa", schema="video_curriculos_empleos")
public class VideoCurriculoUsuario {

    /**
     * Identificador primario del video curriculo
     */
    private Long id;

    /**
     * Nombre del video curriculo especificado por el usuario al que pertenece
     */
    private String nombre;

    /**
     * Descripcion del video curriculo especificada por el usuario al que pertenece
     */
    private String descripcion;

    /**
     * Usuario de tipo {@link Usuario} quien es propietario del video
     */
    private Usuario usuario;

    /**
     * Oferta laboral a la que postulo el usuario postulante para ser contactado por la empresa
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Idioma del video curriculo grabado por el usuario
     */
    private IdiomaVideoCurriculo idioma;

    /**
     * Flag que indica si es que el video es visible (se encuentra publicado) al sistema (true) o no (false).
     */
    private Boolean publicado = false;

    /**
     * Fecha de creacion del video
     */
    private Date fechaCreacion;

    /**
     * Fecha de publicacion del video
     */
    private Date fechaPublicacion;

    /**
     * Indica si el video curriculo ha sido editado mediante el editor de videos que ofrece el sistema
     */
    private Boolean fueEditado = false;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_curriculo_id_seq")
    @SequenceGenerator(name = "video_curriculo_id_seq", sequenceName = "video_curriculo_id_seq", allocationSize=1)
    @Column(name = "video_curriculo_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "video_curriculo_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "video_curriculo_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }


    @Column(name = "video_curriculo_idioma")
    public IdiomaVideoCurriculo getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaVideoCurriculo idioma) {
        this.idioma = idioma;
    }

    @Column(name = "video_curriculo_publicado")
    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    @Column(name = "video_curriculo_fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Column(name = "video_curriculo_fecha_publicacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Column(name = "video_curriculo_fue_editado")
    public Boolean getFueEditado() {
        return fueEditado;
    }

    public void setFueEditado(Boolean fueEditado) {
        this.fueEditado = fueEditado;
    }
}

