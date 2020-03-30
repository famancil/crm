package crm.repositories;

import crm.entities.CorreosValidados;
import crm.entities.RespaldoUsuario;
import crm.entities.RespaldoUsuarioPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RespaldoUsuario}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface RespaldoUsuarioRepository extends CrudRepository<RespaldoUsuario, RespaldoUsuarioPK> {




    /**
     * Retorna una List con {@link crm.entities.RespaldoUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.RespaldoUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RespaldoUsuario AS r WHERE r.idUsuario = :idUsuario ")
    List<RespaldoUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.RespaldoUsuario}
     *
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param fechaModificacionBuscar fecha al que se desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE RespaldoUsuario AS r SET r.idUsuario = :idSetear WHERE r.fechaModificacion = :fechaModificacionBuscar AND r.idUsuario = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("fechaModificacionBuscar") Date fechaModificacionBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idSetear") Long idSetear);



    /**
     * Elimina un {@link crm.entities.RespaldoUsuario} segun los id especificados como parametro
     *
     * @param fechaModificacionBuscar Fecha del {@link crm.entities.RespaldoUsuario}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RespaldoUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RespaldoUsuario AS r WHERE r.fechaModificacion = :fechaModificacionBuscar AND r.idUsuario = :idUsuarioBuscar")
    void eliminar(@Param("fechaModificacionBuscar") Date fechaModificacionBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);



}
