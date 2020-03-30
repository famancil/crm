package crm.entities;

import java.io.Serializable;
//import java.util.List;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla tipo_dominio_computacional. Contiene
 * los distintos niveles de dominio computacional que se pueden tener. En la version actual del sistema estan disponibles
 * los siguientes valores:
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Nivel Bajo</li>
 *     <li>Nivel Usuario</li>
 *     <li>Nivel Medio</li>
 *     <li>Nivel Avanzado</li>
 *     <li>Nivel Profesional Experto</li>
 * </ul>
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "tipo_dominio_computacional", schema = "empleo")
public class TipoDominioCompu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia
     */
    private Short codigo;

    /**
     * Nombre del nivel del dominio computacional
     */
    private String nombre;

    //@OneToMany(mappedBy = "tipoMoneda")
    //private List<InfoProfesionalExalumno> infoProfesionalExalumnoList;

    public TipoDominioCompu() {
    }

    @Id
    @Column(name = "cod_dominio_compu")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codMoneda) {
        this.codigo= codMoneda;
    }

    @Column(name = "nom_dominio_compu")
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
        if (!(object instanceof TipoDominioCompu)) {
            return false;
        }
        TipoDominioCompu other = (TipoDominioCompu) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crm.entities.TipoDominoCompu[codigo=" + codigo+ "]";
    }

}
