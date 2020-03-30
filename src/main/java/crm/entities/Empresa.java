package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

/**
 * @author ignacio oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "perfil_empresa_usmempleo", schema = "org")
public class Empresa implements Serializable {

    /**
     * Identificador de la instancia actual.
     */
    private Long id;

    /**
     * Numero identificador en caso de que la empresa sea extranjera
     * (si es nacional, este valor debe ser nulo).
     */
    private String idEmpresaExtranjera;

    /**
     * Numero (rut nacional) identificador en caso de que la empresa sea nacional
     * (si es extranjera este valor debe ser nulo).
     */
    private String rutEmpresa;

    /**
     * Tipo de vigencia de la empresa
     * (proviene de la tabla tipo_vigencia).
     */
    private TipoVigencia vigencia;

    /**
     * Sector al cual pertenece la empresa
     * (proviene de la tabla tipo_sector).
     */
    private TipoSector sector;

    /**
     * nivel de facturacion de la empresa
     * (proviene de la tabla tipo_nivel_facturacion).
     */
    private TipoNivelFacturacion nivelFacturacion;

    /**
     * Pais al cual pertenece la empresa
     * (proviene de la tabla pais).
     */
    private Pais pais;

    /**
     * Descripcion de la empresa.
     */
    private String descripcion;

    /**
     * Indica si la empresa tiene logo o no.
     */
    private Boolean logo;

    /**
     * Numero de empleados que tiene la empresa.
     */
    private Integer numEmpleados;

    /**
     * Numero de contratos anuales de la empresa.
     */
    private Short numContratAnu;

    /**
     * Razon social de la empresa.
     */
    private String razonSocial;

    /**
     * Giro de la empresa.
     */
    private String giroEmpresa;

    /**
     * Indica si la empresa es headhunter o no.
     */
    private Boolean headHunter;

    /**
     * Indica si la empresa esta o no subscrita al boletin exalumnos.
     */
    private Boolean boletinExalumnos;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion.
     */
    private Date fechaModificacion;

    /**
     * Estado de la empresa
     */
    private Boolean estado;

    /**
     * Nombre de fantasia de la empresa.
     */
    private String nombreFantasiaEmpresa;

    /**
     * Sigla de la empresa.
     */
    private String sigla;

    /**
     * URL de la empresa.
     */
    private String url;

    /**
     * Slug del URL de la empresa.
     */

    private String urlSlug;

    /**
     * Indica si la empresa tiene calidad premium o no.
     */
    private Boolean premiumEmpresa;

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
     * Indica el numero de sucursales que tiene una empresa (no se encuentra en la base de datos)
     */
    private Integer cantidadSucursales;

    /**
     * Indica el numero de sucursales por validar que tiene una empresa (no se encuentra en la base de datos)
     */
    private Integer cantidadSucursalesPorValidar;

    /**
     * Lista de sucursales que posee la empresa
     */
    private List<SucursalEmpresa> sucursales;

    /**
     * Lista de sucursales por validar que posee la empresa
     */
    private List<SucursalEmpresa> sucursalesPorValidar;

    public Empresa(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_seq_gen")
    @SequenceGenerator(name = "empresa_seq_gen", sequenceName = "org.perfil_empresa_usmempleo_seq", allocationSize = 1)
    @Column(name = "perempusm_id")
    @JsonView(ResponseView.MainView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "empextaex_id")
    public String getIdEmpresaExtranjera() {
        return idEmpresaExtranjera;
    }

    public void setIdEmpresaExtranjera(String idEmpresaExtranjera) {
        this.idEmpresaExtranjera = idEmpresaExtranjera;
    }

    @Column(name = "rut_empresa")
    @JsonView(ResponseView.MainView.class)
    public String getRutEmpresa() {
        return rutEmpresa;
    }

    public void setRutEmpresa(String rutEmpresa) {
        this.rutEmpresa = rutEmpresa;
    }

    @JoinColumn(name = "cod_sector", referencedColumnName = "cod_sector")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoSector getSector() {
        return sector;
    }

    public void setSector(TipoSector sector) {
        this.sector = sector;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoVigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(TipoVigencia vigencia) {
        this.vigencia = vigencia;
    }

    @JoinColumn(name = "cod_nivel_facturacion", referencedColumnName = "cod_nivel_facturacion")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoNivelFacturacion getNivelFacturacion() {
        return nivelFacturacion;
    }

    public void setNivelFacturacion(TipoNivelFacturacion nivelFacturacion) {
        this.nivelFacturacion = nivelFacturacion;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Column(name = "perempusm_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "perempusm_logo")
    public Boolean getLogo() {
        return logo;
    }

    public void setLogo(Boolean logo) {
        this.logo = logo;
    }

    @Column(name = "perempusm_num_empleados")
    public Integer getNumEmpleados() {
        return numEmpleados;
    }

    public void setNumEmpleados(Integer numEmpleados) {
        this.numEmpleados = numEmpleados;
    }

    @Column(name = "perempusm_num_contrat_anu")
    public Short getNumContratAnu() {
        return numContratAnu;
    }

    public void setNumContratAnu(Short numContratAnu) {
        this.numContratAnu = numContratAnu;
    }

    @Column(name = "perempusm_razon_social")
    @JsonView(ResponseView.MainView.class)
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Column(name = "perempusm_giro_empresa")
    @JsonView(ResponseView.MainView.class)
    public String getGiroEmpresa() {
        return giroEmpresa;
    }

    public void setGiroEmpresa(String giroEmpresa) {
        this.giroEmpresa = giroEmpresa;
    }

    @Column(name = "perempusm_head_hunter")
    public Boolean getHeadHunter() {
        return headHunter;
    }

    public void setHeadHunter(Boolean headHunter) {
        this.headHunter = headHunter;
    }

    @Column(name = "perempusm_boletin_exalumnos")
    public Boolean getBoletinExalumnos() {
        return boletinExalumnos;
    }

    public void setBoletinExalumnos(Boolean boletinExalumnos) {
        this.boletinExalumnos = boletinExalumnos;
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

    @Column(name = "perempusm_estado")
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Column(name = "perempusm_nombre_fantasia_empresa")
    @JsonView(ResponseView.MainView.class)
    public String getNombreFantasiaEmpresa() {
        return nombreFantasiaEmpresa;
    }

    public void setNombreFantasiaEmpresa(String nombreFantasiaEmpresa) {
        this.nombreFantasiaEmpresa = nombreFantasiaEmpresa;
    }

    @Column(name = "perempusm_sigla")
    @JsonView(ResponseView.MainView.class)
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Column(name = "perempusm_url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "perempusm_url_slug")
    public String getUrlSlug() {
        return urlSlug;
    }

    public void setUrlSlug(String urlSlug) {
        this.urlSlug = urlSlug;
    }

    @Column(name = "perempusm_premium_empresa")
    public Boolean getPremiumEmpresa() {
        return premiumEmpresa;
    }

    public void setPremiumEmpresa(Boolean premiumEmpresa) {
        this.premiumEmpresa = premiumEmpresa;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "empresa")
    public List<SucursalEmpresa> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<SucursalEmpresa> sucursales) {
        this.sucursales = sucursales;
    }

    @Transient
    public List<SucursalEmpresa> getSucursalesPorValidar() {
        return sucursalesPorValidar;
    }

    public void setSucursalesPorValidar(List<SucursalEmpresa> sucursalesPorValidar) {
        this.sucursalesPorValidar = sucursalesPorValidar;
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

    @Transient
    public Integer getCantidadSucursales() {
        return cantidadSucursales;
    }

    public void setCantidadSucursales(Integer cantidadSucursales) {
        this.cantidadSucursales = cantidadSucursales;
    }

    @Transient
    public Integer getCantidadSucursalesPorValidar() {
        return cantidadSucursalesPorValidar;
    }

    public void setCantidadSucursalesPorValidar(Integer cantidadSucursalesPorValidar) {
        this.cantidadSucursalesPorValidar = cantidadSucursalesPorValidar;
    }
}
