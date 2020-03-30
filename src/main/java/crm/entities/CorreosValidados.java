package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Permite saber si un correo electrónico fue validado.
 * Funciona en conjunto con los sistemas que envían correos y con el
 * proveedor de despacho de emails, por ejemplo AWS, Mailchimp, etc
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "correos_validados")
public class CorreosValidados implements Serializable {

    /*
    * Id del correo validado
    */
    private Long id;

    /*
    * Id del Usuario
    */
    private Long idUsuario;

    /*
    * Email validado
    */
    private String email;

    /**
     * TODO comentar
     */
    private Date inicioValidacion;

    /**
     * TODO comentar
     */
    private Date finValidacion;

    /**
     * TODO comentar
     */
    private Short complaint;

    /**
     * TODO comentar
     */
    private Short bounced;

    /**
     * TODO comentar
     */
    private Short bounceSoft;

    /**
     * Fecha de ultima modificacion de la instancia actual.
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "corval_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Column(name = "corval_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "corval_fecha_inicio_validacion")
    public Date getInicioValidacion() {
        return inicioValidacion;
    }

    public void setInicioValidacion(Date inicioValidacion) {
        this.inicioValidacion = inicioValidacion;
    }

    @Column(name = "corval_fecha_fin_validacion")
    public Date getFinValidacion() {
        return finValidacion;
    }

    public void setFinValidacion(Date finValidacion) {
        this.finValidacion = finValidacion;
    }

    @Column(name = "corval_complaint")
    public Short getComplaint() {
        return complaint;
    }

    public void setComplaint(Short complaint) {
        this.complaint = complaint;
    }

    @Column(name = "corval_bounced")
    public Short getBounced() {
        return bounced;
    }

    public void setBounced(Short bounced) {
        this.bounced = bounced;
    }

    @Column(name = "corval_bounce_soft")
    public Short getBounceSoft() {
        return bounceSoft;
    }

    public void setBounceSoft(Short bounceSoft) {
        this.bounceSoft = bounceSoft;
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
