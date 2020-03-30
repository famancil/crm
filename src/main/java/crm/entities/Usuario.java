package crm.entities;

import crm.utils.EncodingUtil;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.usuario_aexa. Contiene toda la informacion relevante de un usuario perteneciente
 * a la red de exalumnos ya sea dado que es un alumno o exalumno de la UTFSM independiente de su calidad de socio con
 * la asociacion.
 *
 * @author dacuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "usuario_aexa")
public class Usuario implements UserDetails {

    private Long id;
    private Integer rut;
    private String digitoVerificador;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private String direccion;
    private Comuna comuna;
    private Pais pais;
    private String nacionalidad;
    private String fonoParticular;
    private String fonoOpcional;
    private String celular;
    private String email;
    private String emailOpcional;
    private String emailLaboral;
    private Short sexo;
    private Date fechaRegistro;
    private String urlFoto;
    private String apodo;
    private String pasaporte;
    private Integer codigoPostal;
    private TipoEstadoCivil estadoCivil;
    private Short numeroHijos;
    private String vocativo;
    private Short completitudContacto;
    private Short trayectoriaCompleta;
    private Date fechaModificacion;
    private Integer rutUsuario;
    private LoginAexa credencialesAcceso;
    private UsuarioApoderado apoderado;
    private TipoVigencia tipoVigencia;
    private Boolean estado;

    /*
    TODO BORRAR  (en conjunto con los getter y setter de abajo)
    private Usuario operadorEncargado;
    private ComoSupoDeRedExalumnos comoSupoDeRedExalumnos;
    private EstadoSolicitudCredencial estadoSolicitudCredencial;
    private TipoUsuario tipoUsuario;
    */
    private PreferenciaPrivacidad preferenciaPrivacidad;
    private PreferenciaUsuarioUsmempleo preferenciaUsuarioUsmempleo;
    private InfoProfesionalExalumno informacionProfesional;
    private Asesor asesor;

    private List<RolUsuario> rolesUsuario;
    private List<AccesoSistema> accesosSistemas;
    private List<AutorizacionUsuario> autorizacionesUsuario;
    private List<CompromisoSocio> compromisoUsuarioList;
    private List<AntecedenteEducacional> antecedenteEducacionalList;
    private List<ManejoIdioma> manejoIdiomasList;
    private List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoList;
    private List<ArchivosAdjuntos> archivosAdjuntosList;
    private List<RecadoContacto> recadoContactoUsuarioList;
    private List<RecadoContacto> recadoContactoUsuUsuarioList;
    private List<CampanaExalumno> campanaExalumnoList;
    private List<StickynotesAexa> stickynotesAexaUsuarioList;
    private List<StickynotesAexa> stickynotesAexaUsuUsuarioList;
    private List<OperadorContabilidad> usuariosOperadosContabilidadList;
    private List<OperadorContabilidad> usuariosOperadoresContabilidadList;
    private List<PaginaExalumno> paginaExalumnoList;
    private List<CompetenciaExalumno> competenciaExalumnoList;
    private List<ConocimientoInfoExalumno> conocimientoInfoExalumnoList;
    private List<AntecedenteColegio> antecedenteColegioList;
    private List<DuenoEmpresa> duenoEmpresaList;
    private List<AporteSocio> aporteSocioList;
    private List<UsuarioVistoUsmEmpleo> usuarioVistoUsmEmpleoList;
    private List<CapacitacionExalumno> capacitacionExalumnoList;
    private List<ActividadExalumno> actividadExalumnoList;
    private List<ContactoHistoricoEmpresa> contactoHistoricoEmpresaList;
    private List<InvitacionVideoEntrevistaUsmEmpleo> invitacionVideoEntrevistaUsmEmpleoList;
    private List<VideoEntrevistaUsmEmpleo> videoEntrevistaUsmEmpleoList;
    private List<VideoCurriculoUsuario> videoCurriculoUsuarioList;
    private List<AptitudUsuario> aptitudUsuarioList;
    private List<FiltroOfertaLaboral> filtroOfertaLaboralList;
    private List<PagoAsesoria> pagoAsesoriaList;
    private List<PostulanteFavorito> postulanteFavoritoList;
    private List<PostulacionUsuarioAsesoria> postulacionUsuarioAsesoriaList;
    private List<EncuestaPostulacionLaboral> encuestaPostulacionLaboralList;
    private List<TestPsicologicoExalumno> testPsicologicoExalumnoList;
    private List<CategoriaAsesoriaUsuario> categoriaAsesoriaUsuarioList;
    private List<RespuestaUsuario> respuestaUsuarioList;
    private List<RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaOfertaLaboralExalumnoList;
    private List<OfertaCrawled> ofertaCrawledList;
    private List<CorreosValidados> correosValidadosList;
    private List<RespaldoUsuario> respaldoUsuarioList;
    private List<PostulacionArchivosAdjuntos> postulacionArchivosAdjuntosList;

    public Usuario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "usuario_aexa_usuaex_id_seq", allocationSize = 1)
    @Column(name = "usuaex_id")
    public Long getId() {
        return id;
    }

    public void setId(Long usuaexId) {
        this.id = usuaexId;
    }

    @Column(name = "usuaex_rut")
    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer usuaexRut) {
        this.rut = usuaexRut;
    }

    @Column(name = "usuaex_digito")
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String usuaexDigito) {
        this.digitoVerificador = usuaexDigito;
    }

    @Column(name = "usuaex_nombres")
    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String usuaexNombres) {
        this.nombres = usuaexNombres;
    }

    @Column(name = "usuaex_ap_paterno")
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String usuaexApPaterno) {
        this.apellidoPaterno = usuaexApPaterno;
    }

    @Column(name = "usuaex_ap_materno")
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String usuaexApMaterno) {
        this.apellidoMaterno = usuaexApMaterno;
    }

    @Column(name = "usuaex_fec_nac")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date usuaexFecNac) {
        this.fechaNacimiento = usuaexFecNac;
    }

    @Column(name = "usuaex_direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String usuaexDireccion) {
        this.direccion = usuaexDireccion;
    }

    @JoinColumn(name = "cod_comuna", referencedColumnName = "cod_comuna")
    @ManyToOne(fetch = FetchType.LAZY)
    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Column(name = "usuaex_nacionalidad")
    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String usuaexNacionalidad) {
        this.nacionalidad = usuaexNacionalidad;
    }

    @Column(name = "usuaex_fono_particular")
    public String getFonoParticular() {
        return fonoParticular;
    }

    public void setFonoParticular(String usuaexFonoParticular) {
        this.fonoParticular = usuaexFonoParticular;
    }

    @Column(name = "usuaex_fono_opcional")
    public String getFonoOpcional() {
        return fonoOpcional;
    }

    public void setFonoOpcional(String usuaexFonoOpcional) {
        this.fonoOpcional = usuaexFonoOpcional;
    }

    @Column(name = "usuaex_celular")
    public String getCelular() {
        return celular;
    }

    public void setCelular(String usuaexCelular) {
        this.celular = usuaexCelular;
    }

    @Column(name = "usuaex_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String usuaexEmail) {
        this.email = usuaexEmail;
    }

    @Column(name = "usuaex_email_opcional")
    public String getEmailOpcional() {
        return emailOpcional;
    }

    public void setEmailOpcional(String usuaexEmailOpcional) {
        this.emailOpcional = usuaexEmailOpcional;
    }

    @Column(name = "usuaex_email_laboral")
    public String getEmailLaboral() {
        return emailLaboral;
    }

    public void setEmailLaboral(String usuaexEmailLaboral) {
        this.emailLaboral = usuaexEmailLaboral;
    }

    @Column(name = "usuaex_sexo")
    public Short getSexo() {
        return sexo;
    }

    public void setSexo(Short usuaexSexo) {
        this.sexo = usuaexSexo;
    }

    @Column(name = "usuaex_fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date usuaexFechaRegistro) {
        this.fechaRegistro = usuaexFechaRegistro;
    }

    @Column(name = "usuaex_url_foto")
    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String usuaexUrlFoto) {
        this.urlFoto = usuaexUrlFoto;
    }

    @Column(name = "usuaex_apodo")
    public String getApodo() {
        return apodo;
    }

    public void setApodo(String usuaexApodo) {
        this.apodo = usuaexApodo;
    }

    @Column(name = "usuaex_pasaporte")
    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String usuaexPasaporte) {
        this.pasaporte = usuaexPasaporte;
    }

    @Column(name = "usuaex_cod_postal")
    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer usuaexCodPostal) {
        this.codigoPostal = usuaexCodPostal;
    }

    @JoinColumn(name = "cod_estado_civil", referencedColumnName = "cod_estado_civil")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoEstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(TipoEstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    @Column(name = "usuaex_num_hijos")
    public Short getNumeroHijos() {
        return numeroHijos;
    }

    public void setNumeroHijos(Short usuaexNumHijos) {
        this.numeroHijos = usuaexNumHijos;
    }

    @Column(name = "usuaex_vocativo")
    public String getVocativo() {
        return vocativo;
    }

    public void setVocativo(String usuaexVocativo) {
        this.vocativo = usuaexVocativo;
    }

    @Column(name = "usuaex_completitud_contacto")
    public Short getCompletitudContacto() {
        return completitudContacto;
    }

    public void setCompletitudContacto(Short usuaexCompletitudContacto) {
        this.completitudContacto = usuaexCompletitudContacto;
    }

    @Column(name = "usuaex_trayectoria_completa")
    public Short getTrayectoriaCompleta() {
        return trayectoriaCompleta;
    }

    public void setTrayectoriaCompleta(Short usuaexTrayectoriaCompleta) {
        this.trayectoriaCompleta = usuaexTrayectoriaCompleta;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @Column(name = "usuaex_estado")
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public List<RolUsuario> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(List<RolUsuario> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public List<AutorizacionUsuario> getAutorizacionesUsuario() {
        return autorizacionesUsuario;
    }

    public void setAutorizacionesUsuario(List<AutorizacionUsuario> autorizacionesUsuario) {
        this.autorizacionesUsuario = autorizacionesUsuario;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL)
    public LoginAexa getCredencialesAcceso() {
        return credencialesAcceso;
    }

    public void setCredencialesAcceso(LoginAexa loginAexa) {
        this.credencialesAcceso = loginAexa;
    }

    /*
    @JoinColumn(name = "cod_credencial", referencedColumnName = "cod_credencial")
    @ManyToOne(fetch = FetchType.LAZY)
    public EstadoSolicitudCredencial getEstadoSolicitudCredencial() {
        return estadoSolicitudCredencial;
    }

    public void setEstadoSolicitudCredencial(EstadoSolicitudCredencial tipoEstadoCredencial) {
        this.estadoSolicitudCredencial = tipoEstadoCredencial;
    }

    @JoinColumn(name = "cod_usuario_aexa", referencedColumnName = "cod_usuario_aexa")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @JoinColumn(name = "usuaex_operador", referencedColumnName = "usuaex_id")
    @OneToOne(fetch = FetchType.LAZY)
    public Usuario getOperadorEncargado() {
        return operadorEncargado;
    }

    public void setOperadorEncargado(Usuario operadorEncargado) {
        this.operadorEncargado = operadorEncargado;
    }
    */

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.DETACH)
    public UsuarioApoderado getApoderado() {
        return apoderado;
    }

    public void setApoderado(UsuarioApoderado apoderado) {
        this.apoderado = apoderado;
    }

    /*
    @JoinColumn(name = "cod_como_supo_aexa", referencedColumnName = "cod_como_supo_aexa")
    @ManyToOne(fetch = FetchType.LAZY)
    public ComoSupoDeRedExalumnos getComoSupoDeRedExalumnos() {
        return comoSupoDeRedExalumnos;
    }

    public void setComoSupoDeRedExalumnos(ComoSupoDeRedExalumnos comoSupoDeRedExalumnos) {
        this.comoSupoDeRedExalumnos = comoSupoDeRedExalumnos;
    }
    */

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<AccesoSistema> getAccesosSistemas() {
        return accesosSistemas;
    }

    public void setAccesosSistemas(List<AccesoSistema> accesosSistemas) {
        this.accesosSistemas = accesosSistemas;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<CompromisoSocio> getCompromisoUsuarioList()  {
        return compromisoUsuarioList;
    }

    public void setCompromisoUsuarioList(List<CompromisoSocio> compromisoUsuarioList) {
        this.compromisoUsuarioList = compromisoUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<AntecedenteEducacional> getAntecedenteEducacionalList()  {
        return antecedenteEducacionalList;
    }

    public void setAntecedenteEducacionalList(List<AntecedenteEducacional> antecedenteEducacionalList) {
        this.antecedenteEducacionalList = antecedenteEducacionalList;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
    public InfoProfesionalExalumno getInformacionProfesional() {
        return informacionProfesional;
    }

    public void setInformacionProfesional(InfoProfesionalExalumno informacionProf) {
        this.informacionProfesional = informacionProf;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.DETACH)
    public Asesor getAsesor() {
        return asesor;
    }

    public void setAsesor(Asesor asesor) {
        this.asesor = asesor;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.DETACH)
    public PreferenciaPrivacidad getPreferenciaPrivacidad() {
        return preferenciaPrivacidad;
    }

    public void setPreferenciaPrivacidad(PreferenciaPrivacidad preferenciaPrivacidad) {
        this.preferenciaPrivacidad = preferenciaPrivacidad;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
    public PreferenciaUsuarioUsmempleo getPreferenciaUsuarioUsmempleo() {
        return preferenciaUsuarioUsmempleo;
    }

    public void setPreferenciaUsuarioUsmempleo(PreferenciaUsuarioUsmempleo preferenciaUsuarioUsmempleo) {
        this.preferenciaUsuarioUsmempleo = preferenciaUsuarioUsmempleo;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<ManejoIdioma> getManejoIdiomasList() {
        return manejoIdiomasList;
    }

    public void setManejoIdiomasList(List<ManejoIdioma> manejoIdiomasList) {
        this.manejoIdiomasList = manejoIdiomasList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<PostulacionOfertaLaboralUsmempleo> getPostulacionOfertaLaboralUsmempleoList() {
        return postulacionOfertaLaboralUsmempleoList;
    }

    public void setPostulacionOfertaLaboralUsmempleoList(List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoList) {
        this.postulacionOfertaLaboralUsmempleoList = postulacionOfertaLaboralUsmempleoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<ArchivosAdjuntos> getArchivosAdjuntosList() {
        return archivosAdjuntosList;
    }

    public void setArchivosAdjuntosList(List<ArchivosAdjuntos> archivosAdjuntosList) {
        this.archivosAdjuntosList = archivosAdjuntosList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<RecadoContacto> getRecadoContactoUsuarioList() {
        return recadoContactoUsuarioList;
    }

    public void setRecadoContactoUsuarioList(List<RecadoContacto> recadoContactoUsuarioList) {
        this.recadoContactoUsuarioList = recadoContactoUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuUsuario")
    public List<RecadoContacto> getRecadoContactoUsuUsuarioList() {
        return recadoContactoUsuUsuarioList;
    }

    public void setRecadoContactoUsuUsuarioList(List<RecadoContacto> recadoContactoUsuUsuarioList) {
        this.recadoContactoUsuUsuarioList = recadoContactoUsuUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<CampanaExalumno> getCampanaExalumnoList() {
        return campanaExalumnoList;
    }

    public void setCampanaExalumnoList(List<CampanaExalumno> campanaExalumnoList) {
        this.campanaExalumnoList = campanaExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<StickynotesAexa> getStickynotesAexaUsuarioList() {
        return stickynotesAexaUsuarioList;
    }

    public void setStickynotesAexaUsuarioList(List<StickynotesAexa> stickynotesAexaUsuarioList) {
        this.stickynotesAexaUsuarioList = stickynotesAexaUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuUsuario")
    public List<StickynotesAexa> getStickynotesAexaUsuUsuarioList() {
        return stickynotesAexaUsuUsuarioList;
    }

    public void setStickynotesAexaUsuUsuarioList(List<StickynotesAexa> stickynotesAexaUsuUsuarioList) {
        this.stickynotesAexaUsuUsuarioList = stickynotesAexaUsuUsuarioList;
    }


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuarioOperador")
    public List<OperadorContabilidad> getUsuariosOperadosContabilidadList() {
        return usuariosOperadosContabilidadList;
    }

    public void setUsuariosOperadosContabilidadList(List<OperadorContabilidad> usuariosOperadosContabilidadList) {
        this.usuariosOperadosContabilidadList = usuariosOperadosContabilidadList;
    }


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<OperadorContabilidad> getUsuariosOperadoresContabilidadList() {
        return usuariosOperadoresContabilidadList;
    }

    public void setUsuariosOperadoresContabilidadList(List<OperadorContabilidad> usuariosOperadoresContabilidadList) {
        this.usuariosOperadoresContabilidadList = usuariosOperadoresContabilidadList;
    }


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<PaginaExalumno> getPaginaExalumnoList() {
        return paginaExalumnoList;
    }

    public void setPaginaExalumnoList(List<PaginaExalumno> paginaExalumnoList) {
        this.paginaExalumnoList = paginaExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<CompetenciaExalumno> getCompetenciaExalumnoList() {
        return competenciaExalumnoList;
    }

    public void setCompetenciaExalumnoList(List<CompetenciaExalumno> competenciaExalumnoList) {
        this.competenciaExalumnoList = competenciaExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<ConocimientoInfoExalumno> getConocimientoInfoExalumnoList() {
        return conocimientoInfoExalumnoList;
    }

    public void setConocimientoInfoExalumnoList(List<ConocimientoInfoExalumno> conocimientoInfoExalumnoList) {
        this.conocimientoInfoExalumnoList = conocimientoInfoExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<AntecedenteColegio> getAntecedenteColegioList() {
        return antecedenteColegioList;
    }

    public void setAntecedenteColegioList(List<AntecedenteColegio> antecedenteColegioList) {
        this.antecedenteColegioList = antecedenteColegioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<DuenoEmpresa> getDuenoEmpresaList() {
        return duenoEmpresaList;
    }

    public void setDuenoEmpresaList(List<DuenoEmpresa> duenoEmpresaList) {
        this.duenoEmpresaList = duenoEmpresaList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<AporteSocio> getAporteSocioList() {
        return aporteSocioList;
    }

    public void setAporteSocioList(List<AporteSocio> aporteSocioList) {
        this.aporteSocioList = aporteSocioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<UsuarioVistoUsmEmpleo> getUsuarioVistoUsmEmpleoList() {
        return usuarioVistoUsmEmpleoList;
    }

    public void setUsuarioVistoUsmEmpleoList(List<UsuarioVistoUsmEmpleo> usuarioVistoUsmEmpleoList) {
        this.usuarioVistoUsmEmpleoList = usuarioVistoUsmEmpleoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<CapacitacionExalumno> getCapacitacionExalumnoList() {
        return capacitacionExalumnoList;
    }

    public void setCapacitacionExalumnoList(List<CapacitacionExalumno> capacitacionExalumnoList) {
        this.capacitacionExalumnoList = capacitacionExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<ActividadExalumno> getActividadExalumnoList() {
        return actividadExalumnoList;
    }

    public void setActividadExalumnoList(List<ActividadExalumno> actividadExalumnoList) {
        this.actividadExalumnoList = actividadExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<ContactoHistoricoEmpresa> getContactoHistoricoEmpresaList() {
        return contactoHistoricoEmpresaList;
    }

    public void setContactoHistoricoEmpresaList(List<ContactoHistoricoEmpresa> contactoHistoricoEmpresaList) {
        this.contactoHistoricoEmpresaList = contactoHistoricoEmpresaList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<InvitacionVideoEntrevistaUsmEmpleo> getInvitacionVideoEntrevistaUsmEmpleoList() {
        return invitacionVideoEntrevistaUsmEmpleoList;
    }

    public void setInvitacionVideoEntrevistaUsmEmpleoList(List<InvitacionVideoEntrevistaUsmEmpleo> invitacionVideoEntrevistaUsmEmpleoList) {
        this.invitacionVideoEntrevistaUsmEmpleoList = invitacionVideoEntrevistaUsmEmpleoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<VideoEntrevistaUsmEmpleo> getVideoEntrevistaUsmEmpleoList() {
        return videoEntrevistaUsmEmpleoList;
    }

    public void setVideoEntrevistaUsmEmpleoList(List<VideoEntrevistaUsmEmpleo> videoEntrevistaUsmEmpleoList) {
        this.videoEntrevistaUsmEmpleoList = videoEntrevistaUsmEmpleoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<VideoCurriculoUsuario> getVideoCurriculoUsuarioList() {
        return videoCurriculoUsuarioList;
    }

    public void setVideoCurriculoUsuarioList(List<VideoCurriculoUsuario> videoCurriculoUsuarioList) {
        this.videoCurriculoUsuarioList = videoCurriculoUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<AptitudUsuario> getAptitudUsuarioList() {
        return aptitudUsuarioList;
    }

    public void setAptitudUsuarioList(List<AptitudUsuario> aptitudUsuarioList) {
        this.aptitudUsuarioList = aptitudUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<FiltroOfertaLaboral> getFiltroOfertaLaboralList() {
        return filtroOfertaLaboralList;
    }

    public void setFiltroOfertaLaboralList(List<FiltroOfertaLaboral> filtroOfertaLaboralList) {
        this.filtroOfertaLaboralList = filtroOfertaLaboralList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<PagoAsesoria> getPagoAsesoriaList() {
        return pagoAsesoriaList;
    }

    public void setPagoAsesoriaList(List<PagoAsesoria> pagoAsesoriaList) {
        this.pagoAsesoriaList = pagoAsesoriaList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<PostulanteFavorito> getPostulanteFavoritoList() {
        return postulanteFavoritoList;
    }

    public void setPostulanteFavoritoList(List<PostulanteFavorito> postulanteFavoritoList) {
        this.postulanteFavoritoList = postulanteFavoritoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<PostulacionUsuarioAsesoria> getPostulacionUsuarioAsesoriaList() {
        return postulacionUsuarioAsesoriaList;
    }

    public void setPostulacionUsuarioAsesoriaList(List<PostulacionUsuarioAsesoria> postulacionUsuarioAsesoriaList) {
        this.postulacionUsuarioAsesoriaList = postulacionUsuarioAsesoriaList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<EncuestaPostulacionLaboral> getEncuestaPostulacionLaboralList() {
        return encuestaPostulacionLaboralList;
    }

    public void setEncuestaPostulacionLaboralList(List<EncuestaPostulacionLaboral> encuestaPostulacionLaboralList) {
        this.encuestaPostulacionLaboralList = encuestaPostulacionLaboralList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<TestPsicologicoExalumno> getTestPsicologicoExalumnoList() {
        return testPsicologicoExalumnoList;
    }

    public void setTestPsicologicoExalumnoList(List<TestPsicologicoExalumno> testPsicologicoExalumnoList) {
        this.testPsicologicoExalumnoList = testPsicologicoExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<CategoriaAsesoriaUsuario> getCategoriaAsesoriaUsuarioList() {
        return categoriaAsesoriaUsuarioList;
    }

    public void setCategoriaAsesoriaUsuarioList(List<CategoriaAsesoriaUsuario> categoriaAsesoriaUsuarioList) {
        this.categoriaAsesoriaUsuarioList = categoriaAsesoriaUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<RespuestaUsuario> getRespuestaUsuarioList() {
        return respuestaUsuarioList;
    }

    public void setRespuestaUsuarioList(List<RespuestaUsuario> respuestaUsuarioList) {
        this.respuestaUsuarioList = respuestaUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<RespuestaPreguntaOfertaLaboralExalumno> getRespuestaPreguntaOfertaLaboralExalumnoList() {
        return respuestaPreguntaOfertaLaboralExalumnoList;
    }

    public void setRespuestaPreguntaOfertaLaboralExalumnoList(List<RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaOfertaLaboralExalumnoList) {
        this.respuestaPreguntaOfertaLaboralExalumnoList = respuestaPreguntaOfertaLaboralExalumnoList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<OfertaCrawled> getOfertaCrawledList() {
        return ofertaCrawledList;
    }

    public void setOfertaCrawledList(List<OfertaCrawled> ofertaCrawledList) {
        this.ofertaCrawledList = ofertaCrawledList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idUsuario")
    public List<CorreosValidados> getCorreosValidadosList() {
        return correosValidadosList;
    }

    public void setCorreosValidadosList(List<CorreosValidados> correosValidadosList) {
        this.correosValidadosList = correosValidadosList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<RespaldoUsuario> getRespaldoUsuarioList() {
        return respaldoUsuarioList;
    }

    public void setRespaldoUsuarioList(List<RespaldoUsuario> respaldoUsuarioList) {
        this.respaldoUsuarioList = respaldoUsuarioList;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    public List<PostulacionArchivosAdjuntos> getPostulacionArchivosAdjuntosList() {
        return postulacionArchivosAdjuntosList;
    }

    public void setPostulacionArchivosAdjuntosList(List<PostulacionArchivosAdjuntos> postulacionArchivosAdjuntosList) {
        this.postulacionArchivosAdjuntosList = postulacionArchivosAdjuntosList;
    }

    @Transient
    public String getNombre() {
        String primerNombre = nombres.split(" ")[0];
        return WordUtils.capitalize(primerNombre.toLowerCase() + " " + apellidoPaterno.toLowerCase());
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<RolUsuario> rolesAsignados = this.getRolesUsuario();
        for (RolUsuario rol : rolesAsignados) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rol.getRolAcceso().getNombre());
            authorities.add(authority);
        }
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Transient
    @Override
    public String getPassword() {
        return credencialesAcceso.getPassword();
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Transient
    @Override
    public String getUsername() {
        return credencialesAcceso.getUsername();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
     * (ie expired)
     */
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
     * authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
     * valid (ie expired)
     */
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (id != null ? !id.equals(usuario.id) : usuario.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Calcula la edad actual de la persona
     * @return Edad de la persona
     */
    @Transient
    public Integer getEdad(){
        if(this.fechaNacimiento == null)
            return null;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String hoy = formato.format(new Date());
        String nac = formato.format(this.fechaNacimiento);
        String[] fechaNacimiento = nac.split("/");
        String[] fechaActual = hoy.split("/");
        int anos = Integer.parseInt(fechaActual[2]) - Integer.parseInt(fechaNacimiento[2]);
        int mes = Integer.parseInt(fechaActual[1]) - Integer.parseInt(fechaNacimiento[1]);
        if (mes < 0)
            anos = anos - 1;
        else if (mes == 0) {
            int dia = Integer.parseInt(fechaActual[0]) - Integer.parseInt(fechaNacimiento[0]);
            if (dia > 0)
                anos = anos - 1;
        }
        return anos;
    }

    /**
     * Obtiene el nombre completo de la persona, con las letras iniciales en mayuscula
     * y el resto en minuscula
     * @return Nombre completo con formato de titulo
     */
    @Transient
    public String getNombreCompleto(){
        String nombreCompleto = new String();

        if (this.nombres != null) {
            nombreCompleto = nombreCompleto + this.nombres + " ";
        }
        else {
            nombreCompleto = nombreCompleto + "SinInformacion ";
        }

        if (this.apellidoPaterno != null) {
            nombreCompleto = nombreCompleto + this.apellidoPaterno + " ";
        }
        else {
            nombreCompleto = nombreCompleto + "SinInformacion ";
        }

        if (this.apellidoMaterno != null) {
            nombreCompleto = nombreCompleto + this.apellidoMaterno;
        }
        else {
            nombreCompleto = nombreCompleto + "SinInformacion ";
        }

        String[] names = nombreCompleto.split(" ");
        StringBuilder b = new StringBuilder();
        for (String name : names) {
            if (name == null || name.isEmpty()) {
                b.append(" ");
                continue;
            }
            b.append(name.substring(0, 1).toUpperCase())
                    .append(name.substring(1).toLowerCase())
                    .append(" ");
        }
        return b.toString();
    }

    /**
     * Obtiene el nombre completo de la persona, con las letras iniciales en mayuscula
     * y el resto en minuscula codificado para ser utilizado en URIs
     * @return Nombre completo con formato de titulo
     */
    @Transient
    public String getNombreCompletoCodificado(){
        String nombreCompleto = new String();

        if (this.nombres != null) {
            nombreCompleto = nombreCompleto + this.nombres + " ";
        }
        else {
            nombreCompleto = nombreCompleto + "SinInformacion ";
        }

        if (this.apellidoPaterno != null) {
            nombreCompleto = nombreCompleto + this.apellidoPaterno + " ";
        }
        else {
            nombreCompleto = nombreCompleto + "SinInformacion ";
        }

        if (this.apellidoMaterno != null) {
            nombreCompleto = nombreCompleto + this.apellidoMaterno;
        }
        else {
            nombreCompleto = nombreCompleto + "SinInformacion ";
        }

        String[] names = nombreCompleto.split(" ");
        StringBuilder b = new StringBuilder();
        for (String name : names) {
            if (name == null || name.isEmpty()) {
                b.append(" ");
                continue;
            }
            b.append(name.substring(0, 1).toUpperCase())
                    .append(name.substring(1).toUpperCase())
                    .append(" ");
        }
        return EncodingUtil.encodeURIComponent(b.toString());
    }




    /**
     * Obtiene la cantidad de compromisos que posean tipo de vigencia "vigente" y "no vigente",
     * asumidos por el usuario
     *
     * @return cantidad de compromisos asumidos por el usuario
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transient
    public Integer cantidadCompromisos(){
        Integer cantidadCompromisos = 0;

        for(CompromisoSocio c : this.getCompromisoUsuarioList())
            if(c.getTipoVigencia().getCodVigencia()!=3)
                cantidadCompromisos++;

        return cantidadCompromisos;
        /*
        try{

        }catch(Exception e){
            return cantidadCompromisos;
        }
        */
    }




    /**
     * Obtiene el nunmero celular del usuario con formato adecuado de pais, dependiendo del si lo posee o no
     *
     * @return cantidad de compromisos asumidos por el usuario
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transient
    public String getCelularFormateado(){
        if (celular.length() <= 8)
            return celular;
        else{
            String numeroFormateado = celular.substring(0, celular.length() - 8) + " " + celular.substring(celular.length() - 8);
            return numeroFormateado;
        }
    }

    @Transient
    public RolAcceso getRolPrincipal() {
        if (rolesUsuario.size() > 0)
            return rolesUsuario.get(0).getRolAcceso();
        return null;
    }

    @Override
    public String toString() {
        return "Usuario";
    }
}
