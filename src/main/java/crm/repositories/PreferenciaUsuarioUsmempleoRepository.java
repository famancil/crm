package crm.repositories;

import crm.entities.PreferenciaUsuarioUsmempleo;
import crm.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PreferenciaUsuarioUsmempleo}
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface PreferenciaUsuarioUsmempleoRepository extends CrudRepository<PreferenciaUsuarioUsmempleo, Long> {

    /**
     * Retorna un {@link crm.entities.PreferenciaUsuarioUsmempleo} que posean un usuarioId
     * igual al parametro que se le entregue
     * @param id id usado para la busqueda.
     * @return {@link crm.entities.PreferenciaUsuarioUsmempleo}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    PreferenciaUsuarioUsmempleo findByUsuarioId(Long id);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PreferenciaUsuarioUsmempleo}
     *
     * @param idBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PreferenciaUsuarioUsmempleo AS p SET p.usuarioId = :idSetear WHERE p.usuarioId = :idBuscar")
    void actualizarUsuarioId(@Param("idBuscar") Long idBuscar, @Param("idSetear") Long idSetear );



    /**
     * Elimina un {@link crm.entities.PreferenciaUsuarioUsmempleo} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.PreferenciaUsuarioUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PreferenciaUsuarioUsmempleo AS p WHERE p.usuarioId = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
