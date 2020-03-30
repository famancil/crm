package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla dbo.tipo_estado_estudio.
 * Contiene un listado con los tipos de estado de estudio que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Estudiante</li>
 *     <li>Estudiante de Media Jornada</li>
 *     <li>Estudiante Oyente</li>
 *     <li>Ha estudiado</li>
 *     <li>Egresado</li>
 *     <li>Memorista</li>
 *     <li>Tesista</li>
 *     <li>Titulado</li>
 *     <li>Graduado</li>
 *     <li>Diplomado</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_estado_estudio", schema = "academia")
public class TipoEstadoEstudio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**

     * Id del tipo de estado de estudio.
     */
    private Short codEstadoEstudio;

    /**
     * Nombre del tipo de estado de estudio.
     */
    private String nomEstadoEstudio;




    public TipoEstadoEstudio() {
    }

    public TipoEstadoEstudio(Short codEstadoEstudio) {
        this.codEstadoEstudio = codEstadoEstudio;
    }

    @Id
    @Column(name = "cod_estado_estudio")
    public Short getCodEstadoEstudio() {
        return codEstadoEstudio;
    }

    public void setCodEstadoEstudio(Short codEstadoEstudio) {
        this.codEstadoEstudio = codEstadoEstudio;
    }

    @Column(name = "nom_estado_estudio")
    public String getNomEstadoEstudio() {
        return nomEstadoEstudio;
    }

    public void setNomEstadoEstudio(String nomEstadoEstudio) {
        this.nomEstadoEstudio = nomEstadoEstudio;
    }

}
