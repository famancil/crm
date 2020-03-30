package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_cargo.
 * Contiene un listado con los tipos de cargos que maneja el sistema

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_cargo", schema = "empleo")
public class TipoCargo {

    /*
    * Id del cargo
    */
    private Short codCargo;

    /*
    * Nombre del cargo
    */
    private String nomCargo;

    /*
    * Nivel del cargo. Agrupa los cargos de acuerdo a la importancia (tipifica los cargos)
    */
    private String nivCargo;


    public TipoCargo() {
    }

    public TipoCargo(Short codCargo) {
        this.codCargo = codCargo;
    }

    @Id
    @Column(name = "cod_cargo")
    @JsonView(ResponseView.MainView.class)
    public Short getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Short codCargo) {
        this.codCargo = codCargo;
    }

    @Column(name = "nom_cargo")
    @JsonView(ResponseView.MainView.class)
    public String getNomCargo() {
        return nomCargo;
    }

    public void setNomCargo(String nomCargo) {
        this.nomCargo = nomCargo;
    }

    @Column(name = "niv_cargo")
    @JsonView(ResponseView.MainView.class)
    public String getNivCargo() {
        return nivCargo;
    }

    public void setNivCargo(String nivCargo) {
        this.nivCargo = nivCargo;
    }
}
