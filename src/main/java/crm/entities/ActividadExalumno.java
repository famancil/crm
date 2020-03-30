package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla actividad_exalumno.
 * Contiene los empleos en los que ha estado el usuario y ha
 * registrado en el sistema.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "actividad_exalumno", schema = "public")
public class ActividadExalumno implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * Id de la ActividadExalumno
    */
    private Long id;

    /**
    * Empresa donde trabajo el exalumno
    */
    private Empresa empresa;

    /**
    * Sucursal donde trabajo el exalumno
    */
    private SucursalEmpresa sucursalEmpresa;

    /**
    * Tipo de cargo desempeñado
    */
    private TipoCargo tipoCargo;

    /**
    * Usuario asociado a la actividad
    */
    private Usuario usuario;

    /**
    * Tipo de actividad desempeñada
    */
    private TipoActividadExalumno tipoActividadExalumno;

    /**
    * Tipo de moneda en que recibió el pago
    */
    private TipoMoneda tipoMoneda;

    /**
    * Nombre real del cargo desempeñado
    */
    private String cargo;

    /**
    * Fecha en que comenzó la actividad
    */
    private Date fechaIngreso;

    /**
    * Fecha en que terminó la actividad
    */
    private Date fechaEgreso;

    /**
    * Departamento dentro de la empresa en el cual desempeñó el cargo
    */
    private String departamento;

    /**
    * Remuneracion Liquida obtenida
    */
    private Integer remuneracion;

    /**
    * Descripción de los detalles referentes a la remuneración
    */
    private String descripcionRemuneracion;

    /**
    * Notas en escala 1 a 7 que el usuario coloca a la remuneración
    */
    private Short notaRemuneracion;

    /**
    * Notas en escala 1 a 7 que el usuario coloca a la estabilidad del trabajo
    */
    private Short notaEstabilidad;

    /**
    * Notas en escala 1 a 7 que el usuario coloca al ingreso en la empresa. (qué tan dificil es entrar en ella)
    */
    private Short notaIngreso;

    /**
    * Notas en escala 1 a 7 que el usuario coloca al ambiente en la empresa
    */
    private Short notaAmbiente;

    /**
    * Notas en escala 1 a 7 que el usuario coloca a la empresa
    */
    private Short notaEmpresa;

    /**
    * Notas en escala 1 a 7 que el usuario coloca a qué tan feliz estuvo dentro de la empresa
    */
    private Short notaFelicidad;

    /**
    * Descripción del cargo, labores desarrolladas, logros
    */
    private String descripcion;

    /**
    * Orden de los antecedentes del exalumno, ordenado en forma ascendente
    */
    private Short posicionOrden;

    /**
    * Fecha de modificacion de la actividadexalumno en la BD
    */
    private Date fechaModificacion;

    /**
    * Rut de quien crea/modifica la actividadexalumno en la BD
    */
    private Integer rutUsuario;

    /**
     * Cantidad de compromisos que tiene el exalumno cno la red de exalumnos.
     */
    private Integer cantidadCompromisos;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "act_exalumno_seq_gen")
    @SequenceGenerator(name = "act_exalumno_seq_gen", sequenceName = "actividad_exalumno_actexa_seq", allocationSize = 1)
    @Column(name = "actexa_id")
    public Long getId() {
        return id;
    }

    public void setId(Long actexa_id) {
        this.id = actexa_id;
    }

    @JoinColumn(name = "perempusm_id", referencedColumnName = "perempusm_id")
    @OneToOne(optional = false)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JoinColumn(name = "cod_cargo", referencedColumnName = "cod_cargo")
    @OneToOne(optional = false)
    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @OneToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "cod_actividad_exalumno", referencedColumnName = "cod_actividad_exalumno")
    @OneToOne
    public TipoActividadExalumno getTipoActividadExalumno() {
        return tipoActividadExalumno;
    }

    public void setTipoActividadExalumno(TipoActividadExalumno tipoActividadExalumno) {
        this.tipoActividadExalumno = tipoActividadExalumno;
    }

    @JoinColumn(name = "cod_moneda", referencedColumnName = "cod_moneda")
    @OneToOne
    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    @Column(name = "actexa_cargo")
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String actexaCargo) {
        this.cargo = actexaCargo;
    }

    @Column(name = "actexa_fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date actexaFechaIngreso) {
        this.fechaIngreso = actexaFechaIngreso;
    }

    @Column(name = "actexa_fecha_egreso")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date actexaFechaEgreso) {
        this.fechaEgreso = actexaFechaEgreso;
    }

    @Column(name = "actexa_departamento")
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String actexaDepartamento) {
        this.departamento = actexaDepartamento;
    }

    @Column(name = "actexa_remuneracion_liquida")
    public Integer getRemuneracion() {
        return remuneracion;
    }

    public void setRemuneracion(Integer actexaRemuneracionLiquida) {
        this.remuneracion = actexaRemuneracionLiquida;
    }

    @Column(name = "actexa_descrip_remuneracion")
    public String getDescripcionRemuneracion() {
        return descripcionRemuneracion;
    }

    public void setDescripcionRemuneracion(String actexaDescripRemuneracion) {
        this.descripcionRemuneracion = actexaDescripRemuneracion;
    }

    @Column(name = "actexa_nota_remuneracion")
    public Short getNotaRemuneracion() {
        return notaRemuneracion;
    }

    public void setNotaRemuneracion(Short actexaNotaRemuneracion) {
        this.notaRemuneracion = actexaNotaRemuneracion;
    }

    @Column(name = "actexa_nota_estabilidad")
    public Short getNotaEstabilidad() {
        return notaEstabilidad;
    }

    public void setNotaEstabilidad(Short actexaNotaEstabilidad) {
        this.notaEstabilidad = actexaNotaEstabilidad;
    }

    @Column(name = "actexa_nota_ingreso")
    public Short getNotaIngreso() {
        return notaIngreso;
    }

    public void setNotaIngreso(Short actexaNotaIngreso) {
        this.notaIngreso = actexaNotaIngreso;
    }

    @Column(name = "actexa_nota_ambiente")
    public Short getNotaAmbiente() {
        return notaAmbiente;
    }

    public void setNotaAmbiente(Short actexaNotaAmbiente) {
        this.notaAmbiente = actexaNotaAmbiente;
    }

    @Column(name = "actexa_nota_empresa")
    public Short getNotaEmpresa() {
        return notaEmpresa;
    }

    public void setNotaEmpresa(Short actexaNotaEmpresa) {
        this.notaEmpresa = actexaNotaEmpresa;
    }

    @Column(name = "actexa_nota_felicidad")
    public Short getNotaFelicidad() {
        return notaFelicidad;
    }

    public void setNotaFelicidad(Short actexaNotaFelicidad) {
        this.notaFelicidad = actexaNotaFelicidad;
    }

    @Column(name = "actexa_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String actexaDescripcion) {
        this.descripcion = actexaDescripcion;
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

    @JoinColumn(name = "sucursal_codigo", referencedColumnName = "suc_codigo")
    @OneToOne
    public SucursalEmpresa getSucursalEmpresa() {
        return sucursalEmpresa;
    }

    public void setSucursalEmpresa(SucursalEmpresa sucursalEmpresa) {
        this.sucursalEmpresa = sucursalEmpresa;
    }

    @Column(name = "actexa_posicion_orden")
    public Short getPosicionOrden() {
        return posicionOrden;
    }

    public void setPosicionOrden(Short actexa_posicion_orden) {
        this.posicionOrden = actexa_posicion_orden;
    }

    @Transient
    public Integer getCantidadCompromisos() {
        return cantidadCompromisos;
    }

    public void setCantidadCompromisos(Integer cantidadCompromisos) {
        this.cantidadCompromisos = cantidadCompromisos;
    }
}
