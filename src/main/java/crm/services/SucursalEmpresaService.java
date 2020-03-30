package crm.services;

import crm.entities.*;
import crm.repositories.*;
import crm.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.orm.hibernate3.SessionFactoryUtils.getSession;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.SucursalEmpresa} y con otras
 * entidades relacionadas.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Component
public class SucursalEmpresaService {

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.SucursalEmpresa}.
     */
    @Autowired
    private SucursalEmpresaRepository sucursalEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Empresa}.
     */
    @Autowired
    private EmpresaRepository empresaRepository;


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}.
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Provincia}.
     */
    @Autowired
    private ProvinciaRepository provinciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Region}.
     */
    @Autowired
    private RegionRepository regionRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
     */
    @Autowired
    private PaisRepository paisRepository;


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}.
     */
    @Autowired
    private TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaRepository usuarioUsmempleoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ActividadExalumno}.
     */
    @Autowired
    private ActividadExalumnoRepository actividadExalumnoRepository;

    @Autowired
    private UsuarioService usuarioService;


    /**
     * Obtiene la {@link crm.entities.SucursalEmpresa} segun el id que se pase como parametro
     *
     * @param idSucursal id de la sucursa a buscar.
     * @return la {@link crm.entities.SucursalEmpresa} o null si es que no se encontraron resultados.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public SucursalEmpresa getSucursalEmpresaById(Long idSucursal){
        SucursalEmpresa sucursalEmpresa = sucursalEmpresaRepository.findBySucursalCodigo(idSucursal);
        if(sucursalEmpresa!=null){
            return sucursalEmpresa;
        }
        else return null;
    }

    /**
     * Actualiza la {@link crm.entities.SucursalEmpresa} que se pase como parametro en la base de datos
     *
     * @param sucursal objeto sucursal que se desea actualizar.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     *
     */
    public SucursalEmpresa saveSucursalEmpresa(SucursalEmpresa sucursal) {
        SucursalEmpresa antiguo = sucursalEmpresaRepository.findBySucursalCodigo(sucursal.getSucursalCodigo());

        if ((antiguo.getSucSucursal() == null) || (antiguo.getSucSucursal()!= null && sucursal.getSucSucursal().compareTo(antiguo.getSucSucursal()) != 0))
            antiguo.setSucSucursal(sucursal.getSucSucursal());

        if ((antiguo.getSucEmail() == null) || (antiguo.getSucEmail()!= null && sucursal.getSucEmail().compareTo(antiguo.getSucEmail()) != 0))
            antiguo.setSucEmail(sucursal.getSucEmail());

        if ((antiguo.getSucDireccion() == null) || (antiguo.getSucDireccion()!= null && sucursal.getSucDireccion().compareTo(antiguo.getSucDireccion()) != 0))
            antiguo.setSucDireccion(sucursal.getSucDireccion());

        if ((antiguo.getSucFono() == null) || (antiguo.getSucFono()!= null && sucursal.getSucFono().compareTo(antiguo.getSucFono()) != 0))
            antiguo.setSucFono(sucursal.getSucFono());

        if ((antiguo.getSucFax() == null) || (antiguo.getSucFax()!= null && sucursal.getSucFax().compareTo(antiguo.getSucFax()) != 0))
            antiguo.setSucFax(sucursal.getSucFax());

        if(sucursal.getTipoVigencia().getCodVigencia().compareTo(antiguo.getTipoVigencia().getCodVigencia()) != 0){
            antiguo.setTipoVigencia(tipoVigenciaRepository.findByCodVigencia(sucursal.getTipoVigencia().getCodVigencia()));
        }

        if(sucursal.getPais() == null || sucursal.getPais() != null && sucursal.getPais().getId().compareTo(antiguo.getPais().getId()) != 0){
            antiguo.setPais(paisRepository.findOne(sucursal.getPais().getId()));
        }

        if(sucursal.getComuna() == null || sucursal.getComuna() != null && sucursal.getComuna().getCodigo().compareTo(antiguo.getComuna().getCodigo()) != 0){
            antiguo.setComuna(comunaRepository.buscarPorId(sucursal.getComuna().getCodigo()));
            antiguo.getComuna().setProvincia(provinciaRepository.findOne(sucursal.getComuna().getProvincia().getId()));
            antiguo.getComuna().getProvincia().setRegion(regionRepository.findOne(sucursal.getComuna().getProvincia().getRegion().getId()));
        }

        return sucursalEmpresaRepository.save(antiguo);
    }

    /**
     * Guarda una entidad {@Link crm.entities.SucursalEmpresa} nueva en la base de datos
     *
     * @param sucursal sucursal que se quiere guardar en la base de datos
     * @return La {@Link crm.entities.SucursalEmpresa} recien creada
     */
    public SucursalEmpresa guardarSucursal(SucursalEmpresa sucursal) {
        SucursalEmpresa s = sucursalEmpresaRepository.save(sucursal);
        return s;
    }

    /**
     * Elimina fisicamente una sucursal de la base de datos
     *
     * @param sucursal objeto del tipo sucursal que se quiere eliminar
     */
    public void eliminarSucursal(SucursalEmpresa sucursal) {
        sucursalEmpresaRepository.delete(sucursal);
    }

    /**
     * Retorna la comuna que se busque segun el codigo entregado
     *
     * @param codigo codigo de la comuna que se quiere buscar
     * @return entidad del tipo {@Link crm.entities.Comuna}
     */
    public Comuna setComunaSucursalByCodComuna(short codigo) {
        Comuna comuna = comunaRepository.findByCodigo(codigo);
        return comuna;
    }

    public Boolean verificarDatosInvalidosOIncompletosSucursalEmpresa(Long idSucursal) {
        SucursalEmpresa sucursal = sucursalEmpresaRepository.findOne(idSucursal);
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern patternEmail = Pattern.compile(PATTERN_EMAIL);
        String PATTERN_FONO = "\\+?[0-9]+";
        Pattern patternFono = Pattern.compile(PATTERN_FONO);
        String PATTERN_FAX = "[0-9]+";
        Pattern patternFax = Pattern.compile(PATTERN_FAX);

        if(sucursal.getSucDireccion().isEmpty() || sucursal.getSucDireccion() == null || (!sucursal.getSucDireccion().isEmpty() && sucursal.getSucDireccion().length() > 100)) {
            return true;
        }
        if(sucursal.getSucEmail().isEmpty() || sucursal.getSucEmail() == null){
            return true;
        }
        else {
            Matcher matcher = patternEmail.matcher(sucursal.getSucEmail());
            if (!matcher.matches() || sucursal.getSucEmail().length() > 50) {
                return true;
            }
        }
        if(sucursal.getSucFono().isEmpty() || sucursal.getSucFono() == null) {
            return true;
        }else {
            Matcher matcher = patternFono.matcher(sucursal.getSucFono());
            if (!matcher.matches() || sucursal.getSucFono().length() > 20) {
                return true;
            }
        }
        if(sucursal.getSucFax().isEmpty() || sucursal.getSucFax() == null) {
            return true;
        }else{
            Matcher matcher = patternFax.matcher(sucursal.getSucFax());
            if (!matcher.matches() || sucursal.getSucFax().length() > 25) {
                return true;
            }
        }
        if(sucursal.getSucSucursal().isEmpty() || sucursal.getSucSucursal() == null || sucursal.getSucSucursal().length() > 150) {
            return true;
        }
        if(sucursal.getComuna() == null || sucursal.getPais() == null) return true;
        return false;
    }

    public void validarSucursalEmpresa(Long idSucursal) {
        SucursalEmpresa sucursal = sucursalEmpresaRepository.findOne(idSucursal);
        sucursal.setTipoVigencia(new TipoVigencia(TipoVigencia.ID_VIGENTE,TipoVigencia.TIPO_VIGENTE));
        sucursalEmpresaRepository.save(sucursal);
    }

    public void corregirSucursal(SucursalEmpresa sucursalReasignar, SucursalEmpresa sucursalACorregir) {
        List<UsuarioUsmempleoEmpresa> usuariosUsmempleoEmpresa = usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorCodSucursalEmpresa(sucursalACorregir.getSucursalCodigo());
        for(UsuarioUsmempleoEmpresa u: usuariosUsmempleoEmpresa){
            u.setSucursalEmpresa(sucursalReasignar);
            usuarioUsmempleoEmpresaRepository.save(u);
        }
        List<ActividadExalumno> actividadesExalumno = actividadExalumnoRepository.getActividadExalumnosSucursalEmpresa(sucursalACorregir.getSucursalCodigo());
        for(ActividadExalumno a: actividadesExalumno){
            a.setSucursalEmpresa(sucursalReasignar);
            actividadExalumnoRepository.save(a);
        }
        sucursalEmpresaRepository.delete(sucursalACorregir);
    }

    public List<ActividadExalumno> getActividadesExalumnoPorSucursalCodigo(Long idSucursalACorregir) {
        return actividadExalumnoRepository.getActividadExalumnosSucursalEmpresa(idSucursalACorregir);
    }

    public List<UsuarioUsmempleoEmpresa> getContactoEmpresaPorSucursalCodigo(Long idSucursalACorregir) {
        return usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorCodSucursalEmpresa(idSucursalACorregir);
    }

    public void rechazarSucursal(SucursalEmpresa sucursalEmpresaARechazar) {
        List<UsuarioUsmempleoEmpresa> usuariosUsmempleoEmpresa = usuarioUsmempleoEmpresaRepository.buscarUsuariosUsmempleoEmpresaPorCodSucursalEmpresa(sucursalEmpresaARechazar.getSucursalCodigo());
        SucursalEmpresa casaMatriz = empresaRepository.traerCasaMatrizEmpresaPorIdEmpresa(sucursalEmpresaARechazar.getEmpresa().getId());
        for(UsuarioUsmempleoEmpresa u: usuariosUsmempleoEmpresa){
            u.setSucursalEmpresa(casaMatriz);
            u.setVigencia(new TipoVigencia(TipoVigencia.ID_NO_VIGENTE,TipoVigencia.TIPO_NO_VIGENTE));
            u.setFechaModificacion(new Date());
            u.setRutUsuario(usuarioService.getCurrentUser().getRut());
            usuarioUsmempleoEmpresaRepository.save(u);
        }
        List<ActividadExalumno> actividadesExalumno = actividadExalumnoRepository.getActividadExalumnosSucursalEmpresa(sucursalEmpresaARechazar.getSucursalCodigo());
        for(ActividadExalumno a: actividadesExalumno){
            a.setSucursalEmpresa(casaMatriz);
            a.setFechaModificacion(new Date());
            a.setRutUsuario(usuarioService.getCurrentUser().getRut());
            actividadExalumnoRepository.save(a);
        }
        sucursalEmpresaRepository.delete(sucursalEmpresaARechazar);
    }
}
