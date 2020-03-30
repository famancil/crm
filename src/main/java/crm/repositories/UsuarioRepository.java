package crm.repositories;

import crm.entities.LoginAexa;
import crm.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Usuario}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    /**
     * Retorna un listado de {@link crm.entities.Usuario} que posean un email igual al parametro
     * email.
     *
     * @param email email utilizado para la busqueda.
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     */
    List<Usuario> findByEmail(String email);

    /**
     * Retorna un listado de {@link crm.entities.Usuario} que posean un email igual al parametro
     * email, ignorando la presencia de mayusculas
     *
     * @param email email utilizado para la busqueda.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<Usuario> findByEmailIgnoreCase(String email);


    /**
     * Retorna un {@link crm.entities.Usuario} que posean un id igual al parametro
     * id.
     *
     * @param id id usado para la busqueda.
     * @return {@link crm.entities.Usuario}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl|>
     */
    Usuario findById(Long id);


    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que posean un nombre con algún
     * calce al parametro nombres.
     *
     * @param nombres nombre (o nombres) que se desean buscar..
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // COALESCE es para el manejo de valores nulos en la BD
    @Query(value="SELECT u " +
                "FROM Usuario AS u " +
                "WHERE UPPER(translate( COALESCE(u.nombres, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "ORDER BY u.apellidoPaterno ASC",
            countQuery = "SELECT COUNT(u) " +
                    "FROM Usuario AS u " +
                    "WHERE UPPER(translate(u.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " )
    Page<Usuario> buscarUsuariosPorNombres(@Param("nombres") String nombres, Pageable page);


    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que posean un rut igual al parametro
     * rut.
     *
     * @param rut rut que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT u " +
            "FROM Usuario u " +
            "WHERE u.rut = :rut",
            countQuery = "SELECT COUNT(u) " +
                    "FROM Usuario u " +
                    "WHERE u.rut = :rut" )
    Page<Usuario> buscarUsuariosPorRut(@Param("rut") Integer rut,
                                       Pageable page);



    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que posean un apellido con algún
     * calce al parametro apellido paterno y materno
     *
     * @param apellidoPaterno apellido paterno que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // COALESCE es para el manejo de valores nulos en la BD
    @Query(value="SELECT u " +
                "FROM Usuario u " +
                "WHERE UPPER(translate( COALESCE(u.apellidoPaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "AND UPPER(translate( COALESCE(u.apellidoMaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "ORDER BY u.apellidoPaterno, u.apellidoMaterno ASC",
            countQuery = "SELECT COUNT(u) " +
                        "FROM Usuario u " +
                    "WHERE UPPER(translate( COALESCE(u.apellidoPaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate( COALESCE(u.apellidoMaterno, '') ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') "  )
    Page<Usuario> buscarUsuariosPorApellidos(@Param("apellidoPaterno") String apellidoPaterno,
                                             @Param("apellidoMaterno") String apellidoMaterno,
                                             Pageable page);




    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que posean un email igual al parametro
     * email.
     *
     * @param email email del usuario que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT u " +
                "FROM Usuario u " +
                "WHERE UPPER(u.email) = UPPER(:email) " +
                    "OR UPPER(u.emailOpcional) = UPPER(:email) " +
                    "OR UPPER(u.emailLaboral) = UPPER(:email) " +
                "ORDER BY u.apellidoPaterno ASC",
            countQuery = "SELECT COUNT( DISTINCT u ) " +
                        "FROM Usuario u " +
                        "WHERE UPPER(u.email) = UPPER(:email) " +
                            "OR UPPER(u.emailOpcional) = UPPER(:email) " +
                            "OR UPPER(u.emailLaboral) = UPPER(:email) "  )
    Page<Usuario> buscarUsuariosPorEmail(@Param("email") String email,
                                         Pageable page);

    /**
     * Retorna un listado paginado de {@link crm.entities.Usuario} que pertenescan al pais idPais
     *
     * @param idPais id del pais
     * @param page
     * @return Coleccion {@link java.util.List} de {@link crm.entities.Usuario}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query(value="SELECT u " +
                "FROM Usuario u " +
                "WHERE u.pais.id = :idPais " +
                "ORDER BY u.apellidoPaterno ASC",
            countQuery = "SELECT COUNT(u) " +
                        "FROM Usuario u " +
                        "WHERE u.pais.id = :idPais")
    Page<Usuario> buscarUsuariosPorPais(@Param("idPais") Short idPais, Pageable page);

    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que posean un nombre y apellido paterno
     * con algún calce con los parametros buscados.
     *
     * @param nombres nombre del usuario que se desea buscar.
     * @param apellidoPaterno apellido paterno del usuario que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // COALESCE es para el manejo de valores nulos en la BD
    @Query(value="SELECT u " +
                "FROM Usuario u " +
                "WHERE UPPER(translate( COALESCE(u.nombres, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(u.apellidoPaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "AND UPPER(translate(COALESCE(u.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "ORDER BY u.apellidoPaterno, u.apellidoMaterno ASC",
            countQuery = "SELECT COUNT(u) " +
                        "FROM Usuario u " +
                        "WHERE UPPER(translate(u.nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                            "AND UPPER(translate(u.apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoPaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                            "AND UPPER(translate(COALESCE(u.apellidoMaterno, ''),'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:apellidoMaterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " )
    Page<Usuario> buscarUsuariosPorNombreYApellidos(@Param("nombres") String nombres,
                                                   @Param("apellidoPaterno") String apellidoPaterno,
                                                   @Param("apellidoMaterno") String apellidoMaterno,
                                                   Pageable page);



    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que posean asociados a una carrera en su
     * antecedente educacional, segun ano de ingreso, egreso y titulacion
     *
     * @param codInstitucion id de la institucion registrada en el Antecedente Educacional.
     * @param codCarrera id de la carrera registrado en el Antecedente Educacional.
     * @param anoIngreso año de ingreso registrado en el Antecedente Educacional.
     * @param anoEgreso año de egreso registrado en el Antecedente Educacional
     * @param anoTitulo año de titulo registrado en el Antecedente Educacional
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // COALESCE es para el manejo de valores nulos en la BD (soluciona el hecho que exista un valor nulo al momento de buscar un año con el comodin '%' "
    @Query(value="SELECT DISTINCT a.usuario  " +
                "FROM AntecedenteEducacional AS a " +
                "WHERE COALESCE(TO_CHAR(a.carrera.codCarrera, 'FM9999'), '') LIKE :codCarrera " +
                    "AND a.institucion.codInstitucion = :codInstitucion " +
                    "AND COALESCE(TO_CHAR(a.anioIngreso, 'FM9999'), '') LIKE :anoIngreso " +
                    "AND COALESCE(TO_CHAR(a.anioEgreso, 'FM9999'), '') LIKE  :anoEgreso " +
                    "AND COALESCE(TO_CHAR(a.anioTitulo, 'FM9999'), '') LIKE :anoTitulo " +
                "ORDER BY a.usuario.apellidoPaterno ASC",
            countQuery = "SELECT COUNT(DISTINCT a.usuario) " +
                        "FROM AntecedenteEducacional AS a " +
                        "WHERE COALESCE(TO_CHAR(a.carrera.codCarrera, 'FM9999'), '') LIKE :codCarrera " +
                            "AND a.institucion.codInstitucion = :codInstitucion " +
                            "AND COALESCE(TO_CHAR(a.anioIngreso, 'FM9999'), '') LIKE :anoIngreso " +
                            "AND COALESCE(TO_CHAR(a.anioEgreso, 'FM9999'), '') LIKE  :anoEgreso " +
                            "AND COALESCE(TO_CHAR(a.anioTitulo, 'FM9999'), '') LIKE :anoTitulo " )
    Page<Usuario> buscarUsuariosPorCarreraInstitucionIngresoEgresoTitulacion(@Param("codInstitucion") Short codInstitucion,
                                                                             @Param("codCarrera") String codCarrera,
                                                                             @Param("anoIngreso") String anoIngreso,
                                                                             @Param("anoEgreso") String anoEgreso,
                                                                             @Param("anoTitulo") String anoTitulo,
                                                                             Pageable page);

    /**
     * Retorna una lista paginada de {@link crm.entities.Usuario} que pertenescan a un pais, region, provincia o
     * comuna
     *
     * @param idPais La id del pais a buscar
     * @param idRegion La id de la region a buscar (Opcional)
     * @param idProvincia La id de la provincia a buscar (Opcional)
     * @param idComuna La id de la comuna a buscar (Opcional)
     * @param page
     *
     * @return {@link java.util.Collection} de {@link crm.entities.Usuario}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query(value = "SELECT u " +
                "FROM Usuario AS u " +
                "WHERE u.pais.id = :idPais " +
                "AND COALESCE(TO_CHAR(u.comuna.provincia.region.id, 'FM9999'), '') LIKE :idRegion " +
                "AND COALESCE(TO_CHAR(u.comuna.provincia.id, 'FM9999'), '') LIKE :idProvincia " +
                "AND COALESCE(TO_CHAR(u.comuna.id, 'FM9999'), '') LIKE :idComuna " +
                "ORDER BY u.apellidoPaterno ASC",
        countQuery = "SELECT COUNT(u) " +
                "FROM Usuario AS u " +
                "WHERE u.pais.id = :idPais " +
                "AND COALESCE(TO_CHAR(u.comuna.provincia.region.id, 'FM9999'), '') LIKE :idRegion " +
                "AND COALESCE(TO_CHAR(u.comuna.provincia.id, 'FM9999'), '') LIKE :idProvincia " +
                "AND COALESCE(TO_CHAR(u.comuna.id, 'FM9999'), '') LIKE :idComuna")
    Page<Usuario> busquedaGeografica(@Param("idPais") Short idPais,
                                     @Param("idRegion") String idRegion,
                                     @Param("idProvincia") String idProvincia,
                                     @Param("idComuna") String idComuna,
                                     Pageable page);

    /**
     * Retorna un listado de manera paginada, de {@link crm.entities.Usuario} que esten asociados a una empresa en su
     * {@link crm.entities.ActividadExalumno}
     *
     * @param idEmpresa id de la empresa sobre la cual se desea buscar usuarios con actividadExalumno relacionada a ella .
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT u " +
                "FROM ActividadExalumno AS a " +
                "JOIN a.usuario AS u " +
                "JOIN a.empresa AS e " +
                "WHERE e.id = :idEmpresa ORDER BY u.apellidoPaterno ASC",
            countQuery= "SELECT COUNT(DISTINCT u) " +
                        "FROM ActividadExalumno AS a " +
                        "JOIN a.usuario AS u " +
                        "JOIN a.empresa AS e " +
                        "WHERE e.id = :idEmpresa ")
    Page<Usuario> buscarUsuariosDeActividadExalumnoPorIdEmpresa(@Param("idEmpresa") Long idEmpresa,
                                                                Pageable page);

    /**
     * Obtiene a los usuarios repetidos segun su email.
     *
     * @param page Sublista a obtener.
     *
     * @return Lista pagina de strings con los emails de los usuarios.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value="SELECT LOWER(u.email), COUNT(1) FROM Usuario AS u GROUP BY LOWER(u.email) HAVING COUNT(1) > 1",
            countQuery = "SELECT COUNT(u.email) FROM Usuario AS u GROUP BY LOWER(u.email) HAVING COUNT(1) > 1")
    Page<List<String>> getUsuariosRepetidosPorEmail(Pageable page);

    /**
     * Obtiene a los usuarios repetidos segun sus nombres.
     *
     * @param page Sublista a obtener.
     *
     * @return Lista pagina de strings con los nombres de los usuarios.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value="SELECT UPPER(u.nombres), UPPER(u.apellidoPaterno), UPPER(u.apellidoMaterno), COUNT(1) FROM Usuario AS u GROUP BY UPPER(u.nombres), UPPER(u.apellidoPaterno), UPPER(u.apellidoMaterno) HAVING COUNT(1) > 1",
            countQuery = "SELECT COUNT(u.apellidoPaterno) FROM Usuario AS u GROUP BY UPPER(u.nombres), UPPER(u.apellidoPaterno), UPPER(u.apellidoMaterno) HAVING COUNT(1) > 1")
    Page<List<String>> getUsuariosRepetidosPorNombres(Pageable page);

    /**
     * Obtiene a los {@link crm.entities.Usuario} repetidos segun su rut.
     *
     * @param page datos que permiten la paginación.
     *
     * @return Coleccion {@Link java.util.Page}  contenedora de los rut repetidos; para cada uno de ellos
     *          Coleccion {@link java.util.List} que contiene el rut y la cantidad repetida
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT u.rut, COUNT(1) " +
                "FROM Usuario AS u " +
                "GROUP BY u.rut " +
                "HAVING COUNT(1) > 1",
            countQuery = "SELECT COUNT(1) " +
                            "FROM Usuario AS u " +
                            "GROUP BY u.rut " +
                            "HAVING COUNT(1) > 1")
    Page<List<String>> getUsuariosRepetidosPorRut(Pageable page);





    /**
     * Obtiene una lista de {@link crm.entities.Usuario} por nombre y apellidos, haciendo ignorando el case de ellos
     *
     * @param nombres Nombres del usuario a buscar.
     * @param paterno Apellido paterno del usuario a buscar.
     * @param materno Apellido materno del usuario a buscar.
     *
     * @return {@link java.util.List} de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT u FROM Usuario u WHERE UPPER(translate( u.nombres ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT(UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') AND UPPER(translate( u.apellidoPaterno ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE UPPER(translate(:paterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) AND UPPER(translate( u.apellidoMaterno ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE UPPER(translate(:materno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))")
    List<Usuario> buscarUsuariosPorNombreYApellidos(@Param("nombres") String nombres, @Param("paterno") String paterno, @Param("materno") String materno);




    /**
     * Cambia el rut de un usuario por otro especificado.
     *
     * @param rut Rut a actualizar en la base de datos.
     * @param id Id del usuario al que se desea cambiarle el rut.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE Usuario u set rut = :rut WHERE id = :id")
    @Transactional
    void actualizarRut(@Param("rut") Integer rut, @Param("id") Long id);



      /*
        Actualizacion de los ids para la parte del mezclador de usuario. Esta hecho a base de SQL plano.
        No funciona eso si. Por eso no termine de comentar.
     */
    /**
     * Actualiza el usuaex_id de la tabla aexa.aporte_socio que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE aexa.aporte_socio set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarAporteSocio(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla archivos_adjuntos_exalumno que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE archivos_adjuntos_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarArchivosAdjuntos(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla conocimientos_info_exalumno que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE conocimientos_info_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarConocimientoInfo(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla empleo.manejo_idioma_ofe_alumno que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE empleo.manejo_idioma_ofe_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarManejoIdiomaOfe(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla aexa.recado_contacto que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE aexa.recado_contacto set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarRecadoContactoId(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE aexa.recado_contacto set usu_usuaex_id= :id1 where usu_usuaex_id = :id2", nativeQuery = true)
    void actualizarRecadoContacto(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla aexa.stickynotes_aexa que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE aexa.stickynotes_aexa set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarStickyNotesId(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla aexa.aporte_socio que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE aexa.stickynotes_aexa set usu_usuaex_id= :id1 where usu_usuaex_id = :id2", nativeQuery = true)
    void actualizarStickyNotes(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla crawler_ofertas.oferta_crawled que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE crawler_ofertas.oferta_crawled set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarCrawlerOferta(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE crm.autorizacion_usuario set usuario_id = :id1 where usuario_id = :id2", nativeQuery = true)
    void actualizarAutorizacionUsuario(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Actualiza el usuaex_id de la tabla crm.perfil_aexa que posea como usuaex_id el id2.
     *
     * @param id1 usuaex_id por el cual se cambiara el usuaex_id actual.
     * @param id2 usuaex_id del usuario antiguo que poseia el registro.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "UPDATE crm.perfil_aexa set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarPerfilAexa(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE empleo.filtro_oferta_laboral set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarOfertaLaboral(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE empleo.postulacion_archivos_adjuntos set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarPostulacionArchivos(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE empleo.postulacion_ofe_lab_usmempleo set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarPostulacionOfeLab(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE empleo.postulante_favorito set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarPostulanteFavorito(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE empleo.respuesta_pre_ofe_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarRespuestaPreOfe(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE empleo.usuario_visto_usmempleo set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarUsuarioVisto(@Param("id1") Long id1, @Param("id2") Long id2);

    //@Query(value = "UPDATE org.contacto_historico_empresa set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    //void actualizarContactoHistoricoEmpresa(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE capacitacion_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarCapacitacion(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE competencia_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarCompetencia(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE correos_validados set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarCorreosValidados(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE dueño_empresa set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarDuenioEmpresa(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE manejo_idioma set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarManejoIdioma(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE pagina_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarPagina(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE respaldo_usuario_aexa_contacto set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarRespaldoUsuario(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE respuesta_usu_aexa set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarRespuestaUsuAexa(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE test_psicologico_exalumno set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarTestPsicologico(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE usuario_aexa_apoderado set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarUsuarioAexaApoderado(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE video_curriculos_empleos.invitacion_video_entrevista_usuempleo set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarInvitacionVideo(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE video_curriculos_empleos.video_curriculo_usuario_aexa set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarVideoCurriculo(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(value = "UPDATE video_curriculos_empleos.video_entrevista_usmempleo set usuaex_id= :id1 where usuaex_id = :id2", nativeQuery = true)
    void actualizarVideoEntrevista(@Param("id1") Long id1, @Param("id2") Long id2);




    /**
     * Obtiene un String con la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} ) que
     * han sido modificado dentro de los 3, 6, 12, 18, 24, 36, y mayor a 36 meses, según la fecha de modificación del curriculo
     * y la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} del {@link crm.entities.AntecedenteEducacional}
     * asociado al {@link crm.entities.Usuario}
     *
     * @return String contenedora de la cantidad de curriculos que han sido modificado dentro
     *          de los 3, 6, 12, 18, 24, 36 meses (cantidades separadas por coma)
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     * TODO buscar alguna forma para implementar las cotas solo por medio de hql.
     */
    @Query(value="SELECT COUNT(CASE WHEN u.fechaModificacion >= :cota3 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaModificacion >= :cota6 AND u.fechaModificacion < :cota3 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaModificacion >= :cota12 AND u.fechaModificacion < :cota6  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaModificacion >= :cota18 AND u.fechaModificacion < :cota12  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaModificacion >= :cota24 AND u.fechaModificacion < :cota18  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaModificacion >= :cota36 AND u.fechaModificacion < :cota24 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaModificacion < :cota36 THEN 1 END), " +
                        "COUNT(1) " +
                "FROM Usuario AS u " +
                "WHERE u IN (SELECT DISTINCT u " +
                            "FROM AntecedenteEducacional AS a " +
                            "JOIN a.usuario AS u " +
                            "WHERE a.institucion.codInstitucion = :codInstitucion " +
                                "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera ) " )
    String indiceCurriculosPorFechaModificacion( @Param("codInstitucion") Short codInstitucion,
                                                 @Param("codCarrera") String codCarrera,
                                                 @Param("cota3") Date cota3,
                                                 @Param("cota6") Date cota6,
                                                 @Param("cota12") Date cota12,
                                                 @Param("cota18") Date cota18,
                                                 @Param("cota24") Date cota24,
                                                 @Param("cota36") Date cota36 );




    /**
     * Obtiene un String con la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} ),
     * segmentada por el sexo del {@link crm.entities.Usuario}, según la {@link crm.entities.Carrera} e
     * {@link crm.entities.Institucion} del {@link crm.entities.AntecedenteEducacional} asociado al
     * {@link crm.entities.Usuario}
     *
     * @return String contenedora de la cantidad de curriculos por sexo
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(CASE WHEN u.sexo = 0 THEN 1 END), " +
                        "COUNT(CASE WHEN u.sexo = 1 THEN 1 END), " +
                        "COUNT(CASE WHEN u.sexo = 2 THEN 1 END), " +
                        "COUNT(1) " +
                "FROM Usuario AS u " +
                "WHERE u IN (SELECT DISTINCT u " +
                            "FROM AntecedenteEducacional AS a " +
                            "JOIN a.usuario AS u " +
                            "WHERE TO_CHAR(a.institucion.codInstitucion, 'FM9999')  LIKE :codInstitucion " +
                                "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera ) " )
    String indiceCurriculosPorSexo ( @Param("codInstitucion") String codInstitucion,
                                     @Param("codCarrera") String codCarrera);



    /**
     * Obtiene un Array de String con la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado en rangos de edad. según  la {@link crm.entities.Carrera} e {@link crm.entities.Institucion} del
     * {@link crm.entities.AntecedenteEducacional} asociado al {@link crm.entities.Usuario}
     *
     * @return  String contenedor de la cantidad de curriculos según el rango de edad al que pertenece
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     * TODO buscar alguna forma para implementar las cotas solo por medio de hql.
     */
    @Query(value="SELECT COUNT(CASE WHEN u.fechaNacimiento > :cota20 AND u.fechaNacimiento <= :cota15 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota25 AND u.fechaNacimiento <= :cota20 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota30 AND u.fechaNacimiento <= :cota25 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota35 AND u.fechaNacimiento <= :cota30  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota40 AND u.fechaNacimiento <= :cota35  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota45 AND u.fechaNacimiento <= :cota40  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota50 AND u.fechaNacimiento <= :cota45  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota55 AND u.fechaNacimiento <= :cota50  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota60 AND u.fechaNacimiento <= :cota55  THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento > :cota65 AND u.fechaNacimiento <= :cota60 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento <= :cota65 THEN 1 END), " +
                        "COUNT(CASE WHEN u.fechaNacimiento IS NULL THEN 1 END), " +
                        "COUNT(1) " +
                "FROM Usuario AS u " +
                "WHERE u IN (SELECT DISTINCT u " +
                            "FROM AntecedenteEducacional AS a " +
                            "JOIN a.usuario AS u " +
                            "WHERE a.institucion.codInstitucion = :codInstitucion " +
                                "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera ) " )
    String indiceCurriculosPorEdad( @Param("codInstitucion") Short codInstitucion,
                                    @Param("codCarrera") String codCarrera,
                                    @Param("cota15") Date cota15,
                                    @Param("cota20") Date cota20,
                                    @Param("cota25") Date cota25,
                                    @Param("cota30") Date cota30,
                                    @Param("cota35") Date cota35,
                                    @Param("cota40") Date cota40,
                                    @Param("cota45") Date cota45,
                                    @Param("cota50") Date cota50,
                                    @Param("cota55") Date cota55,
                                    @Param("cota60") Date cota60,
                                    @Param("cota65") Date cota65 );



    /**
     * Obtiene un listado de la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado por el porcentaje del nivel de trayectoria completa del {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return  String contenedora de la cantidad de curriculos para cada segmento de porcentaje del nivel de trayectoria
     *          completa del Usuario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(CASE WHEN u.trayectoriaCompleta >= 0 AND u.trayectoriaCompleta < 10 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 10 AND u.trayectoriaCompleta < 20 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 20 AND u.trayectoriaCompleta < 30 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 30 AND u.trayectoriaCompleta < 40 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 40 AND u.trayectoriaCompleta < 50 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 50 AND u.trayectoriaCompleta < 60 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 60 AND u.trayectoriaCompleta < 70 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 70 AND u.trayectoriaCompleta < 80 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 80 AND u.trayectoriaCompleta < 90 THEN 1 END), " +
                        "COUNT(CASE WHEN u.trayectoriaCompleta >= 90 AND u.trayectoriaCompleta <= 100 THEN 1 END), " +
                        "COUNT(1) " +
                "FROM Usuario AS u " +
                "WHERE u IN (SELECT DISTINCT u " +
                            "FROM AntecedenteEducacional AS a " +
                            "JOIN a.usuario AS u " +
                            "WHERE a.institucion.codInstitucion = :codInstitucion " +
                                "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera ) " )
    String indiceCurriculosPorTrayectoriaCompleta ( @Param("codInstitucion") Short codInstitucion,
                                                    @Param("codCarrera") String codCarrera );




    /**
     * Obtiene un listado de la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado por el porcentaje de la Completitud de Contacto de los datos del {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return  String contenedora de la cantidad de curriculos para cada segmento de porcentaje del nivel de completitud
     *          del contacto del Usuario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(CASE WHEN u.completitudContacto >= 0  AND u.completitudContacto < 10 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 10 AND u.completitudContacto < 20 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 20 AND u.completitudContacto < 30 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 30 AND u.completitudContacto < 40 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 40 AND u.completitudContacto < 50 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 50 AND u.completitudContacto < 60 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 60 AND u.completitudContacto < 70 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 70 AND u.completitudContacto < 80 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 80 AND u.completitudContacto < 90 THEN 1 END), " +
                        "COUNT(CASE WHEN u.completitudContacto >= 90 AND u.completitudContacto <= 100 THEN 1 END), " +
                        "COUNT(1) " +
                "FROM Usuario AS u " +
                "WHERE u IN (SELECT DISTINCT u " +
                            "FROM AntecedenteEducacional AS a " +
                            "JOIN a.usuario AS u " +
                            "WHERE a.institucion.codInstitucion = :codInstitucion " +
                                "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera ) " )
    String indiceCurriculosPorCompletitudContacto ( @Param("codInstitucion") Short codInstitucion,
                                                    @Param("codCarrera") String codCarrera );





    /**
     * Actualiza el Rut de un {@link crm.entities.Usuario}
     *
     * @param idUsuario {@link crm.entities.Usuario} a realizar.
     * @param rut rut que se desea setear al {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE Usuario AS u SET u.rut = :rut, u.digitoVerificador = :digitoVerificador WHERE u.id = :idUsuario")
    void actualizarRut( @Param("idUsuario") Long idUsuario, @Param("rut") Integer rut , @Param("digitoVerificador") String digitoVerificador);



    /**
     * Elimina un {@link crm.entities.Usuario} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM Usuario AS u WHERE u.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    /**
     * Retorna un {@link crm.entities.Usuario} que posea un rut igual al parametro rut.
     *
     * @param rut rut que se desea buscar.
     *
     * @return {@link crm.entities.Usuario} con el rut que se entrego como parametro.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT u FROM Usuario u WHERE u.rut = :rut")
    Usuario findByRut(@Param("rut") Integer rut);





    /**
     * Retorna un {@link crm.entities.Usuario} que posea cualquiera de las propiedades email, email_laboral
     * o email opcional igual al parametro email.
     *
     * @param email rut que se desea buscar.
     *
     * @return {@link crm.entities.Usuario} con el email que se entrego como parametro.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT u FROM Usuario u WHERE u.email = :email OR u.emailOpcional = :email OR u.emailLaboral = :email")
    Usuario findByEmails(@Param("email") String email);





    /**
     * TODO comentar
     *
     * @param codInstitucion id de la institucion registrada en el Antecedente Educacional.
     * @param codCarrera id de la carrera registrado en el Antecedente Educacional.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Usuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT u  " +
            "FROM AntecedenteEducacional AS a " +
                "JOIN a.usuario AS u " +
                "JOIN a.institucion AS i " +
                "JOIN a.carrera AS c " +
            "WHERE c.codCarrera = :codCarrera " +
                "AND i.codInstitucion = :codInstitucion " +
            "ORDER BY u.apellidoPaterno ASC" )
    List<Usuario> buscarUsuariosPorCarreraInstitucion( @Param("codCarrera") Long codCarrera,
                                                       @Param("codInstitucion") Short codInstitucion);




    /**
     * Obtiene una lista de {@link crm.entities.Usuario} por nombre y apellido paterno
     *
     * @param nombres Nombres del usuario a buscar.
     * @param paterno Apellido paterno del usuario a buscar.
     *
     * @return {@link java.util.List} de {@link crm.entities.Usuario}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT u FROM Usuario u WHERE UPPER(translate( u.nombres ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT(UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') AND UPPER(translate( u.apellidoPaterno ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE UPPER(translate(:paterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))")
    List<Usuario> buscarUsuariosPorNombreYApellidoPaterno(@Param("nombres") String nombres, @Param("paterno") String paterno);





    /**
     * Obtiene una lista de {@link crm.entities.Usuario} que tengan nombre, apellido paterno o apellido materno igual a los parametros entregados
     *
     * @param nombres Nombres del usuario a buscar.
     * @param paterno Apellido paterno del usuario a buscar.
     * @param materno Apellido materno del usuario a buscar
     *
     * @return {@link java.util.List} de {@link crm.entities.Usuario}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT u FROM Usuario u WHERE UPPER(translate( u.nombres ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT(UPPER(translate(:nombres,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') OR UPPER(translate( u.apellidoPaterno ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE UPPER(translate(:paterno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) OR UPPER(translate( u.apellidoMaterno ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE UPPER(translate(:materno,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))")
    List<Usuario> buscarUsuariosPorNombresOApellidoPaternoOApellidoMaterno(@Param("nombres") String nombres, @Param("paterno") String paterno, @Param("materno") String materno);





    /**
     * Actualiza la trayectoria {@link crm.entities.Usuario}, registrando el valor recalculado
     *
     * @param idUsuario {@link crm.entities.Usuario} a realizarle la actualizacion
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE Usuario AS u SET u.trayectoriaCompleta = :trayectoria " +
                    "WHERE u.id = :idUsuario")
    void actualizarTrayectoria( @Param("idUsuario") Long idUsuario, @Param("trayectoria") Short trayectoria);
}
