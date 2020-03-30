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
 * Entidad correspondiente a la tabla dbo.tipo_aporte.
 * Contiene un listado con los tipos de grado academicos que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Efectivo</li>
 *     <li>Transbank</li>
 *     <li>Desconocido</li>
 *     <li>Deposito</li>
 *     <li>WEBPAY</li>
 *     <li>USM</li>
 *     <li>ANSCO</li>
 *     <li>Transferencia Programada</li>
 *     <li>PAC - Pago Automatico de Cuentas</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_aporte")
public class TipoAporte implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id del aporte.
     */
    private Short codAporte;

    /**
     * Nombre del aporte.
     */
    private String nomAporte;


    public TipoAporte() {
    }

    public TipoAporte(Short codAporte) {
        this.codAporte = codAporte;
    }

    @Id
    @Column(name = "cod_aporte")
    public Short getCodAporte() {
        return codAporte;
    }

    public void setCodAporte(Short codAporte) {
        this.codAporte = codAporte;
    }

    @Column(name = "nom_aporte")
    public String getNomAporte() {
        return nomAporte;
    }

    public void setNomAporte(String nomAporte) {
        this.nomAporte = nomAporte;
    }
}
