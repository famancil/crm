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
@Table(name = "tipo_comprobante", schema = "crm")
public class TipoComprobante implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Comprobante
     */
    private Short codComprobante;

    /**
     * Nombre del Tipo de Comprobante
     */
    private String nomComprobante;


    public TipoComprobante() {
    }

    public TipoComprobante(Short codComprobante) {
        this.codComprobante = codComprobante;
    }


    @Id
    @Column(name = "cod_comprobante")
    public Short getCodComprobante() {
        return codComprobante;
    }

    public void setCodComprobante(Short codComprobante) {
        this.codComprobante = codComprobante;
    }

    @Column(name = "nom_comprobante")
    public String getNomComprobante() {
        return nomComprobante;
    }

    public void setNomComprobante(String nomComprobante) {
        this.nomComprobante = nomComprobante;
    }
}
