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
 * Entidad correspondiente a la tabla dbo.tipo_causa_aporte.
 * Contiene un listado con los tipos de causa aporte que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>AEXA Valparaiso</li>
 *     <li>AEXA Santiago</li>
 *     <li>AEXA USA</li>
 *     <li>Pablo Elgueta</li>
 *     <li>Rancagua</li>
 *     <li>Red de Ex Alumnos Valparaiso</li>
 *     <li>Red de Ex Alumnos Viña del Mar</li>
 *     <li>Red de Ex Alumnos Santiago</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_causa_aporte", schema = "aexa")
public class TipoCausaAporte implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id de la Causa-Aporte.
     */
    private Short codCausaAporte;

    /**
     * Nombre de la Causa-Aporte.
     */
    private String nomCausaAporte;


    public TipoCausaAporte() {
    }

    public TipoCausaAporte(Short codCausaAporte) {
        this.codCausaAporte = codCausaAporte;
    }

    @Id
    @Column(name = "cod_causa_aporte")
    public Short getCodCausaAporte() {
        return codCausaAporte;
    }

    public void setCodCausaAporte(Short codCausaAporte) {
        this.codCausaAporte = codCausaAporte;
    }

    @Column(name = "nom_causa_aporte")
    public String getNomCausaAporte() {
        return nomCausaAporte;
    }

    public void setNomCausaAporte(String nomCausaAporte) {
        this.nomCausaAporte = nomCausaAporte;
    }

}
