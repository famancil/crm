package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.usuario_empresa_usmempleo.
 *
 * @author  Ignacio Oneto <Ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "usuario_empresa_usmempleo", schema = "org")
public class UsuarioEmpresaUsmempleo implements Serializable {

    /*
    * Identificador de la entidad actual.
    */
    private Long id;

    /*
    * Pais de la entidad actual.
    */
    private Pais pais;

    /*
    * Comuna de la entidad actual.
    */
    private Comuna comuna;

    /*
    * Nombre completo del usuario.
    */
    private String nombreCompleto;

    /*
    * Fono del usuario.
    */
    private String fono;

    /*
    * Direccion del usuario.
    */
    private String direccion;

    /*
    * Celular del usuario.
    */
    private String celular;

    /*
    * Email del usuario.
    */
    private String email;

    /*
    * Password del usuario.
    */
    private String password;

    /*
    * Cargo del usuario.
    */
    private String cargo;

    /*
    * Rut del usuario que realizo la ultima modificacion de la entidad actual.
    */
    private Integer rutUsuario;

    /*
    * Fecha de la ultima modificacion de la entidad actual.
    */
    private Date fechaModificacion;

    /*
    * Rut del usuario
    */
    private Integer rut;

    /*
    * Fono opcional del usuario
    */
    private String fonoOpcional;

    /*
        * Estado de video conferencia.
        */
    private Integer estadoVideoConferencia;

    public UsuarioEmpresaUsmempleo(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_empresa_usmempleo_seq_gen")
    @SequenceGenerator(name = "usuario_empresa_usmempleo_seq_gen", sequenceName = "org.usuario_empresa_usmempleo_seq", allocationSize = 1)
    @Column(name = "usuempusm_id")
    @JsonView(ResponseView.MainView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @JoinColumn(name = "cod_comuna", referencedColumnName = "cod_comuna")
    @ManyToOne(fetch = FetchType.LAZY)
    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @Column(name = "usuempusm_nombre_completo")
    @JsonView(ResponseView.MainView.class)
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Column(name = "usuempusm_fono")
    @JsonView(ResponseView.MainView.class)
    public String getFono() {
        return fono;
    }

    public void setFono(String fono) {
        this.fono = fono;
    }

    @Column(name = "usuempusm_direccion")
    @JsonView(ResponseView.MainView.class)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "usuempusm_celular")
    @JsonView(ResponseView.MainView.class)
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Column(name = "usuempusm_email")
    @JsonView(ResponseView.MainView.class)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "usuempusm_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "usuempusm_cargo")
    @JsonView(ResponseView.MainView.class)
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "usuempusm_rut")
    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    @Column(name = "usuempusm_fono_opcional")
    public String getFonoOpcional() {
        return fonoOpcional;
    }

    public void setFonoOpcional(String fonoOpcional) {
        this.fonoOpcional = fonoOpcional;
    }

    @Column(name = "usuempusm_estado_video_conferencia")
    public Integer getEstadoVideoConferencia() {
        return estadoVideoConferencia;
    }

    public void setEstadoVideoConferencia(Integer estadoVideoConferencia) {
        this.estadoVideoConferencia = estadoVideoConferencia;
    }



}
