package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
//import java.util.List;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla tipo_moneda. Contiene los distintos
 * tipos de moneda que existen en el mercado internacional. En la version actual del sistema estan disponibles
 * los siguientes valores:
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Pesos</li>
 *     <li>Dolar</li>
 *     <li>Euros</li>
 *     <li>Dolares Canadienses</li>
 * </ul>
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "tipo_moneda")
public class TipoMoneda implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia
     */
    private Short codigo;

    /**
     * Nombre de la moneda
     */
    private String nombre;

    //@OneToMany(mappedBy = "tipoMoneda")
    //private List<InfoProfesionalExalumno> infoProfesionalExalumnoList;

    public TipoMoneda() {
    }

    @Id
    @Column(name = "cod_moneda")
    @JsonView(ResponseView.MainView.class)
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codMoneda) {
        this.codigo= codMoneda;
    }

    @Column(name = "nom_moneda")
    @JsonView(ResponseView.MainView.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomMoneda) {
        this.nombre = nomMoneda;
    }

    /*
    public List<InfoProfesionalExalumno> getInfoProfesionalExalumnoList() {
        return infoProfesionalExalumnoList;
    }

    public void setInfoProfesionalExalumnoList(List<InfoProfesionalExalumno> infoProfesionalExalumnoList) {
        this.infoProfesionalExalumnoList = infoProfesionalExalumnoList;
    } */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMoneda)) {
            return false;
        }
        TipoMoneda other = (TipoMoneda) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.utfsm.redexalumnos.crm.entities.TipoMoneda[codigo=" + codigo+ "]";
    }

}
