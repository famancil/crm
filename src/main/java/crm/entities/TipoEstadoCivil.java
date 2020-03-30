package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_estado_civil. Contiene un listado con los posibles estados civiles
 * que un {@link Usuario} puede tener (segun Chile). En la version actual del sistema, los estados
 * civiles disponibles son:
 * <br>
 * <ul>
 *     <li>Sin información</li>
 *     <li>Soltero (a)</li>
 *     <li>Casado (a)</li>
 *     <li>Separado (a) legalmente</li>
 *     <li>Separado(a) de hecho</li>
 *     <li>Divorciado (a)</li>
 *     <li>Viudo (a)</li>
 * </ul>
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "tipo_estado_civil")
public class TipoEstadoCivil {

    /**
     * Identificador del estado civil.
     */
    private Short codigo;

    /**
     * Nombre del estado civil.
     */
    private String nombre;

    public TipoEstadoCivil() {
    }

    @Id
    @Column(name = "cod_estado_civil")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codEstadoCivil) {
        this.codigo = codEstadoCivil;
    }

    @Column(name = "nom_estado_civil")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomEstadoCivil) {
        this.nombre = nomEstadoCivil;
    }

}

