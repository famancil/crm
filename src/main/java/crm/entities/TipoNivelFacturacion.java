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
 * Entidad correspondiente a la tabla dbo.tipo_nivel_facturacion.
 * Contiene un listado con los tipos de niveles de facturacion que
 * maneja el sistema:
 *
 *   1  - Sin información
 *   2  - Micro (0,1 - 200 UF)
 *   3  - Pequeña (10.000,1 - 25.000 UF)
 *   4  - Mediana (25.000,1 - 50.000 UF)
 *   5  - Grande (600.000,1 - 1.000.000 UF)
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_nivel_facturacion", schema = "org")
public class TipoNivelFacturacion {

    /*
    * Codigo (Id) del tipo de nivel de facturacion
    */
    private Short codNivelFacturacion;

    /*
    * Nombre del tipo de nivel de facturacion
    */
    private String nomNivelFacturacion;

    public TipoNivelFacturacion() {
        short s = 0;
        this.setCodNivelFacturacion(s);
    }

    @Id
    @Column(name = "cod_nivel_facturacion")
    public Short getCodNivelFacturacion() {
        return codNivelFacturacion;
    }

    public void setCodNivelFacturacion(Short codNivelFacturacion) {
        this.codNivelFacturacion = codNivelFacturacion;
    }

    @Column(name = "nom_nivel_facturacion")
    public String getNomNivelFacturacion() {
        return nomNivelFacturacion;
    }

    public void setNomNivelFacturacion(String nomNivelFacturacion) {
        this.nomNivelFacturacion = nomNivelFacturacion;
    }

}
