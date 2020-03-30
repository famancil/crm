package crm.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, String> direccion;
	public static volatile ListAttribute<Usuario, ActividadExalumno> actividadExalumnoList;
	public static volatile SingularAttribute<Usuario, TipoEstadoCivil> estadoCivil;
	public static volatile SingularAttribute<Usuario, String> fonoParticular;
	public static volatile ListAttribute<Usuario, CapacitacionExalumno> capacitacionExalumnoList;
	public static volatile SingularAttribute<Usuario, Short> sexo;
	public static volatile SingularAttribute<Usuario, Short> completitudContacto;
	public static volatile SingularAttribute<Usuario, Date> fechaNacimiento;
	public static volatile SingularAttribute<Usuario, Short> numeroHijos;
	public static volatile ListAttribute<Usuario, AutorizacionUsuario> autorizacionesUsuario;
	public static volatile ListAttribute<Usuario, CompetenciaExalumno> competenciaExalumnoList;
	public static volatile ListAttribute<Usuario, CampanaExalumno> campanaExalumnoList;
	public static volatile ListAttribute<Usuario, CategoriaAsesoriaUsuario> categoriaAsesoriaUsuarioList;
	public static volatile ListAttribute<Usuario, EncuestaPostulacionLaboral> encuestaPostulacionLaboralList;
	public static volatile SingularAttribute<Usuario, String> nombres;
	public static volatile SingularAttribute<Usuario, Short> trayectoriaCompleta;
	public static volatile ListAttribute<Usuario, VideoCurriculoUsuario> videoCurriculoUsuarioList;
	public static volatile SingularAttribute<Usuario, String> digitoVerificador;
	public static volatile SingularAttribute<Usuario, Integer> rutUsuario;
	public static volatile SingularAttribute<Usuario, String> pasaporte;
	public static volatile SingularAttribute<Usuario, String> urlFoto;
	public static volatile ListAttribute<Usuario, FiltroOfertaLaboral> filtroOfertaLaboralList;
	public static volatile SingularAttribute<Usuario, String> nacionalidad;
	public static volatile SingularAttribute<Usuario, InfoProfesionalExalumno> informacionProfesional;
	public static volatile ListAttribute<Usuario, TestPsicologicoExalumno> testPsicologicoExalumnoList;
	public static volatile ListAttribute<Usuario, AptitudUsuario> aptitudUsuarioList;
	public static volatile ListAttribute<Usuario, RolUsuario> rolesUsuario;
	public static volatile SingularAttribute<Usuario, Asesor> asesor;
	public static volatile ListAttribute<Usuario, RespaldoUsuario> respaldoUsuarioList;
	public static volatile SingularAttribute<Usuario, String> vocativo;
	public static volatile ListAttribute<Usuario, UsuarioVistoUsmEmpleo> usuarioVistoUsmEmpleoList;
	public static volatile ListAttribute<Usuario, ArchivosAdjuntos> archivosAdjuntosList;
	public static volatile SingularAttribute<Usuario, LoginAexa> credencialesAcceso;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile ListAttribute<Usuario, AporteSocio> aporteSocioList;
	public static volatile ListAttribute<Usuario, AntecedenteEducacional> antecedenteEducacionalList;
	public static volatile ListAttribute<Usuario, RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaOfertaLaboralExalumnoList;
	public static volatile SingularAttribute<Usuario, String> emailLaboral;
	public static volatile SingularAttribute<Usuario, String> fonoOpcional;
	public static volatile SingularAttribute<Usuario, UsuarioApoderado> apoderado;
	public static volatile ListAttribute<Usuario, InvitacionVideoEntrevistaUsmEmpleo> invitacionVideoEntrevistaUsmEmpleoList;
	public static volatile SingularAttribute<Usuario, String> celular;
	public static volatile SingularAttribute<Usuario, Integer> codigoPostal;
	public static volatile SingularAttribute<Usuario, Pais> pais;
	public static volatile ListAttribute<Usuario, StickynotesAexa> stickynotesAexaUsuarioList;
	public static volatile SingularAttribute<Usuario, String> apellidoPaterno;
	public static volatile ListAttribute<Usuario, RespuestaUsuario> respuestaUsuarioList;
	public static volatile SingularAttribute<Usuario, Date> fechaRegistro;
	public static volatile ListAttribute<Usuario, CompromisoSocio> compromisoUsuarioList;
	public static volatile ListAttribute<Usuario, OperadorContabilidad> usuariosOperadosContabilidadList;
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, String> emailOpcional;
	public static volatile SingularAttribute<Usuario, Comuna> comuna;
	public static volatile ListAttribute<Usuario, ConocimientoInfoExalumno> conocimientoInfoExalumnoList;
	public static volatile ListAttribute<Usuario, RecadoContacto> recadoContactoUsuarioList;
	public static volatile ListAttribute<Usuario, PostulacionUsuarioAsesoria> postulacionUsuarioAsesoriaList;
	public static volatile SingularAttribute<Usuario, Integer> rut;
	public static volatile SingularAttribute<Usuario, String> apellidoMaterno;
	public static volatile SingularAttribute<Usuario, Date> fechaModificacion;
	public static volatile ListAttribute<Usuario, VideoEntrevistaUsmEmpleo> videoEntrevistaUsmEmpleoList;
	public static volatile ListAttribute<Usuario, RecadoContacto> recadoContactoUsuUsuarioList;
	public static volatile ListAttribute<Usuario, OfertaCrawled> ofertaCrawledList;
	public static volatile SingularAttribute<Usuario, PreferenciaPrivacidad> preferenciaPrivacidad;
	public static volatile ListAttribute<Usuario, AccesoSistema> accesosSistemas;
	public static volatile ListAttribute<Usuario, OperadorContabilidad> usuariosOperadoresContabilidadList;
	public static volatile ListAttribute<Usuario, PagoAsesoria> pagoAsesoriaList;
	public static volatile ListAttribute<Usuario, PostulacionArchivosAdjuntos> postulacionArchivosAdjuntosList;
	public static volatile ListAttribute<Usuario, StickynotesAexa> stickynotesAexaUsuUsuarioList;
	public static volatile SingularAttribute<Usuario, String> apodo;
	public static volatile ListAttribute<Usuario, PostulanteFavorito> postulanteFavoritoList;
	public static volatile ListAttribute<Usuario, DuenoEmpresa> duenoEmpresaList;
	public static volatile ListAttribute<Usuario, PaginaExalumno> paginaExalumnoList;
	public static volatile ListAttribute<Usuario, AntecedenteColegio> antecedenteColegioList;
	public static volatile ListAttribute<Usuario, ContactoHistoricoEmpresa> contactoHistoricoEmpresaList;
	public static volatile ListAttribute<Usuario, CorreosValidados> correosValidadosList;
	public static volatile SingularAttribute<Usuario, TipoVigencia> tipoVigencia;
	public static volatile ListAttribute<Usuario, PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoList;
	public static volatile ListAttribute<Usuario, ManejoIdioma> manejoIdiomasList;
	public static volatile SingularAttribute<Usuario, PreferenciaUsuarioUsmempleo> preferenciaUsuarioUsmempleo;

}

