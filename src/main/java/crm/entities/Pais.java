package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad correspondiente a la tabla dbo.pais. Contiene un listado con los paises del mundo.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "pais")
public class Pais implements Serializable{

    /**
     * Identificador de la instancia actual.
     */
    private Short id;

    /**
     * Nombre del pais.
     */
    private String nombre;

    /**
     * Nombre de la nacionalidad en idioma espanol asociada al pais.
     */
    private String nombreNacionalidad;

    /**
     * Codigo de area del pais.
     */
    private Short codigo;

    /**
     * Fecha de ucreación de la instancia actual.
     */
    private Date fechaCreacion;

    /**
     * Fecha de ultima modificacion de la instancia actual.
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;

    public Pais() {
    }

    @Id
    @Column(name = "cod_pais")
    public Short getId() {
        return id;
    }

    public void setId(Short codPais) {
        this.id = codPais;
    }

    @Column(name = "nom_pais")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomPais) {
        this.nombre = nomPais;
    }

    @Column(name = "nom_nacionalidad")
    public String getNombreNacionalidad() {
        return nombreNacionalidad;
    }

    public void setNombreNacionalidad(String nomNacionalidad) {
        this.nombreNacionalidad = nomNacionalidad;
    }

    @Column(name = "num_pais")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short numPais) {
        this.codigo = numPais;
    }

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

}