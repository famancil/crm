package crm.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by famancil on 29-07-16.
 */
@Entity
@Table(name = "aporte_empresa", schema = "crm")
public class AporteEmpresa implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Id del aporte
     */
    private Long id;

    /**
     * Sucursal de la empresa que hace el aporte, segun los tipos especificados en {@link SucursalEmpresa}
     */
    private SucursalEmpresa sucursalEmpresa;

    /**
     * Compromiso de la empresa del que hace el aporte, segun los tipos especificados en {@link CompromisoEmpresa}
     */
    private CompromisoEmpresa compromisoEmpresa;

    /**
     * Tipo Estado de Pago del aporte, segun los tipos especificados en {@link TipoEstadoPago}
     */
    private TipoEstadoPago tipoEstadoPago;

    /**
     * Tipo de forma de pago del aporte, segun los tipos especificados en {@link TipoFormaPago}
     */
    private TipoFormaPago tipoFormaPago;

    /**
     * Institucion a la que se hace el aporte, segun los tipos especificados en {@link Institucion}
     */
    private Institucion institucion;

    /**
     * Tipo de Comprobante del aporte, segun los tipos especificados en {@link TipoComprobante}
     */
    private TipoComprobante tipoComprobante;

    /**
     * Fecha del aporte
     */
    @DateTimeFormat(pattern="dd/MM/YYYY")
    private Date fecha;

    /**
     * Monto pagado del aporte
     */
    private Integer montoPagado;

    /**
     * Fecha de pago del aporte
     */
    private Date fechaPago;

    /**
     * Descripcion del aporte
     */
    private String descripcion;

    /**
     * Rut de quien crea/modifica el compromiso en la BD
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion/modificacion del compromiso en la BD
     */
    private Date fechaModificacion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aporte_empresa_seq_gen")
    @SequenceGenerator(name = "aporte_empresa_seq_gen", sequenceName = "crm.aporte_empresa_apoemp_id_seq", allocationSize = 1)
    @Column(name = "apoemp_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "suc_codigo", referencedColumnName = "suc_codigo")
    @ManyToOne
    public SucursalEmpresa getSucursalEmpresa() { return sucursalEmpresa; }

    public void setSucursalEmpresa(SucursalEmpresa sucursalEmpresa) { this.sucursalEmpresa = sucursalEmpresa; }

    @JoinColumn(name = "comemp_id", referencedColumnName = "comemp_id")
    @ManyToOne
    public CompromisoEmpresa getCompromisoEmpresa() { return compromisoEmpresa; }

    public void setCompromisoEmpresa(CompromisoEmpresa compromisoEmpresa) { this.compromisoEmpresa = compromisoEmpresa;}

    @JoinColumn(name = "cod_estado_pago", referencedColumnName = "cod_estado_pago")
    @ManyToOne
    public TipoEstadoPago getTipoEstadoPago() {
        return tipoEstadoPago;
    }

    public void setTipoEstadoPago(TipoEstadoPago tipoEstadoPago) {
        this.tipoEstadoPago = tipoEstadoPago;
    }

    @JoinColumn(name = "cod_forma_pago", referencedColumnName = "cod_forma_pago")
    @ManyToOne
    public TipoFormaPago getTipoFormaPago() {
        return tipoFormaPago;
    }

    public void setTipoFormaPago(TipoFormaPago tipoFormaPago) {
        this.tipoFormaPago = tipoFormaPago;
    }

    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion")
    @ManyToOne
    public Institucion getInstitucion() { return institucion; }

    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }

    @JoinColumn(name = "cod_comprobante", referencedColumnName = "cod_comprobante")
    @ManyToOne
    public TipoComprobante getTipoComprobante() { return tipoComprobante; }

    public void setTipoComprobante(TipoComprobante tipoComprobante) { this.tipoComprobante = tipoComprobante; }

    @Column(name = "apoemp_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "apoemp_monto_pagado")
    public Integer getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Integer montoPagado) {
        this.montoPagado = montoPagado;
    }

    @Column(name = "apoemp_fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Column(name = "apoemp_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

}
