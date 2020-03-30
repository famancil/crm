package crm.entities;

import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla contacto_historico_empresa_persona_participante.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "contacto_historico_empresa_persona_participante", schema = "org")
@IdClass(ContactoHistoricoEmpresaPersonaParticipantePK.class)
public class ContactoHistoricoEmpresaPersonaParticipante {

    /**
     * Identificador del {@link crm.entities.ContactoHistoricoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     */
    private Long contactoHistoricoEmpresaId;

    /**
     * Identificador del {@link crm.entities.UsuarioUsmempleoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     */
    private Long usuarioUsmempleoEmpresaId;

    /**
     * Instancia de la entidad ContactoHistoricoEmpresa asociada a la instancia actual
     */
    private ContactoHistoricoEmpresa contactoHistoricoEmpresa;

    /**
     * Instancia de la entidad UsuarioUsmempleoEmpresa asociada a la instancia actual
     */
    private UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa;

    /**
     * Rut del usuario que realizo la ultima modificacion en la instancia actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */
    private Date fechaModificacion;

    @Id
    @Column(name = "conhisemp_id")
    public Long getContactoHistoricoEmpresaId() {
        return contactoHistoricoEmpresaId;
    }

    public void setContactoHistoricoEmpresaId(Long contactoHistoricoEmpresaId) {
        this.contactoHistoricoEmpresaId = contactoHistoricoEmpresaId;
    }

    @Id
    @Column(name = "usuempempusm_id")
    public Long getUsuarioUsmempleoEmpresaId() {
        return usuarioUsmempleoEmpresaId;
    }

    public void setUsuarioUsmempleoEmpresaId(Long usuarioUsmempleoEmpresaId) {
        this.usuarioUsmempleoEmpresaId = usuarioUsmempleoEmpresaId;
    }

    @JoinColumn(name = "conhisemp_id", referencedColumnName = "conhisemp_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public ContactoHistoricoEmpresa getContactoHistoricoEmpresa() {
        return contactoHistoricoEmpresa;
    }

    public void setContactoHistoricoEmpresa(ContactoHistoricoEmpresa contactoHistoricoEmpresa) {
        this.contactoHistoricoEmpresa = contactoHistoricoEmpresa;
    }

    @JoinColumn(name = "usuempempusm_id", referencedColumnName = "usuempempusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public UsuarioUsmempleoEmpresa getUsuarioUsmempleoEmpresa() {
        return usuarioUsmempleoEmpresa;
    }

    public void setUsuarioUsmempleoEmpresa(UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa) {
        this.usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresa;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_modificacion")
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

}
