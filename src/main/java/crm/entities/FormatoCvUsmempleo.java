package crm.entities;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * Entidad correspondiente a la tabla formato_cv_usmempleo. Contiene información del
 * formato del curriculum de un usuario.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "formato_cv_usmempleo", schema = "empleo")
public class FormatoCvUsmempleo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia actual
     */
    private Long id;

    /**
     * Nombre del formato del curriculum
     */
    private String nombre;

    /**
     * Descripcion del formato del curriculum
     */
    private String descripcion;

    /**
     * Nombre original del formato
     */
    private String birt;

    /*
    * Rut del usuario que realizo la ultima modificacion de la entidad actual.
    */
    private Integer rutUsuario;

    /*
    * Fecha de la ultima modificacion de la entidad actual.
    */
    private Date fechaModificacion;

    public FormatoCvUsmempleo() {
    }

    @Id
    @Column(name = "forcvusm_id")
    public Long getId() {
        return id;
    }

    public void setId(Long forcvusmId) {
        this.id = forcvusmId;
    }

    @Column(name = "forcvusm_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String forcvusmNombre) {
        this.nombre = forcvusmNombre;
    }

    @Column(name = "forcvusm_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String forcvusmDescripcion) {
        this.descripcion = forcvusmDescripcion;
    }

    @Column(name = "forcvusm_nombre_birt")
    public String getBirt() {
        return birt;
    }

    public void setBirt(String forcvusmNombreBirt) {
        this.birt = forcvusmNombreBirt;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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
        if (!(object instanceof FormatoCvUsmempleo)) {
            return false;
        }
        FormatoCvUsmempleo other = (FormatoCvUsmempleo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.utfsm.redexalumnos.crm.entities.FormatoCvUsmempleo[forcvusmId=" + id + "]";
    }
}
