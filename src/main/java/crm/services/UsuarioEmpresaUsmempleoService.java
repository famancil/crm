package crm.services;

import crm.controllers.PerfilPublicadorController;
import crm.entities.*;
import crm.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.UsuarioEmpresaUsmempleo}
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Component
public class UsuarioEmpresaUsmempleoService {
    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaRepository usuarioUsmempleoEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioEmpresaUsmempleo}.
     */
    @Autowired
    private UsuarioEmpresaUsmempleoRepository usuarioEmpresaUsmempleoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}
     */
    @Autowired
    private ComunaRepository comunaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}
     */
    @Autowired
    private PaisRepository paisRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Empresa}
     */
    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.SucursalEmpresa}
     */
    @Autowired
    private SucursalEmpresaRepository sucursalEmpresaRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}
     */
    @Autowired
    private TipoVigenciaRepository tipoVigenciaRepository;

    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(UsuarioUsmempleoEmpresaService.class);

    /**
     * Guarda un {@link crm.entities.UsuarioEmpresaUsmempleo} nuevo en la base de datos.
     *
     * @param usuarioEmpresaUsmempleo {@link crm.entities.UsuarioEmpresaUsmempleo} a guardar en la base de datos.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    public void guardarPerfilPublicador(UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo, String nombreEmpresa, Long codSucursal) {
        Date fechaActual = new java.util.Date();
        Integer rutUsuario = ((Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRut();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(usuarioEmpresaUsmempleo.getPassword().getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            usuarioEmpresaUsmempleo.setPassword(sb.toString());
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Error al codificar contraseña");
        }
        usuarioEmpresaUsmempleo.setNombreCompleto(usuarioEmpresaUsmempleo.getNombreCompleto().toUpperCase());
        usuarioEmpresaUsmempleo.setPais(paisRepository.findById(usuarioEmpresaUsmempleo.getPais().getId()));
        usuarioEmpresaUsmempleo.setComuna(comunaRepository.findByCodigo(usuarioEmpresaUsmempleo.getComuna().getCodigo()));
        usuarioEmpresaUsmempleo.setEstadoVideoConferencia(0);
        usuarioEmpresaUsmempleo.setRutUsuario(rutUsuario);
        usuarioEmpresaUsmempleo.setFechaModificacion(fechaActual);

        usuarioEmpresaUsmempleo = usuarioEmpresaUsmempleoRepository.save(usuarioEmpresaUsmempleo);

        UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa = new UsuarioUsmempleoEmpresa();

        usuarioUsmempleoEmpresa.setEmpresa(empresaRepository.buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(nombreEmpresa).get(0));
        usuarioUsmempleoEmpresa.setUsuarioEmpresaUsmempleo(usuarioEmpresaUsmempleo);
        usuarioUsmempleoEmpresa.setFechaModificacion(fechaActual);
        usuarioUsmempleoEmpresa.setRutUsuario(rutUsuario);
        usuarioUsmempleoEmpresa.setSucursalEmpresa(sucursalEmpresaRepository.findBySucursalCodigo(codSucursal));
        usuarioUsmempleoEmpresa.setVigencia(tipoVigenciaRepository.findByCodVigencia((short)1));
        usuarioUsmempleoEmpresa.setPerfilActualizado(false);

        usuarioUsmempleoEmpresaRepository.save(usuarioUsmempleoEmpresa);
    }

    public List<String> buscarUsuariosEmpresaPorCalceNombreCompleto(String tagName) {
        return usuarioEmpresaUsmempleoRepository.buscarUsuariosEmpresaPorCalceNombreCompleto(tagName);
    }
}
