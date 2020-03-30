package crm.repositories;

import crm.entities.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RolUsuario}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface RolUsuarioRepository extends CrudRepository<RolUsuario, RolUsuarioPK>{



    /**
     * Retorna una List con {@link crm.entities.RolUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.RolUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT ru " +
            "FROM RolUsuario AS ru " +
            "WHERE ru.compositeId.usuarioId = :idUsuario ")
    List<RolUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.RolUsuario}
     *
     * @param idRolAccesoBuscar Id del {@link crm.entities.RolAcceso} asociado a {@link crm.entities.RolUsuario} al que se le desea setear el valor
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RolUsuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE RolUsuario AS ru " +
                    "SET ru.compositeId.usuarioId = :idUsuarioSetear " +
                    "WHERE ru.compositeId.idRolAcceso = :idRolAccesoBuscar " +
                        "AND ru.compositeId.usuarioId = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idRolAccesoBuscar") Short idRolAccesoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Elimina un {@link crm.entities.RolUsuario} segun los id especificados como parametro
     *
     * @param idRolAccesoBuscar Id del {@link crm.entities.RolAcceso} asociado a {@link crm.entities.RolUsuario}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RolUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RolUsuario AS ru " +
                    "WHERE ru.compositeId.idRolAcceso = :idRolAccesoBuscar " +
                        "AND ru.compositeId.usuarioId = :idUsuarioBuscar")
    void eliminar(@Param("idRolAccesoBuscar") Short idRolAccesoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
