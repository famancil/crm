package crm.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Entidad correspondiente a la tabla crm.rol_acceso. Contiene un listado con todos los roles disponibles para el
 * sistema CRM. Un rol es un permiso global asociado a un usuario tal que le permite realizar acciones que otros
 * usuarios que no poseen dicho rol no podrian realizar. Sin embargo, en ciertos metodos podria requerirse de un
 * acceso mayor que un rol para un usuario por lo que en algunas circunstancias un usuario debe poseer un rol + un
 * permiso tipo ACL para ejecutar una accion. El sistema de permisos del CRM permite ambos casos:
 * <br>
 *     <ul>
 *         <li>Se puede proteger un metodo solicitando solo un rol para acceder. Ej:
 *         <code>@PreAuthorize(hasRole('ROL_ADMIN'))</code></li>
 *         <li>Se puede proteger un metodo solicitando tanto un rol como un permiso adicional. Ej:
 *         <code>@PreAuthorize(hasPermission(T(fully.qualified.OtherClass).MODULE), 'WRITE')</code><br>
 *             En este caso se puede verificar que el usuario posea un permiso determinado si es que el rol que posee
 *             dicho usuario posee esos permisos. Esta opcion de autorizacion es la preferida ya que permite mucha
 *             flexibilidad para asignar permisos.</li>
 *     </ul>
 * La idea en el sistema de permisos es que se creen dinamicamente {@link crm.entities.RolAcceso} y a estos roles
 * se les asignen dinamicamente permisos sobre los modulos del CRM. Asi, en los metodos que requieran proteccion se
 * utilice la segunda forma de autorizar (hasPermission).
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "rol_acceso", schema = "seguridad")
public class RolAcceso {

    public final static String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    public final static String ADMIN_INSTITUCION = "ROLE_ADMIN_INSTITUCION";
    public final static String AYUDANTE_INSTITUCION = "ROLE_AYUDANTE_INSTITUCION";
    public final static String LEER_INSTITUCION = "ROLE_LEER_INSTITUCION";
    public final static String ADMIN_CARRERA = "ROLE_ADMIN_CARRERA";

    /**
     * Identificador del rol.
     */
    private Short id;

    /**
     * Nombre del rol.
     */
    private String nombre;

    /**
     * Nombre del rol a utilizar en las vistas del sistema.
     */
    private String nombrePresentacion;

    /**
     * Descripcion del rol.
     */
    private String descripcion;

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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_acceso_seq_gen")
    @SequenceGenerator(name = "rol_acceso_seq_gen", sequenceName = "rol_acceso_id_seq", allocationSize = 1)
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
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
    @Column(name = "nombre_presentacion")
    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(o instanceof RolAcceso)) return false;

        RolAcceso rolAcceso = (RolAcceso) o;

        if (descripcion != null ? !descripcion.equals(rolAcceso.descripcion) : rolAcceso.descripcion != null)
            return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(rolAcceso.fechaCreacion) : rolAcceso.fechaCreacion != null)
            return false;
        if (fechaModificacion != null ? !fechaModificacion.equals(rolAcceso.fechaModificacion) : rolAcceso.fechaModificacion != null)
            return false;
        if (id != null ? !id.equals(rolAcceso.id) : rolAcceso.id != null) return false;
        if (nombre != null ? !nombre.equals(rolAcceso.nombre) : rolAcceso.nombre != null) return false;
        if (nombrePresentacion != null ? !nombrePresentacion.equals(rolAcceso.nombrePresentacion) : rolAcceso.nombrePresentacion != null)
            return false;
        if (rutUsuario != null ? !rutUsuario.equals(rolAcceso.rutUsuario) : rolAcceso.rutUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (nombrePresentacion != null ? nombrePresentacion.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (rutUsuario != null ? rutUsuario.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        result = 31 * result + (fechaModificacion != null ? fechaModificacion.hashCode() : 0);
        return result;
    }
}
