package crm.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by famancil on 29-07-16.
 */
@Entity
@Table(name = "compromiso_empresa", schema = "crm")
public class CompromisoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id del compromiso
     */
    private Long id;

    /**
     * Tipo de forma de pago del compromiso, segun los tipos especificados en {@link TipoFormaPago}
     */
    private TipoFormaPago tipoFormaPago;

    /**
     * Tipo de vigencia del compromiso, segun los tipos especificados en {@link TipoVigencia}
     */
    private TipoVigencia tipoVigencia;

    /**
     * Tipo de la oportunidad del compromiso, segun los tipos especificados en {@link TipoOportunidad}
     */
    private TipoCompromiso tipoCompromiso;

    /**
     * Institucion a la que se hace el compromiso, segun los tipos especificados en {@link Institucion}
     */
    private Institucion institucion;

    /**
     * Sucursal de la empresa que hace el compromiso, segun los tipos especificados en {@link SucursalEmpresa}
     */
    private SucursalEmpresa sucursalEmpresa;

    /**
     * Operador a cargo del manejo del compromiso
     */
    private Usuario operadorACargo;

    /**
     * Monto comprometido en el compromiso por la empresa
     */
    private Integer montoComprometido;

    /**
     * Fecha de Inicio del compromiso
     */
    @DateTimeFormat(pattern="dd/MM/YYYY")
    private Date fechaInicio;

    /**
     * Fecha de termino del compromiso
     */
    @DateTimeFormat(pattern="dd/MM/YYYY")
    private Date fechaTermino;

    /**
     * Fecha del siguiente compromiso
     */
    @DateTimeFormat(pattern="dd/MM/YYYY")
    private Date fechaSiguienteCompromiso;

    /**
     * Rut de quien crea/modifica el compromiso en la BD
     */
    private String comentario;

    /**
     * Rut de quien crea/modifica el compromiso en la BD
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion/modificacion del compromiso en la BD
     */
    private Date fechaModificacion;

    /*
    * Indica la cantidad de aportes a la entidad actual.
    */
    private Integer cantidadAportes=-1;


    public CompromisoEmpresa() {
    }

    public CompromisoEmpresa(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compromiso_empresa_seq_gen")
    @SequenceGenerator(name = "compromiso_empresa_seq_gen", sequenceName = "crm.compromiso_empresa_comemp_id_seq", allocationSize = 1)
    @Column(name = "comemp_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_forma_pago", referencedColumnName = "cod_forma_pago")
    @ManyToOne
    public TipoFormaPago getTipoFormaPago() {
        return tipoFormaPago;
    }

    public void setTipoFormaPago(TipoFormaPago tipoFormaPago) {
        this.tipoFormaPago = tipoFormaPago;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @JoinColumn(name = "cod_compromiso", referencedColumnName = "cod_compromiso")
    @ManyToOne
    public TipoCompromiso getTipoCompromiso() {
        return tipoCompromiso;
    }

    public void setTipoCompromiso(TipoCompromiso tipoCompromiso) {
        this.tipoCompromiso = tipoCompromiso;
    }

    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion")
    @ManyToOne
    public Institucion getInstitucion() { return institucion; }

    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }

    @JoinColumn(name = "suc_codigo", referencedColumnName = "suc_codigo")
    @ManyToOne
    public SucursalEmpresa getSucursalEmpresa() { return sucursalEmpresa; }

    public void setSucursalEmpresa(SucursalEmpresa sucursalEmpresa) { this.sucursalEmpresa = sucursalEmpresa; }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne
    public Usuario getOperadorACargo() {
        return operadorACargo;
    }

    public void setOperadorACargo(Usuario operadorACargo) {
        this.operadorACargo = operadorACargo;
    }

    @Column(name = "comemp_monto_comprometido")
    public Integer getMontoComprometido() {
        return montoComprometido;
    }

    public void setMontoComprometido(Integer montoComprometido) {
        this.montoComprometido = montoComprometido;
    }

    @Column(name = "comemp_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "comemp_fecha_termino")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    @Column(name = "comemp_sig_compromiso")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaSiguienteCompromiso() {
        return fechaSiguienteCompromiso;
    }

    public void setFechaSiguienteCompromiso(Date fechaSiguienteCompromiso) {
        this.fechaSiguienteCompromiso = fechaSiguienteCompromiso;
    }

    @Column(name = "comemp_comentario")
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    @Transient
    public Integer getCantidadAportes() {
        return cantidadAportes;
    }

    public void setCantidadAportes(Integer cantidadAportes) {
        this.cantidadAportes = cantidadAportes;
    }

}
