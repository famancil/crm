package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link UsuarioVistoUsmEmpleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class UsuarioVistoUsmEmpleoPK implements Serializable {

    /**
     * Identificador de la {@link Usuario} asociada en {@link UsuarioVistoUsmEmpleo}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link Empresa} asociada en {@link UsuarioVistoUsmEmpleo}
     */
    private Long idEmpresa;


    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "perempusm_id")
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }



}
