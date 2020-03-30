package crm.entities;

import javax.persistence.*;
import java.util.Date;


/**
 * Entidad correspondiente a la tabla aexa.aporte_socio.
 * Contiene un listado con los aportes realizados por los socios
 *
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "aporte_socio", schema = "aexa")
@IdClass(AporteSocioPK.class)
public class AporteSocio {

    /**
     * Identificador del {@link Usuario} asociado al {@link AporteSocio}
     */
    private Long idUsuario;

    /**
     * Identificador del {@link CompromisoSocio} asociado al {@link AporteSocio}
     */
    private Long idCompromisoSocio;

    /**
     * {@link crm.entities.Usuario} que realiza el aporte
     */
    private Usuario usuario;

    /**
     * Compromiso del socio del que hace el aporte
     */
    private CompromisoSocio compromisoSocio;

    /**
     * Fecha del aporte
     */
    private Date fecha;

    /**
     * Tipo Estado de Pago del aporte
     */
    private TipoEstadoPago tipoEstadoPago;

    /**
     * Tipo Aporte del aporte
     */
    private TipoAporte tipoAporte;

    /**
     * Campaña con la que se logra el aporte
     */
    private CampanaExalumno campanaExalumno;

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
     * Rut del {@link crm.entities.Usuario} que realizo la ultima modificacion en la instancia actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la instancia actual
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

    @Id
    @Column(name = "comsoc_id")
    public Long getIdCompromisoSocio() {
        return idCompromisoSocio;
    }

    public void setIdCompromisoSocio(Long idCompromisoSocio) {
        this.idCompromisoSocio = idCompromisoSocio;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "cod_estado_pago", referencedColumnName = "cod_estado_pago")
    @ManyToOne
    public TipoEstadoPago getTipoEstadoPago() {
        return tipoEstadoPago;
    }

    public void setTipoEstadoPago(TipoEstadoPago tipoEstadoPago) {
        this.tipoEstadoPago = tipoEstadoPago;
    }

    @JoinColumn(name = "cod_aporte", referencedColumnName = "cod_aporte")
    @ManyToOne(optional = false)
    public TipoAporte getTipoAporte() {
        return tipoAporte;
    }

    public void setTipoAporte(TipoAporte tipoAporte) {
        this.tipoAporte = tipoAporte;
    }

    @JoinColumn(name = "comsoc_id", referencedColumnName = "comsoc_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public CompromisoSocio getCompromisoSocio() {
        return compromisoSocio;
    }

    public void setCompromisoSocio(CompromisoSocio compromisoSocio) {
        this.compromisoSocio = compromisoSocio;
    }

    @JoinColumn(name = "camexa_id", referencedColumnName = "camexa_id")
    @ManyToOne
    public CampanaExalumno getCampanaExalumno() {
        return campanaExalumno;
    }

    public void setCampanaExalumno(CampanaExalumno campanaExalumno) {
        this.campanaExalumno = campanaExalumno;
    }

    @Column(name = "aposoc_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "aposoc_monto_pagado")
    public Integer getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Integer montoPagado) {
        this.montoPagado = montoPagado;
    }

    @Column(name = "aposoc_fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Column(name = "aposoc_descripcion")
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
