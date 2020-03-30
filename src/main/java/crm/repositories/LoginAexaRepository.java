package crm.repositories;

import crm.entities.LoginAexa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.LoginAexa}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface LoginAexaRepository extends CrudRepository<LoginAexa, Long> {

    /**
     * Retorna un {@link crm.entities.LoginAexa} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param id id de Usuario usado para la busqueda.
     *
     * @return {@link crm.entities.LoginAexa} buscado
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    LoginAexa findByUsuarioId(Long id);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.LoginAexa}
     *
     * @param idBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE LoginAexa AS l SET l.usuarioId = :idSetear WHERE l.usuarioId = :idBuscar")
    void actualizarUsuarioId(@Param("idBuscar") Long idBuscar, @Param("idSetear") Long idSetear);


    /**
     * Elimina un {@link crm.entities.LoginAexa} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.LoginAexa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM LoginAexa AS l WHERE l.usuarioId = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    @Query(value = "SELECT l FROM LoginAexa AS l WHERE l.username = :username")
    LoginAexa findByUsername(@Param("username") String username);
}
