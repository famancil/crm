package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link PostulanteFavorito}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class PostulanteFavoritoPK implements Serializable {

    /**
     * Identificador de la {@link Usuario} asociada en {@link PostulanteFavorito}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link .UsuarioEmpresaUsmempleo} asociada en {@link PostulanteFavorito}
     */
    private Long idUsuarioEmpresaUsmempleo;


    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "usuempusm_id")
    public Long getIdUsuarioEmpresaUsmempleo() {
        return idUsuarioEmpresaUsmempleo;
    }

    public void setIdUsuarioEmpresaUsmempleo(Long idUsuarioEmpresaUsmempleo) {
        this.idUsuarioEmpresaUsmempleo = idUsuarioEmpresaUsmempleo;
    }

}


