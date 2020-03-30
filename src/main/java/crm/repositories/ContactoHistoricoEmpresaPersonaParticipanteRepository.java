package crm.repositories;

import crm.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface ContactoHistoricoEmpresaPersonaParticipanteRepository extends CrudRepository<ContactoHistoricoEmpresaPersonaParticipante, ContactoHistoricoEmpresaPersonaParticipantePK> {

    /**
     * Devuelve una lista de {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     * que posean un usuarioUsmempleoEmpresa.empresa.id igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado historico de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} que corresponden
     * a contactos historicos con la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT c FROM ContactoHistoricoEmpresaPersonaParticipante AS c JOIN c.usuarioUsmempleoEmpresa AS u WHERE u.empresa.id = :idEmpresa")
    List<ContactoHistoricoEmpresaPersonaParticipante> getContactoHistoricoEmpresaPersonaParticipante(@Param("idEmpresa") Long idEmpresa);

    /**
     * Buscar todos los contactos {@link crm.entities.ContactoHistoricoEmpresa}.
     *
     * @param page Integer de la pagina inicial.
     * @param sector Short del sector a que pertenece la empresa.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     *
     */
    @Query("SELECT chep FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "JOIN che.usuario AS us " +
            "WHERE ts.codigo = :codSector ORDER BY us.nombres")
    Page<ContactoHistoricoEmpresaPersonaParticipante> buscarTodosContactosHistoricoEmpresaPersonaParticipantePorTipoSector(Pageable page,@Param("codSector") Short sector );

    /**
     * Buscar todos los contactos {@link crm.entities.ContactoHistoricoEmpresa}.
     *
     * @param page Integer de la pagina inicial.
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     *
     */
    @Query("SELECT chep FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us ORDER BY us.nombres")
    Page<ContactoHistoricoEmpresaPersonaParticipante> buscarTodosContactosHistoricoEmpresaPersonaParticipante(Pageable page);

    /**
     * Devuelve la lista de {@link crm.entities.UsuarioEmpresaUsmempleo} relacionada
     * desde {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} y
     * que posean un usuarioUsmempleoEmpresa.empresa.id igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado historico de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.UsuarioEmpresaUsmempleo} que corresponden
     * a las personas de contacto de la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT ue FROM ContactoHistoricoEmpresaPersonaParticipante AS c JOIN c.usuarioUsmempleoEmpresa AS u JOIN u.usuarioEmpresaUsmempleo ue WHERE u.empresa.id = :idEmpresa")
    List<UsuarioEmpresaUsmempleo> getUsuarioEmpresaUsmempleoContactoHistoricoEmpresaPersonaParticipante(@Param("idEmpresa") Long idEmpresa);

    /**
     * Devuelve una lista de {@link crm.entities.Usuario} relacionada desde {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     * y que posean un usuarioUsmempleoEmpresa.empresa.id igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado historico de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.Usuario} que corresponden
     * a los usuarios que hicieron el contacto con la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT us FROM ContactoHistoricoEmpresaPersonaParticipante AS c JOIN c.usuarioUsmempleoEmpresa AS u JOIN c.contactoHistoricoEmpresa co JOIN co.usuario AS us WHERE u.empresa.id = :idEmpresa")
    List<Usuario> getUsuarioContactoHistoricoEmpresaPersonaParticipante(@Param("idEmpresa") Long idEmpresa);

    /**
     * Relaciona idContactoEmpresa con idUsuario
     *
     * @author Rocío Barramuño <rocio.barramuno@alumnos.usm.cl>
     */

    @Query ("SELECT c FROM ContactoHistoricoEmpresaPersonaParticipante AS c WHERE c.contactoHistoricoEmpresaId = :idContactoEmpresa AND c.usuarioUsmempleoEmpresaId = :idUsuario")
    ContactoHistoricoEmpresaPersonaParticipante editarContactoEmpresa(@Param("idContactoEmpresa") Long idContactoEmpresa,@Param("idUsuario") Long idUsuario);

    /**
     * Devuelve un Page de {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     *
     * @param nombres String del nombre del operador al cual se obtendra la lista de contactos.
     * @param apellidoPaterno String del apellido del operador al cual se obtendra la lista de contactos.
     *
     * @return Colleccion {@Link java.util.Page} de  {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} que corresponden
     * a los contactos que hizo el operador.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT chep FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us " +
            "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
            "WHERE UPPER(translate(COALESCE(us.nombres, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(COALESCE(us.apellidoPaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') ORDER BY uue.empresa.nombreFantasiaEmpresa ASC ",
            countQuery ="SELECT COUNT(chep) FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
                    "JOIN chep.contactoHistoricoEmpresa AS che " +
                    "JOIN che.usuario AS us " +
                    "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
                    "WHERE UPPER(translate(COALESCE(us.nombres, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(us.apellidoPaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')")
    Page<ContactoHistoricoEmpresaPersonaParticipante> buscarContactoHistoricoEmpresaPorNombreYApellidoPaternoOperador(@Param("nombres") String nombres,
                                                                                                                      @Param("apellidoPaterno") String apellidoPaterno,
                                                                                                                      Pageable page);

    /**
     * Devuelve un Page de {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     *
     * @param nombres String del nombre del operador al cual se obtendra la lista de contactos.
     * @param apellidoPaterno String del apellido del operador al cual se obtendra la lista de contactos.
     * @param codSector Integer del sector de la empresa que se busca.
     *
     * @return Colleccion {@Link java.util.Page} de  {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} que corresponden
     * a los contactos que hizo el operador.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT chep FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us " +
            "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
            "WHERE UPPER(translate(COALESCE(us.nombres, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(COALESCE(us.apellidoPaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND ts.codigo = :codSector ORDER BY uue.empresa.nombreFantasiaEmpresa ASC ",
            countQuery ="SELECT COUNT(chep) FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
                    "JOIN uue.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "JOIN chep.contactoHistoricoEmpresa AS che " +
                    "JOIN che.usuario AS us " +
                    "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
                    "WHERE UPPER(translate(COALESCE(us.nombres, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(us.apellidoPaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND ts.codigo = :codSector ")
    Page<ContactoHistoricoEmpresaPersonaParticipante> buscarContactoHistoricoEmpresaPorNombreYApellidoPaternoOperadorYSector(@Param("nombres") String nombres,
                                                                                                                             @Param("apellidoPaterno") String apellidoPaterno,
                                                                                                                             @Param("codSector") Short codSector,
                                                                                                                             Pageable page);


    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.Usuario} por mes
     * de todas los {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}, realizando una busqueda según un año como parametro.
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page datos que permiten la paginación.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} por mes
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=1 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=2 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=3 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=4 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=5 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=6 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=7 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=8 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=9 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=10 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=11 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=12 THEN 1 END) ," +
            "COUNT(1) " +
            "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us " +
            "WHERE YEAR(che.fechaContacto) = :anio " +
            "GROUP BY  us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno  ORDER BY us.apellidoPaterno",
            countQuery = "SELECT COUNT(us.nombres) " +
                    "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.contactoHistoricoEmpresa AS che " +
                    "JOIN che.usuario AS us " +
                    "WHERE YEAR(che.fechaContacto) = :anio " +
                    "GROUP BY  us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno")
    Page<List<String>> indiceCantidadContactoEmpresa(@Param("anio") Integer anio,Pageable page);

    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.Usuario} por mes
     * de todas los {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}, realizando una busqueda según un año y operador como parametros.
     *
     * @param nombreOperador nombre del operador que hizo los contactos.
     * @param apPaternoOperador apellido paterno del operador que hizo los contactos.
     * @param apMaternoOperador apellido materno del operador que hizo los contactos.
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page datos que permiten la paginación.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} por mes
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT us.nombres,us.apellidoPaterno,us.apellidoMaterno," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=1 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=2 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=3 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=4 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=5 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=6 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=7 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=8 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=9 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=10 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=11 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=12 THEN 1 END) ," +
            "COUNT(1) " +
            "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us " +
            "WHERE YEAR(che.fechaContacto) = :anio AND " +
            "UPPER(translate(us.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(us.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(COALESCE(us.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "GROUP BY us.nombres,us.apellidoPaterno,us.apellidoMaterno  ORDER BY us.apellidoPaterno",
            countQuery = "SELECT COUNT(us.nombres) " +
                    "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.contactoHistoricoEmpresa AS che " +
                    "JOIN che.usuario AS us " +
                    "WHERE YEAR(che.fechaContacto) = :anio AND " +
                    "UPPER(translate(us.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(us.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(us.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "GROUP BY us.nombres,us.apellidoPaterno,us.apellidoMaterno")
    Page<List<String>> indiceCantidadContactoEmpresaPorOperador(@Param("nombres") String nombreOperador,
                                                                @Param("apellidoPaterno") String apPaternoOperador,
                                                                @Param("apellidoMaterno") String apMaternoOperador,
                                                                @Param("anio") Integer anio,Pageable page);

    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.ContactoHistoricoEmpresa} por mes
     * de todas los {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}, realizando una busqueda según un año y tipo de oportunidad como parametros.
     *
     * @param oportunidad nombre de la oportunidad sobre el cual se desea obtener los datos.
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page datos que permiten la paginación.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} por mes
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT  us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=1 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=2 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=3 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=4 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=5 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=6 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=7 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=8 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=9 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=10 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=11 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=12 THEN 1 END) ," +
            "COUNT(1) " +
            "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us " +
            "JOIN che.tipoOportunidad AS to " +
            "WHERE YEAR(che.fechaContacto) = :anio AND to.codOportunidad = :oportunidad AND " +
            "UPPER(translate(us.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(us.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(COALESCE(us.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "GROUP BY  us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno  ORDER BY us.apellidoPaterno",
            countQuery = "SELECT COUNT(us.nombres) " +
                    "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.contactoHistoricoEmpresa AS che " +
                    "JOIN che.usuario AS us " +
                    "JOIN che.tipoOportunidad AS to " +
                    "WHERE YEAR(che.fechaContacto) = :anio AND to.codOportunidad = :oportunidad AND " +
                    "UPPER(translate(us.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(us.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(us.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "GROUP BY us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno")
    Page<List<String>> indiceCantidadContactoEmpresaPorOportunidad(@Param("oportunidad") Short oportunidad,
                                                                   @Param("nombres") String nombreOperador,
                                                                   @Param("apellidoPaterno") String apPaternoOperador,
                                                                   @Param("apellidoMaterno") String apMaternoOperador,
                                                                   @Param("anio") Integer anio,Pageable page);

    /**
     * Obtiene un listado, de manera paginada, de la cantidad total de {@link crm.entities.ContactoHistoricoEmpresa} por mes
     * de todas los {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}, realizando una busqueda según el año..
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page datos que permiten la paginación.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.ContactoHistoricoEmpresa} por mes
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT  us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=1 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=2 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=3 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=4 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=5 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=6 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=7 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=8 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=9 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=10 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=11 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(che.fechaContacto)=12 THEN 1 END) ," +
            "COUNT(1) " +
            "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "JOIN che.usuario AS us " +
            "JOIN che.tipoOportunidad AS to " +
            "WHERE YEAR(che.fechaContacto) = :anio AND " +
            "UPPER(translate(us.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(us.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "AND UPPER(translate(COALESCE(us.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "GROUP BY  us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno  ORDER BY us.apellidoPaterno",
            countQuery = "SELECT COUNT(us.nombres) " +
                    "FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.contactoHistoricoEmpresa AS che " +
                    "JOIN che.usuario AS us " +
                    "JOIN che.tipoOportunidad AS to " +
                    "WHERE YEAR(che.fechaContacto) = :anio AND " +
                    "UPPER(translate(us.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(us.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(us.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "GROUP BY us.id,us.nombres,us.apellidoPaterno,us.apellidoMaterno")
    Page<List<String>> indiceCantidadTotalContactoEmpresaPorOportunidad(@Param("nombres") String nombreOperador,
                                                                        @Param("apellidoPaterno") String apPaternoOperador,
                                                                        @Param("apellidoMaterno") String apMaternoOperador,
                                                                        @Param("anio") Integer anio,Pageable page);

    /**
     * Elimina un {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} segun la id especificada como parametro
     *
     * @param idContactoHistoricoBuscar Id del {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM ContactoHistoricoEmpresaPersonaParticipante AS chep WHERE chep.contactoHistoricoEmpresaId = :idContactoHistoricoBuscar")
    void eliminar(@Param("idContactoHistoricoBuscar") Long idContactoHistoricoBuscar);
}
