package crm.entities;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Entidad correspondiente a la tabla public.usuario_aexa_apoderado.
 * Contiene la información del apoderado del usuario.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
// TODO revisar porque no tiene foranea de comuna

@Entity
@Table(name = "usuario_aexa_apoderado")
public class UsuarioApoderado implements Serializable {

    /**
     * Identificador del usuario del cual es apoderado
     */
    private Long usuarioId;

    /**
     * Usuario del cual es apoderado
     */
    private Usuario usuario;

    /*
    * Rut del Apoderado
    */
    private Integer rut;

    /*
    * Digito verificador del Apoderado
    */
    private String digitoVerificador;

    /*
    * Telefono del Apoderado
    */
    private Integer fono;

    /*
    * Telefono del Apoderado
    */
    private String nombre;

    /*
    * Dirección del Apoderado
    */
    private String direccion;

    /*
    * Comuna del Apoderado
    */
    private Short comuna;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;

    /**
     * Fecha de creación del Usuario Apoderado en la BD
     */
    private Date fechaCreacion;

    /**
     * Fecha de modificación del Usuario Apoderado en la BD
     */
    private Date fechaModificacion;



    public UsuarioApoderado() {
    }


    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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

    @Column(name = "usuaex_rut_apoderado")
    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    @Column(name = "usuaex_dv_apoderado")
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    @Column(name = "usuaex_fono_apoderado")
    public Integer getFono() {
        return fono;
    }

    public void setFono(Integer fono) {
        this.fono = fono;
    }

    @Column(name = "usuaex_nombre_apoderado")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "usuaex_direccion_apoderado")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "usuaex_comuna_apoderado")
    public Short getComuna() {
        return comuna;
    }

    public void setComuna(Short comuna) {
        this.comuna = comuna;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
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
}
