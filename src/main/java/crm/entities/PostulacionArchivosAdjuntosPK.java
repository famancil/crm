package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link PostulacionArchivosAdjuntos}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class PostulacionArchivosAdjuntosPK implements Serializable{

    /**
     * Identificador del {@link OfertaLaboralUsmempleo} asociada a la {@link PostulacionArchivosAdjuntos}
     */
    private Long idOfertaLaboralUsmempleo;

    /**
     * Id del {@link Usuario} asociado a la {@link PostulacionArchivosAdjuntos}
     */
    private Long idUsuario;

    /**
     * Id del {@link crm.entities.ArchivosAdjuntos} asociado a la {@link PostulacionArchivosAdjuntos}
     */
    private Long idArchivoAdjunto;


    @Id
    @Column(name = "ofelabusm_id")
    public Long getIdOfertaLaboralUsmempleo() {
        return idOfertaLaboralUsmempleo;
    }

    public void setIdOfertaLaboralUsmempleo(Long idOfertaLaboralUsmempleo) {
        this.idOfertaLaboralUsmempleo = idOfertaLaboralUsmempleo;
    }

    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "arcadjexa_id")
    public Long getIdArchivoAdjunto() {
        return idArchivoAdjunto;
    }

    public void setIdArchivoAdjunto(Long idArchivoAdjunto) {
        this.idArchivoAdjunto = idArchivoAdjunto;
    }
}