package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link CarreraGrupo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class CarreraGrupoPK implements Serializable {

    /**
     * Identificador de la {@link Carrera} asociada en {@link CarreraGrupo}
     */
    private Long codCarrera;

    /**
     * Identificador del {@link GrupoEmpleo} asociada en {@link CarreraGrupo}
     */
    private Short idGrupoUsmempleo;




    @Id
    @Column(name = "cod_carrera")
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
    }

    @Id
    @Column(name = "gruusm_id")
    public Short getIdGrupoUsmempleo() {
        return idGrupoUsmempleo;
    }

    public void setIdGrupoUsmempleo(Short idGrupoUsmempleo) {
        this.idGrupoUsmempleo = idGrupoUsmempleo;
    }
}
