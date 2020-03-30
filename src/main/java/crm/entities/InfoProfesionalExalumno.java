package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla info_profesional_exalumno. Contiene la
 * informacion profesional de un usuario.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "info_profesional_exalumno")
public class InfoProfesionalExalumno implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instancia actual
     */
    private Long usuarioId;

    /**
     * Entero que indica la expectativa salarial del exalumno
     */
    private Integer expectativaSalarial;

    /**
     * Booleano que indica si el exalumno se puede mover en la region
     */
    private Boolean movilidadRegion;

    /**
     * Booleano que indica si el exalumno se puede mover en el pais
     */
    private Boolean movilidadPais;

    /**
     * Entero que indica los anios de experiencia laboral del exalumno
     */
    private Short anoExpLaboral;

    /**
     * Booleano que indica si el exalumno posee un vehiculo propio
     */
    private Boolean vehiculoPropio;

    /**
     * Booleano que indica si el exalumno posee licencia de conducir
     */
    private Boolean licenciaConducir;

    /**
     * Objetivo profesional del exalumno
     */
    private String objetivoProfesional;

    /**
     * Distinciones que posee el exalumno
     */
    private String distinciones;

    /**
     * Fecha de la ultima modificacion de una instancia
     */
    private Date fechaModificacion;

    /**
     * Rut del ultimo usuario que la modifico
     */
    private Integer rutUsuario;

    /**
     * Usuario al cual pertenece la informacion profesional
     */
    private Usuario usuario;

    /**
     * Tipo de moneda que utiliza el exalumno
     */
    private TipoMoneda tipoMoneda;

    /**
     * Disponibilidad (part-time, full-time, etc)
     */
    private TipoDisponibilidad disponibilidad;

    /**
     * Nivel de dominio del computador
     */
    private TipoDominioCompu dominioComputacional;

    /**
     * Situacion laboral actual del exalumno
     */
    private TipoSituacionLaboral situacionLaboral;

    public InfoProfesionalExalumno() {
    }

    public InfoProfesionalExalumno(InfoProfesionalExalumno info){

    }

    @Id
    @Column(name = "usuaex_id", unique= true, nullable = false)
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuaexId) {
        this.usuarioId = usuaexId;
    }

    @Column(name = "infpro_expec_salarial")
    public Integer getExpectativaSalarial() {
        return expectativaSalarial;
    }

    public void setExpectativaSalarial(Integer infproExpecSalarial) {
        this.expectativaSalarial = infproExpecSalarial;
    }

    @Column(name = "infpro_movilidad_region")
    public Boolean getMovilidadRegion() {
        return movilidadRegion;
    }

    public void setMovilidadRegion(Boolean infproMovilidadRegion) {
        this.movilidadRegion = infproMovilidadRegion;
    }

    @Column(name = "infpro_movilidad_pais")
    public Boolean getMovilidadPais() {
        return movilidadPais;
    }

    public void setMovilidadPais(Boolean infproMovilidadPais) {
        this.movilidadPais = infproMovilidadPais;
    }

    @Column(name = "infpro_año_exp_laboral")
    public Short getAnoExpLaboral() {
        return anoExpLaboral;
    }

    public void setAnoExpLaboral(Short infproAnoExpLaboral) {
        this.anoExpLaboral = infproAnoExpLaboral;
    }

    @Column(name = "infpro_vehiculo_propio")
    public Boolean getVehiculoPropio() {
        return vehiculoPropio;
    }

    public void setVehiculoPropio(Boolean infproVehiculoPropio) {
        this.vehiculoPropio = infproVehiculoPropio;
    }

    @Column(name = "infpro_licencia_conducir")
    public Boolean getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(Boolean infproLicenciaConducir) {
        this.licenciaConducir = infproLicenciaConducir;
    }

    @Column(name = "infpro_objetivo_profesional")
    public String getObjetivoProfesional() {
        return objetivoProfesional;
    }

    public void setObjetivoProfesional(String infproObjetivoProfesional) {
        this.objetivoProfesional = infproObjetivoProfesional;
    }

    @Column(name = "infpro_distinciones")
    public String getDistinciones() {
        return distinciones;
    }

    public void setDistinciones(String infproDistinciones) {
        this.distinciones = infproDistinciones;
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

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = true)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarioAexa) {
        this.usuario = usuarioAexa;
    }

    @JoinColumn(name = "cod_moneda", referencedColumnName = "cod_moneda")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    @JoinColumn(name = "cod_disponibilidad", referencedColumnName = "cod_disponibilidad")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(TipoDisponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @JoinColumn(name = "cod_dominio_compu", referencedColumnName = "cod_dominio_compu")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoDominioCompu getDominioComputacional() {
        return dominioComputacional;
    }

    public void setDominioComputacional(TipoDominioCompu dominioComputacional) {
        this.dominioComputacional = dominioComputacional;
    }

    @JoinColumn(name = "cod_situacion_laboral", referencedColumnName = "cod_situacion_laboral")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoSituacionLaboral getSituacionLaboral() {
        return situacionLaboral;
    }

    public void setSituacionLaboral(TipoSituacionLaboral situacionLaboral) {
        this.situacionLaboral = situacionLaboral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoProfesionalExalumno)) {
            return false;
        }
        InfoProfesionalExalumno other = (InfoProfesionalExalumno) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.utfsm.redexalumnos.crm.entities.InfoProfesionalExalumno[usuaexId=" + usuarioId + "]";
    }

    @Transient
    public void setDefaultValues(Long id, TipoMoneda mon, TipoDisponibilidad disp,
                                   TipoDominioCompu dom, TipoSituacionLaboral sit) {
        this.usuarioId = id;
        this.tipoMoneda = mon;
        this.objetivoProfesional = null;
        this.expectativaSalarial = 0;
        this.movilidadRegion = false;
        this.movilidadPais = false;
        this.anoExpLaboral = 0;
        this.vehiculoPropio = false;
        this.licenciaConducir = false;
        this.distinciones = null;
        this.disponibilidad = disp;
        this.dominioComputacional = dom;
        this.situacionLaboral = sit;

    }
}