package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link RespuestaUsuario}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class RespuestaUsuarioPK implements Serializable {

    /**
     * Identificador de la {@link Usuario} asociada en {@link RespuestaUsuario}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link crm.entities.PreguntasUsuario} asociada en {@link RespuestaUsuario}
     */
    private Long codPreguntaUsuario;


    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "cod_pregunta_usu_aexa")
    public Long getCodPreguntaUsuario() {
        return codPreguntaUsuario;
    }

    public void setCodPreguntaUsuario(Long codPreguntaUsuario) {
        this.codPreguntaUsuario = codPreguntaUsuario;
    }
}