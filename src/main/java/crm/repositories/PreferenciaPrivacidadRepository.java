package crm.repositories;

import crm.entities.PostulacionUsuarioAsesoria;
import crm.entities.PreferenciaPrivacidad;
import crm.entities.PreferenciaPrivacidadPK;
import crm.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PreferenciaPrivacidad}
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
public interface PreferenciaPrivacidadRepository extends CrudRepository<PreferenciaPrivacidad, PreferenciaPrivacidadPK> {


    /**
     * Retorna un {@link crm.entities.PreferenciaPrivacidad} que posean un usuarioId
     * igual al parametro buscado
     *
     * @param id id usado para la busqueda.
     *
     * @return Instancia de la {@link crm.entities.PreferenciaPrivacidad}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    PreferenciaPrivacidad findByUsuarioId(Long id);



    /**
     * Retorna el listado de todos los {@link crm.entities.PreferenciaPrivacidad}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.PreferenciaPrivacidad}.
     */
    List<PreferenciaPrivacidad> findAll();





    /**
     * Retorna una List con {@link crm.entities.PreferenciaPrivacidad} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.PreferenciaPrivacidad} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p " +
            "FROM PreferenciaPrivacidad AS p " +
            "WHERE p.usuarioId = :idUsuario ")
    List<PreferenciaPrivacidad> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PreferenciaPrivacidad}
     *
     * @param idInstitucionBuscar Id de {@link crm.entities.Institucion} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PreferenciaPrivacidad AS p " +
                    "SET p.usuarioId = :idUsuarioSetear " +
                    "WHERE p.codInstitucion = :idInstitucionBuscar " +
                        "AND p.usuarioId = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idInstitucionBuscar") Short idInstitucionBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Actualiza la {@link crm.entities.Institucion} asociada de un {@link PreferenciaPrivacidad} especifico
     *
     * @param idInstitucionBuscar Id de {@link crm.entities.Institucion} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link Usuario} al que se le desea setear el valor
     * @param codInstitucionSetear Nuevo Id de {@link crm.entities.Institucion} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PreferenciaPrivacidad AS p " +
                    "SET p.codInstitucion = :codInstitucionSetear " +
                    "WHERE p.codInstitucion = :idInstitucionBuscar " +
                        "AND p.usuarioId = :idUsuarioBuscar")
    void actualizarCodInstitucion(@Param("idInstitucionBuscar") Short idInstitucionBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("codInstitucionSetear") Short codInstitucionSetear);





    /**
     * Elimina un {@link crm.entities.PreferenciaPrivacidad} segun los id especificados como parametro
     *
     * @param codInstitucionBuscar Id de la {@link crm.entities.Institucion} asociado a {@link crm.entities.PreferenciaPrivacidad}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PreferenciaPrivacidad}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PreferenciaPrivacidad AS p " +
                    "WHERE p.codInstitucion = :codInstitucionBuscar " +
                        "AND p.usuarioId = :idUsuarioBuscar")
    void eliminar(@Param("codInstitucionBuscar") Short codInstitucionBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);




    /**
     * Obtiene un listado de  {@link crm.entities.PreferenciaPrivacidad}, según un id de {@link crm.entities.Institucion}
     *
     * @param codInstitucion id de la {@link crm.entities.Institucion} asociada a {@link crm.entities.PreferenciaPrivacidad}
     *
     * @return  {@link crm.entities.PreferenciaPrivacidad} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p " +
            "FROM PreferenciaPrivacidad AS p " +
            "WHERE p.institucion.codInstitucion = :codInstitucion ")
    List<PreferenciaPrivacidad> buscarPorCodInstitucion(@Param("codInstitucion") Short codInstitucion);

}
