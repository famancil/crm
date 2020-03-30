package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_periocidad.
 * Contiene un listado con los tipos de periocidad que maneja el sistema
 * <br>
 * <ul>
 *     <li>Mensual</li>
 *     <li>Cada 2 Meses</li>
 *     <li>Cada 3 Meses</li>
 *     <li>Cada 4 Meses</li>
 *     <li>Cada 5 Meses</li>
 *     <li>Cada 6 Meses</li>
 *     <li>Cada 7 Meses</li>
 *     <li>Cada 8 Meses</li>
 *     <li>Cada 9 Meses</li>
 *     <li>Cada 10 Meses</li>
 *     <li>Cada 11 Meses</li>
 *     <li>Cada 12 Meses</li>
 *     <li>Cada 24 Meses</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_periocidad", schema = "aexa")
public class TipoPeriocidad implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * Id del tipo de periocidad
     */
    private Short codPeriocidad;

    /*
    * Nombre del tipo de periocidad
    */
    private String nomPeriocidad;

    /*
    * Numero de meses del periodo de periocidad
    */
    private Short numPeriocidad;


    public TipoPeriocidad() {
    }

    public TipoPeriocidad(Short codPeriocidad) {
        this.codPeriocidad = codPeriocidad;
    }

    @Id
    @Column(name = "cod_periocidad")
    public Short getCodPeriocidad() {
        return codPeriocidad;
    }

    public void setCodPeriocidad(Short codPeriocidad) {
        this.codPeriocidad = codPeriocidad;
    }

    @Column(name = "nom_periocidad")
    public String getNomPeriocidad() {
        return nomPeriocidad;
    }

    public void setNomPeriocidad(String nomPeriocidad) {
        this.nomPeriocidad = nomPeriocidad;
    }

    @Column(name = "num_periocidad")
    public Short getNumPeriocidad() {
        return numPeriocidad;
    }

    public void setNumPeriocidad(Short numPeriocidad) {
        this.numPeriocidad = numPeriocidad;
    }
}
