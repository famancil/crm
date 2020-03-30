package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla empleo.tipo_competencia_usmempleo.
 * Tipificación de las competencias
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_competencia_usmempleo", schema = "empleo")
public class TipoCompetencia {

    /**
     * Id del tipo de competencia
     */
    private Integer codigo;

    /**
     * Nombre del tipo de competencia
     */
    private String nombre;

    @Id
    @Column(name = "cod_competencia_usmempleo")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_competencia_usmempleo")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
