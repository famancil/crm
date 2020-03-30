package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de la entidad {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class ContactoHistoricoEmpresaPersonaParticipantePK implements Serializable {

    /**
     * Identificador del {@link crm.entities.ContactoHistoricoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     */
    private Long contactoHistoricoEmpresaId;

    /**
     * Identificador del {@link crm.entities.UsuarioUsmempleoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     */
    private Long usuarioUsmempleoEmpresaId;

    public ContactoHistoricoEmpresaPersonaParticipantePK() {

    }

    public ContactoHistoricoEmpresaPersonaParticipantePK(Long contactoHistoricoEmpresaId, Long usuarioUsmempleoEmpresaId) {
        this.contactoHistoricoEmpresaId = contactoHistoricoEmpresaId;
        this.usuarioUsmempleoEmpresaId = usuarioUsmempleoEmpresaId;
    }

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

}