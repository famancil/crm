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
 * Entidad correspondiente a la tabla dbo.campana_exalumno.
 * Contiene las campanas realizadas
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "campaña_exalumno", schema = "aexa")
public class CampanaExalumno implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * Id de la campana exalumno
    */
    private Long id;

    /*
    * Tipo de campana, segun los tipos especificados en {@link TipoCampana}
    */
    private TipoCampana tipoCampana;

    /*
    * Tipo del objetivo de la campana, segun los tipos especificados en {@link TipoObjetivoCampana}
    */
    private TipoObjetivoCampana tipoObjetivoCampana;

    /*
    * Usuario del sistema que crea la campaña
    */
    private Usuario usuario;

    /*
    * Nombre de la campana
    */
    private String nombre;

    /*
    * Fecha de creacion de la campana
    */
    private Date fechaCreacionCampana;

    /*
    * Fecha de termino de la campana
    */
    private Date fechaTerminoCampana;

    /*
    * Fecha de inicio de la campana
    */
    private Date fechaInicioCampana;

    /*
    * Presupuesto asociado a la campana
    */
    private Integer presupuesto;

    /*
    * Costo real de la campana
    */
    private Integer costoReal;

    /*
    * Ingresos Esperados por la campana
    */
    private Integer ingresosEsperados;

    /*
    * Descripcion de la campana
    */
    private String descripcion;

    /*
    * Cantidad de socios potenciales esperados por la campana
    */
    private Integer sociosPotenciales;

    /*
    * Cantidad de socios logrados por la campana
    */
    private Integer sociosLogrados;

    /*
    * Descripcion de algun aprendizaje durante la campaña
    */
    private String aprendizajes;

    /**
     * Fecha de modificacion de la campana en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica la campana en la BD
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion de la campana en la BD
     */
    private Date fechaCreacion;






    public CampanaExalumno() {
    }

    public CampanaExalumno(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "camexa_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_campaña", referencedColumnName = "cod_campaña")
    @ManyToOne
    public TipoCampana getTipoCampana() {
        return tipoCampana;
    }

    public void setTipoCampana(TipoCampana tipoCampana) {
        this.tipoCampana = tipoCampana;
    }

    @JoinColumn(name = "cod_objetivo_campaña", referencedColumnName = "cod_objetivo_campaña")
    @ManyToOne
    public TipoObjetivoCampana getTipoObjetivoCampana() {
        return tipoObjetivoCampana;
    }

    public void setTipoObjetivoCampana(TipoObjetivoCampana tipoObjetivoCampana) {
        this.tipoObjetivoCampana = tipoObjetivoCampana;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "camexa_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "camexa_fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaCreacionCampana() {
        return fechaCreacionCampana;
    }

    public void setFechaCreacionCampana(Date fechaCreacionCampana) {
        this.fechaCreacionCampana = fechaCreacionCampana;
    }

    @Column(name = "camexa_fecha_termino")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaTerminoCampana() {
        return fechaTerminoCampana;
    }

    public void setFechaTerminoCampana(Date fechaTerminoCampana) {
        this.fechaTerminoCampana = fechaTerminoCampana;
    }

    @Column(name = "camexa_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicioCampana() {
        return fechaInicioCampana;
    }

    public void setFechaInicioCampana(Date fechaInicioCampana) {
        this.fechaInicioCampana = fechaInicioCampana;
    }

    @Column(name = "camexa_presupuesto")
    public Integer getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Integer presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Column(name = "camexa_costo_real")
    public Integer getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(Integer costoReal) {
        this.costoReal = costoReal;
    }

    @Column(name = "camexa_ing_esperados")
    public Integer getIngresosEsperados() {
        return ingresosEsperados;
    }

    public void setIngresosEsperados(Integer ingresosEsperados) {
        this.ingresosEsperados = ingresosEsperados;
    }

    @Column(name = "camexa_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "camexa_socios_potenciales")
    public Integer getSociosPotenciales() {
        return sociosPotenciales;
    }

    public void setSociosPotenciales(Integer sociosPotenciales) {
        this.sociosPotenciales = sociosPotenciales;
    }

    @Column(name = "camexa_socios_logrados")
    public Integer getSociosLogrados() {
        return sociosLogrados;
    }

    public void setSociosLogrados(Integer sociosLogrados) {
        this.sociosLogrados = sociosLogrados;
    }

    @Column(name = "camexa_aprendizajes")
    public String getAprendizajes() {
        return aprendizajes;
    }

    public void setAprendizajes(String aprendizajes) {
        this.aprendizajes = aprendizajes;
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


    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
