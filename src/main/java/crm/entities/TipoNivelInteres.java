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
@Table(name = "tipo_nivel_interes", schema = "crm")
public class TipoNivelInteres implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Nivel de Interes
     */
    private Short codNivelInteres;

    /**
     * Nombre del Tipo de Nivel de Interes
     */
    private String nomNivelInteres;


    public TipoNivelInteres() {
    }

    public TipoNivelInteres(Short codNivelInteres) {
        this.codNivelInteres = codNivelInteres;
    }


    @Id
    @Column(name = "cod_nivel_interes")
    public Short getCodNivelInteres() {
        return codNivelInteres;
    }

    public void setCodNivelInteres(Short codNivelInteres) {
        this.codNivelInteres = codNivelInteres;
    }

    @Column(name = "nom_nivel_interes")
    public String getNomNivelInteres() {
        return nomNivelInteres;
    }

    public void setNomNivelInteres(String nomNivelInteres) {
        this.nomNivelInteres = nomNivelInteres;
    }

}
