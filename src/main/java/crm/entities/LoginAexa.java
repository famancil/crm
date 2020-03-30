package crm.entities;


import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.login_aexa. Contiene la informacion de acceso a los sistemas de la red de
 * exalumnos para los usuarios que poseen accesos a dichos sistemas. Las passwords de todos los usuarios en todos los
 * sistemas se encuentran encriptadas con el algoritmo MD5.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "login_aexa")
public class LoginAexa {

    /**
     * Identificador del {@link Usuario} asociado a las credenciales de acceso.
     */
    private Long usuarioId;

    /**
     * Nombre de usuario para acceder a los sistemas. En general es el email del {@link Usuario}.
     */
    private String username;

    /**
     * Password del {@link Usuario}.
     */
    private String password;

    /**
     * Intentos fallidos de ingreso realizados por el {@link Usuario}.
     */
    private Short intentosFallidosIngreso;

    /**
     * Fecha en la que el {@link Usuario} realizo por ultima vez un cambio de su clave.
     */
    private Date fechaUltimoCambioClave;

    /**
     * Fecha de ultima modificacion de la instancia actual.
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;

    /**
     * {@link Usuario} asociado a las presentes credenciales de acceso.
     */
    private Usuario usuario;

    public LoginAexa() {
    }

    public LoginAexa(LoginAexa login){

    }

    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuaexId) {
        this.usuarioId = usuaexId;
    }

    @Column(name = "logaex_username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String logaexUsername) {
        this.username = logaexUsername;
    }

    @Column(name = "logaex_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String logaexPassword) {
        this.password = logaexPassword;
    }

    @Column(name = "logaex_intentos")
    public Short getIntentosFallidosIngreso() {
        return intentosFallidosIngreso;
    }

    public void setIntentosFallidosIngreso(Short logaexIntentos) {
        this.intentosFallidosIngreso = logaexIntentos;
    }

    @Column(name = "logaex_fecha_clave")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaUltimoCambioClave() {
        return fechaUltimoCambioClave;
    }

    public void setFechaUltimoCambioClave(Date logaexFechaClave) {
        this.fechaUltimoCambioClave = logaexFechaClave;
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

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = true)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginAexa loginAexa = (LoginAexa) o;

        return usuarioId.equals(loginAexa.usuarioId);
    }

    @Override
    public String toString() {
        return "LoginAexa{" +
                "usuarioId=" + usuarioId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", intentosFallidosIngreso=" + intentosFallidosIngreso +
                ", fechaUltimoCambioClave=" + fechaUltimoCambioClave +
                ", fechaModificacion=" + fechaModificacion +
                ", rutUsuario=" + rutUsuario +
                ", usuario=" + usuario +
                '}';
    }

    @Override
    public int hashCode() {
        return usuarioId.hashCode();
    }
}