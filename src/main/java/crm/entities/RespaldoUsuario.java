package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Tabla en la cual se registran mediante un trigger los datos de contacto de
 * un usuario aexa en el caso de update , delete
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "respaldo_usuario_aexa_contacto")
@IdClass(RespaldoUsuarioPK.class)
public class RespaldoUsuario {

    /**
     * Identificador del {@link Usuario} asociada al {@link RespaldoUsuario}
     */
    private Long idUsuario;

    /**
     * {@link Usuario} asociada al {@link RespaldoUsuario}
     */
    private Usuario usuario;

    /**
     * Email del {@link Usuario}
     */
    private String email;

    /**
     * Email opcional del {@link Usuario}
     */
    private String emailOpcional;

    /**
     * Email laboral del {@link Usuario}
     */
    private String emailLaboral;

    /**
     * Direccion del {@link Usuario}
     */
    private String direccion;

    /**
     * Celular del {@link Usuario}
     */
    private String celular;

    /**
     * Fono Particular del {@link Usuario}
     */
    private String fonoParticular;

    /**
     * Rut del {@link Usuario} que modifica el {@link RespaldoUsuario}
     */
    private Integer rutUsuario;

    /**
     * Fecha de modificacion del {@link RespaldoUsuario}
     */
    private Date fechaModificacion;





    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "resinfocontac_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "resinfocontac_email_opcional")
    public String getEmailOpcional() {
        return emailOpcional;
    }

    public void setEmailOpcional(String emailOpcional) {
        this.emailOpcional = emailOpcional;
    }

    @Column(name = "resinfocontac_email_laboral")
    public String getEmailLaboral() {
        return emailLaboral;
    }

    public void setEmailLaboral(String emailLaboral) {
        this.emailLaboral = emailLaboral;
    }

    @Column(name = "resinfocontac_direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "resinfocontac_celular")
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Column(name = "resinfocontac_fono_particular")
    public String getFonoParticular() {
        return fonoParticular;
    }

    public void setFonoParticular(String fonoParticular) {
        this.fonoParticular = fonoParticular;
    }

    @Id
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
