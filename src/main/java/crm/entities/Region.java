package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad correspondiente a la tabla dbo.region. Contiene la lista de las regiones segun un pais
 *
 * @author Renata Mella
 */
@Entity
@Table(name = "region")
public class Region {

    /**
     * Identificador de la instancia actual
     */
    private Short id;

    /**
     * Nombre de la region
     */
    private String nombre;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion
     */
    private Integer rutUsuario;

    /**
     * Listado con las provincias pertenecientes a una region
     */
    private List<Provincia> provincias;

    public Region() {
    }

    @Id
    @Column(name = "cod_region")
    @JsonView(ResponseView.MainView.class)
    public Short getId() {
        return id;
    }

    public void setId(Short codRegion) {
        this.id = codRegion;
    }

    @Column(name = "nom_region")
    @JsonView(ResponseView.MainView.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomRegion) {
        this.nombre = nomRegion;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(ResponseView.MainView.class)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "rut_usuario")
    @JsonView(ResponseView.MainView.class)
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @OneToMany(mappedBy = "region")
    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provinciaList) {
        this.provincias = provinciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crm.entities.Region[codRegion=" + id + "]";
    }

}