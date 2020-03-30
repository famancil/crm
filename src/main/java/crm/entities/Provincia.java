package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *  Entidad correspondiente a la tabla dbo.provincia. Contiene las provincias
 *  pertenecientes a una region.
 *
 * @author Renata Mella
 */
@Entity
@Table(name = "provincia")
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia actual
     */
    private Short id;

    /**
     * Nombre de la provincia
     */
    private String nombre;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */

    private Date fechaModificacion;

    /**
     * Rut del usuario que hizo la ultima modificacion
     */
    private Integer rutUsuario;

    /**
     * Region a la cual pertenece la provincia
     */
    private Region region;

    /**
     * Pais al que pertenece la region asociada a la provincia
     */
    private Pais pais;

    /**
     * Lista de comunas que posee la provincia
     */
    private List<Comuna> comunas;

    public Provincia() {
    }

    @Id
    @Column(name = "cod_provincia")
    @JsonView(ResponseView.MainView.class)
    public Short getId() {
        return id;
    }

    public void setId(Short codProvincia) {
        this.id = codProvincia;
    }

    @Column(name = "nom_provincia")
    @JsonView(ResponseView.MainView.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomProvincia) {
        this.nombre = nomProvincia;
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

    @JoinColumn(name = "cod_region", referencedColumnName = "cod_region")
    @ManyToOne
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @OneToMany(mappedBy = "provincia")
    @JsonView(ResponseView.MainView.class)
    public List<Comuna> getComunas() {
        return comunas;
    }

    public void setComunas(List<Comuna> comunaList) {
        this.comunas = comunaList;
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
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crm.entities.Provincia[codProvincia=" + id + "]";
    }

}