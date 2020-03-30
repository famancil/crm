package crm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link crm.entities.CarreraInstitucion}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class CarreraInstitucionPK implements Serializable {

    /**
     * Identificador de la {@link crm.entities.Carrera} asociada en {@link crm.entities.CarreraInstitucion}
     */
    private Long codCarrera;

    /**
     * Identificador de la {@link crm.entities.Institucion} asociada en {@link crm.entities.CarreraInstitucion}
     */
    private Short codInstitucion;


    @Id
    @Column(name = "cod_carrera")
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
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
