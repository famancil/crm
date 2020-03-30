package crm.repositories;

import crm.entities.AutorizacionUsuario;
import crm.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AutorizacionUsuario}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface AutorizacionUsuarioRepository extends CrudRepository<AutorizacionUsuario, Long>{

    /**
     * Retorna un listado de {@link crm.entities.AutorizacionUsuario} buscando por nombre de objeto y un usuario que
     * ha recibido una autorizacion.
     *
     * @param nombreObjeto Nombre de objeto sobre el que se dan permisos.
     * @param usuario {@link crm.entities.Usuario} que recibio la autorizacion.
     * @return {@link java.util.List} de {@link crm.entities.AutorizacionUsuario} para los parametros entregados.
     */
    public List<AutorizacionUsuario> findByNombreObjetoAndUsuario(String nombreObjeto, Usuario usuario);




    /**
     * Retorna una List con {@link crm.entities.AutorizacionUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.AutorizacionUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AutorizacionUsuario AS a WHERE a.usuario.id = :idUsuario ")
    List<AutorizacionUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.AutorizacionUsuario}
     *
     * @param idAutorizacionUsuarioBuscar Id del {@link crm.entities.AutorizacionUsuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AutorizacionUsuario AS a SET a.usuario.id = :idUsuarioSetear WHERE a.id = :idAutorizacionUsuarioBuscar")
    void actualizarUsuarioId(@Param("idAutorizacionUsuarioBuscar") Long idAutorizacionUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);

    /**
     * Cuenta la cantidad de administradores en el crm por su rol de acceso
     *
     * @return String de la forma "SUPER_ADMIN, ADMIN_INSTITUCION"
     */
    @Query("SELECT COUNT(CASE WHEN ru.rolAcceso.nombre LIKE 'ROLE_SUPER_ADMIN' THEN 1 END), " +
            "COUNT(CASE WHEN ru.rolAcceso.nombre LIKE 'ROLE_ADMIN_INSTITUCION' THEN 1 END), " +
            "COUNT(CASE WHEN ru.rolAcceso.nombre LIKE 'ROLE_AYUDANTE_INSTITUCION' THEN 1 END), " +
            "COUNT(CASE WHEN ru.rolAcceso.nombre LIKE 'ROLE_LEER_INSTITUCION' THEN 1 END), " +
            "COUNT(CASE WHEN ru.rolAcceso.nombre LIKE 'ROLE_ADMIN_CARRERA' THEN 1 END) " +
            "FROM RolUsuario ru")
    String contarAdministradoresCrm();


    /**
     * Elimina un {@link crm.entities.AutorizacionUsuario} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.AutorizacionUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AutorizacionUsuario AS a WHERE a.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    /**
     * Retorna todas las autorizaciones de usuario que provengan de un rol de acceso en especifico
     *
     * @return Lista de AutorizacionUsuario
     */
    @Query("SELECT a FROM AutorizacionUsuario a WHERE a.rolAccesoId =:idRolAcceso AND a.usuario.id =:idUsuario ")
    List<AutorizacionUsuario> buscarPorIdRolAccesoYIdUsuario(@Param("idRolAcceso") Short idRolAcceso,@Param("idUsuario") Long idUsuario);
}
