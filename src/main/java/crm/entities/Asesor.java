package crm.entities;


import javax.persistence.*;
import java.util.Date;

/**
 * Guarda la informacion de un Usuario que ha realizado algun
 * tipo de asesoria
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "asesor", schema = "asesoria")
public class Asesor {

    /**
     * TODO comentar
     */
    private Long usuarioId;

    /**
     * Usuario asesor
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private Date fechaInicio;

    /**
     * TODO comentar
     */
    private Date fechaFin;

    /**
     * TODO comentar
     */
    private String descripcion;

    /**
     * TODO comentar
     */
    private Integer horasDisponibleSemana;

    /**
     * Fecha de creacion en la BD
     */
    private Date fechaCreacion;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que crea/modifica en la BD
     */
    private Integer rutUsuario;



    @Id
    @Column(name = "usuaex_id", unique = true, nullable = false)
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuaexId) {
        this.usuarioId = usuaexId;
    }


    @MapsId
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = true)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "num_horas_disp_semana")
    public Integer getHorasDisponibleSemana() {
        return horasDisponibleSemana;
    }

    public void setHorasDisponibleSemana(Integer horasDisponibleSemana) {
        this.horasDisponibleSemana = horasDisponibleSemana;
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