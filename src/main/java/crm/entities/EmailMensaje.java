package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que corresponde a la tabla empleo.email_mensaje
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */

@Entity
@Table(name = "email_mensaje", schema = "empleo")
public class EmailMensaje implements Serializable {

    /**
     * Identificador de la clase
     */
    private Integer id;

    /**
     * Asunto del email
     */
    private String asunto;

    /**
     * Mensaje del email
     */
    private String mensaje;

    /**
     * Breve descripcion del email
     */
    private String descripcion;


    private String contenidoResp;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica de la institucion en la BD
     */
    private Integer rutUsuario;

    @Id
    @Column(name = "email_mensaje_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email_mensaje_asunto")
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    @Column(name = "email_mensaje_contenido")
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Column(name = "descripcion_contenido")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "email_mensaje_contenido_resp")
    public String getContenidoResp() {
        return contenidoResp;
    }

    public void setContenidoResp(String contenidoResp) {
        this.contenidoResp = contenidoResp;
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
