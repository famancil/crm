package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link PostulacionUsuarioAsesoria}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class PostulacionUsuarioAsesoriaPK implements Serializable {

    /**
     * Identificador del {@link Usuario} asociada en {@link PostulacionUsuarioAsesoria}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link OfertaAsesoria} asociada en {@link PostulacionUsuarioAsesoria}
     */
    private Long idOfertaAsesoria;


    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "oferta_asesoria_id")
    public Long getIdOfertaAsesoria() {
        return idOfertaAsesoria;
    }

    public void setIdOfertaAsesoria(Long idOfertaAsesoria) {
        this.idOfertaAsesoria = idOfertaAsesoria;
    }
}

