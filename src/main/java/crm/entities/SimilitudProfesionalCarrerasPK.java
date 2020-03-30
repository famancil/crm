package crm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que maneja la clave compuesta de la entidad {@link SimilitudProfesionalCarreras}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public class SimilitudProfesionalCarrerasPK implements Serializable {

    /**
     * Identificador de la {@link Carrera} asociada en {@link SimilitudProfesionalCarreras}
     */
    private Long codCarrera;

    /**
     * Identificador de la {@link Institucion} asociada en {@link SimilitudProfesionalCarreras}
     */
    private Short codInstitucion;

    /**
     * Similitud de las carreras
     */
    private String similitud;




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

    @Id
    @Column(name = "similitud")
    public String getSimilitud() {
        return similitud;
    }

    public void setSimilitud(String similitud) {
        this.similitud = similitud;
    }
}
