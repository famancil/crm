package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla crm.perfil.
 * TODO comentar
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "perfil", schema = "crm")
public class Perfil {

    /**
     * Identificador del perfil
     */
    private Integer codigo;

    /**
     * Nombre del perfil
     */
    private String nombre;

    /**
     * TODO
     */
    private String codigoGrupoPerfil;

    /**
     * Jerarquia del Perfil
     */
    private Integer jerarquia;

    /**
     * Descripción del perfil
     */
    private String descripcion;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "cod_perfil")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_perfil")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "cod_grupo_perfil")
    public String getCodigoGrupoPerfil() {
        return codigoGrupoPerfil;
    }

    public void setCodigoGrupoPerfil(String codigoGrupoPerfil) {
        this.codigoGrupoPerfil = codigoGrupoPerfil;
    }

    @Column(name = "perf_jerarquia")
    public Integer getJerarquia() {
        return jerarquia;
    }

    public void setJerarquia(Integer jerarquia) {
        this.jerarquia = jerarquia;
    }

    @Column(name = "perf_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "fecha_modificacion")
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