package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla crm.tipo_compromiso.
 * Contiene un listado con los tipos de compromisos que se pueden generar
 * en un compromiso con una empresa
 *
 * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
 */

@Entity
@Table(name = "tipo_compromiso", schema = "crm")
public class TipoCompromiso {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Compromiso
     */
    private Short codCompromiso;

    /**
     * Nombre del Tipo de Compromiso
     */
    private String nomCompromiso;


    public TipoCompromiso() {
    }

    public TipoCompromiso(Short codCompromiso) {
        this.codCompromiso = codCompromiso;
    }


    @Id
    @Column(name = "cod_compromiso")
    public Short getCodCompromiso() {
        return codCompromiso;
    }

    public void setCodCompromiso(Short codCompromiso) {
        this.codCompromiso = codCompromiso;
    }

    @Column(name = "nom_compromiso")
    public String getNomCompromiso() {
        return nomCompromiso;
    }

    public void setNomCompromiso(String nomCompromiso) {
        this.nomCompromiso = nomCompromiso;
    }

}
