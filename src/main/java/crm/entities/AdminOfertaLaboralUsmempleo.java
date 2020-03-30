package crm.entities;

import javax.persistence.*;
import java.util.Date;


/**
 * Entidad correspondiente a la tabla empleo.admin_oferta_usmempleo.
 * Contiene un listado con los administradores de las {@Link OfertaLaboralUsmempleo}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "admin_oferta_usmempleo", schema = "empleo")
@IdClass(AdminOfertaLaboralUsmempleoPK.class)
public class AdminOfertaLaboralUsmempleo {





    /**
     * Identificador del {@link UsuarioUsmempleoEmpresa} asociado al {@link AdminOfertaLaboralUsmempleo}
     */
    private Long idUsuarioUsmempleoEmpresa;

    /**
     * Identificador de la {@link OfertaLaboralUsmempleo} asociado al {@link AdminOfertaLaboralUsmempleo}
     */
    private Long idOfertaLaboralUsmempleo;

    /*
    * UsuarioEmpresaUsmempleo asociado a la entidad actual
    */
    private UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa;

    /**
     * Instancia de la entidad {@link crm.entities.OfertaLaboralUsmempleo} asociada a la entidad actual
     */
    private OfertaLaboralUsmempleo ofertaLaboralUsmempleo;

    /**
     * Indica si es un administrador creador de la {@OfertaLaboralUsmempleo}
     */
    private Boolean creador;

    /**
     * Indica si es un administrador publicador de la {@OfertaLaboralUsmempleo}
     */
    private Boolean publicador;

    /**
     * Rut del {@link Usuario} que realizo la ultima modificacion en la instancia actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */
    private Date fechaModificacion;







    @Id
    @Column(name = "usuempempusm_id")
    public Long getIdUsuarioUsmempleoEmpresa() {
        return idUsuarioUsmempleoEmpresa;
    }

    public void setIdUsuarioUsmempleoEmpresa(Long idUsuarioUsmempleoEmpresa) {
        this.idUsuarioUsmempleoEmpresa = idUsuarioUsmempleoEmpresa;
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmempleo() {
        return idOfertaLaboralUsmempleo;
    }

    public void setIdOfertaLaboralUsmempleo(Long idOfertaLaboralUsmempleo) {
        this.idOfertaLaboralUsmempleo = idOfertaLaboralUsmempleo;
    }

    @JoinColumn(name = "ofelabusm_id", referencedColumnName = "ofelabusm_id", insertable = false, updatable = false)
    @ManyToOne
    public OfertaLaboralUsmempleo getOfertaLaboralUsmempleo() {
        return ofertaLaboralUsmempleo;
    }

    public void setOfertaLaboralUsmempleo(OfertaLaboralUsmempleo ofertaLaboralUsmempleo) {
        this.ofertaLaboralUsmempleo = ofertaLaboralUsmempleo;
    }

    @JoinColumn(name = "usuempempusm_id", referencedColumnName = "usuempempusm_id", insertable = false, updatable = false)
    @ManyToOne
    public UsuarioUsmempleoEmpresa getUsuarioUsmempleoEmpresa() {
        return usuarioUsmempleoEmpresa;
    }

    public void setUsuarioUsmempleoEmpresa(UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa) {
        this.usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresa;
    }

    @Column(name = "admofeusm_creador")
    public Boolean getCreador() {
        return creador;
    }

    public void setCreador(Boolean creador) {
        this.creador = creador;
    }

    @Column(name = "admofeusm_publicador")
    public Boolean getPublicador() {
        return publicador;
    }

    public void setPublicador(Boolean publicador) {
        this.publicador = publicador;
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
}
