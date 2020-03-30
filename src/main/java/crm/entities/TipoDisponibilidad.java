package crm.entities;

import java.io.Serializable;
//import java.util.List;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla tipo_disponibilidad_para_trabajar. Contiene
 * los diferentes tipos de disponibilidad existentes segun el tiempo que se tenga
 * para trabajar. En la version actual del sistema estan disponibles
 * los siguientes valores:
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Jornada Completa</li>
 *     <li>Media Jornada</li>
 *     <li>Por turnos</li>
 *     <li>Part Time</li>
 *     <li>Solo en los veranos</li>
 *     <li>Freelance</li>
 *     <li>Sin horarios y por comision</li>
 * </ul>
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "tipo_disponibilidad_para_trabajar", schema = "empleo")
public class TipoDisponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia
     */
    private Short codigo;

    /**
     * Nombre de la disponibilidad
     */
    private String nombre;

    //@OneToMany(mappedBy = "tipoMoneda")
    //private List<InfoProfesionalExalumno> infoProfesionalExalumnoList;

    public TipoDisponibilidad() {
    }

    @Id
    @Column(name = "cod_disponibilidad")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codMoneda) {
        this.codigo = codMoneda;
    }

    @Column(name = "nom_disponibilidad")
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
        if (!(object instanceof TipoDisponibilidad)) {
            return false;
        }
        TipoDisponibilidad other = (TipoDisponibilidad) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crm.entities.TipoDisponibilidad[codigo=" + codigo + "]";
    }
}
