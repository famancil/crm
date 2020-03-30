package crm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Entidad correspondiente a la tabla dbo.sistema. Contiene la definicion de todos los sistemas informaticos que existen
 * en la universidad y en la red de exalumnos. Se utiliza principalmente para conocer a que sistemas tienen acceso
 * distintos {@link crm.entities.Usuario}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "sistema", schema = "seguridad")
public class Sistema {

    /**
     * Codigo identificador de cada sistema.
     */
    private Short codigo;

    /**
     * Nombre del sistema.
     */
    private String nombre;

    /**
     * Rut del usuario que realizo la insercion o modificacion de la instancia actual
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion del rol.
     */
    private Timestamp fechaCreacion;

    /**
     * Fecha de ultima modificacion del rol.
     */
    private Timestamp fechaModificacion;

    public Sistema() {

    }

    public Sistema(Short codigo) {
        this.codigo = codigo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistema_seq_gen")
    @SequenceGenerator(name = "sistema_seq_gen", sequenceName = "sistema_codigo_seq", allocationSize = 1)
    @Column(name = "cod_sistema")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codSistema) {
        this.codigo = codSistema;
    }

    @Basic
    @Column(name = "nom_sistema")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomSistema) {
        this.nombre = nomSistema;
    }

    @Basic
    @Column(name = "rut_usuario")
    public int getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(int rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Basic
    @Column(name = "fecha_creacion")
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Basic
    @Column(name = "fecha_modificacion")
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sistema)) return false;

        Sistema sistema = (Sistema) o;

        if (codigo != null ? !codigo.equals(sistema.codigo) : sistema.codigo != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(sistema.fechaCreacion) : sistema.fechaCreacion != null)
            return false;
        if (fechaModificacion != null ? !fechaModificacion.equals(sistema.fechaModificacion) : sistema.fechaModificacion != null)
            return false;
        if (nombre != null ? !nombre.equals(sistema.nombre) : sistema.nombre != null) return false;
        if (rutUsuario != null ? !rutUsuario.equals(sistema.rutUsuario) : sistema.rutUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (rutUsuario != null ? rutUsuario.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        result = 31 * result + (fechaModificacion != null ? fechaModificacion.hashCode() : 0);
        return result;
    }
}