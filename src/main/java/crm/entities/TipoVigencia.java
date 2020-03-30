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
 * Entidad correspondiente a la tabla dbo.tipo_vigencia.
 * Contiene un listado con los tipos de vigencia que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin información</li>
 *     <li>Vigente</li>
 *     <li>No Vigente</li>
 *     <li>Eliminacion Logica</li>
 *     <li>Por Validar</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_vigencia")
public class TipoVigencia implements Serializable {

    public final static Short ID_VIGENTE = 1;
    public final static Short ID_NO_VIGENTE = 2;
    public final static Short ID_ELIMINACION_LOGICA = 3;
    public final static Short ID_POR_VALIDAR= 4;
    public final static String TIPO_VIGENTE = "Vigente";
    public final static String TIPO_NO_VIGENTE = "No Vigente";
    public final static String TIPO_ELIMINACION_LOGICA = "Eliminación Lógica";
    public final static String TIPO_POR_VALIDAR = "Por Validar";

    /*
    * Codigo (Id) del tipo de vigencia
    */
    private Short codVigencia;

    /*
    * Nombre del tipo vigencia
    */
    private String nomVigencia;

    public TipoVigencia() {
    }

    public TipoVigencia(Short codVigencia) {
        this.codVigencia = codVigencia;
    }

    public TipoVigencia(Short codVigencia, String nomVigencia){
        this.codVigencia = codVigencia;
        this.nomVigencia = nomVigencia;
    }

    @Id
    @Column(name = "cod_vigencia")
    public Short getCodVigencia() {
        return codVigencia;
    }

    public void setCodVigencia(Short codVigencia) {
        this.codVigencia = codVigencia;
    }

    @Column(name = "nom_vigencia")
    public String getNomVigencia() {
        return nomVigencia;
    }

    public void setNomVigencia(String nomVigencia) {
        this.nomVigencia = nomVigencia;
    }

}
