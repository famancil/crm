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
@Table(name = "tipo_encargado", schema = "crm")
public class TipoEncargado implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Encargado
     */
    private Short codEncargado;

    /**
     * Nombre del Tipo de Encargado
     */
    private String nomEncargado;


    public TipoEncargado() {
    }

    public TipoEncargado(Short codEncargado) {
        this.codEncargado = codEncargado;
    }


    @Id
    @Column(name = "cod_encargado")
    public Short getCodEncargado() {
        return codEncargado;
    }

    public void setCodEncargado(Short codEncargado) {
        this.codEncargado = codEncargado;
    }

    @Column(name = "nom_encargado")
    public String getNomEncargado() {
        return nomEncargado;
    }

    public void setNomEncargado(String nomEncargado) {
        this.nomEncargado = nomEncargado;
    }
}
