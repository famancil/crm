package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Contiene la relacion entre las distintas aptitudes que puede tener
 * un alumno o exalumno para efectos de exhibir sus capacidades a la
 * hora de realizar una asesoria
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "aptitud_usuario", schema = "asesoria")
public class AptitudUsuario {

    /**
     * Id de la aptitud.
     */
    private Long id;

    /**
     * Tipo de aptitud de la Aptitud Usuario.
     */
    private TipoAptitud tipoAptitud;

    /**
     * Usuario asociado a la Aptitud
     */
    private Usuario usuario;

    /**
     * Indica el nivel de conocimiento que tiene el Usuario acerca la aptitud en cuestion (los valores van entre 1 y 10 donde 1 significa nivel basico y 10 significa nivel experto)
     */
    private Short nivelConocimiento;

    /**
     * Fecha de modificacion de la carrera en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica la carrera en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "aptusu_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_aptitud", referencedColumnName = "cod_aptitud")
    @ManyToOne(optional = false)
    public TipoAptitud getTipoAptitud() {
        return tipoAptitud;
    }

    public void setTipoAptitud(TipoAptitud tipoAptitud) {
        this.tipoAptitud = tipoAptitud;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "nivel_conocimiento")
    public Short getNivelConocimiento() {
        return nivelConocimiento;
    }

    public void setNivelConocimiento(Short nivelConocimiento) {
        this.nivelConocimiento = nivelConocimiento;
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
