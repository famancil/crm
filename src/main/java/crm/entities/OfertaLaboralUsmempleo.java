package crm.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.oferta_laboral_usmempleo.
 *
 * @author  Ignacio Oneto <Ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "oferta_laboral_usmempleo", schema = "empleo")
public class OfertaLaboralUsmempleo {

    /**
     * Identificador de la instancia actual.
     */
    private Long id;

    /**
     * Pais de la oferta actual.
     */
    private Pais Pais;

    /**
     * Tipo de estudio necesario para la oferta.
     */
    private TipoEstudio estudio;

    /**
     * Area de la oferta actual.
     */
    private TipoArea area;

    /**
     * Vigencia de la oferta actual.
     */
    private TipoVigencia vigencia;

    /**
     * Tipo de publicacion de la oferta actual.
     */
    private TipoPublicacionUsmempleo publicacionUsmempleo;

    /**
     * Region de la oferta actual.
     */
    private Region region;

    /**
     * Tipo de moneda de la oferta actual.
     */
    private TipoMoneda moneda;

    /**
     * Empresa que ofrece oferta actual.
     */
    private Empresa empresa;

    /**
     * Tipo de la oferta actual.
     */
    private TipoOferta oferta;

    /**
     * Tipo de cargo de la oferta actual.
     */
    private TipoCargo cargo;

    /**
     * Comuna de la oferta actual.
     */
    private Comuna comuna;

    /**
     * Titulo de la oferta actual.
     */
    private String titulo;

    /**
     * Descripcion de la oferta actual.
     */
    private String descripcion;

    /**
     * Localidad de la oferta actual.
     */
    private String localidad;

    /**
     * Vacantes de la oferta actual.
     */
    private Short vacantes;

    /**
     * Duracion de contrato de la oferta actual.
     */
    private String duracionContrato;

    /**
     * Salario de la oferta actual.
     */
    private Integer salario;

    /**
     * Descuento de salario de la oferta actual.
     */
    private String descSalario;

    /**
     * Rango de años de experiencia de la oferta actual.
     */
    private String rangoAniosExp;

    /**
     * Años de experiencia de la oferta actual.
     */
    private Short aniosExperiencia;

    /**
     * Fecha de inicio de la oferta actual.
     */
    private Date fechaInicio;

    /**
     * Fecha de vigencia de la oferta actual.
     */
    private Date fechaVigencia;

    /**
     * Indica si se requiere movilizacion propia o no.
     */
    private Boolean movilizacionPropia;

    /**
     * Requisitos minimos de la oferta actual.
     */
    private String requisitosMinimos;

    /**
     * Profesionalizacion de la oferta actual.
     */
    private String profesionalizacion;

    /**
     * Contador de correos de la oferta actual.
     */
    private Short contadorCorreos;

    /**
     * Indica si se debe mostrar o no el sueldo de la oferta actual.
     */
    private Boolean mostrarSueldo;

    /**
     * Indica si se debe mostrar o no el nombre de la oferta actual.
     */
    private Boolean mostrarNombre;

    /**
     * Nombre de fantasia de la oferta actual.
     */
    private String nombreFantasia;

    /**
     * Indica si existe un pago por la publicacion de la oferta.
     */
    private Boolean pagoPublicacion;

    /**
     * Estado de mailing de la oferta actual.
     */
    private Integer estadoMailing;

    /**
     * Comentario del mailing de la oferta actual.
     */
    private String comentarioMailing;

    /**
     * Rut del usuario que realizo la ultima modificacion de la oferta actual.
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la oferta actual.
     */
    private Date fechaModificacion;

    /**
     * Paso de la oferta actual.
     */
    private Short paso;

    /**
     * Alias de la empresa que publico la oferta actual.
     * (Nombre que desea poner como publicador de la oferta)Fecha
     */
    private String empresaAlias;

    /**
     * Carta de presentacion de la oferta actual.
     */
    private Boolean cartaPresentacion;

    /**
     * Indica si la oferta es valida o no.
     */
    private Boolean ofertaValida;

    /**
     * Fecha de envio de los postulantes a la oferta.
     */
    private Date envioPostulantes;

    /**
     * Cantidad de postulantes de la oferta actual.
     */
    private Integer cantPostulantes;

    /**
     * Fecha de publicacion de la oferta actual.
     */
    private Date fechaPublicacion;

    /**
     * Indica si la oferta actual es parte de una expo laboral.
     */
    private Boolean esExpolaboral;

    /**
     * Indica si la oferta actual pertenece a otro portal.
     */
    private Boolean otroPortal;

    /**
     * URL de la oferta actual.
     */
    private String url;

    /**
     * Indica si la empresa que publico la oferta actual es premium o no.
     */
    private Boolean solicitoEmpPremium;

    /**
     * Lista de las {@Link Carrera} declaradas en la oferta laboral
     */
    private List<OfertaCarreraUsmempleo> ofertaCarreraUsmempleoList;

    /**
     * Lista de {@Link AdminOfertaLaboralUsmempleo} asociadas a la Oferta Laboral
     */
    private List<AdminOfertaLaboralUsmempleo> adminOfertaLaboralUsmempleoList;

    /**
     * Lista de las {@Link PostulacionOfertaLaboralUsmempleo} declaradas en la oferta laboral
     */
    private List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoList;

    /**
     * Lista de las {@Link PreguntaOfertaLaboralUsmEmpleo} declaradas en la oferta laboral
     */
    private List<PreguntaOfertaLaboralUsmEmpleo> preguntaOfertaLaboralUsmEmpleoList;

    /**
     * Lista de las {@Link ManejoIdiomaOfertaLaboral} requeridas en la oferta laboral
     */
    private List<ManejoIdiomaOfertaLaboral> manejoIdiomaOfertaLaboralList;

    /**
     * Lista de las {@Link CompetenciaOfertaLaboral} requeridas en la oferta laboral
     */
    private List<CompetenciaOfertaLaboral> competenciaOfertaLaboralList;



    public OfertaLaboralUsmempleo(){
    }

    @Id
    @Column(name = "ofelabusm_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais pais) {
        Pais = pais;
    }

    @JoinColumn(name = "cod_estudio", referencedColumnName = "cod_estudio")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoEstudio getEstudio() {
        return estudio;
    }

    public void setEstudio(TipoEstudio estudio) {
        this.estudio = estudio;
    }

    @JoinColumn(name = "cod_area", referencedColumnName = "cod_area")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoArea getArea() {
        return area;
    }

    public void setArea(TipoArea area) {
        this.area = area;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(TipoVigencia vigencia) {
        this.vigencia = vigencia;
    }

    @JoinColumn(name = "cod_publicacion", referencedColumnName = "cod_publicacion")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoPublicacionUsmempleo getPublicacionUsmempleo() {
        return publicacionUsmempleo;
    }

    public void setPublicacionUsmempleo(TipoPublicacionUsmempleo publicacionUsmempleo) {
        this.publicacionUsmempleo = publicacionUsmempleo;
    }

    @JoinColumn(name = "cod_region", referencedColumnName = "cod_region")
    @ManyToOne(fetch = FetchType.LAZY)
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @JoinColumn(name = "cod_moneda", referencedColumnName = "cod_moneda")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    @JoinColumn(name = "perempusm_id", referencedColumnName = "perempusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JoinColumn(name = "cod_oferta", referencedColumnName = "cod_oferta")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoOferta getOferta() {
        return oferta;
    }

    public void setOferta(TipoOferta oferta) {
        this.oferta = oferta;
    }

    @JoinColumn(name = "cod_cargo", referencedColumnName = "cod_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoCargo getCargo() {
        return cargo;
    }

    public void setCargo(TipoCargo cargo) {
        this.cargo = cargo;
    }

    @JoinColumn(name = "cod_comuna", referencedColumnName = "cod_comuna")
    @ManyToOne(fetch = FetchType.LAZY)
    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @Column(name = "ofelabusm_titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "ofelabusm_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "ofelabusm_localidad")
    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Column(name = "ofelabusm_vacantes")
    public Short getVacantes() {
        return vacantes;
    }

    public void setVacantes(Short vacantes) {
        this.vacantes = vacantes;
    }

    @Column(name = "ofelabusm_duracion_contrato")
    public String getDuracionContrato() {
        return duracionContrato;
    }

    public void setDuracionContrato(String duracionContrato) {
        this.duracionContrato = duracionContrato;
    }

    @Column(name = "ofelabusm_salario")
    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    @Column(name = "ofelabusm_desc_salario")
    public String getDescSalario() {
        return descSalario;
    }

    public void setDescSalario(String descSalario) {
        this.descSalario = descSalario;
    }

    @Column(name = "ofelabusm_rango_años_exp")
    public String getRangoAniosExp() {
        return rangoAniosExp;
    }

    public void setRangoAniosExp(String rangoAniosExp) {
        this.rangoAniosExp = rangoAniosExp;
    }

    @Column(name = "ofelabusm_años_experiencia")
    public Short getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Short aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Column(name = "ofelabusm_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "ofelabusm_fecha_vigencia")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    @Column(name = "ofelabusm_movilizacion_propia")
    public Boolean getMovilizacionPropia() {
        return movilizacionPropia;
    }

    public void setMovilizacionPropia(Boolean movilizacionPropia) {
        this.movilizacionPropia = movilizacionPropia;
    }

    @Column(name = "ofelabusm_requisitos_minimos")
    public String getRequisitosMinimos() {
        return requisitosMinimos;
    }

    public void setRequisitosMinimos(String requisitosMinimos) {
        this.requisitosMinimos = requisitosMinimos;
    }

    @Column(name = "ofelabusm_profesionalizacion")
    public String getProfesionalizacion() {
        return profesionalizacion;
    }

    public void setProfesionalizacion(String profesionalizacion) {
        this.profesionalizacion = profesionalizacion;
    }

    @Column(name = "ofelabusm_contador_correos")
    public Short getContadorCorreos() {
        return contadorCorreos;
    }

    public void setContadorCorreos(Short contadorCorreos) {
        this.contadorCorreos = contadorCorreos;
    }

    @Column(name = "ofelabusm_mostrar_sueldo")
    public Boolean getMostrarSueldo() {
        return mostrarSueldo;
    }

    public void setMostrarSueldo(Boolean mostrarSueldo) {
        this.mostrarSueldo = mostrarSueldo;
    }

    @Column(name = "ofelabusm_mostrar_nombre")
    public Boolean getMostrarNombre() {
        return mostrarNombre;
    }

    public void setMostrarNombre(Boolean mostrarNombre) {
        this.mostrarNombre = mostrarNombre;
    }

    @Column(name = "ofelabusm_nombre_fantasia")
    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    @Column(name = "ofelabusm_pago_publicacion")
    public Boolean getPagoPublicacion() {
        return pagoPublicacion;
    }

    public void setPagoPublicacion(Boolean pagoPublicacion) {
        this.pagoPublicacion = pagoPublicacion;
    }

    @Column(name = "ofelabusm_estado_mailing")
    public Integer getEstadoMailing() {
        return estadoMailing;
    }

    public void setEstadoMailing(Integer estadoMailing) {
        this.estadoMailing = estadoMailing;
    }

    @Column(name = "ofelabusm_comentario_mailing")
    public String getComentarioMailing() {
        return comentarioMailing;
    }

    public void setComentarioMailing(String comentarioMailing) {
        this.comentarioMailing = comentarioMailing;
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

    @Column(name = "ofelabusm_paso")
    public Short getPaso() {
        return paso;
    }

    public void setPaso(Short paso) {
        this.paso = paso;
    }

    @Column(name = "ofelabusm_empresa")
    public String getEmpresaAlias() {
        return empresaAlias;
    }

    public void setEmpresaAlias(String empresaAlias) {
        this.empresaAlias = empresaAlias;
    }

    @Column(name = "ofelabusm_carta_presentacion")
    public Boolean getCartaPresentacion() {
        return cartaPresentacion;
    }

    public void setCartaPresentacion(Boolean cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }

    @Column(name = "ofelabusm_oferta_validada")
    public Boolean getOfertaValida() {
        return ofertaValida;
    }

    public void setOfertaValida(Boolean ofertaValida) {
        this.ofertaValida = ofertaValida;
    }

    @Column(name = "ofelabusm_envio_postulantes")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEnvioPostulantes() {
        return envioPostulantes;
    }

    public void setEnvioPostulantes(Date envioPostulantes) {
        this.envioPostulantes = envioPostulantes;
    }

    @Column(name = "ofelabusm_cant_postulantes")
    public Integer getCantPostulantes() {
        return cantPostulantes;
    }

    public void setCantPostulantes(Integer cantPostulantes) {
        this.cantPostulantes = cantPostulantes;
    }

    @Column(name = "ofelabusm_fecha_publicacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Column(name = "ofelabusm_es_expolaboral")
    public Boolean getEsExpolaboral() {
        return esExpolaboral;
    }

    public void setEsExpolaboral(Boolean esExpolaboral) {
        this.esExpolaboral = esExpolaboral;
    }

    @Column(name = "ofelabusm_otro_portal")
    public Boolean getOtroPortal() {
        return otroPortal;
    }

    public void setOtroPortal(Boolean otroPortal) {
        this.otroPortal = otroPortal;
    }

    @Column(name = "ofelabusm_url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "ofelabusm_solicito_emp_premium")
    public Boolean getSolicitoEmpPremium() {
        return solicitoEmpPremium;
    }

    public void setSolicitoEmpPremium(Boolean solicitoEmpPremium) {
        this.solicitoEmpPremium = solicitoEmpPremium;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaLaboralUsmempleo")
    public List<OfertaCarreraUsmempleo> getOfertaCarreraUsmempleoList() {
        return ofertaCarreraUsmempleoList;
    }

    public void setOfertaCarreraUsmempleoList(List<OfertaCarreraUsmempleo> ofertaCarreraUsmempleoList) {
        this.ofertaCarreraUsmempleoList = ofertaCarreraUsmempleoList;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaLaboralUsmempleo")
    public List<AdminOfertaLaboralUsmempleo> getAdminOfertaLaboralUsmempleoList() {
        return adminOfertaLaboralUsmempleoList;
    }

    public void setAdminOfertaLaboralUsmempleoList(List<AdminOfertaLaboralUsmempleo> adminOfertaLaboralUsmempleoList) {
        this.adminOfertaLaboralUsmempleoList = adminOfertaLaboralUsmempleoList;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaLaboralUsmempleo")
    public List<PostulacionOfertaLaboralUsmempleo> getPostulacionOfertaLaboralUsmempleoList() {
        return postulacionOfertaLaboralUsmempleoList;
    }

    public void setPostulacionOfertaLaboralUsmempleoList(List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoList) {
        this.postulacionOfertaLaboralUsmempleoList = postulacionOfertaLaboralUsmempleoList;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaLaboralUsmempleo")
    public List<PreguntaOfertaLaboralUsmEmpleo> getPreguntaOfertaLaboralUsmEmpleoList() {
        return preguntaOfertaLaboralUsmEmpleoList;
    }

    public void setPreguntaOfertaLaboralUsmEmpleoList(List<PreguntaOfertaLaboralUsmEmpleo> preguntaOfertaLaboralUsmEmpleoList) {
        this.preguntaOfertaLaboralUsmEmpleoList = preguntaOfertaLaboralUsmEmpleoList;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaLaboralUsmempleo")
    public List<ManejoIdiomaOfertaLaboral> getManejoIdiomaOfertaLaboralList() {
        return manejoIdiomaOfertaLaboralList;
    }

    public void setManejoIdiomaOfertaLaboralList(List<ManejoIdiomaOfertaLaboral> manejoIdiomaOfertaLaboralList) {
        this.manejoIdiomaOfertaLaboralList = manejoIdiomaOfertaLaboralList;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaLaboralUsmempleo")
    public List<CompetenciaOfertaLaboral> getCompetenciaOfertaLaboralList() {
        return competenciaOfertaLaboralList;
    }

    public void setCompetenciaOfertaLaboralList(List<CompetenciaOfertaLaboral> competenciaOfertaLaboralList) {
        this.competenciaOfertaLaboralList = competenciaOfertaLaboralList;
    }
}
