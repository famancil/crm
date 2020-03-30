package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla org.tipo_oportunidad.
 * Contiene un listado con los tipos de oportunidades que se pueden generar
 * en un contacto (reunion) con una empresa
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_oportunidad", schema = "org")
public class TipoOportunidad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Oportunidad
     */
    private Short codOportunidad;

    /**
     * Nombre del Tipo de Oportunidad
     */
    private String nomOportunidad;

    /**
     * Tipo
     * TODO preguntar que cosa es
     */
    private String tipoInteres;


    public TipoOportunidad() {
    }

    public TipoOportunidad(Short codOportunidad) {
        this.codOportunidad = codOportunidad;
    }


    @Id
    @Column(name = "cod_oportunidad")
    public Short getCodOportunidad() {
        return codOportunidad;
    }

    public void setCodOportunidad(Short codOportunidad) {
        this.codOportunidad = codOportunidad;
    }

    @Column(name = "nom_oportunidad")
    public String getNomOportunidad() {
        return nomOportunidad;
    }

    public void setNomOportunidad(String nomOportunidad) {
        this.nomOportunidad = nomOportunidad;
    }

    @Column(name = "tipo_interes")
    public String getTipoInteres() {
        return tipoInteres;
    }

    public void setTipoInteres(String tipoInteres) {
        this.tipoInteres = tipoInteres;
    }
    
}
