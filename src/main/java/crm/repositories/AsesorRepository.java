package crm.repositories;

import crm.entities.Asesor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Asesor}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface AsesorRepository extends CrudRepository<Asesor, Long> {

    /**
     * Retorna un {@link crm.entities.Asesor} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param id id usado para la busqueda.
     * @return {@link crm.entities.Asesor}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    Asesor findByUsuarioId(Long id);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.Asesor}
     *
     * @param idBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE Asesor AS a SET a.usuarioId = :idSetear WHERE a.usuarioId = :idBuscar")
    void actualizarUsuarioId(@Param("idBuscar") Long idBuscar, @Param("idSetear") Long idSetear);



    /**
     * Elimina un {@link crm.entities.Asesor} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.Asesor}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM Asesor AS a WHERE a.usuarioId = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);




}
