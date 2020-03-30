package crm.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entidad correspondiente a la tabla empleo.postulacion_archivos_adjuntos
 * Contiene la lista de archivos que un usuario_aexa adjunta a una oferta laboral
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 * TODO cambiar nombre de los atributos
 */
@Entity
@Table(name = "postulacion_archivos_adjuntos", schema = "empleo")
@IdClass(PostulacionArchivosAdjuntosPK.class)
public class PostulacionArchivosAdjuntos {


    /**
     * Identificador del {@link OfertaLaboralUsmempleo} asociada a la {@link PostulacionArchivosAdjuntos}
     */
    private Long idOfertaLaboralUsmempleo;

    /**
     * Id del {@link Usuario} asociado a la {@link PostulacionArchivosAdjuntos}
     */
    private Long idUsuario;

    /**
     * Id del {@link crm.entities.ArchivosAdjuntos} asociado a la {@link PostulacionArchivosAdjuntos}
     */
    private Long idArchivoAdjunto;

    /**
     * Instancia de la entidad {@link crm.entities.OfertaLaboralUsmempleo} asociada a la instancia actual
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Instancia de la entidad {@link crm.entities.Usuario} asociada a la instancia actual
     */
    private Usuario usuario;

    /**
     * Instancia de la entidad {@link crm.entities.ArchivosAdjuntos} asociada a la instancia actual
     */
    private ArchivosAdjuntos archivosAdjuntos;

    /**
     * TODO comentar
     */
    private Date fechaPostulacionAdjunto;

    /**
     * Rut del {@link Usuario} que realizo la ultima modificacion en la instancia actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */
    private Date fechaModificacion;

    /**
     * Postulacion asociada a la respuesta de la pregunta
     */
    private PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboralUsmempleo;


    public PostulacionArchivosAdjuntos() {
    }


    public PostulacionArchivosAdjuntos(PostulacionArchivosAdjuntos postulacionArchivo) {
        this.idOfertaLaboralUsmempleo = postulacionArchivo.getIdOfertaLaboralUsmempleo();
        this.idUsuario = postulacionArchivo.getIdUsuario();
        this.idArchivoAdjunto = postulacionArchivo.getIdArchivoAdjunto();
        this.fechaPostulacionAdjunto = postulacionArchivo.getFechaPostulacionAdjunto();
        this.rutUsuario = postulacionArchivo.getRutUsuario();
        this.fechaModificacion = postulacionArchivo.getFechaModificacion();
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmempleo() {
        return idOfertaLaboralUsmempleo;
    }

    public void setIdOfertaLaboralUsmempleo(Long idOfertaLaboralUsmempleo) {
        this.idOfertaLaboralUsmempleo = idOfertaLaboralUsmempleo;
    }

    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "arcadjexa_id")
    public Long getIdArchivoAdjunto() {
        return idArchivoAdjunto;
    }

    public void setIdArchivoAdjunto(Long idArchivoAdjunto) {
        this.idArchivoAdjunto = idArchivoAdjunto;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "arcadjexa_id", referencedColumnName = "arcadjexa_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public ArchivosAdjuntos getArchivosAdjuntos() {
        return archivosAdjuntos;
    }

    public void setArchivosAdjuntos(ArchivosAdjuntos archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }

    @Column(name = "fecha_post_adju")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPostulacionAdjunto() {
        return fechaPostulacionAdjunto;
    }

    public void setFechaPostulacionAdjunto(Date fechaPostulacionAdjunto) {
        this.fechaPostulacionAdjunto = fechaPostulacionAdjunto;
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

    @JoinColumns({
            @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false),
            @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public PostulacionOfertaLaboralUsmempleo getPostulacionOfertaLaboralUsmempleo() {

        return postulacionOfertaLaboralUsmempleo;
    }

    public void setPostulacionOfertaLaboralUsmempleo(PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboralUsmempleo) {
        this.postulacionOfertaLaboralUsmempleo = postulacionOfertaLaboralUsmempleo;
    }



}
