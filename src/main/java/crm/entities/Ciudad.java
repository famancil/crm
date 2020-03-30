package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entidad correspondiente a la tabla dbo.ciudad. Contiene un listado con las ciudades de Chile.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "ciudad")
public class Ciudad implements Serializable{

    /**
     * Identificador de la ciudad. Ademas corresponde al codigo de area de esta.
     */
    private Short codigo;

    /**
     * Nombre de la ciudad.
     */
    private String nombre;

    /**
     * Fecha de ultima modificacion de la instancia actual.
     */
    private Timestamp fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;

    @Id
    @Column(name = "cod_ciudad")
    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "nom_ciudad")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "fecha_modificacion")
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Basic
    @Column(name = "rut_usuario")
    public int getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(int rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ciudad ciudad = (Ciudad) o;

        return Objects.equals(codigo, ciudad.codigo) && Objects.equals(rutUsuario, ciudad.rutUsuario) &&
                !(fechaModificacion != null ? !fechaModificacion.equals(ciudad.fechaModificacion) : ciudad.fechaModificacion != null)
                && !(nombre != null ? !nombre.equals(ciudad.nombre) : ciudad.nombre != null);

    }

    @Override
    public int hashCode() {
        int result = (int) codigo;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (fechaModificacion != null ? fechaModificacion.hashCode() : 0);
        result = 31 * result + rutUsuario;
        return result;
    }
}
