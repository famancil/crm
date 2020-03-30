package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.filtro_oferta_laboral.
 * Esta tabla se utiliza para guardar los filtros automáticos
 * y poder adicionalmente guardar cual filtro será enviado por
 * correo cuando existan ofertas laborales que cumplan el filtro
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "filtro_oferta_laboral", schema = "empleo")
public class FiltroOfertaLaboral {

    /**
     * Id del FiltroOfertaLaboral
     */
    private Integer id;

    /**
     * Pais del FiltroOfertaLaboral
     */
    private Pais pais;

    /**
     * Region del FiltroOfertaLaboral
     */
    private Region region;

    /**
     * TipoEstudio del FiltroOfertaLaboral
     */
    private TipoEstudio tipoEstudio;

    /**
     * TipoCargo del FiltroOfertaLaboral
     */
    private TipoCargo tipoCargo;

    /**
     * TipoArea del FiltroOfertaLaboral
     */
    private TipoArea tipoArea;

    /**
     * TipoMoneda del FiltroOfertaLaboral
     */
    private TipoMoneda tipoMoneda;

    /**
     * Carrera del FiltroOfertaLaboral
     */
    private Carrera carrera;

    /**
     * Usuario del FiltroOfertaLaboral
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private String rangoAniosExperiencia;

    /**
     * TODO comentar
     */
    private Short aniosExperiencia;

    /**
     * TODO comentar
     */
    private Integer salarioLimiteInferior;

    /**
     * TODO comentar
     */
    private Integer salarioLimiteSuperior;

    /**
     * TODO comentar
     */
    private Boolean enviarEmail;

    /**
     * TODO comentar
     */
    private Short diaEnvio;

    /**
     * TODO comentar
     */
    private Short momentoEnvio;

    /**
     * TODO comentar
     */
    private String nombre;
    /**
     * Fecha de creacion del filtro en la BD
     */
    private Date fechaCreacion;

    /**
     * Fecha de modificacion del filtro en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica el filtro en la BD
     */
    private Integer rutUsuario;







    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filtro_oferta_laboral_seq_gen")
    @SequenceGenerator(name = "filtro_oferta_laboral_seq_gen", sequenceName = "empleo.filtro_oferta_laboral_filofelab_id_seq", allocationSize = 1)
    @Column(name = "filofelab_id")
    @JsonView(ResponseView.MainView.class)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @JoinColumn(name = "cod_region", referencedColumnName = "cod_region")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @JoinColumn(name = "cod_estudio", referencedColumnName = "cod_estudio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public TipoEstudio getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(TipoEstudio tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    @JoinColumn(name = "cod_cargo", referencedColumnName = "cod_cargo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    @JoinColumn(name = "cod_area", referencedColumnName = "cod_area")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public TipoArea getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(TipoArea tipoArea) {
        this.tipoArea = tipoArea;
    }

    @JoinColumn(name = "cod_moneda", referencedColumnName = "cod_moneda")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    @JoinColumn(name = "cod_carrera", referencedColumnName = "cod_carrera")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(ResponseView.MainView.class)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "ofelabusm_rango_años_exp")
    @JsonView(ResponseView.MainView.class)
    public String getRangoAniosExperiencia() {
        return rangoAniosExperiencia;
    }

    public void setRangoAniosExperiencia(String rangoAniosExperiencia) {
        this.rangoAniosExperiencia = rangoAniosExperiencia;
    }

    @Column(name = "ofelabusm_años_experiencia")
    @JsonView(ResponseView.MainView.class)
    public Short getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Short aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Column(name = "filofelab_salario_limite_inferior")
    @JsonView(ResponseView.MainView.class)
    public Integer getSalarioLimiteInferior() {
        return salarioLimiteInferior;
    }

    public void setSalarioLimiteInferior(Integer salarioLimiteInferior) {
        this.salarioLimiteInferior = salarioLimiteInferior;
    }

    @Column(name = "filofelab_salario_limite_superior")
    @JsonView(ResponseView.MainView.class)
    public Integer getSalarioLimiteSuperior() {
        return salarioLimiteSuperior;
    }

    public void setSalarioLimiteSuperior(Integer salarioLimiteSuperior) {
        this.salarioLimiteSuperior = salarioLimiteSuperior;
    }

    @Column(name = "filofelab_enviar_email")
    @JsonView(ResponseView.MainView.class)
    public Boolean getEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(Boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    @Column(name = "filofelab_dia_envio")
    @JsonView(ResponseView.MainView.class)
    public Short getDiaEnvio() {
        return diaEnvio;
    }

    public void setDiaEnvio(Short diaEnvio) {
        this.diaEnvio = diaEnvio;
    }

    @Column(name = "filofelab_momento_envio")
    @JsonView(ResponseView.MainView.class)
    public Short getMomentoEnvio() {
        return momentoEnvio;
    }

    public void setMomentoEnvio(Short momentoEnvio) {
        this.momentoEnvio = momentoEnvio;
    }

    @Column(name = "filofelab_nombre")
    @JsonView(ResponseView.MainView.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "filofelab_fecha_creacion")
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

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

}
