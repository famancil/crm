package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by famancil on 28-07-16.
 */
@Entity
@Table(name = "contacto_historico_empresa_funcionario_participante", schema = "crm")
@IdClass(ContactoHistoricoEmpresaFuncionarioParticipantePK.class)
public class ContactoHistoricoEmpresaFuncionarioParticipante implements Serializable {

    /**
     * Identificador del {@link crm.entities.ContactoHistoricoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaFuncionarioParticipante}
     */
    private Long contactoHistoricoEmpresaId;

    /**
     * Identificador del {@link crm.entities.UsuarioUsmempleoEmpresa} asociado en {@link crm.entities.ContactoHistoricoEmpresaFuncionarioParticipante}
     */
    private Long usuarioId;

    /**
     * Instancia de la entidad ContactoHistoricoEmpresa asociada a la instancia actual
     */
    private ContactoHistoricoEmpresa contactoHistoricoEmpresa;

    /**
     * Instancia de la entidad UsuarioUsmempleoEmpresa asociada a la instancia actual
     */
    private Usuario usuario;

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
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @JoinColumn(name = "conhisemp_id", referencedColumnName = "conhisemp_id", insertable = false, updatable = false)
    @ManyToOne
    public ContactoHistoricoEmpresa getContactoHistoricoEmpresa() {
        return contactoHistoricoEmpresa;
    }

    public void setContactoHistoricoEmpresa(ContactoHistoricoEmpresa contactoHistoricoEmpresa) {
        this.contactoHistoricoEmpresa = contactoHistoricoEmpresa;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
