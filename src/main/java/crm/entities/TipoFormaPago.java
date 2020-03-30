package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by famancil on 28-07-16.
 */
@Entity
@Table(name = "tipo_forma_pago", schema = "crm")
public class TipoFormaPago implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Forma de Pago
     */
    private Short codFormaPago;

    /**
     * Nombre del Tipo de Forma de Pago
     */
    private String nomFormaPago;


    public TipoFormaPago() {
    }

    public TipoFormaPago(Short codFormaPago) {
        this.codFormaPago = codFormaPago;
    }


    @Id
    @Column(name = "cod_forma_pago")
    public Short getCodFormaPago() {
        return codFormaPago;
    }

    public void setCodFormaPago(Short codFormaPago) {
        this.codFormaPago = codFormaPago;
    }

    @Column(name = "nom_forma_pago")
    public String getNomFormaPago() {
        return nomFormaPago;
    }

    public void setNomFormaPago(String nomFormaPago) {
        this.nomFormaPago = nomFormaPago;
    }
}
