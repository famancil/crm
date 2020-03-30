package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla aexa.tipo_estado_pago.
 * Contiene un listado con los tipos de estados de los pagos realizados
 * por los usuarios
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_estado_pago", schema = "aexa")
public class TipoEstadoPago {

    /**
     * Id del tipo de estado de pago.
     */
    private Short codEstadoPago;

    /**
     * Nombre del tipo de estado de pago.
     */
    private String nomEstadoPago;


    public TipoEstadoPago() {
    }

    public TipoEstadoPago(Short codEstadoPago) {
        this.codEstadoPago = codEstadoPago;
    }

    @Id
    @Column(name = "cod_estado_pago")
    public Short getCodEstadoPago() {
        return codEstadoPago;
    }

    public void setCodEstadoPago(Short codEstadoPago) {
        this.codEstadoPago = codEstadoPago;
    }


    @Column(name = "nom_estado_pago")
    public String getNomEstadoPago() {
        return nomEstadoPago;
    }

    public void setNomEstadoPago(String nomEstadoPago) {
        this.nomEstadoPago = nomEstadoPago;
    }
}
