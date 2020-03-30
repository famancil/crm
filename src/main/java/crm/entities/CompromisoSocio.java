package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad correspondiente a la tabla dbo.compromiso_socio.
 * Contiene los compromisos asocuados a un {@link crm.entities.Usuario}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "compromiso_socio", schema = "aexa")
public class CompromisoSocio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * Id del compromiso
    */
    private Long id;

    /**
    * Tipo de aporte del compromiso, segun los tipos especificados en {@link TipoAporte}
    */
    private TipoAporte tipoAporte;

    /**
    * Tipo de vigencia del compromiso, segun los tipos especificados en {@link TipoVigencia}
    */
    private TipoVigencia tipoVigencia;

    /**
    * Tipo de la causa aporte del compromiso, segun los tipos especificados en {@link TipoCausaAporte}
    */
    private TipoCausaAporte tipoCausaAporte;

    /**
    * Usuario asociado al compromiso
    */
    private Usuario usuario;

    /**
    * Tipo de periocidad del compromiso, segun los tipos especificados en {@link TipoPeriocidad}
    */
    private TipoPeriocidad tipoPeriocidad;

    /**
    * Tipo de periocidad del compromiso, segun los tipos especificados en {@link TipoPeriocidad}
    */
    private CampanaExalumno campanaExalumno;

    /**
    * Monto comprometido en el compromiso por el socio
    */
    private Integer montoComprometido;

    /**
    * Saldo a favor del monto que ha pagado el usuario.
    * (usuario paga mas de lo que se compromete)
    */
    private Integer saldoFavor;

    /**
    * Fecha de Inicio del compromiso
    */
    private Date fechaInicio;

    /**
    * Fecha de termino del compromiso
    */
    private Date fechaTermino;

    /**
    * Fecha del siguiente compromiso
    */
    private Date siguienteCompromiso;

    /**
    * Indica si el socio se encuentra al dia con su compromiso
    */
    private Boolean socioAlDia;

    /**
    * Operador a cargo del manejo del compromiso
    */
    private Long operadorACargo;

    /**
    * Fecha de creacion/modificacion del compromiso en la BD
    */
    private Date fechaModificacion;

    /**
    * Rut de quien crea/modifica el compromiso en la BD
    */
    private Integer rutUsuario;


    public CompromisoSocio() {
    }

    public CompromisoSocio(Long id) {
        this.id = id;
    }

    public CompromisoSocio(Long id, Boolean socioAlDia) {
        this.id = id;
        this.socioAlDia = socioAlDia;
    }

    @Id
    @Column(name = "comsoc_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_aporte", referencedColumnName = "cod_aporte")
    @ManyToOne(optional = false)
    public TipoAporte getTipoAporte() {
        return tipoAporte;
    }

    public void setTipoAporte(TipoAporte tipoAporte) {
        this.tipoAporte = tipoAporte;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @JoinColumn(name = "cod_causa_aporte", referencedColumnName = "cod_causa_aporte")
    @ManyToOne(optional = false)
    public TipoCausaAporte getTipoCausaAporte() {
        return tipoCausaAporte;
    }

    public void setTipoCausaAporte(TipoCausaAporte tipoCausaAporte) {
        this.tipoCausaAporte = tipoCausaAporte;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "cod_periocidad", referencedColumnName = "cod_periocidad")
    @ManyToOne(optional = false)
    public TipoPeriocidad getTipoPeriocidad() {
        return tipoPeriocidad;
    }

    public void setTipoPeriocidad(TipoPeriocidad tipoPeriocidad) {
        this.tipoPeriocidad = tipoPeriocidad;
    }

    @JoinColumn(name = "camexa_id", referencedColumnName = "camexa_id")
    @ManyToOne
    public CampanaExalumno getCampanaExalumno() {
        return campanaExalumno;
    }

    public void setCampanaExalumno(CampanaExalumno campanaExalumno) {
        this.campanaExalumno = campanaExalumno;
    }

    @Column(name = "comsoc_monto_comprometido")
    public Integer getMontoComprometido() {
        return montoComprometido;
    }

    public void setMontoComprometido(Integer montoComprometido) {
        this.montoComprometido = montoComprometido;
    }

    @Column(name = "comsoc_saldo_favor")
    public Integer getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(Integer saldoFavor) {
        this.saldoFavor = saldoFavor;
    }


    @Column(name = "comsoc_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "comsoc_fecha_termino")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    @Column(name = "comsoc_sig_compromiso")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSiguienteCompromiso() {
        return siguienteCompromiso;
    }

    public void setSiguienteCompromiso(Date siguienteCompromiso) {
        this.siguienteCompromiso = siguienteCompromiso;
    }

    @Column(name = "comsoc_socio_al_dia")
    public Boolean getSocioAlDia() {
        return socioAlDia;
    }

    public void setSocioAlDia(Boolean socioAlDia) {
        this.socioAlDia = socioAlDia;
    }

    @Column(name = "comsoc_operador_a_cargo")
    public Long getOperadorACargo() {
        return operadorACargo;
    }

    public void setOperadorACargo(Long operadorACargo) {
        this.operadorACargo = operadorACargo;
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
