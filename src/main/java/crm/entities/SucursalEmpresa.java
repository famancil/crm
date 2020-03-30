package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.sucursal_empresa.
 * Contiene las sucursales de una empresa
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "sucursal_empresa", schema = "org")
public class SucursalEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * Id de la sucursal (a nivel de BD)
    */
    private Long sucursalCodigo;

    /*
    * Empresa a la que pertenece la sucursal
    */
    private Empresa empresa;

    /*
    * Tipo de vigencia de la sucursal
    */
    private TipoVigencia tipoVigencia;

    /*
    * Pais donde se encuentra la sucursal
    */
    private Pais pais;

    /*
    * Comuna donde se encuentra la sucursal
    */
    private Comuna comuna;

    /*
    * Nombre de la sucursal
    */
    private String sucSucursal;

    /*
    * Fono de la sucursal
    */
    private String sucFono;

    /*
    * Fax de la sucursal
    */
    private String sucFax;

    /*
    * Direccion de la sucursal
    */
    private String sucDireccion;

    /*
    * Email de la sucursal
    */
    private String sucEmail;

    /*
    * Rut de quien modifica la sucursal en la BD
    */
    private Integer rutUsuario;

    /*
     * Fecha de modificacion del compromiso en la BD
     */
    private Date fechaModificacion;

    /*
     * Latitud de la sucursal
     */
    private String sucLatitud;

    /*
     * Longitud de sucursal
     */
    private String sucLongitud;

    /**
     * Indica el numero de ofertas que ha publicado la empresa (no se encuentra en la base de datos).
     */
    private Integer cantidadOfertas;

    /**
     * Indica el numero de publicadores que tiene una empresa (no se encuentra en la base de datos).
     */
    private Integer cantidadPublicadores;

    /**
     * Indica el numero de usuarios que han tenido relacion con la empresa (no se encuentra en la base de datos).
     */
    private Integer cantidadUsuarios;

    /**
     * Lista de {@Link UsuarioUsmempleoEmpresa} asociadas a la sucursal
     */
    private List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresaList;




    public SucursalEmpresa() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sucursal_empresa_seq_gen")
    @SequenceGenerator(name = "sucursal_empresa_seq_gen", sequenceName = "org.sucursal_codigo_seq", allocationSize = 1)
    @Column(name = "suc_codigo")
    @JsonView(ResponseView.MainView.class)
    public Long getSucursalCodigo() {
        return sucursalCodigo;
    }

    public void setSucursalCodigo(Long sucursalCodigo) {
        this.sucursalCodigo = sucursalCodigo;
    }

    @JoinColumn(name = "perempusm_id", referencedColumnName = "perempusm_id")
    @ManyToOne(optional = false)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne(optional = false)
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @JoinColumn(name = "cod_comuna", referencedColumnName = "cod_comuna")
    @ManyToOne
    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @Column(name = "suc_sucursal")
    @JsonView(ResponseView.MainView.class)
    public String getSucSucursal() {
        return sucSucursal;
    }

    public void setSucSucursal(String sucSucursal) {
        this.sucSucursal = sucSucursal;
    }

    @Column(name = "suc_fono")
    public String getSucFono() {
        return sucFono;
    }

    public void setSucFono(String sucFono) {
        this.sucFono = sucFono;
    }

    @Column(name = "suc_fax")
    public String getSucFax() {
        return sucFax;
    }

    public void setSucFax(String sucFax) {
        this.sucFax = sucFax;
    }

    @Column(name = "suc_direccion")
    public String getSucDireccion() {
        return sucDireccion;
    }

    public void setSucDireccion(String sucDireccion) {
        this.sucDireccion = sucDireccion;
    }

    @Column(name = "suc_email")
    public String getSucEmail() {
        return sucEmail;
    }

    public void setSucEmail(String sucEmail) {
        this.sucEmail = sucEmail;
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

    @Column(name = "suc_latitud")
    public String getSucLatitud() {
        return sucLatitud;
    }

    public void setSucLatitud(String sucLatitud) {
        this.sucLatitud = sucLatitud;
    }

    @Column(name = "suc_longitud")
    public String getSucLongitud() {
        return sucLongitud;
    }

    public void setSucLongitud(String sucLongitud) {
        this.sucLongitud = sucLongitud;
    }

    @Transient
    public Integer getCantidadOfertas() {
        return cantidadOfertas;
    }

    public void setCantidadOfertas(Integer cantidadOfertas) {
        this.cantidadOfertas = cantidadOfertas;
    }

    @Transient
    public Integer getCantidadPublicadores() {
        return cantidadPublicadores;
    }

    public void setCantidadPublicadores(Integer cantidadPublicadores) {
        this.cantidadPublicadores = cantidadPublicadores;
    }

    @Transient
    public Integer getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    public void setCantidadUsuarios(Integer cantidadUsuarios) {
        this.cantidadUsuarios = cantidadUsuarios;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "sucursalEmpresa")
    public List<UsuarioUsmempleoEmpresa> getUsuarioUsmempleoEmpresaList() {
        return usuarioUsmempleoEmpresaList;
    }

    public void setUsuarioUsmempleoEmpresaList(List<UsuarioUsmempleoEmpresa> usuarioUsmempleoEmpresaList) {
        this.usuarioUsmempleoEmpresaList = usuarioUsmempleoEmpresaList;
    }
}
