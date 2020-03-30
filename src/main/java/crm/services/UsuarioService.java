package crm.services;

import crm.entities.*;
import crm.repositories.*;
import org.apache.poi.hssf.usermodel.DummyGraphics2d;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.Usuario} y con otras
 * entidades relacionadas.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class UsuarioService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AccesoSistema}.
     */
    @Autowired
    private AccesoSistemaRepository accesoSistemaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoCivil}
     */
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoUsuario}.
     */
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoMoneda}.
     */
    @Autowired
    private TipoMonedaRepository tipoMonedaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoDominioCompu}.
     */
    @Autowired
    private TipoDominioCompuRepository tipoDominioCompuRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoDisponibilidad}.
     */
    @Autowired
    private TipoDisponibilidadRepository tipoDisponibilidadRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSituacionLaboral}.
     */
    @Autowired
    private TipoSituacionLaboralRepository tipoSituacionLaboralRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ComoSupoDeRedExalumnos}
     */
    @Autowired
    private ComoSupoDeRedExalumnoRepository comoSupoDeRedExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PreferenciaUsuarioUsmempleo}
     */
    @Autowired
    private PreferenciaUsuarioUsmempleoRepository preferenciaUsuarioUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteEducacional}
     */
    @Autowired
    private AntecedenteEducacionalRepository antecedenteEducacionalRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.FormatoCvUsmempleo}
     */
    @Autowired
    FormatoCvUsmempleoRepository formatoCvUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Empresa}
     */
    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Carrera}
     */
    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Institucion}
     */
    @Autowired
    private InstitucionRepository institucionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteColegio}
     */
    @Autowired
    private AntecedenteColegioRepository antecedenteColegioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ActividadExalumno}
     */
    @Autowired
    private ActividadExalumnoRepository actividadExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompromisoEmpresa}
     */
    @Autowired
    private CompromisoEmpresaRepository compromisoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AporteEmpresa}
     */
    @Autowired
    private AporteEmpresaRepository aporteEmpresaRepository;

    /*
    TODO BORRAR
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EstadoSolicitudCredencial}
    @Autowired
    private EstadoSolicitudCredencialRepository estadoSolicitudCredencialRepository;
 */

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PreferenciaPrivacidad}
     */
    @Autowired
    private PreferenciaPrivacidadRepository preferenciaPrivacidadRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.InfoProfesionalExalumno}
     */
    @Autowired
    private InfoProfesionalExalumnoRepository infoProfesionalExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompromisoSocio}
     */
    @Autowired
    private CompromisoSocioRepository compromisoSocioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ContactoHistoricoEmpresa}
     */
    @Autowired
    private ContactoHistoricoEmpresaRepository contactoHistoricoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     */
    @Autowired
    private ContactoHistoricoEmpresaPersonaParticipanteRepository contactoHistoricoEmpresaPersonaParticipanteRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@Link crm.entities.Provincia}
     */
    @Autowired
    private ProvinciaRepository provinciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@Link crm.entities.Region}
     */
    @Autowired
    private RegionRepository regionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.LoginAexa}
     */
    @Autowired
    private LoginAexaRepository loginAexaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioApoderado}
     */
    @Autowired
    private UsuarioApoderadoRepository usuarioApoderadoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Asesor}
     */
    @Autowired
    private AsesorRepository asesorRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ManejoIdioma}
     */
    @Autowired
    private ManejoIdiomasRepository manejoIdiomasRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ArchivosAdjuntos}
     */
    @Autowired
    private ArchivosAdjuntosRepository archivosAdjuntosRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CampanaExalumno}
     */
    @Autowired
    private CampanaExalumnoRepository campanaExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PaginaExalumno}
     */
    @Autowired
    private PaginaExalumnoRepository paginaExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AutorizacionUsuario}
     */
    @Autowired
    private AutorizacionUsuarioRepository autorizacionUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AutorizacionUsuarioPermisoAcceso}
     */
    @Autowired
    private AutorizacionUsuarioPermisoAccesoRepository autorizacionUsuarioPermisoAccesoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ConocimientoInfoExalumno}
     */
    @Autowired
    private ConocimientoInfoExalumnoRepository conocimientoInfoExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CapacitacionExalumno}
     */
    @Autowired
    private CapacitacionExalumnoRepository capacitacionExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo}
     */
    @Autowired
    private InvitacionVideoEntrevistaUsmEmpleoRepository invitacionVideoEntrevistaUsmEmpleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.VideoEntrevistaUsmEmpleo}
     */
    @Autowired
    private VideoEntrevistaUsmEmpleoRepository videoEntrevistaUsmEmpleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.VideoCurriculoUsuario}
     */
    @Autowired
    private VideoCurriculoUsuarioRepository videoCurriculoUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AptitudUsuario}
     */
    @Autowired
    private AptitudUsuarioRepository aptitudUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.FiltroOfertaLaboral}
     */
    @Autowired
    private FiltroOfertaLaboralRepository filtroOfertaLaboralRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PagoAsesoria}
     */
    @Autowired
    private PagoAsesoriaRepository pagoAsesoriaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CategoriaAsesoriaUsuario}
     */
    @Autowired
    private CategoriaAsesoriaUsuarioRepository categoriaAsesoriaUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TestPsicologicoExalumno}
     */
    @Autowired
    private TestPsicologicoExalumnoRepository testPsicologicoExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OfertaCrawled}
     */
    @Autowired
    private OfertaCrawledRepository ofertaCrawledRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.StickynotesAexa}
     */
    @Autowired
    private StickynotesAexaRepository stickynotesAexaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     */
    @Autowired
    private PostulacionOfertaLaboralUsmempleoRepository postulacionOfertaLaboralUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompetenciaExalumno}
     */
    @Autowired
    private CompetenciaExalumnoRepository competenciaExalumnoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AporteSocio}
     */
    @Autowired
    private AporteSocioRepository aporteSocioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioVistoUsmEmpleo}
     */
    @Autowired
    private UsuarioVistoUsmempleoRepository usuarioVistoUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.DuenoEmpresa}
     */
    @Autowired
    private DuenoEmpresaRepository duenoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulanteFavorito}
     */
    @Autowired
    private PostulanteFavoritoRepository postulanteFavoritoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionUsuarioAsesoria}
     */
    @Autowired
    private PostulacionUsuarioAsesoriaRepository postulacionUsuarioAsesoriaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EncuestaPostulacionLaboral}
     */
    @Autowired
    private EncuestaPostulacionLaboralRepository encuestaPostulacionLaboralRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RespuestaUsuario}
     */
    @Autowired
    private RespuestaUsuarioRepository respuestaUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RecadoContacto}
     */
    @Autowired
    private RecadoContactoRepository recadoContactoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CorreosValidados}
     */
    @Autowired
    private CorreosValidadosRepository correosValidadosRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RespaldoUsuario}
     */
    @Autowired
    private RespaldoUsuarioRepository respaldoUsuarioRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     */
    @Autowired
    private RespuestaPreguntaOfertaLaboralRepository respuestaPreguntaOfertaLaboralRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionArchivosAdjuntos}
     */
    @Autowired
    private PostulacionArchivosAdjuntosRepository postulacionArchivosAdjuntosRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OperadorContabilidad}
     */
    @Autowired
    private OperadorContabilidadRepository operadorContabilidadRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    private TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RolUsuario}
     */
    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    /**
     * Objeto del tipo EntityManagerFactory utilizado para el manejo de criterias.
     */
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private EntityManagerFactory emf;

    /**
     * Obtiene el {@link crm.entities.AccesoSistema} de un {@link crm.entities.Usuario} al sistema CRM si es
     * que este posee dicho acceso.
     *
     * @param usuario usuario a comprobar acceso a sistema CRM.
     *
     * @return el acceso correspondiente o null si es que el usuario no posee acceso al sistema.
     */
    public AccesoSistema getAccesoCRMDeUsuario(Usuario usuario) {
        List<AccesoSistema> accesos = accesoSistemaRepository.findByUsuario(usuario);
        for(AccesoSistema acceso : accesos) {
            if (acceso.getCompositeId().getCodigoSistema() == 38)
                return acceso;
        }
        return null;
    }

    /**
     * Obtiene el {@link crm.entities.Usuario} segun el id que se pase como parametro
     *
     * @param idUsuario id del usuario a buscar.
     *
     * @return el {@link crm.entities.Usuario} o null si es que no se encontraron resultados.
     * @author renata mella <renata.mella.12@sansano.usm.cl>
     */
    // TODO permiso OK
    //@PostAuthorize("hasPermission(returnObject,'Ver')")
    public Usuario getUsuarioById(Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if(usuario!=null){
            return usuario;
        }
        else return null;
    }

    /**
     * Actualiza el {@link crm.entities.Usuario} que se pase como parametro en la base de datos
     *
     * @param usuario objeto usuario que se desea actualizar.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    //@PreAuthorize("hasPermission(#usuario, 'Editar')")
    public void saveUsuario(Usuario usuario) {
        Date fechaActual = new java.util.Date();
        Usuario antiguo = usuarioRepository.findById(usuario.getId());

        if ((antiguo.getRut() == null) || (antiguo.getRut()!= null && usuario.getRut() != null && usuario.getRut().compareTo(antiguo.getRut()) != 0))
            antiguo.setRut(usuario.getRut());

        if ((antiguo.getDigitoVerificador() == null) || (antiguo.getDigitoVerificador() != null && usuario.getDigitoVerificador() != null && usuario.getDigitoVerificador().compareTo(antiguo.getDigitoVerificador()) != 0))
            antiguo.setDigitoVerificador(usuario.getDigitoVerificador().toUpperCase());

        if ((antiguo.getPasaporte() == null) || (antiguo.getPasaporte()!= null && usuario.getPasaporte().compareTo(antiguo.getPasaporte()) != 0))
            antiguo.setPasaporte(usuario.getPasaporte());

        if ((antiguo.getFechaNacimiento() == null) || (antiguo.getFechaNacimiento()!= null && usuario.getFechaNacimiento().compareTo(antiguo.getFechaNacimiento()) != 0))
            antiguo.setFechaNacimiento(usuario.getFechaNacimiento());

        if ((antiguo.getVocativo() == null) || (antiguo.getVocativo()!= null && usuario.getVocativo().compareTo(antiguo.getVocativo()) != 0))
            antiguo.setVocativo(usuario.getVocativo());

        if ((antiguo.getNombres() == null) || (antiguo.getNombres()!= null && usuario.getNombres().compareTo(antiguo.getNombres()) != 0))
            antiguo.setNombres(usuario.getNombres().toUpperCase());

        if ((antiguo.getApellidoPaterno() == null) || (antiguo.getApellidoPaterno()!= null && usuario.getApellidoPaterno().compareTo(antiguo.getApellidoPaterno()) != 0))
            antiguo.setApellidoPaterno(usuario.getApellidoPaterno().toUpperCase());

        if ((antiguo.getApellidoMaterno() == null) || (antiguo.getApellidoMaterno()!= null && usuario.getApellidoMaterno().compareTo(antiguo.getApellidoMaterno()) != 0))
            antiguo.setApellidoMaterno(usuario.getApellidoMaterno().toUpperCase());

        if ((antiguo.getSexo() == null) || (antiguo.getSexo()!= null && usuario.getSexo().compareTo(antiguo.getSexo()) != 0))
            antiguo.setSexo(usuario.getSexo());

        if (usuario.getEstadoCivil().getCodigo().compareTo(antiguo.getEstadoCivil().getCodigo()) != 0) {
            TipoEstadoCivil nuevoEstadoCivil = estadoCivilRepository.findOne(usuario.getEstadoCivil().getCodigo());
            antiguo.setEstadoCivil(nuevoEstadoCivil);
        }

        if ((antiguo.getNumeroHijos() == null) || (antiguo.getNumeroHijos()!= null && usuario.getNumeroHijos().compareTo(antiguo.getNumeroHijos()) != 0))
            antiguo.setNumeroHijos(usuario.getNumeroHijos());

        if ((antiguo.getApodo() == null) || (antiguo.getApodo()!= null && usuario.getApodo().compareTo(antiguo.getApodo()) != 0))
            antiguo.setApodo(usuario.getApodo());

        if ((antiguo.getCodigoPostal() == null) || (antiguo.getCodigoPostal()!= null && usuario.getCodigoPostal().compareTo(antiguo.getCodigoPostal()) != 0))
            antiguo.setCodigoPostal(usuario.getCodigoPostal());

        if ((antiguo.getDireccion() == null) || (antiguo.getDireccion()!= null && usuario.getDireccion().compareTo(antiguo.getDireccion()) != 0))
            antiguo.setDireccion(usuario.getDireccion());

        if ((antiguo.getComuna().getCodigo() == null) || (antiguo.getComuna().getCodigo()!= null && usuario.getComuna().getCodigo() != antiguo.getComuna().getCodigo())) {
            Comuna comuna = comunaRepository.findByCodigo( usuario.getComuna().getCodigo());
            antiguo.setComuna(comuna);
        }
        if ((antiguo.getFonoParticular() == null) || (antiguo.getFonoParticular()!= null && usuario.getFonoParticular().compareTo(antiguo.getFonoParticular()) != 0))
            antiguo.setFonoParticular(usuario.getFonoParticular());

        if ((antiguo.getFonoOpcional() == null) || (antiguo.getFonoOpcional()!= null && usuario.getFonoOpcional().compareTo(antiguo.getFonoOpcional()) != 0))
            antiguo.setFonoOpcional(usuario.getFonoOpcional());

        if ((antiguo.getCelular() == null) || (antiguo.getCelular()!= null && usuario.getCelular().compareTo(antiguo.getCelular()) != 0))
            antiguo.setCelular(usuario.getCelular());

        /*
        TODO BORRAR
        if ((antiguo.getComoSupoDeRedExalumnos().getCodigo() == null) || (antiguo.getComoSupoDeRedExalumnos().getCodigo()!= null && usuario.getComoSupoDeRedExalumnos().getCodigo().compareTo(antiguo.getComoSupoDeRedExalumnos().getCodigo()) != 0)) {
            ComoSupoDeRedExalumnos nuevaRazon = comoSupoDeRedExalumnoRepository.findOne(usuario.getComoSupoDeRedExalumnos().getCodigo());
            antiguo.setComoSupoDeRedExalumnos(nuevaRazon);
        }
        */


        // si no tiene credencial de acceso y se ha ingresado un email principal, simplemente se setea una nueva con el email ingresado y password por defecto
        // ( en caso que en la vista se agregó una password, será seteada mas abajo)
        if (antiguo.getCredencialesAcceso() == null && usuario.getEmail() != null) {
            actualizaLoginAexa(antiguo, usuario.getEmail(), "cambiame" );
        }

        // si se eliminó el email principal, se copia el laboral y luego opcional ( en ese orden en caso que no existan)
        if ( usuario.getEmail().compareTo("") == 0 ) {

            // si existe email laboral, se copia de email laboral a principal y login
            if ( usuario.getEmailLaboral().compareTo("") != 0 ) {
                antiguo.setEmail(usuario.getEmailLaboral());
                antiguo.getCredencialesAcceso().setUsername(usuario.getEmailLaboral());
            }

            // si existe email opcional, se copia de email opcional a principal y login
            else if (usuario.getEmailOpcional().compareTo("") != 0 ) {
                antiguo.setEmail(usuario.getEmailOpcional());
                antiguo.getCredencialesAcceso().setUsername(usuario.getEmailOpcional());
            }

            // si no existe ninguno se elimina el login
            else {
                antiguo.setEmail(null);

                if (loginAexaRepository.findByUsuarioId( antiguo.getId()) != null) {
                    loginAexaRepository.delete( antiguo.getId() );
                }
            }
        }
        else {
            // cambio de email a otro ( si se cambia el email principal, se cambia tambien en el login aexa )
            if ((antiguo.getEmail() == null) || (antiguo.getEmail()!= null && usuario.getEmail().compareTo(antiguo.getEmail()) != 0)) {
                antiguo.setEmail(usuario.getEmail());
                antiguo.getCredencialesAcceso().setUsername(usuario.getEmail());
            }
        }

        // actualizacion de email opcional
        if ((antiguo.getEmailOpcional() == null) || (antiguo.getEmailOpcional()!= null && usuario.getEmailOpcional().compareTo(antiguo.getEmailOpcional()) != 0))
            antiguo.setEmailOpcional(usuario.getEmailOpcional());

        // si email opcional es vacio se setea como nulo
        if ( usuario.getEmailOpcional().compareTo("") == 0 ) {
            antiguo.setEmailOpcional(null);
        }

        // actualizacion de email laboral
        if ((antiguo.getEmailLaboral() == null) || (antiguo.getEmailLaboral()!= null && usuario.getEmailLaboral().compareTo(antiguo.getEmailLaboral()) != 0))
            antiguo.setEmailLaboral(usuario.getEmailLaboral());

        // si email laboral es vacio se setea como nulo
        if ( usuario.getEmailLaboral().compareTo("") == 0 ) {
            antiguo.setEmailLaboral(null);
        }

        // actualizacion del password
        if (antiguo.getCredencialesAcceso() != null && antiguo.getCredencialesAcceso().getPassword() != null ) {
            if ( usuario.getCredencialesAcceso().getPassword().compareTo("") != 0  && usuario.getCredencialesAcceso().getPassword().compareTo(antiguo.getCredencialesAcceso().getPassword()) != 0) {
                actualizaLoginAexa(antiguo, usuario.getEmail(), usuario.getCredencialesAcceso().getPassword());
            }
        }

        if (usuario.getTipoVigencia().getCodVigencia().compareTo(antiguo.getTipoVigencia().getCodVigencia()) != 0) {
            TipoVigencia nuevoTipoVigencia = tipoVigenciaRepository.findOne(usuario.getTipoVigencia().getCodVigencia());
            antiguo.setTipoVigencia(nuevoTipoVigencia);
        }

        antiguo.setEstado(usuario.getEstado());

        PreferenciaUsuarioUsmempleo preferencias = antiguo.getPreferenciaUsuarioUsmempleo();

        // verifica el caso que el usuario no posea PreferenciaUsuarioUsmempleo, se setea una por defecto
        if ( preferencias == null ) {
            preferencias = new PreferenciaUsuarioUsmempleo ();

            // campos no mostrados en la vista, por lo que se debe setear a mano
            preferencias.setAceptoCondiciones(true);
            preferencias.setEstadoVideoConferencia(0);
        }

        preferencias.setOfertasPorCorreo(usuario.getPreferenciaUsuarioUsmempleo().getOfertasPorCorreo());
        if(usuario.getPreferenciaUsuarioUsmempleo().getOfertasPorCorreo() == false){
            List<FiltroOfertaLaboral> filtrosOfertasLaborales = filtroOfertaLaboralRepository.buscarPorIdUsuario(usuario.getId());
            for(int i=0; i<filtrosOfertasLaborales.size();i++){
                filtrosOfertasLaborales.get(i).setEnviarEmail(false);
                filtroOfertaLaboralRepository.save(filtrosOfertasLaborales.get(i));
            }
        }
        preferencias.setCvVisible(usuario.getPreferenciaUsuarioUsmempleo().getCvVisible());
        preferencias.setMostrarFoto(usuario.getPreferenciaUsuarioUsmempleo().getMostrarFoto());
        preferencias.setHabilitadoParaGrabar(usuario.getPreferenciaUsuarioUsmempleo().getHabilitadoParaGrabar());
        preferencias.setMostrarExpectativaSalario(usuario.getPreferenciaUsuarioUsmempleo().getMostrarExpectativaSalario());
        preferencias.setEstadoCivil(usuario.getPreferenciaUsuarioUsmempleo().getEstadoCivil());
        preferencias.setActualizarDatos(usuario.getPreferenciaUsuarioUsmempleo().getActualizarDatos());
        preferencias.setNewsletter(usuario.getPreferenciaUsuarioUsmempleo().getNewsletter());
        preferencias.setEncabezado(usuario.getPreferenciaUsuarioUsmempleo().getEncabezado());
        preferencias.setInfoPersonal(usuario.getPreferenciaUsuarioUsmempleo().getInfoPersonal());
        preferencias.setInicioPermisoPost(usuario.getPreferenciaUsuarioUsmempleo().getInicioPermisoPost());
        preferencias.setFinPermisoPost(usuario.getPreferenciaUsuarioUsmempleo().getFinPermisoPost());
        preferencias.setFormatoCvUsmempleo(usuario.getPreferenciaUsuarioUsmempleo().getFormatoCvUsmempleo());

        preferencias.setRutUsuario(getCurrentUser().getRut());
        preferencias.setFechaModificacion(fechaActual);
        preferencias.setUsuario(antiguo);

        antiguo.setPreferenciaUsuarioUsmempleo(preferencias);


        InfoProfesionalExalumno infoProfesionalExalumno = antiguo.getInformacionProfesional();

        // verifica el caso que el usuario no posea PreferenciaUsuarioUsmempleo, se setea una por defecto
        if ( infoProfesionalExalumno == null ) {
            infoProfesionalExalumno = new InfoProfesionalExalumno ();
        }

        infoProfesionalExalumno.setMovilidadPais(usuario.getInformacionProfesional().getMovilidadPais());
        infoProfesionalExalumno.setMovilidadRegion(usuario.getInformacionProfesional().getMovilidadRegion());
        infoProfesionalExalumno.setLicenciaConducir(usuario.getInformacionProfesional().getLicenciaConducir());
        infoProfesionalExalumno.setVehiculoPropio(usuario.getInformacionProfesional().getVehiculoPropio());
        infoProfesionalExalumno.setExpectativaSalarial(usuario.getInformacionProfesional().getExpectativaSalarial());
        infoProfesionalExalumno.setAnoExpLaboral(usuario.getInformacionProfesional().getAnoExpLaboral());
        infoProfesionalExalumno.setObjetivoProfesional(usuario.getInformacionProfesional().getObjetivoProfesional());
        infoProfesionalExalumno.setDistinciones(usuario.getInformacionProfesional().getDistinciones());
        infoProfesionalExalumno.setTipoMoneda(usuario.getInformacionProfesional().getTipoMoneda());
        infoProfesionalExalumno.setSituacionLaboral(usuario.getInformacionProfesional().getSituacionLaboral());
        infoProfesionalExalumno.setDisponibilidad(usuario.getInformacionProfesional().getDisponibilidad());
        infoProfesionalExalumno.setDominioComputacional(usuario.getInformacionProfesional().getDominioComputacional());
        infoProfesionalExalumno.setRutUsuario(getCurrentUser().getRut());
        infoProfesionalExalumno.setFechaModificacion(fechaActual);
        infoProfesionalExalumno.setUsuario(antiguo);

        antiguo.setInformacionProfesional(infoProfesionalExalumno);

        // almacena rut y fecha de la modificacion
        antiguo.setRutUsuario(getCurrentUser().getRut());
        antiguo.setFechaModificacion(fechaActual);

        usuarioRepository.save(antiguo);
    }




    /**
     * Calcula los dias restantes que quedan para el cumpleanos de una persona.
     *
     * @param fecha fecha de nacimiento.
     *
     * @return Dias que faltan para el cumpleanos de un {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Integer getDiasRestantes(Date fecha){
        if(fecha != null){
            Integer i = 0;
            Calendar cal = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("MM-dd");
            if(df.format(cal.getTime()).equals(df.format(fecha)))
                return 0;
            else {
                do {
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    i++;
                }while(!df.format(cal.getTime()).equals(df.format(fecha)));
            }
            return i;
        }
        return null;
    }

    /**
     * Obtiene un listado de {@link crm.entities.Usuario}, segun criterio de la busqueda y valores a buscar
     *
     * @param criterioBusqueda Criterio sobre el cual se desea realizar la busqueda (nombres, apellidos, carrera, etc).
     * @param valoresBusqueda Almacenamiento de los datos ingresados en la busqueda
     * @param tamanoPagina Cantidad de elementos a mostrar por página
     * @param numeroPagina Numero de la pagina de la paginación, que se desea mostrar
     *
     * @return Lista de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_INSTITUCION','ROLE_AYUDANTE_INSTITUCION','ROLE_LEER_INSTITUCION','ROLE_ADMIN_CARRERA')")
    public Page<Usuario> busquedaUsuarioPorCriterio(String criterioBusqueda,
                                                    Map<String, String> valoresBusqueda,
                                                    Integer tamanoPagina, Integer numeroPagina) {

        if (getCurrentUser().getRolesUsuario().get(0).getRolAcceso().getNombre().compareTo("ROLE_SUPER_ADMIN")!=0) {
            List<AutorizacionUsuario> autorizaciones = getCurrentUser().getAutorizacionesUsuario();
            PageRequest pageable = new PageRequest(numeroPagina, tamanoPagina);
            EntityManager em = emf.createEntityManager();
            String query = "SELECT DISTINCT ae.usuario FROM AntecedenteEducacional ae";

            if (criterioBusqueda.equals("empresas")) {
                query += " JOIN ae.usuario u JOIN u.actividadExalumnoList aex";
            }

            query += " WHERE";

            for(int i=0; i<autorizaciones.size();i++){
                if(autorizaciones.get(i).getRestriccion().split("=")[0].compareTo("codInstitucion")==0){
                        if (i == 0)
                            query += " ae.institucion.codInstitucion = " + Short.parseShort(autorizaciones.get(i).getRestriccion().split("=")[1]);
                        else
                            query += " OR ae.institucion.codInstitucion = " + Short.parseShort(autorizaciones.get(i).getRestriccion().split("=")[1]);
                }
                else if(autorizaciones.get(i).getRestriccion().split("=")[0].compareTo("codCarrera")==0){
                        if(i==0) query += " ae.institucion.codInstitucion = "+Long.parseLong(autorizaciones.get(i).getRestriccion().split(";")[1].split("=")[1])
                                + " AND ae.carrera.codCarrera = "+Short.parseShort(autorizaciones.get(i).getRestriccion().split(";")[0].split("=")[1]);
                        else query += " OR ae.institucion.codInstitucion = "+Long.parseLong(autorizaciones.get(i).getRestriccion().split(";")[1].split("=")[1])
                                + " AND ae.carrera.codCarrera = "+Short.parseShort(autorizaciones.get(i).getRestriccion().split(";")[0].split("=")[1]);
                }
            }
            if (criterioBusqueda.equals("nombres")) query += " AND UPPER(translate( COALESCE(ae.usuario.nombres, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("nombres")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')";

            if (criterioBusqueda.equals("rut")) {
                Integer rutUsuario = 0;
                if (!valoresBusqueda.get("rut").isEmpty()) rutUsuario = Integer.parseInt(valoresBusqueda.get("rut").split("-")[0]);
                query += " AND ae.usuario.rut = "+rutUsuario;
            }

            if (criterioBusqueda.equals("apellidos")) query += " AND UPPER(translate( COALESCE(ae.usuario.apellidoPaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-paterno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')"
                                                            + " AND UPPER(translate( COALESCE(ae.usuario.apellidoMaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-materno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')";

            if (criterioBusqueda.equals("email")) query += " AND UPPER(ae.usuario.email) = UPPER('"+valoresBusqueda.get("email")+"')"
                                                        + " OR UPPER(ae.usuario.emailOpcional) = UPPER('"+valoresBusqueda.get("email")+"')"
                                                        + " OR UPPER(ae.usuario.emailLaboral) = UPPER('"+valoresBusqueda.get("email")+"')";

            if (criterioBusqueda.equals("nombre-y-apellido")) query += " AND UPPER(translate( COALESCE(ae.usuario.nombres, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("nombres")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')"
                                                                    + " AND UPPER(translate( COALESCE(ae.usuario.apellidoPaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-paterno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')"
                                                                    + " AND UPPER(translate( COALESCE(ae.usuario.apellidoMaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-materno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')";

            if (criterioBusqueda.equals("carreras")) {
                if (valoresBusqueda.get("carrera").equals("0")) valoresBusqueda.put("carrera", "%");
                if (valoresBusqueda.get("ano-ingreso").equals("0")) valoresBusqueda.put("ano-ingreso", "%");
                if (valoresBusqueda.get("ano-egreso").equals("0")) valoresBusqueda.put("ano-egreso", "%");
                if (valoresBusqueda.get("ano-titulo").equals("0")) valoresBusqueda.put("ano-titulo", "%");
                query += " AND COALESCE(TO_CHAR(ae.carrera.codCarrera, 'FM9999'), '') LIKE '"+valoresBusqueda.get("carrera")+"'" +
                        " AND ae.institucion.codInstitucion = "+valoresBusqueda.get("institucion") +
                        " AND COALESCE(TO_CHAR(ae.anioIngreso, 'FM9999'), '') LIKE '"+valoresBusqueda.get("ano-ingreso")+"'" +
                        " AND COALESCE(TO_CHAR(ae.anioEgreso, 'FM9999'), '') LIKE  '"+valoresBusqueda.get("ano-egreso")+"'" +
                        " AND COALESCE(TO_CHAR(ae.anioTitulo, 'FM9999'), '') LIKE '"+valoresBusqueda.get("ano-titulo")+"'";
            }

            if (criterioBusqueda.equals("empresas")) {
                List<Empresa> empresas = empresaRepository.buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(valoresBusqueda.get("empresa"));
                query += " AND aex.empresa.id = "+empresas.get(0).getId();
            }

            if (criterioBusqueda.equals("pais")) {
                query += " AND ae.usuario.pais.id = "+valoresBusqueda.get("pais");
                if(valoresBusqueda.get("region") != null && !valoresBusqueda.get("region").equals("0")) query += " AND ae.usuario.comuna.provincia.region.id = "+valoresBusqueda.get("region");
                if(valoresBusqueda.get("provincia") != null && !valoresBusqueda.get("provincia").equals("0")) query += " AND ae.usuario.comuna.provincia.id = "+valoresBusqueda.get("provincia");
                if(valoresBusqueda.get("comuna") != null && !valoresBusqueda.get("comuna").equals("0")) query += " AND ae.usuario.comuna.id = "+valoresBusqueda.get("comuna");
            }
            query+=" ORDER BY ae.usuario.apellidoPaterno, ae.usuario.apellidoMaterno ASC";
            TypedQuery<Usuario> typedQuery = em.createQuery(query , Usuario.class);
            Long totalResultados = getResultCount(em, criterioBusqueda, valoresBusqueda);

            typedQuery.setFirstResult((pageable.getPageNumber())*pageable.getPageSize());
            typedQuery.setMaxResults(pageable.getPageSize());
            List<Usuario> usuarios = typedQuery.getResultList();
            Page<Usuario> resultado = new PageImpl<>(usuarios, pageable, totalResultados);
            return resultado;

        }

        PageRequest pageRequest = new PageRequest(numeroPagina, tamanoPagina);

        if (criterioBusqueda.equals("nombres")) return usuarioRepository.buscarUsuariosPorNombres(valoresBusqueda.get("nombres"), pageRequest);

        if (criterioBusqueda.equals("rut")) {
            Integer rutUsuario = 0;
            if (!valoresBusqueda.get("rut").isEmpty()) rutUsuario = Integer.parseInt(valoresBusqueda.get("rut"));
            return usuarioRepository.buscarUsuariosPorRut( rutUsuario , pageRequest);
        }

        if (criterioBusqueda.equals("apellidos")) return usuarioRepository.buscarUsuariosPorApellidos(valoresBusqueda.get("apellido-paterno"), valoresBusqueda.get("apellido-materno"),  pageRequest);

        if (criterioBusqueda.equals("email")) return usuarioRepository.buscarUsuariosPorEmail(valoresBusqueda.get("email"), pageRequest);

        if (criterioBusqueda.equals("nombre-y-apellido")) return usuarioRepository.buscarUsuariosPorNombreYApellidos(valoresBusqueda.get("nombres"), valoresBusqueda.get("apellido-paterno"), valoresBusqueda.get("apellido-materno"), pageRequest);

        if (criterioBusqueda.equals("carreras")) {
            if (valoresBusqueda.get("carrera").equals("0")) valoresBusqueda.put("carrera", "%");
            if (valoresBusqueda.get("ano-ingreso").equals("0")) valoresBusqueda.put("ano-ingreso", "%");
            if (valoresBusqueda.get("ano-egreso").equals("0")) valoresBusqueda.put("ano-egreso", "%");
            if (valoresBusqueda.get("ano-titulo").equals("0")) valoresBusqueda.put("ano-titulo", "%");
            return usuarioRepository.buscarUsuariosPorCarreraInstitucionIngresoEgresoTitulacion(Short.parseShort(valoresBusqueda.get("institucion")), valoresBusqueda.get("carrera"), valoresBusqueda.get("ano-ingreso"), valoresBusqueda.get("ano-egreso"), valoresBusqueda.get("ano-titulo"), pageRequest);
        }

        if (criterioBusqueda.equals("empresas")) {
            List<Empresa> empresas = empresaRepository.buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(valoresBusqueda.get("empresa"));
            if (empresas.size() != 0) return usuarioRepository.buscarUsuariosDeActividadExalumnoPorIdEmpresa(empresas.get(0).getId(), pageRequest);
        }

        if (criterioBusqueda.equals("pais")) {
            String region = "%";
            String provincia = "%";
            String comuna = "%";
            if(valoresBusqueda.get("region") != null && !valoresBusqueda.get("region").equals("0")) region = valoresBusqueda.get("region");
            if(valoresBusqueda.get("provincia") != null && !valoresBusqueda.get("provincia").equals("0")) provincia = valoresBusqueda.get("provincia");
            if(valoresBusqueda.get("comuna") != null && !valoresBusqueda.get("comuna").equals("0")) comuna = valoresBusqueda.get("comuna");
            return usuarioRepository.busquedaGeografica(Short.parseShort(valoresBusqueda.get("pais")), region, provincia, comuna, pageRequest);
        }

        return null;
    }

    private Long getResultCount(EntityManager em, String criterioBusqueda, Map<String, String> valoresBusqueda){
        List<AutorizacionUsuario> autorizaciones = getCurrentUser().getAutorizacionesUsuario();
        String query = "SELECT COUNT( DISTINCT ae.usuario ) FROM AntecedenteEducacional ae";

        if (criterioBusqueda.equals("empresas")) {
            query += " JOIN ae.usuario u JOIN u.actividadExalumnoList aex";
        }

        query += " WHERE";

        for(int i=0; i<autorizaciones.size();i++){
            if(autorizaciones.get(i).getRestriccion().split("=")[0].compareTo("codInstitucion")==0){
                if (i == 0)
                    query += " ae.institucion.codInstitucion = " + Short.parseShort(autorizaciones.get(i).getRestriccion().split("=")[1]);
                else
                    query += " OR ae.institucion.codInstitucion = " + Short.parseShort(autorizaciones.get(i).getRestriccion().split("=")[1]);
            }
            else if(autorizaciones.get(i).getRestriccion().split("=")[0].compareTo("codCarrera")==0){
                if(i==0) query += " ae.institucion.codInstitucion = "+Long.parseLong(autorizaciones.get(i).getRestriccion().split(";")[1].split("=")[1])
                        + " AND ae.carrera.codCarrera = "+Short.parseShort(autorizaciones.get(i).getRestriccion().split(";")[0].split("=")[1]);
                else query += " OR ae.institucion.codInstitucion = "+Long.parseLong(autorizaciones.get(i).getRestriccion().split(";")[1].split("=")[1])
                        + " AND ae.carrera.codCarrera = "+Short.parseShort(autorizaciones.get(i).getRestriccion().split(";")[0].split("=")[1]);
            }
        }
        if (criterioBusqueda.equals("nombres")) query += " AND UPPER(translate( COALESCE(ae.usuario.nombres, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("nombres")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')";

        if (criterioBusqueda.equals("rut")) {
            Integer rutUsuario = 0;
            if (!valoresBusqueda.get("rut").isEmpty()) rutUsuario = Integer.parseInt(valoresBusqueda.get("rut").split("-")[0]);
            query += " AND ae.usuario.rut = "+rutUsuario;
        }

        if (criterioBusqueda.equals("apellidos")) query += " AND UPPER(translate( COALESCE(ae.usuario.apellidoPaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-paterno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')"
                + " AND UPPER(translate( COALESCE(ae.usuario.apellidoMaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-materno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')";

        if (criterioBusqueda.equals("email")) query += " AND UPPER(ae.usuario.email) = UPPER('"+valoresBusqueda.get("email")+"')"
                + " OR UPPER(ae.usuario.emailOpcional) = UPPER('"+valoresBusqueda.get("email")+"')"
                + " OR UPPER(ae.usuario.emailLaboral) = UPPER('"+valoresBusqueda.get("email")+"')";

        if (criterioBusqueda.equals("nombre-y-apellido")) query += " AND UPPER(translate( COALESCE(ae.usuario.nombres, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("nombres")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')"
                + " AND UPPER(translate( COALESCE(ae.usuario.apellidoPaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-paterno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')"
                + " AND UPPER(translate( COALESCE(ae.usuario.apellidoMaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate('"+valoresBusqueda.get("apellido-materno")+"','áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')";

        if (criterioBusqueda.equals("carreras")) {
            if (valoresBusqueda.get("carrera").equals("0")) valoresBusqueda.put("carrera", "%");
            if (valoresBusqueda.get("ano-ingreso").equals("0")) valoresBusqueda.put("ano-ingreso", "%");
            if (valoresBusqueda.get("ano-egreso").equals("0")) valoresBusqueda.put("ano-egreso", "%");
            if (valoresBusqueda.get("ano-titulo").equals("0")) valoresBusqueda.put("ano-titulo", "%");
            query += " AND COALESCE(TO_CHAR(ae.carrera.codCarrera, 'FM9999'), '') LIKE '"+valoresBusqueda.get("carrera")+"'" +
                    " AND ae.institucion.codInstitucion = "+valoresBusqueda.get("institucion") +
                    " AND COALESCE(TO_CHAR(ae.anioIngreso, 'FM9999'), '') LIKE '"+valoresBusqueda.get("ano-ingreso")+"'" +
                    " AND COALESCE(TO_CHAR(ae.anioEgreso, 'FM9999'), '') LIKE  '"+valoresBusqueda.get("ano-egreso")+"'" +
                    " AND COALESCE(TO_CHAR(ae.anioTitulo, 'FM9999'), '') LIKE '"+valoresBusqueda.get("ano-titulo")+"'";
        }

        if (criterioBusqueda.equals("empresas")) {
            List<Empresa> empresas = empresaRepository.buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(valoresBusqueda.get("empresa"));
            query += " AND aex.empresa.id = "+empresas.get(0).getId();
        }

        if (criterioBusqueda.equals("pais")) {
            query += " AND ae.usuario.pais.id = "+valoresBusqueda.get("pais");
            if(valoresBusqueda.get("region") != null && !valoresBusqueda.get("region").equals("0")) query += " AND ae.usuario.comuna.provincia.region.id = "+valoresBusqueda.get("region");
            if(valoresBusqueda.get("provincia") != null && !valoresBusqueda.get("provincia").equals("0")) query += " AND ae.usuario.comuna.provincia.id = "+valoresBusqueda.get("provincia");
            if(valoresBusqueda.get("comuna") != null && !valoresBusqueda.get("comuna").equals("0")) query += " AND ae.usuario.comuna.id = "+valoresBusqueda.get("comuna");
        }
        Long count = (Long) em.createQuery(query).getSingleResult();
        return count;
    }

    /**
     * Guarda un {@link crm.entities.Usuario} nuevo en la base de datos.
     *
     * @param usuario {@link crm.entities.Usuario} a guardar en la base de datos.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @PreAuthorize("hasPermission(#usuario, 'Crear')")
    public void guardarUsuario(Usuario usuario){
        //dacuna: se deben settear previo a guardar un usuario todas las relaciones/colecciones que se desean crear en
        //conjunto con el usuario creado.

        Date fechaActual = new java.util.Date();

        usuario.setNombres(usuario.getNombres().toUpperCase());
        usuario.setApellidoPaterno(usuario.getApellidoPaterno().toUpperCase());
        usuario.setApellidoMaterno(usuario.getApellidoMaterno().toUpperCase());
        usuario.setDigitoVerificador(usuario.getDigitoVerificador().toUpperCase());

        usuario.setCompletitudContacto(getCompletitud(usuario));

        usuario.setTipoVigencia(tipoVigenciaRepository.findByCodVigencia(Short.parseShort("1")));  // queda en estado vigente por ser seteado por el admin

        /*
        TODO BORRAR
        usuario.setTipoUsuario(getTipoUsuarioById((short) 2));
        */


        usuario.setTrayectoriaCompleta((short) 25);
        usuario.setComuna(comunaRepository.findByCodigo(usuario.getComuna().getCodigo()));
        usuario.setPais(paisRepository.findById(usuario.getPais().getId()));
        usuario.getComuna().setProvincia(provinciaRepository.findById(usuario.getComuna().getProvincia().getId()).get(0));
        usuario.getComuna().getProvincia().setRegion(regionRepository.findOne(usuario.getComuna().getProvincia().getRegion().getId()));
        usuario.setNacionalidad(usuario.getPais().getNombreNacionalidad());
        usuario.setEstadoCivil(estadoCivilRepository.findByCodigo(usuario.getEstadoCivil().getCodigo()));

        /*
        TODO BORRAR
        usuario.setComoSupoDeRedExalumnos(comoSupoDeRedExalumnoRepository.findByCodigo(usuario.getComoSupoDeRedExalumnos().getCodigo()));
        */

        //Se setea la InfoProfesionalExalumno del usuario a crear con valores por defecto
        TipoMoneda tipoMoneda = tipoMonedaRepository.findByCodigo((short)1);
        TipoDisponibilidad tipoDisponibilidad = tipoDisponibilidadRepository.findByCodigo((short) 0);
        TipoDominioCompu tipoDominioCompu = tipoDominioCompuRepository.findByCodigo((short) 0);
        TipoSituacionLaboral tipoSituacionLaboral = tipoSituacionLaboralRepository.findByCodigo((short)0);

        InfoProfesionalExalumno infoProfesionalExalumno = new InfoProfesionalExalumno();
        infoProfesionalExalumno.setDefaultValues(usuario.getId(), tipoMoneda, tipoDisponibilidad, tipoDominioCompu, tipoSituacionLaboral);
        infoProfesionalExalumno.setRutUsuario(getCurrentUser().getRut());
        infoProfesionalExalumno.setFechaModificacion(fechaActual);
        infoProfesionalExalumno.setUsuario(usuario);
        infoProfesionalExalumno.setUsuarioId(usuario.getId());
        usuario.setInformacionProfesional(infoProfesionalExalumno);


        //Se setea la PreferenciaUsuarioUsmempleo del usuario a crear con valores por defecto
        FormatoCvUsmempleo formatoCvUsmempleo = formatoCvUsmempleoRepository.findById(Long.parseLong("1"));         // se le setea el formato estandar

        PreferenciaUsuarioUsmempleo preferenciaUsuarioUsmempleo = new PreferenciaUsuarioUsmempleo();
        preferenciaUsuarioUsmempleo.setDefaultValues(usuario.getId(), formatoCvUsmempleo);
        preferenciaUsuarioUsmempleo.setRutUsuario(getCurrentUser().getRut());
        preferenciaUsuarioUsmempleo.setFechaModificacion(fechaActual);
        preferenciaUsuarioUsmempleo.setUsuario(usuario);
        preferenciaUsuarioUsmempleo.setUsuarioId(usuario.getId());
        usuario.setPreferenciaUsuarioUsmempleo(preferenciaUsuarioUsmempleo);


        usuario.setRutUsuario(getCurrentUser().getRut());
        usuario.setFechaModificacion(fechaActual);
        usuario.setFechaRegistro(fechaActual);

        if( usuario.getDigitoVerificador().compareTo("k") == 0 ){
            usuario.setDigitoVerificador("K");
        }

        // si email es vacio se setea como nulo
        if ( usuario.getEmail().compareTo("") == 0 ) {
            usuario.setEmail(null);
        }
        // seteo de login aexa
        actualizaLoginAexa(usuario, usuario.getEmail(), usuario.getCredencialesAcceso().getPassword() );

        // si email opcional es vacio se setea como nulo
        if ( usuario.getEmailOpcional().compareTo("") == 0 ) {
            usuario.setEmailOpcional(null);
        }

        // si email laboral es vacio se setea como nulo
        if ( usuario.getEmailLaboral().compareTo("") == 0 ) {
            usuario.setEmailLaboral(null);
        }

        // si numero celular está vacio se setea como nulo
        if ( usuario.getCelular().compareTo("") == 0 ) {
            usuario.setCelular(null);
        }

        // si numero telefono opcional está vacio se setea como nulo
        if ( usuario.getFonoOpcional().compareTo("") == 0 ) {
            usuario.setFonoOpcional(null);
        }

        // si numero telefono particular está vacio se setea como nulo
        if ( usuario.getFonoParticular().compareTo("") == 0 ) {
            usuario.setFonoParticular(null);
        }

        // si apodo está vacio se setea como nulo
        if ( usuario.getApodo().compareTo("") == 0 ) {
            usuario.setApodo(null);
        }

        // si pasaporte está vacio se setea como nulo
        if ( usuario.getPasaporte().compareTo("") == 0 ) {
            usuario.setPasaporte(null);
        }

        // si direccion está vacio se setea como nulo
        if ( usuario.getDireccion().compareTo("") == 0 ) {
            usuario.setDireccion(null);
        }

        usuarioRepository.save(usuario);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.TipoUsuario}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.TipoUsuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<TipoUsuario> getTiposUsuario(){
        return tipoUsuarioRepository.findAll();
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<ComoSupoDeRedExalumnos> getComoSupo(){
        return comoSupoDeRedExalumnoRepository.findAll();
    }

    /**
     * Obtiene el porcentaje de cuan completo estan los datos de contacto de un
     * {@link crm.entities.Usuario}.
     *
     * @param usuario {@link crm.entities.Usuario} del cual se desea obtener la completitud
     *
     * @return {@link java.lang.Short} con el porcentaje correspondiente.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Short getCompletitud(Usuario usuario){
        Short completitud = 0;
        if(usuario.getCelular() != null && !usuario.getCelular().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(usuario.getFonoOpcional() != null && !usuario.getFonoOpcional().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(usuario.getFonoParticular() != null && !usuario.getFonoParticular().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(usuario.getDireccion() != null && !usuario.getDireccion().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(usuario.getEmail() != null && !usuario.getEmail().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(usuario.getEmailLaboral() != null && !usuario.getEmailLaboral().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(usuario.getEmailOpcional() != null && !usuario.getEmailOpcional().isEmpty()){
            completitud =(short)(completitud + 14);
        }
        if(completitud>97){
            completitud = (short)100;
        }
        return completitud;
    }

    /**
     * Obtiene una instancia de {@link crm.entities.TipoUsuario}.
     *
     * @param id Id del usuario del que se quiere obtener el {@link crm.entities.TipoUsuario}.
     *
     * @return Objeto de {@link crm.entities.TipoUsuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public TipoUsuario getTipoUsuarioById(Short id){
        return tipoUsuarioRepository.findByCodigo(id);
    }

    /**
     * Obtiene una instancia de {@link crm.entities.FormatoCvUsmempleo}.
     *
     * @param id Id id del usuario del que se quiere obtener el {@link crm.entities.FormatoCvUsmempleo}.
     *
     * @return Objeto de {@link crm.entities.FormatoCvUsmempleo}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public FormatoCvUsmempleo getFormatoCvUsuario(Long id){
        return formatoCvUsmempleoRepository.findById(id);
    }

    /**
     * Obtiene una instancia de {@link crm.entities.Usuario} correspondiente al usuario
     * que esta logueado en el sistema actualmente.
     *
     * @return Objeto de {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Usuario getCurrentUser(){
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Obtiene una instancia de {@link crm.entities.PreferenciaUsuarioUsmempleo}.
     *
     * @param id Id del usuario del que se desea obtener la {@link crm.entities.PreferenciaUsuarioUsmempleo}.
     *
     * @return Objeto de {@link crm.entities.PreferenciaUsuarioUsmempleo}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public PreferenciaUsuarioUsmempleo getPreferenciaUsuario(Long id){

        return preferenciaUsuarioUsmempleoRepository.findByUsuarioId(id);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.FormatoCvUsmempleo}.
     *
     * @return Collection ({@link java.util.Iterator}) de {@link crm.entities.FormatoCvUsmempleo}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public Iterable<FormatoCvUsmempleo> getFormatos(){
        return formatoCvUsmempleoRepository.findAll();
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.AntecedenteEducacional} de una persona que
     * tengan como institucion a la USM.
     *
     * @param idUsuario Id del usuario del que se desean obtener los {@link crm.entities.AntecedenteEducacional}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.AntecedenteEducacional}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<AntecedenteEducacional> getAntecedentesEducacionalesUsm(Long idUsuario){
        return antecedenteEducacionalRepository.findByInstitucionUsm((short) 25, idUsuario);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.AntecedenteEducacional} de una persona que
     * NO tengan como institucion a la USM.
     *
     * @param idUsuario Id del usuario del que se desean obtener los {@link crm.entities.AntecedenteEducacional}
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.AntecedenteEducacional}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<AntecedenteEducacional> getAntecedentesEducacionales(Long idUsuario){
        return antecedenteEducacionalRepository.findByInstitucion((short) 25, idUsuario);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.AntecedenteColegio} de una persona.
     *
     * @param id Id del usuario del que se desean obtener los {@link crm.entities.AntecedenteColegio}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.AntecedenteColegio}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<AntecedenteColegio> getAntecedentesColegio(Long id){
        return antecedenteColegioRepository.findByColegio(id);
    }

    /**
     * Obtiene una lista de todos los {@link crm.entities.ActividadExalumno} de una persona.
     *
     * @param id Id del usuario del que se desea obtener las {@link crm.entities.ActividadExalumno}.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.ActividadExalumno}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<ActividadExalumno> getActividadExalumno(Long id){
        return actividadExalumnoRepository.buscarPorIdUsuario(id);
    }

    /**
     * Obtiene una lista paginada con el email de un usuario o los nombres y apellidos de
     * de una persona segun el criterio de busqueda.
     *
     * @return Page de una lista de Strings con los datos del usuario.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public Page<List<String>> busquedaUsuarioMezclador(String criterio, Integer tamanoPagina, Integer numPagina){

        PageRequest page = new PageRequest(numPagina, tamanoPagina);

        if(criterio.equals("rut")){
            return usuarioRepository.getUsuariosRepetidosPorRut(page);
        }

        if(criterio.equals("nombreYApellido")){
            return usuarioRepository.getUsuariosRepetidosPorNombres(page);
        }

        else if(criterio.equals("email")){
            return usuarioRepository.getUsuariosRepetidosPorEmail(page);
        }

        return null;
    }

    /**
     * Obtiene una lista de {@link crm.entities.Usuario} segun el criterio de busqueda.
     *
     * @param criterio Criterio de busqueda para mezclar los usuarios.
     * @param valor Caracteristica del usuario a buscar.
     * @param paterno Apellido paterno del usuario a buscar.
     * @param materno Apellido materno del usuario a buscar.
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public List<Usuario> getUsuariosByCriterio(String criterio, String valor,
                                               String paterno, String materno){
        if(criterio.equals("nombreYApellido")){
            return usuarioRepository.buscarUsuariosPorNombreYApellidos(valor, paterno, materno);
        }
        else if(criterio.equals("email")){
            return usuarioRepository.findByEmailIgnoreCase(valor);
        }
        return null;
    }

    /**
     * Realiza el proceso de mezclado de dos usuarios, incluyendo la actualizacion del id en las
     * entidades que tengan clave foranea a la tabla usuario aexa. Para esto es que mantiene los datos del usuario con id
     * seleccionado y se le actualiza según las preferencias elegidas en la vista
     *
     * @param usuario {@link crm.entities.Usuario} con las caracteristicas finales de ambos usuarios.
     * @param idUsuario1 id de uno de los usuarios a mezclar.
     * @param idUsuario2 id del otro usuario a mezclar.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void mezclarUsuario(Usuario usuario, Long idUsuario1, Long idUsuario2, Map<String, String> checkboxs) {

        // variable que permite detectar si hubo algun conflicto con clave primaria
        boolean existeConflictoConPK = false;



        // ATRIBUTOS
        // obtencion de datos en BD del usuario a actualizar
        Usuario usuarioFinal = usuarioRepository.findById(usuario.getId());

        usuarioFinal.setNombres(usuario.getNombres().toUpperCase());

        usuarioFinal.setApellidoPaterno(usuario.getApellidoPaterno().toUpperCase());

        usuarioFinal.setApellidoMaterno(usuario.getApellidoMaterno().toUpperCase());

        usuarioFinal.setFechaNacimiento(usuario.getFechaNacimiento());

        usuarioFinal.setDigitoVerificador(usuario.getDigitoVerificador());

        usuarioFinal.setDireccion(usuario.getDireccion());

        if ( usuario.getComuna() != null && ( usuarioFinal.getComuna() == null || usuario.getComuna().getCodigo().compareTo( usuarioFinal.getComuna().getCodigo() ) != 0 )) {                   // si se cambia
            Comuna comuna = comunaRepository.findByCodigo(usuario.getComuna().getCodigo());
            usuarioFinal.setComuna(comuna);
        }

        if ( usuario.getPais() != null && ( usuarioFinal.getPais() == null || usuario.getPais().getId().compareTo( usuarioFinal.getPais().getId() ) != 0 )) {                       // si se cambia
            Pais pais = paisRepository.findById(usuario.getPais().getId());
            usuarioFinal.setPais(pais);
        }

        usuarioFinal.setNacionalidad(usuario.getNacionalidad());

        usuarioFinal.setFonoParticular(usuario.getFonoParticular());

        usuarioFinal.setFonoOpcional(usuario.getFonoOpcional());

        usuarioFinal.setCelular(usuario.getCelular());

        usuarioFinal.setEmail(usuario.getEmail());

        usuarioFinal.setEmailOpcional(usuario.getEmailOpcional());

        usuarioFinal.setEmailLaboral(usuario.getEmailLaboral());

        usuarioFinal.setSexo(usuario.getSexo());

        usuarioFinal.setFechaRegistro(usuario.getFechaRegistro());

        usuarioFinal.setUrlFoto(usuario.getUrlFoto());

        usuarioFinal.setApodo(usuario.getApodo());

        usuarioFinal.setPasaporte(usuario.getPasaporte());

        usuarioFinal.setCodigoPostal(usuario.getCodigoPostal());

        usuarioFinal.setTipoVigencia(tipoVigenciaRepository.findByCodVigencia(Short.parseShort("1")));  // queda en estado vigente por ser seteado por el admin

        /*
        TODO BORRAR
        TipoUsuario nuevoTipoUsuario = tipoUsuarioRepository.findByCodigo(Short.parseShort("2"));
        usuarioFinal.setTipoUsuario(nuevoTipoUsuario);
        */


        if ( usuario.getEstadoCivil() != null && (usuarioFinal.getEstadoCivil() == null || usuario.getEstadoCivil().getCodigo().compareTo( usuarioFinal.getEstadoCivil().getCodigo() ) != 0 )) {         // si se cambia
            TipoEstadoCivil nuevoEstadoCivil = estadoCivilRepository.findByCodigo(usuario.getEstadoCivil().getCodigo());
            usuarioFinal.setEstadoCivil(nuevoEstadoCivil);
        }

        usuarioFinal.setNumeroHijos(usuario.getNumeroHijos());

        usuarioFinal.setVocativo(usuario.getVocativo());


        /*
        TODO BORRAR
        if ( usuario.getEstadoSolicitudCredencial() != null && ( usuarioFinal.getEstadoSolicitudCredencial() == null || usuario.getEstadoSolicitudCredencial().getCodigo().compareTo( usuarioFinal.getEstadoSolicitudCredencial().getCodigo() ) != 0 )) {         // si se cambia
            EstadoSolicitudCredencial nuevoEstadoSolicitud = estadoSolicitudCredencialRepository.findByCodigo(usuario.getEstadoSolicitudCredencial().getCodigo());
            usuarioFinal.setEstadoSolicitudCredencial(nuevoEstadoSolicitud);
        }

        if ( usuario.getOperadorEncargado() != null && ( usuarioFinal.getOperadorEncargado() == null || usuario.getOperadorEncargado().getId().compareTo( usuarioFinal.getOperadorEncargado().getId() ) != 0 )) {         // si se cambia
            Usuario nuevoOperadorACargo = usuarioRepository.findById(usuario.getOperadorEncargado().getId());
            usuarioFinal.setOperadorEncargado(nuevoOperadorACargo);
        }

        if ( usuario.getComoSupoDeRedExalumnos() != null && ( usuarioFinal.getComoSupoDeRedExalumnos() == null || usuario.getComoSupoDeRedExalumnos().getCodigo().compareTo( usuarioFinal.getComoSupoDeRedExalumnos().getCodigo() ) != 0 )) {         // si se cambia
            ComoSupoDeRedExalumnos nuevoComoSupo = comoSupoDeRedExalumnoRepository.findByCodigo(usuario.getComoSupoDeRedExalumnos().getCodigo());
            usuarioFinal.setComoSupoDeRedExalumnos(nuevoComoSupo);
        }
        */


        // TODO "completitudContacto" y "trayectoriaCompleta" son manejados por trigger. Actualmente se mantienen los
        // TODO valores que posee el usuario final

        Date fechaActual = new java.util.Date();
        usuarioFinal.setFechaModificacion(fechaActual);

        usuarioFinal.setRutUsuario(getCurrentUser().getRut());




// TABLAS UNO-A-UNO


    // Sin PK compuesta (eliminacion directa de las entidades que no se seleccionó, y actualización directa a la id del usuario final)

        // ------> Apoderado
        if(usuario.getApoderado() != null) {
            if (usuario.getApoderado() != null && usuario.getApoderado().getUsuarioId().compareTo(idUsuario1) == 0 && usuarioApoderadoRepository.findByUsuarioId(idUsuario2) != null) {
                usuarioApoderadoRepository.eliminar(idUsuario2);
            }
            if (usuario.getApoderado() != null && usuario.getApoderado().getUsuarioId().compareTo(idUsuario2) == 0 && usuarioApoderadoRepository.findByUsuarioId(idUsuario1) != null) {
                usuarioApoderadoRepository.eliminar(idUsuario1);
            }

            usuarioApoderadoRepository.actualizarUsuarioId(usuario.getApoderado().getUsuarioId(), usuarioFinal.getId());
        }



        // ------> InfoProfesionalExalumno
        if(usuario.getInformacionProfesional() != null) {
            if (usuario.getInformacionProfesional().getUsuarioId().compareTo(idUsuario1) == 0 && infoProfesionalExalumnoRepository.findByUsuarioId(idUsuario2) != null) {                              // eliminacion de InfoProfesionalExalumno no seleccionada
                infoProfesionalExalumnoRepository.eliminar(idUsuario2);
            }
            if (usuario.getInformacionProfesional().getUsuarioId().compareTo(idUsuario2) == 0 && infoProfesionalExalumnoRepository.findByUsuarioId(idUsuario1) != null) {                              // eliminacion de InfoProfesionalExalumno no seleccionada
                infoProfesionalExalumnoRepository.eliminar(idUsuario1);
            }

            infoProfesionalExalumnoRepository.actualizarUsuarioId(usuario.getInformacionProfesional().getUsuarioId(), usuarioFinal.getId());

        }



        // ------> PreferenciaUsuarioUsmempleo
        if(usuario.getPreferenciaUsuarioUsmempleo() != null) {
            if (usuario.getPreferenciaUsuarioUsmempleo().getUsuarioId().compareTo(idUsuario1) == 0 && preferenciaUsuarioUsmempleoRepository.findByUsuarioId(idUsuario2) != null) {                          // eliminacion de preferenciaUsuarioUsmempleo no seleccionada
                preferenciaUsuarioUsmempleoRepository.eliminar(idUsuario2);
            }
            if (usuario.getPreferenciaUsuarioUsmempleo().getUsuarioId().compareTo(idUsuario2) == 0 && preferenciaUsuarioUsmempleoRepository.findByUsuarioId(idUsuario1) != null) {                          // eliminacion de preferenciaUsuarioUsmempleo no seleccionada
                preferenciaUsuarioUsmempleoRepository.eliminar(idUsuario1);
            }

            preferenciaUsuarioUsmempleoRepository.actualizarUsuarioId(usuario.getPreferenciaUsuarioUsmempleo().getUsuarioId(), usuarioFinal.getId());
        }



        // ------> Asesor
        if(usuario.getAsesor() != null) {
            if (usuario.getAsesor().getUsuarioId().compareTo(idUsuario1) == 0 && asesorRepository.findByUsuarioId(idUsuario2) != null) {                          // eliminacion de Asesor no seleccionada
                asesorRepository.eliminar(idUsuario2);
            }
            if (usuario.getAsesor().getUsuarioId().compareTo(idUsuario2) == 0 && asesorRepository.findByUsuarioId(idUsuario1) != null) {                          // eliminacion de Asesor no seleccionada
                asesorRepository.eliminar(idUsuario1);
            }

            asesorRepository.actualizarUsuarioId(usuario.getAsesor().getUsuarioId(), usuarioFinal.getId());

        }



        // ------> CredencialAcceso (LoginAexa)
        if(usuario.getCredencialesAcceso() != null) {
            if (usuario.getCredencialesAcceso().getUsuarioId().compareTo(idUsuario1) == 0 && loginAexaRepository.findByUsuarioId(idUsuario2) != null) {                          // eliminacion de Asesor no seleccionada
                loginAexaRepository.eliminar(idUsuario2);
            }
            if (usuario.getCredencialesAcceso().getUsuarioId().compareTo(idUsuario2) == 0 && loginAexaRepository.findByUsuarioId(idUsuario1) != null) {                          // eliminacion de Asesor no seleccionada
                loginAexaRepository.eliminar(idUsuario1);
            }

            loginAexaRepository.actualizarUsuarioId(usuario.getCredencialesAcceso().getUsuarioId(), usuarioFinal.getId());
        }



    // Con PK compuesta (eliminacion directa de las entidades que no se seleccionó. La preferencia privacidad seleccionadas
    // se actualizan de manera que el cambio no produzca conflicto con alguna que pudiera existir anteriormente con el mismo
    // otro atributo que en conjunto forman la clave primaria)

        // ------> PreferenciaPrivacidad

        List<PreferenciaPrivacidad> preferenciaPrivacidadUsuario1 = preferenciaPrivacidadRepository.buscarPorIdUsuario(idUsuario1);
        List<PreferenciaPrivacidad> preferenciaPrivacidadUsuario2 = preferenciaPrivacidadRepository.buscarPorIdUsuario(idUsuario2);
        List<PreferenciaPrivacidad> preferenciaPrivacidadUsuarioFinal;          // lista de objectos que ya posee el usuario que va a quedar


        if(usuario.getPreferenciaPrivacidad() != null) {

            Iterator iterator;

            // solo se cambian los id de las entidades que no sean del usuario final
            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {
                iterator = preferenciaPrivacidadUsuario2.iterator();
                preferenciaPrivacidadUsuarioFinal = preferenciaPrivacidadUsuario1;
            }
            else {
                iterator = preferenciaPrivacidadUsuario1.iterator();
                preferenciaPrivacidadUsuarioFinal = preferenciaPrivacidadUsuario2;
            }

            // cambio de usuario a cada entidad
            while (iterator.hasNext()) {
                existeConflictoConPK = false;

                PreferenciaPrivacidad preferenciaPrivacidad = (PreferenciaPrivacidad) iterator.next();

                // revisa que el cambio de id no producirá conflicto con una llave primaria compuesta que ya exista
                for (PreferenciaPrivacidad preferenciaPrivacidadVerificaConflictoPK : preferenciaPrivacidadUsuarioFinal) {
                    if (preferenciaPrivacidadVerificaConflictoPK.getCodInstitucion().compareTo(preferenciaPrivacidad.getCodInstitucion()) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                // si no existe conflicto se actualiza el id de Usuario
                if (!existeConflictoConPK) {
                    preferenciaPrivacidadRepository.actualizarUsuarioId(preferenciaPrivacidad.getCodInstitucion(), preferenciaPrivacidad.getUsuarioId(), usuarioFinal.getId());
                }
                // si existe conflicto, la elimino
                else {
                    iterator.remove();
                    preferenciaPrivacidadRepository.eliminar(preferenciaPrivacidad.getCodInstitucion(), preferenciaPrivacidad.getUsuarioId());
                }
            }
        }




// TABLAS MUCHOS-A-UNO


     // Sin PK compuesta ( para OperadorContabilidad, quedan los registros del usuario que se seleccionó según el id del usuario seleccionado)
        // TODO revisar si el manejo de esto es correcto

        // ------> UsuarioOperador (registros en los cuales es Usuario es el operado por otro)
        List<OperadorContabilidad> operadorContabilidadUsuario1 = operadorContabilidadRepository.buscarPorIdUsuario(idUsuario1);
        List<OperadorContabilidad> operadorContabilidadUsuario2 = operadorContabilidadRepository.buscarPorIdUsuario(idUsuario2);

        //elimino las del usuario no seleccionado
        if (usuario.getId().compareTo(idUsuario1) == 0) {
            for (OperadorContabilidad operadorContabilidad : operadorContabilidadUsuario2) {
                operadorContabilidadRepository.eliminar(operadorContabilidad.getId());
            }
        }
        if (usuario.getId().compareTo(idUsuario2) == 0) {
            for (OperadorContabilidad operadorContabilidad : operadorContabilidadUsuario1) {
                operadorContabilidadRepository.eliminar(operadorContabilidad.getId());
            }
        }



    // Sin PK compuesta


        // ------> Manejo Idiomas
        List<ManejoIdioma> manejoIdiomasUsuario1 = manejoIdiomasRepository.buscarPorIdUsuario(idUsuario1);
        List<ManejoIdioma> manejoIdiomasUsuario2 = manejoIdiomasRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxManejoIdiomas") != null) {                                                           // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (ManejoIdioma manejoIdioma : manejoIdiomasUsuario2) {
                    manejoIdiomasRepository.actualizarUsuarioId(manejoIdioma.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (ManejoIdioma manejoIdioma : manejoIdiomasUsuario1) {
                    manejoIdiomasRepository.actualizarUsuarioId(manejoIdioma.getId(), usuarioFinal.getId());
                }
            }
        }
        else {
            for (ManejoIdioma manejoIdioma : manejoIdiomasUsuario1) {
                manejoIdiomasRepository.eliminar(manejoIdioma.getId());
            }

            for (ManejoIdioma manejoIdioma : manejoIdiomasUsuario2) {
                manejoIdiomasRepository.eliminar(manejoIdioma.getId());
            }
        }



        // ------> CompromisoSocio
        List<CompromisoSocio> compromisoSocioUsuario1 = compromisoSocioRepository.buscarPorIdUsuario(idUsuario1);
        List<CompromisoSocio> compromisoSocioUsuario2 = compromisoSocioRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxCompromisosUsuario") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (CompromisoSocio compromisoSocio : compromisoSocioUsuario2) {
                    compromisoSocioRepository.actualizarUsuarioId(compromisoSocio.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (CompromisoSocio compromisoSocio : compromisoSocioUsuario1) {
                    compromisoSocioRepository.actualizarUsuarioId(compromisoSocio.getId(), usuarioFinal.getId());
                }
            }
        }
        else {
            for (CompromisoSocio compromisoSocio : compromisoSocioUsuario1) {
                compromisoSocioRepository.eliminar(compromisoSocio.getId());
            }
            for (CompromisoSocio compromisoSocio : compromisoSocioUsuario2) {
                compromisoSocioRepository.eliminar(compromisoSocio.getId());
            }
        }



        // ------> AntecedenteEducacional
        List<AntecedenteEducacional> antecedenteEducacionalUsuario1 = antecedenteEducacionalRepository.buscarPorIdUsuario(idUsuario1);
        List<AntecedenteEducacional> antecedenteEducacionalUsuario2 = antecedenteEducacionalRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxAntecedenteEducacional") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (AntecedenteEducacional antecedenteEducacional : antecedenteEducacionalUsuario2) {
                    antecedenteEducacionalRepository.actualizarUsuarioId(antecedenteEducacional.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (AntecedenteEducacional antecedenteEducacional : antecedenteEducacionalUsuario1) {
                    antecedenteEducacionalRepository.actualizarUsuarioId(antecedenteEducacional.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (AntecedenteEducacional antecedenteEducacional : antecedenteEducacionalUsuario1) {
                antecedenteEducacionalRepository.eliminar(antecedenteEducacional.getId());
            }
            for (AntecedenteEducacional antecedenteEducacional : antecedenteEducacionalUsuario2) {
                antecedenteEducacionalRepository.eliminar(antecedenteEducacional.getId());
            }
        }



        // ------> AntecedenteColegio
        List<AntecedenteColegio> antecedenteColegioUsuario1 = antecedenteColegioRepository.buscarPorIdUsuario(idUsuario1);
        List<AntecedenteColegio> antecedenteColegioUsuario2 = antecedenteColegioRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxAntecedenteColegio") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (AntecedenteColegio antecedenteColegio : antecedenteColegioUsuario2) {
                    antecedenteColegioRepository.actualizarUsuarioId(antecedenteColegio.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (AntecedenteColegio antecedenteColegio : antecedenteColegioUsuario1) {
                    antecedenteColegioRepository.actualizarUsuarioId(antecedenteColegio.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (AntecedenteColegio antecedenteColegio : antecedenteColegioUsuario1) {
                antecedenteColegioRepository.eliminar(antecedenteColegio.getId());
            }
            for (AntecedenteColegio antecedenteColegio : antecedenteColegioUsuario2) {
                antecedenteColegioRepository.eliminar(antecedenteColegio.getId());
            }
        }






        // ------> ArchivosAdjuntos
        List<ArchivosAdjuntos> archivosAdjuntosUsuario1 = archivosAdjuntosRepository.buscarPorIdUsuario(idUsuario1);
        List<ArchivosAdjuntos> archivosAdjuntosUsuario2 = archivosAdjuntosRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxArchivosAdjuntos") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (ArchivosAdjuntos archivoAdjunto : archivosAdjuntosUsuario2) {
                    archivosAdjuntosRepository.actualizarUsuarioId(archivoAdjunto.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (ArchivosAdjuntos archivoAdjunto : archivosAdjuntosUsuario1) {
                    archivosAdjuntosRepository.actualizarUsuarioId(archivoAdjunto.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (ArchivosAdjuntos archivoAdjunto : archivosAdjuntosUsuario1) {
                archivosAdjuntosRepository.eliminar(archivoAdjunto.getId());
            }
            for (ArchivosAdjuntos archivoAdjunto : archivosAdjuntosUsuario2) {
                archivosAdjuntosRepository.eliminar(archivoAdjunto.getId());
            }
        }



        // ------> CampanaExalumno
        List<CampanaExalumno> campanaExalumno1 = campanaExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<CampanaExalumno> campanaExalumno2 = campanaExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxCampanaExalumno") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (CampanaExalumno campanaExalumno : campanaExalumno2) {
                    campanaExalumnoRepository.actualizarUsuarioId(campanaExalumno.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (CampanaExalumno campanaExalumno : campanaExalumno1) {
                    campanaExalumnoRepository.actualizarUsuarioId(campanaExalumno.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (CampanaExalumno campanaExalumno : campanaExalumno1) {
                campanaExalumnoRepository.eliminar(campanaExalumno.getId());
            }

            for (CampanaExalumno campanaExalumno : campanaExalumno2) {
                campanaExalumnoRepository.eliminar(campanaExalumno.getId());
            }
        }



        // ------> StickynotesAexa UsuUsuario ( quien hace el stickynotes)
        List<StickynotesAexa> stickynotesAexaUsuUsuario1 = stickynotesAexaRepository.buscarPorIdUsuUsuario(idUsuario1);
        List<StickynotesAexa> stickynotesAexaUsuUsuario2 = stickynotesAexaRepository.buscarPorIdUsuUsuario(idUsuario2);

        if (checkboxs.get("checkBoxStickynotesAexaUsuUsuario") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (StickynotesAexa stickynotes : stickynotesAexaUsuUsuario2) {
                    stickynotesAexaRepository.actualizarUsuUsuarioId(stickynotes.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (StickynotesAexa stickynotes : stickynotesAexaUsuUsuario1) {
                    stickynotesAexaRepository.actualizarUsuUsuarioId(stickynotes.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (StickynotesAexa stickynotes : stickynotesAexaUsuUsuario1) {
                stickynotesAexaRepository.eliminar(stickynotes.getId());
            }
            for (StickynotesAexa stickynotes : stickynotesAexaUsuUsuario2) {
                stickynotesAexaRepository.eliminar(stickynotes.getId());
            }

        }



        // ------> StickynotesAexa Usuario ( a quien va dirigido el stickynotes)
        List<StickynotesAexa> stickynotesAexaUsuario1 = stickynotesAexaRepository.buscarPorIdUsuario(idUsuario1);
        List<StickynotesAexa> stickynotesAexaUsuario2 = stickynotesAexaRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxStickynotesAexaUsuario") != null) {                                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (StickynotesAexa stickynotes : stickynotesAexaUsuario2) {
                    stickynotesAexaRepository.actualizarUsuarioId(stickynotes.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (StickynotesAexa stickynotes : stickynotesAexaUsuario1) {
                    stickynotesAexaRepository.actualizarUsuarioId(stickynotes.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (StickynotesAexa stickynotes : stickynotesAexaUsuario1) {
                stickynotesAexaRepository.eliminar(stickynotes.getId());
            }
            for (StickynotesAexa stickynotes : stickynotesAexaUsuario2) {
                stickynotesAexaRepository.eliminar(stickynotes.getId());
            }
        }




        // ------> PaginaExalumno
        List<PaginaExalumno> paginaExalumno1 = paginaExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<PaginaExalumno> paginaExalumno2 = paginaExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxPaginasUsuario") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (PaginaExalumno paginaExalumno : paginaExalumno2) {
                    paginaExalumnoRepository.actualizarUsuarioId(paginaExalumno.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (PaginaExalumno paginaExalumno : paginaExalumno1) {
                    paginaExalumnoRepository.actualizarUsuarioId(paginaExalumno.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (PaginaExalumno paginaExalumno : paginaExalumno1) {
                paginaExalumnoRepository.eliminar(paginaExalumno.getId());
            }
            for (PaginaExalumno paginaExalumno : paginaExalumno2) {
                paginaExalumnoRepository.eliminar(paginaExalumno.getId());
            }
        }



        // ------> AutorizacionUsuario
        List<AutorizacionUsuario> autorizacionUsuario1 = autorizacionUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<AutorizacionUsuario> autorizacionUsuario2 = autorizacionUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxAutorizaciones") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (AutorizacionUsuario autorizacionUsuario : autorizacionUsuario2) {
                    autorizacionUsuarioRepository.actualizarUsuarioId(autorizacionUsuario.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (AutorizacionUsuario autorizacionUsuario : autorizacionUsuario1) {
                    autorizacionUsuarioRepository.actualizarUsuarioId(autorizacionUsuario.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (AutorizacionUsuario autorizacionUsuario : autorizacionUsuario1) {
                autorizacionUsuarioRepository.eliminar(autorizacionUsuario.getId());
            }
            for (AutorizacionUsuario autorizacionUsuario : autorizacionUsuario2) {
                autorizacionUsuarioRepository.eliminar(autorizacionUsuario.getId());
            }
        }




        // ------> ConocimientoInfoExalumno
        List<ConocimientoInfoExalumno> conocimientoInformaticoUsuario1 = conocimientoInfoExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<ConocimientoInfoExalumno> conocimientoInformaticoUsuario2 = conocimientoInfoExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxConocimientoInformatico") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (ConocimientoInfoExalumno conocimientoInformatico : conocimientoInformaticoUsuario2) {
                    conocimientoInfoExalumnoRepository.actualizarUsuarioId(conocimientoInformatico.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (ConocimientoInfoExalumno conocimientoInformatico : conocimientoInformaticoUsuario1) {
                    conocimientoInfoExalumnoRepository.actualizarUsuarioId(conocimientoInformatico.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas

            for (ConocimientoInfoExalumno conocimientoInformatico : conocimientoInformaticoUsuario1) {
                conocimientoInfoExalumnoRepository.eliminar(conocimientoInformatico.getId());
            }
            for (ConocimientoInfoExalumno conocimientoInformatico : conocimientoInformaticoUsuario2) {
                conocimientoInfoExalumnoRepository.eliminar(conocimientoInformatico.getId());
            }
        }




        // ------> CapacitacionExalumno
        List<CapacitacionExalumno> capacitacionExalumnoUsuario1 = capacitacionExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<CapacitacionExalumno> capacitacionExalumnoUsuario2 = capacitacionExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxCapacitacionExalumno") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (CapacitacionExalumno capacitacion : capacitacionExalumnoUsuario2) {
                    capacitacionExalumnoRepository.actualizarUsuarioId(capacitacion.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (CapacitacionExalumno capacitacion : capacitacionExalumnoUsuario1) {
                    capacitacionExalumnoRepository.actualizarUsuarioId(capacitacion.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas

            for (CapacitacionExalumno capacitacion : capacitacionExalumnoUsuario1) {
                capacitacionExalumnoRepository.eliminar(capacitacion.getId());
            }
            for (CapacitacionExalumno capacitacion : capacitacionExalumnoUsuario2) {
                capacitacionExalumnoRepository.eliminar(capacitacion.getId());
            }
        }




        // ------> ActividadExalumno
        List<ActividadExalumno> actividadExalumnoUsuario1 = actividadExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<ActividadExalumno> actividadExalumnoUsuario2 = actividadExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxActividadExalumno") != null) {                                                 // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (ActividadExalumno actividadExalumno : actividadExalumnoUsuario2) {
                    actividadExalumnoRepository.actualizarUsuarioId(actividadExalumno.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (ActividadExalumno actividadExalumno : actividadExalumnoUsuario1) {
                    actividadExalumnoRepository.actualizarUsuarioId(actividadExalumno.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (ActividadExalumno actividadExalumno : actividadExalumnoUsuario1) {
                actividadExalumnoRepository.eliminar(actividadExalumno.getId());
            }
            for (ActividadExalumno actividadExalumno : actividadExalumnoUsuario2) {
                actividadExalumnoRepository.eliminar(actividadExalumno.getId());
            }
        }




        // ------> ContactoHistoricoEmpresa
        List<ContactoHistoricoEmpresa> contactoHistoricoEmpresaUsuario1 = contactoHistoricoEmpresaRepository.buscarPorIdUsuario(idUsuario1);
        List<ContactoHistoricoEmpresa> contactoHistoricoEmpresaUsuario2 = contactoHistoricoEmpresaRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxContactoHistoricoEmpresa") != null) {                                                 // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (ContactoHistoricoEmpresa contactoHistorico : contactoHistoricoEmpresaUsuario2) {
                    contactoHistoricoEmpresaRepository.actualizarUsuarioId(contactoHistorico.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (ContactoHistoricoEmpresa contactoHistorico : contactoHistoricoEmpresaUsuario1) {
                    contactoHistoricoEmpresaRepository.actualizarUsuarioId(contactoHistorico.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas

            for (ContactoHistoricoEmpresa contactoHistorico : contactoHistoricoEmpresaUsuario1) {
                contactoHistoricoEmpresaRepository.eliminar(contactoHistorico.getId());
            }

            for (ContactoHistoricoEmpresa contactoHistorico : contactoHistoricoEmpresaUsuario2) {
                contactoHistoricoEmpresaRepository.eliminar(contactoHistorico.getId());
            }
        }




        // ------> InvitacionVideoEntrevistaUsmEmpleo
        List<InvitacionVideoEntrevistaUsmEmpleo> invitacionVideoEntrevistaUsmEmpleoUsuario1 = invitacionVideoEntrevistaUsmEmpleoRepository.buscarPorIdUsuario(idUsuario1);
        List<InvitacionVideoEntrevistaUsmEmpleo> invitacionVideoEntrevistaUsmEmpleoUsuario2 = invitacionVideoEntrevistaUsmEmpleoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxInvitacionVideoEntrevistaUsmEmpleo") != null) {                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (InvitacionVideoEntrevistaUsmEmpleo invitacionVideoEntrevistaUsmEmpleo : invitacionVideoEntrevistaUsmEmpleoUsuario2) {
                    invitacionVideoEntrevistaUsmEmpleoRepository.actualizarUsuarioId(invitacionVideoEntrevistaUsmEmpleo.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (InvitacionVideoEntrevistaUsmEmpleo invitacionVideoEntrevistaUsmEmpleo : invitacionVideoEntrevistaUsmEmpleoUsuario1) {
                    invitacionVideoEntrevistaUsmEmpleoRepository.actualizarUsuarioId(invitacionVideoEntrevistaUsmEmpleo.getId(), usuarioFinal.getId());
                }
            }

        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (InvitacionVideoEntrevistaUsmEmpleo invitacionVideoEntrevistaUsmEmpleo : invitacionVideoEntrevistaUsmEmpleoUsuario1) {
                invitacionVideoEntrevistaUsmEmpleoRepository.eliminar(invitacionVideoEntrevistaUsmEmpleo.getId());
            }
            for (InvitacionVideoEntrevistaUsmEmpleo invitacionVideoEntrevistaUsmEmpleo : invitacionVideoEntrevistaUsmEmpleoUsuario2) {
                invitacionVideoEntrevistaUsmEmpleoRepository.eliminar(invitacionVideoEntrevistaUsmEmpleo.getId());
            }
        }




        // ------> VideoEntrevistaUsmEmpleo
        List<VideoEntrevistaUsmEmpleo> videoEntrevistaUsmEmpleoUsuario1 = videoEntrevistaUsmEmpleoRepository.buscarPorIdUsuario(idUsuario1);
        List<VideoEntrevistaUsmEmpleo> videoEntrevistaUsmEmpleoUsuario2 = videoEntrevistaUsmEmpleoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxVideoEntrevistaUsmEmpleo") != null) {                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (VideoEntrevistaUsmEmpleo videoEntrevista : videoEntrevistaUsmEmpleoUsuario2) {
                    videoEntrevistaUsmEmpleoRepository.actualizarUsuarioId(videoEntrevista.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (VideoEntrevistaUsmEmpleo videoEntrevista : videoEntrevistaUsmEmpleoUsuario1) {
                    videoEntrevistaUsmEmpleoRepository.actualizarUsuarioId(videoEntrevista.getId(), usuarioFinal.getId());
                }
            }

        }
        else {                                                                                                          // si no se selecciono, se borran todas

            for (VideoEntrevistaUsmEmpleo videoEntrevista : videoEntrevistaUsmEmpleoUsuario1) {
                videoEntrevistaUsmEmpleoRepository.eliminar(videoEntrevista.getId());
            }
            for (VideoEntrevistaUsmEmpleo videoEntrevista : videoEntrevistaUsmEmpleoUsuario2) {
                videoEntrevistaUsmEmpleoRepository.eliminar(videoEntrevista.getId());
            }
        }




        // ------> VideoCurriculoUsuario
        List<VideoCurriculoUsuario> videoCurriculoUsuario1 = videoCurriculoUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<VideoCurriculoUsuario> videoCurriculoUsuario2 = videoCurriculoUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxVideoCurriculoUsuario") != null) {                                      // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (VideoCurriculoUsuario videoCurriculo : videoCurriculoUsuario2) {
                    videoCurriculoUsuarioRepository.actualizarUsuarioId(videoCurriculo.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (VideoCurriculoUsuario videoCurriculo : videoCurriculoUsuario1) {
                    videoCurriculoUsuarioRepository.actualizarUsuarioId(videoCurriculo.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (VideoCurriculoUsuario videoCurriculo : videoCurriculoUsuario1) {
                videoCurriculoUsuarioRepository.eliminar(videoCurriculo.getId());
            }
            for (VideoCurriculoUsuario videoCurriculo : videoCurriculoUsuario2) {
                videoCurriculoUsuarioRepository.eliminar(videoCurriculo.getId());
            }
        }




        // ------> AptitudUsuario
        List<AptitudUsuario> aptitudUsuario1 = aptitudUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<AptitudUsuario> aptitudUsuario2 = aptitudUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxAptitudUsuario") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (AptitudUsuario aptitudUsuario : aptitudUsuario2) {
                    aptitudUsuarioRepository.actualizarUsuarioId(aptitudUsuario.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (AptitudUsuario aptitudUsuario : aptitudUsuario1) {
                    aptitudUsuarioRepository.actualizarUsuarioId(aptitudUsuario.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (AptitudUsuario aptitudUsuario : aptitudUsuario1) {
                aptitudUsuarioRepository.eliminar(aptitudUsuario.getId());
            }
            for (AptitudUsuario aptitudUsuario : aptitudUsuario2) {
                aptitudUsuarioRepository.eliminar(aptitudUsuario.getId());
            }
        }





        // ------> FiltroOfertaLaboral
        List<FiltroOfertaLaboral> filtroOfertaLaboralUsuario1 = filtroOfertaLaboralRepository.buscarPorIdUsuario(idUsuario1);
        List<FiltroOfertaLaboral> filtroOfertaLaboralUsuario2 = filtroOfertaLaboralRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxFiltroOfertaLaboral") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (FiltroOfertaLaboral filtroOfertaLaboral : filtroOfertaLaboralUsuario2) {
                    filtroOfertaLaboralRepository.actualizarUsuarioId(filtroOfertaLaboral.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (FiltroOfertaLaboral filtroOfertaLaboral : filtroOfertaLaboralUsuario1) {
                    filtroOfertaLaboralRepository.actualizarUsuarioId(filtroOfertaLaboral.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (FiltroOfertaLaboral filtroOfertaLaboral : filtroOfertaLaboralUsuario1) {
                filtroOfertaLaboralRepository.eliminar(filtroOfertaLaboral.getId());
            }
            for (FiltroOfertaLaboral filtroOfertaLaboral : filtroOfertaLaboralUsuario2) {
                filtroOfertaLaboralRepository.eliminar(filtroOfertaLaboral.getId());
            }
        }




        // ------> PagoAsesoria
        List<PagoAsesoria> pagoAsesoriaUsuario1 = pagoAsesoriaRepository.buscarPorIdUsuario(idUsuario1);
        List<PagoAsesoria> pagoAsesoriaUsuario2 = pagoAsesoriaRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxPagoAsesoria") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (PagoAsesoria pagoAsesoria : pagoAsesoriaUsuario2) {
                    pagoAsesoriaRepository.actualizarUsuarioId(pagoAsesoria.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (PagoAsesoria pagoAsesoria : pagoAsesoriaUsuario1) {
                    pagoAsesoriaRepository.actualizarUsuarioId(pagoAsesoria.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (PagoAsesoria pagoAsesoria : pagoAsesoriaUsuario1) {
                pagoAsesoriaRepository.eliminar(pagoAsesoria.getId());
            }
            for (PagoAsesoria pagoAsesoria : pagoAsesoriaUsuario2) {
                pagoAsesoriaRepository.eliminar(pagoAsesoria.getId());
            }
        }




        // ------> CategoriaAsesoriaUsuario
        List<CategoriaAsesoriaUsuario> categoriaAsesoriaUsuario1 = categoriaAsesoriaUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<CategoriaAsesoriaUsuario> categoriaAsesoriaUsuario2 = categoriaAsesoriaUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxCategoriaAsesoriaUsuario") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (CategoriaAsesoriaUsuario categoriaAsesoriaUsuario : categoriaAsesoriaUsuario2) {
                    categoriaAsesoriaUsuarioRepository.actualizarUsuarioId(categoriaAsesoriaUsuario.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (CategoriaAsesoriaUsuario categoriaAsesoriaUsuario : categoriaAsesoriaUsuario1) {
                    categoriaAsesoriaUsuarioRepository.actualizarUsuarioId(categoriaAsesoriaUsuario.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (CategoriaAsesoriaUsuario categoriaAsesoriaUsuario : categoriaAsesoriaUsuario1) {
                categoriaAsesoriaUsuarioRepository.eliminar(categoriaAsesoriaUsuario.getId());
            }
            for (CategoriaAsesoriaUsuario categoriaAsesoriaUsuario : categoriaAsesoriaUsuario2) {
                categoriaAsesoriaUsuarioRepository.eliminar(categoriaAsesoriaUsuario.getId());
            }
        }




        // ------> TestPsicologicoExalumno
        List<TestPsicologicoExalumno> testPsicologicoUsuario1 = testPsicologicoExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<TestPsicologicoExalumno> testPsicologicoUsuario2 = testPsicologicoExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxTestPsicologicoExalumno") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (TestPsicologicoExalumno testPsicologicoExalumno : testPsicologicoUsuario2) {
                    testPsicologicoExalumnoRepository.actualizarUsuarioId(testPsicologicoExalumno.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (TestPsicologicoExalumno testPsicologicoExalumno : testPsicologicoUsuario1) {
                    testPsicologicoExalumnoRepository.actualizarUsuarioId(testPsicologicoExalumno.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (TestPsicologicoExalumno testPsicologicoExalumno : testPsicologicoUsuario1) {
                testPsicologicoExalumnoRepository.eliminar(testPsicologicoExalumno.getId());
            }
            for (TestPsicologicoExalumno testPsicologicoExalumno : testPsicologicoUsuario2) {
                testPsicologicoExalumnoRepository.eliminar(testPsicologicoExalumno.getId());
            }
        }





        // ------> OfertaCrawled
        List<OfertaCrawled> ofertaCrawledUsuario1 = ofertaCrawledRepository.buscarPorIdUsuario(idUsuario1);
        List<OfertaCrawled> ofertaCrawledUsuario2 = ofertaCrawledRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxOfertasCrawled") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (OfertaCrawled ofertaCrawled : ofertaCrawledUsuario2) {
                    ofertaCrawledRepository.actualizarUsuarioId(ofertaCrawled.getId(), usuarioFinal.getId());
                }
            }
            else {
                for (OfertaCrawled ofertaCrawled : ofertaCrawledUsuario1) {
                    ofertaCrawledRepository.actualizarUsuarioId(ofertaCrawled.getId(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (OfertaCrawled ofertaCrawled : ofertaCrawledUsuario1) {
                ofertaCrawledRepository.eliminar(ofertaCrawled.getId());
            }
            for (OfertaCrawled ofertaCrawled : ofertaCrawledUsuario2) {
                ofertaCrawledRepository.eliminar(ofertaCrawled.getId());
            }
        }





        // ------> CorreosValidados
        // TODO terminar, segun requerimiento hecho al Hector
        List<CorreosValidados> correosValidadosUsuario1 = correosValidadosRepository.buscarPorIdUsuario(idUsuario1);
        List<CorreosValidados> correosValidadosUsuario2 = correosValidadosRepository.buscarPorIdUsuario(idUsuario2);

        if (checkboxs.get("checkBoxCorreosValidados") != null) {                                                          // caso que se seleccion el checkbox

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                for (CorreosValidados correoValidado : correosValidadosUsuario2) {
                    correosValidadosRepository.actualizarUsuarioId(correoValidado.getIdUsuario(), usuarioFinal.getId());
                }
            }
            else {
                for (CorreosValidados correoValidado : correosValidadosUsuario1) {
                    correosValidadosRepository.actualizarUsuarioId(correoValidado.getIdUsuario(), usuarioFinal.getId());
                }
            }
        }
        else {                                                                                                          // si no se selecciono, se borran todas
            for (CorreosValidados correoValidado : correosValidadosUsuario1) {
                correosValidadosRepository.eliminar(correoValidado.getIdUsuario());
            }
            for (CorreosValidados correoValidado : correosValidadosUsuario2) {
                correosValidadosRepository.eliminar(correoValidado.getIdUsuario());
            }
        }






    // Con PK compuesta ( es necesario ver si al cambiar el usuario, se va a generar conflicto,. Puede que ya exista una
    // PK con los valores nuevos que se desean guardar al mezclar )



        // ------> PostulacionOfertaLaboralUsmempleo
        // Se obtienen las postulaciones y a su vez las encuestas, archivos adjuntos y respuestas asociadas a las postulaciones
        // haciendose una copia de ellas, eliminandose las originales y tras lo cual se setean el nuevo id de usuario
        // (actualiza en cascada, por eso es distinto el manejo de con respecto a las otras entidades con PK compuesta)

        List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoUsuario1 = postulacionOfertaLaboralUsmempleoRepository.buscarPorIdUsuario(idUsuario1);
        List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsmempleoUsuario2 = postulacionOfertaLaboralUsmempleoRepository.buscarPorIdUsuario(idUsuario2);

        List<PostulacionOfertaLaboralUsmempleo> postulacionOfertaLaboralUsuarioFinal;                                   // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxPostulacionOfertaLaboral") != null) {                                                // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = postulacionOfertaLaboralUsmempleoUsuario2.iterator();
                postulacionOfertaLaboralUsuarioFinal = postulacionOfertaLaboralUsmempleoUsuario1;
            }
            else {
                iterator = postulacionOfertaLaboralUsmempleoUsuario1.iterator();
                postulacionOfertaLaboralUsuarioFinal = postulacionOfertaLaboralUsmempleoUsuario2;
            }

            while (iterator.hasNext()) {                                                              // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboral = (PostulacionOfertaLaboralUsmempleo) iterator.next();

                EncuestaPostulacionLaboral encuestaPostulacionAsociadaABorrar = encuestaPostulacionLaboralRepository.buscarPorIdUsuarioYIdOfertaLaboral(postulacionOfertaLaboral.getUsuarioId(), postulacionOfertaLaboral.getOfertaLaboralUsmempleoId());  // obtencion de encuesta asociada (si la hubiese)
                List<RespuestaPreguntaOfertaLaboralExalumno> listaRespuestaPreguntaAsociadaABorrar = respuestaPreguntaOfertaLaboralRepository.buscarPorIdUsuarioYIdOfertaLaboral(postulacionOfertaLaboral.getUsuarioId(), postulacionOfertaLaboral.getOfertaLaboralUsmempleoId());  // obtencion de RespuestaPreguntaOfertaLaboralExalumno asociada (si la hubiese)
                List<PostulacionArchivosAdjuntos> listaPostulacionArchivosAsociadaABorrar = postulacionArchivosAdjuntosRepository.buscarPorIdUsuarioYIdOfertaLaboral(postulacionOfertaLaboral.getUsuarioId(), postulacionOfertaLaboral.getOfertaLaboralUsmempleoId());  // obtencion de PostulacionArchivosAdjuntos asociada (si la hubiese)


                for (PostulacionOfertaLaboralUsmempleo postulacionVerificaConflictoConPK : postulacionOfertaLaboralUsuarioFinal) { // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (postulacionVerificaConflictoConPK.getOfertaLaboralUsmempleoId().compareTo( postulacionOfertaLaboral.getOfertaLaboralUsmempleoId() ) == 0 ) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    PostulacionOfertaLaboralUsmempleo postulacionAsociadaAModificar = new PostulacionOfertaLaboralUsmempleo(postulacionOfertaLaboral);
                    EncuestaPostulacionLaboral encuestaPostulacionAsociadaAModificar = new EncuestaPostulacionLaboral();

                    List<RespuestaPreguntaOfertaLaboralExalumno> listaRespuestaPreguntaAsociadaAModificar = new ArrayList<RespuestaPreguntaOfertaLaboralExalumno>();

                    List<PostulacionArchivosAdjuntos> listaPostulacionArchivosAsociadaAModificar = new ArrayList<PostulacionArchivosAdjuntos>();

                    // copia de encuesta asociada
                    if (encuestaPostulacionAsociadaABorrar != null) {
                        encuestaPostulacionAsociadaAModificar = new EncuestaPostulacionLaboral(encuestaPostulacionAsociadaABorrar);
                        encuestaPostulacionLaboralRepository.eliminar(encuestaPostulacionAsociadaABorrar.getIdOfertaLaboralUsmempleo(), encuestaPostulacionAsociadaABorrar.getIdUsuario());
                    }

                    // copia de respuestas preguntas asociadas
                    if (listaRespuestaPreguntaAsociadaABorrar != null) {

                        for (RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta : listaRespuestaPreguntaAsociadaABorrar) {
                            RespuestaPreguntaOfertaLaboralExalumno respuestaPreguntaAsociadaAModificar = new RespuestaPreguntaOfertaLaboralExalumno(respuestaPregunta);

                            if (respuestaPreguntaOfertaLaboralRepository.buscarPorIdUsuarioYIdOfertaLaboralYIdPregunta(usuarioFinal.getId(), respuestaPreguntaAsociadaAModificar.getIdOfertaLaboralUsmEmpleo(), respuestaPreguntaAsociadaAModificar.getIdPreguntaOfertaLaboralUsmEmpleo()) == null) {    // si no hay conflicto se agrega
                                listaRespuestaPreguntaAsociadaAModificar.add(respuestaPreguntaAsociadaAModificar);
                            }

                            respuestaPreguntaOfertaLaboralRepository.eliminar(respuestaPregunta.getIdUsuario(), respuestaPregunta.getIdOfertaLaboralUsmEmpleo(), respuestaPregunta.getIdPreguntaOfertaLaboralUsmEmpleo());
                        }
                    }

                    // copia de postulacion archivos adjuntos asociadas
                    if (listaPostulacionArchivosAsociadaABorrar != null) {

                        for (PostulacionArchivosAdjuntos postulacionArchivo : listaPostulacionArchivosAsociadaABorrar) {
                            PostulacionArchivosAdjuntos postulacionArchivoAsociadaAModificar = new PostulacionArchivosAdjuntos(postulacionArchivo);

                            if (postulacionArchivosAdjuntosRepository.buscarPorIdUsuarioYIdOfertaLaboralYIdArchivoAdjunto(usuarioFinal.getId(), postulacionArchivoAsociadaAModificar.getIdOfertaLaboralUsmempleo(), postulacionArchivoAsociadaAModificar.getIdArchivoAdjunto()) == null) {    // si no hay conflicto se agrega
                                listaPostulacionArchivosAsociadaAModificar.add(postulacionArchivoAsociadaAModificar);
                            }

                            postulacionArchivosAdjuntosRepository.eliminar(postulacionArchivo.getIdUsuario(), postulacionArchivo.getIdOfertaLaboralUsmempleo(), postulacionArchivo.getIdArchivoAdjunto());
                        }
                    }

                    // eliminacion de la postulacion
                    postulacionOfertaLaboralUsmempleoRepository.eliminar(postulacionOfertaLaboral.getOfertaLaboralUsmempleoId(), postulacionOfertaLaboral.getUsuarioId());

                    // seteo y guardado de nueva postulacion con id del usuario final
                    postulacionAsociadaAModificar.setUsuarioId(usuarioFinal.getId());
                    postulacionOfertaLaboralUsmempleoRepository.save(postulacionAsociadaAModificar);

                    // seteo de id usuario a encuesta asociada
                    if (encuestaPostulacionAsociadaABorrar != null) {
                        encuestaPostulacionAsociadaAModificar.setIdUsuario(usuarioFinal.getId());
                        encuestaPostulacionLaboralRepository.save(encuestaPostulacionAsociadaAModificar);
                    }

                    // seteo de id usuario a respuestas pregunta asociada
                    if (listaRespuestaPreguntaAsociadaABorrar != null) {
                        for (RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta : listaRespuestaPreguntaAsociadaAModificar) {
                            respuestaPregunta.setIdUsuario(usuarioFinal.getId());
                            respuestaPreguntaOfertaLaboralRepository.save(respuestaPregunta);
                        }
                    }

                    // seteo de id usuario a postulacion archivos adjuntos asociada
                    if (listaPostulacionArchivosAsociadaABorrar != null) {
                        for (PostulacionArchivosAdjuntos postulacionArchivo : listaPostulacionArchivosAsociadaAModificar) {
                            postulacionArchivo.setIdUsuario(usuarioFinal.getId());
                            postulacionArchivosAdjuntosRepository.save(postulacionArchivo);
                        }
                    }

                }
                else {                                                                                                  // si existe conflicto, se elimina postulacion y asociados
                    // eliminacion de encuestas asociadas
                    if (encuestaPostulacionAsociadaABorrar != null) {
                        encuestaPostulacionLaboralRepository.eliminar(encuestaPostulacionAsociadaABorrar.getIdOfertaLaboralUsmempleo(), encuestaPostulacionAsociadaABorrar.getIdUsuario());

                    }

                    // eliminacion de respuestas pregunta asociadas
                    if (listaRespuestaPreguntaAsociadaABorrar != null) {
                        for (RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta : listaRespuestaPreguntaAsociadaABorrar) {
                            respuestaPreguntaOfertaLaboralRepository.eliminar(respuestaPregunta.getIdUsuario(), respuestaPregunta.getIdOfertaLaboralUsmEmpleo(), respuestaPregunta.getIdPreguntaOfertaLaboralUsmEmpleo());         // eliminacion de las respuesta

                        }


                        // copia de respuestas preguntas asociadas
                        for (RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta : listaRespuestaPreguntaAsociadaABorrar) {
                            RespuestaPreguntaOfertaLaboralExalumno respuestaPreguntaAsociadaAModificar = new RespuestaPreguntaOfertaLaboralExalumno(respuestaPregunta);

                            if (respuestaPreguntaOfertaLaboralRepository.buscarPorIdUsuarioYIdOfertaLaboralYIdPregunta(usuarioFinal.getId(), respuestaPreguntaAsociadaAModificar.getIdOfertaLaboralUsmEmpleo(), respuestaPreguntaAsociadaAModificar.getIdPreguntaOfertaLaboralUsmEmpleo()) == null) {    // si no hay conflicto se agrega
                                respuestaPreguntaAsociadaAModificar.setIdUsuario(usuarioFinal.getId());
                                respuestaPreguntaOfertaLaboralRepository.save(respuestaPreguntaAsociadaAModificar);
                            }
                        }
                    }

                    // eliminacion de postulacion archivos adjuntos asociadas
                    if (listaPostulacionArchivosAsociadaABorrar != null) {
                        for (PostulacionArchivosAdjuntos postulacionArchivo : listaPostulacionArchivosAsociadaABorrar) {
                            postulacionArchivosAdjuntosRepository.eliminar(postulacionArchivo.getIdUsuario(), postulacionArchivo.getIdOfertaLaboralUsmempleo(), postulacionArchivo.getIdArchivoAdjunto());         // eliminacion de las postulacion archivos
                        }

                        // copia de postulacion archivos adjuntos asociadas
                        for (PostulacionArchivosAdjuntos postulacionArchivo : listaPostulacionArchivosAsociadaABorrar) {
                            PostulacionArchivosAdjuntos postulacionArchivoAsociadaAModificar = new PostulacionArchivosAdjuntos(postulacionArchivo);

                            if (postulacionArchivosAdjuntosRepository.buscarPorIdUsuarioYIdOfertaLaboralYIdArchivoAdjunto(usuarioFinal.getId(), postulacionArchivoAsociadaAModificar.getIdOfertaLaboralUsmempleo(), postulacionArchivoAsociadaAModificar.getIdArchivoAdjunto()) == null) {    // si no hay conflicto se agrega
                                postulacionArchivoAsociadaAModificar.setIdUsuario(usuarioFinal.getId());
                                postulacionArchivosAdjuntosRepository.save(postulacionArchivoAsociadaAModificar);
                            }
                        }
                    }

                    iterator.remove();

                    // eliminacion de la postulacion
                    postulacionOfertaLaboralUsmempleoRepository.eliminar(postulacionOfertaLaboral.getOfertaLaboralUsmempleoId(), postulacionOfertaLaboral.getUsuarioId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas (borrado en cascada)
            for (PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboral : postulacionOfertaLaboralUsmempleoUsuario1) {
                postulacionOfertaLaboralUsmempleoRepository.eliminar(postulacionOfertaLaboral.getOfertaLaboralUsmempleoId(), postulacionOfertaLaboral.getUsuarioId());
            }
            for (PostulacionOfertaLaboralUsmempleo postulacionOfertaLaboral : postulacionOfertaLaboralUsmempleoUsuario2) {
                postulacionOfertaLaboralUsmempleoRepository.eliminar(postulacionOfertaLaboral.getOfertaLaboralUsmempleoId(), postulacionOfertaLaboral.getUsuarioId());
            }
        }



        // caso que no se selecciona el checkbox de RespuestaPreguntaOfertaLaboralExalumno (caso seleccionado manejado anteriormente)
        if (checkboxs.get("checkBoxRespuestaPreguntaOfertaLaboral") == null) {
            List<RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaUsuario1 = respuestaPreguntaOfertaLaboralRepository.buscarPorIdUsuario(idUsuario1);
            List<RespuestaPreguntaOfertaLaboralExalumno> respuestaPreguntaUsuario2 = respuestaPreguntaOfertaLaboralRepository.buscarPorIdUsuario(idUsuario2);


            for (RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta : respuestaPreguntaUsuario1) {
                respuestaPreguntaOfertaLaboralRepository.eliminar(respuestaPregunta.getIdUsuario(), respuestaPregunta.getIdOfertaLaboralUsmEmpleo(), respuestaPregunta.getIdPreguntaOfertaLaboralUsmEmpleo());         // eliminacion de las respuesta
            }
            for (RespuestaPreguntaOfertaLaboralExalumno respuestaPregunta : respuestaPreguntaUsuario2) {
                respuestaPreguntaOfertaLaboralRepository.eliminar(respuestaPregunta.getIdUsuario(), respuestaPregunta.getIdOfertaLaboralUsmEmpleo(), respuestaPregunta.getIdPreguntaOfertaLaboralUsmEmpleo());         // eliminacion de las respuesta
            }
        }

        // caso que no se selecciona el checkbox de PostulacionArchivosAdjuntos (caso seleccionado manejado anteriormente)
        if (checkboxs.get("checkBoxPostulacionArchivosAdjuntos") == null) {
            List <PostulacionArchivosAdjuntos> postulacionArchivosAdjuntosUsuario1 = postulacionArchivosAdjuntosRepository.buscarPorIdUsuario(idUsuario1);
            List <PostulacionArchivosAdjuntos> postulacionArchivosAdjuntosUsuario2 = postulacionArchivosAdjuntosRepository.buscarPorIdUsuario(idUsuario2);


            for (PostulacionArchivosAdjuntos postulacionArchivo : postulacionArchivosAdjuntosUsuario1) {
                postulacionArchivosAdjuntosRepository.eliminar(postulacionArchivo.getIdUsuario(), postulacionArchivo.getIdOfertaLaboralUsmempleo(), postulacionArchivo.getIdArchivoAdjunto());         // eliminacion de las postulacion archivos
            }
            for (PostulacionArchivosAdjuntos postulacionArchivo : postulacionArchivosAdjuntosUsuario2) {
                postulacionArchivosAdjuntosRepository.eliminar(postulacionArchivo.getIdUsuario(), postulacionArchivo.getIdOfertaLaboralUsmempleo(), postulacionArchivo.getIdArchivoAdjunto());         // eliminacion de las postulacion archivos
            }
        }






        // ------> AccesoUsuarioSistema (PerfilAexa)
        List<AccesoSistema> accesoSistemaUsuario1 = accesoSistemaRepository.buscarPorIdUsuario(idUsuario1);
        List<AccesoSistema> accesoSistemaUsuario2 = accesoSistemaRepository.buscarPorIdUsuario(idUsuario2);

        List<AccesoSistema> accesoSistemaUsuarioFinal;                                                    // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxAccesosSistemas") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = accesoSistemaUsuario2.iterator();
                accesoSistemaUsuarioFinal = accesoSistemaUsuario1;
            }
            else {
                iterator = accesoSistemaUsuario1.iterator();
                accesoSistemaUsuarioFinal = accesoSistemaUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                AccesoSistema accesoSistema = (AccesoSistema) iterator.next();

                for (AccesoSistema accesoVerificaConflictoConPK : accesoSistemaUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (accesoVerificaConflictoConPK.getCompositeId().getCodigoSistema().compareTo(accesoSistema.getCompositeId().getCodigoSistema() ) == 0 ) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    accesoSistemaRepository.actualizarUsuarioId(accesoSistema.getCompositeId().getCodigoSistema(), accesoSistema.getCompositeId().getUsuarioId(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    accesoSistemaRepository.eliminar(accesoSistema.getCompositeId().getCodigoSistema(), accesoSistema.getCompositeId().getUsuarioId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (AccesoSistema accesoSistema : accesoSistemaUsuario1) {
                accesoSistemaRepository.eliminar(accesoSistema.getCompositeId().getCodigoSistema(), accesoSistema.getCompositeId().getUsuarioId());
            }
            for (AccesoSistema accesoSistema : accesoSistemaUsuario2) {
                accesoSistemaRepository.eliminar(accesoSistema.getCompositeId().getCodigoSistema(), accesoSistema.getCompositeId().getUsuarioId());
            }
        }




        // ------> CompetenciaExalumno
        List<CompetenciaExalumno> competenciaExalumnoUsuario1 = competenciaExalumnoRepository.buscarPorIdUsuario(idUsuario1);
        List<CompetenciaExalumno> competenciaExalumnoUsuario2 = competenciaExalumnoRepository.buscarPorIdUsuario(idUsuario2);

        List<CompetenciaExalumno> competenciaExalumnoUsuarioFinal;                                                          // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxCompetencias") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = competenciaExalumnoUsuario2.iterator();
                competenciaExalumnoUsuarioFinal = competenciaExalumnoUsuario1;
            }
            else {
                iterator = competenciaExalumnoUsuario1.iterator();
                competenciaExalumnoUsuarioFinal = competenciaExalumnoUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                CompetenciaExalumno competenciaExalumno = (CompetenciaExalumno) iterator.next();

                for (CompetenciaExalumno competenciaVerificaConflictoConPK : competenciaExalumnoUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (competenciaVerificaConflictoConPK.getNivelCompetenciaUsmempleoId().compareTo(competenciaExalumno.getNivelCompetenciaUsmempleoId() ) == 0 ) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    competenciaExalumnoRepository.actualizarUsuarioId(competenciaExalumno.getNivelCompetenciaUsmempleoId(), competenciaExalumno.getUsuarioId(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    competenciaExalumnoRepository.eliminar(competenciaExalumno.getNivelCompetenciaUsmempleoId(), competenciaExalumno.getUsuarioId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (CompetenciaExalumno competenciaExalumno : competenciaExalumnoUsuario1) {
                competenciaExalumnoRepository.eliminar(competenciaExalumno.getNivelCompetenciaUsmempleoId(), competenciaExalumno.getUsuarioId());
            }
            for (CompetenciaExalumno competenciaExalumno : competenciaExalumnoUsuario2) {
                competenciaExalumnoRepository.eliminar(competenciaExalumno.getNivelCompetenciaUsmempleoId(), competenciaExalumno.getUsuarioId());
            }
        }




        // ------> AporteSocio
        List<AporteSocio> aporteSocioUsuario1 = aporteSocioRepository.buscarPorIdUsuario(idUsuario1);
        List<AporteSocio> aporteSocioUsuario2 = aporteSocioRepository.buscarPorIdUsuario(idUsuario2);

        List<AporteSocio> aporteSocioUsuarioFinal;                                                          // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxAporteSocio") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = aporteSocioUsuario2.iterator();
                aporteSocioUsuarioFinal = aporteSocioUsuario1;
            }
            else {
                iterator = aporteSocioUsuario1.iterator();
                aporteSocioUsuarioFinal = aporteSocioUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                AporteSocio aporteSocio = (AporteSocio) iterator.next();

                for (AporteSocio aporteVerificaConflictoConPK : aporteSocioUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (aporteVerificaConflictoConPK.getIdCompromisoSocio().compareTo(aporteSocio.getIdCompromisoSocio() ) == 0 && aporteVerificaConflictoConPK.getFecha().compareTo(aporteSocio.getFecha() ) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    aporteSocioRepository.actualizarUsuarioId(aporteSocio.getIdCompromisoSocio(), aporteSocio.getFecha(), aporteSocio.getIdUsuario(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    aporteSocioRepository.eliminar(aporteSocio.getIdCompromisoSocio(), aporteSocio.getFecha(), aporteSocio.getIdUsuario());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (AporteSocio aporteSocio : aporteSocioUsuario1) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                aporteSocioRepository.eliminar(aporteSocio.getIdCompromisoSocio(), aporteSocio.getFecha(), aporteSocio.getIdUsuario());
            }
            for (AporteSocio aporteSocio : aporteSocioUsuario2) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                aporteSocioRepository.eliminar(aporteSocio.getIdCompromisoSocio(), aporteSocio.getFecha(), aporteSocio.getIdUsuario());
            }
        }




        // ------> UsuarioVistoUsmEmpleo
        List<UsuarioVistoUsmEmpleo> usuarioVistoUsmEmpleoUsuario1 = usuarioVistoUsmempleoRepository.buscarPorIdUsuario(idUsuario1);
        List<UsuarioVistoUsmEmpleo> usuarioVistoUsmEmpleoUsuario2 = usuarioVistoUsmempleoRepository.buscarPorIdUsuario(idUsuario2);

        List<UsuarioVistoUsmEmpleo> usuarioVistoUsmEmpleoFinal;                                                          // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxUsuarioVistoUsmEmpleo") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = usuarioVistoUsmEmpleoUsuario2.iterator();
                usuarioVistoUsmEmpleoFinal = usuarioVistoUsmEmpleoUsuario1;
            }
            else {
                iterator = usuarioVistoUsmEmpleoUsuario1.iterator();
                usuarioVistoUsmEmpleoFinal = usuarioVistoUsmEmpleoUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                UsuarioVistoUsmEmpleo usuarioVistoUsmEmpleo = (UsuarioVistoUsmEmpleo) iterator.next();

                for (UsuarioVistoUsmEmpleo usuarioVistoVerificaConflictoConPK : usuarioVistoUsmEmpleoFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (usuarioVistoVerificaConflictoConPK.getIdEmpresa().compareTo(usuarioVistoUsmEmpleo.getIdEmpresa() ) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    usuarioVistoUsmempleoRepository.actualizarUsuarioId(usuarioVistoUsmEmpleo.getIdEmpresa(), usuarioVistoUsmEmpleo.getIdUsuario(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    usuarioVistoUsmempleoRepository.eliminar(usuarioVistoUsmEmpleo.getIdEmpresa(), usuarioVistoUsmEmpleo.getIdUsuario());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (UsuarioVistoUsmEmpleo usuarioVistoUsmEmpleo : usuarioVistoUsmEmpleoUsuario1) {
                usuarioVistoUsmempleoRepository.eliminar(usuarioVistoUsmEmpleo.getIdEmpresa(), usuarioVistoUsmEmpleo.getIdUsuario());
            }
            for (UsuarioVistoUsmEmpleo usuarioVistoUsmEmpleo : usuarioVistoUsmEmpleoUsuario2) {
                usuarioVistoUsmempleoRepository.eliminar(usuarioVistoUsmEmpleo.getIdEmpresa(), usuarioVistoUsmEmpleo.getIdUsuario());
            }
        }




        // ------> DuenoEmpresa
        List<DuenoEmpresa> duenoEmpresaUsuario1 = duenoEmpresaRepository.buscarPorIdUsuario(idUsuario1);
        List<DuenoEmpresa> duenoEmpresaUsuario2 = duenoEmpresaRepository.buscarPorIdUsuario(idUsuario2);

        List<DuenoEmpresa> duenoEmpresaUsuarioFinal;                                                          // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxDuenoEmpresa") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = duenoEmpresaUsuario2.iterator();
                duenoEmpresaUsuarioFinal = duenoEmpresaUsuario1;
            }
            else {
                iterator = duenoEmpresaUsuario1.iterator();
                duenoEmpresaUsuarioFinal = duenoEmpresaUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                DuenoEmpresa duenoEmpresa = (DuenoEmpresa) iterator.next();

                for (DuenoEmpresa duenoVerificaConflictoConPK : duenoEmpresaUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (duenoVerificaConflictoConPK.getEmpresaId().compareTo(duenoEmpresa.getEmpresaId() ) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    duenoEmpresaRepository.actualizarUsuarioId(duenoEmpresa.getEmpresaId(), duenoEmpresa.getUsuarioId(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    duenoEmpresaRepository.eliminar(duenoEmpresa.getEmpresaId(), duenoEmpresa.getUsuarioId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (DuenoEmpresa duenoEmpresa : duenoEmpresaUsuario1) {
                duenoEmpresaRepository.eliminar(duenoEmpresa.getEmpresaId(), duenoEmpresa.getUsuarioId());
            }
            for (DuenoEmpresa duenoEmpresa : duenoEmpresaUsuario2) {
                duenoEmpresaRepository.eliminar(duenoEmpresa.getEmpresaId(), duenoEmpresa.getUsuarioId());
            }
        }




        // ------> PostulanteFavorito
        List<PostulanteFavorito> postulanteFavoritoUsuario1 = postulanteFavoritoRepository.buscarPorIdUsuario(idUsuario1);
        List<PostulanteFavorito> postulanteFavoritoUsuario2 = postulanteFavoritoRepository.buscarPorIdUsuario(idUsuario2);

        List<PostulanteFavorito> postulanteFavoritoUsuarioFinal;                                                          // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxPostulanteFavorito") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = postulanteFavoritoUsuario2.iterator();
                postulanteFavoritoUsuarioFinal = postulanteFavoritoUsuario1;
            }
            else {
                iterator = postulanteFavoritoUsuario1.iterator();
                postulanteFavoritoUsuarioFinal = postulanteFavoritoUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                PostulanteFavorito postulanteFavorito = (PostulanteFavorito) iterator.next();

                for (PostulanteFavorito postulantesVerificaConflictoConPK : postulanteFavoritoUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (postulantesVerificaConflictoConPK.getIdUsuarioEmpresaUsmempleo().compareTo(postulanteFavorito.getIdUsuarioEmpresaUsmempleo() ) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    postulanteFavoritoRepository.actualizarUsuarioId(postulanteFavorito.getIdUsuarioEmpresaUsmempleo(), postulanteFavorito.getIdUsuario(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    postulanteFavoritoRepository.eliminar(postulanteFavorito.getIdUsuarioEmpresaUsmempleo(), postulanteFavorito.getIdUsuario());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (PostulanteFavorito postulanteFavorito : postulanteFavoritoUsuario1) {
                postulanteFavoritoRepository.eliminar(postulanteFavorito.getIdUsuarioEmpresaUsmempleo(), postulanteFavorito.getIdUsuario());
            }
            for (PostulanteFavorito postulanteFavorito : postulanteFavoritoUsuario2) {
                postulanteFavoritoRepository.eliminar(postulanteFavorito.getIdUsuarioEmpresaUsmempleo(), postulanteFavorito.getIdUsuario());
            }
        }




        // ------> PostulacionUsuarioAsesoria
        List<PostulacionUsuarioAsesoria> postulacionUsuarioAsesoriaUsuario1 = postulacionUsuarioAsesoriaRepository.buscarPorIdUsuario(idUsuario1);
        List<PostulacionUsuarioAsesoria> postulacionUsuarioAsesoriaUsuario2 = postulacionUsuarioAsesoriaRepository.buscarPorIdUsuario(idUsuario2);

        List<PostulacionUsuarioAsesoria> postulacionUsuarioAsesoriaUsuarioFinal;                                        // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxPostulacionUsuarioAsesoria") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = postulacionUsuarioAsesoriaUsuario2.iterator();
                postulacionUsuarioAsesoriaUsuarioFinal = postulacionUsuarioAsesoriaUsuario1;
            }
            else {
                iterator = postulacionUsuarioAsesoriaUsuario1.iterator();
                postulacionUsuarioAsesoriaUsuarioFinal = postulacionUsuarioAsesoriaUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                PostulacionUsuarioAsesoria postulacionUsuarioAsesoria = (PostulacionUsuarioAsesoria) iterator.next();

                for (PostulacionUsuarioAsesoria postulacionVerificaConflictoConPK : postulacionUsuarioAsesoriaUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (postulacionVerificaConflictoConPK.getIdOfertaAsesoria().compareTo(postulacionUsuarioAsesoria.getIdOfertaAsesoria() ) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    postulacionUsuarioAsesoriaRepository.actualizarUsuarioId(postulacionUsuarioAsesoria.getIdOfertaAsesoria(), postulacionUsuarioAsesoria.getIdUsuario(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    postulacionUsuarioAsesoriaRepository.eliminar(postulacionUsuarioAsesoria.getIdOfertaAsesoria(), postulacionUsuarioAsesoria.getIdUsuario());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (PostulacionUsuarioAsesoria postulacionUsuarioAsesoria : postulacionUsuarioAsesoriaUsuario1) {
                postulacionUsuarioAsesoriaRepository.eliminar(postulacionUsuarioAsesoria.getIdOfertaAsesoria(), postulacionUsuarioAsesoria.getIdUsuario());
            }
            for (PostulacionUsuarioAsesoria postulacionUsuarioAsesoria : postulacionUsuarioAsesoriaUsuario2) {
                postulacionUsuarioAsesoriaRepository.eliminar(postulacionUsuarioAsesoria.getIdOfertaAsesoria(), postulacionUsuarioAsesoria.getIdUsuario());
            }
        }




        // ------> RespuestaUsuario
        List<RespuestaUsuario> respuestaUsuario1 = respuestaUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<RespuestaUsuario> respuestaUsuario2 = respuestaUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        List<RespuestaUsuario> respuestaUsuarioFinal;                                        // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxRespuestaUsuario") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = respuestaUsuario2.iterator();
                respuestaUsuarioFinal = respuestaUsuario1;
            }
            else {
                iterator = respuestaUsuario1.iterator();
                respuestaUsuarioFinal = respuestaUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                RespuestaUsuario respuestaUsuario = (RespuestaUsuario) iterator.next();

                for (RespuestaUsuario respuestaVerificaConflictoConPK : respuestaUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (respuestaVerificaConflictoConPK.getCodPreguntaUsuario().compareTo(respuestaUsuario.getCodPreguntaUsuario() ) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    respuestaUsuarioRepository.actualizarUsuarioId(respuestaUsuario.getCodPreguntaUsuario(), respuestaUsuario.getIdUsuario(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    respuestaUsuarioRepository.eliminar(respuestaUsuario.getCodPreguntaUsuario(), respuestaUsuario.getIdUsuario());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (RespuestaUsuario respuestaUsuario : respuestaUsuario1) {
                respuestaUsuarioRepository.eliminar(respuestaUsuario.getCodPreguntaUsuario(), respuestaUsuario.getIdUsuario());
            }
            for (RespuestaUsuario respuestaUsuario : respuestaUsuario2) {
                respuestaUsuarioRepository.eliminar(respuestaUsuario.getCodPreguntaUsuario(), respuestaUsuario.getIdUsuario());
            }
        }




        // ------> RecadoContacto Usuario (a quien va dirigido el contacto)
        List<RecadoContacto> recadoContactoUsuario1 = recadoContactoRepository.buscarPorIdUsuario(idUsuario1);
        List<RecadoContacto> recadoContactoUsuario2 = recadoContactoRepository.buscarPorIdUsuario(idUsuario2);

        List<RecadoContacto> recadoContactoUsuarioFinal;                                        // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxRecadosContactoUsuario") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = recadoContactoUsuario2.iterator();
                recadoContactoUsuarioFinal = recadoContactoUsuario1;
            }
            else {
                iterator = recadoContactoUsuario1.iterator();
                recadoContactoUsuarioFinal = recadoContactoUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                RecadoContacto recadoContacto = (RecadoContacto) iterator.next();

                for (RecadoContacto recadoVerificaConflictoConPK : recadoContactoUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (recadoVerificaConflictoConPK.getFecha().compareTo(recadoContacto.getFecha()) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    recadoContactoRepository.actualizarUsuarioId(recadoContacto.getFecha(), recadoContacto.getUsuarioId(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    recadoContactoRepository.eliminar(recadoContacto.getFecha(), recadoContacto.getUsuarioId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (RecadoContacto recadoContacto : recadoContactoUsuario1) {
                recadoContactoRepository.eliminar(recadoContacto.getFecha(), recadoContacto.getUsuarioId());
            }
            for (RecadoContacto recadoContacto : recadoContactoUsuario2) {
                recadoContactoRepository.eliminar(recadoContacto.getFecha(), recadoContacto.getUsuarioId());
            }
        }




        // ------> RecadoContacto UsuUsuario (quien realiza el contacto)
        List<RecadoContacto> recadoContactoUsuUsuario1 = recadoContactoRepository.buscarPorIdUsuUsuario(idUsuario1);
        List<RecadoContacto> recadoContactoUsuUsuario2 = recadoContactoRepository.buscarPorIdUsuUsuario(idUsuario2);

        List<RecadoContacto> recadoContactoUsuUsuarioFinal;                                                             // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxRecadosContactoUsuUsuario") != null) {                                                          // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = recadoContactoUsuUsuario2.iterator();
                recadoContactoUsuUsuarioFinal = recadoContactoUsuUsuario1;
            }
            else {
                iterator = recadoContactoUsuUsuario1.iterator();
                recadoContactoUsuUsuarioFinal = recadoContactoUsuUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                RecadoContacto recadoContacto = (RecadoContacto) iterator.next();

                for (RecadoContacto recadoVerificaConflictoConPK : recadoContactoUsuUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (recadoVerificaConflictoConPK.getFecha().compareTo(recadoContacto.getFecha()) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    recadoContactoRepository.actualizarUsuUsuarioId(recadoContacto.getFecha(), recadoContacto.getUsuUsuario().getId(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    recadoContactoRepository.eliminar(recadoContacto.getFecha(), recadoContacto.getUsuUsuario().getId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (RecadoContacto recadoContacto : recadoContactoUsuUsuario1) {
                recadoContactoRepository.eliminar(recadoContacto.getFecha(), recadoContacto.getUsuUsuario().getId());
            }
            for (RecadoContacto recadoContacto : recadoContactoUsuUsuario2) {
                recadoContactoRepository.eliminar(recadoContacto.getFecha(), recadoContacto.getUsuUsuario().getId());
            }
        }




        // ------> RespaldoUsuario
        List<RespaldoUsuario> respaldoUsuarioUsuario1 = respaldoUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<RespaldoUsuario> respaldoUsuarioUsuario2 = respaldoUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        List<RespaldoUsuario> respaldoUsuarioUsuarioFinal;                                        // lista de objectos que ya posee el usuario que va a quedar

        if (checkboxs.get("checkBoxRespaldoUsuario") != null) {                                                              // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = respaldoUsuarioUsuario2.iterator();
                respaldoUsuarioUsuarioFinal = respaldoUsuarioUsuario1;
            }
            else {
                iterator = respaldoUsuarioUsuario1.iterator();
                respaldoUsuarioUsuarioFinal = respaldoUsuarioUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                RespaldoUsuario respaldoUsuario = (RespaldoUsuario) iterator.next();

                for (RespaldoUsuario respaldoVerificaConflictoConPK : respaldoUsuarioUsuarioFinal) {                      // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (respaldoVerificaConflictoConPK.getFechaModificacion().compareTo(respaldoUsuario.getFechaModificacion()) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    respaldoUsuarioRepository.actualizarUsuarioId(respaldoUsuario.getFechaModificacion(), respaldoUsuario.getIdUsuario(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    respaldoUsuarioRepository.eliminar(respaldoUsuario.getFechaModificacion(), respaldoUsuario.getIdUsuario());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (RespaldoUsuario respaldoUsuario : respaldoUsuarioUsuario1) {
                respaldoUsuarioRepository.eliminar(respaldoUsuario.getFechaModificacion(), respaldoUsuario.getIdUsuario());
            }
            for (RespaldoUsuario respaldoUsuario : respaldoUsuarioUsuario2) {
                respaldoUsuarioRepository.eliminar(respaldoUsuario.getFechaModificacion(), respaldoUsuario.getIdUsuario());
            }
        }




        // ------> RolUsuario
        List<RolUsuario> rolUsuarioUsuario1 = rolUsuarioRepository.buscarPorIdUsuario(idUsuario1);
        List<RolUsuario> rolUsuarioUsuario2 = rolUsuarioRepository.buscarPorIdUsuario(idUsuario2);

        List<RolUsuario> rolUsuarioUsuarioFinal;                                                                        // lista de objectos con que va a quedar el usuario que va a quedar

        if (checkboxs.get("checkBoxRoles") != null) {                                                                    // caso que se seleccion el checkbox

            Iterator iterator;

            if ( idUsuario1.compareTo( usuarioFinal.getId() ) == 0) {                                                   // solo se cambian los id de las entidades que no sean del usuario final
                iterator = rolUsuarioUsuario2.iterator();
                rolUsuarioUsuarioFinal = rolUsuarioUsuario1;
            }
            else {
                iterator = rolUsuarioUsuario1.iterator();
                rolUsuarioUsuarioFinal = rolUsuarioUsuario2;
            }

            while (iterator.hasNext()) {                                                                                // cambio de usuario a cada entidad
                existeConflictoConPK = false;

                RolUsuario rolUsuario = (RolUsuario) iterator.next();

                for (RolUsuario rolVerificaConflictoConPK : rolUsuarioUsuarioFinal) {                                   // verifica que no exista conflicto con una llave primaria compuesta que ya exista
                    if (rolVerificaConflictoConPK.getCompositeId().getIdRolAcceso().compareTo(rolUsuario.getCompositeId().getIdRolAcceso()) == 0) {
                        existeConflictoConPK = true;
                        break;
                    }
                }

                if (!existeConflictoConPK) {                                                                            // si no existe conflicto se agrega
                    rolUsuarioRepository.actualizarUsuarioId(rolUsuario.getCompositeId().getIdRolAcceso(), rolUsuario.getCompositeId().getUsuarioId(), usuarioFinal.getId());
                }
                else {                                                                                                  // si existe conflicto, la elimino
                    iterator.remove();
                    rolUsuarioRepository.eliminar(rolUsuario.getCompositeId().getIdRolAcceso(), rolUsuario.getCompositeId().getUsuarioId());
                }
            }
        }
        else {                                                                                                          // si no se quieren mezclar, se borran todas
            for (RolUsuario rolUsuario : rolUsuarioUsuario1) {
                rolUsuarioRepository.eliminar(rolUsuario.getCompositeId().getIdRolAcceso(), rolUsuario.getCompositeId().getUsuarioId());
            }
            for (RolUsuario rolUsuario : rolUsuarioUsuario2) {
                rolUsuarioRepository.eliminar(rolUsuario.getCompositeId().getIdRolAcceso(), rolUsuario.getCompositeId().getUsuarioId());
            }
        }




        // eliminacion del usuario no seleccionado
        if(usuarioFinal.getId().compareTo(idUsuario1) == 0){
            usuarioRepository.eliminar(idUsuario2);
        }
        else{
            usuarioRepository.eliminar(idUsuario1);
        }


        // setear password del login aexa, a uno por defecto
        actualizaLoginAexa(usuarioFinal, usuarioFinal.getEmail(), "cambiar");


        // guardado de usuario final
        usuarioRepository.save(usuarioFinal);


        // Actualiza el rut (manejado asi para evitar el conflicto generado por la duplicidad de rut)
        if ( usuario.getRut() != null && ( usuarioFinal.getRut() == null || usuarioFinal.getRut().compareTo(usuario.getRut()) != 0 ) ) {
            usuarioRepository.actualizarRut(usuarioFinal.getId(), usuario.getRut(), usuario.getDigitoVerificador());
        }

    }








    /**
     * Guarda la {@link crm.entities.ActividadExalumno} de un usuario.
     *
     * @param actividad {@link crm.entities.ActividadExalumno} a realizar.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    public void guardarActividadExalumno(ActividadExalumno actividad){
        actividadExalumnoRepository.save(actividad);
        System.out.println(actividad.getId());
    }






    /**
     * Actualiza el {@link crm.entities.LoginAexa} de un {@link crm.entities.Usuario}. (El metodo "save" de la entidad
     * Usuario debe ser llamado en el metodo desde donde se llama a actualizaLoginAexa)
     *
     * @param usuario {@link crm.entities.Usuario} a actualizar.
     * @param email Email del {@link crm.entities.Usuario} a actualizar en el Login
     * @param password Password del {@link crm.entities.Usuario} a actualizar en el Login (como string, sin ser md5)
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    public void actualizaLoginAexa(Usuario usuario, String email, String password){
        LoginAexa login = loginAexaRepository.findByUsuarioId(usuario.getId());

        // caso que no posea LoginAexa cargado en el sistema
        if (login == null ) {
            login = new LoginAexa ();
        }

        // transformacion md5 de password y seteo de login aexa del usuario
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            login.setPassword(sb.toString());

            Date fechaActual = new java.util.Date();
            login.setUsername(email);
            login.setIntentosFallidosIngreso((short) 0);
            login.setFechaUltimoCambioClave(fechaActual);
            login.setFechaModificacion(fechaActual);
            login.setRutUsuario(getCurrentUser().getRut());

            login.setUsuario(usuario);
            usuario.setCredencialesAcceso(login);

        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Error al codificar contraseña");
        }

    }

    /**
     * Lee un archivo excel con informacion de usuarios, parsea la información que contiene y la guarda en la base de datos.
     *
     *  La estructura del excel es: Rut, Mail, Nombre, Telefono, Carrera, Direccion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Async
    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void leerExcelUsuarios(InputStream archivo, HttpSession session) throws IOException{
        Integer usuariosValidosCount = 0;
        Date fechaActual = new Date();
        List<Carrera> carrerasEncontradas;
        Usuario usuario = new Usuario();
        AntecedenteEducacional antEdu = new AntecedenteEducacional();
        InfoProfesionalExalumno infoProfesionalExalumno = new InfoProfesionalExalumno();
        TipoMoneda tipoMoneda = tipoMonedaRepository.findByCodigo((short)1);
        TipoDisponibilidad tipoDisponibilidad = tipoDisponibilidadRepository.findByCodigo((short) 0);
        TipoDominioCompu tipoDominioCompu = tipoDominioCompuRepository.findByCodigo((short) 0);
        TipoSituacionLaboral tipoSituacionLaboral = tipoSituacionLaboralRepository.findByCodigo((short)0);

        String rut, mail, nombres, carrera;

        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern mailPattern = Pattern.compile(PATTERN_EMAIL);

        String PATTERN_RUT1 = "^\\d{1,2}[.]\\d{3}[.]\\d{3}[-][K|k|\\d]{1}";
        String PATTERN_RUT2 = "^\\d{7,8}[-][K|k|\\d]{1}";
        String PATTERN_RUT3 = "^\\d{7,8}[K|k|\\d]{1}";

        Pattern rutPattern1 = Pattern.compile(PATTERN_RUT1);
        Pattern rutPattern2 = Pattern.compile(PATTERN_RUT2);
        Pattern rutPattern3 = Pattern.compile(PATTERN_RUT3);

        String PATTERN_WORDS = "[A-Za-zñÑ ]+";

        Pattern wordsPattern = Pattern.compile(PATTERN_WORDS);

        XSSFWorkbook wb = new XSSFWorkbook(archivo);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator rows = sheet.rowIterator();
        XSSFRow row;
        XSSFCell cell;
        Integer totalRows = sheet.getPhysicalNumberOfRows()-1;
        Integer parsedRows=0;
        session.setAttribute("percent", parsedRows / totalRows);
        while (rows.hasNext()) {
                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                Boolean usuarioValido = true;
                    if (cells.hasNext()) {
                        System.out.println("Verificando Rut");
                        cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        rut = cell.getStringCellValue();
                        Matcher rutMatcher1 = rutPattern1.matcher(rut);
                        Matcher rutMatcher2 = rutPattern2.matcher(rut);
                        Matcher rutMatcher3 = rutPattern3.matcher(rut);
                        if (rutMatcher1.matches() || rutMatcher3.matches() || rutMatcher2.matches()) {
                            rut = rut.trim();
                            rut = rut.replace("-", "");
                            rut = rut.replace(".", "");
                            if(rut.length()>10){
                                usuarioValido = false;
                            }else {
                                usuario.setRut(Integer.valueOf(rut.substring(0, rut.length() - 1)));
                                usuario.setDigitoVerificador(rut.substring(rut.length() - 1));
                            }
                            if (usuarioRepository.findByRut(usuario.getRut()) != null) {
                                usuarioValido = false;
                            }
                        } else {
                            usuarioValido = false;
                        }
                    } else {
                        usuario = new Usuario();
                    }
                    if (usuarioValido == true && cells.hasNext()) {
                        System.out.println("Verificando Mail");
                        cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        mail = cell.getStringCellValue();
                        mail = mail.trim();
                        Matcher mailMatcher = mailPattern.matcher(mail);
                        if (mailMatcher.matches()) {
                            if(mail.length()>50){
                                usuarioValido = false;
                            }else usuario.setEmail(mail);
                            if (usuarioRepository.findByEmails(usuario.getEmail()) != null || loginAexaRepository.findByUsername(usuario.getEmail()) != null) {
                                usuarioValido = false;
                            }
                        } else {
                            usuarioValido = false;
                        }
                    } else {
                        usuario = new Usuario();
                    }
                    if (usuarioValido == true && cells.hasNext()) {
                        System.out.println("Verificando Nombre");
                        cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        nombres = cell.getStringCellValue();
                        int wordCount = 0;
                        boolean word = false;
                        int endOfLine = nombres.length() - 1;
                        //Contamos las palabras que hay en el string
                        for (int j = 0; j < nombres.length(); j++) {
                            if (Character.isLetter(nombres.charAt(j)) && j != endOfLine) {
                                word = true;
                            } else if (!Character.isLetter(nombres.charAt(j)) && word) {
                                wordCount++;
                                word = false;
                            } else if (Character.isLetter(nombres.charAt(j)) && j == endOfLine) {
                                wordCount++;
                            }
                        }
                        Matcher wordsMatcher = wordsPattern.matcher(nombres);
                        if (wordsMatcher.matches()) {
                            String[] partes = nombres.split(" ");
                            if (wordCount == 4) {
                                if(partes[0].length()>100||partes[1].length()>100||partes[2].length()>100||partes[3].length()>100) usuarioValido = false;
                                else {
                                    usuario.setNombres(partes[0] + " " + partes[1]);
                                    usuario.setApellidoPaterno(partes[2]);
                                    usuario.setApellidoMaterno(partes[3]);
                                }
                            } else if (wordCount == 3) {
                                if(partes[0].length()>100||partes[1].length()>100||partes[2].length()>100) usuarioValido = false;
                                else {
                                    usuario.setNombres(partes[0]);
                                    usuario.setApellidoPaterno(partes[1]);
                                    usuario.setApellidoMaterno(partes[2]);
                                }
                            } else if (wordCount == 2) {
                                if(partes[0].length()>100||partes[1].length()>100) usuarioValido = false;
                                else {
                                    usuario.setNombres(partes[0]);
                                    usuario.setApellidoPaterno(partes[1]);
                                }
                            } else {
                                usuarioValido = false;
                            }
                        } else {
                            usuarioValido = false;
                        }
                    } else {
                        usuario = new Usuario();
                    }
                    if (usuarioValido == true && cells.hasNext()) {
                        System.out.println("Verificando Telefono");
                        cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if(cell.getStringCellValue().length()>20) usuarioValido = false;
                        else usuario.setFonoParticular(cell.getStringCellValue());
                    } else {
                        usuario = new Usuario();
                    }
                    if (usuarioValido == true && cells.hasNext()) {
                        System.out.println("Verificando Carrera");
                        cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        carrera = cell.getStringCellValue();
                        carrerasEncontradas = carreraRepository.buscarCarrerasPorCalceNombre(carrera);
                        if (carrerasEncontradas.size() > 0) {
                            antEdu.setCarrera(carrerasEncontradas.get(0));
                        } else {
                            antEdu.setCarrera(null);
                        }
                    } else{
                        usuario = new Usuario();
                    }
                    if (usuarioValido == true && cells.hasNext()) {
                        System.out.println("Verificando Direccion");
                        cell = (XSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if(cell.getStringCellValue().length()>1000) usuarioValido = false;
                        else usuario.setDireccion(cell.getStringCellValue());

                        infoProfesionalExalumno.setDefaultValues(usuario.getId(),tipoMoneda,tipoDisponibilidad,tipoDominioCompu,tipoSituacionLaboral);
                        infoProfesionalExalumno.setRutUsuario(81668700);
                        infoProfesionalExalumno.setFechaModificacion(fechaActual);

                        antEdu.setPais(paisRepository.findById((short) 56));
                        antEdu.setInstitucion(institucionRepository.findByCodInstitucion((short)25));
                        antEdu.setRutUsuario(81668700);
                        antEdu.setFechaModificacion(fechaActual);

                        usuario.setCompletitudContacto(getCompletitud(usuario));
                        usuario.setTipoVigencia(tipoVigenciaRepository.findByCodVigencia((short) 1));
                        usuario.setEstadoCivil(estadoCivilRepository.findByCodigo((short)1));
                        usuario.setTrayectoriaCompleta((short) 0);
                        usuario.setPais(paisRepository.findById((short) 56));
                        usuario.setNacionalidad(usuario.getPais().getNombreNacionalidad());
                        usuario.setInformacionProfesional(infoProfesionalExalumno);
                        usuario.setRutUsuario(81668700);
                        usuario.setFechaModificacion(fechaActual);
                        usuario.setFechaRegistro(fechaActual);

                        infoProfesionalExalumno.setUsuario(usuario);
                        infoProfesionalExalumno.setUsuarioId(usuario.getId());

                        antEdu.setUsuario(usuario);
                        //seteo de login aexa
                        LoginAexa login = new LoginAexa();
                        // transformacion md5 de password y seteo de login aexa del usuario
                        try {
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            md.update("cambiar".getBytes());
                            byte[] digest = md.digest();
                            StringBuffer sb = new StringBuffer();
                            for (byte b : digest) {
                                sb.append(String.format("%02x", b & 0xff));
                            }
                            login.setPassword(sb.toString());
                            login.setUsername(usuario.getEmail());
                            login.setIntentosFallidosIngreso((short) 0);
                            login.setFechaUltimoCambioClave(fechaActual);
                            login.setFechaModificacion(fechaActual);
                            login.setRutUsuario(81668700);
                            login.setUsuario(usuario);
                            usuario.setCredencialesAcceso(login);
                        } catch (java.security.NoSuchAlgorithmException e) {
                            System.out.println("Error al codificar contraseña");
                        }
                        try {
                            System.out.println("Guardando");
                            usuario = usuarioRepository.save(usuario);
                            infoProfesionalExalumno.setUsuario(usuario);
                            antEdu.setUsuario(usuario);
                            infoProfesionalExalumnoRepository.save(infoProfesionalExalumno);
                            if (antEdu.getCarrera() != null) antecedenteEducacionalRepository.save(antEdu);
                            usuariosValidosCount++;
                            usuario = new Usuario();
                            infoProfesionalExalumno = new InfoProfesionalExalumno();
                            antEdu = new AntecedenteEducacional();
                        }catch(JpaSystemException e){
                            System.out.println(e);
                        }
                    } else {
                        usuario = new Usuario();
                    }
            parsedRows++;
            session.setAttribute("percent", (int) Math.floor((double) 100 * parsedRows / totalRows));
        }
        session.setAttribute("usuariosValidos", usuariosValidosCount);
    }

    public List<Usuario> buscarUsuariosPorCalceNombreCompleto(String tagName) {
        List<Usuario> usuariosList = new ArrayList<>();
        int wordCount = 0;
        boolean word = false;
        int endOfLine = tagName.length() - 1;
        Usuario usuario = new Usuario();
        for (int j = 0; j < tagName.length(); j++) {
            if (Character.isLetter(tagName.charAt(j)) && j != endOfLine) {
                word = true;
            } else if (!Character.isLetter(tagName.charAt(j)) && word) {
                wordCount++;
                word = false;
            } else if (Character.isLetter(tagName.charAt(j)) && j == endOfLine) {
                wordCount++;
            }
        }
            String[] partes = tagName.split(" ");
            if (wordCount == 4) {
                usuario.setNombres(partes[0] + " " + partes[1]);
                usuario.setApellidoPaterno(partes[2]);
                usuario.setApellidoMaterno(partes[3]);
                usuariosList = usuarioRepository.buscarUsuariosPorNombreYApellidos(usuario.getNombres(),usuario.getApellidoPaterno(),usuario.getApellidoMaterno());
            } else if (wordCount == 3) {
                usuario.setNombres(partes[0]);
                usuario.setApellidoPaterno(partes[1]);
                usuario.setApellidoMaterno(partes[2]);
                usuariosList = usuarioRepository.buscarUsuariosPorNombreYApellidos(usuario.getNombres(),usuario.getApellidoPaterno(),usuario.getApellidoMaterno());
            } else if (wordCount == 2) {
                usuario.setNombres(partes[0]);
                usuario.setApellidoPaterno(partes[1]);
                usuariosList = usuarioRepository.buscarUsuariosPorNombreYApellidoPaterno(usuario.getNombres(), usuario.getApellidoPaterno());
            } else if (wordCount == 1) {
                usuario.setNombres(partes[0]);
                usuario.setApellidoPaterno(partes[0]);
                usuario.setApellidoMaterno(partes[0]);
                usuariosList = usuarioRepository.buscarUsuariosPorNombresOApellidoPaternoOApellidoMaterno(usuario.getNombres(),usuario.getApellidoPaterno(),usuario.getApellidoMaterno());
            }
        return usuariosList;
    }

    public Usuario getUsuarioByRut(String rut) {
        return usuarioRepository.findByRut(Integer.parseInt(rut));
    }




    //@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @Transactional
    public void eliminarUsuarioById(Long id) {
        List<PostulacionArchivosAdjuntos> postulacionUsuario = postulacionArchivosAdjuntosRepository.buscarPorIdUsuario(id);
        if(!postulacionUsuario.isEmpty()){
            postulacionArchivosAdjuntosRepository.eliminarPorIdUsuario(id);
        }
        List<ArchivosAdjuntos> archivos= archivosAdjuntosRepository.buscarPorIdUsuario(id);
        if(!archivos.isEmpty()){
            for(ArchivosAdjuntos temp : archivos)
                archivosAdjuntosRepository.eliminar(temp.getId());
        }
        List<AporteSocio> aporteSocios = aporteSocioRepository.buscarPorIdUsuario(id);
        if(!aporteSocios.isEmpty()){
            for(AporteSocio  aporte: aporteSocios)
                aporteSocioRepository.eliminar(aporte.getIdCompromisoSocio(),aporte.getFecha(),aporte.getIdUsuario());
        }
        /*List<CompromisoSocio> compromisoSocios = compromisoSocioRepository.buscarPorIdUsuario(id);
        if(compromisoSocios.isEmpty()){
            for(CompromisoSocio compromiso: compromisoSocios) {
                aporteSocioRepository.eliminarPorIdCompromiso(compromiso.getId());
                compromisoSocioRepository.eliminar(compromiso.getId());
            }
        }*/
        List<CampanaExalumno> campanaExalumnos = campanaExalumnoRepository.buscarPorIdUsuario(id);
        if(!campanaExalumnos.isEmpty()){
            for(CampanaExalumno campana : campanaExalumnos) {
                List<CompromisoSocio> compromisos = compromisoSocioRepository.buscarPorIdCampana(campana.getId());
                for(CompromisoSocio comp : compromisos) {
                    aporteSocioRepository.eliminarPorIdCompromiso(comp.getId());
                    aporteSocioRepository.eliminarPorIdCampana(comp.getCampanaExalumno().getId());
                }
                compromisoSocioRepository.eliminarPorIdCampana(campana.getId());
            }
            compromisoSocioRepository.eliminarPorIdUsuario(id);
            campanaExalumnoRepository.eliminarPorIdUsuario(id);
        }
        else compromisoSocioRepository.eliminarPorIdUsuario(id);
        List<RecadoContacto> recadoContactos = recadoContactoRepository.buscarPorIdUsuario(id);
        if(!recadoContactos.isEmpty()){
            for(RecadoContacto recado : recadoContactos)
                recadoContactoRepository.eliminar(recado.getFecha(),recado.getUsuarioId());
            recadoContactoRepository.eliminarPorIdUsuUsuario(id);
        }
        List<OperadorContabilidad> operadorContabilidads = operadorContabilidadRepository.buscarPorIdUsuarioOperador(id);
        if(!operadorContabilidads.isEmpty() || !operadorContabilidadRepository.buscarPorIdUsuario(id).isEmpty()){
            operadorContabilidadRepository.eliminarPorIdUsuario(id);
            operadorContabilidadRepository.eliminarPorIdUsuarioOperador(id);
        }
        List<StickynotesAexa> stickynotesAexas = stickynotesAexaRepository.buscarPorIdUsuario(id);
        if(!stickynotesAexas.isEmpty()){
            for(StickynotesAexa sticky : stickynotesAexas)
                stickynotesAexaRepository.eliminar(sticky.getId());
        }
        List<AptitudUsuario> aptitudUsuarios = aptitudUsuarioRepository.buscarPorIdUsuario(id);
        if(!aptitudUsuarios.isEmpty()){
            for(AptitudUsuario aptitud : aptitudUsuarios)
                aptitudUsuarioRepository.eliminar(aptitud.getId());
        }
        List<CategoriaAsesoriaUsuario> categoriaAsesoriaUsuarios = categoriaAsesoriaUsuarioRepository.buscarPorIdUsuario(id);
        if(!categoriaAsesoriaUsuarios.isEmpty()){
            for(CategoriaAsesoriaUsuario categoria : categoriaAsesoriaUsuarios)
                categoriaAsesoriaUsuarioRepository.eliminar(categoria.getId());
        }
        List<PagoAsesoria> pagoAsesorias = pagoAsesoriaRepository.buscarPorIdUsuario(id);
        if(!pagoAsesorias.isEmpty()){
            for(PagoAsesoria pago : pagoAsesorias)
                pagoAsesoriaRepository.eliminar(pago.getId());
        }
        List<PostulacionUsuarioAsesoria> postulacionUsuarioAsesorias = postulacionUsuarioAsesoriaRepository.buscarPorIdUsuario(id);
        if(!postulacionUsuarioAsesorias.isEmpty()){
            for(PostulacionUsuarioAsesoria postulacion : postulacionUsuarioAsesorias)
                postulacionUsuarioAsesoriaRepository.eliminar(postulacion.getIdOfertaAsesoria(),postulacion.getIdUsuario());
        }
        List<OfertaCrawled> ofertaCrawleds = ofertaCrawledRepository.buscarPorIdUsuario(id);
        if(!ofertaCrawleds.isEmpty()){
            for(OfertaCrawled oferta : ofertaCrawleds)
                ofertaCrawledRepository.eliminar(oferta.getId());
        }
        List<CompromisoEmpresa> compromisoEmpresas = compromisoEmpresaRepository.buscarPorIdUsuario(id);
        if(!compromisoEmpresas.isEmpty()){
            for(CompromisoEmpresa compromiso : compromisoEmpresas){
                aporteEmpresaRepository.eliminar(compromiso.getId());
                compromisoEmpresaRepository.eliminar(compromiso.getId());
            }
        }
        List<FiltroOfertaLaboral> filtro = filtroOfertaLaboralRepository.buscarPorIdUsuario(id);
        if(!filtro.isEmpty())
            filtroOfertaLaboralRepository.eliminarPorIdUsuario(id);
        List<PostulacionOfertaLaboralUsmempleo> postulacionOferta= postulacionOfertaLaboralUsmempleoRepository.buscarPorIdUsuario(id);
        if(!postulacionOferta.isEmpty()){
            for(PostulacionOfertaLaboralUsmempleo oferta : postulacionOferta) {
                encuestaPostulacionLaboralRepository.eliminar(oferta.getOfertaLaboralUsmempleoId(), oferta.getUsuarioId());
                respuestaPreguntaOfertaLaboralRepository.eliminarPorIdOfertayIdUsuario(oferta.getUsuarioId(),oferta.getOfertaLaboralUsmempleoId());
            }
            postulacionOfertaLaboralUsmempleoRepository.eliminarPorIdUsuario(id);
        }
        List<PostulanteFavorito> postulanteFavoritos = postulanteFavoritoRepository.buscarPorIdUsuario(id);
        if(!postulanteFavoritos.isEmpty()){
            for(PostulanteFavorito postulante : postulanteFavoritos)
                postulanteFavoritoRepository.eliminar(postulante.getIdUsuarioEmpresaUsmempleo(),postulante.getIdUsuario());
        }
        if(preferenciaUsuarioUsmempleoRepository.findByUsuarioId(id)!=null)
            preferenciaUsuarioUsmempleoRepository.eliminar(id);
        if(!usuarioVistoUsmempleoRepository.buscarPorIdUsuario(id).isEmpty())
            usuarioVistoUsmempleoRepository.eliminarPorIdUsuario(id);
        List<ActividadExalumno> actividades = actividadExalumnoRepository.buscarPorIdUsuario(id);
        if(!actividades.isEmpty()) {
            for (ActividadExalumno actividad : actividades)
                actividadExalumnoRepository.eliminar(actividad.getId());
        }
        List<ContactoHistoricoEmpresa> contactos = contactoHistoricoEmpresaRepository.buscarPorIdUsuario(id);
        if(!contactos.isEmpty()){
            for(ContactoHistoricoEmpresa contacto : contactos) {
                contactoHistoricoEmpresaPersonaParticipanteRepository.eliminar(contacto.getId());
                contactoHistoricoEmpresaRepository.eliminar(contacto.getId());
            }

        }
        List<AntecedenteColegio> antecedenteColegios = antecedenteColegioRepository.buscarPorIdUsuario(id);
        if(!antecedenteColegios.isEmpty()){
            for(AntecedenteColegio antecedente : antecedenteColegios)
                antecedenteColegioRepository.eliminar(antecedente.getId());
        }
        List<AntecedenteEducacional> antecedenteEducacionals = antecedenteEducacionalRepository.buscarPorIdUsuario(id);
        if(!antecedenteEducacionals.isEmpty()){
            for(AntecedenteEducacional ante : antecedenteEducacionals)
                antecedenteEducacionalRepository.eliminar(ante.getId());
        }
        List<CapacitacionExalumno> capacitacionExalumnos = capacitacionExalumnoRepository.buscarPorIdUsuario(id);
        if(!capacitacionExalumnos.isEmpty()){
            for(CapacitacionExalumno capacitacion: capacitacionExalumnos)
                capacitacionExalumnoRepository.eliminar(capacitacion.getId());
        }
        if(usuarioApoderadoRepository.findByUsuarioId(id)!=null)
            usuarioApoderadoRepository.eliminar(id);
        List<CompetenciaExalumno> competencias = competenciaExalumnoRepository.buscarPorIdUsuario(id);
        if(!competencias.isEmpty()){
            for(CompetenciaExalumno comp: competencias)
                competenciaExalumnoRepository.eliminar(comp.getNivelCompetenciaUsmempleoId(),comp.getUsuarioId());
        }
        List<ConocimientoInfoExalumno> conocimientoInfoExalumnos = conocimientoInfoExalumnoRepository.buscarPorIdUsuario(id);
        if(!conocimientoInfoExalumnos.isEmpty()){
            for(ConocimientoInfoExalumno conocimiento: conocimientoInfoExalumnos)
                conocimientoInfoExalumnoRepository.eliminar(conocimiento.getId());
        }
        List<CorreosValidados> correosValidados = correosValidadosRepository.buscarPorIdUsuario(id);
        if(!correosValidados.isEmpty()){
            for(CorreosValidados correo : correosValidados)
                correosValidadosRepository.eliminar(correo.getId());
        }
        List<DuenoEmpresa> duenos = duenoEmpresaRepository.buscarPorIdUsuario(id);
        if(!duenos.isEmpty()){
            for(DuenoEmpresa dueno : duenos)
                duenoEmpresaRepository.eliminar(dueno.getEmpresaId(),dueno.getUsuarioId());
        }
        if(infoProfesionalExalumnoRepository.findByUsuarioId(id)!=null)
            infoProfesionalExalumnoRepository.eliminar(id);
        if(loginAexaRepository.findByUsuarioId(id)!=null)
            loginAexaRepository.eliminar(id);
        List<ManejoIdioma> manejoIdiomas = manejoIdiomasRepository.buscarPorIdUsuario(id);
        if(!manejoIdiomas.isEmpty()){
            for(ManejoIdioma manejo : manejoIdiomas)
                manejoIdiomasRepository.eliminar(manejo.getId());
        }
        List<PaginaExalumno> paginaExalumnos = paginaExalumnoRepository.buscarPorIdUsuario(id);
        if(!paginaExalumnos.isEmpty()){
            for(PaginaExalumno pagina : paginaExalumnos)
                paginaExalumnoRepository.eliminar(pagina.getId());
        }
        PreferenciaPrivacidad privacidad = preferenciaPrivacidadRepository.findByUsuarioId(id);
        if(privacidad!=null)
            preferenciaPrivacidadRepository.eliminar(privacidad.getCodInstitucion(),privacidad.getUsuarioId());
        List<RespaldoUsuario> respaldoUsuarios = respaldoUsuarioRepository.buscarPorIdUsuario(id);
        if(!respaldoUsuarios.isEmpty()){
            for (RespaldoUsuario respaldo : respaldoUsuarios)
                respaldoUsuarioRepository.eliminar(respaldo.getFechaModificacion(),respaldo.getIdUsuario());
        }
        List<RespuestaUsuario> respuestaUsuarios = respuestaUsuarioRepository.buscarPorIdUsuario(id);
        if(!respuestaUsuarios.isEmpty()){
            for(RespuestaUsuario respuesta: respuestaUsuarios)
                respuestaUsuarioRepository.eliminar(respuesta.getCodPreguntaUsuario(),respuesta.getIdUsuario());
        }
        List<TestPsicologicoExalumno> tests = testPsicologicoExalumnoRepository.buscarPorIdUsuario(id);
        if(!tests.isEmpty()){
            for(TestPsicologicoExalumno test : tests)
                testPsicologicoExalumnoRepository.eliminar(test.getId());
        }
        List<InvitacionVideoEntrevistaUsmEmpleo> invitaciones = invitacionVideoEntrevistaUsmEmpleoRepository.buscarPorIdUsuario(id);
        if(!invitaciones.isEmpty()){
            for(InvitacionVideoEntrevistaUsmEmpleo invitacion : invitaciones)
                invitacionVideoEntrevistaUsmEmpleoRepository.eliminar(invitacion.getId());
        }
        List<VideoCurriculoUsuario>  videoCurriculoUsuarios = videoCurriculoUsuarioRepository.buscarPorIdUsuario(id);
        if(!videoCurriculoUsuarios.isEmpty()){
            for(VideoCurriculoUsuario video : videoCurriculoUsuarios)
                videoCurriculoUsuarioRepository.eliminar(video.getId());
        }
        List<VideoEntrevistaUsmEmpleo> videoEntrevistaUsmEmpleos = videoEntrevistaUsmEmpleoRepository.buscarPorIdUsuario(id);
        if(!videoEntrevistaUsmEmpleos.isEmpty()){
            for(VideoEntrevistaUsmEmpleo videoEntrevista : videoEntrevistaUsmEmpleos )
                videoEntrevistaUsmEmpleoRepository.eliminar(videoEntrevista.getId());
        }
        List<AutorizacionUsuario> autorizacionUsuarios = autorizacionUsuarioRepository.buscarPorIdUsuario(id);
        if(!autorizacionUsuarios.isEmpty()){
            for(AutorizacionUsuario autorizacion : autorizacionUsuarios) {
                autorizacionUsuarioPermisoAccesoRepository.eliminar(autorizacion.getId());
                autorizacionUsuarioRepository.eliminar(autorizacion.getId());
            }
        }
        List<RolUsuario> rolUsuarios = rolUsuarioRepository.buscarPorIdUsuario(id);
        if(!rolUsuarios.isEmpty()){
            for(RolUsuario rol : rolUsuarios)
                rolUsuarioRepository.eliminar(rol.getRolAcceso().getId(),rol.getCompositeId().getUsuarioId());
        }
        //usuarioRepository.delete(id);
        usuarioRepository.eliminar(id);
    }




    /**
     * Actualiza la trayectoria {@link crm.entities.Usuario}, realizando un recalculo del mismo en base a las ponderaciones
     * que tiene el trigger asociado. Si el atributo no es nulo o no está vacio, realiza la suma correspondiente del
     * aporte a la trayectoria del usuario
     *
     * @param idUsuario {@link crm.entities.Usuario} a actualizar su porcentaje de trayectoria
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Transactional
    public void actualizarTrayectoria(String idUsuario) {
        short trayectoria = 0;

        Usuario usuario = usuarioRepository.findById(Long.parseLong(idUsuario));

        if (usuario.getNombres() != null && usuario.getNombres().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getApellidoPaterno() != null && usuario.getApellidoPaterno().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getApellidoMaterno() != null && usuario.getApellidoMaterno().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getEstadoCivil() != null && usuario.getEstadoCivil().getCodigo() != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getSexo() != null && usuario.getSexo().toString().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getNacionalidad() != null && usuario.getNacionalidad().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getUrlFoto() != null && usuario.getUrlFoto().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 2);
        }

        if (usuario.getEmailOpcional() != null && usuario.getEmailOpcional().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getEmailLaboral() != null && usuario.getEmailLaboral().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getCodigoPostal() != null && usuario.getCodigoPostal() != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getCelular() != null && usuario.getCelular().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 3);
        }

        if (usuario.getFonoParticular() != null && usuario.getFonoParticular().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getFonoOpcional() != null && usuario.getFonoOpcional().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getDireccion() != null && usuario.getDireccion().compareTo("") != 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getPais() != null && usuario.getPais().getCodigo() != 0) {
            trayectoria = (short) (trayectoria + 3);
        }

        if (usuario.getComuna() != null && usuario.getComuna().getCodigo() != 0) {
            trayectoria = (short) (trayectoria + 3);
        }

        if (usuario.getPaginaExalumnoList().size() > 0) {
            trayectoria = (short) (trayectoria + 1);
        }

        if (usuario.getAntecedenteEducacionalList().size() == 1) {
            trayectoria = (short) (trayectoria + 6);
        }
        else if (usuario.getAntecedenteEducacionalList().size() > 1) {
            trayectoria = (short) (trayectoria + 12);
        }

        if (usuario.getCapacitacionExalumnoList().size() > 0) {
            trayectoria = (short) (trayectoria + 4);
        }

        if (usuario.getManejoIdiomasList().size() > 0) {
            trayectoria = (short) (trayectoria + 8);
        }

        if (usuario.getConocimientoInfoExalumnoList().size() > 0) {
            trayectoria = (short) (trayectoria + 8);
        }

        if (usuario.getActividadExalumnoList().size() > 0) {
            trayectoria = (short) (trayectoria + 8);
        }

        if (usuario.getArchivosAdjuntosList().size() > 0) {
            for (ArchivosAdjuntos archivosAdjunto : usuario.getArchivosAdjuntosList()) {
                if (archivosAdjunto.getUtilizado() == false) {
                    trayectoria = (short) (trayectoria + 8);
                    break;
                }
            }
        }

        if (usuario.getCompetenciaExalumnoList().size() > 0) {
            trayectoria = (short) (trayectoria + 4);
        }


        if (usuario.getPreferenciaUsuarioUsmempleo() != null) {
            if (usuario.getPreferenciaUsuarioUsmempleo().getEncabezado() != null && usuario.getPreferenciaUsuarioUsmempleo().getEncabezado().compareTo("") != 0) {
                trayectoria = (short) (trayectoria + 5);
            }

            if (usuario.getPreferenciaUsuarioUsmempleo().getInfoPersonal() != null && usuario.getPreferenciaUsuarioUsmempleo().getInfoPersonal().compareTo("") != 0) {
                trayectoria = (short) (trayectoria + 7);
            }
        }

        if (usuario.getInformacionProfesional() != null )  {
            if (usuario.getInformacionProfesional().getDisponibilidad() != null && usuario.getInformacionProfesional().getDisponibilidad().getCodigo() != 0 ) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getSituacionLaboral() != null && usuario.getInformacionProfesional().getSituacionLaboral().getCodigo() != 0 ) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getAnoExpLaboral() != null && usuario.getInformacionProfesional().getAnoExpLaboral() != 0) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getDominioComputacional() != null && usuario.getInformacionProfesional().getDominioComputacional().getCodigo() != 0) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getObjetivoProfesional() != null && usuario.getInformacionProfesional().getObjetivoProfesional().compareTo("") != 0) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getDistinciones() != null && usuario.getInformacionProfesional().getDistinciones().compareTo("") != 0) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getExpectativaSalarial() != null && usuario.getInformacionProfesional().getExpectativaSalarial() != 0) {
                trayectoria = (short) (trayectoria + 3);
            }

            if (usuario.getInformacionProfesional().getTipoMoneda() != null && usuario.getInformacionProfesional().getTipoMoneda().getCodigo() != 0) {
                trayectoria = (short) (trayectoria + 3);
            }
        }


        // revisa caso que trayectoria sea mayor que 100
        if (trayectoria > 100) {
            trayectoria = 100;
        }

        usuarioRepository.actualizarTrayectoria(usuario.getId(), trayectoria);
    }

    /**
     * Lee un archivo excel con informacion de usuarios, toma los datos de los usuarios que hay en el archivo
     * y busca a los usuarios, luego completa la tabla con datos faltantes extraidos desde la base de datos.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void procesarExcelUsuarios(InputStream archivo, String nombre) throws IOException{
        Usuario usuarioEncontrado;
        int i=0;
        Boolean flag = false;
        String datoUsuario;
        XSSFWorkbook wb = new XSSFWorkbook(archivo);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator rows = sheet.rowIterator();
        XSSFRow row;
        XSSFCell cell;
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();
            row.createCell(6);
            row.createCell(7);
            row.createCell(8);
            row.createCell(9);
            row.createCell(10);
            row.createCell(11);
            Iterator cells = row.cellIterator();
            cells.next();
            cells.next();
            cell = (XSSFCell) cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            datoUsuario = cell.getStringCellValue();
            usuarioEncontrado = usuarioRepository.findByRut(Integer.valueOf(datoUsuario));
            cells.next();
            cell = (XSSFCell) cells.next();
            if(usuarioEncontrado == null){
                datoUsuario = cell.getStringCellValue();
                String[] parts = datoUsuario.split(" ");
                if(parts.length == 4){
                    if(usuarioRepository.buscarUsuariosPorNombreYApellidos(parts[0]+" "+parts[1],parts[2],parts[3]).size() != 0)
                        usuarioEncontrado = usuarioRepository.buscarUsuariosPorNombreYApellidos(parts[0]+" "+parts[1],parts[2],parts[3]).get(0);
                }else if(parts.length == 5){
                    if(usuarioRepository.buscarUsuariosPorNombreYApellidos(parts[0]+" "+parts[1]+" "+parts[2],parts[3],parts[4]).size() != 0)
                        usuarioEncontrado = usuarioRepository.buscarUsuariosPorNombreYApellidos(parts[0]+" "+parts[1]+" "+parts[2],parts[3],parts[4]).get(0);
                }
            }
            if (usuarioEncontrado != null) {
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(usuarioEncontrado.getCelular() != null){
                    cell.setCellValue(usuarioEncontrado.getCelular());
                }
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(usuarioEncontrado.getFonoParticular()!= null){
                    cell.setCellValue(usuarioEncontrado.getFonoParticular());
                }
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(usuarioEncontrado.getFonoOpcional()!= null){
                    cell.setCellValue(usuarioEncontrado.getFonoOpcional());
                }
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(usuarioEncontrado.getEmail()!= null){
                    cell.setCellValue(usuarioEncontrado.getEmail());
                }
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(usuarioEncontrado.getEmailLaboral()!= null){
                    cell.setCellValue(usuarioEncontrado.getEmailLaboral());
                }
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(usuarioEncontrado.getEmailOpcional()!= null){
                    cell.setCellValue(usuarioEncontrado.getEmailOpcional());
                }
            }/*else{
                cells.next();
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                apPaterno = cell.getStringCellValue();
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                apMaterno = cell.getStringCellValue();
                cell = (XSSFCell) cells.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                nombres = cell.getStringCellValue();
                usuarios = usuarioRepository.buscarUsuariosPorNombreYApellidos(nombres, apPaterno, apMaterno);
                if(usuarios == null || usuarios.size()== 0) usuarioEncontrado = null;
                else usuarioEncontrado = usuarioRepository.buscarUsuariosPorNombreYApellidos(nombres, apPaterno, apMaterno).get(0);
                if (usuarioEncontrado != null) {
                    cell = (XSSFCell) cells.next();
                    if (usuarioEncontrado.getFonoParticular() != null && usuarioEncontrado.getFonoParticular().compareTo("") != 0)
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(usuarioEncontrado.getFonoParticular());
                    cell = (XSSFCell) cells.next();
                    if (usuarioEncontrado.getCelular() != null && usuarioEncontrado.getCelular().compareTo("") != 0)
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(usuarioEncontrado.getCelular());
                    cell = (XSSFCell) cells.next();
                    if (usuarioEncontrado.getFonoOpcional() != null && usuarioEncontrado.getFonoOpcional().compareTo("") != 0)
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(usuarioEncontrado.getFonoOpcional());
                    cell = (XSSFCell) cells.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (usuarioEncontrado.getEmail() != null && usuarioEncontrado.getEmail().compareTo("") != 0)
                        cell.setCellValue(usuarioEncontrado.getEmail());
                    if (usuarioEncontrado.getEmailLaboral() != null && usuarioEncontrado.getEmailLaboral().compareTo("") != 0)
                        cell.setCellValue(usuarioEncontrado.getEmailLaboral());
                    if (usuarioEncontrado.getEmailOpcional() != null && usuarioEncontrado.getEmailOpcional().compareTo("") != 0)
                        cell.setCellValue(usuarioEncontrado.getEmailOpcional());
                }
            }*/
            System.out.println(++i);
        }
        FileOutputStream fileOut = new FileOutputStream(nombre);
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    /*
    TODO BORRAR
    public List<KeyValueContainer<Long>> datosDashboard() {
        List<KeyValueContainer<Long>> datos = new ArrayList<>();
        String[] tiposUsuario = usuarioRepository.contarUsuariosPorTipo().split(",");

        datos.add(new KeyValueContainer<>("Antiguos", Long.parseLong(tiposUsuario[0])));
        datos.add(new KeyValueContainer<>("Nuevos", Long.parseLong(tiposUsuario[1])));
        datos.add(new KeyValueContainer<>("Validados", Long.parseLong(tiposUsuario[2])));
        datos.add(new KeyValueContainer<>("Rechazados", Long.parseLong(tiposUsuario[3])));
        datos.add(new KeyValueContainer<>("Eliminados", Long.parseLong(tiposUsuario[4])));
        datos.add(new KeyValueContainer<>("Cliente Potencial", Long.parseLong(tiposUsuario[5])));
        datos.add(new KeyValueContainer<>("Temporales", Long.parseLong(tiposUsuario[6])));
        datos.add(new KeyValueContainer<>("Ex Alumno Potencial", Long.parseLong(tiposUsuario[7])));

        return datos;
    }
     */

}
