package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by famancil on 28-07-16.
 */
@Embeddable
public class ContactoHistoricoEmpresaFuncionarioParticipantePK implements Serializable {

    /**
     * Identificador del {@link crm.entities.ContactoHistoricoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaFuncionarioParticipante}
     */
    private Long contactoHistoricoEmpresaId;

    /**
     * Identificador del {@link crm.entities.Usuario} asociado en {@link crm.entities.ContactoHistoricoEmpresaFuncionarioParticipante}
     */
    private Long usuarioId;

    public ContactoHistoricoEmpresaFuncionarioParticipantePK() {

    }

    public ContactoHistoricoEmpresaFuncionarioParticipantePK(Long contactoHistoricoEmpresaId, Long usuarioId) {
        this.contactoHistoricoEmpresaId = contactoHistoricoEmpresaId;
        this.usuarioId = usuarioId;
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
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
