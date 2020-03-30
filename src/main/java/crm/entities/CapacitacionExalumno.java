package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Entidad correspondiente a la tabla public.capacitacion_exalumno.
 * Guarda los cursos que ha realizado un usuario
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "capacitacion_exalumno")
public class CapacitacionExalumno {

    /**
     * Identificador de la capacitacion
     */
    private Long id;

    /**
     * Usuario que realizó la capacitacion
     */
    private Usuario usuario;

    /**
     * Nombre de la capacitacion.
     */
    private String nombre;

    /**
     * Descripción de la capacitacion.
     */
    private String descripcion;

    /**
     * Nombre de la instición en la que se realizó la capacitacion.
     */
    private String institucion;

    /**
     * Numero de horas de la capacitacion.
     */
    private Short numeroHoras;

    /**
     * Fecha de inicio de la capacitación.
     */
    private Date fechaInicio;

    /**
     * Fecha de termino de la capacitación.
     */
    private Date fechaFin;

    /**
     * Fecha de ultima modificacion de la instancia actual.
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;




    @Id
    @Column(name = "capexa_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "capexa_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "capexa_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "capexa_institucion")
    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    @Column(name = "capexa_num_horas")
    public Short getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(Short numeroHoras) {
        this.numeroHoras = numeroHoras;
    }

    @Column(name = "capexa_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "capexa_fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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
