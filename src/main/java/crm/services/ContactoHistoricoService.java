package crm.services;


import crm.entities.*;
import crm.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio que contiene los metodos para interactuar con entidades de tipo {@link crm.entities.ContactoHistoricoEmpresa}
 * y {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Component
public class ContactoHistoricoService {


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ContactoHistoricoEmpresa}.
     */
    @Autowired
    private ContactoHistoricoEmpresaRepository contactoHistoricoEmpresaRepository;


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}.
     */
    @Autowired
    private ContactoHistoricoEmpresaPersonaParticipanteRepository contactoHistoricoEmpresaPersonaParticipanteRepository;


    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
     */
    @Autowired
    private UsuarioUsmempleoEmpresaRepository usuarioUsmempleoEmpresaRepository;

    /**
     * Servicio relacionado a la entidad {@link crm.entities.Usuario}.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoContacto}
     */
    @Autowired
    TipoContactoRepository tipoContactoRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoOportunidad}
     */
    @Autowired
    TipoOportunidadRepository tipoOportunidadRepository;

    /**
     * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstado}
     */
    @Autowired
    TipoEstadoRepository tipoEstadoRepository;


    /**
     * {@link org.apache.log4j.Logger} especifico de la clase
     */
    private static final Logger logger = Logger.getLogger(ContactoHistoricoService.class);




    /**
     * Maneja el guardado de un nuevo {@link crm.entities.ContactoHistoricoEmpresa} en la Base de Datos,
     * según el objeto asado como parámetro, que contiene los datos a ser guardados,
     *
     * @param usuariosUsmempleoEmpresaParticipantes string con los datos de {@link crm.entities.UsuarioUsmempleoEmpresa}
     *                                                   participantes del contacto.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public void agregarContactoHistoricoEmpresa(ContactoHistoricoEmpresa contactoHistoricoEmpresa,
                                                String usuariosUsmempleoEmpresaParticipantes) {

        // almacena quien agregó el registro y la fecha actual
        Date fechaActual = new Date();
        contactoHistoricoEmpresa.setRutUsuario(usuarioService.getCurrentUser().getRut());
        contactoHistoricoEmpresa.setFechaModificacion(fechaActual);

        // guardado en la base de datos
        contactoHistoricoEmpresa = contactoHistoricoEmpresaRepository.save(contactoHistoricoEmpresa);

        List<String> idsUsuariosUsmempleoEmpresaParticipantes = new ArrayList();

        // obtiene un arreglo de string con los datos de cada usuario (en la vista se separa cada usuario con el caracter ";")
        String[] datosUsuarios = usuariosUsmempleoEmpresaParticipantes.split(";");

        for (String usuario : datosUsuarios)  {
            if (datosUsuarios.length != 0) {
                // obtencion del id desde los datos del usuario (en la vista se separa cada dato (id, nombre, etc) con el caracter "|")
                idsUsuariosUsmempleoEmpresaParticipantes.add(usuario.split("\\|")[0]);
            }
        }

        // se agregan los usuarios participantes del contactoHistoricoEmpresa
        agregarContactoHistoricoEmpresaPersonaParticipante( contactoHistoricoEmpresa, idsUsuariosUsmempleoEmpresaParticipantes );
    }





    /**
     * Maneja el guardado de nuevo(s)  {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} en la Base de Datos,
     * según el string que contiene los id de {@link crm.entities.UsuarioUsmempleoEmpresa} participantes de un
     * {@link crm.entities.ContactoHistoricoEmpresa}
     *
     * @param idsUsuariosUsmempleoEmpresaParticipantes Lista de string con los id de {@link crm.entities.UsuarioUsmempleoEmpresa}
     *                                                   participantes del contacto.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     *
     */
    public void agregarContactoHistoricoEmpresaPersonaParticipante(ContactoHistoricoEmpresa contactoHistoricoEmpresa,
                                                                    List<String> idsUsuariosUsmempleoEmpresaParticipantes) {

        ContactoHistoricoEmpresaPersonaParticipante contactoHistoricoEmpresaPersonaParticipante = new ContactoHistoricoEmpresaPersonaParticipante();
        UsuarioUsmempleoEmpresa usuarioUsmempleoEmpresa = new UsuarioUsmempleoEmpresa();

        for (String idUsuarioUsmempleoEmpresa : idsUsuariosUsmempleoEmpresaParticipantes) {
            if(idUsuarioUsmempleoEmpresa != null && idUsuarioUsmempleoEmpresa.length() !=0) {

                usuarioUsmempleoEmpresa = usuarioUsmempleoEmpresaRepository.findById(Long.parseLong(idUsuarioUsmempleoEmpresa ) );

                // seteo de los id de los objetos usuarioUsmempleoEmpresa y contactoHistoricoEmpresa
                contactoHistoricoEmpresaPersonaParticipante.setUsuarioUsmempleoEmpresaId( usuarioUsmempleoEmpresa.getId() );
                contactoHistoricoEmpresaPersonaParticipante.setContactoHistoricoEmpresaId( contactoHistoricoEmpresa.getId() );

                // seteo de los objetos usuarioUsmempleoEmpresa y contactoHistoricoEmpresa
                contactoHistoricoEmpresaPersonaParticipante.setUsuarioUsmempleoEmpresa(usuarioUsmempleoEmpresa);
                contactoHistoricoEmpresaPersonaParticipante.setContactoHistoricoEmpresa(contactoHistoricoEmpresa);

                // almacena quien agregó el registro y la fecha actual
                Date fechaActual = new Date();
                contactoHistoricoEmpresaPersonaParticipante.setRutUsuario(usuarioService.getCurrentUser().getRut());
                contactoHistoricoEmpresaPersonaParticipante.setFechaModificacion(fechaActual);

                // guardado en la base de datos
                contactoHistoricoEmpresaPersonaParticipanteRepository.save(contactoHistoricoEmpresaPersonaParticipante);
            }
        }
    }

    /**
     * Buscar todos los contacto {@link crm.entities.ContactoHistoricoEmpresa} en la Base de Datos,
     * según el nombre del operador {@link crm.entities.Usuario}
     *
     * @param nombre String con el nombre del operador
     * @param apellido String con el apellido paterno del operador
     * @param pagina Integer de la pagina inicial.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     *
     */
    public Page<ContactoHistoricoEmpresaPersonaParticipante> buscarContactoHistoricoPorNombreOperador(String nombre, String apellido, Short sector, int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
        if(sector!=-1) return contactoHistoricoEmpresaPersonaParticipanteRepository.buscarContactoHistoricoEmpresaPorNombreYApellidoPaternoOperadorYSector(nombre,apellido,sector,pageRequest);
        else return contactoHistoricoEmpresaPersonaParticipanteRepository.buscarContactoHistoricoEmpresaPorNombreYApellidoPaternoOperador(nombre,apellido,pageRequest);
    }

    /**
     * Buscar todos los contactos {@link crm.entities.ContactoHistoricoEmpresa}.
     *
     * @param pagina Integer de la pagina inicial.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     *
     */
    public Page<ContactoHistoricoEmpresaPersonaParticipante> buscarTodosContactoHistorico(int pagina,Short sector) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
        //Iterator<ContactoHistoricoEmpresaPersonaParticipante> cont = contactoHistoricoEmpresaPersonaParticipanteRepository.
        if(sector!=-1)return contactoHistoricoEmpresaPersonaParticipanteRepository.buscarTodosContactosHistoricoEmpresaPersonaParticipantePorTipoSector(pageRequest,sector);
        else return contactoHistoricoEmpresaPersonaParticipanteRepository.buscarTodosContactosHistoricoEmpresaPersonaParticipante(pageRequest);
        //Iterator<ContactoHistoricoEmpresaPersonaParticipante> con=IteratorUtils
        //contactoHistoricoEmpresaPersonaParticipanteRepository.findA

    }

}
