package crm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Entidad correspondiente a la tabla crm.permiso_acceso. Contiene un listado con todos los permisos disponibles que
 * se pueden aplicar sobre objetos del sistema. Los permisos corresponde a verbos que representan acciones, tales como:
 *
 * <ul>
 *     <li>Leer</li>
 *     <li>Escribir</li>
 *     <li>Eliminar</li>
 *     <li>...</li>
 * </ul>
 *
 * Se pueden agregar mas permisos al sistema sin desmedro de la funcionalidad del sistema de permisos.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "permiso_acceso", schema = "seguridad")
public class PermisoAcceso {

    public final static String LEER = "Leer";
    public final static String CREAR = "Crear";
    public final static String EDITAR = "Editar";
    public final static String ELIMINAR = "Eliminar";

    /**
     * Codigo identificador del permiso
     */
    private Short codigo;

    /**
     * Nombre del permiso
     */
    private String nombre;

    /**
     * Rut del usuario que inserto o modifico la instancia actual
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion del permiso
     */
    private Timestamp fechaCreacion;

    /**
     * Fecha de modificacion del permiso
     */
    private Timestamp fechaModificacion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permiso_acceso_seq_gen")
    @SequenceGenerator(name = "permiso_acceso_seq_gen", sequenceName = "permiso_acceso_codigo_seq", allocationSize = 1)
    @Column(name = "codigo")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
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
        if (o == null || getClass() != o.getClass()) return false;

        PermisoAcceso that = (PermisoAcceso) o;

        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (fechaModificacion != null ? !fechaModificacion.equals(that.fechaModificacion) : that.fechaModificacion != null)
            return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (rutUsuario != null ? !rutUsuario.equals(that.rutUsuario) : that.rutUsuario != null) return false;

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
