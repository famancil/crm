package crm.repositories;

import crm.entities.PostulanteFavorito;
import crm.entities.PostulanteFavoritoPK;
import crm.entities.RespuestaUsuario;
import crm.entities.RespuestaUsuarioPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RespuestaUsuario}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface RespuestaUsuarioRepository extends CrudRepository<RespuestaUsuario, RespuestaUsuarioPK> {



    /**
     * Retorna una List con {@link crm.entities.RespuestaUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.RespuestaUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RespuestaUsuario AS r WHERE r.idUsuario = :idUsuario ")
    List<RespuestaUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.RespuestaUsuario}
     *
     * @param idPreguntasUsuarioBuscar Id de {@link crm.entities.PreguntasUsuario} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE RespuestaUsuario AS r SET r.idUsuario = :idUsuarioSetear WHERE r.codPreguntaUsuario = :idPreguntasUsuarioBuscar AND r.idUsuario = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idPreguntasUsuarioBuscar") Long idPreguntasUsuarioBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.RespuestaUsuario} segun los id especificados como parametro
     *
     * @param idPreguntasUsuarioBuscar Id del {@link crm.entities.PreguntasUsuario} asociado a {@link crm.entities.RespuestaUsuario}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RespuestaUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RespuestaUsuario AS r WHERE r.codPreguntaUsuario = :idPreguntasUsuarioBuscar AND r.idUsuario = :idUsuarioBuscar")
    void eliminar(@Param("idPreguntasUsuarioBuscar") Long idPreguntasUsuarioBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
