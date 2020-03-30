package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla seguridad.acceso_sistema. Realiza la relacion entre que {@link crm.entities.Sistema} puede
 * acceder un {@link crm.entities.Usuario}. Para el caso del CRM los usuarios deben poder tener acceso al sistema de
 * codigo 38 = "Asociación de ex alumnos AEXA".
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "acceso_sistema", schema = "seguridad")
public class AccesoSistema {

    /**
     * Clave primaria compuesta de la entidad.
     */
    private AccesoSistemaPK compositeId;

    /**
     * Sistema al cual se esta asignando permisos de acceso.
     */
    private Sistema sistema;

    /**
     * Usuario que posee acceso al sistema correspondiente.
     */
    private Usuario usuario;

    /**
     * Rut del usuario que realizo la ultima modificacion en una instancia de la clase.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion realizada a la instancia de una clase.
     */
    private Date fechaCreacion;

    /**
     * Nombre delo usuario que realizo la ultima modificacion (se obtiene a partir de rutUsuario)
     */
    private String rutUsuarioNombre;


    public AccesoSistema() {

    }

    @EmbeddedId
    public AccesoSistemaPK getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(AccesoSistemaPK compositeId) {
        this.compositeId = compositeId;
    }

    @JoinColumn(name = "cod_sistema", referencedColumnName = "cod_sistema", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    @JoinColumn(name = "usuario_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Transient
    public String getRutUsuarioNombre() {
        return rutUsuarioNombre;
    }

    public void setRutUsuarioNombre(String rutUsuarioNombre) {
        this.rutUsuarioNombre = rutUsuarioNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccesoSistema)) return false;

        AccesoSistema that = (AccesoSistema) o;

        if (compositeId != null ? !compositeId.equals(that.compositeId) : that.compositeId != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (rutUsuario != null ? !rutUsuario.equals(that.rutUsuario) : that.rutUsuario != null) return false;
        if (sistema != null ? !sistema.equals(that.sistema) : that.sistema != null) return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = compositeId != null ? compositeId.hashCode() : 0;
        result = 31 * result + (sistema != null ? sistema.hashCode() : 0);
        result = 31 * result + (usuario != null ? usuario.hashCode() : 0);
        result = 31 * result + (rutUsuario != null ? rutUsuario.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        return result;
    }
}
