package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_actividad_exalumno.
 * Contiene un listado con los tipos de actividad del exalumno quee maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Trabajo Regular</li>
 *     <li>Tiempo Sabático</li>
 *     <li>Trabajo Voluntario</li>
 *     <li>Trabajo Medio Tiempo</li>
 *     <li>Trabajo sin horarios y por objetivos</li>
 *     <li>Práctica</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_actividad_exalumno")
public class TipoActividadExalumno implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * Id del tipo de actividad del exalumno
    */
    private Short codActividadExalumno;

    /*
    * Nombre del tipo de actividad del exalumno
    */
    private String nomActividadExalumno;


    public TipoActividadExalumno() {
    }

    public TipoActividadExalumno(Short codActividadExalumno) {
        this.codActividadExalumno = codActividadExalumno;
    }

    @Id
    @Column(name = "cod_actividad_exalumno")
    public Short getCodActividadExalumno() {
        return codActividadExalumno;
    }

    public void setCodActividadExalumno(Short codActividadExalumno) {
        this.codActividadExalumno = codActividadExalumno;
    }

    @Column(name = "nom_actividad_exalumno")
    public String getNomActividadExalumno() {
        return nomActividadExalumno;
    }

    public void setNomActividadExalumno(String nomActividadExalumno) {
        this.nomActividadExalumno = nomActividadExalumno;
    }
}


