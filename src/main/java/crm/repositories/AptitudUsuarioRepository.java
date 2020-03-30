package crm.repositories;

import crm.entities.AptitudUsuario;
import crm.entities.VideoCurriculoUsuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AptitudUsuario}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface AptitudUsuarioRepository extends CrudRepository<AptitudUsuario, Long> {




    /**
     * Retorna una List con {@link crm.entities.AptitudUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.AptitudUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AptitudUsuario AS a WHERE a.usuario.id = :idUsuario ")
    List<AptitudUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.AptitudUsuario}
     *
     * @param idAptitudUsuarioBuscar Id del {@link crm.entities.AptitudUsuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AptitudUsuario AS a SET a.usuario.id = :idUsuarioSetear WHERE a.id = :idAptitudUsuarioBuscar")
    void actualizarUsuarioId(@Param("idAptitudUsuarioBuscar") Long idAptitudUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.AptitudUsuario} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.AptitudUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AptitudUsuario AS a WHERE a.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
