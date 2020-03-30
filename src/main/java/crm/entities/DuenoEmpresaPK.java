package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link DuenoEmpresa}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class DuenoEmpresaPK implements Serializable {

    /**
     * Id del {@link crm.entities.Usuario} que es dueño de la {@link crm.entities.Empresa}
     */
    private Long usuarioId;

    /**
     * Identificador del {@link crm.entities.Empresa} de la cual el {@link crm.entities.Usuario} es Dueño
     */
    private Long empresaId;



    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "perempusm_id")
    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
