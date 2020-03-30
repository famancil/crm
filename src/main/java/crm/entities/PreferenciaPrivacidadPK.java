package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link PreferenciaPrivacidad}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class PreferenciaPrivacidadPK implements Serializable {

    /**
     * Identificador del {@link crm.entities.Usuario} asociada al {@link crm.entities.PreferenciaPrivacidad}
     */
    private Long usuarioId;

    /**
     * Identificador de la {@link crm.entities.Institucion} asociada en {@link crm.entities.PreferenciaPrivacidad}
     */
    private Short codInstitucion;





    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "cod_institucion")
    public Short getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Short codInstitucion) {
        this.codInstitucion = codInstitucion;
    }
}
